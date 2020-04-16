package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.House;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Petr Vitovsky
 */
@Service
public interface HouseService {
    House getHouseById(Long id);
    List<House> getAllHouses();
    void createHouse(House house);
    void deleteHouse(House house);
    House updateHouse(House house);
}
