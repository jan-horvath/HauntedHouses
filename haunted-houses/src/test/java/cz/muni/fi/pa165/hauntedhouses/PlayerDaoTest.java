package cz.muni.fi.pa165.hauntedhouses;

import cz.muni.fi.pa165.hauntedhouses.dao.PlayerDao;
import cz.muni.fi.pa165.hauntedhouses.model.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Petr Vitovsky
 */
@Transactional
@SpringBootTest
@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class PlayerDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private EntityManager em;

    private Player p1;
    private Player p2;
    private Player p3;

    @BeforeMethod
    public void setup() {
        p1 = new Player();
        p1.setName("testPlayer1");
        p1.setEmail("testEmail1");

        p2 = new Player();
        p2.setName("testPlayer2");
        p2.setEmail("testEmail2");

        p3 = new Player();
        p3.setName("testPlayer1");
        p3.setEmail("testEmail1");
    }

    @Test
    public void createAndGetByIdPlayerTest() {
        playerDao.createPlayer(p1);

        Player found = playerDao.getPlayerById(p1.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(p1.getName(), found.getName());
        Assert.assertEquals(p1.getEmail(), found.getEmail());
    }

    @Test
    public void createAndGetByNamePlayerTest() {
        playerDao.createPlayer(p1);

        Player found = playerDao.getPlayerByName(p1.getName());
        Assert.assertNotNull(found);
        Assert.assertEquals(p1.getName(), found.getName());
        Assert.assertEquals(p1.getEmail(), found.getEmail());
        Assert.assertNull(playerDao.getPlayerByName("nonexisting"));
    }

    @Test
    public void createAndGetAllPlayerTest() {
        playerDao.createPlayer(p1);
        playerDao.createPlayer(p2);

        Assert.assertEquals(playerDao.getAllPlayers().size(),2);
        Assert.assertEquals(p1.getName(), playerDao.getPlayerById(p1.getId()).getName());
        Assert.assertEquals(p1.getEmail(), playerDao.getPlayerById(p1.getId()).getEmail());
        Assert.assertEquals(p2.getName(), playerDao.getPlayerById(p2.getId()).getName());
        Assert.assertEquals(p2.getEmail(), playerDao.getPlayerById(p2.getId()).getEmail());
    }

    @Test
    public void createUpdateAndGetByNamePlayerTest() {
        playerDao.createPlayer(p1);

        p1.setName("updatedName");
        playerDao.updatePlayer(p1);

        Player found = playerDao.getPlayerByName(p1.getName());
        Assert.assertNotNull(found);
        Assert.assertEquals(p1.getName(), found.getName());
        Assert.assertEquals(p1.getEmail(), found.getEmail());
    }

    @Test
    public void createAndDeletePlayerTest() {
        playerDao.createPlayer(p1);

        Player found = playerDao.getPlayerByName(p1.getName());
        Assert.assertNotNull(found);
        Assert.assertEquals(p1.getName(), found.getName());
        Assert.assertEquals(p1.getEmail(), found.getEmail());

        playerDao.deletePlayer(p1);
        found = playerDao.getPlayerByName(p1.getName());
        Assert.assertNull(found);
    }

    @Test
    public void createUpdateAndDeletePlayerTest() {
        playerDao.createPlayer(p1);

        Player found = playerDao.getPlayerByName(p1.getName());
        Assert.assertNotNull(found);
        Assert.assertEquals(p1.getName(), found.getName());
        Assert.assertEquals(p1.getEmail(), found.getEmail());

        p1.setName("changed");
        playerDao.updatePlayer(p1);

        playerDao.deletePlayer(p1);
        found = playerDao.getPlayerByName(p1.getName());
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
        em.flush();
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createDuplicatePlayerTest() {
        playerDao.createPlayer(p1);
        playerDao.createPlayer(p3);
        em.flush();
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void updateDuplicatePlayerTest() {
        playerDao.createPlayer(p1);
        playerDao.createPlayer(p2);
        p2.setName(p1.getName());
        playerDao.updatePlayer(p2);
        em.flush();
    }
}
