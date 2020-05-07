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

    public Long createInventory(String email) {

        User user = em.find(User.class, email);
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }

        Inventory inventory = new Inventory();
        inventory.setUser(user);
        inventory.setItemList(new ArrayList<>());

        em.persist(inventory);
        user.setInventory(inventory);
        em.merge(inventory);

        return inventory.getId();
    }


    public Inventory getInventory(Long id){
        return em.find(Inventory.class, id);
    }

    public void openLootBox(String email) {

       List<Item> itemsFromLootBox = itemService.getRandomItems(3);

        User user = userService.getUser(email);

        Inventory inventory = getInventory(user.getInventory().getId());

        if (user.getLootBoxesLeft() != 0) {
            user.setLootBoxesLeft(user.getLootBoxesLeft() - 1);
        } else {
            return;
        }

        if (inventory != null) {

            for (int i = 0; i < itemsFromLootBox.size(); i++) {

                Long itemId = itemsFromLootBox.get(i).getId();

                Item item = itemService.getItem(itemId);

                boolean alreadyOwned = inventory.getItemList().contains(item);

                if (alreadyOwned) {
                    Item ownedItem = inventory.getItemList().get(i);
                    ownedItem.setQuantity(ownedItem.getQuantity() + 1);
                } else {
                    item.setQuantity(1);
                    inventory.getItemList().add(item);
                }
            }
        }


        //User lootbox counter updates
        em.merge(user);

        //Inventory updates
        em.merge(inventory);

    }


}
