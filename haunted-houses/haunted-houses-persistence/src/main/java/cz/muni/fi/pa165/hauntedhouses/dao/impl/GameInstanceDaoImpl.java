package cz.muni.fi.pa165.hauntedhouses.dao.impl;

import cz.muni.fi.pa165.hauntedhouses.dao.GameInstanceDao;
import cz.muni.fi.pa165.hauntedhouses.model.GameInstance;
import cz.muni.fi.pa165.hauntedhouses.model.Player;
import cz.muni.fi.pa165.hauntedhouses.model.Specter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author David Hofman
 */
@Repository
public class GameInstanceDaoImpl implements GameInstanceDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createGameInstance(GameInstance gameInstance) {
        em.persist(gameInstance);
    }

    @Override
    public GameInstance getGameInstanceById(Long id) {
        return em.find(GameInstance.class, id);
    }

    @Override
    public GameInstance getGameInstanceByPlayer(Player player) {
        try {
            return em.createQuery("SELECT g FROM GameInstance g WHERE g.player = :player", GameInstance.class)
                    .setParameter("player",player).getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public GameInstance getGameInstanceBySpecter(Specter specter) {
        try {
            return em.createQuery("SELECT g FROM GameInstance g WHERE g.specter = :specter", GameInstance.class)
                    .setParameter("specter",specter).getSingleResult();
        } catch(NoResultException e) {
            return null;
        }
    }

    @Override
    public List<GameInstance> getAllGameInstances() {
        return em.createQuery("SELECT g FROM GameInstance g", GameInstance.class).getResultList();
    }

    @Override
    public GameInstance updateGameInstance(GameInstance gameInstance) {
        if (gameInstance.getId() == null || em.find(GameInstance.class, gameInstance.getId()) == null) {
            return null;
        }
        return em.merge(gameInstance);
    }

    @Override
    public void deleteGameInstance(GameInstance gameInstance) {
        em.remove(em.contains(gameInstance) ? gameInstance : em.merge(gameInstance));
    }
}

