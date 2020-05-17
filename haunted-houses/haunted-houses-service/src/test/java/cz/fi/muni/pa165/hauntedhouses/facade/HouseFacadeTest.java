package cz.fi.muni.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;

import org.hibernate.service.spi.ServiceException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Calendar;
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

    @Mock
    private MappingService mappingService;

    @Mock
    private HouseService houseService;

    private HouseFacade houseFacade;

    private List<HouseDTO> housesDTO;
    private HouseCreateDTO houseCreateDTO;
    private HouseDTO houseDTO;
    private House house;

    @Autowired
    public HouseFacadeTest(HouseFacade houseFacade) {
        this.houseFacade = houseFacade;
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        reset(houseService);
        reset(mappingService);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1990);
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 10);

        house = new House();
        house.setId(1L);
        house.setName("name");
        house.setAddress("address");
        house.setHauntedSince(cal.getTime());
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
