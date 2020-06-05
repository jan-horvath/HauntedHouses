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
import java.util.Date;

@Component
@Transactional
public class InitialDataFacadeImpl implements InitialDataFacade {

    private PlayerService playerService;
    private HouseService houseService;
    private AbilityService abilityService;

    private Calendar calendar = Calendar.getInstance();

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

    private void createHouse(String name, String address, String history, String hint, int day, int month, int year) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        House house = new House();
        house.setName(name);
        house.setAddress(address);
        house.setHistory(history);
        house.setHint(hint);
        house.setHauntedSince(calendar.getTime());

        houseService.createHouse(house);
    }

    private void createAbility(String name, String description) {
        Ability ability = new Ability();
        ability.setName(name);
        ability.setDescription(description);
        abilityService.createAbility(ability);
    }

    private void loadHouses() {
        createHouse("Berghof", "Berchtesgaden, Bavaria, Germany",
                "The Berghof began as a much smaller chalet called Haus Wachenfeld, " +
                        "a holiday home built in 1916 (or 1917) by Kommerzienrat Otto Winter.",
                "This house was Hitler's home in the Bavarian Alps", 8, Calendar.SEPTEMBER, 1918);

        createHouse("Palace of Versailles", "Place d'Armes, 78000 Versailles, France",
                "It was the principal royal residence of France from 1682, until the start of the " +
                        "French Revolution in 1789", "The Sun King lived there.",
                31, Calendar.DECEMBER, 1800);

        createHouse("Hagenauerhaus", "Getreidegasse 9, 5020 Salzburg, Austria",
                "The house was built in the 12th century on ground which had been part of the garden belonging " +
                        "to the Benedictine monks of St Peter's, Salzburg.",
                "Mozart was born in this house.", 5, Calendar.MAY, 1995);

        createHouse("Villa Tugendhat", "Černopolní 45, 613 00 Brno",
                "The construction company of Artur and Mořic Eisler began construction in the summer " +
                        "of 1929 and finished it in 14 months.",
                "A famous building in modern architectural style designed by Ludwig Mies van der Rohe and Lilly Reich",
                7, Calendar.AUGUST, 2017);

        createHouse("The White House", "1600 Pennsylvania Avenue NW",
                "Built in 1792. Designed by irish architect James Hoban.",
                "The US president lives there.", 20, Calendar.JANUARY, 2017);

        createHouse("Number 10", "10 Downing Street",
                "Number 10 is over 300 years old and contains approximately 100 rooms.",
                "The UK prime minister lives there.", 24, Calendar.JUNE, 1990);

        createHouse("Empire State Building", "350 Fifth Avenue, Manhattan, NY",
                "It was designed by Shreve, Lamb & Harmon and built from 1930 to 1931.",
                "Its name is derived from the nickname of the city, where it stands.",
                12, Calendar.FEBRUARY, 1999);

        createHouse("Leaning Tower of Pisa", "Piazza del Duomo, 56126, Pisa PI, Italy",
                "The tower began to sink after construction had progressed to the second floor in 1178.",
                "Torre di ...", 18, Calendar.OCTOBER, 1444);

        createHouse("Taj Mahal", "Agra, Uttar Pradesh, India",
                "Estimated cost of the construction is about 70 billion rupees (916 million dolars)",
                "Lies on the southern bank of the river Yamuna", 1, 2, 1993);

        createHouse("Burj Khalifa", "1 Sheikh Mohammed bin Rashid Boulevard",
                " Sheikh Khalifa, the ruler of the UAE, granted monetary aid and funding, " +
                        "hence resulting in the changing of the name to \"Burj Khalifa\".",
                "Tallest building in the world since 2009", 29, 9, 2013);

        createHouse("Potala Palace", "No. 35, Beijing Middle Road, Chengguan District, Lhasa City",
                "This has been a museum since 1959, and is a World Heritage Site since 1994",
                "The winter palace of the Dalai Lamas", 25, Calendar.MAY, 1646);

        createHouse("Taipei 101", "No. 7, Section 5, Xinyi Road, Xinyi District",
                "In 2011 Taipei 101 received a Platinum rating under the LEED certification system to become the " +
                        "largest green building in the world.",
                "Tallest building between 2004 and 2010", 10, Calendar.SEPTEMBER, 2012);
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
        createAbility("Invisibility", "The specter may become invisible when nobody is watching");
        createAbility("Fire breathing", "The specter may breathe fire.");
        createAbility("Summon skeletons", "The specter may summons skeletons.");
        createAbility("Hide under bed", "The specter might be hiding under your bed IRL.");
        createAbility("Protection", "The specter will haunt you for the rest of your life if we get a low score for this milestone.");
        createAbility("Time travel", "The specter can travel through time but only into the future at a rate of one second per second");
        createAbility("Insect transformation","The specter can transform into any insect... Irreversibly.");
        createAbility("Poisonous clouds", "The specter can create highly poisonous clouds around it. The specter is not immune to the poison.");
    }
}
