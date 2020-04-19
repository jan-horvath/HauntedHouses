package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.GameInstanceCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.GameInstanceDTO;

/**
 * @author Zoltan Fridrich
 */
public interface GameInstanceFacade {
    /**
     * Finds the GameInstance of given Player
     * @param playerId ID of the Player
     * @return GameInstanceDTO of given Player if he exists and has a GameInstance, null otherwise
     */
    GameInstanceDTO findGameInstanceByPlayerId(Long playerId);

    /**
     * Creates new GameInstance
     * @param gameInstance DTO containing all information that is required for creating new GameInstance
     * @return ID of the GameInstance
     */
    Long createGameInstance(GameInstanceCreateDTO gameInstance);

    /**
     * Deletes the GameInstance from the database
     * @param id ID of the GameInstance
     */
    void deleteGameInstance(Long id);
}
