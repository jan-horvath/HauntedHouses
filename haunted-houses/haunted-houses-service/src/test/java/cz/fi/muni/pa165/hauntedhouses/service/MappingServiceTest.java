package cz.fi.muni.pa165.hauntedhouses.service;

import cz.muni.fi.pa165.hauntedhouses.dto.AbilityDTO;
import cz.muni.fi.pa165.hauntedhouses.model.Ability;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import cz.muni.fi.pa165.hauntedhouses.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class MappingServiceTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MappingService mappingService;

    @Test
    public void abilityConversionTest() {
        Ability ability = new Ability();
        ability.setName("Some name");
        ability.setDescription("Some description");

        AbilityDTO abilityDTO = mappingService.mapTo(ability, AbilityDTO.class);
        Assert.assertEquals(abilityDTO.getName(), ability.getName());
        Assert.assertEquals(abilityDTO.getDescription(), ability.getDescription());
    }
}
