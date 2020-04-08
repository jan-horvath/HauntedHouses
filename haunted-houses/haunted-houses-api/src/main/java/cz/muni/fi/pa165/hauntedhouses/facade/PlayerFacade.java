package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.PlayerAuthenticationDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.PlayerDTO;

/**
 * @author Jan Horvath
 */

public interface PlayerFacade {

    PlayerDTO findPlayerById(Long id);

    PlayerDTO findPlayerByName(String name);

    void registerPlayer(PlayerDTO player, String unencryptedPassword);

    boolean authenticate(PlayerAuthenticationDTO authentication);

    boolean isAdmin(Long playerId);
}
