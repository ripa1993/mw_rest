package it.polimi.moscowmule.boardgamemanager.authentication;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.security.auth.login.LoginException;

public class Authenticator {
	private static Authenticator authenticator = null;
	
	// Stores <username, password>
	private final Map<String, String> usersStorage = new HashMap<String,String>();
	// Stores <auth_token, username>
	private final Map<String, String> authorizationTokensStorage = new HashMap<String, String>();

	private Authenticator(){
		// usersStorage
		usersStorage.put("1", "password");
		usersStorage.put("2", "password");
		usersStorage.put("3", "password");
	}
	
	public static Authenticator getInstance(){
		if(authenticator==null){
			authenticator = new Authenticator();
		}
		
		return authenticator;
	}

	/*
	 * Validates an user, createing an authorization token
	 */
	public String login(String username, String password) throws LoginException{
		if (usersStorage.containsKey(username)){
			if (usersStorage.get(username).equals(password)){
				/* 
				 * Valid username and password
				 * Generate an authorization token
				 * Use it for next invocations
				 */
				String authToken = UUID.randomUUID().toString();
				authorizationTokensStorage.put(authToken, username);
				/*
				 * TODO: remove old auth token, eg in case of two consecutive logins
				 */
				
				return authToken;
			}
		}
		throw new LoginException("Invalid credentials");
	}
	
	/*
	 * Check if the client that invokes the REST API is authorized
	 */
	public boolean isAuthTokenValid(String authToken, String username){
		if(authorizationTokensStorage.containsKey(authToken)){
			if(authorizationTokensStorage.get(authToken).equals(username)){
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Logout, removes the authorization token
	 */
	public void logout(String authToken, String username) throws GeneralSecurityException{
		if(authorizationTokensStorage.containsKey(authToken)){
			if(authorizationTokensStorage.get(authToken).equals(username)){
				authorizationTokensStorage.remove(authToken);
			}
		}
		throw new GeneralSecurityException("Invalid pair username and auth token");
	}
}
