package cz.fi.muni.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.GameInstanceDao;
import cz.muni.fi.pa165.hauntedhouses.dao.PlayerDao;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.Player;
import cz.muni.fi.pa165.hauntedhouses.model.Specter;
import cz.muni.fi.pa165.hauntedhouses.service.GameInstanceService;
import cz.muni.fi.pa165.hauntedhouses.service.GameInstanceServiceImpl;
import cz.muni.fi.pa165.hauntedhouses.service.SpecterService;

import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.testng.Assert.assertEquals;

public class GameInstanceServiceTest {

    @InjectMocks
    GameInstanceService gameInstanceService = new GameInstanceServiceImpl();

    @Mock
    private GameInstanceDao gameInstanceDao;

    @Mock
    private PlayerDao playerDao;

    @Mock
    private SpecterService specterService;

    private GameInstance gameInstance;
    private GameInstance gameInstance2;
    private Player player;
    private Specter randomSpecter;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        reset(gameInstanceDao);
        reset(playerDao);
        reset(specterService);

        gameInstance = new GameInstance();
        gameInstance.setId(1L);

        gameInstance2 = new GameInstance();
        gameInstance.setId(2L);

        player = new Player();
        player.setId(3L);
        player.setGameInstance(gameInstance);

        randomSpecter = new Specter();
        randomSpecter.setId(4L);

        when(gameInstanceDao.getGameInstanceById(gameInstance.getId())).thenReturn(gameInstance);
        when(gameInstanceDao.updateGameInstance(gameInstance2)).thenReturn(gameInstance);
        when(playerDao.getPlayerById(player.getId())).thenReturn(player);
        when(specterService.generateRandomSpecter()).thenReturn(randomSpecter);
    }

    @Test
    public void getGameInstanceByIdTest() {
        assertEquals(gameInstanceService.getGameInstanceById(gameInstance.getId()), gameInstance);
        verify(gameInstanceDao).getGameInstanceById(gameInstance.getId());
    }

    @Test
    public void getGameInstanceByPlayerIdTest() {
        assertEquals(gameInstanceService.getGameInstanceByPlayerId(player.getId()), gameInstance);
        verify(playerDao).getPlayerById(player.getId());
    }

    @Test
    public void createGameInstanceTest() {
        gameInstanceService.createGameInstance(gameInstance);
        verify(gameInstanceDao).createGameInstance(gameInstance);
    }

    @Test
    public void createGameInstanceWithRandomSpecterTest() {
        gameInstanceService.createGameInstanceWithRandomSpecter(gameInstance);
        assertEquals(gameInstance.getSpecter(), randomSpecter);
        verify(gameInstanceDao).createGameInstance(gameInstance);
        verify(specterService).generateRandomSpecter();
    }

    @Test
    public void updateGameInstanceTest() {
        assertEquals(gameInstanceService.updateGameInstance(gameInstance2), gameInstance);
        verify(gameInstanceDao).updateGameInstance(gameInstance2);
    }

    @Test
    public void deleteGameInstanceTest() {
        gameInstanceService.deleteGameInstance(gameInstance);
        verify(gameInstanceDao).deleteGameInstance(gameInstance);
    }
}
