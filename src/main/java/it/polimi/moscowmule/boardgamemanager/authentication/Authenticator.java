package it.polimi.moscowmule.boardgamemanager.authentication;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.security.auth.login.LoginException;

public class Authenticator {
	private static Authenticator authenticator = null;

	// Stores <username, password>
	private final Map<String, String> usersStorage = new HashMap<String, String>();
	// Stores <auth_token, username>
	private final Map<String, String> authorizationTokensStorage = new HashMap<String, String>();
	// List of power users
	private final List<String> powerUsers = new ArrayList<String>();

	private Authenticator() {
		// usersStorage
		usersStorage.put("1", "password");
		usersStorage.put("2", "password");
		usersStorage.put("3", "password");

		powerUsers.add("1");
	}

	public static Authenticator getInstance() {
		if (authenticator == null) {
			authenticator = new Authenticator();
		}

		return authenticator;
	}

	/*
	 * Validates an user, createing an authorization token
	 */
	public String login(String username, String password) throws LoginException {
		if (usersStorage.containsKey(username)) {
			if (usersStorage.get(username).equals(password)) {
				/*
				 * Valid username and password Generate an authorization token
				 * Use it for next invocations
				 */
				String authToken = UUID.randomUUID().toString();
				authorizationTokensStorage.put(authToken, username);
				/*
				 * TODO: remove old auth token, eg in case of two consecutive
				 * logins
				 */

				return authToken;
			}
		}
		throw new LoginException("Invalid credentials");
	}

	/*
	 * Check if the client that invokes the REST API is authorized
	 */
	public boolean isAuthTokenValid(String authToken) {
		if (authorizationTokensStorage.containsKey(authToken)) {
			return true;
		}
		return false;
	}

	/*
	 * Logout, removes the authorization token
	 */
	public void logout(String authToken) throws GeneralSecurityException {
		if (authorizationTokensStorage.containsKey(authToken)) {
			authorizationTokensStorage.remove(authToken);
		} else {
			throw new GeneralSecurityException("Invalid pair username and auth token");
		}
	}

	/*
	 * Returns wheter an auth token is associated to a power user
	 */
	public boolean isPowerUserToken(String authToken) {
		String username = authorizationTokensStorage.get(authToken);
		if (powerUsers.contains(username)) {
			return true;
		}
		return false;
	}
	
	/*
	 * Create new standard user
	 */
	public void create(String id, String password) {
		usersStorage.put(id, password);
	}
}
