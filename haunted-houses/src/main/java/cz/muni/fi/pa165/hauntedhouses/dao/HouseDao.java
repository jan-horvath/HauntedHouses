package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.model.House;

import java.util.List;

/**
 * @author Petr Vitovsky
 */
public interface HouseDao {

    /**
     * Adds new House to the database
     * @param house New House
     */
    void create(House house);

    /**
     * Searches database for a House with a specific id
     * @param id Id of a House
     * @return House with the given id
     */
    House readId(long id);

    /**
     * Searches database for a House with a specific address
     * @param address Address of a House
     * @return House with the given address
     */
    House readAddress(String address);

    /**
     * Searches database for all Houses
     * @return List of all houses in database
     */
    List<House> readAll();

    /**
     * Updates the given House in the database
     * @param house House for update
     */
    void update(House house);

    /**
     * Deletes the given House from the database
     * @param house House for deletion
     */
    void delete(House house);
}
