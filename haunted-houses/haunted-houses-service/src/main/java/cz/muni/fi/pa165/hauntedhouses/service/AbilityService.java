package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Petr Vitovsky
 */
@Service
public interface AbilityService {
    Ability getAbilityById(Long id);
    Ability getAbilityByName(String name);
    List<Ability> getAllAbilities();
    void createAbility(Ability ability);
    void deleteAbility(Ability ability);
}
