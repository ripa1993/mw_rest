package it.polimi.moscowmule.boardgamemanager.play;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public enum PlayStorage {
	instance;
	
	private Map<String, Play> contentProvider = new HashMap<>();
	
	private PlayStorage(){
		Play play = new Play("1", "1", "1", new Date());
		contentProvider.put("1", play);
	}
	
	public Map<String,Play> getModel(){
		return contentProvider;
	}
}
