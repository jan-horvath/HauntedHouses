package cz.muni.fi.pa165.hauntedhouses.dto;

import java.util.Objects;

/**
 * @author Zoltan Fridrich
 */
public class GameInstanceDTO {

    private Long id;

    private int banishesAttempted;

    private int banishesRequired;

    private PlayerDTO player;

    private SpecterDTO specter;

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

    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }

    public SpecterDTO getSpecter() {
        return specter;
    }

    public void setSpecter(SpecterDTO specter) {
        this.specter = specter;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof GameInstanceDTO)) return false;
        GameInstanceDTO gameInstance = (GameInstanceDTO) obj;
        return getPlayer().equals(gameInstance.getPlayer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayer());
    }
}
