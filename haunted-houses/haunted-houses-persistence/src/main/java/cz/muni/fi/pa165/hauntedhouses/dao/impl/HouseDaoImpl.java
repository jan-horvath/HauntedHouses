package cz.muni.fi.pa165.hauntedhouses.dao.impl;

import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * @author Petr Vitovsky
 */
@Repository
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
    public House updateHouse(House house) {
        if (house.getId() == null ||em.find(House.class,house.getId()) == null) {
            return null;
        }
        return em.merge(house);
    }

    @Override
    public void deleteHouse(House house) {
        em.remove(em.contains(house) ? house : em.merge(house));

    }
}
