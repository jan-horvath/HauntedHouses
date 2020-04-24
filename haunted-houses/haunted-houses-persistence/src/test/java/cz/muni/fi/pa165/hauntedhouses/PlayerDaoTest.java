package cz.muni.fi.pa165.hauntedhouses;

import cz.muni.fi.pa165.hauntedhouses.dao.PlayerDao;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

/**
 * @author Petr Vitovsky
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class PlayerDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private PlayerDao playerDao;

    private Player p1;
    private Player p2;
    private Player p3;

    @BeforeMethod
    public void setup() {
        p1 = new Player();
        p1.setName("testPlayer1");
        p1.setEmail("testEmail1");
        p1.setPasswordHash("testHash1");

        p2 = new Player();
        p2.setName("testPlayer2");
        p2.setEmail("testEmail2");
        p2.setPasswordHash("testHash2");

        p3 = new Player();
        p3.setName("testPlayer1");
        p3.setEmail("testEmail1");
        p3.setPasswordHash("testHash3");
    }

    @Test
    public void createAndGetByIdPlayerTest() {
        playerDao.createPlayer(p1);

        Player found = playerDao.getPlayerById(p1.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(p1.getName(), found.getName());
        Assert.assertEquals(p1.getEmail(), found.getEmail());
        Assert.assertEquals(p1.getPasswordHash(), found.getPasswordHash());
    }

    @Test
    public void getByIdNonexistingPlayerTest() {
        playerDao.createPlayer(p1);

        Long nonexistingId = p1.getId() + 1;
        Assert.assertNull(playerDao.getPlayerById(nonexistingId));
    }

    @Test
    public void createAndGetByNamePlayerTest() {
        playerDao.createPlayer(p1);

        Player found = playerDao.getPlayerByEmail(p1.getEmail());
        Assert.assertNotNull(found);
        Assert.assertEquals(p1.getName(), found.getName());
        Assert.assertEquals(p1.getEmail(), found.getEmail());
        Assert.assertEquals(p1.getPasswordHash(), found.getPasswordHash());
    }

    @Test public void getByEmailNonexistingPlayerTest() {
        Assert.assertNull(playerDao.getPlayerByEmail("nonexisting"));
    }

    @Test
    public void createAndGetAllPlayerTest() {
        Assert.assertEquals(playerDao.getAllPlayers().size(), 0);
        playerDao.createPlayer(p1);
        playerDao.createPlayer(p2);

        Assert.assertEquals(playerDao.getAllPlayers().size(),2);
        Assert.assertTrue(playerDao.getAllPlayers().contains(p1));
        Assert.assertTrue(playerDao.getAllPlayers().contains(p2));
    }

    @Test
    public void createUpdateAndGetByEmailPlayerTest() {
        playerDao.createPlayer(p1);

        p1.setName("updatedName");
        playerDao.updatePlayer(p1);

        Player found = playerDao.getPlayerByEmail(p1.getEmail());
        Assert.assertNotNull(found);
        Assert.assertEquals(p1.getName(), found.getName());
        Assert.assertEquals(p1.getEmail(), found.getEmail());
        Assert.assertEquals(p1.getPasswordHash(), found.getPasswordHash());
    }

    @Test
    public void createAndGetByGameInstance() {
        GameInstance gameInstance = new GameInstance();
        gameInstance.setPlayer(p1);
        p1.setGameInstance(gameInstance);
        playerDao.createPlayer(p1);

        Player copy = new Player();
        copy.setEmail("testEmail1");

        Assert.assertNotNull(gameInstance.getId());
        Assert.assertEquals(playerDao.getPlayerByGameInstance(gameInstance), copy);
    }

    @Test
    public void createAndDeletePlayerTest() {
        playerDao.createPlayer(p1);

        Player found = playerDao.getPlayerByEmail(p1.getEmail());
        Assert.assertNotNull(found);
        Assert.assertEquals(p1.getName(), found.getName());
        Assert.assertEquals(p1.getEmail(), found.getEmail());
        Assert.assertEquals(p1.getPasswordHash(), found.getPasswordHash());

        playerDao.deletePlayer(p1);
        found = playerDao.getPlayerByEmail(p1.getEmail());
        Assert.assertNull(found);
    }

    @Test
    public void createUpdateAndDeletePlayerTest() {
        playerDao.createPlayer(p1);

        Player found = playerDao.getPlayerByEmail(p1.getEmail());
        Assert.assertNotNull(found);
        Assert.assertEquals(p1.getName(), found.getName());
        Assert.assertEquals(p1.getEmail(), found.getEmail());
        Assert.assertEquals(p1.getPasswordHash(), found.getPasswordHash());

        p1.setName("changed");
        playerDao.updatePlayer(p1);

        playerDao.deletePlayer(p1);
        found = playerDao.getPlayerByEmail(p1.getEmail());
        Assert.assertNull(found);
    }

    @Test
    public void updateNonexistingPlayerTest() {
        Assert.assertNull(playerDao.updatePlayer(p1));
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createPlayerWithNullNameTest() {
        p1.setName(null);
        playerDao.createPlayer(p1);
        playerDao.getAllPlayers();
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createPlayerWithNullEmailTest() {
        p1.setEmail(null);
        playerDao.createPlayer(p1);
        playerDao.getAllPlayers();
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createDuplicatePlayerTest() {
        playerDao.createPlayer(p1);
        playerDao.createPlayer(p3);
        playerDao.getAllPlayers();
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void updateDuplicatePlayerTest() {
        playerDao.createPlayer(p1);
        playerDao.createPlayer(p2);
        p2.setEmail(p1.getEmail());
        playerDao.updatePlayer(p2);
        playerDao.getAllPlayers();
    }
}
