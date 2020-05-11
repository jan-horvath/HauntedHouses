package cz.muni.fi.pa165.hauntedhouses.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.PlayerAuthenticationDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.PlayerDTO;

import java.util.Collection;

/**
 * @author Jan Horvath
 */

public interface PlayerFacade {

    /**
     * Search for player with given id
     *
     * @param id Players id
     * @return Player with given id
     */
    PlayerDTO findPlayerById(Long id);

    /**
     * Search for player with given email
     *
     * @param email Players email
     * @return Player with given email
     */
    PlayerDTO findPlayerByEmail(String email);

    /**
     * Get all registered players
     *
     * @return Collection of all registered players
     */
    Collection<PlayerDTO> getAllPlayers();

    /**
     * Register the given player with the given unencrypted password
     *
     * @param player Player to register
     * @param unencryptedPassword Players unencrypted password
     */
    void registerPlayer(PlayerDTO player, String unencryptedPassword);

    /**
     * Try to authenticate a player. Return true only if the hashed password matches the records
     *
     * @param authentication Players id and password
     * @return True if player has been successfully authenticated, false otherwise
     */
    boolean authenticate(PlayerAuthenticationDTO authentication);

    /**
     * Check if the given player is admin
     *
     * @param player Player
     * @return True if player is admin, false otherwise
     */
    boolean isAdmin(PlayerDTO player);

    /**
     * Deletes a player specified by ID
     * @param id
     */
    void deletePlayer(Long id);
}
