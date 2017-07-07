package it.polimi.moscowmule.boardgamemanager.game;

import static it.polimi.moscowmule.boardgamemanager.utils.Constants.BASE_URL;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Representation of a board game
 *
 * @author Simone Ripamonti
 * @version 1
 */
@XmlRootElement
@XmlType(propOrder = { "id", "name", "minPlayers", "maxPlayers", "playTime", "minAge", "difficulty", "designer",
		"artist", "publisher", "coverArt", "uri" })
public class Game {
	/**
	 * Used to generate unique ID
	 */
	private static Integer counter = 1;
	/**
	 * Id of the game
	 */
	private String id;
	/**
	 * Name of the game
	 */
	private String name;
	/**
	 * Minimum number of players
	 */
	private int minPlayers;
	/**
	 * Maximum number of players
	 */
	private int maxPlayers;
	/**
	 * Time needed to play the game, in minutes
	 */
	private int playTime;
	/**
	 * Suggested minimum age to play the game
	 */
	private int minAge;
	/**
	 * Difficulty of the game: 1.00 = easy, 5.00 = hard
	 */
	private float difficulty;
	/**
	 * Comma separated list of designer names
	 */
	private String designer;
	/**
	 * Comma separated list of artists
	 */
	private String artist;
	/**
	 * Comma separated list of publishers
	 */
	private String publisher;
	/**
	 * URL to cover art
	 */
	private String coverArt;
	/**
	 * URL to this game
	 */
	private String uri;

	public Game() {

	}

	public Game(String name) {
		synchronized(Game.counter){
			this.id = counter.toString();
			counter++;
		}
		this.name = name;
		this.uri = BASE_URL+"rest/games/"+id;
		this.coverArt = BASE_URL+"rest/img/"+id+".jpg";
		this.minPlayers = 0;
		this.maxPlayers = 99;
		this.playTime = 0;
		this.minAge = 0;
		this.difficulty = 0f;
		this.designer="";
		this.artist="";
		this.publisher="";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public int getPlayTime() {
		return playTime;
	}

	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public float getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}

	public String getDesigner() {
		return designer;
	}

	public void setDesigner(String designer) {
		this.designer = designer;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getCoverArt() {
		return coverArt;
	}

	public void setCoverArt(String coverArt) {
		this.coverArt = coverArt;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
