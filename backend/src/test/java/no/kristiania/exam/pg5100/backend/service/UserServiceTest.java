package no.kristiania.exam.pg5100.backend.service;


import no.kristiania.exam.pg5100.backend.StubApplication;
import no.kristiania.exam.pg5100.backend.entity.Item;
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
public class UserServiceTest extends ServiceTestBase{

    @Autowired
    private UserService userService;

    @Autowired
    private InventoryService inventoryService;


    @Test
    public void testCreateUser(){
      assertTrue(userService.createUser("foo@bar.baz", "1234", "foo", "bar"));
    }

    @Test
    public void testFailAlreadyCreated(){
        boolean isCreated = userService.createUser("foo@bar.baz", "1234", "foo", "bar");

        assertTrue(isCreated);

        boolean isCreatedAgain = userService.createUser("foo@bar.baz", "1234", "foo", "bar");

        assertFalse(isCreatedAgain);
    }

    @Test
    public void testCanRetrieveUser(){


        boolean isCreated = userService.createUser(email, "1234", "foo", "bar");

        assertTrue(isCreated);

        User user = userService.getUser(email);

        assertEquals("foo", user.getForename());
        assertEquals("foo@foo.com", user.getEmail());
        assertEquals("bar", user.getSurname());
        assertEquals(3000, user.getMillCurrency());
        assertEquals(3, user.getLootBoxesLeft());
        assertTrue(user.getEnabled());
    }


    @Test
    public void testBuyLootBox() {

        userService.createUser(email, "1234", "foo", "bar");
        User user = userService.getUser(email);

        assertEquals(3000, user.getMillCurrency());
        assertEquals(3, user.getLootBoxesLeft());

        userService.buyLootBox(email);

        User updated = userService.getUser(email);
        assertTrue(updated.getMillCurrency() < 3000);
        assertEquals(4, updated.getLootBoxesLeft());

    }

    @Test
    public void testNotEnoughCurrency(){
        userService.createUser(email, "1234", "foo", "bar");

        userService.updateUserCurrency(email, 300);

        assertFalse(userService.buyLootBox(email));

    }

    @Test
    public void testSellItem(){

        initThreeTestItems();
        createUserWithInventory();

        inventoryService.openLootBox(email);
        inventoryService.openLootBox(email);

        User user = userService.getUser(email);

        // User should own 2 copies of each items
        assertEquals(3, user.getInventory().getItemList().size());
        assertEquals(3000, user.getMillCurrency());

        for (int i = 0; i < 3; i++) {

            // Sell all copies of first item
            Long itemId = user.getInventory().getItemList().get(0).getId();
            userService.sellItem(email, itemId);

        }

        User updated = userService.getUser(email);

        assertTrue(updated.getMillCurrency() > 3000);
        assertEquals(2, updated.getInventory().getItemList().size());
    }

}
