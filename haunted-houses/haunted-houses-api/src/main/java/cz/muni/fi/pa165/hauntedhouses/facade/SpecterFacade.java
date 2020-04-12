package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.SpecterCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.SpecterDTO;

/**
 * @author Zoltan Fridrich
 */
public interface SpecterFacade {

    SpecterDTO findSpecterByGameInstanceID(Long id);

    void createSpecter(SpecterCreateDTO specter);

    void deleteSpecter(Long id);
}
