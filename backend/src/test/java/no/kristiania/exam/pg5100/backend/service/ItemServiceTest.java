package no.kristiania.exam.pg5100.backend.service;


import no.kristiania.exam.pg5100.backend.StubApplication;
import no.kristiania.exam.pg5100.backend.entity.Rarity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


public class ItemServiceTest extends ServiceTestBase {

    @Autowired
    private ItemService itemService;


    @Test
    public void testCreateItem() {
        Long itemId = itemService.createItem(
                getTestTitle(), getTestRarity(), "Some description", 9999);

        assertNotNull(itemId);
    }

    @Test
    public void testThrowIfSameTitle() {

        Long itemId = itemService.createItem(
                getTestTitle(), getTestRarity(), "Some description", 9999);

        assertNotNull(itemId);


        assertThrows(DataIntegrityViolationException.class, () ->
                itemService.createItem(getTestTitle(), Rarity.LEGENDARY, "Some description", 9999));
    }

    private String getTestTitle() {
        return "Nithog";
    }

    private Rarity getTestRarity() {
        return Rarity.EPIC;
    }



}
