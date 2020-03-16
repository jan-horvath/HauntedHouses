package cz.muni.fi.pa165.hauntedhouses.model;


import javax.persistence.*;
import java.util.Objects;

/**
 * @author Petr Vitovsky
 */

@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String address;

    @Column
    private java.time.LocalDate hauntedSince;

    @Column
    private String history;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
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

    public java.time.LocalDate getHauntedSince() {
        return this.hauntedSince;
    }

    public void setHauntedSince(java.time.LocalDate hauntedSince) {
        this.hauntedSince = hauntedSince;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return address.equals(house.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
