package cz.fi.muni.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dto.SpecterDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.SpecterFacade;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.Player;
import cz.muni.fi.pa165.hauntedhouses.model.Specter;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import cz.muni.fi.pa165.hauntedhouses.service.SpecterService;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.service.facade.SpecterFacadeImpl;
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

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class SpecterFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private SpecterService specterService;

    @Autowired
    private MappingService mappingService;

    private SpecterFacade specterFacade;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        specterFacade = new SpecterFacadeImpl(specterService, mappingService);
    }

    private Specter specter;
    private Specter specterOther;
    private GameInstance gameInstance;
    private GameInstance gameInstanceOther;


    @BeforeMethod
    public void init() {
        gameInstance = new GameInstance();
        gameInstance.setId(1L);

        specter = new Specter();
        specter.setName("specter");
        specter.setDescription("spooky");
        specter.setGameInstance(gameInstance);

        gameInstanceOther = new GameInstance();
        gameInstanceOther.setId(3L);

        specterOther = new Specter();
        specterOther.setName("other specter");
        specterOther.setDescription("even more spooky");
        specterOther.setGameInstance(gameInstanceOther);

        when(specterService.getByGameInstanceId(1L)).thenReturn(specter);
        when(specterService.getByGameInstanceId(3L)).thenReturn(specterOther);
        when(specterService.getByGameInstanceId(2L)).thenReturn(null);
    }

    @Test
    public void findSpecterByGameInstanceIdTest() {
        SpecterDTO testSpecter = specterFacade.findSpecterByGameInstanceId(1L);
        SpecterDTO testSpecterOther = specterFacade.findSpecterByGameInstanceId(3L);

        Assert.assertEquals(testSpecter.getName(), specter.getName());
        Assert.assertEquals(testSpecter.getDescription(), specter.getDescription());

        Assert.assertEquals(testSpecterOther.getName(), specterOther.getName());
        Assert.assertEquals(testSpecterOther.getDescription(), specterOther.getDescription());

        Assert.assertNull(specterFacade.findSpecterByGameInstanceId(2L));
    }
}
