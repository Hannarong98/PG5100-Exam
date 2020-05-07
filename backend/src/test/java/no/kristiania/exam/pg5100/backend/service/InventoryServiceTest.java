package no.kristiania.exam.pg5100.backend.service;

import no.kristiania.exam.pg5100.backend.entity.Item;
import no.kristiania.exam.pg5100.backend.entity.Rarity;
import no.kristiania.exam.pg5100.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceTest extends ServiceTestBase {

    @Autowired
    private UserService userService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ItemService itemService;

    String email = "foo@foo.com";

    public void createUser() {
        userService.createUser(email, "bar", "baz", "qux");
    }

    @Test
    public void testCreateInventory() {
        createUser();
        User user = userService.getUser(email);
        assertNull(user.getInventory());

        Long itemId = itemService.createItem("test", Rarity.COMMON, "test", 1);
        inventoryService.createCopy(email);

        inventoryService.addItemToCollectionList(email, itemId);

        User same = userService.getUser(email);

        assertTrue(same.getInventory().getItemList().size() > 0);

    }


    @Test
    public void testCountItemById() {
        createUser();
        User user = userService.getUser(email);
        assertNull(user.getInventory());

        Long itemOneId = itemService.createItem("test", Rarity.COMMON, "test", 1);
        Long itemTwoId = itemService.createItem("123", Rarity.COMMON, "test", 1);
        inventoryService.createCopy(email);

        inventoryService.addItemToCollectionList(email, itemOneId);
        inventoryService.addItemToCollectionList(email, itemOneId);
        inventoryService.addItemToCollectionList(email, itemTwoId);

        User same = userService.getUser(email);

        assertEquals(2, same.getInventory().getItemQuantity(itemOneId));
        assertEquals(1, same.getInventory().getItemQuantity(itemTwoId));
    }
}
