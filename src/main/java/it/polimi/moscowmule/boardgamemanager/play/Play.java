package it.polimi.moscowmule.boardgamemanager.play;

import static it.polimi.moscowmule.boardgamemanager.utils.Constants.BASE_URL;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Representation of a boardgame play
 *
 * @author Simone Ripamonti
 * @version 1
 */
@XmlRootElement
@XmlType(propOrder = { "id", "userId", "gameId", "date", "timeToComplete", "numPlayers", "winnerId", "uri", "userUri",
		"gameUri", "winnerUri" })
public class Play {
	/**
	 * Used to generate unique IDs
	 */
	private static Integer counter = 1;
	/**
	 * The unique id of the plays
	 */
	private String id;
	/**
	 * The user id who created the play
	 */
	private String userId;
	/**
	 * The game id of the game played
	 */
	private String gameId;
	/**
	 * Date of the play
	 */
	private Date date;
	/**
	 * Time needed to complete the play, in minutes
	 */
	private int timeToComplete;
	/**
	 * Number of players that partecipated
	 */
	private int numPlayers;
	/**
	 * The user id of the player who won the game
	 */
	private String winnerId;
	/**
	 * URL to this play
	 */
	private String uri;
	/**
	 * URL to the user who created the play
	 */
	private String userUri;
	/**
	 * URL to the game played
	 */
	private String gameUri;
	/**
	 * URL to the user who won the play
	 */
	private String winnerUri;

	public Play() {

	}

	public Play(String userId, String gameId, Date date) {
		synchronized (Play.counter) {
			this.id = counter.toString();
			counter++;
		}
		this.userId = userId;
		this.gameId = gameId;
		this.date = date;
		this.setUri(BASE_URL+"rest/plays/" + id);
		this.setUserUri(BASE_URL+"rest/users/" + userId);
		this.setGameUri(BASE_URL+"rest/games/" + gameId);

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
		this.setWinnerUri(BASE_URL+"rest/users/" + winnerId);
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
