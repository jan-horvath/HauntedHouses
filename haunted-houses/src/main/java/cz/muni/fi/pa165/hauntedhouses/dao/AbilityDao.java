package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.model.Ability;

import java.util.List;

/**
 * @author David Hofman
 */
public interface AbilityDao {


    /**
     * Inserts an ability into the database
     * @param A Ability to be inserted into the database
     */
    void createAbility(Ability A);

    /**
     * Searches the database for an ability with given id
     * @param id Id of the ability
     * @return Ability with given id
     */
    Ability getAbilityById(Long id);

    /**
     * Searches the database for an ability with given name
     * @param name Name of the ability
     * @return Ability with given name
     */
    Ability getAbilityByName(String name);

    /**
     * Searches the database for all the abilities
     * @return List containing all the abilities
     */
    List<Ability> getAllAbilities();

    /**
     * Updates the given ability
     * @param A Ability to be updated
     * @return the updated ability.
     */
    Ability updateAbility(Ability A);

    /**
     * Deletes the given ability from the database
     * @param A Ability to be deleted
     */
    void deleteAbility(Ability A);

}
