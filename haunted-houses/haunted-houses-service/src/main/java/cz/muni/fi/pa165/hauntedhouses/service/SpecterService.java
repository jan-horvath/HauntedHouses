package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.Specter;
import org.springframework.stereotype.Service;

/**
 * @author Jan Horvath
 */

@Service
public interface SpecterService {

    /**
     * Generates random specter with random (meaningful) name, random hour of start and end of haunting, description
     * set to "No description" and upto three random abilities from the database
     * @return randomly generated specter
     */
    Specter generateRandomSpecter();

    /**
     * Retrieves specter using game instance ID
     * @param gameInstanceId
     * @throws org.springframework.dao.DataRetrievalFailureException if such game instance does not exist
     * @return specter associated with given game instance or null is no such specter exists
     */
    Specter getByGameInstanceId(Long gameInstanceId);
}
