package it.polimi.moscowmule.boardgamemanager.play;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that stores data using an hashmap, easily an external database is implementable
 *
 * @author Simone Ripamonti
 * @version 1
 */
public enum PlayStorage {
	/**
	 * Singleton instance
	 */
	instance;

	/**
	 * Stores in a hashmap [play_id, play]
	 */
	private Map<String, Play> contentProvider = new HashMap<>();
	
	private PlayStorage(){
		Play play = new Play("storna", "2", new Date());
		play.setNumPlayers(4);
		storePlay(play);
		Play play2 = new Play("ripa1993", "1", new Date());
		play2.setNumPlayers(3);
		play2.setTimeToComplete(60);
		play2.setWinnerId("storna");
		storePlay(play2);
		Play play3 = new Play("rpressiani", "4", new Date());
		play3.setNumPlayers(2);
		play3.setTimeToComplete(120);
		play3.setWinnerId("ripa1993");
		storePlay(play3);
	}
	
	/**
	 * Stores a play
	 * @param play
	 */
	public void storePlay(Play play){
		contentProvider.put(play.getId(), play);
	}
	
	/**
	 * Retireves the count of plays
	 * @return
	 */
	public int getCount(){
		return contentProvider.size();
	}
	
	/**
	 * Retrieves a play
	 * @param id
	 * @return possible null pointer
	 */
	public Play getPlay(String id){
		return contentProvider.get(id);
	}
	
	/**
	 * Retrieves all the plays in storage
	 * @return
	 */
	public Collection<Play> getAllPlays(){
		return contentProvider.values();
	}
}
