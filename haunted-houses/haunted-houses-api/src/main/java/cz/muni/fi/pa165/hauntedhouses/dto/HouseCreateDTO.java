package cz.muni.fi.pa165.hauntedhouses.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Jan Horvath
 */

public class HouseCreateDTO {

    @NotNull(message = "Name cannot be null!")
    @NotEmpty(message = "Name cannot be empty!")
    private String name;

    @NotNull(message = "Address cannot be null!")
    @NotEmpty(message = "Address cannot be empty!")
    private String address;

    private LocalDate hauntedSince;

    private String history;

    @NotNull(message = "Hint cannot be null!")
    @NotEmpty(message = "Hint cannot be empty!")
    private String hint;

    public HouseCreateDTO() {}

    public HouseCreateDTO(String name, String address, String hint) {
        this.name = name;
        this.address = address;
        this.hint = hint;
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

    public LocalDate getHauntedSince() {
        return this.hauntedSince;
    }

    public void setHauntedSince(LocalDate hauntedSince) {
        this.hauntedSince = hauntedSince;
    }

    public String getHint() {
        return this.hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseCreateDTO)) return false;
        HouseCreateDTO house = (HouseCreateDTO) o;
        return getAddress().equals(house.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress());
    }

    @Override
    public String toString() {
        return "HouseCreateDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", hauntedSince=" + hauntedSince +
                ", history='" + history + '\'' +
                ", hint='" + hint + '\'' +
                '}';
    }
}
