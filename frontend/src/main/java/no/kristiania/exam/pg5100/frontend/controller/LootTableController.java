package no.kristiania.exam.pg5100.frontend.controller;

import no.kristiania.exam.pg5100.backend.entity.Item;
import no.kristiania.exam.pg5100.backend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class LootTableController implements Serializable {

    @Autowired
    private ItemService itemService;

    public List<Item> getAllItems() {
       return itemService.getAllItems();
    }
}
