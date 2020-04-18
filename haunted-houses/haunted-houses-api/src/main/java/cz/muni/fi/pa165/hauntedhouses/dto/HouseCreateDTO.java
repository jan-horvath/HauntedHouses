package cz.muni.fi.pa165.hauntedhouses.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class HouseCreateDTO {

    @NotNull
    private String name;

    @NotNull
    private String address;

    private LocalDate hauntedSince;

    private String history;

    @NotNull
    private String hint;

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
