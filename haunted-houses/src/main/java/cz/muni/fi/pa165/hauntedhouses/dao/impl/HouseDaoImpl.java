package cz.muni.fi.pa165.hauntedhouses.dao.impl;

import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.model.House;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;

/**
 * @author Petr Vitovsky
 */
public class HouseDaoImpl implements HouseDao {
    private static EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("default");

    @Override
    public void create(House house) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();

        em.persist(house);

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public House readId(long id) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();

        House house = em.find(House.class, id);

        em.getTransaction().commit();
        em.close();
        return house;
    }

    @Override
    public House readAddress(String address) {
        EntityManager em = emFactory.createEntityManager();

        House house;
        try {
            house = em.createQuery(
                    "SELECT house from House house WHERE house.address = :address", House.class).
                    setParameter("address", address).getSingleResult();
        } catch (NoResultException e) {
            house = null;
        } finally {
            em.close();
        }

        return house;
    }

    @Override
    public List<House> readAll() {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();

        List<House> result = em.createQuery("select h from House h", House.class).getResultList();

        em.getTransaction().commit();
        em.close();
        return result;
    }

    @Override
    public void update(House house) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();

        em.merge(house);

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(House house) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();

        if (em.contains(house)) {
            em.remove(house);
        } else {
            em.remove(em.getReference(House.class, house.getId()));
        }

        em.getTransaction().commit();
        em.close();
    }
}
