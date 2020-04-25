package cz.muni.fi.pa165.hauntedhouses.dao.impl;

import cz.muni.fi.pa165.hauntedhouses.dao.AbilityDao;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

/**
 * @author David Hofman
 */
@Repository
public class AbilityDaoImpl implements AbilityDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createAbility(Ability A) {
        em.persist(A);
    }

    @Override
    public Optional<Ability> getAbilityById(Long id) {
        return Optional.ofNullable(em.find(Ability.class, id));
    }

    @Override
    public Optional<Ability> getAbilityByName(String name) {
        try {
            return Optional.of(em.createQuery("SELECT a FROM Ability a WHERE a.name = :name", Ability.class)
                    .setParameter("name",name).getSingleResult());
        } catch(NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Ability> getAllAbilities() {
        return em.createQuery("SELECT a FROM Ability a", Ability.class).getResultList();
    }

    @Override
    public Optional<Ability> updateAbility(Ability A) {
        if(A.getId() == null || em.find(Ability.class, A.getId()) == null) return Optional.empty();
        return Optional.of(em.merge(A));
    }

    @Override
    public void deleteAbility(Ability A) {
        em.remove(em.contains(A) ? A : em.merge(A));
    }
}
