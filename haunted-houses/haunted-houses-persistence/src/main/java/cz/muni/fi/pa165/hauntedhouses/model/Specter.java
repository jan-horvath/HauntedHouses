package cz.muni.fi.pa165.hauntedhouses.model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * @author Zoltan Fridrich
 */
@Entity
public class Specter {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private LocalTime startOfHaunting;

    @Column(nullable = false)
    private LocalTime endOfHaunting;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn
    private House house;

    @ManyToMany
    private List<Ability> abilities = new ArrayList<>();

    public Specter() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getStartOfHaunting() {
        return startOfHaunting;
    }

    public void setStartOfHaunting(LocalTime startOfHaunting) {
        this.startOfHaunting = startOfHaunting;
    }

    public LocalTime getEndOfHaunting() {
        return endOfHaunting;
    }

    public void setEndOfHaunting(LocalTime endOfHaunting) {
        this.endOfHaunting = endOfHaunting;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public void addAbility(Ability ability) {
        abilities.add(ability);
    }

    public void removeAbility(Ability ability) {
        abilities.remove(ability);
    }

    public void removeAllAbilities() {
        abilities.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specter)) return false;
        Specter specter = (Specter) o;
        return getName().equals(specter.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
