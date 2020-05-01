package cz.muni.fi.pa165.hauntedhouses;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import cz.muni.fi.pa165.hauntedhouses.rest.controllers.ExceptionController;
import cz.muni.fi.pa165.hauntedhouses.rest.controllers.HouseController;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Method;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

/**
 * @author Petr Vitovsky
 */
@WebAppConfiguration
@ContextConfiguration(classes = {RootWebContext.class})
public class HouseControllerTest extends AbstractTestNGSpringContextTests {

    @Mock
    private HouseFacade houseFacade;

    @Autowired
    @InjectMocks
    private HouseController houseController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private HouseDTO houseOne;
    private HouseDTO houseTwo;
    private List<HouseDTO> houses;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(houseController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @BeforeMethod
    public void init() {
        houseOne = new HouseDTO();
        houseOne.setId(1L);
        houseOne.setAddress("addressOne");
        houseOne.setHint("hintOne");
        houseOne.setName("nameOne");

        houseTwo = new HouseDTO();
        houseTwo.setId(2L);
        houseTwo.setAddress("addressTwo");
        houseTwo.setHint("hintTwo");
        houseTwo.setName("nameTwo");

        houses = new ArrayList<>();
        houses.add(houseOne);
        houses.add(houseTwo);
    }

    private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(ExceptionController.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new ExceptionController(), method);
            }
        };
        exceptionResolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

    @Test
    public void getAllHouses() throws Exception {

        doReturn(Collections.unmodifiableList(houses)).when(
                houseFacade).findAllHouses();

        mockMvc.perform(get("/house"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[?(@.id==1)].name").value("nameOne"))
                .andExpect(jsonPath("$.[?(@.id==1)].address").value("addressOne"))
                .andExpect(jsonPath("$.[?(@.id==1)].hint").value("hintOne"))
                .andExpect(jsonPath("$.[?(@.id==2)].name").value("nameTwo"))
                .andExpect(jsonPath("$.[?(@.id==2)].address").value("addressTwo"))
                .andExpect(jsonPath("$.[?(@.id==2)].hint").value("hintTwo"));
    }

    @Test
    public void getHouse() throws Exception {
        doReturn(houseOne).when(houseFacade).findHouseById(1L);
        doReturn(houseTwo).when(houseFacade).findHouseById(2L);

        mockMvc.perform(get("/house/1"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("nameOne"))
                .andExpect(jsonPath("$.address").value("addressOne"))
                .andExpect(jsonPath("$.hint").value("hintOne"));

        mockMvc.perform(get("/house/2"))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("nameTwo"))
                .andExpect(jsonPath("$.address").value("addressTwo"))
                .andExpect(jsonPath("$.hint").value("hintTwo"));
    }

    @Test
    public void getNonexistingHouse() throws Exception {
        doReturn(null).when(houseFacade).findHouseById(1L);

        mockMvc.perform(get("/house/1")).andExpect(
                status().is4xxClientError());
    }

    @Test
    public void createHouse() throws Exception {
        HouseCreateDTO houseCreateDTO = new HouseCreateDTO();
        houseCreateDTO.setAddress("addressOne");
        houseCreateDTO.setHint("hintOne");
        houseCreateDTO.setName("nameOne");

        doReturn(1L).when(houseFacade).createHouse(
                any(HouseCreateDTO.class));

        String json = this.convertObjectToJsonBytes(houseCreateDTO);

        mockMvc.perform(
                post("/house/create").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        verify(houseFacade).createHouse(houseCreateDTO);
    }

    @Test
    public void createInvalidHouse() throws Exception {
        HouseCreateDTO houseCreateDTO = new HouseCreateDTO();

        doReturn(1L).when(houseFacade).createHouse(
                any(HouseCreateDTO.class));

        String json = this.convertObjectToJsonBytes(houseCreateDTO);

        mockMvc.perform(post("/house/create")).andExpect(
                status().is4xxClientError());
    }

    @Test
    public void updateHouse() throws Exception {
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setId(1L);
        houseDTO.setAddress("addressOne");
        houseDTO.setHint("hintOne");
        houseDTO.setName("nameOne");

        String json = this.convertObjectToJsonBytes(houseDTO);

        mockMvc.perform(
                post("/house/update").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        verify(houseFacade).updateHouse(houseDTO);
    }

    @Test
    public void updateInvalidHouse() throws Exception {
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setId(1L);
        houseDTO.setAddress(null);
        houseDTO.setHint("hintOne");
        houseDTO.setName("nameOne");

        String json = this.convertObjectToJsonBytes(houseDTO);

        doThrow(new RuntimeException("invalid parameter")).when(houseFacade).updateHouse(houseDTO);

        mockMvc.perform(post("/house/update").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void deleteHouse() throws Exception {
        mockMvc.perform(delete("/house/1"))
                .andExpect(status().isOk());

        verify(houseFacade).deleteHouse(1L);
    }

    @Test
    public void deleteNonexistingHouse() throws Exception {
        doThrow(new RuntimeException("the house does not exist")).when(houseFacade).deleteHouse(1L);

        mockMvc.perform(delete("/house/1"))
                .andExpect(status().is4xxClientError());
    }

    private static String convertObjectToJsonBytes(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }
}
