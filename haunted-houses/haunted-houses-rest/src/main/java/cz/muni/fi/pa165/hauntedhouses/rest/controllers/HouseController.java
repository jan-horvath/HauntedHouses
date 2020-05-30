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
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Petr Vitovsky
 */
@RestController
@RequestMapping("/api/v1/house")
public class HouseController {

    final static Logger logger = LoggerFactory.getLogger(HouseController.class);

    private HouseFacade houseFacade;

    @Autowired
    public HouseController(HouseFacade houseFacade) {
        this.houseFacade = houseFacade;
    }

    /**
     * Get all houses in the database
     * curl -i -X GET http://localhost:8080/pa165/rest/api/v1/house
     * @return List of all houses
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<HouseDTO> getHouses() {
        logger.debug("rest getHouses()");
        return houseFacade.getAllHouses();
    }

    /**
     * Get house with specified ID
     * curl -i -X GET http://localhost:8080/pa165/rest/api/v1/house/{id}
     * @param id ID of the house
     * @return House with specified ID
     * @throws Exception House with specified ID does not exist
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HouseDTO getHouse(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getHouse({})", id);

        HouseDTO houseDTO = houseFacade.findHouseById(id);
        if (houseDTO == null) throw new ResourceNotFoundException("House with ID " + id + " does not exist!");

        return houseDTO;
    }

    /**
     * Creates new house
     * curl -X POST -i -H "Content-Type: application/json"
     * --data '{\"name\":\"nameValue\",\"address\":\"addressValue\",\"history\":\"historyValue\",\"hint\":\"hintValue\",\"hauntedSince\":\"yyyy-MM-dd\"}'
     * http://localhost:8080/pa165/rest/api/v1/house
     * @param house New house
     * @return Newly created house
     * @throws Exception House already exists
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final HouseDTO createHouse(@Valid @RequestBody HouseCreateDTO house) throws Exception {
        logger.debug("rest createHouse()");

        try {
            Long id = houseFacade.createHouse(house);
            return houseFacade.findHouseById(id);
        } catch (DataAccessException ex) {
            throw new ResourceAlreadyExistingException("House with address " + house.getAddress() + " already exists!");
        }
    }

    /**
     * Deletes house with specified ID
     * curl -i -X DELETE http://localhost:8080/pa165/rest/api/v1/house/{id}
     * @param id ID of the house
     * @return String containing information about deleted house
     * @throws Exception House with specified ID does not exist
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final String deleteHouse(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteHouse({})", id);
        try {
            houseFacade.deleteHouse(id);
            return "House " + id + " was successfully deleted.";
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("House with ID " + id + " does not exist!");
        }
    }

    /**
     * Updates house in the database
     * curl -X PUT -i -H "Content-Type: application/json"
     * --data '{\"id\":"1",\"name\":\"nameValue\",\"address\":\"addressValue\",\"history\":\"historyValue\",\"hint\":\"hintValue\",\"hauntedSince\":\"yyyy-MM-dd\"}'
     * http://localhost:8080/pa165/rest/api/v1/house
     * @param house Updated house
     * @return Newly updated house
     * @throws Exception Updated parameters are invalid
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final HouseDTO updateHouse(@Valid @RequestBody HouseDTO house) throws Exception {
        logger.debug("rest updateHouse()");

        try {
            houseFacade.updateHouse(house);
            return houseFacade.findHouseById(house.getId());
        } catch (DataAccessException ex) {
            throw new InvalidParameterException("House with address " + house.getAddress() + " already exists!");
        }
    }
}
