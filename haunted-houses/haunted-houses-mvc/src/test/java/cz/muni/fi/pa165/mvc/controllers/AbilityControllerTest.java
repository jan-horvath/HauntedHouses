package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.hauntedhouses.controllers.AbilityController;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.AbilityFacade;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
public class AbilityControllerTest {

    @Mock
    private AbilityFacade abilityFacade;

    private AbilityController abilityController;

    private MockMvc mockMvc;

    private AbilityDTO abilityDTO;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        abilityController = new AbilityController(abilityFacade);
        mockMvc = MockMvcBuilders.standaloneSetup(abilityController).build();
    }

    @BeforeMethod
    public void setup() {
        abilityDTO = new AbilityDTO();
        abilityDTO.setId(1L);
        abilityDTO.setName("name");
        abilityDTO.setDescription("description");
    }

    @Test
    public void listTest() throws Exception {
        List<AbilityDTO> abilities = new ArrayList<>();
        abilities.add(abilityDTO);
        when(abilityFacade.findAllAbilities()).thenReturn(abilities);

        this.mockMvc.perform(get("/ability/list/")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("abilities"))
                .andExpect(model().attribute("abilities", abilities))
                .andExpect(forwardedUrl("ability/list"));
    }

    @Test
    public void viewTest() throws Exception {
        when(abilityFacade.findAbilityById(1L)).thenReturn(abilityDTO);

        this.mockMvc.perform(get("/ability/view/1")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ability"))
                .andExpect(model().attribute("ability", abilityDTO))
                .andExpect(forwardedUrl("ability/view"));
    }

    @Test
    public void createTest() throws Exception {
        AbilityCreateDTO abilityCreateDTO = new AbilityCreateDTO();
        abilityCreateDTO.setName("name");
        abilityCreateDTO.setDescription("description");

        when(abilityFacade.createAbility(abilityCreateDTO)).thenReturn(1L);

        this.mockMvc.perform(post("/ability/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "name")
                .param("description", "description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/ability/view/1"));
    }

    @Test
    public void createFailTest() throws Exception {
        AbilityCreateDTO abilityCreateDTO = new AbilityCreateDTO();
        abilityCreateDTO.setName("name");
        abilityCreateDTO.setDescription("description");

        when(abilityFacade.createAbility(abilityCreateDTO)).thenReturn(1L);

        this.mockMvc.perform(post("/ability/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "name"))
                .andExpect(forwardedUrl("ability/new"));
    }

    @Test
    public void updateTest() throws Exception {
        this.mockMvc.perform(post("/ability/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("name", "name")
                .param("description", "description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/ability/view/1"));
    }

    @Test
    public void updateFailTest() throws Exception {
        this.mockMvc.perform(post("/ability/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "name")
                .param("description", ""))
                .andExpect(forwardedUrl("ability/update"));
    }

    @Test
    public void deleteTest() throws Exception {
        when(abilityFacade.findAbilityById(1L)).thenReturn(abilityDTO);

        this.mockMvc.perform(post("/ability/delete/1"))
                .andExpect(redirectedUrl("http://localhost/ability/list"));
    }
}
