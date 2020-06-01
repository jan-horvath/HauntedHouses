package cz.fi.muni.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dto.*;
import cz.muni.fi.pa165.hauntedhouses.model.*;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalTime;
import java.util.Calendar;

/**
 * @author Jan Horvath
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class MappingServiceTest extends AbstractTestNGSpringContextTests {

    private MappingService mappingService;

    @Autowired
    public MappingServiceTest(MappingService mappingService) {
        this.mappingService = mappingService;
    }

    @Test
    public void abilityCreateDTO_conversionTest() {
        AbilityCreateDTO abilityCreateDTO_A = new AbilityCreateDTO();
        abilityCreateDTO_A.setName("DTO name");
        abilityCreateDTO_A.setDescription("DTO description");

        Ability ability_A = mappingService.mapTo(abilityCreateDTO_A, Ability.class);
        Assert.assertNull(ability_A.getId());
        Assert.assertEquals(ability_A.getName(), "DTO name");
        Assert.assertEquals(ability_A.getDescription(), "DTO description");

        Ability ability_B = new Ability();
        ability_B.setId(1000L);
        ability_B.setName("entity name");
        ability_B.setDescription("entity description");

        AbilityCreateDTO abilityCreateDTO_B = mappingService.mapTo(ability_B, AbilityCreateDTO.class);
        Assert.assertEquals(abilityCreateDTO_B.getName(), "entity name");
        Assert.assertEquals(abilityCreateDTO_B.getDescription(), "entity description");
    }

    @Test
    public void abilityDTO_conversionTest() {
        Ability ability_A = new Ability();
        ability_A.setId(222L);
        ability_A.setName("entity name");
        ability_A.setDescription("entity description");

        AbilityDTO abilityDTO_A = mappingService.mapTo(ability_A, AbilityDTO.class);
        Assert.assertEquals(abilityDTO_A.getId(), Long.valueOf(222));
        Assert.assertEquals(abilityDTO_A.getName(), "entity name");
        Assert.assertEquals(abilityDTO_A.getDescription(), "entity description");

        AbilityDTO abilityDTO_B = new AbilityDTO();
        abilityDTO_B.setId(999L);
        abilityDTO_B.setName("DTO name");
        abilityDTO_B.setDescription("DTO description");

        Ability ability_B = mappingService.mapTo(abilityDTO_B, Ability.class);
        Assert.assertEquals(ability_B.getId(), Long.valueOf(999));
        Assert.assertEquals(ability_B.getName(), "DTO name");
        Assert.assertEquals(ability_B.getDescription(), "DTO description");
    }

    @Test
    public void gameInstanceDTO_conversionTest() {
        GameInstanceDTO gameInstanceDTO_A = new GameInstanceDTO();
        gameInstanceDTO_A.setId(500L);
        gameInstanceDTO_A.setBanishesAttempted(10);
        gameInstanceDTO_A.setBanishesRequired(2);
        gameInstanceDTO_A.setSpecter(null);
        gameInstanceDTO_A.setPlayer(null);

        GameInstance gameInstance = mappingService.mapTo(gameInstanceDTO_A, GameInstance.class);
        Assert.assertEquals(gameInstance.getId(), Long.valueOf(500));
        Assert.assertEquals(gameInstance.getBanishesRequired(), 2);
        Assert.assertEquals(gameInstance.getBanishesAttempted(), 10);
        Assert.assertNull(gameInstance.getSpecter());
        Assert.assertNull(gameInstance.getPlayer());

        GameInstanceDTO gameInstanceDTO_B = mappingService.mapTo(gameInstance, GameInstanceDTO.class);
        Assert.assertEquals(gameInstanceDTO_B.getId(), Long.valueOf(500));
        Assert.assertEquals(gameInstanceDTO_B.getBanishesAttempted(), 10);
        Assert.assertEquals(gameInstanceDTO_B.getBanishesRequired(), 2);
        Assert.assertNull(gameInstanceDTO_B.getPlayer());
        Assert.assertNull(gameInstanceDTO_B.getSpecter());
    }

    @Test
    public void GameInstanceCreateDTO_conversionTest() {
        GameInstanceCreateDTO gameInstanceCreateDTO_A = new GameInstanceCreateDTO();
        gameInstanceCreateDTO_A.setBanishesRequired(2);
        gameInstanceCreateDTO_A.setPlayer(null);
        gameInstanceCreateDTO_A.setSpecter(null);

        GameInstance gameInstance = mappingService.mapTo(gameInstanceCreateDTO_A, GameInstance.class);
        Assert.assertNull(gameInstance.getId());
        Assert.assertEquals(gameInstance.getBanishesRequired(), 2);
        Assert.assertEquals(gameInstance.getBanishesAttempted(), 10);
        Assert.assertNull(gameInstance.getSpecter());
        Assert.assertNull(gameInstance.getPlayer());

        GameInstanceCreateDTO gameInstanceCreateDTO_B = mappingService.mapTo(gameInstance, GameInstanceCreateDTO.class);
        Assert.assertEquals(gameInstanceCreateDTO_B.getBanishesRequired(), 2);
        Assert.assertNull(gameInstanceCreateDTO_B.getPlayer());
        Assert.assertNull(gameInstanceCreateDTO_B.getSpecter());
    }

    @Test
    public void HouseCreateDTO_conversionTest() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1990);
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 10);

        HouseCreateDTO houseCreateDTO_A = new HouseCreateDTO();
        houseCreateDTO_A.setName("name");
        houseCreateDTO_A.setAddress("address");
        houseCreateDTO_A.setHauntedSince(cal.getTime());
        houseCreateDTO_A.setHint("hint");
        houseCreateDTO_A.setHistory("history");

        House house = mappingService.mapTo(houseCreateDTO_A, House.class);
        Assert.assertNull(house.getId());
        Assert.assertEquals(house.getName(), "name");
        Assert.assertEquals(house.getAddress(), "address");
        Assert.assertEquals(house.getHauntedSince(), cal.getTime());
        Assert.assertEquals(house.getHint(), "hint");
        Assert.assertEquals(house.getHistory(), "history");

        HouseCreateDTO houseCreateDTO_B = mappingService.mapTo(house, HouseCreateDTO.class);
        Assert.assertEquals(houseCreateDTO_B.getName(), "name");
        Assert.assertEquals(houseCreateDTO_B.getAddress(), "address");
        Assert.assertEquals(houseCreateDTO_B.getHauntedSince(), cal.getTime());
        Assert.assertEquals(houseCreateDTO_B.getHint(), "hint");
        Assert.assertEquals(houseCreateDTO_B.getHistory(), "history");
    }

    @Test
    public void houseDTO_conversionTest() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1990);
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 10);

        HouseDTO houseDTO_A = new HouseDTO();
        houseDTO_A.setId(888L);
        houseDTO_A.setName("name");
        houseDTO_A.setAddress("address");
        houseDTO_A.setHauntedSince(cal.getTime());
        houseDTO_A.setHint("hint");
        houseDTO_A.setHistory("history");

        House house = mappingService.mapTo(houseDTO_A, House.class);
        Assert.assertEquals(house.getId(), Long.valueOf(888));
        Assert.assertEquals(house.getName(), "name");
        Assert.assertEquals(house.getAddress(), "address");
        Assert.assertEquals(house.getHauntedSince(), cal.getTime());
        Assert.assertEquals(house.getHint(), "hint");
        Assert.assertEquals(house.getHistory(), "history");

        HouseDTO houseDTO_B = mappingService.mapTo(house, HouseDTO.class);
        Assert.assertEquals(houseDTO_B.getId(), Long.valueOf(888));
        Assert.assertEquals(houseDTO_B.getName(), "name");
        Assert.assertEquals(houseDTO_B.getAddress(), "address");
        Assert.assertEquals(houseDTO_B.getHauntedSince(), cal.getTime());
        Assert.assertEquals(houseDTO_B.getHint(), "hint");
        Assert.assertEquals(houseDTO_B.getHistory(), "history");
    }

    @Test
    public void playerDTO_conversionTest() {
        PlayerDTO playerDTO_A = new PlayerDTO();
        playerDTO_A.setId(500L);
        playerDTO_A.setName("name");
        playerDTO_A.setEmail("email");

        Player player = mappingService.mapTo(playerDTO_A, Player.class);
        Assert.assertEquals(player.getId(), Long.valueOf(500));
        Assert.assertEquals(player.getName(), "name");
        Assert.assertEquals(player.getEmail(), "email");
        Assert.assertNull(player.getGameInstance());

        PlayerDTO playerDTO_B = mappingService.mapTo(player, PlayerDTO.class);
        Assert.assertEquals(playerDTO_B.getId(), Long.valueOf(500));
        Assert.assertEquals(playerDTO_B.getName(), "name");
        Assert.assertEquals(playerDTO_B.getEmail(), "email");
    }

    @Test
    public void specterCreateDTO_conversionTest() {
        SpecterCreateDTO specterCreateDTO_A = new SpecterCreateDTO();
        specterCreateDTO_A.setName("name");
        specterCreateDTO_A.setDescription("description");
        specterCreateDTO_A.setStartOfHaunting(LocalTime.of(22, 0));
        specterCreateDTO_A.setEndOfHaunting(LocalTime.of(1, 30));
        specterCreateDTO_A.setAbilities(null);
        specterCreateDTO_A.setGameInstance(null);
        specterCreateDTO_A.setHouse(null);

        Specter specter = mappingService.mapTo(specterCreateDTO_A, Specter.class);
        Assert.assertEquals(specter.getName(), "name");
        Assert.assertEquals(specter.getDescription(), "description");
        Assert.assertEquals(specter.getStartOfHaunting(), LocalTime.of(22, 0));
        Assert.assertEquals(specter.getEndOfHaunting(), LocalTime.of(1, 30));
        Assert.assertNull(specter.getAbilities());
        Assert.assertNull(specter.getHouse());
        Assert.assertNull(specter.getGameInstance());
        Assert.assertNull(specter.getId());

        SpecterCreateDTO specterCreateDTO_B = mappingService.mapTo(specter, SpecterCreateDTO.class);
        Assert.assertEquals(specterCreateDTO_B.getName(), "name");
        Assert.assertEquals(specterCreateDTO_B.getDescription(), "description");
        Assert.assertEquals(specterCreateDTO_B.getStartOfHaunting(), LocalTime.of(22, 0));
        Assert.assertEquals(specterCreateDTO_B.getEndOfHaunting(), LocalTime.of(1, 30));
        Assert.assertNull(specterCreateDTO_B.getAbilities());
        Assert.assertNull(specterCreateDTO_B.getHouse());
        Assert.assertNull(specterCreateDTO_B.getGameInstance());
    }

    @Test
    public void specterDTO_conversionTest() {
        SpecterDTO specterDTO_A = new SpecterDTO();
        specterDTO_A.setName("name");
        specterDTO_A.setDescription("description");
        specterDTO_A.setStartOfHaunting(LocalTime.of(22, 0));
        specterDTO_A.setEndOfHaunting(LocalTime.of(1, 30));
        specterDTO_A.setAbilities(null);
        specterDTO_A.setGameInstance(null);
        specterDTO_A.setHouse(null);

        Specter specter = mappingService.mapTo(specterDTO_A, Specter.class);
        Assert.assertEquals(specter.getName(), "name");
        Assert.assertEquals(specter.getDescription(), "description");
        Assert.assertEquals(specter.getStartOfHaunting(), LocalTime.of(22, 0));
        Assert.assertEquals(specter.getEndOfHaunting(), LocalTime.of(1, 30));
        Assert.assertNull(specter.getAbilities());
        Assert.assertNull(specter.getHouse());
        Assert.assertNull(specter.getGameInstance());
        Assert.assertNull(specter.getId());

        SpecterDTO specterDTO_B = mappingService.mapTo(specter, SpecterDTO.class);
        Assert.assertEquals(specterDTO_B.getName(), "name");
        Assert.assertEquals(specterDTO_B.getDescription(), "description");
        Assert.assertEquals(specterDTO_B.getStartOfHaunting(), LocalTime.of(22, 0));
        Assert.assertEquals(specterDTO_B.getEndOfHaunting(), LocalTime.of(1, 30));
        Assert.assertNull(specterDTO_B.getAbilities());
        Assert.assertNull(specterDTO_B.getHouse());
        Assert.assertNull(specterDTO_B.getGameInstance());
    }
}
