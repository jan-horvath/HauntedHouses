package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.hauntedhouses.controllers.HouseController;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
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
public class HouseControllerTest {

    @Mock
    private HouseFacade houseFacade;

    private HouseController houseController;

    private MockMvc mockMvc;

    private HouseDTO houseDTO;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        houseController = new HouseController(houseFacade);
        mockMvc = MockMvcBuilders.standaloneSetup(houseController).build();
    }

    @BeforeMethod
    public void setup() {
        houseDTO = new HouseDTO();
        houseDTO.setId(1L);
        houseDTO.setName("name");
        houseDTO.setAddress("address");
        houseDTO.setHint("hint");
    }

    @Test
    public void listTest() throws Exception {
        List<HouseDTO> houses = new ArrayList<>();
        houses.add(houseDTO);
        when(houseFacade.findAllHouses()).thenReturn(houses);

        this.mockMvc.perform(get("/house/list/")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("houses"))
                .andExpect(model().attribute("houses", houses))
                .andExpect(forwardedUrl("house/list"));
    }

    @Test
    public void viewTest() throws Exception {
        when(houseFacade.findHouseById(1L)).thenReturn(houseDTO);

        this.mockMvc.perform(get("/house/view/1")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("house"))
                .andExpect(model().attribute("house", houseDTO))
                .andExpect(forwardedUrl("house/view"));
    }

    @Test
    public void createTest() throws Exception {
        HouseCreateDTO houseCreateDTO = new HouseCreateDTO();
        houseCreateDTO.setName("name");
        houseCreateDTO.setAddress("address");
        houseCreateDTO.setHint("hint");

        when(houseFacade.createHouse(houseCreateDTO)).thenReturn(1L);

        this.mockMvc.perform(post("/house/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "name")
                .param("address", "address")
                .param("hint", "hint"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/house/view/1"));
    }

    @Test
    public void createFailTest() throws Exception {
        HouseCreateDTO houseCreateDTO = new HouseCreateDTO();
        houseCreateDTO.setName("name");
        houseCreateDTO.setAddress("address");
        houseCreateDTO.setHint("hint");

        when(houseFacade.createHouse(houseCreateDTO)).thenReturn(1L);

        this.mockMvc.perform(post("/house/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "name")
                .param("address", "address"))
                .andExpect(forwardedUrl("house/new"));
    }

    @Test
    public void updateTest() throws Exception {
        this.mockMvc.perform(post("/house/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("name", "name")
                .param("address", "address")
                .param("hint", "hint"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/house/view/1"));
    }

    @Test
    public void updateFailTest() throws Exception {
        this.mockMvc.perform(post("/house/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "name")
                .param("address", "address")
                .param("hint", ""))
                .andExpect(forwardedUrl("house/update"));
    }

    @Test
    public void deleteTest() throws Exception {
        when(houseFacade.findHouseById(1L)).thenReturn(houseDTO);

        this.mockMvc.perform(post("/house/delete/1"))
                .andExpect(redirectedUrl("http://localhost/house/list"));
    }
}
