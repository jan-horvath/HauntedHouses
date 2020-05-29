package cz.fi.muni.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.GameInstanceDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.SpecterDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.SpecterFacade;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.Player;
import cz.muni.fi.pa165.hauntedhouses.model.Specter;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import cz.muni.fi.pa165.hauntedhouses.service.SpecterService;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

/**
 * @author Petr Vitovsky
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class SpecterFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private SpecterService specterService;

    @Mock
    private MappingService mappingService;

    private SpecterFacade specterFacade;

    @Autowired
    public SpecterFacadeTest(SpecterFacade specterFacade) {
        this.specterFacade = specterFacade;
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private Specter specter;
    private SpecterDTO specterDTO;
    private Specter specterOther;
    private SpecterDTO specterOtherDTO;

    private GameInstance gameInstance;
    private GameInstanceDTO gameInstanceDTO;
    private GameInstance gameInstanceOther;
    private GameInstanceDTO gameInstanceOtherDTO;

    private Player player1;
    private Player player2;

    @BeforeMethod
    public void init() {
        player1 = new Player();
        player1.setName("p1");
        player1.setEmail("e1");

        player2 = new Player();
        player2.setName("p2");
        player2.setEmail("e2");

        gameInstance = new GameInstance();
        gameInstance.setId(1L);
        gameInstance.setPlayer(player1);

        gameInstanceDTO = new GameInstanceDTO();
        gameInstanceDTO.setId(1L);

        specter = new Specter();
        specter.setName("specter");
        specter.setDescription("spooky");
        specter.setGameInstance(gameInstance);

        specterDTO = new SpecterDTO();
        specterDTO.setName("specter");
        specterDTO.setDescription("spooky");
        specterDTO.setGameInstance(gameInstanceDTO);

        gameInstanceOther = new GameInstance();
        gameInstanceOther.setId(3L);
        gameInstanceOther.setPlayer(player2);

        gameInstanceOtherDTO = new GameInstanceDTO();
        gameInstanceOtherDTO.setId(3L);

        specterOther = new Specter();
        specterOther.setName("other specter");
        specterOther.setDescription("even more spooky");
        specterOther.setGameInstance(gameInstanceOther);

        specterOtherDTO = new SpecterDTO();
        specterOtherDTO.setName("other specter");
        specterOtherDTO.setDescription("even more spooky");
        specterOtherDTO.setGameInstance(gameInstanceOtherDTO);

        when(specterService.getByGameInstanceId(1L)).thenReturn(specter);
        when(specterService.getByGameInstanceId(3L)).thenReturn(specterOther);
        when(mappingService.mapTo(specter, SpecterDTO.class)).thenReturn(specterDTO);
        when(mappingService.mapTo(specterOther, SpecterDTO.class)).thenReturn(specterOtherDTO);
    }

    @Test
    public void findSpecterByGameInstanceIdTest() {
        SpecterDTO testSpecter = specterFacade.getSpecterByGameInstanceId(1L);
        SpecterDTO testSpecterOther = specterFacade.getSpecterByGameInstanceId(3L);

        Assert.assertEquals(testSpecter.getName(), specter.getName());
        Assert.assertEquals(testSpecter.getDescription(), specter.getDescription());

        Assert.assertEquals(testSpecterOther.getName(), specterOther.getName());
        Assert.assertEquals(testSpecterOther.getDescription(), specterOther.getDescription());
    }

    @Test(expectedExceptions = DataRetrievalFailureException.class)
    public void findSpecterByGameInstanceIdNullTest() {
        when(specterService.getByGameInstanceId(2L)).thenThrow(DataRetrievalFailureException.class);

        specterFacade.getSpecterByGameInstanceId(2L);
    }

    @Test
    public void findSpecterByGameInstanceIdNoSpecterTest() {
        when(specterService.getByGameInstanceId(3L)).thenReturn(null);

        Assert.assertNull(specterFacade.getSpecterByGameInstanceId(3L));
    }
}
