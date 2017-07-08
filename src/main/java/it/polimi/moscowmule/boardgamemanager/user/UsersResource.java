package it.polimi.moscowmule.boardgamemanager.user;

import static it.polimi.moscowmule.boardgamemanager.utils.Constants.BASE_URL;

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
import it.polimi.moscowmule.boardgamemanager.play.Play;
import it.polimi.moscowmule.boardgamemanager.play.PlayStorage;
import it.polimi.moscowmule.boardgamemanager.utils.Message;

// resource relative to a group of users
@Path("/users")
public class UsersResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	private final static Logger log = Logger.getLogger(UsersResource.class.getName());

	/**
	 * Retrieves list of users
	 * @param id of the user
	 * @param name of the user
	 * @param country of the user
	 * @param state of the user
	 * @param town of the user
	 * @param orderby value in [id, name, country, state, town]
	 * @param order value in [asc, desc]
	 * @return	OK: list of users {@link User}
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getUsersApp(@DefaultValue("") @QueryParam("id") String id,
			@DefaultValue("") @QueryParam("name") String name, @DefaultValue("") @QueryParam("country") String country,
			@DefaultValue("") @QueryParam("state") String state, @DefaultValue("") @QueryParam("town") String town,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		List<User> users = getUsersCommon(id, name, country, state, town, orderby, order);
		GenericEntity<List<User>> usersGeneric = new GenericEntity<List<User>>(users){};
		return Response.ok(usersGeneric).build();
	}

	/**
	 * Retrieves list of users
	 * @param id of the user
	 * @param name of the user
	 * @param country of the user
	 * @param state of the user
	 * @param town of the user
	 * @param orderby value in [id, name, country, state, town]
	 * @param order value in [asc, desc]
	 * @return	OK: html representation of the list of users
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getUsersBrowser(@DefaultValue("") @QueryParam("id") String id,
			@DefaultValue("") @QueryParam("name") String name, @DefaultValue("") @QueryParam("country") String country,
			@DefaultValue("") @QueryParam("state") String state, @DefaultValue("") @QueryParam("town") String town,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		List<User> users = getUsersCommon(id, name, country, state, town, orderby, order);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", users);
		return Response.ok(new Viewable("/user_list", map)).build();
	}

	/**
	 * Retrieves a list of user
	 * @param id
	 * @param name
	 * @param country
	 * @param state
	 * @param town
	 * @param orderby
	 * @param order
	 * @return
	 */
	private List<User> getUsersCommon(String id, String name, String country, String state, String town, String orderby, String order) {
		// new list of users
		List<User> users = new ArrayList<User>();
		users.addAll(UserStorage.instance.getAllUsers());

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
		return users;
	}

	/**
	 * Retrieves the count of all user
	 * @return
	 */
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getCount() {
		int count = UserStorage.instance.getCount();
		return Response.ok(String.valueOf(count)).build();
	}

	/**
	 * 
	 * @param id username REQUIRED
	 * @param password REQUIRED
	 * @param name REQUIRED
	 * @param mail
	 * @param country
	 * @param state
	 * @param town
	 * @param servletResponse
	 * @return	BAD_REQUEST: if something went wrong
	 * 			CREATED: the created resource
	 * @throws IOException
	 */
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newUserBrowser(@FormParam("id") String id, @FormParam("password") String password,
			@FormParam("name") String name, @FormParam("mail") String mail, @FormParam("country") String country,
			@FormParam("state") String state, @FormParam("town") String town,
			@Context HttpServletResponse servletResponse) throws IOException {
		
		Message errorMessages = checkErrorNewUser(id, password, name);
		/*
		 * Abort if errors occurred 
		 */
		if(errorMessages.getErrors().size() > 0){
			log.info("errors:" + errorMessages.getErrors().size());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("errors", errorMessages.getErrors());
			return Response.status(Status.BAD_REQUEST).entity(new Viewable("/register", map)).build();
		}
		
		User user = storeUser(id, password, name, mail, country, state, town);
		servletResponse.sendRedirect(BASE_URL);
		return Response.created(URI.create(user.getUri())).build();
	}
	
	/**
	 * 
	 * @param id username REQUIRED
	 * @param password REQUIRED
	 * @param name REQUIRED
	 * @param mail
	 * @param country
	 * @param state
	 * @param town
	 * @param servletResponse
	 * @return	BAD_REQUEST: message with errors {@link Message}
	 * 			CREATED: the created resource {@link User}
	 * @throws IOException
	 */
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newUserApp(@FormParam("id") String id, @FormParam("password") String password,
			@FormParam("name") String name, @FormParam("mail") String mail, @FormParam("country") String country,
			@FormParam("state") String state, @FormParam("town") String town,
			@Context HttpServletResponse servletResponse) throws IOException {
		
		Message errorMessages = checkErrorNewUser(id, password, name);
		/*
		 * Abort if errors occurred 
		 */
		if(errorMessages.getErrors().size() > 0){
			return Response.status(Status.BAD_REQUEST).entity(errorMessages).build();
		}
		
		/*
		 * Create user instance
		 */
		User user = storeUser(id, password, name, mail, country, state, town);
		return Response.created(URI.create(user.getUri())).entity(user).build();
	}

	/**
	 * Chcek if there are errros when creating a user with this parameters
	 * @param id
	 * @param password
	 * @param name
	 * @return
	 */
	private Message checkErrorNewUser(String id, String password, String name) {
		Message errorMessages = new Message();
		
		if( id == null){
			errorMessages.getErrors().add("Please insert id!");
		} else if ( id.length() == 0){
			errorMessages.getErrors().add("Id field cannot be empty!");
		} else if (UserStorage.instance.existsId(id)) {
			errorMessages.getErrors().add("Id already in use");
		}
		
		if(name == null){
			errorMessages.getErrors().add("Please insert a name!");
		} else if (name.length() == 0){
			errorMessages.getErrors().add("Name field cannot be empty!");
		}

		if(password == null){
			errorMessages.getErrors().add("Please insert a password!");
		} else if (password.length() == 0){
			errorMessages.getErrors().add("Password field cannot be empty!");
		}
		
		return errorMessages;
	}

	/**
	 * Create and store the user using this parameters
	 * @param id
	 * @param password
	 * @param name
	 * @param mail
	 * @param country
	 * @param state
	 * @param town
	 * @return
	 */
	private User storeUser(String id, String password, String name, String mail, String country, String state,
			String town) {
		
		User user = new User(id, name);
		UserStorage.instance.storeUser(user);
		
		/*
		 * Complete optional fields
		 */
		if (mail != null && mail.length()>0) {
			user.setMail(mail);
		}
		if (country != null && country.length()>0) {
			user.setCountry(country);
		}
		if (state != null && state.length()>0) {
			user.setState(state);
		}
		if (town != null && town.length()>0) {
			user.setTown(town);
		}
		
		/*
		 * Create new login information
		 */
		Authenticator authenticator = Authenticator.getInstance();
		authenticator.create(id, password);
		return user;
	}

	/**
	 * Resource representing a single user
	 * @param id
	 * @return
	 */
	@Path("{user}")
	public UserResource getUser(@PathParam("user") String id) {
		return new UserResource(uriInfo, request, id);
	}

	/**
	 * Plays of a given user
	 * @param id of the user REQUIRED
	 * @param date
	 * @param game
	 * @param orderby value in [date, game]
	 * @param order value in [asc, desc]
	 * @return	NOT_FOUND: if the user doesnt exists {@link Message}
	 * 			OK: the requested user plays {@link Play}
	 */
	@GET
	@Path("{user}/plays")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response showPlaysApp(@PathParam("user") String id, @DefaultValue("") @QueryParam("date") String date,
			@DefaultValue("") @QueryParam("game") String game,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		
		if(UserStorage.instance.equals(id)){
			List<Play> plays = getPlaysCommon(id, date, game, orderby, order);
			GenericEntity<List<Play>> playsGeneric = new GenericEntity<List<Play>>(plays){};
			return Response.ok(playsGeneric).build();
		} else {
			return Response.status(Status.NOT_FOUND).entity(new Message("User not found")).build();
		}
		

	}

	/**
	 * Plays of a given user
	 * @param id of the user REQUIRED
	 * @param date
	 * @param game
	 * @param orderby value in [date, game]
	 * @param order value in [asc, desc]
	 * @return	NOT_FOUND: if the user doesnt exists 
	 * 			OK: the requested user plays 
	 */
	@GET
	@Path("{user}/plays")
	@Produces(MediaType.TEXT_HTML)
	public Response showPlaysBrowser(@PathParam("user") String id, @DefaultValue("") @QueryParam("date") String date,
			@DefaultValue("") @QueryParam("game") String game,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(UserStorage.instance.existsId(id)){
			List<Play> plays = getPlaysCommon(id, date, game, orderby, order);
			map.put("plays", plays);
			return Response.ok(new Viewable("/play_list", map)).build();
		} else {
			map.put("not_found", true);
			return Response.status(Status.NOT_FOUND).entity(new Viewable("/user_detail", map)).build();
		}

	}

	/**
	 * Retrieves plays of a user
	 * @param id
	 * @param date
	 * @param game
	 * @param orderby
	 * @param order
	 * @return
	 */
	private List<Play> getPlaysCommon(String id, String date, String game, String orderby, String order) {
		List<Play> plays = new ArrayList<Play>();
		Iterator<Play> it = PlayStorage.instance.getAllPlays().iterator();
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
		return plays;
	}

}
