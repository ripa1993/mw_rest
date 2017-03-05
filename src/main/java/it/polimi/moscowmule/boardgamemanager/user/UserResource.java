package it.polimi.moscowmule.boardgamemanager.user;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Viewable;

// resource relative to a single user
public class UserResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;

	public UserResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

	// application
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getUser() {
		User user = UserStorage.instance.getModel().get(id);
		if (user == null)
			throw new RuntimeException("Get: User with " + id + " not found");
		return Response.ok(user).build();
	}

	// browser
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getUserBrowser() {
		User user = UserStorage.instance.getModel().get(id);
		if (user == null)
			throw new RuntimeException("Get: User with " + id + " not found");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		return Response.ok(new Viewable("/user_detail", map)).build();
	}
}
