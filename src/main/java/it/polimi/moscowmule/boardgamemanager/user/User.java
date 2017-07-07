package it.polimi.moscowmule.boardgamemanager.user;

import static it.polimi.moscowmule.boardgamemanager.utils.Constants.BASE_URL;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Representation of an user
 *
 * @author Simone Ripamonti
 * @version 1
 */
@XmlRootElement
@XmlType(propOrder={"id", "name", "mail", "country", "state", "town", "uri", "playsUri"})
public class User {
	/**
	 * Username
	 */
	private String id;
	/**
	 * Name
	 */
	private String name;
	/**
	 * Country
	 */
	private String country;
	/**
	 * State
	 */
	private String state;
	/**
	 * Town
	 */
	private String town;
	/**
	 * Email
	 */
	private String mail;
	/**
	 * URL to this user
	 */
	private String uri;
	/**
	 * URL to user's plays
	 */
	private String playsUri;

	public User() {
	}

	public User(String id, String name) {
		this.id = id;
		this.name = name;
		this.uri = BASE_URL +"rest/users/"+id;
		this.playsUri = BASE_URL+"rest/users/"+id+"/plays";
		this.country = "N/A";
		this.state = "N/A";
		this.town = "N/A";
		this.mail = "N/A";
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
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPlaysUri() {
		return playsUri;
	}

	public void setPlaysUri(String playsUri) {
		this.playsUri = playsUri;
	}
}
