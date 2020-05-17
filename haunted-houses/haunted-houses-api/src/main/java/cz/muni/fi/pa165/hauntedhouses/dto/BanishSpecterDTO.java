package cz.muni.fi.pa165.hauntedhouses.dto;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BanishSpecterDTO)) return false;
        BanishSpecterDTO that = (BanishSpecterDTO) o;
        return Objects.equals(getHouseId(), that.getHouseId()) &&
                Objects.equals(getGameInstanceId(), that.getGameInstanceId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHouseId(), getGameInstanceId());
    }

    @Override
    public String toString() {
        return "BanishSpecterDTO{" +
                "houseId=" + houseId +
                ", gameInstanceId=" + gameInstanceId +
                '}';
    }
}
