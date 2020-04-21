package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.GameInstanceDao;
import cz.muni.fi.pa165.hauntedhouses.dao.PlayerDao;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author David Hofman
 */

@Service
public class GameInstanceServiceImpl implements GameInstanceService {

    @Autowired
    GameInstanceDao gameInstanceDao;

    @Autowired
    PlayerDao playerDao;

    @Autowired
    SpecterService specterService;

    @Override
    public GameInstance getGameInstanceById(Long id) {
        return gameInstanceDao.getGameInstanceById(id);
    }

    @Override
    public GameInstance getGameInstanceByPlayerId(Long id) {
        return playerDao.getPlayerById(id).getGameInstance();
    }

    @Override
    public void createGameInstance(GameInstance gameInstance) {
        gameInstanceDao.createGameInstance(gameInstance);
    }

    @Override
    public void createGameInstanceWithRandomSpecter(GameInstance gameInstance) {
        gameInstance.setSpecter(specterService.generateRandomSpecter());
        gameInstanceDao.createGameInstance(gameInstance);
    }

    @Override
    public GameInstance updateGameInstance(GameInstance gameInstance) {
        return gameInstanceDao.updateGameInstance(gameInstance);
    }

    @Override
    public void deleteGameInstance(GameInstance gameInstance) {
        gameInstanceDao.deleteGameInstance(gameInstance);
    }
}

