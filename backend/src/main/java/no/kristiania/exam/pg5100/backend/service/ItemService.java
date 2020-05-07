package no.kristiania.exam.pg5100.backend.service;

import no.kristiania.exam.pg5100.backend.entity.Item;
import no.kristiania.exam.pg5100.backend.entity.Rarity;
import no.kristiania.exam.pg5100.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

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

    public List<Item> getAllItems(){
        TypedQuery<Item> query = em.createQuery("select i from Item i", Item.class);

        return query.getResultList();
    }

    public List<Item> getRandomItems(int n, long categoryId){

        TypedQuery<Long> sizeQuery= em.createQuery("select count(i) from Item i where i.id=?1", Long.class);
        sizeQuery.setParameter(1, categoryId);
        long size = sizeQuery.getSingleResult();

        if(n > size){
            throw new IllegalArgumentException("Cannot choose " + n + " unique items out of the " + size + " existing");
        }

        Random random = new Random();

        List<Item> items = new ArrayList<>();
        Set<Integer> chosen = new HashSet<>();

        while(chosen.size() < n) {

            int k = random.nextInt((int)size);
            if(chosen.contains(k)){
                continue;
            }
            chosen.add(k);

            TypedQuery<Item> query = em.createQuery(
                    "select i from Item i where i.id=?1", Item.class);
            query.setParameter(1, categoryId);
            query.setMaxResults(1);
            query.setFirstResult(k);

            items.add(query.getSingleResult());
        }
        return  items;
    }
}
