package it.polimi.moscowmule.boardgamemanager.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class that stores data using an hashmap, easily an external database is implementable
 *
 * @author Simone Ripamonti
 * @version 1
 */public enum UserStorage {
	/**
	 * Singleton instance
	 */
	instance;

	/**
	 * Hashmap that stores [user_id, user]
	 */
	private Map<String, User> contentProvider = new HashMap<>();
	

	private UserStorage() {
		User user = new User("ripa1993", "Simone");
		user.setCountry("Italy");
		user.setState("CO");
		user.setTown("Guanzate");
		user.setMail("simone@mail.com");
		contentProvider.put(user.getId(), user);
		User user2 = new User("storna", "Luca");
		user2.setCountry("Italy");
		user2.setState("BG");
		user2.setTown("Villa d'Adda");
		user2.setMail("storna@polimi.it");
		contentProvider.put(user2.getId(), user2);
		User user3 = new User("rpressiani", "Riccardo");
		user3.setCountry("Italy");
		user3.setMail("rpressiani@example.com");
		contentProvider.put(user3.getId(), user3);
	}

	public void storeUser(User user){
		contentProvider.put(user.getId(), user);
	}
	
	public int getCount(){
		return contentProvider.size();
	}
	
	public Set<String> getAllIds(){
		return contentProvider.keySet();
	}
	
	public User getUser(String id){
		return contentProvider.get(id);
	}
	
	public Collection<User> getAllUsers(){
		return contentProvider.values();
	}
	
	public boolean existsId(String id){
		return contentProvider.containsKey(id);
	}
}
