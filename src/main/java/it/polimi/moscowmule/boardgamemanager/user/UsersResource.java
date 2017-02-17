package it.polimi.moscowmule.boardgamemanager.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import it.polimi.moscowmule.boardgamemanager.play.Play;
import it.polimi.moscowmule.boardgamemanager.play.PlayStorage;

// resource relative to a group of users
@Path("/users")
public class UsersResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// application
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		users.addAll(UserStorage.instance.getModel().values());
		return users;
	}

	// browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<User> getUsersBrowser() {
		List<User> users = new ArrayList<User>();
		users.addAll(UserStorage.instance.getModel().values());
		return users;
	}
	
	// TODO: filter by 
	
	// TODO: order by

	// count of users
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = UserStorage.instance.getModel().size();
		return String.valueOf(count);
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newUser(@FormParam("id") String id, @FormParam("name") String name, @FormParam("mail") String mail,
			@FormParam("country") String country, @FormParam("state") String state, @FormParam("town") String town,
			@Context HttpServletResponse servletResponse) throws IOException {
		User user = new User(id, name);
		UserStorage.instance.getModel().put(id, user);
		if(mail!=null){
			user.setMail(mail);
		}
		if(country!=null){
			user.setCountry(country);
		}
		if(state!=null){
			user.setState(state);
		}
		if(town!=null){
			user.setTown(town);
		}
		servletResponse.sendRedirect("../create_user.html");
	}

	@Path("{user}")
	public UserResource getUser(@PathParam("user") String id) {
		return new UserResource(uriInfo, request, id);
	}
	
	@GET
	@Path("{user}/plays")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Play> showPlays(@PathParam("user") String id){
		List<Play> plays = new ArrayList<Play>();
		Iterator<Play> it = PlayStorage.instance.getModel().values().iterator();
		while(it.hasNext()){
			Play p = it.next();
			if (p.getUserId().equals(id)){
				plays.add(p);
			}
		}
		return plays;
	}
	
	// TODO: filter by 
	
	// TODO: order by
	
}
