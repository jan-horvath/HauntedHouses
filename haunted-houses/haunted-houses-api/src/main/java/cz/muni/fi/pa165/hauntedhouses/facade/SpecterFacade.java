package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.SpecterDTO;

/**
 * @author Zoltan Fridrich
 */
public interface SpecterFacade {

    /**
     * Finds a specter that belongs to GameInstance with given Id
     * @param gameInstanceId Id of GameInstance
     * @return Found Specter, null if it doesn't exist
     */
    SpecterDTO getSpecterByGameInstanceId(Long gameInstanceId);

    /**
     * Deletes Specter with the given ID from the database
     * @param id
     */
    void deleteSpecter(Long id);
}
