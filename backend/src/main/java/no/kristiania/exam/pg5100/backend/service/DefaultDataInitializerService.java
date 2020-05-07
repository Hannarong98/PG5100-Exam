// THIS CLASS IS COPIED FROM
// https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-06/src/main/java/org/tsdes/intro/exercises/quizgame/ejb/DefaultDataInitializerEjb.java

package no.kristiania.exam.pg5100.backend.service;


import no.kristiania.exam.pg5100.backend.entity.Rarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.function.Supplier;


@Service
public class DefaultDataInitializerService {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private InventoryService inventoryService;


    @PostConstruct
    public void initialize() {

        attempt(() -> userService.createUser("foo@chocolatebar.baz", "123", "foo", "chocobar"));
        Long id = attempt(()-> itemService.createItem("Some", Rarity.RARE, "some rare item", 1000));
        attempt(()-> inventoryService.addItemToCollectionList("foo@chocolatebar.baz", id));

    }


    private <T> T attempt(Supplier<T> lambda) {
        try {
            return lambda.get();
        } catch (Exception e) {
            return null;
        }
    }
}
