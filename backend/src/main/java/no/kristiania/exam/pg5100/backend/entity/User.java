package no.kristiania.exam.pg5100.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 128)
    private String firstname;

    @NotBlank
    @Size(max = 128)
    private String surname;

    @Min(0)
    @NotNull
    private int lootBoxesLeft;

    @Min(0)
    @NotNull
    private int millCurrency;

    @NotBlank
    private String password;

    @OneToOne
    private Inventory inventory;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    @NotNull
    private Boolean enabled;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String forename) {
        this.firstname = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getLootBoxesLeft() {
        return lootBoxesLeft;
    }

    public void setLootBoxesLeft(int lootBoxesLeft) {
        this.lootBoxesLeft = lootBoxesLeft;
    }

    public int getMillCurrency() {
        return millCurrency;
    }

    public void setMillCurrency(int millCurrency) {
        this.millCurrency = millCurrency;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
