package cz.fi.muni.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dto.BanishSpecterDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.GameFacade;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import cz.muni.fi.pa165.hauntedhouses.model.Player;
import cz.muni.fi.pa165.hauntedhouses.service.GameInstanceService;
import cz.muni.fi.pa165.hauntedhouses.service.GameService;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.service.facade.GameFacadeImpl;
import org.hibernate.service.spi.ServiceException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class GameFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    HouseService houseService;

    @Mock
    GameInstanceService gameInstanceService;

    @Mock
    GameService gameService;

    GameFacade gameFacade;

    BanishSpecterDTO correctBanish;
    BanishSpecterDTO incorrectBanish;
    House correctHouse;
    House incorrectHouse;
    GameInstance gameInstance;
    Player player;

    long correctHouseId = 10;
    long incorrectHouseId = 9;
    long gameInstanceId = 5;

    @BeforeClass
    public void mockitoInit() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        gameFacade = new GameFacadeImpl(houseService, gameInstanceService, gameService);
    }

    @BeforeMethod
    public void setup() {
        correctBanish = new BanishSpecterDTO();
        correctBanish.setGameInstanceId(gameInstanceId);
        correctBanish.setHouseId(correctHouseId);

        incorrectBanish = new BanishSpecterDTO();
        incorrectBanish.setGameInstanceId(gameInstanceId);
        incorrectBanish.setHouseId(incorrectHouseId);

        correctHouse = new House();
        correctHouse.setName("correct house name");
        correctHouse.setAddress("correct house address");

        incorrectHouse = new House();
        incorrectHouse.setName("incorrect house name");
        incorrectHouse.setAddress("incorrect house address");

        gameInstance = new GameInstance();
        gameInstance.setId(gameInstanceId);

        player = new Player();
        player.setEmail("player email");

        gameInstance.setPlayer(player);

        setupMockedBehaviour();
    }

    private void setupMockedBehaviour() {
        when(houseService.getHouseById(correctHouseId)).thenReturn(correctHouse);
        when(houseService.getHouseById(incorrectHouseId)).thenReturn(incorrectHouse);
        when(gameInstanceService.getGameInstanceById(gameInstanceId)).thenReturn(gameInstance);
        when(gameService.checkAnswer(correctHouse, gameInstance)).thenReturn(true);
        when(gameService.checkAnswer(incorrectHouse, gameInstance)).thenReturn(false);
    }

    @Test
    public void correctBanishSpecterTest() {
        Assert.assertTrue(gameFacade.banishSpecter(correctBanish));
        verify(gameInstanceService, times(1)).updateGameInstance(gameInstance);
        reset(gameInstanceService);
    }

    @Test
    public void incorrectBanishSpecterTest() {
        Assert.assertFalse(gameFacade.banishSpecter(incorrectBanish));
        verify(gameInstanceService, times(1)).updateGameInstance(gameInstance);
        reset(gameInstanceService);
    }


}
