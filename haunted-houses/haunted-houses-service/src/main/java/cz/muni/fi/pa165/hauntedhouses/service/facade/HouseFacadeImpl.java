package cz.muni.fi.pa165.hauntedhouses.service.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import cz.muni.fi.pa165.hauntedhouses.service.HouseService;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Jan Horvath
 */

@Service
@Transactional
public class HouseFacadeImpl implements HouseFacade {

    private MappingService mappingService;
    private HouseService houseService;

    @Autowired
    public HouseFacadeImpl(MappingService mappingService,
                           HouseService houseService) {
        this.mappingService = mappingService;
        this. houseService = houseService;
    }

    @Override
    public HouseDTO getHouseById(Long id) {
        House houseById = houseService.getHouseById(id);
        return (houseById == null) ? null : mappingService.mapTo(houseById, HouseDTO.class);
    }

    @Override
    public List<HouseDTO> getAllHouses() {
        return mappingService.mapTo(houseService.getAllHouses(), HouseDTO.class);
    }

    @Override
    public Long createHouse(HouseCreateDTO house) {
        House newHouse = mappingService.mapTo(house, House.class);
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
