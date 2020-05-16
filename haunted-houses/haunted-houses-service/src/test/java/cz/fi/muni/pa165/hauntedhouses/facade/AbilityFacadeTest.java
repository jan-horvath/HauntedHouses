package cz.fi.muni.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.AbilityFacade;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.service.facade.AbilityFacadeImpl;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author Zoltan Fridrich
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class AbilityFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MappingService mappingService;

    @Mock
    private AbilityService abilityService;

    @InjectMocks
    private AbilityFacade abilityFacade = new AbilityFacadeImpl(mappingService,abilityService);

    private List<AbilityDTO> abilitiesDTO;
    private AbilityCreateDTO abilityCreateDTO;
    private AbilityDTO abilityDTO;
    private Ability ability;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        reset(abilityService);
        reset(mappingService);

        ability = new Ability();
        ability.setId(1L);
        ability.setName("name");
        ability.setDescription("description");

        abilityDTO = new AbilityDTO();
        abilityDTO.setId(ability.getId());
        abilityDTO.setName(ability.getName());
        abilityDTO.setDescription(ability.getDescription());

        abilityCreateDTO = new AbilityCreateDTO();
        abilityCreateDTO.setName(ability.getName());
        abilityCreateDTO.setDescription(ability.getDescription());

        List<Ability> abilities = new ArrayList<>();
        abilities.add(ability);
        abilities.add(new Ability());

        abilitiesDTO = new ArrayList<>();
        abilitiesDTO.add(abilityDTO);
        abilitiesDTO.add(new AbilityDTO());

        when(mappingService.mapTo(abilityCreateDTO, Ability.class)).thenReturn(ability);
        when(mappingService.mapTo(abilityDTO, Ability.class)).thenReturn(ability);
        when(mappingService.mapTo(ability, AbilityDTO.class)).thenReturn(abilityDTO);
        when(mappingService.mapTo(abilities, AbilityDTO.class)).thenReturn(abilitiesDTO);

        when(abilityService.getAbilityById(ability.getId())).thenReturn(ability);
        when(abilityService.getAbilityByName(ability.getName())).thenReturn(ability);
        when(abilityService.getAllAbilities()).thenReturn(abilities);
    }

    @Test
    public void findAbilityByIdTest() {
        assertEquals(abilityDTO, abilityFacade.findAbilityById(ability.getId()));
        verify(abilityService).getAbilityById(ability.getId());

        assertNull(abilityFacade.findAbilityById(2L));
    }

    @Test
    public void findAbilityByNameTest() {
        assertEquals(abilityDTO, abilityFacade.findAbilityByName(ability.getName()));
        verify(abilityService).getAbilityByName(ability.getName());

        assertNull(abilityFacade.findAbilityByName("non-existent"));
    }

    @Test
    public void findAllAbilitiesTest() {
        assertEquals(abilitiesDTO, abilityFacade.findAllAbilities());
        verify(abilityService).getAllAbilities();
    }

    @Test
    public void createAbilityTest() {
        assertEquals(ability.getId(), abilityFacade.createAbility(abilityCreateDTO));
        verify(abilityService).createAbility(ability);
    }

    @Test
    public void deleteAbilityTest() {
        abilityFacade.deleteAbility(ability.getId());
        verify(abilityService).deleteAbility(ability);
    }

    @Test
    public void updateAbilityTest() {
        abilityDTO.setName("new name");
        abilityFacade.updateAbility(abilityDTO);
        assertEquals(abilityDTO, abilityFacade.findAbilityById(ability.getId()));
        verify(abilityService).updateAbility(ability);
    }
}
