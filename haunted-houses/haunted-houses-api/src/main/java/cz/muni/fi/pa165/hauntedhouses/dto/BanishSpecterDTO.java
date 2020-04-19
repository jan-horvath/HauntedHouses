package cz.muni.fi.pa165.hauntedhouses.dto;

/**
 * @author Jan Horvath
 */

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

    @Override
    public String toString() {
        return "BanishSpecterDTO{" +
                "houseId=" + houseId +
                ", gameInstanceId=" + gameInstanceId +
                '}';
    }
}
