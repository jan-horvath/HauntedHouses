package cz.muni.fi.pa165.hauntedhouses;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class HauntedHousesApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(PersistenceApplicationContext.class, args);
	}

}
