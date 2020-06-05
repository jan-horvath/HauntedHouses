package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.House;

import org.springframework.stereotype.Service;
import cz.muni.fi.pa165.hauntedhouses.service.exceptions.NoHousesException;

import java.util.List;

/**
 * @author Petr Vitovsky
 */
@Service
public interface HouseService {

    int SUBSET_SIZE = 5;

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
     * Returns a subset of all houses in the database which contains a specific house. The size of the subset is given
     * by SUBSET_SIZE
     * @param specificHouseId ID of a specific house which is contained in the database
     * @throws org.springframework.dao.DataRetrievalFailureException if house with given ID does not exist
     * @throws cz.muni.fi.pa165.hauntedhouses.service.exceptions.NotEnoughHousesException - if there are fewer than
     * SUBSET_SIZE houses
     */
    List<House> getSubsetWithSpecificHouse(Long specificHouseId);

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
     * @throws NoHousesException if there are currently not houses in the database
     */
    House getRandomHouse();
}
