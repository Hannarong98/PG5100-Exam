package no.kristiania.exam.pg5100.backend.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;;

@Entity
public class Inventory {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItemList() {
        return items;
    }

    public void setItemList(List<Item> items) {
        this.items = items;
    }


}
