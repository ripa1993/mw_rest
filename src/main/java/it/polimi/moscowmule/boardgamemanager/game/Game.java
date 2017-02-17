package it.polimi.moscowmule.boardgamemanager.game;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "id", "name", "minPlayers", "maxPlayers", "playTime", "minAge", "difficulty", "designer",
		"artist", "publisher", "coverArt", "uri" })
public class Game {
	private String id;
	private String name;
	private int minPlayers;
	private int maxPlayers;
	private int playTime; // minutes
	private int minAge;
	private float difficulty; // 1.00 easy -> 5.00 hard
	private String designer;
	private String artist;
	private String publisher;
	private String coverArt;
	private String uri;

	public Game() {

	}

	public Game(String id, String name) {
		this.id = id;
		this.name = name;
		this.uri = "http://localhost:8080/boardgamemanager/rest/games/"+id;
		this.coverArt = "http://localhost:8080/boardgamemanager/rest/img/"+id+".jpg";
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
