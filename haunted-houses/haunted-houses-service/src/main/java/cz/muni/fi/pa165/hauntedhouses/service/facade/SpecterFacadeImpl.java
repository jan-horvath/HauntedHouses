package cz.muni.fi.pa165.hauntedhouses.service.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.SpecterCreateDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.SpecterDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.SpecterFacade;

public class SpecterFacadeImpl implements SpecterFacade {
    @Override
    public SpecterDTO findSpecterByGameInstanceId(Long gameInstanceId) {
        return null;
    }

    @Override
    public void createSpecter(SpecterCreateDTO specter) {

    }

    @Override
    public void deleteSpecter(Long id) {

    }
}
