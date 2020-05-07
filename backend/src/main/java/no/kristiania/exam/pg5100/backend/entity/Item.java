package no.kristiania.exam.pg5100.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ITEMS")
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(max = 128)
    @Column(unique = true)
    private String title;

    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    @NotNull
    @Size(max = 255)
    private String description;

    @NotNull
    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
