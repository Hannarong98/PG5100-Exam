package no.kristiania.exam.pg5100.backend.service;


import no.kristiania.exam.pg5100.backend.StubApplication;
import no.kristiania.exam.pg5100.backend.entity.Item;
import no.kristiania.exam.pg5100.backend.entity.Rarity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class ItemServiceTest extends ServiceTestBase {

    @Autowired
    private ItemService itemService;


    @Test
    public void testCreateAndRetrieveItem() {
        Long itemId = itemService.createItem(getTestTitle(), getTestRarity(), "Some description", 9999);

        assertNotNull(itemService.getItem(itemId));
    }

    @Test
    public void testThrowIfSameTitle() {

        Long itemId = itemService.createItem(getTestTitle(), getTestRarity(), "Some description", 9999);

        assertNotNull(itemId);


        assertThrows(DataIntegrityViolationException.class, () ->
                itemService.createItem(getTestTitle(), Rarity.LEGENDARY, "Some description", 9999));
    }


    @Test
    public void testTooManyItems(){
        initThreeTestItems();

        assertThrows(IllegalArgumentException.class, ()-> itemService.getRandomItems(4));

    }

    @Test
    public void testGetRandom() {

        initThreeTestItems();

        Set<String> titles = new HashSet<>();

        for (int i = 0; i < 50; i++) {
            List<Item> items = itemService.getRandomItems(2);
            assertEquals(2, items.size());

            Item itemOne = items.get(0);
            Item itemTwo = items.get(1);

            assertNotEquals(itemOne.getTitle(), itemTwo.getTitle());

            titles.add(itemOne.getTitle());
            titles.add(itemOne.getTitle());
        }
            assertEquals(3, titles.size());
        assertTrue(titles.contains("one"));
        assertTrue(titles.contains("two"));

    }


    private String getTestTitle() {
        return "Nithog";
    }

    private Rarity getTestRarity() {
        return Rarity.EPIC;
    }


}
