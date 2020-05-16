package cz.fi.muni.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.AbilityDao;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
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

/**
 * @author David Hofman
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class AbilityServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private AbilityDao abilityDao;

    private AbilityService abilityService;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private Ability ability1;
    private Ability ability2;
    List<Ability> allAbilities;

    @Autowired
    public AbilityServiceTest(AbilityService abilityService) {
        this.abilityService = abilityService;
    }

    @BeforeMethod
    public void prepareAbilities() {
        ability1 = new Ability();
        ability1.setName("ability1");

        ability2 = new Ability();
        ability2.setName("ability2");

        allAbilities = new ArrayList<>();
        allAbilities.add(ability1);
        allAbilities.add(ability2);
    }

    @Test
    public void getAbilityByIdTest() {
        when(abilityDao.getAbilityById(1L)).thenReturn(ability1);
        when(abilityDao.getAbilityById(2L)).thenReturn(ability2);
        when(abilityDao.getAbilityById(3L)).thenReturn(null);

        Assert.assertEquals(abilityService.getAbilityById(1L),ability1);
        Assert.assertEquals(abilityService.getAbilityById(2L),ability2);
        Assert.assertNull(abilityService.getAbilityById(3L));
    }

    @Test
    public void getAbilityByNameTest() {
        when(abilityDao.getAbilityByName("ability1")).thenReturn(ability1);
        when(abilityDao.getAbilityByName("ability2")).thenReturn(ability2);
        when(abilityDao.getAbilityByName("nonexistentAbility")).thenReturn(null);

        Assert.assertEquals(abilityService.getAbilityByName("ability1"),ability1);
        Assert.assertEquals(abilityService.getAbilityByName("ability2"),ability2);
        Assert.assertNull(abilityService.getAbilityByName("nonexistentAbility"));
    }

    @Test
    public void getAllAbilitiesTest() {
        when(abilityDao.getAllAbilities()).thenReturn(allAbilities);
        Assert.assertEquals(abilityService.getAllAbilities(),allAbilities);
    }

    @Test
    public void createAbilityTest() {
        abilityService.createAbility(ability1);
        verify(abilityDao,times(1)).createAbility(ability1);
    }

    @Test
    public void deleteAbilityTest() {
        abilityService.deleteAbility(ability1);
        verify(abilityDao,times(1)).deleteAbility(ability1);
    }
}
