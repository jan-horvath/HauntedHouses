package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.House;

import org.springframework.stereotype.Service;

/**
 * @author Petr Vitovsky
 */
@Service
public interface GameService {
    /**
     * Checks whether the player chose the House where the Specter is hiding, increases the number of attempts
     * and in case of right answer decreases the required number of banishes
     * @param house The House chosen by the Player
     * @param instance GameInstance of the Player
     * @return true if the player chose the right House, false otherwise
     */
    boolean checkAnswer(House house, GameInstance instance);

}
