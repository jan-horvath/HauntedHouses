package cz.muni.fi.pa165.hauntedhouses.service;

import com.github.javafaker.Faker;
import cz.muni.fi.pa165.hauntedhouses.dao.SpecterDao;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.Specter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

/**
 * @author Jan Horvath
 */

@Service
public class SpecterServiceImpl implements SpecterService {

    private static final int MAX_SPECTER_ABILITIES = 4;

    private static final List<String> descriptions = Collections.unmodifiableList(Arrays.asList(new String[] {
            "The scariest specter in the world.",
            "One of the oldest specters in the world.",
            "The most unholy creature that has ever existed.",
            "The most dangerous specter of them all.",
            "Looks cute. Will bite your face off.",
            "Hates mondays. Loves casual killing fridays.",
            "Likes brunches, walks on the beach and spontaneous sacrificial rituals."}));

    private AbilityService abilityService;
    private GameInstanceService gameInstanceService;
    private SpecterDao specterDao;

    @Autowired
    public SpecterServiceImpl(AbilityService abilityService,
                              GameInstanceService gameInstanceService,
                              SpecterDao specterDao) {
        this.abilityService = abilityService;
        this.gameInstanceService = gameInstanceService;
        this.specterDao = specterDao;
    }

    @Override
    public Specter generateRandomSpecter() {
        Faker faker = new Faker();
        Random random = new Random();

        Specter specter = new Specter();
        specter.setName(faker.witcher().monster());
        specter.setStartOfHaunting(LocalTime.of(random.nextInt(24), 0));
        specter.setEndOfHaunting(LocalTime.of(random.nextInt(24), 0));
        specter.setDescription(descriptions.get(random.nextInt(descriptions.size())));

        List<Ability> allAbilities = abilityService.getAllAbilities();
        if (allAbilities.size() < MAX_SPECTER_ABILITIES) {
            specter.setAbilities(new HashSet<>(allAbilities));
        } else {
            Collections.shuffle(allAbilities);
            specter.setAbilities(new HashSet<>(allAbilities.subList(0, MAX_SPECTER_ABILITIES)));
        }

        return specter;
    }

    @Override
    public Specter getBySpecterId(Long specterId) {
        return specterDao.getSpecterById(specterId);
    }

    @Override
    public Specter getByGameInstanceId(Long gameInstanceId) {
        GameInstance gameInstanceById = gameInstanceService.getGameInstanceById(gameInstanceId);
        if (gameInstanceById == null) {
            throw new DataRetrievalFailureException("No such game instance found");
        }
        return specterDao.getSpecterByGameInstance(gameInstanceById);
    }

    @Override
    public void deleteSpecter(Specter specter) {
        specterDao.deleteSpecter(specter);
    }
}
