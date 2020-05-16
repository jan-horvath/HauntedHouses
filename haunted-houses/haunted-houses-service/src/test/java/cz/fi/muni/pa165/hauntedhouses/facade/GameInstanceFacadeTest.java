package cz.fi.muni.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.GameInstanceCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.GameInstanceDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.PlayerDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.GameInstanceFacade;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.Player;
import cz.muni.fi.pa165.hauntedhouses.service.GameInstanceService;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

/**
 * @author Jan Horvath
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class GameInstanceFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private GameInstanceService gameInstanceService;

    @Mock
    private MappingService mappingService;

    private GameInstanceFacade gameInstanceFacade;

    private long playerId = 7;
    private long gameInstanceId = 15;

    private Player player;
    private PlayerDTO mappedPlayer;

    private PlayerDTO playerDTO;
    private Player mappedPlayerDTO;

    private GameInstance gameInstance;
    private GameInstanceDTO mappedGameInstance;

    private GameInstanceDTO gameInstanceDTO;

    private long newGameInstanceId = 22;
    private GameInstanceCreateDTO gameInstanceCreateDTO;
    private GameInstance mappedGameInstanceCreateDTO;

    @Autowired
    public GameInstanceFacadeTest(GameInstanceFacade gameInstanceFacade) {
        this.gameInstanceFacade = gameInstanceFacade;
    }

    @BeforeClass
    public void mockitoInit() throws ServiceException {
        MockitoAnnotations.initMocks(this);
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

        mappedPlayer = new PlayerDTO();
        mappedPlayer.setEmail("email");
        mappedPlayer.setId(playerId);

        mappedGameInstance = new GameInstanceDTO();
        mappedGameInstance.setId(gameInstanceId);
        mappedGameInstance.setPlayer(mappedPlayer);
        mappedPlayer.setGameInstance(mappedGameInstance);

        playerDTO = new PlayerDTO();
        playerDTO.setEmail("email");
        playerDTO.setId(playerId);
        gameInstanceDTO = new GameInstanceDTO();
        gameInstanceDTO.setPlayer(playerDTO);
        playerDTO.setGameInstance(gameInstanceDTO);

        mappedPlayerDTO = new Player();
        mappedPlayerDTO.setEmail("email");
        mappedPlayerDTO.setId(playerId);

        gameInstanceCreateDTO = new GameInstanceCreateDTO();
        gameInstanceCreateDTO.setPlayer(playerDTO);

        mappedGameInstanceCreateDTO = new GameInstance();
        mappedGameInstanceCreateDTO.setPlayer(mappedPlayerDTO);

        setupMockedBehaviour();
    }

    private void setupMockedBehaviour() {
        when(gameInstanceService.getGameInstanceByPlayerId(playerId)).thenReturn(gameInstance);
        when(gameInstanceService.getGameInstanceById(gameInstanceId)).thenReturn(gameInstance);
        Mockito.doAnswer(invocationOnMock -> {
            GameInstance argument = invocationOnMock.getArgument(0);
            argument.setId(newGameInstanceId);
            return null;
        }).when(gameInstanceService).createGameInstanceWithRandomSpecter(gameInstance);

        when(mappingService.mapTo(gameInstance, GameInstanceDTO.class)).thenReturn(mappedGameInstance);
        when(mappingService.mapTo(gameInstanceCreateDTO, GameInstance.class)).thenReturn(mappedGameInstanceCreateDTO);
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
        verify(gameInstanceService, times(1)).createGameInstanceWithRandomSpecter(gameInstance);
    }

    @Test
    public void deleteGameInstanceTest() {
        gameInstanceFacade.deleteGameInstance(gameInstanceId);
        verify(gameInstanceService, times(1)).deleteGameInstance(gameInstance);
    }
}
