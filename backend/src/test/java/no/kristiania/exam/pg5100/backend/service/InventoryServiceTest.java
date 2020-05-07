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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
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

        inventoryService.createInventory(email);

        User updated = userService.getUser(email);

        assertEquals(0, updated.getInventory().getItemList().size());

    }

    @Test
    public void testAddItemToInventory() {

        createUser();
        initThreeTestItems();

        User user = userService.getUser(email);
        assertNull(user.getInventory());

        inventoryService.createInventory(email);
        User updated = userService.getUser(email);


        List<Item> items = itemService.getRandomItems(3);

        assertEquals(0, updated.getInventory().getItemList().size());

        inventoryService.addCardsToInventory(email, items);
        User updatedAgain = userService.getUser(email);

        assertEquals(3, updatedAgain.getInventory().getItemList().size());

    }

    private void initThreeTestItems() {
        itemService.createItem("one", Rarity.RARE, "Some description", 9999);
        itemService.createItem("two", Rarity.RARE, "Some description", 9999);
        itemService.createItem("three", Rarity.RARE, "Some description", 9999);
    }

}
