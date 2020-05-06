package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.House;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Petr Vitovsky
 */
@Service
public interface HouseService {
    /**
     * Finds House with the given ID
     * @param id ID of the House
     * @return House with the given ID if it exists, null otherwise
     */
    House getHouseById(Long id);

    /**
     * Finds all Houses in the database
     * @return List of all Houses
     */
    List<House> getAllHouses();

    /**
     * Creates new House in the database
     * @param house New House
     * @throws org.springframework.dao.DataAccessException if constraints are violated
     */
    void createHouse(House house);

    /**
     * Deletes the House from the database
     * @param house House for deletion
     */
    void deleteHouse(House house);

    /**
     * Updates the House in the database
     * @param house House for update
     * @throws org.springframework.dao.DataAccessException if constraints are violated
     * @return Updated House
     */
    House updateHouse(House house);

    /**
     * @return random house from the database
     */
    House getRandomHouse();
}
