package it.polimi.moscowmule.boardgamemanager.authentication;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class used to represent the authentication token of a user
 * @author Simone Ripamonti
 * @version 1
 */
@XmlRootElement
public class AuthToken {
	private String authToken;
	
	public AuthToken(){
		
	}
	
	public AuthToken(String authToken){
		this.authToken = authToken;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	
}
