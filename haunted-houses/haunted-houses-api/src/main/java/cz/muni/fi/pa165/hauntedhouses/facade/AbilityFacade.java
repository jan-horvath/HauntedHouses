package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;

/**
 * @author Zoltan Fridrich
 */
public interface AbilityFacade {

    AbilityDTO findAbilityByName(String name);

    void createAbility(AbilityCreateDTO ability);

    void deleteAbility(Long id);
}
