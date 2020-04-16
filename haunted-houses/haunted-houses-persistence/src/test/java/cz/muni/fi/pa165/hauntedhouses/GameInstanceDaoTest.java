package cz.muni.fi.pa165.hauntedhouses;

import cz.muni.fi.pa165.hauntedhouses.dao.*;
import cz.muni.fi.pa165.hauntedhouses.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.PersistenceException;
import java.time.LocalTime;
import java.util.List;

/**
 * @author Jan Horvath
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class GameInstanceDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private GameInstanceDao gameInstanceDao;

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private SpecterDao specterDao;

    private Player player1;
    private Player player2;

    private GameInstance gameInstance1;
    private GameInstance gameInstance2;

    private Specter specter1;
    private Specter specter2;

    @BeforeMethod
    public void setup() {
        player1 = new Player();
        player1.setName("Player1 name");
        player1.setEmail("Player1 email");
        playerDao.createPlayer(player1);

        player2 = new Player();
        player2.setName("Player2 name");
        player2.setEmail("Player2 email");
        playerDao.createPlayer(player2);

        gameInstance1 = new GameInstance();
        gameInstance1.setPlayer(player1);
        gameInstance2 = new GameInstance();
        gameInstance2.setPlayer(player2);

        specter1 = new Specter();
        specter1.setName("Specter1 name");
        specter1.setDescription("Specter1 description");
        specter1.setStartOfHaunting(LocalTime.of(10,0));
        specter1.setEndOfHaunting(LocalTime.of(11, 0));
        specter1.setGameInstance(gameInstance1);

        specter2 = new Specter();
        specter2.setName("Specter2 name");
        specter2.setDescription("Specter2 description");
        specter2.setStartOfHaunting(LocalTime.of(2,0));
        specter2.setEndOfHaunting(LocalTime.of(3, 0));
        specter2.setGameInstance(gameInstance2);
    }

    //-------------------------------------------- Create and get tests ------------------------------------------------

    @Test
    public void createGameInstanceAndGetAllTest() {
        Assert.assertEquals(gameInstanceDao.getAllGameInstances().size(), 0);

        gameInstanceDao.createGameInstance(gameInstance1);

        List<GameInstance> allGameInstances = gameInstanceDao.getAllGameInstances();
        Assert.assertEquals(allGameInstances.size(), 1);
        Assert.assertTrue(allGameInstances.contains(gameInstance1));
        Assert.assertFalse(allGameInstances.contains(gameInstance2));

        gameInstanceDao.createGameInstance(gameInstance2);

        allGameInstances = gameInstanceDao.getAllGameInstances();
        Assert.assertEquals(allGameInstances.size(), 2);
        Assert.assertTrue(allGameInstances.contains(gameInstance1));
        Assert.assertTrue(allGameInstances.contains(gameInstance2));
    }

    @Test
    public void getGameInstanceByIdTest() {
        Assert.assertEquals(gameInstanceDao.getAllGameInstances().size(), 0);
        Assert.assertNull(gameInstance1.getId());
        Assert.assertNull(gameInstance2.getId());

        gameInstanceDao.createGameInstance(gameInstance1);

        GameInstance foundGameInstance = gameInstanceDao.getGameInstanceById(gameInstance1.getId());
        Assert.assertEquals(gameInstance1, foundGameInstance);

        gameInstanceDao.createGameInstance(gameInstance2);

        foundGameInstance = gameInstanceDao.getGameInstanceById(gameInstance2.getId());
        Assert.assertEquals(gameInstance2, foundGameInstance);

    }

    @Test
    public void getGameInstanceByPlayerTest() {
        Assert.assertEquals(gameInstanceDao.getAllGameInstances().size(), 0);

        gameInstanceDao.createGameInstance(gameInstance1);

        Player foundPlayer1 = playerDao.getPlayerById(player1.getId());
        Player foundPlayer2 = playerDao.getPlayerById(player2.getId());
        GameInstance foundGameInstance = gameInstanceDao.getGameInstanceByPlayer(foundPlayer1);
        Assert.assertEquals(gameInstance1, foundGameInstance);
        Assert.assertNull(gameInstanceDao.getGameInstanceByPlayer(foundPlayer2));

        gameInstanceDao.createGameInstance(gameInstance2);

        foundPlayer2 = playerDao.getPlayerById(player2.getId());
        foundGameInstance = gameInstanceDao.getGameInstanceByPlayer(foundPlayer2);
        Assert.assertEquals(gameInstance2, foundGameInstance);

    }

    @Test
    public void getGameInstanceBySpecterTest() {
        Assert.assertEquals(gameInstanceDao.getAllGameInstances().size(), 0);

        gameInstance1.setSpecter(specter1);
        gameInstance2.setSpecter(specter2);
        gameInstanceDao.createGameInstance(gameInstance1);
        gameInstanceDao.createGameInstance(gameInstance2);

        Specter foundSpecter1 = specterDao.getSpecterById(specter1.getId());
        GameInstance foundGameInstance = gameInstanceDao.getGameInstanceBySpecter(foundSpecter1);
        Assert.assertEquals(foundGameInstance, gameInstance1);

        Specter foundSpecter2 = specterDao.getSpecterById(specter2.getId());
        foundGameInstance = gameInstanceDao.getGameInstanceBySpecter(foundSpecter2);
        Assert.assertEquals(foundGameInstance, gameInstance2);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createTheSameGameInstanceTwiceTest() {
        gameInstance1.setPlayer(player1);
        gameInstance2.setPlayer(player1);

        gameInstanceDao.createGameInstance(gameInstance1);
        gameInstanceDao.createGameInstance(gameInstance2);
        gameInstanceDao.getAllGameInstances();
    }

    @Test
    public void getNonExistingGameInstanceTest() {
        gameInstanceDao.createGameInstance(gameInstance1);

        Assert.assertNull(gameInstanceDao.getGameInstanceById(gameInstance1.getId() + 1));
        Assert.assertNull(gameInstanceDao.getGameInstanceByPlayer(player2));
    }

    //------------------------------------------------- Update tests ---------------------------------------------------

    @Test
    public void updateGameInstanceTest() {
        gameInstance1.setSpecter(specter1);
        gameInstanceDao.createGameInstance(gameInstance1);

        GameInstance gameInstanceToUpdate = gameInstanceDao.getGameInstanceById(gameInstance1.getId());
        gameInstanceToUpdate.setBanishesRequired(10);
        gameInstanceToUpdate.setBanishesAttempted(5);
        gameInstanceDao.updateGameInstance(gameInstanceToUpdate);

        GameInstance updatedGameInstance = gameInstanceDao.getGameInstanceById(gameInstance1.getId());
        Assert.assertEquals(updatedGameInstance.getBanishesAttempted(), 5);
        Assert.assertEquals(updatedGameInstance.getBanishesRequired(), 10);

        gameInstanceToUpdate = gameInstanceDao.getGameInstanceById(gameInstance1.getId());
        gameInstanceToUpdate.setSpecter(specter2);
        specter2.setGameInstance(gameInstanceToUpdate);
        specterDao.createSpecter(specter2);
        //gameInstanceDao.updateGameInstance(gameInstanceToUpdate);

        updatedGameInstance = gameInstanceDao.getGameInstanceById(gameInstance1.getId());
        Assert.assertEquals(updatedGameInstance.getSpecter(), specter2);
    }

    @Test
    public void updateNonexistingAndDeletedGameInstancesTest() {
        gameInstanceDao.createGameInstance(gameInstance1);
        gameInstanceDao.deleteGameInstance(gameInstance1);
        gameInstance1.setBanishesRequired(100);

        Assert.assertNull(gameInstanceDao.updateGameInstance(gameInstance2));
        Assert.assertNotNull(gameInstance1.getId());
        Assert.assertNull(gameInstanceDao.updateGameInstance(gameInstance1));
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void updateGameInstanceToSharePlayerWithOtherInstanceTest() {
        gameInstanceDao.createGameInstance(gameInstance1);
        gameInstanceDao.createGameInstance(gameInstance2);

        gameInstance2.setPlayer(player1);
        gameInstanceDao.updateGameInstance(gameInstance2);
        gameInstanceDao.getAllGameInstances();
    }

    //------------------------------------------------- Delete tests ---------------------------------------------------

    @Test
    public void deleteGameInstanceTest() {
        Assert.assertEquals(playerDao.getAllPlayers().size(), 2);

        gameInstance1.setSpecter(specter1);
        gameInstanceDao.createGameInstance(gameInstance1);
        gameInstance2.setSpecter(specter2);
        gameInstanceDao.createGameInstance(gameInstance2);

        Assert.assertEquals(gameInstanceDao.getAllGameInstances().size(), 2);
        Assert.assertEquals(specterDao.getAllSpecters().size(), 2);
        Assert.assertEquals(playerDao.getAllPlayers().size(), 2);

        gameInstanceDao.deleteGameInstance(gameInstance1);

        List<GameInstance> allGameInstances = gameInstanceDao.getAllGameInstances();
        Assert.assertEquals(allGameInstances.size(), 1);
        Assert.assertEquals(allGameInstances.get(0), gameInstance2);
        Assert.assertEquals(allGameInstances.get(0).getSpecter(), specter2);

        List<Specter> allSpecters = specterDao.getAllSpecters();
        Assert.assertEquals(allSpecters.size(), 1);
        Assert.assertEquals(allSpecters.get(0), specter2);
        Assert.assertEquals(allSpecters.get(0).getGameInstance(), gameInstance2);

        Assert.assertEquals(playerDao.getAllPlayers().size(), 2);

        gameInstanceDao.deleteGameInstance(gameInstance2);

        Assert.assertEquals(gameInstanceDao.getAllGameInstances().size(), 0);
        Assert.assertEquals(specterDao.getAllSpecters().size(), 0);
        Assert.assertEquals(playerDao.getAllPlayers().size(), 2);
    }

    @Test
    public void deleteNonexistingAndDeletedGameInstancesTest() {
        gameInstanceDao.createGameInstance(gameInstance1);

        gameInstanceDao.deleteGameInstance(gameInstance1);
        gameInstanceDao.getAllGameInstances();
        gameInstanceDao.deleteGameInstance(gameInstance1);

        gameInstanceDao.deleteGameInstance(gameInstance2);
    }

}
