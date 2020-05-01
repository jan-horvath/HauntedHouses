package cz.muni.fi.pa165.hauntedhouses.service.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.SpecterDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.SpecterFacade;
import cz.muni.fi.pa165.hauntedhouses.model.Specter;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import cz.muni.fi.pa165.hauntedhouses.service.SpecterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author David Hofman
 */

@Service
@Transactional
public class SpecterFacadeImpl implements SpecterFacade {

    @Autowired
    private SpecterService specterService;

    @Autowired
    private MappingService mappingService;

    @Override
    public SpecterDTO findSpecterByGameInstanceId(Long gameInstanceId) {
        Specter specter = specterService.getByGameInstanceId(gameInstanceId);
        return (specter == null) ? null : mappingService.mapTo(specter, SpecterDTO.class);
    }

    /*
    @Override
    public void createSpecter(SpecterCreateDTO specter) {

    }
    */

    /*
    @Override
    public void deleteSpecter(Long id) {

    }
    */
}
