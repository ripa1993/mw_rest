package it.polimi.moscowmule.boardgamemanager;

import java.util.HashMap;
import java.util.Map;

public enum PlayStorage {
	instance;
	
	private Map<String, Play> contentProvider = new HashMap<>();
	
	private PlayStorage(){
		
	}
	
	public Map<String,Play> getModel(){
		return contentProvider;
	}
}
