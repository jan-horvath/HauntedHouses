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

@Transactional
@Service
public class AbilityFacadeImpl implements AbilityFacade {

    @Autowired
    MappingService mappingService;

    @Autowired
    AbilityService abilityService;

    @Override
    public AbilityDTO findAbilityById(Long id) {
        return mappingService.mapTo(abilityService.getAbilityById(id), AbilityDTO.class);
    }

    @Override
    public AbilityDTO findAbilityByName(String name) {
        return mappingService.mapTo(abilityService.getAbilityByName(name), AbilityDTO.class);
    }

    @Override
    public Long createAbility(AbilityCreateDTO ability) {
        Ability newAbility = new Ability();
        newAbility.setName(ability.getName());
        newAbility.setDescription(ability.getDescription());

        abilityService.createAbility(newAbility);
        return newAbility.getId();
    }

    @Override
    public void deleteAbility(Long id) {
        abilityService.deleteAbility(abilityService.getAbilityById(id));
    }
}
