package it.polimi.moscowmule.boardgamemanager.authentication;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.security.auth.login.LoginException;

/**
 * This class handles the authentication of users. User storage is non persistent by design, but is easy to extend the class
 * in order to connect to a database in order to achieve persistent storage.
 * <br>
 * The class is composed by two hashmaps, that can be easily converted into a single database table.
 * One hashmap is in charge of storing usernames and passwords, the other one authorization tokens and usernames.
 * Passwords are stored hashed in MD5
 * <br>
 * @author Simone Ripamonti
 * @version 2
 */
public class Authenticator {
	/**
	 * Singleton instance
	 */
	private static Authenticator authenticator = null;
	/**
	 * Contains [username -> password hash]
	 */
	private final Map<String, String> usersStorage = new HashMap<String, String>();
	/**
	 * Contains [auth token -> username]
 	 */
	private final Map<String, String> authorizationTokensStorage = new HashMap<String, String>();
	/**
	 * List of poweruser's username
 	 */
	private final List<String> powerUsers = new ArrayList<String>();
	/**
	 * Private constructor
	 */
	private Authenticator() {
		// dummy data
		create("ripa1993", "password");
		create("storna", "password");
		create("rpressiani", "password");
		powerUsers.add("ripa1993");
	}

	/**
	 * Singleton instance getter
	 * @return singleton
	 */
	public static Authenticator getInstance() {
		if (authenticator == null) {
			authenticator = new Authenticator();
		}

		return authenticator;
	}

	/**
	 * Validates an user, createing an authorization token
	 * @param username
	 * @param password
	 * @return the user authorization token
	 * @throws LoginException if credentials are invalid
	 */
	public String login(String username, String password) throws LoginException {
		if (usersStorage.containsKey(username)) {
			if (usersStorage.get(username).equals(getMD5Hash(password))) {
				/*
				 * Valid username and password Generate an authorization token
				 * Use it for next invocations
				 */
				String authToken = UUID.randomUUID().toString();
				authorizationTokensStorage.put(authToken, username);
				return authToken;
			}
		}
		throw new LoginException("Invalid credentials");
	}

	/**
	 * Check if the client that invokes the REST API is authorized
	 * @param authToken
	 * @return true if valid, false otherwise
	 */
	public boolean isAuthTokenValid(String authToken) {
		if (authorizationTokensStorage.containsKey(authToken)) {
			return true;
		}
		return false;
	}

	/**
	 * Logout, removes the authorization token
	 * @param authToken
	 * @throws GeneralSecurityException if the provided auth token is not valid
	 */
	public void logout(String authToken) throws GeneralSecurityException {
		if (authorizationTokensStorage.containsKey(authToken)) {
			authorizationTokensStorage.remove(authToken);
		} else {
			throw new GeneralSecurityException("Invalid auth token");
		}
	}

	/**
	 * Returns wheter an auth token is associated to a power user
	 * @param authToken
	 * @return true if the user associated with this token is power user, otherwise false
	 */
	public boolean isPowerUserToken(String authToken) {
		String username = authorizationTokensStorage.get(authToken);
		if (powerUsers.contains(username)) {
			return true;
		}
		return false;
	}

	/**
	 * Create new standard user
	 * @param id username of the user
	 * @param password
	 */
	public void create(String id, String password) {
		usersStorage.put(id, getMD5Hash(password));
	}

	/**
	 * Finds the username given the token
	 * @param auth_token
	 * @return the user name if the token is valid, otherwise null
	 */
	public String getUserFromToken(String auth_token){
		return authorizationTokensStorage.get(auth_token);
	}

	/**
	 * Generates the MD5 hash of the provided string
	 * @param input the plaintext
	 * @return the hash
	 */
	private String getMD5Hash(String input){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return input;
		}
		byte[] messageDigest = md.digest(input.getBytes());
		BigInteger number = new BigInteger(1, messageDigest);
		String hashtext = number.toString(16);
		return hashtext;
	}
}
