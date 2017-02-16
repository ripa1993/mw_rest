package it.polimi.moscowmule.boardgamemanager;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder={"id", "name", "mail", "country", "state", "town", "uri"})
public class User {
	private String id;
	private String name;
	private String country;
	private String state;
	private String town;
	private String mail;
	private String uri;

	public User() {
	}

	public User(String id, String name) {
		this.id = id;
		this.name = name;
		this.uri = "http:////localhost:8080//boardgamemanager//rest//users//"+id;
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
}
