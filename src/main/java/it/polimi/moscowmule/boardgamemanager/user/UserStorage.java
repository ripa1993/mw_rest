package it.polimi.moscowmule.boardgamemanager.user;

import java.util.HashMap;
import java.util.Map;

// based on TodoDao
public enum UserStorage {
	instance;

	private Map<String, User> contentProvider = new HashMap<>();

	private UserStorage() {
		User user = new User("1", "Simone");
		user.setCountry("Italy");
		user.setState("CO");
		user.setTown("Guanzate");
		user.setMail("simone@mail.com");
		contentProvider.put("1", user);
		User user2 = new User("2", "Luca");
		contentProvider.put("2", user2);
		User user3 = new User("3", "Riccardo");
		contentProvider.put("3", user3);
	}

	public Map<String, User> getModel() {
		return contentProvider;
	}
}
