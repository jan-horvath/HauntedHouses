package cz.muni.fi.pa165.hauntedhouses.dao.impl;

import cz.muni.fi.pa165.hauntedhouses.dao.SpecterDao;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import cz.muni.fi.pa165.hauntedhouses.model.Specter;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zoltan Fridrich
 */
@Repository
@Transactional
public class SpecterDaoImpl implements SpecterDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createSpecter(Specter specter) {
        em.persist(specter);
    }

    @Override
    public Specter updateSpecter(Specter specter) {
        return specter.getId() == null || em.find(Specter.class, specter.getId()) == null ? null : em.merge(specter);
    }

    @Override
    public void deleteSpecter(Specter specter) {
        em.remove(em.contains(specter) ? specter : em.merge(specter));
    }

    @Override
    public Specter getSpecterById(long id) {
        return em.find(Specter.class, id);
    }

    @Override
    public Specter getSpecterByName(String name) {
        try {
            return em.createQuery("SELECT specter FROM Specter specter WHERE :name = specter.name", Specter.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Specter> getSpectersByHouse(House house) {
        return em.createQuery(
                "SELECT specter FROM Specter specter WHERE :house = specter.house", Specter.class)
                .setParameter("house", house)
                .getResultList();
    }

    @Override
    public List<Specter> getSpectersByAbility(Ability ability) {
        return em.createQuery(
                "SELECT specter FROM Specter specter WHERE :ability MEMBER OF specter.abilities", Specter.class)
                .setParameter("ability", ability)
                .getResultList();
    }

    @Override
    public List<Specter> getAllSpecters() {
        return em.createQuery("SELECT specter FROM Specter specter", Specter.class).getResultList();
    }
}
