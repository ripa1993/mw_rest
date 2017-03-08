package it.polimi.moscowmule.boardgamemanager.play;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public enum PlayStorage {
	instance;
	
	private Map<String, Play> contentProvider = new HashMap<>();
	
	private PlayStorage(){
		Play play = new Play("storna", "2", new Date());
		play.setNumPlayers(4);
		contentProvider.put(play.getId(), play);
		Play play2 = new Play("ripa1993", "1", new Date());
		play2.setNumPlayers(3);
		play2.setTimeToComplete(60);
		play2.setWinnerId("storna");
		contentProvider.put(play2.getId(), play2);
		Play play3 = new Play("rpressiani", "4", new Date());
		play3.setNumPlayers(2);
		play3.setTimeToComplete(120);
		play3.setWinnerId("ripa1993");
		contentProvider.put(play3.getId(), play3);
	}
	
	public Map<String,Play> getModel(){
		return contentProvider;
	}
}
