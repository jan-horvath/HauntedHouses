package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.House;

import org.springframework.stereotype.Service;

/**
 * @author Petr Vitovsky
 */
@Service
public interface GameService {
    boolean checkAnswer(House house, GameInstance instance);
}
