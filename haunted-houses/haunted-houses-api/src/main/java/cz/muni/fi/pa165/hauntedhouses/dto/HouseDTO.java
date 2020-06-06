package cz.muni.fi.pa165.hauntedhouses.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;
import java.util.Objects;

/**
 * @author Jan Horvath
 */

public class HouseDTO {

    private Long id;

    @NotNull(message = "Name cannot be null!")
    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @NotNull(message = "Address cannot be null!")
    @NotBlank(message = "Address cannot be empty!")
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    @PastOrPresent(message = "Given date is in future!")
    private Date hauntedSince;

    private String history;

    @NotNull(message = "Clue cannot be null!")
    @NotBlank(message = "Clue cannot be empty!")
    private String clue;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHistory() {
        return this.history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public Date getHauntedSince() {
        return this.hauntedSince;
    }

    public void setHauntedSince(Date hauntedSince) {
        this.hauntedSince = hauntedSince;
    }

    public String getClue() {
        return this.clue;
    }

    public void setClue(String clue) {
        this.clue = clue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseDTO)) return false;
        HouseDTO house = (HouseDTO) o;
        return getAddress().equals(house.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress());
    }

    @Override
    public String toString() {
        return "HouseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", hauntedSince=" + hauntedSince +
                ", history='" + history + '\'' +
                ", clue='" + clue + '\'' +
                '}';
    }
}
