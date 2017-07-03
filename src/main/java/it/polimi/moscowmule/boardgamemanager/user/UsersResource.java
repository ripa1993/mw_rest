package it.polimi.moscowmule.boardgamemanager.user;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Viewable;

import it.polimi.moscowmule.boardgamemanager.authentication.Authenticator;
import it.polimi.moscowmule.boardgamemanager.game.Game;
import it.polimi.moscowmule.boardgamemanager.play.Play;
import it.polimi.moscowmule.boardgamemanager.play.PlayStorage;

// resource relative to a group of users
@Path("/users")
public class UsersResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	private final static Logger log = Logger.getLogger(UsersResource.class.getName());

	// application
	// /users?filter=all&value=all&orderby=id&order=asc
	// legal parameters
	// filter = name, country, state, town
	// value = anything
	// if value = "" or filter = "" then no filtering is performed
	// orderby = name, country, state, town
	// if no valid orderby is specified then default "id" is used
	// order = *, desc
	// if desc then descending, otherwise ascending
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getUsers(@DefaultValue("") @QueryParam("id") String id,
			@DefaultValue("") @QueryParam("name") String name, @DefaultValue("") @QueryParam("country") String country,
			@DefaultValue("") @QueryParam("state") String state, @DefaultValue("") @QueryParam("town") String town,

			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		// new list of users
		List<User> users = new ArrayList<User>();
		users.addAll(UserStorage.instance.getModel().values());

		if (!id.equals("")) {
			log.info("Filtering id");
			users.removeIf(u -> !u.getId().contains(id));
		}

		if (!name.equals("")) {
			log.info("Filtering name");
			users.removeIf(u -> !u.getName().contains(name));
		}

		if (!country.equals("")) {
			log.info("Filtering country");
			users.removeIf(u -> !u.getCountry().contains(country));
		}

		if (!state.equals("")) {
			log.info("Filtering state");
			users.removeIf(u -> !u.getState().contains(state));

		}

		if (!town.equals("")) {
			log.info("Filtering town");
			users.removeIf(u -> !u.getTown().equals(town));

		}

		// order
		if (!orderby.equals("") || !orderby.equals("id")) {
			Comparator<User> comp;
			switch (orderby) {
			case "name":
				comp = (User a, User b) -> a.getName().compareTo(b.getName());
				break;

			case "country":
				comp = (User a, User b) -> a.getCountry().compareTo(b.getCountry());
				break;

			case "state":
				comp = (User a, User b) -> a.getState().compareTo(b.getState());
				break;

			case "town":
				comp = (User a, User b) -> a.getTown().compareTo(b.getTown());
				break;
			default:
				comp = (User a, User b) -> a.getId().compareTo(b.getId());
				break;
			}
			Collections.sort(users, comp);
		}

		// if descending
		if (order.equals("desc")) {
			Collections.reverse(users);
		}
		
		GenericEntity<List<User>> usersGeneric = new GenericEntity<List<User>>(users){};

		return Response.ok(usersGeneric).build();
	}

	// browser
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getUsersBrowser(@DefaultValue("") @QueryParam("id") String id,
			@DefaultValue("") @QueryParam("name") String name, @DefaultValue("") @QueryParam("country") String country,
			@DefaultValue("") @QueryParam("state") String state, @DefaultValue("") @QueryParam("town") String town,

			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		// new list of users
		List<User> users = new ArrayList<User>();
		users.addAll(UserStorage.instance.getModel().values());

		if (!id.equals("")) {
			log.info("Filtering id");
			users.removeIf(u -> !u.getId().contains(id));
		}

		if (!name.equals("")) {
			log.info("Filtering name");
			users.removeIf(u -> !u.getName().contains(name));
		}

		if (!country.equals("")) {
			log.info("Filtering country");
			users.removeIf(u -> !u.getCountry().contains(country));
		}

		if (!state.equals("")) {
			log.info("Filtering state");
			users.removeIf(u -> !u.getState().contains(state));

		}

		if (!town.equals("")) {
			log.info("Filtering town");
			users.removeIf(u -> !u.getTown().equals(town));

		}

		// order
		if (!orderby.equals("") || !orderby.equals("id")) {
			Comparator<User> comp;
			switch (orderby) {
			case "name":
				comp = (User a, User b) -> a.getName().compareTo(b.getName());
				break;

			case "country":
				comp = (User a, User b) -> a.getCountry().compareTo(b.getCountry());
				break;

			case "state":
				comp = (User a, User b) -> a.getState().compareTo(b.getState());
				break;

			case "town":
				comp = (User a, User b) -> a.getTown().compareTo(b.getTown());
				break;
			default:
				comp = (User a, User b) -> a.getId().compareTo(b.getId());
				break;
			}
			Collections.sort(users, comp);
		}

		// if descending
		if (order.equals("desc")) {
			Collections.reverse(users);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", users);
		return Response.ok(new Viewable("/user_list", map)).build();
	}

	// count of users
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getCount() {
		int count = UserStorage.instance.getModel().size();
		return Response.ok(String.valueOf(count)).build();
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newUser(@FormParam("id") String id, @FormParam("password") String password,
			@FormParam("name") String name, @FormParam("mail") String mail, @FormParam("country") String country,
			@FormParam("state") String state, @FormParam("town") String town,
			@Context HttpServletResponse servletResponse) throws IOException {
		/*
		 * Check if ID already exists
		 */
		if (UserStorage.instance.getModel().containsKey(id)) {
			servletResponse.sendRedirect("../create_user.html");
			return Response.status(Status.BAD_REQUEST).build();
		}
		/*
		 * Create user instance
		 */
		User user = new User(id, name);
		UserStorage.instance.getModel().put(id, user);
		if (mail != null) {
			user.setMail(mail);
		}
		if (country != null) {
			user.setCountry(country);
		}
		if (state != null) {
			user.setState(state);
		}
		if (town != null) {
			user.setTown(town);
		}
		/*
		 * Create new login information
		 */
		Authenticator authenticator = Authenticator.getInstance();
		authenticator.create(id, password);
		servletResponse.sendRedirect("../rest/users");
		return Response.created(URI.create("http://localhost:8080/boardgamemanager/rest/users/" + id)).build();
	}

	@Path("{user}")
	public UserResource getUser(@PathParam("user") String id) {
		return new UserResource(uriInfo, request, id);
	}

	@GET
	@Path("{user}/plays")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response showPlays(@PathParam("user") String id, @DefaultValue("") @QueryParam("date") String date,
			@DefaultValue("") @QueryParam("game") String game,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		List<Play> plays = new ArrayList<Play>();
		Iterator<Play> it = PlayStorage.instance.getModel().values().iterator();
		while (it.hasNext()) {
			Play p = it.next();
			if (p.getUserId().equals(id)) {
				plays.add(p);
			}
		}
		// filter
		if (!date.equals("")) {
			log.info("Filtering date");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date dateD;
			try {
				dateD = df.parse(date);
				plays.removeIf(p -> !p.getDate().equals(dateD));
			} catch (ParseException e) {
				// do nothing if date is not valid
			}
		}
		if (!game.equals("")) {
			log.info("Filtering game");
			plays.removeIf(p -> !p.getGameId().equals(game));
		}

		// order
		if (!orderby.equals("") || !orderby.equals("id")) {
			Comparator<Play> comp = (Play a, Play b) -> a.getId().compareTo(b.getId());
			switch (orderby) {
			case "date":
				comp = (Play a, Play b) -> a.getDate().compareTo(b.getDate());
				break;

			case "game":
				comp = (Play a, Play b) -> a.getGameId().compareTo(b.getGameId());
				break;
			}
			Collections.sort(plays, comp);
		}

		// if descending
		if (order.equals("desc")) {
			Collections.reverse(plays);
		}
		
		GenericEntity<List<Play>> playsGeneric = new GenericEntity<List<Play>>(plays){};

		return Response.ok(playsGeneric).build();
	}

	@GET
	@Path("{user}/plays")
	@Produces(MediaType.TEXT_HTML)
	public Response showPlaysBrowser(@PathParam("user") String id, @DefaultValue("") @QueryParam("date") String date,
			@DefaultValue("") @QueryParam("game") String game,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		List<Play> plays = new ArrayList<Play>();
		Iterator<Play> it = PlayStorage.instance.getModel().values().iterator();
		while (it.hasNext()) {
			Play p = it.next();
			if (p.getUserId().equals(id)) {
				plays.add(p);
			}
		}
		// filter
		if (!date.equals("")) {
			log.info("Filtering date");

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date dateD;
			try {
				dateD = df.parse(date);
				plays.removeIf(p -> !p.getDate().equals(dateD));
			} catch (ParseException e) {
				// do nothing if date is not valid
			}
		}
		if (!game.equals("")) {
			log.info("Filtering game");
			plays.removeIf(p -> !p.getGameId().equals(game));
		}

		// order
		if (!orderby.equals("") || !orderby.equals("id")) {
			Comparator<Play> comp = (Play a, Play b) -> a.getId().compareTo(b.getId());
			switch (orderby) {
			case "date":
				comp = (Play a, Play b) -> a.getDate().compareTo(b.getDate());
				break;

			case "game":
				comp = (Play a, Play b) -> a.getGameId().compareTo(b.getGameId());
				break;
			}
			Collections.sort(plays, comp);
		}

		// if descending
		if (order.equals("desc")) {
			Collections.reverse(plays);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("plays", plays);

		return Response.ok(new Viewable("/play_list", map)).build();
	}

}
