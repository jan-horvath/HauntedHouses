package cz.muni.fi.pa165.hauntedhouses.service.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.GameInstanceCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.GameInstanceDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.GameInstanceFacade;

public class GameInstanceFacadeImpl implements GameInstanceFacade {
    @Override
    public GameInstanceDTO findGameInstanceByPlayerId(Long playerId) {
        return null;
    }

    @Override
    public void createGameInstance(GameInstanceCreateDTO gameInstance) {

    }

    @Override
    public void deleteGameInstance(Long id) {

    }
}
