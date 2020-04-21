package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;

/**
 * @author Zoltan Fridrich
 */
public interface AbilityFacade {

    /**
     * Finds ability based on its ID
     * @param id
     * @return found ability if such exists, null otherwise
     */
    AbilityDTO findAbilityById(Long id);

    /**
     * Finds ability based on its name
     * @param name
     * @return found ability if such exists, null otherwise
     */
    AbilityDTO findAbilityByName(String name);

    /**
     * Creates new ability in database
     * @param ability
     * @return database ID of the created ability
     * @throws org.springframework.dao.DataAccessException if constraints are violated
     */
    Long createAbility(AbilityCreateDTO ability);

    /**
     * Deletes ability with given id if it exists
     * @param id
     */
    void deleteAbility(Long id);
}
