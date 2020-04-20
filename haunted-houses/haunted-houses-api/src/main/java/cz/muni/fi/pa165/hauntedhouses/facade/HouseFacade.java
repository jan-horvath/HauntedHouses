package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;

import java.util.List;

/**
 * @author Petr Vitovsky
 */

public interface HouseFacade {

    /**
     * Finds house with given id
     * @param id
     * @return found house if such exists, null otherwise
     */
    HouseDTO findHouseById(Long id);

    /**
     * @return All houses in the database
     */
    List<HouseDTO> findAllHouses();

    /**
     * Creates new house in database
     * @param house
     * @return database ID of the created house
     * @throws org.springframework.dao.DataAccessException if constraints are violated
     */
    Long createHouse(HouseCreateDTO house);

    /**
     * Deletes house with given id if it exists
     * @param id
     */
    void deleteHouse(Long id);

    /**
     * Updates house in the database, if it exists
     * @param house
     * @throws org.springframework.dao.DataAccessException if constraints are violated
     */
    void updateHouse(HouseDTO house);
}
