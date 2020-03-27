package cz.muni.fi.pa165.hauntedhouses;

import cz.muni.fi.pa165.hauntedhouses.dao.HouseDao;
import cz.muni.fi.pa165.hauntedhouses.model.House;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class HauntedHousesApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(PersistenceApplicationContext.class, args);
	}

}
