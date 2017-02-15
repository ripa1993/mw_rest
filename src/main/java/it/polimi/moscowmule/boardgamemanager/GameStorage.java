package it.polimi.moscowmule.boardgamemanager;

import java.util.HashMap;
import java.util.Map;

public enum GameStorage {
	instance;
	
	private Map<String, Game> contentProvider = new HashMap<>();
	
	private GameStorage(){
		Game game = new Game("1", "Risk");
		contentProvider.put("1", game);
		Game game2 = new Game("2", "Terra Mystica: Gaia Project");
		game2.setMinPlayers(1);
		game2.setMaxPlayers(4);
		game2.setPlayTime(60);
		game2.setMinAge(12);
		game2.setDifficulty(4.00f);
		game2.setDesigner("Jens Drögemüller, Helge Ostertag");
		game2.setArtist("Dennis Lohausen");
		game2.setPublisher("Asmodee, Feuerland Spiele");
		contentProvider.put("2", game2);
		
	}
	
	public Map<String, Game> getModel() {
		return contentProvider;
	}
}
