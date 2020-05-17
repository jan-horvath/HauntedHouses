package cz.fi.muni.pa165.hauntedhouses;

import cz.muni.fi.pa165.hauntedhouses.dto.*;
import cz.muni.fi.pa165.hauntedhouses.facade.GameInstanceFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.PlayerFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.SpecterFacade;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalTime;
import java.util.Collection;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class NoMockTest extends AbstractTestNGSpringContextTests {

    private GameInstanceFacade gameInstanceFacade;
    private PlayerFacade playerFacade;
    private HouseFacade houseFacade;
    private SpecterFacade specterFacade;

    @Autowired
    public NoMockTest(GameInstanceFacade gameInstanceFacade,
                      PlayerFacade playerFacade,
                      HouseFacade houseFacade,
                      SpecterFacade specterFacade) {
        this.gameInstanceFacade = gameInstanceFacade;
        this.playerFacade = playerFacade;
        this.houseFacade = houseFacade;
        this. specterFacade = specterFacade;
    }

    @Test
    public void deleteGameInstanceTest() {
        PlayerDTO player = new PlayerDTO("name", "email", true);
        playerFacade.registerPlayer(player, "pass");
        houseFacade.createHouse(new HouseCreateDTO("house1", "address1", "hint1"));

        GameInstanceCreateDTO gameInstanceCreateDTO = new GameInstanceCreateDTO();
        gameInstanceCreateDTO.setPlayer(player);
        gameInstanceCreateDTO.setBanishesRequired(999);

        //This also creates a randomized Specter and assigns it the House above
        gameInstanceFacade.createGameInstance(gameInstanceCreateDTO);
        GameInstanceDTO game = gameInstanceFacade.findGameInstanceByPlayerId(player.getId());
        Assert.assertNotNull(game);

        gameInstanceFacade.deleteGameInstance(game.getId());
        game = gameInstanceFacade.findGameInstanceByPlayerId(player.getId());
        Assert.assertNull(game);
    }

    @Test
    public void deleteSpecterTest() {
        SpecterDTO specterDTO = new SpecterDTO();
        GameInstanceDTO gameInstanceDTO = new GameInstanceDTO();
        PlayerDTO playerDTO = new PlayerDTO();

        playerDTO.setName("player name");
        playerDTO.setEmail("player email");
        playerDTO.setGameInstance(gameInstanceDTO);

        gameInstanceDTO.setPlayer(playerDTO);
        gameInstanceDTO.setSpecter(specterDTO);
        gameInstanceDTO.setBanishesRequired(555);

        specterDTO.setName("specter name");
        specterDTO.setDescription("specter description");
        specterDTO.setStartOfHaunting(LocalTime.of(10, 10));
        specterDTO.setEndOfHaunting(LocalTime.of(10, 11));
        specterDTO.setGameInstance(gameInstanceDTO);

        playerFacade.registerPlayer(playerDTO, "password");

        Collection<PlayerDTO> allPlayers = playerFacade.getAllPlayers();
        Assert.assertTrue(allPlayers.contains(playerDTO));

        PlayerDTO player_found = playerFacade.findPlayerByEmail("player email");
        GameInstanceDTO gameInstance_found = player_found.getGameInstance();
        SpecterDTO specter_found = gameInstance_found.getSpecter();

        Assert.assertEquals(gameInstance_found.getBanishesRequired(), 555);
        Assert.assertEquals(specter_found.getName(), "specter name");

        specterFacade.deleteSpecter(specter_found.getId());

        Assert.assertNull(specterFacade.findSpecterByGameInstanceId(gameInstance_found.getId()));
    }
}
