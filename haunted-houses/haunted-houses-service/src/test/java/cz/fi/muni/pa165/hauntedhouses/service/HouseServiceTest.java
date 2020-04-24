package cz.fi.muni.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author David Hofman
 */
@ContextConfiguration(classes= ServiceConfiguration.class)
public class HouseServiceTest extends AbstractTestNGSpringContextTests  {

    @Mock
    private HouseDao houseDao;

    @Autowired
    @InjectMocks
    private HouseService houseService;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    private House house1;
    private House house2;
    List<House> allHouses;

    @BeforeMethod
    public void prepareHouses() {
        house1 = new House();
        house1.setName("house1");
        house1.setAddress("adress1");

        house2 = new House();
        house2.setName("house2");
        house2.setAddress("adress2");

        allHouses = new ArrayList<>();
        allHouses.add(house1);
        allHouses.add(house2);
    }

    @Test
    public void getHouseByIdTest() {
        when(houseDao.getHouseById(1L)).thenReturn(house1);
        when(houseDao.getHouseById(2L)).thenReturn(house2);
        when(houseDao.getHouseById(3L)).thenReturn(null);

        Assert.assertEquals(houseService.getHouseById(1L),house1);
        Assert.assertEquals(houseService.getHouseById(2L),house2);
        Assert.assertNull(houseService.getHouseById(3L));
    }

    @Test
    public void getAllHousesTest() {
        when(houseDao.getAllHouses()).thenReturn(allHouses);
        Assert.assertEquals(houseService.getAllHouses(),allHouses);
    }

    @Test
    public void createHouseTest()
    {
        houseService.createHouse(house1);
        verify(houseDao,times(1)).createHouse(house1);
    }

    @Test
    public void deleteHouseTest()
    {
        houseService.deleteHouse(house1);
        verify(houseDao,times(1)).deleteHouse(house1);
    }

    @Test
    public void updateHouseTest()
    {
        when(houseDao.updateHouse(house1)).thenReturn(house1);
        Assert.assertEquals(houseService.updateHouse(house1),house1);
    }

}
