package it.polimi.moscowmule.boardgamemanager.user;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

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
	public User getUser() {
		User user = UserStorage.instance.getModel().get(id);
		if (user == null)
			throw new RuntimeException("Get: User with " + id + " not found");
		return user;
	}

	// browser
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getUserBrowser() {
		User user = UserStorage.instance.getModel().get(id);
		if (id == null)
			throw new RuntimeException("Get: User with " + id + " not found");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		return Response.ok(new Viewable("/user_detail", map)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putUser(JAXBElement<User> user) {
		User u = user.getValue();
		Response res;
		if (UserStorage.instance.getModel().containsKey(u.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		UserStorage.instance.getModel().put(u.getId(), u);
		return res;
	}

	@DELETE
	public void deleteUser() {
		User u = UserStorage.instance.getModel().remove(id);
		if (u == null)
			throw new RuntimeException("Delete: User with " + id + " not found");
	}
}
