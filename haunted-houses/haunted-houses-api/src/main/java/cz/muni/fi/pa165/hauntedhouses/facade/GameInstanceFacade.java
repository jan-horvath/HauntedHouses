package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.GameInstanceCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.GameInstanceDTO;

/**
 * @author Zoltan Fridrich
 */
public interface GameInstanceFacade {

    GameInstanceDTO findGameInstanceByPlayerId(Long playerId);

    Long createGameInstance(GameInstanceCreateDTO gameInstance);

    void deleteGameInstance(Long id);
}
