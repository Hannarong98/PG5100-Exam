package no.kristiania.exam.pg5100.backend.service;

import no.kristiania.exam.pg5100.backend.entity.Item;
import no.kristiania.exam.pg5100.backend.entity.Rarity;
import no.kristiania.exam.pg5100.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;

@Service
@Transactional
public class ItemService {

    @Autowired
    private EntityManager em;

    public Long createItem(String title, Rarity rarity, String description, int price) {

        Item item = new Item();
        item.setTitle(title);
        item.setRarity(rarity);
        item.setDescription(description);
        item.setPrice(price);

        em.persist(item);

        return item.getId();
    }

    public Item getItem(long itemId){
        return em.find(Item.class, itemId);
    }
}
