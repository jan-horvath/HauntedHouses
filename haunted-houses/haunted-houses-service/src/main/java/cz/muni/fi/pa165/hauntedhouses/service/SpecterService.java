package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.Specter;
import org.springframework.stereotype.Service;

@Service
public interface SpecterService {

    Specter generateRandomSpecter();

    Specter getByGameInstanceId(Long gameInstanceId);
}
