package cz.muni.fi.pa165.hauntedhouses.dao.impl;

import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Petr Vitovsky
 */
@Repository
@Transactional
public class HouseDaoImpl implements HouseDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createHouse(House house) {
        em.persist(house);
    }

    @Override
    public House getHouseById(long id) {
        return em.find(House.class, id);
    }

    @Override
    public House getHouseByAddress(String address) {
        try {
            return em.createQuery(
                    "SELECT house from House house WHERE house.address = :address", House.class).
                    setParameter("address", address).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<House> getAllHouses() {
        return em.createQuery("select h from House h", House.class).getResultList();
    }

    @Override
    public void updateHouse(House house) {
        if (em.find(House.class, house.getId()) != null) {
            em.merge(house);
        }
    }

    @Override
    public void deleteHouse(House house) {
        if (em.contains(house)) {
            em.remove(house);
        } else {
            em.remove(em.getReference(House.class, house.getId()));
        }
    }
}
