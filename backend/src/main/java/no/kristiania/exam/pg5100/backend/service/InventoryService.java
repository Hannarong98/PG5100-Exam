package no.kristiania.exam.pg5100.backend.service;

import no.kristiania.exam.pg5100.backend.entity.Inventory;
import no.kristiania.exam.pg5100.backend.entity.Item;
import no.kristiania.exam.pg5100.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;

@Service
@Transactional
public class InventoryService {

    @Autowired
    private EntityManager em;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    public Long createCopy(String email){

        User user = em.find(User.class, email);
        if(user == null){
            throw new IllegalArgumentException("User does not exist");
        }

        Inventory copy = new Inventory();
        copy.setUser(user);
        copy.setItemList(new ArrayList<>());

        em.persist(copy);
        user.setInventory(copy);
        em.merge(copy);

        return copy.getId();
    }

    public boolean addItemToCollectionList(String email, Long itemId){

        User user = userService.getUser(email);

        Item item = itemService.getItem(itemId);

        Inventory copy = user.getInventory();
        if(copy != null) {
            copy.getItemList().add(item);
            em.merge(copy);
        } else {
            createCopy(email);
            user.getInventory().getItemList().add(item);
        }
        return true;
    }





}
