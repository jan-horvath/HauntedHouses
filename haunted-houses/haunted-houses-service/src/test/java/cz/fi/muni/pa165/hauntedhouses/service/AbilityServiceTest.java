package cz.fi.muni.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.AbilityDao;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
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
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * @author David Hofman
 */
@ContextConfiguration(classes=ServiceConfiguration.class)
public class AbilityServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private AbilityDao abilityDao;

    @Autowired
    @InjectMocks
    private AbilityService abilityService;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private Ability ability1;
    private Ability ability2;
    private List<Ability> allAbilities;

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
        when(abilityDao.getAbilityById(1L)).thenReturn(Optional.ofNullable(ability1));
        when(abilityDao.getAbilityById(2L)).thenReturn(Optional.ofNullable(ability2));
        when(abilityDao.getAbilityById(3L)).thenReturn(Optional.empty());

        Assert.assertEquals(abilityService.getAbilityById(1L).get(),ability1);
        Assert.assertEquals(abilityService.getAbilityById(2L).get(),ability2);
        Assert.assertTrue(abilityService.getAbilityById(3L).isEmpty());
    }

    @Test
    public void getAbilityByNameTest() {
        when(abilityDao.getAbilityByName("ability1")).thenReturn(Optional.ofNullable(ability1));
        when(abilityDao.getAbilityByName("ability2")).thenReturn(Optional.ofNullable(ability2));
        when(abilityDao.getAbilityByName("nonexistentAbility")).thenReturn(Optional.empty());

        Assert.assertEquals(abilityService.getAbilityByName("ability1").get(),ability1);
        Assert.assertEquals(abilityService.getAbilityByName("ability2").get(),ability2);
        Assert.assertTrue(abilityService.getAbilityByName("nonexistentAbility").isEmpty());
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
