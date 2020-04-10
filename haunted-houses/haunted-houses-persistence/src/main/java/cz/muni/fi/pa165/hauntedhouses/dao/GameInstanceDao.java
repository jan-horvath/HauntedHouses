package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.Player;
import cz.muni.fi.pa165.hauntedhouses.model.Specter;
import org.springframework.orm.jpa.JpaSystemException;

import java.util.List;

/**
 * @author David Hofman
 */
public interface GameInstanceDao {

    /**
     * Inserts a GameInstance entity into the database
     * @param gameInstance GameInstance to be inserted into the database
     * @throws JpaSystemException if the database constraints are violated or the entity already exists
     */
    void createGameInstance(GameInstance gameInstance);

    /**
     * Searches the database for a GameInstance with given id
     * @param id Id of the GameInstance
     * @return GameInstance with given id, null if it doesn't exist
     */
    GameInstance getGameInstanceById(Long id);

    /**
     * Searches the database for a GameInstance of given Player
     * @param player the Player whose GameInstance is to be searched for
     * @return GameInstance of given Player, null if it doesn't exist
     */
    GameInstance getGameInstanceByPlayer(Player player);

    /**
     * Searches the database for a GameInstance with given Specter
     * @param specter the Specter whose GameInstance is to be searched for
     * @return GameInstance of given Specter, null if it doesn't exist
     */
    GameInstance getGameInstanceBySpecter(Specter specter);

    /**
     * Searches the database for all the GameInstances
     * @return List containing all the GameInstances
     */
    List<GameInstance> getAllGameInstances();

    /**
     * Updates the given GameInstance
     * @param gameInstance GameInstance to be updated
     * @return the updated GameInstance, null if such GameInstance doesn't exist
     * @throws JpaSystemException if the database constraints are violated
     */
    GameInstance updateGameInstance(GameInstance gameInstance);

    /**
     * Deletes the given gameInstance from the database if it exists
     * @param gameInstance GameInstance to be deleted
     */
    void deleteGameInstance(GameInstance gameInstance);
}
