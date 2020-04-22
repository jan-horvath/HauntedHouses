package cz.fi.muni.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.PlayerDao;
import cz.muni.fi.pa165.hauntedhouses.model.Player;
import cz.muni.fi.pa165.hauntedhouses.service.PlayerService;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class PlayerServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    PlayerDao playerDao;

    @Autowired
    @InjectMocks
    PlayerService playerService;

    private Player player1;
    private Player player2;
    private List<Player> allPlayers;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        player1 = new Player();
        player1.setName("p1");
        player1.setEmail("email1");

        player2 = new Player();
        player2.setName("p2");
        player2.setEmail("email2");

        allPlayers = new ArrayList<Player>();
        allPlayers.add(player1);
        allPlayers.add(player2);
    }

    @Test
    public void getPlayerByIdTest() {
        when(playerDao.getPlayerById(1L)).thenReturn(player1);
        when(playerDao.getPlayerById(2L)).thenReturn(player2);

        Player testPlayer1 = playerService.getPlayerById(1L);
        Player testPlayer2 = playerService.getPlayerById(2L);
        Player testPlayer3 = playerService.getPlayerById(3L);

        Assert.assertNull(testPlayer3);

        Assert.assertEquals(testPlayer1.getName(), player1.getName());
        Assert.assertEquals(testPlayer1.getEmail(), player1.getEmail());

        Assert.assertEquals(testPlayer2.getName(), player2.getName());
        Assert.assertEquals(testPlayer2.getEmail(), player2.getEmail());
    }

    @Test
    public void getPlayerByEmailTest() {
        when(playerDao.getPlayerByEmail(player1.getEmail())).thenReturn(player1);
        when(playerDao.getPlayerByEmail(player2.getEmail())).thenReturn(player2);

        Player testPlayer1 = playerService.getPlayerByEmail(player1.getEmail());
        Player testPlayer2 = playerService.getPlayerByEmail(player2.getEmail());
        Player testPlayer3 = playerService.getPlayerByEmail("nonexisting email");

        Assert.assertNull(testPlayer3);

        Assert.assertEquals(testPlayer1.getName(), player1.getName());
        Assert.assertEquals(testPlayer1.getEmail(), player1.getEmail());

        Assert.assertEquals(testPlayer2.getName(), player2.getName());
        Assert.assertEquals(testPlayer2.getEmail(), player2.getEmail());
    }

    @Test
    public void getAllPlayersTest() {
        when(playerDao.getAllPlayers()).thenReturn(allPlayers);

        List<Player> testAllPlayers = playerService.getAllPlayers();

        Assert.assertTrue(testAllPlayers.contains(player1));
        Assert.assertTrue(testAllPlayers.contains(player2));
    }

    @Test
    public void registerPlayerTest() {
        reset(playerDao);
        playerService.registerPlayer(player1, "password");

        Assert.assertNotEquals("password", player1.getPasswordHash());
        verify(playerDao, times(1)).createPlayer(player1);
    }

    @Test
    public void authenticatePlayerTest() {
        playerService.registerPlayer(player1, "password");

        boolean rightPassword = playerService.authenticate(player1, "password");
        boolean wrongPassword1 = playerService.authenticate(player1, "wrong");
        boolean wrongPassword2 = playerService.authenticate(player1, "passwort");
        boolean wrongPassword3 = playerService.authenticate(player1, "");

        Assert.assertTrue(rightPassword);
        Assert.assertFalse(wrongPassword1);
        Assert.assertFalse(wrongPassword2);
        Assert.assertFalse(wrongPassword3);
    }

    @Test
    public void isAdminTest() {
        player1.setAdmin(true);
        player1.setId(1L);
        player2.setAdmin(false);
        player2.setId(2L);

        when(playerDao.getPlayerById(1L)).thenReturn(player1);
        when(playerDao.getPlayerById(2L)).thenReturn(player2);

        Assert.assertTrue(playerService.isAdmin(player1));
        Assert.assertFalse(playerService.isAdmin(player2));
    }
}
