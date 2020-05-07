package no.kristiania.exam.pg5100.backend.service;

import no.kristiania.exam.pg5100.backend.entity.Inventory;
import no.kristiania.exam.pg5100.backend.entity.Item;
import no.kristiania.exam.pg5100.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private InventoryService inventoryService;




    public boolean createUser(String email, String password, String forename, String surname) {

        String hashedPassword = passwordEncoder.encode(password);

        if (em.find(User.class, email) != null) {
            return false;
        }

        User user = new User();
        user.setEmail(email);
        user.setForename(forename);
        user.setSurname(surname);
        user.setPassword(hashedPassword);
        user.setLootBoxesLeft(3);
        user.setMillCurrency(3000);
        user.setRoles(Collections.singleton("USER"));
        user.setEnabled(true);

        em.persist(user);

        return true;
    }

    public User getUser(String userEmail){
        return em.find(User.class, userEmail);
    }

    public boolean buyLootBox(String userEmail){
        User user = getUser(userEmail);

        int price = 700;

        if(user.getMillCurrency() >= price){
            user.setLootBoxesLeft(user.getLootBoxesLeft() + 1);
        } else {
            return false;
        }

        em.merge(user);

        return true;
    }

    public boolean sellItem(String userEmail, Long itemId) {
        User user = getUser(userEmail);
        List<Item> itemsInInventory = user.getInventory().getItemList();

        Item found = itemsInInventory.stream()
                .filter(e-> e.getId().equals(itemId))
                .findAny()
                .orElse(null);

        if(found != null){
            if(found.getQuantity() == 1){
                found.setQuantity(0);
                itemsInInventory.remove(found);
                user.setMillCurrency(user.getMillCurrency() + found.getPrice());
            } else {
                found.setQuantity(found.getQuantity() - 1);
            }
        } else {
            return false;
        }
        return true;
    }

    public List<User> getAllUsers(){
        TypedQuery<User> query = em.createQuery("select u from User u", User.class);

        return query.getResultList();
    }
}
