package cz.muni.fi.pa165.hauntedhouses.service.facade;

import cz.muni.fi.pa165.hauntedhouses.facade.InitialDataFacade;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import cz.muni.fi.pa165.hauntedhouses.model.Player;
import cz.muni.fi.pa165.hauntedhouses.service.AbilityService;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import cz.muni.fi.pa165.hauntedhouses.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Component
@Transactional
public class InitialDataFacadeImpl implements InitialDataFacade {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private AbilityService abilityService;

    @Override
    public void loadData() {
        loadPlayers();
        loadHouses();
        loadAbilities();
    }

    private void loadHouses() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1999);
        cal.set(Calendar.MONTH, Calendar.NOVEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 10);

        House house1 = new House();
        house1.setName("Cartoon house");
        house1.setAddress("Evergreen Terrace 742");
        house1.setHistory("The house was built in 1989. The first haunting was recorded in 1999.");
        house1.setHint("House of the Simpsons family.");
        house1.setHauntedSince(cal.getTime());
        houseService.createHouse(house1);
    }

    private void loadPlayers() {
        Player player = new Player();
        player.setName("player1");
        player.setEmail("player1@email.com");
        player.setAdmin(false);

        Player admin = new Player();
        admin.setName("admin");
        admin.setEmail("admin@email.com");
        admin.setAdmin(true);

        playerService.registerPlayer(player, "password");
        playerService.registerPlayer(admin, "admin");
    }

    private void loadAbilities() {
        Ability ability1 = new Ability();
        ability1.setName("Invisibility");
        ability1.setDescription("The specter may become invisible.");

        abilityService.createAbility(ability1);
    }
}
