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

    private SpecterService specterService;
    private MappingService mappingService;

    @Autowired
    public SpecterFacadeImpl(SpecterService specterService,
                             MappingService mappingService) {
        this.specterService = specterService;
        this.mappingService = mappingService;
    }

    @Override
    public SpecterDTO getSpecterByGameInstanceId(Long gameInstanceId) {
        Specter specter = specterService.getByGameInstanceId(gameInstanceId);
        return (specter == null) ? null : mappingService.mapTo(specter, SpecterDTO.class);
    }

    @Override
    public void deleteSpecter(Long id) {
        Specter specter = specterService.getBySpecterId(id);
        specterService.deleteSpecter(specter);
    }

}
