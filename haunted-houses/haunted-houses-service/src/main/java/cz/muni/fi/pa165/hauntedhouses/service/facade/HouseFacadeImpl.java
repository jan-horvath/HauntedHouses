package cz.muni.fi.pa165.hauntedhouses.service.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;

import java.util.List;

public class HouseFacadeImpl implements HouseFacade {
    @Override
    public HouseDTO findHouseById(Long id) {
        return null;
    }

    @Override
    public List<HouseDTO> findAllHouses() {
        return null;
    }

    @Override
    public void createHouse(HouseCreateDTO house) {

    }

    @Override
    public void deleteHouse(Long id) {

    }

    @Override
    public void updateHouse(HouseDTO house) {

    }
}
