package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;

import java.util.List;

/**
 * @author Petr Vitovsky
 */

public interface HouseFacade {

    HouseDTO findHouseById(Long id);

    List<HouseDTO> findAllHouses();

    void createHouse(HouseCreateDTO house);

    void deleteHouse(Long id);

    void updateHouse(HouseDTO house);
}
