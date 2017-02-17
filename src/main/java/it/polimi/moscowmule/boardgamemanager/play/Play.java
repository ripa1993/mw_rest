package it.polimi.moscowmule.boardgamemanager.play;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder={"id", "userId", "gameId", "date", "timeToComplete", "numPlayers", "winnerId", "uri", "userUri", "gameUri", "winnerUri"})
public class Play {
	private String id;
	private String userId;
	private String gameId;
	private Date date;
	private int timeToComplete; // minutes
	private int numPlayers; // that partecipated
	private String winnerId; // that won the game
	private String uri;
	private String userUri;
	private String gameUri;
	private String winnerUri;

	public Play() {

	}

	public Play(String id, String userId, String gameId, Date date) {
		this.setId(id);
		this.userId = userId;
		this.gameId = gameId;
		this.date = date;
		this.setUri("http://localhost:8080/boardgamemanager/rest/plays/"+id);
		this.setUserUri("http://localhost:8080/boardgamemanager/rest/users/"+userId);
		this.setGameUri("http://localhost:8080/boardgamemanager/rest/games/"+gameId);

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getTimeToComplete() {
		return timeToComplete;
	}

	public void setTimeToComplete(int timeToComplete) {
		this.timeToComplete = timeToComplete;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public String getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(String winnerId) {
		this.winnerId = winnerId;
		this.setWinnerUri("http://localhost:8080/boardgamemanager/rest/users/"+winnerId);
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUserUri() {
		return userUri;
	}

	public void setUserUri(String userUri) {
		this.userUri = userUri;
	}

	public String getGameUri() {
		return gameUri;
	}

	public void setGameUri(String gameUri) {
		this.gameUri = gameUri;
	}

	public String getWinnerUri() {
		return winnerUri;
	}

	public void setWinnerUri(String winnerUri) {
		this.winnerUri = winnerUri;
	}
}
