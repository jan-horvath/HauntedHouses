package cz.muni.fi.pa165.mvc.controllers;

import cz.muni.fi.pa165.hauntedhouses.controllers.GameController;
import cz.muni.fi.pa165.hauntedhouses.dto.*;
import cz.muni.fi.pa165.hauntedhouses.facade.GameFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.GameInstanceFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import cz.muni.fi.pa165.hauntedhouses.facade.PlayerFacade;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.security.Principal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
public class GameControllerTest {

    private GameController gameController;
    private MockMvc mockMvc;

    @Mock
    GameInstanceFacade gameInstanceFacade;

    @Mock
    PlayerFacade playerFacade;

    @Mock
    HouseFacade houseFacade;

    @Mock
    GameFacade gameFacade;

    private String playerWithGameEmail, playerWithoutGameEmail;
    private Long playerWithGameId, playerWithoutGameId, newGameInstanceId;
    private PlayerDTO playerWithGame, playerWithoutGame;
    private GameInstanceDTO gameInstanceDTO;
    private GameInstanceCreateDTO gameInstanceCreateDTO;
    private SpecterDTO specterDTO;
    private List<AbilityDTO> abilities;
    private List<HouseDTO> houses;
    private HouseDTO house1;
    private BanishSpecterDTO successfulBanish, failedBanish;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        gameController = new GameController(gameInstanceFacade, playerFacade, houseFacade, gameFacade);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @BeforeMethod
    public void setup() {
        createObjects();
        setupMocks();
    }

    private void createObjects() {
        playerWithGameEmail = "playerWithGameEmail";
        playerWithoutGameEmail = "playerWithoutGameEmail";
        playerWithGameId = 50L;
        playerWithoutGameId = 100L;
        newGameInstanceId = 22L;

        setupPlayersAndGames();
        setupHouses();
        setupSpecterWithAbilities();
        setupBanishments();
    }

    private void setupMocks() {
        when(playerFacade.getPlayerByEmail(playerWithGameEmail)).thenReturn(playerWithGame);
        when(playerFacade.getPlayerByEmail(playerWithoutGameEmail)).thenReturn(playerWithoutGame);
        when(gameInstanceFacade.getGameInstanceByPlayerId(playerWithGameId)).thenReturn(gameInstanceDTO);
        when(gameInstanceFacade.getGameInstanceByPlayerId(playerWithoutGameId)).thenReturn(null);
        when(houseFacade.getAllHouses()).thenReturn(houses);
        when(gameFacade.banishSpecter(successfulBanish)).thenReturn(true);
        when(gameFacade.banishSpecter(failedBanish)).thenReturn(false);
    }

    private void setupPlayersAndGames() {
        playerWithGame = new PlayerDTO("playerWithGameName", playerWithGameEmail, false);
        playerWithGame.setId(playerWithGameId);
        playerWithoutGame = new PlayerDTO("playerWithGameName", playerWithoutGameEmail, false);
        playerWithoutGame.setId(playerWithoutGameId);
        gameInstanceDTO = new GameInstanceDTO();
        gameInstanceDTO.setId(newGameInstanceId);
        gameInstanceDTO.setPlayer(playerWithGame);
        gameInstanceDTO.setBanishesRequired(5);
        gameInstanceDTO.setBanishesAttempted(3);
        gameInstanceCreateDTO = new GameInstanceCreateDTO();
        gameInstanceCreateDTO.setBanishesRequired(10);
        gameInstanceCreateDTO.setBanishesAttempted(0);
    }

    private void setupSpecterWithAbilities() {
        AbilityDTO ability1 = new AbilityDTO();
        ability1.setName("ability1");
        ability1.setDescription("description1");
        AbilityDTO ability2 = new AbilityDTO();
        ability2.setName("ability2");
        ability2.setDescription("description2");

        abilities = new ArrayList<>();
        abilities.add(ability1);
        abilities.add(ability2);

        specterDTO = new SpecterDTO();
        specterDTO.setName("specter name");
        specterDTO.setStartOfHaunting(LocalTime.of(5, 5));
        specterDTO.setEndOfHaunting(LocalTime.of(6, 6));
        specterDTO.setDescription("specter description");
        specterDTO.setAbilities(abilities);
        specterDTO.setGameInstance(gameInstanceDTO);
        gameInstanceDTO.setSpecter(specterDTO);
        specterDTO.setHouse(house1);
    }

