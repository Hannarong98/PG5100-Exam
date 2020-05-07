package no.kristiania.exam.pg5100.backend.service;

import no.kristiania.exam.pg5100.backend.StubApplication;
import no.kristiania.exam.pg5100.backend.entity.Rarity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ServiceTestBase {

    @Autowired
    private ResetService deleteService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private InventoryService inventoryService;

    @BeforeEach
    public void cleanDatabase(){
        deleteService.resetDatabase();
    }

    String email = "foo@foo.com";

    protected void createUser() {
        userService.createUser(email, "bar", "baz", "qux");
    }

    protected void createUserWithInventory() {
        userService.createUser(email, "bar", "baz", "qux");
        inventoryService.createInventory(email);
    }

    protected void initThreeTestItems() {
        itemService.createItem("one", Rarity.RARE, "Some description", 9999);
        itemService.createItem("two", Rarity.RARE, "Some description", 9999);
        itemService.createItem("three", Rarity.RARE, "Some description", 9999);
    }
}
