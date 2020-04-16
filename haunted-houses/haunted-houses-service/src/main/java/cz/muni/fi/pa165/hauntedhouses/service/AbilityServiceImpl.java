package cz.muni.fi.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dao.AbilityDao;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Petr Vitovsky
 */
@Service
public class AbilityServiceImpl implements AbilityService {

    @Autowired
    private AbilityDao abilityDao;

    @Override
    public Ability getAbilityById(Long id) {
        return abilityDao.getAbilityById(id);
    }

    @Override
    public Ability getAbilityByName(String name) {
        return abilityDao.getAbilityByName(name);
    }

    @Override
    public List<Ability> getAllAbilities() {
        return abilityDao.getAllAbilities();
    }

    @Override
    public void createAbility(Ability ability) {
        abilityDao.createAbility(ability);
    }

    @Override
    public void deleteAbility(Ability ability) {
        abilityDao.deleteAbility(ability);
    }
}
