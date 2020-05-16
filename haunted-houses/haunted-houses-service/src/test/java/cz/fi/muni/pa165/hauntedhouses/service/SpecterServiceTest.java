package cz.fi.muni.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.SpecterDao;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.Specter;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
import cz.muni.fi.pa165.hauntedhouses.service.GameInstanceService;
import cz.muni.fi.pa165.hauntedhouses.service.SpecterService;
import cz.muni.fi.pa165.hauntedhouses.service.SpecterServiceImpl;

import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class SpecterServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private AbilityService abilityService;

    @Mock
    private GameInstanceService gameInstanceService;

    @Mock
    private SpecterDao specterDao;

    private SpecterService specterService = new SpecterServiceImpl(
            abilityService,
            gameInstanceService,
            specterDao);

    private List<Ability> abilities;
    private Specter specter;
    private GameInstance gameInstance;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        reset(abilityService);
        reset(gameInstanceService);
        reset(specterDao);

        abilities = new ArrayList<>();
        specter = new Specter();
        gameInstance = new GameInstance();

        when(gameInstanceService.getGameInstanceById(1L)).thenReturn(gameInstance);
        when(gameInstanceService.getGameInstanceById(2L)).thenReturn(null);
        when(abilityService.getAllAbilities()).thenReturn(abilities);
        when(specterDao.getSpecterByGameInstance(gameInstance)).thenReturn(specter);
    }

    @Test
    public void generateRandomSpecterTest() {
        Specter randomSpecter = specterService.generateRandomSpecter();
        assertNotNull(randomSpecter);
        assertNotNull(randomSpecter.getName());
        assertNotNull(randomSpecter.getStartOfHaunting());
        assertNotNull(randomSpecter.getEndOfHaunting());
        assertFalse(randomSpecter.getDescription().isEmpty());
        assertEquals(randomSpecter.getAbilities(), abilities);

        abilities.add(new Ability());
        randomSpecter = specterService.generateRandomSpecter();
        assertNotNull(randomSpecter);
        assertNotNull(randomSpecter.getName());
        assertNotNull(randomSpecter.getStartOfHaunting());
        assertNotNull(randomSpecter.getEndOfHaunting());
        assertFalse(randomSpecter.getDescription().isEmpty());
        assertEquals(randomSpecter.getAbilities(), abilities);
    }

    @Test
    public void getByGameInstanceIdTest() {
        assertEquals(specterService.getByGameInstanceId(1L), specter);
        verify(gameInstanceService).getGameInstanceById(1L);
        verify(specterDao).getSpecterByGameInstance(gameInstance);

        assertThrows(DataRetrievalFailureException.class, () -> specterService.getByGameInstanceId(2L));
    }
}
