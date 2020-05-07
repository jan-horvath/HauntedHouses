package cz.muni.fi.pa165.hauntedhouses.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Date;

/**
 * @author Petr Vitovsky
 */

@Entity
public class House implements Serializable {

    @Id
    @GeneratedValue(generator = "pooled_generator")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String address;

    @Column
    private Date hauntedSince;

    @Column
    private String history;

    @Column(nullable = false)
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

    public Date getHauntedSince() {
        return this.hauntedSince;
    }

    public void setHauntedSince(Date hauntedSince) {
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
        if (!(o instanceof House)) return false;
        House house = (House) o;
        return getAddress().equals(house.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress());
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", hauntedSince=" + hauntedSince +
                ", history='" + history + '\'' +
                ", hint='" + hint + '\'' +
                '}';
    }
}
