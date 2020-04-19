package cz.muni.fi.pa165.hauntedhouses.dto;

import java.util.Objects;

/**
 * @author Jan Horvath
 */

public class PlayerDTO {

    private Long id;

    private String name;

    private String email;

    private GameInstanceDTO gameInstance;

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

    public GameInstanceDTO getGameInstance() {
        return gameInstance;
    }

    public void setGameInstance(GameInstanceDTO gameInstance) {
        this.gameInstance = gameInstance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDTO playerDTO = (PlayerDTO) o;
        return Objects.equals(name, playerDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gameInstanceDTO_ID=" + ((gameInstance == null) ? "gameInstanceDTO is null" : gameInstance.getId()) +
                '}';
    }
}
