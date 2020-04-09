package cz.muni.fi.pa165.hauntedhouses.model;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Objects;

/**
 * @author David Hofman
 */
@Entity
public class Ability {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable=false,unique=true)
    private String name;

    @Column(nullable=false)
    private String description;

    public Ability() {
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Ability)) return false;
        if(this == obj) return true;
        Ability ability = (Ability) obj;
        return getName().equals(ability.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
