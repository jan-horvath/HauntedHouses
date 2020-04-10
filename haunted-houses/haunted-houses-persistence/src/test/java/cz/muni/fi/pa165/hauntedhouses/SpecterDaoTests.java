package cz.muni.fi.pa165.hauntedhouses;

import cz.muni.fi.pa165.hauntedhouses.dao.AbilityDao;
import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.dao.SpecterDao;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import cz.muni.fi.pa165.hauntedhouses.model.Specter;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;

import javax.persistence.PersistenceException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    private Specter s1;
    private Specter s2;

    @BeforeMethod
    public void setup() {

        s1 = new Specter();
        s1.setName("SpecterName1");
        s1.setDescription("SpecterDescription1");
        s1.setStartOfHaunting(LocalTime.of(3,0));
        s1.setEndOfHaunting(LocalTime.of(5,30));

        s2 = new Specter();
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
        specterDao.createSpecter(s1);

        Specter foundSpecter = specterDao.getSpecterById(s1.getId());
        Assert.assertNotNull(foundSpecter);
        Assert.assertEquals(s1.getName(), foundSpecter.getName());
        Assert.assertEquals(s1.getDescription(), foundSpecter.getDescription());
        Assert.assertEquals(s1.getStartOfHaunting(),foundSpecter.getStartOfHaunting());
        Assert.assertEquals(s1.getEndOfHaunting(),foundSpecter.getEndOfHaunting());
    }

    @Test
    public void getByNameNonexistentSpecter() {
        Assert.assertNull(specterDao.getSpecterByName("Non-existing Specter"));
    }

    @Test
    public void createAndGetByNameSpecterTest() {
        specterDao.createSpecter(s1);

        Specter foundSpecter = specterDao.getSpecterByName(s1.getName());
        Assert.assertNotNull(foundSpecter);
        Assert.assertEquals(s1.getName(), foundSpecter.getName());
        Assert.assertEquals(s1.getDescription(), foundSpecter.getDescription());
        Assert.assertEquals(s1.getStartOfHaunting(),foundSpecter.getStartOfHaunting());
        Assert.assertEquals(s1.getEndOfHaunting(),foundSpecter.getEndOfHaunting());
    }

    @Test
    public void createAndGetByHouseSpecterTest() {

        House house1 = new House();
        house1.setAddress("HouseAdress1");
        house1.setName("HouseName1");
        house1.setHint("Hint1");
        houseDao.createHouse(house1);

        House house2 = new House();
        house2.setAddress("HouseAdress2");
        house2.setName("HouseName2");
        house2.setHint("Hint2");
        houseDao.createHouse(house2);

        House house3 = new House();
        house3.setAddress("HouseAdress3");
        house3.setName("HouseName3");
        house3.setHint("Hint3");
        houseDao.createHouse(house3);

        s1.setHouse(house1);
        s2.setHouse(house2);

        specterDao.createSpecter(s1);
        specterDao.createSpecter(s2);


        List<Specter> foundSpecters = specterDao.getSpectersByHouse(house1);
        Assert.assertEquals(foundSpecters.size(),1);
        Assert.assertEquals(s1.getName(), foundSpecters.get(0).getName());
        Assert.assertEquals(s1.getDescription(), foundSpecters.get(0).getDescription());
        Assert.assertEquals(s1.getStartOfHaunting(),foundSpecters.get(0).getStartOfHaunting());
        Assert.assertEquals(s1.getEndOfHaunting(),foundSpecters.get(0).getEndOfHaunting());

        foundSpecters = specterDao.getSpectersByHouse(house2);
        Assert.assertEquals(foundSpecters.size(),1);
        Assert.assertEquals(s2.getName(), foundSpecters.get(0).getName());
        Assert.assertEquals(s2.getDescription(), foundSpecters.get(0).getDescription());
        Assert.assertEquals(s2.getStartOfHaunting(),foundSpecters.get(0).getStartOfHaunting());
        Assert.assertEquals(s2.getEndOfHaunting(),foundSpecters.get(0).getEndOfHaunting());

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

        s1.setAbilities(Arrays.asList(ability1,ability2));
        s2.setAbilities(Collections.singletonList(ability2));

        specterDao.createSpecter(s1);
        specterDao.createSpecter(s2);

        List<Specter> foundSpecters = specterDao.getSpectersByAbility(ability1);
        Assert.assertEquals(foundSpecters.size(),1);
        Assert.assertEquals(s1.getName(), foundSpecters.get(0).getName());
        Assert.assertEquals(s1.getDescription(), foundSpecters.get(0).getDescription());
        Assert.assertEquals(s1.getStartOfHaunting(),foundSpecters.get(0).getStartOfHaunting());
        Assert.assertEquals(s1.getEndOfHaunting(),foundSpecters.get(0).getEndOfHaunting());

        foundSpecters = specterDao.getSpectersByAbility(ability2);
        int index1 = foundSpecters.indexOf(s1);
        int index2 = foundSpecters.indexOf(s2);
        Assert.assertTrue(index1 != -1 && index2 != -1);
        Assert.assertEquals(foundSpecters.size(),2);
        Assert.assertEquals(s1.getName(), foundSpecters.get(index1).getName());
        Assert.assertEquals(s1.getDescription(), foundSpecters.get(index1).getDescription());
        Assert.assertEquals(s1.getStartOfHaunting(),foundSpecters.get(index1).getStartOfHaunting());
        Assert.assertEquals(s1.getEndOfHaunting(),foundSpecters.get(index1).getEndOfHaunting());
        Assert.assertEquals(s2.getName(), foundSpecters.get(index2).getName());
        Assert.assertEquals(s2.getDescription(), foundSpecters.get(index2).getDescription());
        Assert.assertEquals(s2.getStartOfHaunting(),foundSpecters.get(index2).getStartOfHaunting());
        Assert.assertEquals(s2.getEndOfHaunting(),foundSpecters.get(index2).getEndOfHaunting());

        foundSpecters = specterDao.getSpectersByAbility(ability3);
        Assert.assertEquals(foundSpecters.size(),0);
    }

    @Test
    public void createAndGetAllSpecterTest() {
        specterDao.createSpecter(s1);

        List<Specter> foundSpecters = specterDao.getAllSpecters();
        Assert.assertEquals(foundSpecters.size(),1);
        Assert.assertEquals(s1.getName(), foundSpecters.get(0).getName());
        Assert.assertEquals(s1.getDescription(), foundSpecters.get(0).getDescription());
        Assert.assertEquals(s1.getStartOfHaunting(),foundSpecters.get(0).getStartOfHaunting());
        Assert.assertEquals(s1.getEndOfHaunting(),foundSpecters.get(0).getEndOfHaunting());

        specterDao.createSpecter(s2);

        foundSpecters = specterDao.getAllSpecters();
        Assert.assertEquals(foundSpecters.size(),2);
        int index1 = foundSpecters.indexOf(s1);
        int index2 = foundSpecters.indexOf(s2);
        Assert.assertTrue(index1 != -1 && index2 != -1);
        Assert.assertEquals(foundSpecters.size(),2);
        Assert.assertEquals(s1.getName(), foundSpecters.get(index1).getName());
        Assert.assertEquals(s1.getDescription(), foundSpecters.get(index1).getDescription());
        Assert.assertEquals(s1.getStartOfHaunting(),foundSpecters.get(index1).getStartOfHaunting());
        Assert.assertEquals(s1.getEndOfHaunting(),foundSpecters.get(index1).getEndOfHaunting());
        Assert.assertEquals(s2.getName(), foundSpecters.get(index2).getName());
        Assert.assertEquals(s2.getDescription(), foundSpecters.get(index2).getDescription());
        Assert.assertEquals(s2.getStartOfHaunting(),foundSpecters.get(index2).getStartOfHaunting());
        Assert.assertEquals(s2.getEndOfHaunting(),foundSpecters.get(index2).getEndOfHaunting());
    }

    @Test
    public void updateNonexistingSpecter() {
        Assert.assertNull(specterDao.updateSpecter(s1));
    }

    @Test
    public void createUpdateAndGetByNameSpecterTest() {
        specterDao.createSpecter(s1);
        s1.setName("UpdatedSpecterName1");

        Specter updatedSpecter = specterDao.updateSpecter(s1);
        Specter foundSpecter = specterDao.getSpecterByName(s1.getName());

        Assert.assertNotNull(foundSpecter);
        Assert.assertEquals(s1.getName(), foundSpecter.getName());
        Assert.assertEquals(s1.getDescription(), foundSpecter.getDescription());
        Assert.assertEquals(s1.getStartOfHaunting(),foundSpecter.getStartOfHaunting());
        Assert.assertEquals(s1.getEndOfHaunting(),foundSpecter.getEndOfHaunting());
        Assert.assertEquals(updatedSpecter, foundSpecter);
    }

    @Test
    public void deleteNonexistingSpecter() {
        specterDao.deleteSpecter(s1);
    }

    @Test
    public void createDeleteAndGetByIdSpecterTest() {
        specterDao.createSpecter(s1);

        Specter foundSpecter = specterDao.getSpecterById(s1.getId());
        Assert.assertNotNull(foundSpecter);
        Assert.assertEquals(s1.getName(), foundSpecter.getName());
        Assert.assertEquals(s1.getDescription(), foundSpecter.getDescription());
        Assert.assertEquals(s1.getStartOfHaunting(),foundSpecter.getStartOfHaunting());
        Assert.assertEquals(s1.getEndOfHaunting(),foundSpecter.getEndOfHaunting());

        specterDao.deleteSpecter(s1);
        foundSpecter = specterDao.getSpecterById(s1.getId());
        Assert.assertNull(foundSpecter);
    }

    @Test
    public void createDeleteMultipleAndGetByNameSpecterTest() {
        specterDao.createSpecter(s1);
        specterDao.createSpecter(s2);

        Specter foundSpecter = specterDao.getSpecterByName(s1.getName());
        Assert.assertNotNull(foundSpecter);
        Assert.assertEquals(s1.getName(), foundSpecter.getName());
        Assert.assertEquals(s1.getDescription(), foundSpecter.getDescription());
        Assert.assertEquals(s1.getStartOfHaunting(),foundSpecter.getStartOfHaunting());
        Assert.assertEquals(s1.getEndOfHaunting(),foundSpecter.getEndOfHaunting());

        foundSpecter = specterDao.getSpecterByName(s2.getName());
        Assert.assertNotNull(foundSpecter);
        Assert.assertEquals(s2.getName(), foundSpecter.getName());
        Assert.assertEquals(s2.getDescription(), foundSpecter.getDescription());
        Assert.assertEquals(s2.getStartOfHaunting(),foundSpecter.getStartOfHaunting());
        Assert.assertEquals(s2.getEndOfHaunting(),foundSpecter.getEndOfHaunting());

        specterDao.deleteSpecter(s1);

        foundSpecter = specterDao.getSpecterByName(s1.getName());
        Assert.assertNull(foundSpecter);

        foundSpecter = specterDao.getSpecterByName(s2.getName());
        Assert.assertNotNull(foundSpecter);
        Assert.assertEquals(s2.getName(), foundSpecter.getName());
        Assert.assertEquals(s2.getDescription(), foundSpecter.getDescription());
        Assert.assertEquals(s2.getStartOfHaunting(),foundSpecter.getStartOfHaunting());
        Assert.assertEquals(s2.getEndOfHaunting(),foundSpecter.getEndOfHaunting());

        specterDao.deleteSpecter(s2);
        foundSpecter = specterDao.getSpecterByName(s2.getName());
        Assert.assertNull(foundSpecter);

        Assert.assertEquals(specterDao.getAllSpecters().size(),0);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createSpecterWithNullNameTest() {
        s1.setName(null);
        specterDao.createSpecter(s1);
        specterDao.getAllSpecters();
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void createDuplicateSpecterTest() {
        Specter s3 = new Specter();
        s3.setName(s1.getName());
        s3.setDescription(s1.getDescription());
        s3.setStartOfHaunting(s1.getStartOfHaunting());
        s3.setEndOfHaunting(s1.getEndOfHaunting());

        specterDao.createSpecter(s1);
        specterDao.createSpecter(s3);
        specterDao.getAllSpecters();
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void updateDuplicateSpecterTest() {
        specterDao.createSpecter(s1);
        specterDao.createSpecter(s2);
        s2.setName(s1.getName());
        specterDao.updateSpecter(s2);
        specterDao.getAllSpecters();
    }
}
