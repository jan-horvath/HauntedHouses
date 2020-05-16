package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;

import java.util.List;

/**
 * @author Zoltan Fridrich
 */
public interface AbilityFacade {

    /**
     * Finds ability based on its ID
     * @param id Ability ID
     * @return found ability if such exists, null otherwise
     */
    AbilityDTO findAbilityById(Long id);

    /**
     * Finds ability based on its name
     * @param name Ability name
     * @return found ability if such exists, null otherwise
     */
    AbilityDTO findAbilityByName(String name);

    /**
     * Returns all abilities in the database
     *
     * @return All abilities in the database
     */
    List<AbilityDTO> findAllAbilities();

    /**
     * Creates new ability in database
     * @param ability Ability
     * @return database ID of the created ability
     * @throws org.springframework.dao.DataAccessException if constraints are violated
     */
    Long createAbility(AbilityCreateDTO ability);

    /**
     * Deletes ability with given id if it exists
     * @param id Ability ID
     */
    void deleteAbility(Long id);

    /**
     * Updates ability in the database, if it exists
     * @param ability Ability
     * @throws org.springframework.dao.DataAccessException if constraints are violated
     */
    void updateAbility(AbilityDTO ability);
}
