package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.model.House;

import java.util.List;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaSystemException;

/**
 * @author Petr Vitovsky
 */
public interface HouseDao {

    /**
     * Adds new House to the database
     * @param house New House
     * @throws JpaSystemException if the database constraints have been violated or the database already contains the House
     */
    void createHouse(House house) throws JpaSystemException;

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
     * @throws JpaSystemException if the database constraints have been violated
     */
    void updateHouse(House house) throws JpaSystemException;

    /**
     * Deletes the given House from the database
     * @param house House for deletion
     * @throws JpaObjectRetrievalFailureException if the database doesn't contain the House
     */
    void deleteHouse(House house) throws JpaObjectRetrievalFailureException;
}
