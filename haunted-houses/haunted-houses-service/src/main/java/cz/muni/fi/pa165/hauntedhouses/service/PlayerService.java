package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.model.Player;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zoltan Fridrich
 *
 * An interface that defines a service access to the {@link Player} entity.
 */
@Service
public interface PlayerService {

    /**
     * Search for player with given id
     *
     * @param playerId Players id
     * @return Player with given id
     */
    Player getPlayerById(Long playerId);

    /**
     * Search for player with given email
     *
     * @param email Players email
     * @return Player with given email
     */
    Player getPlayerByEmail(String email);

    /**
     * Get all registered players
     *
     * @return List of all registered players
     */
    List<Player> getAllPlayers();

    /**
     * Register the given player with the given unencrypted password
     *
     * @param p Player to be registered
     * @param unencryptedPassword Players unencrypted password
     */
    void registerPlayer(Player p, String unencryptedPassword);

    /**
     * Try to authenticate a player. Return true only if the hashed password matches the records
     *
     * @param p Player to authenticate
     * @param password Password to match the records
     * @return True if player has been successfully authenticated, false otherwise
     */
    boolean authenticate(Player p, String password);

    /**
     * Check if the given player is admin
     *
     * @param p Player
     * @return True if player p is admin, false otherwise
     */
    boolean isAdmin(Player p);
}