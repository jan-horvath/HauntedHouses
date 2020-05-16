package cz.muni.fi.pa165.hauntedhouses.service.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.AbilityFacade;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Jan Horvath
 */

@Transactional
@Service
public class AbilityFacadeImpl implements AbilityFacade {

    @Autowired
    private MappingService mappingService;

    @Autowired
    private AbilityService abilityService;

    @Override
    public AbilityDTO findAbilityById(Long id) {
        Ability abilityById = abilityService.getAbilityById(id);
        return (abilityById == null) ? null : mappingService.mapTo(abilityById, AbilityDTO.class);
    }

    @Override
    public AbilityDTO findAbilityByName(String name) {
        Ability abilityByName = abilityService.getAbilityByName(name);
        return (abilityByName == null) ? null : mappingService.mapTo(abilityByName, AbilityDTO.class);
    }

    @Override
    public List<AbilityDTO> findAllAbilities() {
        return mappingService.mapTo(abilityService.getAllAbilities(), AbilityDTO.class);
    }

    @Override
    public Long createAbility(AbilityCreateDTO ability) {
        Ability newAbility = mappingService.mapTo(ability, Ability.class);
        abilityService.createAbility(newAbility);
        return newAbility.getId();
    }

    @Override
    public void deleteAbility(Long id) {
        abilityService.deleteAbility(abilityService.getAbilityById(id));
    }

    @Override
    public void updateAbility(AbilityDTO ability) {
        Ability abilityToUpdate = mappingService.mapTo(ability, Ability.class);
        abilityService.updateAbility(abilityToUpdate);
    }
}
