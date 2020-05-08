package cz.fi.muni.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dto.PlayerAuthenticationDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.PlayerFacade;
import cz.muni.fi.pa165.hauntedhouses.model.Player;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import cz.muni.fi.pa165.hauntedhouses.service.PlayerService;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.service.facade.PlayerFacadeImpl;
import cz.muni.fi.pa165.hauntedhouses.dto.PlayerDTO;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author David Hofman
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class PlayerFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private PlayerService playerService;

    @Mock
    private MappingService mappingService;

    @InjectMocks
    private PlayerFacade playerFacade = new PlayerFacadeImpl();

    private Player player1;
    private Player player2;

    private PlayerDTO playerDTO1;
    private PlayerDTO playerDTO2;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        player1 = new Player();
        player1.setId(1L);
        player1.setEmail("email1");

        player2 = new Player();
        player2.setId(2L);
        player2.setEmail("email2");

        playerDTO1 = new PlayerDTO();
        playerDTO1.setId(player1.getId());
        playerDTO1.setEmail(player1.getEmail());

        playerDTO2 = new PlayerDTO();
        playerDTO2.setId(player2.getId());
        playerDTO2.setEmail(player2.getEmail());

        reset(mappingService);
        when(mappingService.mapTo(playerDTO1, Player.class)).thenReturn(player1);
        when(mappingService.mapTo(player1,PlayerDTO.class)).thenReturn(playerDTO1);
        when(mappingService.mapTo(playerDTO2, Player.class)).thenReturn(player2);
        when(mappingService.mapTo(player2,PlayerDTO.class)).thenReturn(playerDTO2);

        reset(playerService);
        when(playerService.getPlayerById(player1.getId())).thenReturn(player1);
        when(playerService.getPlayerById(player2.getId())).thenReturn(player2);
        when(playerService.getPlayerById(3L)).thenReturn(null);
        when(playerService.getPlayerByEmail("email1")).thenReturn(player1);
        when(playerService.getPlayerByEmail("email2")).thenReturn(player2);
        when(playerService.getPlayerByEmail("nonexistent")).thenReturn(null);
    }

    @Test
    public void findPlayerByIdTest() {
        Assert.assertEquals(playerFacade.findPlayerById(player1.getId()),playerDTO1);
        Assert.assertEquals(playerFacade.findPlayerById(player2.getId()),playerDTO2);
        Assert.assertNull(playerFacade.findPlayerById(3L));

        verify(playerService).getPlayerById(player1.getId());
        verify(playerService).getPlayerById(player2.getId());
        verify(playerService).getPlayerById(3L);
    }

    @Test
    public void findPlayerByEmailTest() {
        Assert.assertEquals(playerFacade.findPlayerByEmail("email1"),playerDTO1);
        Assert.assertEquals(playerFacade.findPlayerByEmail("email2"),playerDTO2);
        Assert.assertNull(playerFacade.findPlayerByEmail("nonexistent"));

        verify(playerService).getPlayerByEmail("email1");
        verify(playerService).getPlayerByEmail("email2");
        verify(playerService).getPlayerByEmail("nonexistent");
    }

    @Test
    public void getAllPlayersTest() {
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<PlayerDTO> playerDTOs = new ArrayList<>();
        playerDTOs.add(playerDTO1);
        playerDTOs.add(playerDTO2);

        when(mappingService.mapTo(players,PlayerDTO.class)).thenReturn(playerDTOs);
        when(mappingService.mapTo(playerDTOs,Player.class)).thenReturn(players);
        when(playerService.getAllPlayers()).thenReturn(players);

        Assert.assertEquals(playerFacade.getAllPlayers(),playerDTOs);
        verify(playerService).getAllPlayers();
    }

    @Test
    public void registerPlayerTest() {
        Mockito.doAnswer(invocationOnMock -> {
            Player argument = invocationOnMock.getArgument(0);
            argument.setPasswordHash("hashedPassword");
            return null;
        }).when(playerService).registerPlayer(player1,"password");

        playerFacade.registerPlayer(playerDTO1, "password");
        Assert.assertEquals("hashedPassword", player1.getPasswordHash());
        verify(playerService).registerPlayer(player1,"password");
    }

    @Test
    public void authenticateTest() {
        PlayerAuthenticationDTO playerAuthenticationDTO = new PlayerAuthenticationDTO();
        playerAuthenticationDTO.setPlayerId(player1.getId());
        playerAuthenticationDTO.setPassword("correctPassword");

        when(mappingService.mapTo(playerAuthenticationDTO, Player.class)).thenReturn(player1);
        when(mappingService.mapTo(player1,PlayerAuthenticationDTO.class)).thenReturn(playerAuthenticationDTO);

        when(playerService.authenticate(player1,"correctPassword")).thenReturn(true);
        when(playerService.authenticate(player1,"incorrectPassword")).thenReturn(false);

        Assert.assertTrue(playerFacade.authenticate(playerAuthenticationDTO));
        playerAuthenticationDTO.setPassword("incorrectPassword");
        Assert.assertFalse(playerFacade.authenticate(playerAuthenticationDTO));
        verify(playerService).authenticate(player1,"correctPassword");
        verify(playerService).authenticate(player1,"incorrectPassword");

    }

    @Test
    public void isAdminTest() {
        when(playerService.isAdmin(player1)).thenReturn(true);
        when(playerService.isAdmin(player2)).thenReturn(false);

        Assert.assertTrue(playerFacade.isAdmin(playerDTO1));
        Assert.assertFalse(playerFacade.isAdmin(playerDTO2));

        verify(playerService).isAdmin(player1);
        verify(playerService).isAdmin(player2);
    }

}
