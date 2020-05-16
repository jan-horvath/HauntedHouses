package cz.fi.muni.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import cz.muni.fi.pa165.hauntedhouses.model.Specter;
import cz.muni.fi.pa165.hauntedhouses.service.GameService;
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

import static org.mockito.Mockito.when;

/**
 * @author Jan Horvath
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class GameServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private HouseService houseService;

    @InjectMocks
    private GameService gameService;

    private List<House> houses;
    private House correctHouse;
    private House house1;
    private House house2;
    private House house3;

    private GameInstance gameInstance;
    private Specter specter;

    @Autowired
    public GameServiceTest(GameService gameService) {
        this.gameService = gameService;
    }

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createEntities() {
        createHouses();
        specter = new Specter();
        specter.setName("specter");
        specter.setHouse(correctHouse);

        gameInstance = new GameInstance();
        gameInstance.setBanishesAttempted(10);
        gameInstance.setBanishesRequired(5);

        gameInstance.setSpecter(specter);
        specter.setGameInstance(gameInstance);
    }

    private void createHouses() {
        correctHouse = new House();
        correctHouse.setName("correct");
        correctHouse.setAddress("correct address");

        house1 = new House();
        house1.setName("house1");
        house1.setAddress("house1 address");

        house2 = new House();
        house2.setName("house2");
        house2.setAddress("house2 address");

        house3 = new House();
        house3.setName("house3");
        house3.setAddress("house3 address");

        houses = new ArrayList<>();
        houses.add(house1);
        houses.add(house2);
        houses.add(house3);
    }

    @Test
    public void checkAnswerReturnsTrueTest() {
        when(houseService.getRandomHouse()).thenReturn(house2);

        Assert.assertTrue(gameService.checkAnswer(correctHouse, gameInstance));

        Assert.assertEquals(gameInstance.getBanishesAttempted(), 11);
        Assert.assertEquals(gameInstance.getBanishesRequired(), 4);
    }

    @Test
    public void checkAnswerReturnsFalseTest() {
        when(houseService.getAllHouses()).thenReturn(houses);

        Assert.assertFalse(gameService.checkAnswer(house2, gameInstance));

        Assert.assertEquals(gameInstance.getBanishesAttempted(), 11);
        Assert.assertEquals(gameInstance.getBanishesRequired(), 5);
        Assert.assertEquals(specter.getHouse(), correctHouse);
    }
}
