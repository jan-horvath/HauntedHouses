package cz.muni.fi.pa165.hauntedhouses.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

/**
 * @author Zoltan Fridrich
 */
@Entity
public class Specter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
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

    @OneToOne
    @JoinColumn(unique = true, nullable = false)
    private GameInstance gameInstance;

    @ManyToMany
    private Set<Ability> abilities = new HashSet<>();

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

    public GameInstance getGameInstance() {
        return gameInstance;
    }

    public void setGameInstance(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
    }

    public Set<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(Set<Ability> abilities) {
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
        return getGameInstance().equals(specter.getGameInstance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGameInstance());
    }

    @Override
    public String toString() {
        return "Specter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startOfHaunting=" + startOfHaunting +
                ", endOfHaunting=" + endOfHaunting +
                ", description='" + description + '\'' +
                '}';
    }
}
