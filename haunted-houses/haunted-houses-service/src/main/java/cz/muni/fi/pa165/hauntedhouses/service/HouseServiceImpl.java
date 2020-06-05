package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import cz.muni.fi.pa165.hauntedhouses.service.exceptions.NoHousesException;
import cz.muni.fi.pa165.hauntedhouses.service.exceptions.NotEnoughHousesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Petr Vitovsky
 */
@Service
public class HouseServiceImpl implements HouseService {

    private HouseDao houseDao;

    @Autowired
    public HouseServiceImpl(HouseDao houseDao) {
        this.houseDao = houseDao;
    }

    @Override
    public House getHouseById(Long id) {
        return houseDao.getHouseById(id);
    }

    @Override
    public List<House> getAllHouses() {
        return houseDao.getAllHouses();
    }

    @Override
    public List<House> getSubsetWithSpecificHouse(Long specificHouseId) {
        if (SUBSET_SIZE <= 0) {
            throw new IllegalArgumentException("Subset size should be positive");
        }

        House specificHouse = getHouseById(specificHouseId);
        if (specificHouse == null) {
            throw new DataRetrievalFailureException("House with ID " + specificHouseId + " does not exist.");
        }

        List<House> houses = getAllHouses();
        if (houses.size() < SUBSET_SIZE) {
            throw new NotEnoughHousesException();
        }

        Collections.shuffle(houses);
        List<House> subset = houses.subList(0, SUBSET_SIZE);

        if (!subset.contains(specificHouse)) {
            int position = ThreadLocalRandom.current().nextInt(0, SUBSET_SIZE);
            subset.set(position, specificHouse);
        }

        return subset;
    }

    @Override
    public void createHouse(House house) {
        houseDao.createHouse(house);
    }

    @Override
    public void deleteHouse(House house) {
        houseDao.deleteHouse(house);
    }

    @Override
    public House updateHouse(House house) {
        return houseDao.updateHouse(house);
    }

    @Override
    public House getRandomHouse() {
        List<House> houses = houseDao.getAllHouses();

        if (houses.isEmpty()) {
            throw new NoHousesException();
        }

        Random rand = new Random();
        return houses.get(rand.nextInt(houses.size()));
    }
}
