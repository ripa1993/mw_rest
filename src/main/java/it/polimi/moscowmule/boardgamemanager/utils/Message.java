package it.polimi.moscowmule.boardgamemanager.utils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class used to contain single message or list of errors
 * @author Simone Ripamonti
 * @version 1
 */
@XmlRootElement
public class Message {
	private String message;
	private List<String> errors;

	public Message(){
		this.errors = new ArrayList<>();
	}
	
	public Message(String message){
		this.message = message;
		this.errors = new ArrayList<>();
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	
	
}
