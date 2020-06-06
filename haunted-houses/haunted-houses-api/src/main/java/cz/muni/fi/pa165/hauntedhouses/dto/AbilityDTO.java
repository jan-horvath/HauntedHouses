package cz.muni.fi.pa165.hauntedhouses.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Zoltan Fridrich
 */
public class AbilityDTO {

    private Long id;

    @NotNull(message = "Name cannot be null!")
    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @NotNull(message = "Description cannot be null!")
    @NotBlank(message = "Description cannot be empty!")
    private String description;

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
        if(this == obj) return true;
        if(!(obj instanceof AbilityDTO)) return false;
        AbilityDTO ability = (AbilityDTO) obj;
        return getName().equals(ability.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "AbilityDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
