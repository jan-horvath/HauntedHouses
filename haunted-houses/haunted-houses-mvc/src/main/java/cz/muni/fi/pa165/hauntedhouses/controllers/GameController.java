package cz.muni.fi.pa165.hauntedhouses.controllers;

import cz.muni.fi.pa165.hauntedhouses.dto.*;
import cz.muni.fi.pa165.hauntedhouses.facade.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.*;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/game")
public class GameController {

    final static Logger log = LoggerFactory.getLogger(GameController.class);

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

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

    /**
     * Checks whether the currently logged user has a created game. If he does, he is redirected to /game/play,
     * otherwise he is redirected to /game/new
     * @param uriBuilder
     * @param principal
     * @return
     */
    @RequestMapping(value = "/check_game", method = RequestMethod.GET)
    public String toGame(UriComponentsBuilder uriBuilder,
                         Principal principal) {
        PlayerDTO foundPlayer = playerFacade.getPlayerByEmail(principal.getName());
        log.debug("\"/check_game\" called for user email {} (player found: {})", principal.getName(), foundPlayer != null);
        GameInstanceDTO gameInstance = gameInstanceFacade.getGameInstanceByPlayerId(foundPlayer.getId());

        if (gameInstance == null) {
            return "redirect:" + uriBuilder.path("/game/new").toUriString();
        }
        return "redirect:" + uriBuilder.path("/game/play").toUriString();
    }

    /**
     * Sets up view for the creation of a new game
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newGame(Model model, Principal principal) {
        PlayerDTO foundPlayer = playerFacade.getPlayerByEmail(principal.getName());
        log.debug("\"/new\" called for user email {} (player found: {})", principal.getName(), foundPlayer != null);
        model.addAttribute("createDTO", new GameInstanceCreateDTO());
        model.addAttribute("playerId", foundPlayer.getId());
        return "game/new";
    }

    /**
     * Creates a new game based on the logged user input. If the user input is incorrect, the user is sent back to /game/new
     * view. Otherwise a new game is created and the logged user is redirected to /game/check_game.
     * @param createDTO - contains information about the new game
     * @param redirectAttributes
     * @param uriBuilder
     * @param principal
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("createDTO") GameInstanceCreateDTO createDTO, RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder, Principal principal) {
        PlayerDTO foundPlayer = playerFacade.getPlayerByEmail(principal.getName());
        log.debug("\"/create\" called for user email {} (player found: {}, banishments: {})",
                principal.getName(), foundPlayer != null, createDTO.getBanishesRequired());

        createDTO.setPlayer(foundPlayer);
        Set<ConstraintViolation<@Valid GameInstanceCreateDTO>> violations =
                validatorFactory.getValidator().validate(createDTO);

        if (!violations.isEmpty()) {
            redirectAttributes.addFlashAttribute("alert_warning",
                    violations.iterator().next().getMessage());
            return "redirect:" + uriBuilder.path("/game/new").toUriString();
        }

        gameInstanceFacade.createGameInstance(createDTO);
        redirectAttributes.addFlashAttribute("alert_success",
                gameInstanceFacade.getGameInstanceByPlayerId(foundPlayer.getId()));
        return "redirect:" + uriBuilder.path("/game/check_game").toUriString();
    }

    /**
     * The function retrieves all the necessary information about the logged player's game. This information is used to
     * create the /game/game view.
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping(value = "/play", method = RequestMethod.GET)
    public String play(Model model, Principal principal) {
        PlayerDTO foundPlayer = playerFacade.getPlayerByEmail(principal.getName());
        log.debug("\"/play\" called for user email {} (player found: {})", principal.getName(), foundPlayer != null);
        GameInstanceDTO gameInstance = gameInstanceFacade.getGameInstanceByPlayerId(foundPlayer.getId());

        List<HouseDTO> allHouses = houseFacade.getAllHouses();
        SpecterDTO specter = gameInstance.getSpecter();

        model.addAttribute("allHouses", allHouses);
        model.addAttribute("specter", specter);
        model.addAttribute("abilities", specter.getAbilities());
        model.addAttribute("hint", specter.getHouse().getHint());
        model.addAttribute("game", gameInstance);

        return "game/game";
    }

    /**
     * The function processes the player's choice of house. The player is notified whether his/her choice was correct
     * and is redirected to /game/play. In case of winning, the player is redirected to the view /game/finished.
     * @param houseId - house chosen by the player
     * @param uriBuilder
     * @param redirectAttributes
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping(value = "/banish", method = RequestMethod.POST)
    public String banish(@RequestParam Long houseId, UriComponentsBuilder uriBuilder,
                           RedirectAttributes redirectAttributes, Model model, Principal principal) {
        PlayerDTO foundPlayer = playerFacade.getPlayerByEmail(principal.getName());
        log.debug("\"/banish\" called for user email {} (player found: {}, houseId: {})",
                principal.getName(), foundPlayer != null, houseId);
        BanishSpecterDTO banishSpecterDTO = new BanishSpecterDTO();
        banishSpecterDTO.setHouseId(houseId);
        GameInstanceDTO game = gameInstanceFacade.getGameInstanceByPlayerId(foundPlayer.getId());
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
