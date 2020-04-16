package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Petr Vitovsky
 */
@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseDao houseDao;

    @Override
    public House getHouseById(Long id) {
        return houseDao.getHouseById(id);
    }

    @Override
    public List<House> getAllHouses() {
        return houseDao.getAllHouses();
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
}
