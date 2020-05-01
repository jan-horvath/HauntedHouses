package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.BanishSpecterDTO;

/**
 * @author Petr Vitovsky
 */

public interface GameFacade {
    /**
     * Checks whether the player chose the House where the ghost is hiding, increases the number of attempts
     * and in case of right answer decreases the required number of banishes
     * @param banishSpecter DTO with ID of the chosen House and ID of the GameInstance
     * @return true if the player chose the right House, false otherwise
     */
    boolean banishSpecter(BanishSpecterDTO banishSpecter);
}
