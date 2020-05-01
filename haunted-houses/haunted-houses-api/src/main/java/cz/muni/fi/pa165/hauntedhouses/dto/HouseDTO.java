package cz.muni.fi.pa165.hauntedhouses.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Jan Horvath
 */

public class HouseDTO {

    private Long id;

    private String name;

    private String address;

    private LocalDate hauntedSince;

    private String history;

    private String hint;

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
                ", hint='" + hint + '\'' +
                '}';
    }
}
