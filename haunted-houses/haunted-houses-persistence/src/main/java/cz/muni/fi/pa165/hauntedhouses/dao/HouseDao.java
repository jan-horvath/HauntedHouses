package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.model.House;

import java.util.List;
import org.springframework.orm.jpa.JpaSystemException;

/**
 * @author Petr Vitovsky
 */
public interface HouseDao {

    /**
     * Adds new House to the database
     * @param house New House
     * @throws JpaSystemException if the database constraints are violated or the entity already exists
     */
    void createHouse(House house);

    /**
     * Searches database for a House with a specific id
     * @param id Id of a House
     * @return House with the given id if it exists, null otherwise
     */
    House getHouseById(long id);

    /**
     * Searches database for a House with a specific address
     * @param address Address of a House
     * @return House with the given address if it exists, null otherwise
     */
    House getHouseByAddress(String address);

    /**
     * Searches database for all Houses
     * @return List of all houses in database
     */
    List<House> getAllHouses();

    /**
     * Updates the given House in the database if it exists
     * @param house House for update
     * @return House if it was updated, null otherwise
     * @throws JpaSystemException if the database constraints are violated
     */
    House updateHouse(House house);

    /**
     * Deletes the given House from the database if it exists
     * @param house House for deletion
     */
    void deleteHouse(House house);
}
