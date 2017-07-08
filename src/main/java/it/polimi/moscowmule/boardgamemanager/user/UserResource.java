package it.polimi.moscowmule.boardgamemanager.user;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Viewable;

import it.polimi.moscowmule.boardgamemanager.utils.Message;

/**
 * Resource representing a single user
 * 
 * @author Simone Ripamonti
 * @version 1
 */
public class UserResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	/**
	 * The username
	 */
	String id;

	public UserResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

	/**
	 * Retrieves the user
	 * @return	OK: XML or JSON representation of the user {@link User}
	 * 			NOT_FOUND: error message {@link Message}
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getUser() {
		User user = UserStorage.instance.getUser(id);
		if (user == null)
			return Response.status(Status.NOT_FOUND).entity(new Message("UserId not found")).build();
		return Response.ok(user).build();
	}

	/**
	 * Retrieves the user
	 * @return 	OK: HTML representation of the user
	 * 			NOT_FOUND: 404 page
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getUserBrowser() {
		User user = UserStorage.instance.getUser(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		if (user == null){
			map.put("not_found", true);
			return Response.status(Status.NOT_FOUND).entity(new Viewable("/user_detail", map)).build();
		} else {
			return Response.ok(new Viewable("/user_detail", map)).build();
		}
	}
}
