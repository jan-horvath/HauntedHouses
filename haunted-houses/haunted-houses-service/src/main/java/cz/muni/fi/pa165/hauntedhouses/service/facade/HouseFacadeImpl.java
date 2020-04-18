package cz.muni.fi.pa165.hauntedhouses.service.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HouseFacadeImpl implements HouseFacade {

    @Autowired
    MappingService mappingService;

    @Autowired
    HouseService houseService;

    @Override
    public HouseDTO findHouseById(Long id) {
        return mappingService.mapTo(houseService.getHouseById(id), HouseDTO.class);
    }

    @Override
    public List<HouseDTO> findAllHouses() {
        return mappingService.mapTo(houseService.getAllHouses(), HouseDTO.class);
    }

    @Override
    public Long createHouse(HouseCreateDTO house) {
        House newHouse = new House();
        newHouse.setAddress(house.getAddress());
        newHouse.setHauntedSince(house.getHauntedSince());
        newHouse.setHint(house.getHint());
        newHouse.setHistory(house.getHistory());
        newHouse.setName(house.getName());

        houseService.createHouse(newHouse);
        return newHouse.getId();
    }

    @Override
    public void deleteHouse(Long id) {
        houseService.deleteHouse(houseService.getHouseById(id));
    }

    @Override
    public void updateHouse(HouseDTO house) {
        House houseToUpdate = mappingService.mapTo(house, House.class);
        houseService.updateHouse(houseToUpdate);
    }
}