    private void setupHouses() {
        houses = new ArrayList<>();
        house1 = new HouseDTO();
        house1.setId(1L);
        house1.setAddress("address1");
        house1.setHint("hint1");
        HouseDTO house2 = new HouseDTO();
        house2.setId(2L);
        house2.setAddress("address2");
        HouseDTO house3 = new HouseDTO();
        house3.setId(3L);
        house3.setAddress("address3");
        houses.add(house1);
        houses.add(house2);
        houses.add(house3);
    }

    private void setupBanishments() {
        successfulBanish = new BanishSpecterDTO();
        successfulBanish.setGameInstanceId(newGameInstanceId);
        successfulBanish.setHouseId(house1.getId());

        failedBanish = new BanishSpecterDTO();
        failedBanish.setGameInstanceId(newGameInstanceId);
        failedBanish.setHouseId(2L);
    }


    @Test
    public void checkExistingGameTest() throws Exception {
        this.mockMvc.perform(get("/game/check_game")
                .principal(new PrincipalImpl(playerWithGameEmail))
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/game/play"));
    }

    @Test
    public void checkNonexistingGameTest() throws Exception {
        this.mockMvc.perform(get("/game/check_game")
                .principal(new PrincipalImpl(playerWithoutGameEmail))
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/game/new"));
    }

    @Test
    public void newGameTest() throws Exception {
        this.mockMvc.perform(get("/game/new")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8"))
                .principal(new PrincipalImpl(playerWithoutGameEmail)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("createDTO"))
                .andExpect(model().attribute("playerId", playerWithoutGameId))
                .andExpect(forwardedUrl("game/new"));
    }

    @Test
    public void createGameTest() throws Exception {
        this.mockMvc.perform(post("/game/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("createDTO", gameInstanceCreateDTO)
                .principal(new PrincipalImpl(playerWithoutGameEmail)))
                .andExpect(redirectedUrl("http://localhost/game/check_game"));

        Assert.assertEquals(gameInstanceCreateDTO.getPlayer(), playerWithoutGame);
        verify(gameInstanceFacade, times(1)).createGameInstance(gameInstanceCreateDTO);
    }

    @Test
    public void playGameTest() throws Exception {
        this.mockMvc.perform(get("/game/play")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8"))
                .principal(new PrincipalImpl(playerWithGameEmail)))
                .andExpect(model().attribute("allHouses", houses))
                .andExpect(model().attribute("specter", specterDTO))
                .andExpect(model().attribute("abilities", abilities))
                .andExpect(model().attribute("hint", house1.getHint()))
                .andExpect(model().attribute("game", gameInstanceDTO))
                .andExpect(forwardedUrl("game/game"));
    }

    @Test
    public void successfulBanishTest() throws Exception {
        this.mockMvc.perform(post("/game/banish")
                .principal(new PrincipalImpl(playerWithGameEmail))
                .param("houseId", "1"))
                .andExpect(redirectedUrl("http://localhost/game/play"));
        verify(gameFacade, times(1)).banishSpecter(successfulBanish);
        reset(gameFacade);
    }

    @Test
    public void failedBanishTest() throws Exception {
        this.mockMvc.perform(post("/game/banish")
                .principal(new PrincipalImpl(playerWithGameEmail))
                .param("houseId", "2"))
                .andExpect(redirectedUrl("http://localhost/game/play"));
        verify(gameFacade, times(1)).banishSpecter(failedBanish);
        reset(gameFacade);
    }

    @Test
    public void gameEndingBanishTest() throws Exception {
        gameInstanceDTO.setBanishesRequired(1);
        this.mockMvc.perform(post("/game/banish")
                .principal(new PrincipalImpl(playerWithGameEmail))
                .param("houseId", "1"))
                .andExpect(model().attribute("banishments", 4))
                .andExpect(forwardedUrl("game/finished"));
        verify(gameFacade, times(1)).banishSpecter(successfulBanish);
        reset(gameFacade);
    }

    private class PrincipalImpl implements Principal {

        private String name;
        public PrincipalImpl(String name) { this.name = name; }

        @Override
        public String getName() { return name; }
    }

}
