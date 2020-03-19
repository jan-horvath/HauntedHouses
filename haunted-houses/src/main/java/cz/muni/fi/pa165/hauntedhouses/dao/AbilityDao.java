package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.model.Ability;

import java.util.List;

/**
 * @author David Hofman
 */
public interface AbilityDao {

    void createAbility(Ability A);

    Ability getAbilityById(Long id);

    Ability getAbilityByName(String name);

    List<Ability> getAllAbilities();

    void updateAbility(Ability A);

    void deleteAbility(Ability A);

}
