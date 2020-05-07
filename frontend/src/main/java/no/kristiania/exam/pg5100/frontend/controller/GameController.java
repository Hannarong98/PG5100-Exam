package no.kristiania.exam.pg5100.frontend.controller;

import no.kristiania.exam.pg5100.backend.entity.User;
import no.kristiania.exam.pg5100.backend.service.InventoryService;
import no.kristiania.exam.pg5100.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class GameController implements Serializable {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private UserInfoController userInfoController;


    public String openLootBox() {
        String userEmail = userInfoController.getUserEmail();

        User user = userInfoController.getUser();
        if(user.getLootBoxesLeft() == 0){
            return "/ui/redeem.jsf?faces-redirect=true&errorNoLootBox=true";
        }

        inventoryService.openLootBox(userEmail);
        return "/ui/redeem.jsf?faces-redirect=true";
    }


}
