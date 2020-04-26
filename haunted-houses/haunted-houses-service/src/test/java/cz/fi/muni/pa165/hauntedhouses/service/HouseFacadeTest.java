package cz.fi.muni.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.hauntedhouses.service.facade.HouseFacadeImpl;

import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
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
public class HouseFacadeTest extends AbstractTestNGSpringContextTests {

    @InjectMocks
    private HouseFacade houseFacade = new HouseFacadeImpl();

    @Mock
    private HouseService houseService;

    @Mock
    private MappingService mappingService;

    private List<HouseDTO> housesDTO;
    private HouseCreateDTO houseCreateDTO;
    private HouseDTO houseDTO;
    private House house;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        reset(houseService);
        reset(mappingService);

        house = new House();
        house.setId(1L);
        house.setName("name");
        house.setAddress("address");
        house.setHauntedSince(LocalDate.EPOCH);
        house.setHistory("history");
        house.setHint("hint");

        houseDTO = new HouseDTO();
        houseDTO.setId(house.getId());
        houseDTO.setName(house.getName());
        houseDTO.setAddress(house.getAddress());
        houseDTO.setHauntedSince(house.getHauntedSince());
        houseDTO.setHistory(house.getHistory());
        houseDTO.setHint(house.getHint());

        houseCreateDTO = new HouseCreateDTO();
        houseCreateDTO.setName(house.getName());
        houseCreateDTO.setAddress(house.getAddress());
        houseCreateDTO.setHauntedSince(house.getHauntedSince());
        houseCreateDTO.setHistory(house.getHistory());
        houseCreateDTO.setHint(house.getHint());

        List<House> houses = new ArrayList<>();
        houses.add(house);
        houses.add(new House());

        housesDTO = new ArrayList<>();
        housesDTO.add(houseDTO);
        housesDTO.add(new HouseDTO());

        when(mappingService.mapTo(houseCreateDTO, House.class)).thenReturn(house);
        when(mappingService.mapTo(houseDTO, House.class)).thenReturn(house);
        when(mappingService.mapTo(house, HouseDTO.class)).thenReturn(houseDTO);
        when(mappingService.mapTo(houses, HouseDTO.class)).thenReturn(housesDTO);

        when(houseService.getHouseById(house.getId())).thenReturn(house);
        when(houseService.getAllHouses()).thenReturn(houses);
    }

    @Test
    public void findHouseByIdTest() {
        assertEquals(houseDTO, houseFacade.findHouseById(house.getId()));
        verify(houseService).getHouseById(house.getId());

        assertNull(houseFacade.findHouseById(2L));
    }

    @Test
    public void findAllHousesTest() {
        assertEquals(housesDTO, houseFacade.findAllHouses());
        verify(houseService).getAllHouses();
    }

    @Test
    public void createHouseTest() {
        assertEquals(house.getId(), houseFacade.createHouse(houseCreateDTO));
        verify(houseService).createHouse(house);
    }

    @Test
    public void deleteHouseTest() {
        houseFacade.deleteHouse(house.getId());
        verify(houseService).deleteHouse(house);
    }

    @Test
    public void updateHouseTest() {
        houseDTO.setName("new name");
        houseFacade.updateHouse(houseDTO);
        assertEquals(houseDTO, houseFacade.findHouseById(house.getId()));
        verify(houseService).updateHouse(house);
    }
}
