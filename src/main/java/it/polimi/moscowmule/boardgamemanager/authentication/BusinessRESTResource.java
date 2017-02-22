package it.polimi.moscowmule.boardgamemanager.authentication;

import java.security.GeneralSecurityException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class BusinessRESTResource implements BusinessRESTResourceProxy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1366051840424006310L;

	@Override
	public Response login(@Context HttpHeaders httpHeaders, @FormParam("username") String username,
			@FormParam("password") String password) {

		Authenticator authenticator = Authenticator.getInstance();

		try {
			String authToken = authenticator.login(username, password);
			JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
			jsonObjBuilder.add(HTTPHeaderNames.AUTH_TOKEN, authToken);
			JsonObject jsonObj = jsonObjBuilder.build();
			return Response.ok(jsonObj.toString()).build();
		} catch (LoginException e) {
			JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
			jsonObjBuilder.add("message", "Login is incorrect");
			JsonObject jsonObj = jsonObjBuilder.build();
			return Response.status(Status.UNAUTHORIZED).entity(jsonObj.toString()).build();
		}

	}

	@Override
	public Response demoGetMethod() {
		JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
		jsonObjBuilder.add("message", "Executed demoGetMethod");
		JsonObject jsonObj = jsonObjBuilder.build();
		return Response.ok(jsonObj.toString()).build();
	}

	@Override
	public Response demoPostMethod() {
		JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
		jsonObjBuilder.add("message", "Executed demoPostMethod");
		JsonObject jsonObj = jsonObjBuilder.build();
		return Response.ok(jsonObj.toString()).build();
	}

	@Override
	public Response logout(@Context HttpHeaders httpHeaders) {

		try {
			Authenticator authenticator = Authenticator.getInstance();
			String username = httpHeaders.getHeaderString(HTTPHeaderNames.USERNAME);
			String authToken = httpHeaders.getHeaderString(HTTPHeaderNames.AUTH_TOKEN);
			authenticator.logout(authToken, username);
			return Response.noContent().build();
		} catch (GeneralSecurityException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
	}

}
