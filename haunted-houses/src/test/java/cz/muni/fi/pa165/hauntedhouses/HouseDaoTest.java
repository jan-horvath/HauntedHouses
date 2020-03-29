package cz.muni.fi.pa165.hauntedhouses;

import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.model.House;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Zoltan Fridrich
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class HouseDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private HouseDao houseDao;

    private House h1;
    private House h2;
    private House h3;

    @BeforeMethod
    public void setup() {
        h1 = new House();
        h1.setName("name1");
        h1.setAddress("address1");
        h1.setHistory("history1");
        h1.setHauntedSince(LocalDate.of(1995, 12, 22));

        h2 = new House();
        h2.setName("name2");
        h2.setAddress("address2");
        h2.setHistory("history2");
        h2.setHauntedSince(LocalDate.of(2012, 12, 22));

        h3 = new House();
        h3.setName(h1.getName());
        h3.setAddress(h1.getAddress());
        h3.setHistory(h1.getHistory());
        h3.setHauntedSince(h1.getHauntedSince());
    }

    @Test
    public void createAndGetByIdHouseTest() {
        houseDao.createHouse(h1);

        House found = houseDao.getHouseById(h1.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(h1.getName(), found.getName());
        Assert.assertEquals(h1.getAddress(), found.getAddress());
        Assert.assertEquals(h1.getHistory(), found.getHistory());
        Assert.assertEquals(h1.getHauntedSince(), found.getHauntedSince());
    }

    @Test
    public void getByIdNonexistingHouseTest() {
        houseDao.createHouse(h1);

        Long nonexistingId = h1.getId() + 1;
        Assert.assertNull(houseDao.getHouseById(nonexistingId));
    }

    @Test
    public void createAndGetByAddressHouseTest() {
        houseDao.createHouse(h1);

        House found = houseDao.getHouseByAddress(h1.getAddress());
        Assert.assertNotNull(found);
        Assert.assertEquals(h1.getName(), found.getName());
        Assert.assertEquals(h1.getAddress(), found.getAddress());
        Assert.assertEquals(h1.getHistory(), found.getHistory());
        Assert.assertEquals(h1.getHauntedSince(), found.getHauntedSince());
    }

    @Test public void getByAddressNonexistingHouseTest() {
        Assert.assertNull(houseDao.getHouseByAddress("nonexisting"));
    }

    @Test
    public void createAndGetAllHouseTest() {
        Assert.assertEquals(houseDao.getAllHouses().size(), 0);
        houseDao.createHouse(h1);
        houseDao.createHouse(h2);

        Assert.assertEquals(houseDao.getAllHouses().size(),2);
        Assert.assertTrue(houseDao.getAllHouses().contains(h1));
        Assert.assertTrue(houseDao.getAllHouses().contains(h2));
    }

    @Test
    public void createUpdateAndGetByAddressHouseTest() {
        houseDao.createHouse(h1);

        h1.setAddress("updatedAddress");
        houseDao.updateHouse(h1);

        House found = houseDao.getHouseByAddress(h1.getAddress());
        Assert.assertNotNull(found);
        Assert.assertEquals(h1.getName(), found.getName());
        Assert.assertEquals(h1.getAddress(), found.getAddress());
        Assert.assertEquals(h1.getHistory(), found.getHistory());
        Assert.assertEquals(h1.getHauntedSince(), found.getHauntedSince());
    }

    @Test
    public void createAndDeleteHouseTest() {
        houseDao.createHouse(h1);

        House found = houseDao.getHouseByAddress(h1.getAddress());
        Assert.assertNotNull(found);
        Assert.assertEquals(h1.getName(), found.getName());
        Assert.assertEquals(h1.getAddress(), found.getAddress());
        Assert.assertEquals(h1.getHistory(), found.getHistory());
        Assert.assertEquals(h1.getHauntedSince(), found.getHauntedSince());

        houseDao.deleteHouse(h1);
        found = houseDao.getHouseByAddress(h1.getAddress());
        Assert.assertNull(found);
    }

    @Test
    public void createUpdateAndDeleteHouseTest() {
        houseDao.createHouse(h1);

        House found = houseDao.getHouseByAddress(h1.getAddress());
        Assert.assertNotNull(found);
        Assert.assertEquals(h1.getName(), found.getName());
        Assert.assertEquals(h1.getAddress(), found.getAddress());
        Assert.assertEquals(h1.getHistory(), found.getHistory());
        Assert.assertEquals(h1.getHauntedSince(), found.getHauntedSince());

        h1.setAddress("changed");
        houseDao.updateHouse(h1);

        houseDao.deleteHouse(h1);
        found = houseDao.getHouseByAddress(h1.getAddress());
        Assert.assertNull(found);
    }

    @Test
    public void updateNonexistingHouseTest() {
        Assert.assertNull(houseDao.updateHouse(h1));
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createHouseWithNullAddressTest() {
        h1.setAddress(null);
        houseDao.createHouse(h1);
        houseDao.getAllHouses();
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createDuplicateHouseTest() {
        houseDao.createHouse(h1);
        houseDao.createHouse(h3);
        houseDao.getAllHouses();
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void updateDuplicateHouseTest() {
        houseDao.createHouse(h1);
        houseDao.createHouse(h2);
        h2.setAddress(h1.getAddress());
        houseDao.updateHouse(h2);
        houseDao.getAllHouses();
    }
}
