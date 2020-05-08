// THIS CLASS IS COPIED FROM
// https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-06/src/main/java/org/tsdes/intro/exercises/quizgame/ejb/DefaultDataInitializerEjb.java

package no.kristiania.exam.pg5100.backend.service;


import no.kristiania.exam.pg5100.backend.entity.Rarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

        //Default user
        attempt(() -> userService.createUser("gaben@pipe.com", "123", "Gabe", "Bowel"));

        //Legendary cards
        attempt(()-> itemService.createItem("Evocation", Rarity.LEGENDARY, "Fill your hand with random mage spells.", 1000));
        attempt(()-> itemService.createItem("Reliquary of Souls", Rarity.LEGENDARY, "Lifesteal Deathrattle: Shuffle 'Reliquary Prime' into your deck.", 1000));
        attempt(()-> itemService.createItem("Untapped Potential", Rarity.LEGENDARY, "Quest: End 4 turns with any unspent Mana. Reward: Ossirian Tear.", 1000));
        attempt(()-> itemService.createItem("Astromancer Solarian", Rarity.LEGENDARY, "Spell Damage +1. Deathrattle: Shuffle 'Solarian Prime' into your deck.", 1000));
        attempt(()-> itemService.createItem("Bloodmage Thalnos", Rarity.LEGENDARY, "Spell Damage +1. Deathrattle: Draw a card.", 1000));

        //Epic cards
        attempt(()-> itemService.createItem("Ancestor Call", Rarity.EPIC, "Put a random minion from each player's hand into the battlefield.", 750));
        attempt(()-> itemService.createItem("Academic Espionage", Rarity.EPIC, "Shuffle 10 cards from your opponent's clsas into your deck.", 750));
        attempt(()-> itemService.createItem("Anubisath Defender", Rarity.EPIC, "Taunt that cost 0. Costs if you've cast a spell that or more this turn.", 750));
        attempt(()-> itemService.createItem("A New Challenger", Rarity.EPIC, "Discover: a 6-Cost minion. Summon it with Taunt and Divine Shield", 750));
        attempt(()-> itemService.createItem("Ancient of War", Rarity.EPIC, "Choose One: +5 Attack or +5 Health and Taunt", 750));

        //Rare cards
        attempt(()-> itemService.createItem("Air Raid", Rarity.RARE, "Twinspell: summon two 1/1 Silver Hand Recruits with Taunt", 500));
        attempt(()-> itemService.createItem("Aldo Peacekeeper", Rarity.RARE, "Battlecry: change an enemy minion's attack to 1", 500));
        attempt(()-> itemService.createItem("Alexstrasza's Champion", Rarity.RARE, "If you're holding a Dragon, gain 01 Attack and Charge", 500));
        attempt(()-> itemService.createItem("Ambush", Rarity.RARE, "Secret: After your opponent plays a minion, summon a 2/3 Ambusher with Poisonous", 500));
        attempt(()-> itemService.createItem("Angry Chicken", Rarity.RARE, "Enrage: +5 Attack.", 500));

        //Common cards
        attempt(()-> itemService.createItem("A Ligth in the Darkness", Rarity.COMMON, "Discover: a minion and give it +1/+1.", 250));
        attempt(()-> itemService.createItem("Acolyte of Pain", Rarity.COMMON, "Whenever this minion takes damage, draw a card", 250));
        attempt(()-> itemService.createItem("Adaptation", Rarity.COMMON, "Adapt a friendly minion", 250));
        attempt(()-> itemService.createItem("Aeon Reaver", Rarity.COMMON, "Battlecry: Deal damage to a minion equal to its Attack", 250));
        attempt(()-> itemService.createItem("Amber Watcher", Rarity.COMMON, "Battlecry: Restore 8 Health", 250));
    }


    private <T> T attempt(Supplier<T> lambda) {
        try {
            return lambda.get();
        } catch (Exception e) {
            return null;
        }
    }
}
