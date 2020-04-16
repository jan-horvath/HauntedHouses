package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author Petr Vitovsky
 */
@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private HouseService houseService;

    @Override
    public boolean checkAnswer(House house, GameInstance instance) {
        if (house == null || instance == null) {
            throw new IllegalArgumentException("Parameters cannot be null!");
        }

        instance.setBanishesAttempted(instance.getBanishesAttempted() + 1);

        House correctHouse = instance.getSpecter().getHouse();
        if (!house.equals(correctHouse)) {
            return false;
        }

        instance.setBanishesRequired(instance.getBanishesRequired() - 1);
        instance.getSpecter().setHouse(getRandomHouse());
        return true;
    }

    private House getRandomHouse() {
        List<House> houses = houseService.getAllHouses();
        Random rand = new Random();
        return houses.get(rand.nextInt(houses.size()));
    }
}
