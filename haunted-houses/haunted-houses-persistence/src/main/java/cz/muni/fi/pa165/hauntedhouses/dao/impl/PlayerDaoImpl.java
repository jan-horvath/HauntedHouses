package cz.muni.fi.pa165.hauntedhouses.dao.impl;

import cz.muni.fi.pa165.hauntedhouses.dao.PlayerDao;
import cz.muni.fi.pa165.hauntedhouses.model.Player;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Jan Horvath
 */

@Repository
public class PlayerDaoImpl implements PlayerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createPlayer(Player player) {
        entityManager.persist(player);
    }

    @Override
    public Player getPlayerById(Long id) {
        return entityManager.find(Player.class, id);
    }

    @Override
    public Player getPlayerByEmail(String email) {
        try {
            return entityManager.createQuery("select p from Player p where email=:email",
                    Player.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<Player> getAllPlayers() {
        return entityManager.createQuery("select p from Player p", Player.class).getResultList();
    }

    @Override
    public Player updatePlayer(Player player) {
        if (player.getId() == null || entityManager.find(Player.class, player.getId()) == null) {
            return null;
        }
        return entityManager.merge(player);
    }

    @Override
    public void deletePlayer(Player player) {
        entityManager.remove(entityManager.contains(player) ? player : entityManager.merge(player));
    }
}
