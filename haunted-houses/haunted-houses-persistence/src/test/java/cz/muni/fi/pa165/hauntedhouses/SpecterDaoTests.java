package cz.muni.fi.pa165.hauntedhouses;

import cz.muni.fi.pa165.hauntedhouses.dao.*;
import cz.muni.fi.pa165.hauntedhouses.model.*;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;

import javax.persistence.PersistenceException;
import java.time.LocalTime;
import java.util.*;

/**
 * @author David Hofman
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
public class SpecterDaoTests extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private SpecterDao specterDao;

    @Autowired
    private AbilityDao abilityDao;

    @Autowired
    private HouseDao houseDao;

    @Autowired
    private GameInstanceDao gameInstanceDao;

    @Autowired
    private PlayerDao playerDao;

    private Specter s1;
    private Specter s2;

    private void createSpecterUpdateGameInstance(Specter specter) {
        specterDao.createSpecter(specter);
        specter.getGameInstance().setSpecter(specter);
        gameInstanceDao.updateGameInstance(specter.getGameInstance());
    }

    private void safelyDeleteSpecter(Specter specter)
    {
        specterDao.deleteSpecter(specter);
        specter.getGameInstance().setSpecter(null);
        gameInstanceDao.updateGameInstance(specter.getGameInstance());
    }

    private void compareAllSpecterAttributes(Specter originalSpecter, Specter foundSpecter)
    {
        Assert.assertNotNull(foundSpecter);
        Assert.assertEquals(originalSpecter.getName(), foundSpecter.getName());
        Assert.assertEquals(originalSpecter.getDescription(), foundSpecter.getDescription());
        Assert.assertEquals(originalSpecter.getStartOfHaunting(), foundSpecter.getStartOfHaunting());
        Assert.assertEquals(originalSpecter.getEndOfHaunting(), foundSpecter.getEndOfHaunting());
        Assert.assertEquals(originalSpecter.getGameInstance(), foundSpecter.getGameInstance());
        Assert.assertEquals(originalSpecter.getHouse(), foundSpecter.getHouse());
        Assert.assertEquals(originalSpecter.getAbilities(), foundSpecter.getAbilities());
    }

    @BeforeMethod
    public void setup() {
        Player p1 = new Player();
        p1.setName("playerName1");
        p1.setEmail("playerEmail1");
        p1.setPasswordHash("playerPassword1");
        playerDao.createPlayer(p1);

        Player p2 = new Player();
        p2.setName("playerName2");
        p2.setEmail("playerEmail2");
        p2.setPasswordHash("playerPassword2");
        playerDao.createPlayer(p2);

        GameInstance g1 = new GameInstance();
        g1.setPlayer(p1);
        g1.setBanishesRequired(2);
        g1.setBanishesAttempted(1);
        gameInstanceDao.createGameInstance(g1);

        GameInstance g2 = new GameInstance();
        g2.setPlayer(p2);
        g2.setBanishesRequired(5);
        g2.setBanishesAttempted(3);
        gameInstanceDao.createGameInstance(g2);

        p1.setGameInstance(g1);
        p2.setGameInstance(g2);
        playerDao.updatePlayer(p2);
        playerDao.updatePlayer(p1);

        s1 = new Specter();
        s1.setGameInstance(g1);
        s1.setName("SpecterName1");
        s1.setDescription("SpecterDescription1");
        s1.setStartOfHaunting(LocalTime.of(3,0));
        s1.setEndOfHaunting(LocalTime.of(5,30));

        s2 = new Specter();
        s2.setGameInstance(g2);
        s2.setName("SpecterName2");
        s2.setDescription("SpecterDescription2");
        s2.setStartOfHaunting(LocalTime.of(23,40));
        s2.setEndOfHaunting(LocalTime.of(4,15));
    }

    @Test
    public void getByIdNonexistentSpecter() {
        Assert.assertNull(specterDao.getSpecterById(420));
    }

    @Test
    public void createAndGetByIdSpecterTest() {
        createSpecterUpdateGameInstance(s1);
        Specter foundSpecter = specterDao.getSpecterById(s1.getId());
        compareAllSpecterAttributes(s1,foundSpecter);
    }

    @Test
    public void getByGameInstanceNonexistingSpecter() {
        Assert.assertNull(specterDao.getSpecterByGameInstance(s1.getGameInstance()));
    }

    @Test
    public void createAndGetByGameInstanceSpecterTest() {
        createSpecterUpdateGameInstance(s1);
        Specter foundSpecter = specterDao.getSpecterByGameInstance(s1.getGameInstance());
        compareAllSpecterAttributes(s1,foundSpecter);
    }

    @Test
    public void createAndGetByHouseSpecterTest() {
        House house1 = new House();
        house1.setAddress("HouseAddress1");
        house1.setName("HouseName1");
        house1.setHint("Hint1");
        houseDao.createHouse(house1);

        House house2 = new House();
        house2.setAddress("HouseAddress2");
        house2.setName("HouseName2");
        house2.setHint("Hint2");
        houseDao.createHouse(house2);

        House house3 = new House();
        house3.setAddress("HouseAddress3");
        house3.setName("HouseName3");
        house3.setHint("Hint3");
        houseDao.createHouse(house3);

        s1.setHouse(house1);
        s2.setHouse(house2);

        createSpecterUpdateGameInstance(s1);
        createSpecterUpdateGameInstance(s2);

        List<Specter> foundSpecters = specterDao.getSpectersByHouse(house1);
        Assert.assertEquals(foundSpecters.size(),1);
        compareAllSpecterAttributes(s1,foundSpecters.get(0));

        foundSpecters = specterDao.getSpectersByHouse(house2);
        Assert.assertEquals(foundSpecters.size(),1);
        compareAllSpecterAttributes(s2,foundSpecters.get(0));

        foundSpecters = specterDao.getSpectersByHouse(house3);
        Assert.assertEquals(foundSpecters.size(),0);
    }

    @Test
    public void createAndGetByAbilitySpecterTest() {
        Ability ability1 = new Ability();
        ability1.setName("AbilityName1");
        ability1.setDescription("AbilityDescription1");
        abilityDao.createAbility(ability1);

        Ability ability2 = new Ability();
        ability2.setName("AbilityName2");
        ability2.setDescription("AbilityDescription2");
        abilityDao.createAbility(ability2);

        Ability ability3 = new Ability();
        ability3.setName("AbilityName3");
        ability3.setDescription("AbilityDescription3");
        abilityDao.createAbility(ability3);

        //Arrays.asList and Collections.singletonList creates an unmodifiable list which Hibernate seems to hate
        //That is why a new modifiable ArrayList is created with the same contents
        s1.setAbilities(new HashSet<>(Arrays.asList(ability1,ability2)));
        s2.setAbilities(new HashSet<>(Collections.singletonList(ability2)));

        createSpecterUpdateGameInstance(s1);
        createSpecterUpdateGameInstance(s2);

        List<Specter> foundSpecters = specterDao.getSpectersByAbility(ability1);
        Assert.assertEquals(foundSpecters.size(),1);
        compareAllSpecterAttributes(s1,foundSpecters.get(0));

        foundSpecters = specterDao.getSpectersByAbility(ability2);
        int index1 = foundSpecters.indexOf(s1);
        int index2 = foundSpecters.indexOf(s2);
        Assert.assertTrue(index1 != -1 && index2 != -1);
        Assert.assertEquals(foundSpecters.size(),2);
        compareAllSpecterAttributes(s1,foundSpecters.get(index1));
        compareAllSpecterAttributes(s2,foundSpecters.get(index2));

        foundSpecters = specterDao.getSpectersByAbility(ability3);
        Assert.assertEquals(foundSpecters.size(),0);
    }

    @Test
    public void createAndGetAllSpecterTest() {
        createSpecterUpdateGameInstance(s1);

        List<Specter> foundSpecters = specterDao.getAllSpecters();
        Assert.assertEquals(foundSpecters.size(),1);
        compareAllSpecterAttributes(s1,foundSpecters.get(0));

        createSpecterUpdateGameInstance(s2);

        foundSpecters = specterDao.getAllSpecters();
        Assert.assertEquals(foundSpecters.size(),2);
        int index1 = foundSpecters.indexOf(s1);
        int index2 = foundSpecters.indexOf(s2);
        Assert.assertTrue(index1 != -1 && index2 != -1);
        compareAllSpecterAttributes(s1,foundSpecters.get(index1));
        compareAllSpecterAttributes(s2,foundSpecters.get(index2));
    }

    @Test
    public void updateNonexistingSpecter() {
        Assert.assertNull(specterDao.updateSpecter(s1));
    }

    @Test
    public void createUpdateAndGetByIdSpecterTest() {
        createSpecterUpdateGameInstance(s1);
        s1.setName("UpdatedSpecterName1");

        Specter updatedSpecter = specterDao.updateSpecter(s1);
        Specter foundSpecter = specterDao.getSpecterById(s1.getId());

        Assert.assertNotNull(foundSpecter);
        compareAllSpecterAttributes(s1,foundSpecter);
        Assert.assertEquals(updatedSpecter, foundSpecter);
    }

    @Test
    public void deleteNonexistingSpecter() {
        specterDao.deleteSpecter(s1);
    }

    @Test
    public void createDeleteAndGetByIdSpecterTest() {
        createSpecterUpdateGameInstance(s1);

        Specter foundSpecter = specterDao.getSpecterById(s1.getId());
        Assert.assertNotNull(foundSpecter);
        compareAllSpecterAttributes(s1,foundSpecter);

        safelyDeleteSpecter(s1);
        foundSpecter = specterDao.getSpecterById(s1.getId());
        Assert.assertNull(foundSpecter);
    }

    @Test
    public void createDeleteMultipleAndGetByIdSpecterTest() {
        createSpecterUpdateGameInstance(s1);
        createSpecterUpdateGameInstance(s2);

        Specter foundSpecter = specterDao.getSpecterById(s1.getId());
        Assert.assertNotNull(foundSpecter);
        compareAllSpecterAttributes(s1,foundSpecter);

        foundSpecter = specterDao.getSpecterById(s2.getId());
        Assert.assertNotNull(foundSpecter);
        compareAllSpecterAttributes(s2,foundSpecter);

        safelyDeleteSpecter(s1);
        foundSpecter = specterDao.getSpecterById(s1.getId());
        Assert.assertNull(foundSpecter);

        foundSpecter = specterDao.getSpecterById(s2.getId());
        Assert.assertNotNull(foundSpecter);
        compareAllSpecterAttributes(s2,foundSpecter);

        safelyDeleteSpecter(s2);
        foundSpecter = specterDao.getSpecterById(s2.getId());
        Assert.assertNull(foundSpecter);

        Assert.assertEquals(specterDao.getAllSpecters().size(),0);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createSpecterWithNullNameTest() {
        s1.setName(null);
        createSpecterUpdateGameInstance(s1);
        specterDao.getAllSpecters();
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createDuplicateSpecterTest() {
        Specter s3 = new Specter();
        s3.setName(s1.getName());
        s3.setDescription(s1.getDescription());
        s3.setStartOfHaunting(s1.getStartOfHaunting());
        s3.setEndOfHaunting(s1.getEndOfHaunting());
        s3.setGameInstance(s1.getGameInstance());

        createSpecterUpdateGameInstance(s1);
        createSpecterUpdateGameInstance(s3);
        specterDao.getAllSpecters();
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void updateDuplicateSpecterTest() {
        createSpecterUpdateGameInstance(s1);

        specterDao.createSpecter(s2);
        s2.setGameInstance(s1.getGameInstance());
        specterDao.updateSpecter(s2);
        specterDao.getAllSpecters();
    }
}
