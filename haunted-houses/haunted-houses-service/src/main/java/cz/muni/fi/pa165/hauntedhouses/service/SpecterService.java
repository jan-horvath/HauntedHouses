package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.Specter;

public interface SpecterService {

    Specter generateRandomSpecter();

    Specter getByGameInstanceId(Long gameInstanceId);
}
