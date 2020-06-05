package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.service.exceptions.NotEnoughHousesException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@author David Hofman
 */
@Service
public interface GameInstanceService {

    /**
     * Searches the database for the GameInstance with given Id
     * @param id Id of GameInstance
     * @return GameInstance with given Id
     */
    GameInstance getGameInstanceById(Long id);

    /**
     * Searches the database for the GameInstance of the Player with given Id
     * @param id Id of the Player
     * @return GameInstance of the Player with given Id
     */
    GameInstance getGameInstanceByPlayerId(Long id);

    /**
     * @return all game instances from the database
     */
    List<GameInstance> getAllGameInstances();

    /**
     * Creates a GameInstance in the database
     * @param gameInstance GameInstance to be created
     * @throws NotEnoughHousesException if there are too few houses in the database
     * @throws org.springframework.dao.DataAccessException if database constraints are violated
     */
    void createGameInstance(GameInstance gameInstance);

    /**
     * Creates a GameInstance with randomly generated Specter
     * @param gameInstance GameInstance to be created
     * @throws org.springframework.dao.DataAccessException if database constraints are violated
     * @throws NotEnoughHousesException if there are too few houses in the database
     */
    void createGameInstanceWithRandomSpecter(GameInstance gameInstance);

    /**
     * Updates the GameInstance in the database
     * @param gameInstance updated GameInstance
     * @return updated GameInstance
     * @throws org.springframework.dao.DataAccessException if database constraints are violated
     */
    GameInstance updateGameInstance(GameInstance gameInstance);

    /**
     * Deletes the GameInstance from the database
     * @param gameInstance GameInstance to be deleted
     */
    void deleteGameInstance(GameInstance gameInstance);
}
