package cz.muni.fi.pa165.hauntedhouses.dao;

import cz.muni.fi.pa165.hauntedhouses.model.Player;
import org.springframework.orm.jpa.JpaSystemException;

import java.util.List;

/**
 * @author Jan Horvath
 */

public interface PlayerDao {

    /**
     * Creates a new player in the database
     * @param player player to be created
     * @throws JpaSystemException if given player already exists or database constraints are violated
     */
    void createPlayer(Player player);

    /**
     * Searches database for a Player with a specific id
     * @param id ID of a player
     * @return player with the given ID. null if such player is not found
     */
    Player getPlayerById(Long id);

    /**
     * Searches database for a player with a specific name
     * @param name name of a player
     * @return player with the given name or null if such player is not found.
     */
    Player getPlayerByName(String name);

    /**
     * Searches database for all players
     * @return List of all players in the database
     */
    List<Player> getAllPlayers();

    /**
     * Updates the given player in the database
     * @param player player to be updated
     * @return player which was updated or null if such player is not found
     * @throws JpaSystemException if database constraints are violated
     */
    Player updatePlayer(Player player);

    /**
     * Removes given player from the database
     * If the given player does not exist, the method does nothing
     * @param player player to be removed
     */
    void deletePlayer(Player player);
}
