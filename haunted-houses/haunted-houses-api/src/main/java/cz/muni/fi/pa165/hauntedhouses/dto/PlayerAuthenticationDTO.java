package cz.muni.fi.pa165.hauntedhouses.dto;

public class PlayerAuthenticationDTO {

    private Long playerId;
    private String password;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
