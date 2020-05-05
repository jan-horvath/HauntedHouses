package cz.muni.fi.pa165.hauntedhouses.dto;

import cz.muni.fi.pa165.hauntedhouses.facade.PlayerFacade;

import java.util.Objects;

/**
 * @author Jan Horvath
 */

public class PlayerDTO {

    private Long id;

    private String name;

    private String email;

    private String passwordHash;

    private boolean admin;

    private GameInstanceDTO gameInstance;

    public PlayerDTO() {}

    public PlayerDTO(String name, String email, boolean admin) {
        this.name = name;
        this.email = email;
        this.admin = admin;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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
        return Objects.equals(email, playerDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", admin=" + admin +
                ", gameInstance=" + gameInstance +
                '}';
    }
}
