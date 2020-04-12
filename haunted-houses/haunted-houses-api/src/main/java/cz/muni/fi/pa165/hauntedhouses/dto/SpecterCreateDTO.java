package cz.muni.fi.pa165.hauntedhouses.dto;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.util.Objects;

/**
 * @author Zoltan Fridrich
 */
public class SpecterCreateDTO {

    @NotNull
    private String name;

    @NotNull
    private LocalTime startOfHaunting;

    @NotNull
    private LocalTime endOfHaunting;

    @NotNull
    private String description;

    private HouseDTO house;

    @NotNull
    private GameInstanceDTO gameInstance;

    private List<AbilityDTO> abilities = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getStartOfHaunting() {
        return startOfHaunting;
    }

    public void setStartOfHaunting(LocalTime startOfHaunting) {
        this.startOfHaunting = startOfHaunting;
    }

    public LocalTime getEndOfHaunting() {
        return endOfHaunting;
    }

    public void setEndOfHaunting(LocalTime endOfHaunting) {
        this.endOfHaunting = endOfHaunting;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HouseDTO getHouse() {
        return house;
    }

    public void setHouse(HouseDTO house) {
        this.house = house;
    }

    public GameInstanceDTO getGameInstance() {
        return gameInstance;
    }

    public void setGameInstance(GameInstanceDTO gameInstance) {
        this.gameInstance = gameInstance;
    }

    public List<AbilityDTO> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<AbilityDTO> abilities) {
        this.abilities = abilities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecterCreateDTO)) return false;
        SpecterCreateDTO specter = (SpecterCreateDTO) o;
        return getGameInstance().equals(specter.getGameInstance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGameInstance());
    }
}
