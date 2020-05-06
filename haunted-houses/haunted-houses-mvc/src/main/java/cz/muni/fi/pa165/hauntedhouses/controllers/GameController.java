package cz.muni.fi.pa165.hauntedhouses.controllers;

import cz.muni.fi.pa165.hauntedhouses.dto.*;
import cz.muni.fi.pa165.hauntedhouses.facade.GameInstanceFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.PlayerFacade;
import org.hibernate.mapping.IdentifierBag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameController {

    final static Logger log = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameInstanceFacade gameInstanceFacade;

    @Autowired
    private PlayerFacade playerFacade;

    @Autowired
    private HouseFacade houseFacade;

    @RequestMapping(value = "/new")
    public String newGame(@RequestParam long playerid, Model model) {
        log.debug("new game called (playerId = " + playerid + ")");
        model.addAttribute("createDTO", new GameInstanceCreateDTO());
        model.addAttribute("playerid", playerid);
        return "game/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(
            @RequestParam long playerid,
            @ModelAttribute("createDTO") GameInstanceCreateDTO createDTO,
            RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("create game called ({})", createDTO);
        createDTO.setPlayer(playerFacade.findPlayerById(playerid));
        gameInstanceFacade.createGameInstance(createDTO);
        log.debug("Players:" + playerFacade.getAllPlayers().toString());
        redirectAttributes.addFlashAttribute("alert_success", gameInstanceFacade.findGameInstanceByPlayerId(playerid));
        return "redirect:" + uriBuilder.path("/game/check_game").queryParam("playerid", playerid).toUriString();
    }

    @RequestMapping(value = "/check_game", method = RequestMethod.GET)
    public String toGame(@RequestParam long playerid, UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes) {
        log.debug("play game called (playerId = " + playerid + ")");

        GameInstanceDTO gameInstance = gameInstanceFacade.findGameInstanceByPlayerId(playerid);
        if (gameInstance == null) {
            if (playerFacade.findPlayerById(playerid) == null) {
                redirectAttributes.addFlashAttribute("alert_warning", "Player with id " + playerid + " does not exist.");
                return "redirect:" + uriBuilder.path("/").toUriString();
            }
            String url = uriBuilder.path("/game/new").queryParam("playerid", playerid).encode().toUriString();
            return "redirect:" + url;
        }

        String uri = uriBuilder.path("/game/play/{playerId}").buildAndExpand(playerid).encode().toUriString();
        log.debug("Redirect:" + uri);
        return "redirect:" + uri;
    }

    @RequestMapping(value = "/play/{playerid}", method = RequestMethod.GET)
    public String play(@PathVariable Long playerid, Model model) {
        log.debug("play method called");
        GameInstanceDTO gameInstance = gameInstanceFacade.findGameInstanceByPlayerId(playerid);

        List<HouseDTO> allHouses = houseFacade.findAllHouses();
        SpecterDTO specter = gameInstance.getSpecter();

        log.debug("Specter = " + specter);
        log.debug("House = " + specter.getHouse());
        log.debug("Game = " + gameInstance);

        model.addAttribute("allHouses", allHouses);
        model.addAttribute("specter", specter);
        model.addAttribute("hint", specter.getHouse().getHint());
        model.addAttribute("game", gameInstance);

        model.addAttribute("playerId", playerid);

        return "game/game";
    }

    @RequestMapping(value = "/banish", method = RequestMethod.POST)
    public String banish(@RequestParam String playerId, @RequestParam Long houseId, UriComponentsBuilder uriBuilder,
                           RedirectAttributes redirectAttributes) {
        HouseDTO guessedHouse = houseFacade.findHouseById(houseId);
        redirectAttributes.addFlashAttribute("alert_info", "You have chosen "
                + guessedHouse.getName() + " (ID=" + guessedHouse.getId() + ")");
        return "redirect:" + uriBuilder.path("/game/play/{playerId}").buildAndExpand(playerId).toUriString();
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Model model) {
        model.addAttribute("playerCount", playerFacade.getAllPlayers().size());
        model.addAttribute("houseCount", houseFacade.findAllHouses().size());
        return "game/test";
    }

    @RequestMapping(value = "/populate", method = RequestMethod.GET)
    public String populate(UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        playerFacade.registerPlayer(new PlayerDTO("player name", "player email", true), "password");

        houseFacade.createHouse(new HouseCreateDTO("house1", "address1", "hint1"));
        houseFacade.createHouse(new HouseCreateDTO("house2", "address2", "hint2"));
        houseFacade.createHouse(new HouseCreateDTO("house3", "address3", "hint3"));

        redirectAttributes.addFlashAttribute("alert_success", "Database populated");
        return "redirect:" + uriBuilder.path("/").toUriString();
    }
}
