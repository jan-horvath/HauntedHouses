package cz.muni.fi.pa165.hauntedhouses.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Jan Horvath
 */

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    public Player() {
    }

    public Player(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getEmail().equals(player.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }
}
