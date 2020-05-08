package cz.fi.muni.pa165.hauntedhouses;

import cz.muni.fi.pa165.hauntedhouses.dto.GameInstanceCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.GameInstanceDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.PlayerDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.GameInstanceFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.PlayerFacade;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class GameInstanceNoMockTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private GameInstanceFacade gameInstanceFacade;

    @Autowired
    private PlayerFacade playerFacade;

    @Autowired
    private HouseFacade houseFacade;

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
}
