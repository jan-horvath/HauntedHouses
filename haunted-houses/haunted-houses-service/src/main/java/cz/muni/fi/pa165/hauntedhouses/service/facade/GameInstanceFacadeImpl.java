package cz.muni.fi.pa165.hauntedhouses.service.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.GameInstanceCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.GameInstanceDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.GameInstanceFacade;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.service.GameInstanceService;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Petr Vitovsky
 */
@Service
@Transactional
public class GameInstanceFacadeImpl implements GameInstanceFacade {

    @Autowired
    GameInstanceService gameInstanceService;

    @Autowired
    MappingService mappingService;

    @Override
    public GameInstanceDTO findGameInstanceByPlayerId(Long playerId) {
        GameInstance gameInstance = gameInstanceService.getGameInstanceByPlayerId(playerId);
        return (gameInstance == null) ? null : mappingService.mapTo(gameInstance, GameInstanceDTO.class);
    }

    @Override
    public Long createGameInstance(GameInstanceCreateDTO gameInstance) {
        GameInstance gameInstanceEntity = mappingService.mapTo(gameInstance, GameInstance.class);
        gameInstanceService.createGameInstance(gameInstanceEntity);

        return gameInstanceEntity.getId();
    }

    @Override
    public void deleteGameInstance(Long id) {
        GameInstance gameInstance = gameInstanceService.getGameInstanceById(id);
        gameInstanceService.deleteGameInstance(gameInstance);
    }
}
