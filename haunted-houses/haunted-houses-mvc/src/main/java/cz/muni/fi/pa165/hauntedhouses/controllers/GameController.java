package cz.muni.fi.pa165.hauntedhouses.controllers;

import cz.muni.fi.pa165.hauntedhouses.dto.*;
import cz.muni.fi.pa165.hauntedhouses.facade.*;
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

    private GameInstanceFacade gameInstanceFacade;

    private PlayerFacade playerFacade;

    private HouseFacade houseFacade;

    private GameFacade gameFacade;

    @Autowired
    public GameController(GameInstanceFacade gameInstanceFacade, PlayerFacade playerFacade, HouseFacade houseFacade, GameFacade gameFacade) {
        this.gameInstanceFacade = gameInstanceFacade;
        this.playerFacade = playerFacade;
        this.houseFacade = houseFacade;
        this.gameFacade = gameFacade;
    }

    @RequestMapping(value = "/check_game", method = RequestMethod.GET)
    public String toGame(UriComponentsBuilder uriBuilder,
                         Principal principal) {
        PlayerDTO foundPlayer = playerFacade.findPlayerByEmail(principal.getName());
        log.debug("\"/check_game\" called for user email {} (player found: {})", principal.getName(), foundPlayer != null);
        GameInstanceDTO gameInstance = gameInstanceFacade.findGameInstanceByPlayerId(foundPlayer.getId());

        if (gameInstance == null) {
            return "redirect:" + uriBuilder.path("/game/new").toUriString();
        }
        return "redirect:" + uriBuilder.path("/game/play").toUriString();
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newGame(Model model, Principal principal) {
        PlayerDTO foundPlayer = playerFacade.findPlayerByEmail(principal.getName());
        log.debug("\"/new\" called for user email {} (player found: {})", principal.getName(), foundPlayer != null);
        model.addAttribute("createDTO", new GameInstanceCreateDTO());
        model.addAttribute("playerId", foundPlayer.getId());
        return "game/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("createDTO") GameInstanceCreateDTO createDTO,
                         RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, Principal principal) {
        PlayerDTO foundPlayer = playerFacade.findPlayerByEmail(principal.getName());
        log.debug("\"/create\" called for user email {} (player found: {}, banishments: {})",
                principal.getName(), foundPlayer != null, createDTO.getBanishesRequired());
        if (createDTO.getBanishesRequired() <= 0) {
            redirectAttributes.addFlashAttribute("alert_warning", "Please enter a positive number");
            return "/game/new";
        }

        createDTO.setPlayer(foundPlayer);
        gameInstanceFacade.createGameInstance(createDTO);
        redirectAttributes.addFlashAttribute("alert_success",
                gameInstanceFacade.findGameInstanceByPlayerId(foundPlayer.getId()));
        return "redirect:" + uriBuilder.path("/game/check_game").toUriString();
    }

    @RequestMapping(value = "/play", method = RequestMethod.GET)
    public String play(Model model, Principal principal) {
        PlayerDTO foundPlayer = playerFacade.findPlayerByEmail(principal.getName());
        log.debug("\"/play\" called for user email {} (player found: {})", principal.getName(), foundPlayer != null);
        GameInstanceDTO gameInstance = gameInstanceFacade.findGameInstanceByPlayerId(foundPlayer.getId());

        List<HouseDTO> allHouses = houseFacade.findAllHouses();
        SpecterDTO specter = gameInstance.getSpecter();

        model.addAttribute("allHouses", allHouses);
        model.addAttribute("specter", specter);
        model.addAttribute("abilities", specter.getAbilities());
        model.addAttribute("hint", specter.getHouse().getHint());
        model.addAttribute("game", gameInstance);

        return "game/game";
    }

    @RequestMapping(value = "/banish", method = RequestMethod.POST)
    public String banish(@RequestParam Long houseId, UriComponentsBuilder uriBuilder,
                           RedirectAttributes redirectAttributes, Model model, Principal principal) {
        PlayerDTO foundPlayer = playerFacade.findPlayerByEmail(principal.getName());
        log.debug("\"/banish\" called for user email {} (player found: {}, houseId: {})",
                principal.getName(), foundPlayer != null, houseId);
        BanishSpecterDTO banishSpecterDTO = new BanishSpecterDTO();
        banishSpecterDTO.setHouseId(houseId);
        GameInstanceDTO game = gameInstanceFacade.findGameInstanceByPlayerId(foundPlayer.getId());
        banishSpecterDTO.setGameInstanceId(game.getId());
        boolean success = gameFacade.banishSpecter(banishSpecterDTO);

        if (success) {
            if (game.getBanishesRequired() == 1) {
                model.addAttribute("banishments", game.getBanishesAttempted() + 1);
                gameInstanceFacade.deleteGameInstance(game.getId());
                return "game/finished";
            }
            redirectAttributes.addFlashAttribute("alert_success", "Correct guess!");
        } else {
            redirectAttributes.addFlashAttribute("alert_info", "Incorrect guess! Try a different house");
        }

        return "redirect:" + uriBuilder.path("/game/play").toUriString();
    }
}
