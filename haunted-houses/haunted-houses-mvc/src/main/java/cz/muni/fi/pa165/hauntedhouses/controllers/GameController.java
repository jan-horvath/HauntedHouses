package cz.muni.fi.pa165.hauntedhouses.controllers;

import cz.muni.fi.pa165.hauntedhouses.dto.*;
import cz.muni.fi.pa165.hauntedhouses.facade.GameFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.GameInstanceFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.PlayerFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
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

    @Autowired
    private GameFacade gameFacade;

    @RequestMapping(value = "/new")
    public String newGame(Model model, Principal principal) {
        PlayerDTO foundPlayer = playerFacade.findPlayerByEmail(principal.getName());
        log.debug("new game called (playerId = " + foundPlayer.getId() + ")");
        model.addAttribute("createDTO", new GameInstanceCreateDTO());
        model.addAttribute("playerId", foundPlayer.getId());
        return "game/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(
            @ModelAttribute("createDTO") GameInstanceCreateDTO createDTO,
            RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder,
            Principal principal) {
        PlayerDTO foundPlayer = playerFacade.findPlayerByEmail(principal.getName());
        log.debug("player identified (id = " + foundPlayer.getId() + ")");
        log.debug("create game called ({})", createDTO);
        if (createDTO.getBanishesRequired() <= 0) {
            redirectAttributes.addFlashAttribute("alert_warning", "Please enter a positive number");
            return "/game/new";
        }

        createDTO.setPlayer(playerFacade.findPlayerById(foundPlayer.getId()));
        gameInstanceFacade.createGameInstance(createDTO);
        log.debug("Players:" + playerFacade.getAllPlayers().toString());
        redirectAttributes.addFlashAttribute("alert_success", gameInstanceFacade.findGameInstanceByPlayerId(foundPlayer.getId()));
        return "redirect:" + uriBuilder.path("/game/check_game").toUriString();
    }

    @RequestMapping(value = "/check_game", method = RequestMethod.GET)
    public String toGame(UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes,
                         Principal principal) {
        PlayerDTO foundPlayer = playerFacade.findPlayerByEmail(principal.getName());
        log.debug("play game called (playerId = " + foundPlayer.getId() + ")");
        GameInstanceDTO gameInstance = gameInstanceFacade.findGameInstanceByPlayerId(foundPlayer.getId());
        if (gameInstance == null) {
            if (playerFacade.findPlayerById(foundPlayer.getId()) == null) {
                redirectAttributes.addFlashAttribute("alert_warning", "Player with id " + foundPlayer.getId() + " does not exist.");
                return "redirect:" + uriBuilder.path("/").toUriString();
            }
            return "/game/new";
        }

        return "redirect:" + uriBuilder.path("/game/play").toUriString();
    }

    @RequestMapping(value = "/play", method = RequestMethod.GET)
    public String play(Model model, Principal principal) {
        PlayerDTO foundPlayer = playerFacade.findPlayerByEmail(principal.getName());
        log.debug("play method called");
        GameInstanceDTO gameInstance = gameInstanceFacade.findGameInstanceByPlayerId(foundPlayer.getId());

        List<HouseDTO> allHouses = houseFacade.findAllHouses();
        SpecterDTO specter = gameInstance.getSpecter();

        log.debug("Specter = " + specter);
        log.debug("House = " + specter.getHouse());
        log.debug("Game = " + gameInstance);

        model.addAttribute("allHouses", allHouses);
        model.addAttribute("specter", specter);
        model.addAttribute("hint", specter.getHouse().getHint());
        model.addAttribute("game", gameInstance);

        model.addAttribute("playerId", foundPlayer.getId());

        return "game/game";
    }

    @RequestMapping(value = "/banish", method = RequestMethod.POST)
    public String banish(@RequestParam Long houseId, UriComponentsBuilder uriBuilder,
                           RedirectAttributes redirectAttributes, Model model, Principal principal) {
        PlayerDTO foundPlayer = playerFacade.findPlayerByEmail(principal.getName());
        BanishSpecterDTO banishSpecterDTO = new BanishSpecterDTO();
        banishSpecterDTO.setHouseId(houseId);
        GameInstanceDTO game = gameInstanceFacade.findGameInstanceByPlayerId(foundPlayer.getId());
        banishSpecterDTO.setGameInstanceId(game.getId());
        boolean success = gameFacade.banishSpecter(banishSpecterDTO);

        if (success) {
            if (game.getBanishesRequired() == 1) {
                model.addAttribute("banishments", game.getBanishesAttempted() + 1);
                model.addAttribute("playerId", foundPlayer.getId());
                gameInstanceFacade.deleteGameInstance(game.getId());
                return "game/finished";
            }
            redirectAttributes.addFlashAttribute("alert_success", "Correct guess!");
        } else {
            redirectAttributes.addFlashAttribute("alert_info", "Incorrect guess! Try a different house");
        }

        return "redirect:" + uriBuilder.path("/game/play").toUriString();
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Model model) {
        model.addAttribute("playerCount", playerFacade.getAllPlayers().size());
        model.addAttribute("houseCount", houseFacade.findAllHouses().size());
        GameInstanceDTO game = gameInstanceFacade.findGameInstanceByPlayerId(1L);
        if (game != null) {
            gameInstanceFacade.deleteGameInstance(game.getId());
            game = gameInstanceFacade.findGameInstanceByPlayerId(1L);

            model.addAttribute("game", game);
        }
        return "game/test";
    }
}
