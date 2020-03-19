package cz.muni.fi.pa165.hauntedhouses.dao.impl;

import cz.muni.fi.pa165.hauntedhouses.dao.AbilityDao;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;

import javax.persistence.*;
import java.util.List;

/**
 * @author David Hofman
 */
public class AbilityDaoImpl implements AbilityDao {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    @Override
    public void createAbility(Ability A) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(A);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Ability getAbilityById(Long id) {
        EntityManager em = emf.createEntityManager();
        Ability ability = em.find(Ability.class, id);
        em.close();
        return ability;
    }

    @Override
    public Ability getAbilityByName(String name) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Ability> query = em.createQuery("SELECT a FROM Ability a WHERE a.name = :name", Ability.class).setParameter("name",name);
        Ability ability;
        try {
            ability = query.getSingleResult();
        } catch(NoResultException e) {
            ability = null;
        } finally {
            em.close();
        }
        return ability;
    }

    @Override
    public List<Ability> getAllAbilities() {
        EntityManager em = emf.createEntityManager();
        List<Ability> list = em.createQuery("SELECT a FROM Ability a", Ability.class).getResultList();
        em.close();
        return list;
    }

    @Override
    public void updateAbility(Ability A) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(A);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteAbility(Ability A) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        if (em.contains(A)) {
            em.remove(A);
        } else {
            em.remove(em.getReference(Ability.class, A.getId()));
        }
        em.getTransaction().commit();
        em.close();
    }
}