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

    private PlayerService playerService;
    private HouseService houseService;
    private AbilityService abilityService;

    @Autowired
    public InitialDataFacadeImpl(PlayerService playerService,
                                 HouseService houseService,
                                 AbilityService abilityService) {
        this.playerService = playerService;
        this.houseService = houseService;
        this.abilityService = abilityService;
    }

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

        cal.set(Calendar.YEAR, 1650);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 5);

        House house2 = new House();
        house2.setName("Hobbit house");
        house2.setAddress("Hobbiton, Shire");
        house2.setHistory("Founded TA 1601.");
        house2.setHint("House of Frodo and Bilbo Baggins.");
        house2.setHauntedSince(cal.getTime());
        houseService.createHouse(house2);

        cal.set(Calendar.YEAR, 1739);
        cal.set(Calendar.MONTH, Calendar.MAY);
        cal.set(Calendar.DAY_OF_MONTH, 16);

        House house3 = new House();
        house3.setName("The Gingerbread House");
        house3.setAddress("Spooky forest");
        house3.setHistory("Built by a witch using only gingerbread and other sweets.");
        house3.setHint("Hansel and Gretel visited this house.");
        house3.setHauntedSince(cal.getTime());
        houseService.createHouse(house3);

        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 20);

        House house4 = new House();
        house4.setName("The White House");
        house4.setAddress("1600 Pennsylvania Avenue NW");
        house4.setHistory("Built in 1792. Designed by irish architect James Hoban.");
        house4.setHint("The US president lives there.");
        house4.setHauntedSince(cal.getTime());
        houseService.createHouse(house4);

        cal.set(Calendar.YEAR, 1990);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 24);

        House house5 = new House();
        house5.setName("Number 10");
        house5.setAddress("10 Downing Street");
        house5.setHistory("Number 10 is over 300 years old and contains approximately 100 rooms.");
        house5.setHint("The UK prime minister lives there.");
        house5.setHauntedSince(cal.getTime());
        houseService.createHouse(house5);
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
        ability1.setDescription("The specter may become invisible when nobody is watching");
        abilityService.createAbility(ability1);

        Ability ability2 = new Ability();
        ability2.setName("Fire breathing");
        ability2.setDescription("The specter may breathe fire.");
        abilityService.createAbility(ability2);

        Ability ability3 = new Ability();
        ability3.setName("Summon skeletons");
        ability3.setDescription("The specter may summons skeletons.");
        abilityService.createAbility(ability3);

        Ability ability4 = new Ability();
        ability4.setName("Hide under bed");
        ability4.setDescription("The specter might be hiding under your bed IRL.");
        abilityService.createAbility(ability4);

        Ability ability5 = new Ability();
        ability5.setName("Protection");
        ability5.setDescription("The specter will haunt you for the rest of your life if we get a low score for this milestone.");
        abilityService.createAbility(ability5);

        Ability ability6 = new Ability();
        ability6.setName("Time travel");
        ability6.setDescription("The specter can travel through time but only into the future at a rate of one second per second");
        abilityService.createAbility(ability6);

        Ability ability7 = new Ability();
        ability7.setName("Insect transformation");
        ability7.setDescription("The specter can transform into any insect... Irreversibly.");
        abilityService.createAbility(ability7);

        Ability ability8 = new Ability();
        ability8.setName("Poisonous clouds");
        ability8.setDescription("The specter can create highly poisonous clouds around it. The specter is not immune to the poison.");
        abilityService.createAbility(ability8);
    }
}
