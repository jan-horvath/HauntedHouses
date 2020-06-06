package cz.muni.fi.pa165.hauntedhouses.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author David Hofman
 */
@Entity
@Table(name = "game_instances")
public class GameInstance implements Serializable {
    @Id
    @GeneratedValue(generator = "pooled_generator")
    private Long id;

    @Column(nullable=false)
    private int banishesAttempted;

    @Column(nullable=false)
    private int banishesRequired;

    @OneToOne
    @JoinColumn(unique = true, nullable = false)
    private Player player;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "gameInstance")
    private Specter specter;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<House> houses;

    public GameInstance() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBanishesAttempted() {
        return banishesAttempted;
    }

    public void setBanishesAttempted(int banishesAttempted) {
        this.banishesAttempted = banishesAttempted;
    }

    public int getBanishesRequired() {
        return banishesRequired;
    }

    public void setBanishesRequired(int banishesRequired) {
        this.banishesRequired = banishesRequired;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Specter getSpecter() {
        return specter;
    }

    public void setSpecter(Specter specter) {
        this.specter = specter;
    }

    public Set<House> getHouses() {
        return houses;
    }

    public void setHouses(Set<House> houses) {
        this.houses = houses;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof GameInstance)) return false;
        if(this == obj) return true;
        GameInstance gameInstance = (GameInstance) obj;
        return getPlayer().equals(gameInstance.getPlayer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayer());
    }

    @Override
    public String toString() {
        return "GameInstance{" +
                "id=" + id +
                ", banishesAttempted=" + banishesAttempted +
                ", banishesRequired=" + banishesRequired +
                '}';
    }
}
