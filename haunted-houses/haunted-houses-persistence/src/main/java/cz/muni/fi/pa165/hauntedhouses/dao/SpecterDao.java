package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import cz.muni.fi.pa165.hauntedhouses.model.Specter;

import java.util.List;

import org.springframework.orm.jpa.JpaSystemException;

/**
 * @author Zoltan Fridrich
 */
public interface SpecterDao {

    /**
     * Creates new specter in the database.
     * @param specter New specter.
     * @throws JpaSystemException If the database constraints are violated or the entity already exists.
     */
    void createSpecter(Specter specter);

    /**
     * Updates given specter in the database if it exists.
     * @param specter Specter to update.
     * @return Specter if it was updated, null otherwise.
     * @throws JpaSystemException If the database constraints are violated.
     */
    Specter updateSpecter(Specter specter);

    /**
     * Deletes given specter from the database if it exists.
     * @param specter Specter to be deleted.
     */
    void deleteSpecter(Specter specter);

    /**
     * Searches database for a specter with given id.
     * @param id Id of a specter.
     * @return Specter with given id if it exists, null otherwise.
     */
    Specter getSpecterById(long id);

    /**
     * Searches database for a specter with given name.
     * @param name Name of a specter.
     * @return Specter with given name if it exists, null otherwise.
     */
    Specter getSpecterByName(String name);

    /**
     * Searches database for a specter associated with given game instance
     * @param gameInstance
     * @return Specter associated with given game instance, if it exists
     */
    Specter getSpecterByGameInstance(GameInstance gameInstance);

    /**
     * Searches database for all specters with given house.
     * @param house House in which specters haunt.
     * @return List of specters with given house.
     */
    List<Specter> getSpectersByHouse(House house);

    /**
     * Searches database for all specters with given ability.
     * @param ability Ability of specters.
     * @return List of specters with given ability.
     */
    List<Specter> getSpectersByAbility(Ability ability);

    /**
     * Returns list of all specters in the database.
     * @return List of all specters in the database.
     */
    List<Specter> getAllSpecters();
}
