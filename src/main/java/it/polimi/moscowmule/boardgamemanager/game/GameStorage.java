package it.polimi.moscowmule.boardgamemanager.game;

import java.util.HashMap;
import java.util.Map;

public enum GameStorage {
	instance;

	private Map<String, Game> contentProvider = new HashMap<>();

	/*
	 * Populating with dummy games
	 */
	private GameStorage(){
		
		Game game = new Game("Pandemic Legacy: Season 1");
		game.setMinPlayers(2);
		game.setMaxPlayers(4);
		game.setPlayTime(60);
		game.setMinAge(13);
		game.setDifficulty(2.80f);
		game.setDesigner("Rob Daviau, Matt Leacock");
		game.setArtist("Chris Quilliams");
		game.setPublisher("Z-Man Games");
		contentProvider.put(game.getId(), game);
		Game game2 = new Game("Terra Mystica: Gaia Project");
		game2.setMinPlayers(1);
		game2.setMaxPlayers(4);
		game2.setPlayTime(60);
		game2.setMinAge(12);
		game2.setDifficulty(4.00f);
		game2.setDesigner("Jens Drögemüller, Helge Ostertag");
		game2.setArtist("Dennis Lohausen");
		game2.setPublisher("Asmodee, Feuerland Spiele");
		contentProvider.put(game2.getId(), game2);
		Game game3 = new Game("Through the Ages: A New Story of Civilization");
		game3.setMinPlayers(2);
		game3.setMaxPlayers(4);
		game3.setPlayTime(200);
		game3.setMinAge(14);
		game3.setDifficulty(4.29f);
		game3.setDesigner("Vlaada Chvátil");
		game3.setArtist("Jakub Politzer, Milan Vavroň");
		game3.setPublisher("Cranio Creations");
		contentProvider.put(game3.getId(), game3);
		Game game4 = new Game("Twilight Struggle");
		game4.setMinPlayers(2);
		game4.setMaxPlayers(2);
		game4.setPlayTime(180);
		game4.setMinAge(13);
		game4.setDifficulty(3.53f);
		game4.setDesigner("Ananda Gupta, Jason Matthews");
		game4.setArtist("Viktor Csete, Rodger B. MacGowan, Chechu Nieto, Guillaume Ries, Mark Simonitch");
		game4.setPublisher("GMT Games");
		contentProvider.put(game4.getId(), game4);
		Game game5 = new Game("Star Wars: Rebellion");
		game5.setMinPlayers(2);
		game5.setMaxPlayers(4);
		game5.setPlayTime(200);
		game5.setMinAge(14);
		game5.setDifficulty(3.56f);
		game5.setDesigner("Corey Konieczka");
		game5.setPublisher("ADC Blackfire Entertainment");
		contentProvider.put(game5.getId(), game5);
		
	}

	public Map<String, Game> getModel() {
		return contentProvider;
	}
}
