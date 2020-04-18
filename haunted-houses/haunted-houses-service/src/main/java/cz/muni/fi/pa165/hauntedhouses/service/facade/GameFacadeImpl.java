package cz.muni.fi.pa165.hauntedhouses.service.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.BanishSpecterDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.GameFacade;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import cz.muni.fi.pa165.hauntedhouses.service.GameInstanceService;
import cz.muni.fi.pa165.hauntedhouses.service.GameService;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GameFacadeImpl implements GameFacade {

    @Autowired
    HouseService houseService;

    @Autowired
    GameInstanceService gameInstanceService;

    @Autowired
    GameService gameService;

    @Override
    public boolean banishSpecter(BanishSpecterDTO banishSpecter) {
        House guessedHouse = houseService.getHouseById(banishSpecter.getHouseId());
        GameInstance gameInstance = gameInstanceService.getGameInstanceById(banishSpecter.getGameInstanceId());

        boolean answer = gameService.checkAnswer(guessedHouse, gameInstance);
        gameInstanceService.updateGameInstance(gameInstance);

        return answer;
    }
}
