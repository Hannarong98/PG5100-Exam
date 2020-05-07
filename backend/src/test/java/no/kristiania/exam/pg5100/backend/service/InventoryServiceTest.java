package no.kristiania.exam.pg5100.backend.service;

import no.kristiania.exam.pg5100.backend.entity.Rarity;
import no.kristiania.exam.pg5100.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    }
}
