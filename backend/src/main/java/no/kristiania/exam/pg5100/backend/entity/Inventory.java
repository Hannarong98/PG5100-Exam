package no.kristiania.exam.pg5100.backend.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
public class Inventory {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Item> items;

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

    public long getItemQuantity(Long itemId) {
        return items.stream()
                .filter(e-> e.getId().equals(itemId)).count();
    }


    public void setItemList(List<Item> items) {
        this.items = items;
    }


}
