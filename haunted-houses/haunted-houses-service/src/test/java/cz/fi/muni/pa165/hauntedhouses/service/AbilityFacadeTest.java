package cz.fi.muni.pa165.hauntedhouses.service;

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

import java.util.Optional;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class AbilityFacadeTest extends AbstractTestNGSpringContextTests {

    @InjectMocks
    private AbilityFacade abilityFacade = new AbilityFacadeImpl();

    @Mock
    private AbilityService abilityService;

    @Mock
    private MappingService mappingService;

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

        when(mappingService.mapTo(abilityCreateDTO, Ability.class)).thenReturn(ability);
        when(mappingService.mapTo(ability, AbilityDTO.class)).thenReturn(abilityDTO);
        when(abilityService.getAbilityById(ability.getId())).thenReturn(Optional.ofNullable(ability));
        when(abilityService.getAbilityByName(ability.getName())).thenReturn(Optional.ofNullable(ability));
    }

    @Test
    public void findAbilityByIdTest() {
        assertEquals(abilityFacade.findAbilityById(ability.getId()), abilityDTO);
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
    public void createAbilityTest() {
        assertEquals(ability.getId(), abilityFacade.createAbility(abilityCreateDTO));
        verify(abilityService).createAbility(ability);
    }

    @Test
    public void deleteAbilityTest() {
        abilityFacade.deleteAbility(ability.getId());
        verify(abilityService).deleteAbility(ability);
    }
}
