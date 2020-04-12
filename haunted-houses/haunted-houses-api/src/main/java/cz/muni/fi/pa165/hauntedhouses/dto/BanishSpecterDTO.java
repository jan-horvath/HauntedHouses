package cz.muni.fi.pa165.hauntedhouses.dto;

public class BanishSpecterDTO {
    private Long houseId;
    private Long gameInstanceId;

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Long getGameInstanceId() {
        return gameInstanceId;
    }

    public void setGameInstanceId(Long gameInstanceId) {
        this.gameInstanceId = gameInstanceId;
    }
}
