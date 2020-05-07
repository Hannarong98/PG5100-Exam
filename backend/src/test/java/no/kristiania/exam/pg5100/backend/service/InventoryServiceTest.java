package no.kristiania.exam.pg5100.backend.service;

import no.kristiania.exam.pg5100.backend.StubApplication;
import no.kristiania.exam.pg5100.backend.entity.Item;
import no.kristiania.exam.pg5100.backend.entity.Rarity;
import no.kristiania.exam.pg5100.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class InventoryServiceTest extends ServiceTestBase {

    @Autowired
    private UserService userService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ItemService itemService;



    @Test
    public void testCreateInventory() {

        createUser();
        User user = userService.getUser(email);
        assertNull(user.getInventory());

        inventoryService.createInventory(email);

        User updated = userService.getUser(email);

        assertEquals(0, updated.getInventory().getItemList().size());

    }

    @Test
    public void testOpenLootBox() {

        createUserWithInventory();
        initThreeTestItems();

        User user = userService.getUser(email);
        assertEquals(0, user.getInventory().getItemList().size());

        inventoryService.openLootBox(email);
        User updated = userService.getUser(email);
        assertEquals(3, updated.getInventory().getItemList().size());

    }


    @Test
    public void testIncrementQuantityIfAlreadyOwned() {

        createUserWithInventory();
        initThreeTestItems();

        User user = userService.getUser(email);
        assertEquals(0, user.getInventory().getItemList().size());

        inventoryService.openLootBox(email);
        User updated = userService.getUser(email);

        //Three items to start with
        assertEquals(3, updated.getInventory().getItemList().size());

        assertEquals(1, updated.getInventory().getItemList().get(0).getQuantity());


        inventoryService.openLootBox(email);
        User updatedAgain = userService.getUser(email);

        //Still have three items but quantity are updated
        assertEquals(3, updatedAgain.getInventory().getItemList().size());

        assertEquals(2, updatedAgain.getInventory().getItemList().get(0).getQuantity());


    }



}
