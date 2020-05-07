package no.kristiania.exam.pg5100.frontend.controller;

import no.kristiania.exam.pg5100.backend.entity.Item;
import no.kristiania.exam.pg5100.backend.entity.User;
import no.kristiania.exam.pg5100.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

/**
 * Created by arcuri82 on 13-Dec-17.
 */
@Named
@RequestScoped
public class UserInfoController {

    @Autowired
    private UserService userService;

    String userEmail = getUserEmail();

    public String getUserEmail(){
        return ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public User getUser() {
        return userService.getUser(userEmail);
    }

    public List<Item> getUserCollection(){
        User user = getUser();
        return user.getInventory().getItemList();
    }

    public String millItem(Long itemId){
        userService.sellItem(userEmail, itemId);
        return "/ui/profile.jsf?faces-redirect=true";
    }

    public String buyLootBox(){
        User user = getUser();

        if(user.getMillCurrency() < 700) {
            return "/ui/redeem.jsf?faces-redirect=true&errorNotEnoughCurrency=true";
        }
        userService.buyLootBox(userEmail);
        return "/ui/redeem.jsf?faces-redirect=true";
    }

}
