package cz.fi.muni.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dto.GameInstanceCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.GameInstanceDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.PlayerDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.GameInstanceFacade;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.Player;
import cz.muni.fi.pa165.hauntedhouses.service.GameInstanceService;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.service.facade.GameInstanceFacadeImpl;
import org.hibernate.service.spi.ServiceException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class GameInstanceFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    GameInstanceService gameInstanceService;

    @Autowired
    private MappingService mappingService;

    private GameInstanceFacade gameInstanceFacade;

    private long playerId = 7;
    private long gameInstanceId = 15;
    private Player player;
    private PlayerDTO playerDTO;
    private GameInstance gameInstance;
    private GameInstanceDTO gameInstanceDTO;

    private long newGameInstanceId = 22;
    private GameInstanceCreateDTO gameInstanceCreateDTO;

    @BeforeClass
    public void mockitoInit() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        gameInstanceFacade = new GameInstanceFacadeImpl(gameInstanceService, mappingService);
    }

    @BeforeMethod
    public void setup() {
        player = new Player();
        player.setEmail("email");
        player.setId(playerId);
        gameInstance = new GameInstance();
        gameInstance.setId(gameInstanceId);
        gameInstance.setPlayer(player);
        player.setGameInstance(gameInstance);

        playerDTO = new PlayerDTO();
        playerDTO.setEmail("email");
        playerDTO.setId(playerId);
        gameInstanceDTO = new GameInstanceDTO();
        gameInstanceDTO.setPlayer(playerDTO);
        playerDTO.setGameInstance(gameInstanceDTO);

        gameInstanceCreateDTO = new GameInstanceCreateDTO();
        gameInstanceCreateDTO.setPlayer(playerDTO);

        setupMockedBehaviour();
    }

    private void setupMockedBehaviour() {
        when(gameInstanceService.getGameInstanceByPlayerId(playerId)).thenReturn(gameInstance);
        when(gameInstanceService.getGameInstanceById(gameInstanceId)).thenReturn(gameInstance);
        Mockito.doAnswer(invocationOnMock -> {
            GameInstance argument = invocationOnMock.getArgument(0);
            argument.setId(newGameInstanceId);
            return null;
        }).when(gameInstanceService).createGameInstance(gameInstance);
    }

    @Test
    public void findGameInstanceByPlayerIdTest() {
        Assert.assertEquals(gameInstanceFacade.findGameInstanceByPlayerId(playerId), gameInstanceDTO);
    }

    @Test
    public void findGameInstanceByPlayerIdFailTest() {
        Assert.assertNull(gameInstanceFacade.findGameInstanceByPlayerId(123L));
    }

    @Test
    public void createGameInstanceTest() {
        Assert.assertEquals(gameInstanceFacade.createGameInstance(gameInstanceCreateDTO), Long.valueOf(newGameInstanceId));
        verify(gameInstanceService, times(1)).createGameInstance(gameInstance);
    }

    @Test
    public void deleteGameInstanceTest() {
        gameInstanceFacade.deleteGameInstance(gameInstanceId);
        verify(gameInstanceService, times(1)).deleteGameInstance(gameInstance);
    }
}
