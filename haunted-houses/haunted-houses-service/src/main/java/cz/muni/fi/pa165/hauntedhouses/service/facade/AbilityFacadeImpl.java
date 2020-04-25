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
import java.util.Optional;

@Transactional
@Service
public class AbilityFacadeImpl implements AbilityFacade {

    @Autowired
    MappingService mappingService;

    @Autowired
    AbilityService abilityService;

    @Override
    public AbilityDTO findAbilityById(Long id) {
        Optional<Ability> abilityById = abilityService.getAbilityById(id);
        return abilityById.isEmpty() ? null : mappingService.mapTo(abilityById.get(), AbilityDTO.class);
    }

    @Override
    public AbilityDTO findAbilityByName(String name) {
        Optional<Ability> abilityByName = abilityService.getAbilityByName(name);
        return abilityByName.isEmpty() ? null : mappingService.mapTo(abilityByName.get(), AbilityDTO.class);
    }

    @Override
    public Long createAbility(AbilityCreateDTO ability) {
        Ability newAbility = mappingService.mapTo(ability, Ability.class);
        abilityService.createAbility(newAbility);
        return newAbility.getId();
    }

    @Override
    public void deleteAbility(Long id) {
        Optional<Ability> abilityById = abilityService.getAbilityById(id);
        abilityById.ifPresent(ability -> abilityService.deleteAbility(ability));

    }
}
