package cz.muni.fi.pa165.hauntedhouses.rest.controllers;

import cz.muni.fi.pa165.hauntedhouses.dto.HouseCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.HouseFacade;
import cz.muni.fi.pa165.hauntedhouses.dto.HouseDTO;
import cz.muni.fi.pa165.hauntedhouses.rest.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.hauntedhouses.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.hauntedhouses.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/house")
public class HouseController {

    final static Logger logger = LoggerFactory.getLogger(HouseController.class);

    @Autowired
    private HouseFacade houseFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<HouseDTO> getHouses() {
        logger.debug("rest getHouses()");
        return houseFacade.findAllHouses();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HouseDTO getHouse(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getHouse({})", id);

        HouseDTO houseDTO = houseFacade.findHouseById(id);
        if (houseDTO == null) throw new ResourceNotFoundException();

        return houseDTO;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final HouseDTO createHouse(@Valid @RequestBody HouseCreateDTO house) throws Exception {
        logger.debug("rest createHouse()");

        try {
            Long id = houseFacade.createHouse(house);
            return houseFacade.findHouseById(id);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final String deleteHouse(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteHouse({})", id);
        try {
            houseFacade.deleteHouse(id);
            return "House " + id + " was successfully deleted.";
        } catch (Exception ex) {
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final HouseDTO updateHouse(@RequestBody HouseDTO house) throws Exception {
        logger.debug("rest updateHouse()");

        try {
            houseFacade.updateHouse(house);
            return houseFacade.findHouseById(house.getId());
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }
}
