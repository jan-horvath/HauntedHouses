package cz.muni.fi.pa165.hauntedhouses.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author David Hofman
 */
@Entity
public class GameInstance {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable=false)
    private int banishesAttempted;

    @Column(nullable=false)
    private int banishesRequired;

    @OneToOne
    @JoinColumn
    private Player player;

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
}
