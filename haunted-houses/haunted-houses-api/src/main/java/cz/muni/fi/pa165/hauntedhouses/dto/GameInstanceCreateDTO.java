package cz.muni.fi.pa165.hauntedhouses.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Zoltan Fridrich
 */
public class GameInstanceCreateDTO {

    @Min(value = 1, message = "Number of banishments should be positive")
    @Max(value = 20, message = "The maximum number of banishments allowed is 20")
    private int banishesRequired;

    @NotNull
    private PlayerDTO player;

    private SpecterDTO specter;

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
        if(!(obj instanceof GameInstanceCreateDTO)) return false;
        GameInstanceCreateDTO gameInstance = (GameInstanceCreateDTO) obj;
        return getPlayer().equals(gameInstance.getPlayer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayer());
    }

    @Override
    public String toString() {
        return "GameInstanceCreateDTO{" +
                ", banishesRequired=" + banishesRequired +
                ", playerDTO_ID=" + ((player == null) ? "playerDTO is null" : player.getId()) +
                ", specterDTO_ID=" + ((specter == null) ? "specterDTO is null" : specter.getId()) +
                '}';
    }
}
