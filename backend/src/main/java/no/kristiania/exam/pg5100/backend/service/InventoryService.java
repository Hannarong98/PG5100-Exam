package no.kristiania.exam.pg5100.backend.service;

import no.kristiania.exam.pg5100.backend.entity.Inventory;
import no.kristiania.exam.pg5100.backend.entity.Item;
import no.kristiania.exam.pg5100.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InventoryService {

    @Autowired
    private EntityManager em;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    public Long createInventory(String email){

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

    public boolean addCardsToInventory(String email, List<Item> itemsFromLootBox){

        User user = userService.getUser(email);

        Inventory inventory = user.getInventory();

        if(inventory != null) {

            for (int i = 0; i <= itemsFromLootBox.size(); i++) {

                Long itemId = itemsFromLootBox.get(i).getId();

                Item item = itemService.getItem(itemId);

                boolean alreadyOwned = user.getInventory().getItemList().contains(item);

                if(alreadyOwned){
                    Item ownedItem = user.getInventory().getItemList().get(i);
                    ownedItem.setQuantity(ownedItem.getQuantity() + 1);
                } else  {
                    item.setQuantity(1);
                    inventory.getItemList().add(item);
                }
            }

            em.merge(inventory);
        } else {
            createInventory(email);
            itemsFromLootBox.forEach(e-> user.getInventory().getItemList().add(e));
        }
        return true;
    }





}
