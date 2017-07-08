package it.polimi.moscowmule.boardgamemanager.play;

import static it.polimi.moscowmule.boardgamemanager.utils.Constants.AUTH_TOKEN;

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
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
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
import it.polimi.moscowmule.boardgamemanager.game.GameStorage;
import it.polimi.moscowmule.boardgamemanager.user.UserStorage;
import it.polimi.moscowmule.boardgamemanager.utils.Message;

/**
 * Resource representing the list of plays
 *
 * @author Simone Ripamonti
 * @version 1
 */
@Path("/plays")
public class PlaysResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	private final static Logger log = Logger.getLogger(PlaysResource.class.getName());

	/**
	 * Retrieves a list of plays matching the criteria
	 * 
	 * @param date
	 *            of the play
	 * @param game
	 *            played
	 * @param orderby
	 *            value in [date, game]
	 * @param order
	 *            value in [asc, desc]
	 * @return OK: XML or JSON list of plays matching the criteria
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getPlaysApp(@DefaultValue("") @QueryParam("date") String date,
			@DefaultValue("") @QueryParam("game") String game,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		List<Play> plays = getPlaysCommon(date, game, orderby, order);
		GenericEntity<List<Play>> playsGeneric = new GenericEntity<List<Play>>(plays) {
		};
		return Response.ok(playsGeneric).build();
	}

	/**
	 * Retrieves a list of plays matching the criteria
	 * 
	 * @param date
	 *            of the play
	 * @param game
	 *            played
	 * @param orderby
	 *            value in [date, game]
	 * @param order
	 *            value in [asc, desc]
	 * @return 	OK: HTML list of plays matching the criteria
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getPlaysBrowser(@DefaultValue("") @QueryParam("date") String date,
			@DefaultValue("") @QueryParam("game") String game,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		List<Play> plays = getPlaysCommon(date, game, orderby, order);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("plays", plays);
		return Response.ok(new Viewable("/play_list", map)).build();
	}

	/**
	 * Retrieves a list of plays matching the criteria
	 * 
	 * @param date
	 *            of the play
	 * @param game
	 *            played
	 * @param orderby
	 *            value in [date, game]
	 * @param order
	 *            value in [asc, desc]
	 * @return list of {@link Play} matching criteria
	 */
	private List<Play> getPlaysCommon(@DefaultValue("") @QueryParam("date") String date,
			@DefaultValue("") @QueryParam("game") String game,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		List<Play> plays = new ArrayList<Play>();
		plays.addAll(PlayStorage.instance.getAllPlays());

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

	/**
	 * Retrieves the count of plays
	 * 
	 * @return OK: count of plays
	 */
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getCount() {
		int count = PlayStorage.instance.getCount();
		return Response.ok(String.valueOf(count)).build();
	}

	/**
	 * Adds a new play to the storage
	 * 
	 * @param gameId
	 *            id of the game REQUIRED
	 * @param date
	 *            date of the play REQUIRED
	 * @param timeToComplete
	 *            time needed to complete the play
	 * @param numPlayers
	 *            players who partecipated
	 * @param winnerId
	 *            id of the player who won the game
	 * @param servletResponse
	 * @param servletRequest
	 * @return	BAD_REQUEST: display error messages
	 * 			CREATED: the created resource
	 * @throws IOException
	 */
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newPlay(@FormParam("gameId") String gameId, @FormParam("date") String date,
			@FormParam("timeToComplete") String timeToComplete, @FormParam("numPlayers") String numPlayers,
			@FormParam("winnerId") String winnerId, @Context HttpServletResponse servletResponse,
			@Context HttpServletRequest servletRequest) throws IOException {
		String authToken = servletRequest.getHeader(AUTH_TOKEN);
		if (authToken == null) {
			javax.servlet.http.Cookie[] cookies = servletRequest.getCookies();
			for (javax.servlet.http.Cookie c : cookies) {
				if (c.getName().equals(AUTH_TOKEN)) {
					authToken = c.getValue();
				}
			}
		}

		String userId = Authenticator.getInstance().getUserFromToken(authToken);

		Message errorMessages = checkErrorsNewPlay(gameId, date, winnerId, userId);

		if (errorMessages.getErrors().size() > 0) {
			log.info("errors:" + errorMessages.getErrors().size());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("errors", errorMessages.getErrors());
			return Response.status(Status.BAD_REQUEST).entity(new Viewable("/create_play", map)).build();
		}

		log.info("POST play: userId: " + userId + " gameId: " + gameId);

		Play play = storePlay(gameId, date, timeToComplete, numPlayers, winnerId, userId);

		servletResponse.sendRedirect(play.getUri());
		return Response.created(URI.create(play.getUri())).build();

	}

	/**
	 * Adds a new play to the storage
	 * 
	 * @param gameId
	 *            id of the game REQUIRED
	 * @param date
	 *            date of the play REQUIRED
	 * @param timeToComplete
	 *            time needed to complete the play
	 * @param numPlayers
	 *            players who partecipated
	 * @param winnerId
	 *            id of the player who won the game
	 * @param servletResponse
	 * @param servletRequest
	 * @return	BAD_REQUEST: error messages {@link Message}
	 * 			CREATED: the created resource {@link Play}
	 * @return
	 */
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newPlayApp(@FormParam("gameId") String gameId, @FormParam("date") String date,
			@FormParam("timeToComplete") String timeToComplete, @FormParam("numPlayers") String numPlayers,
			@FormParam("winnerId") String winnerId, @Context HttpServletResponse servletResponse,
			@Context HttpServletRequest servletRequest) {

		String authToken = servletRequest.getHeader(AUTH_TOKEN);
		if (authToken == null) {
			javax.servlet.http.Cookie[] cookies = servletRequest.getCookies();
			for (javax.servlet.http.Cookie c : cookies) {
				if (c.getName().equals(AUTH_TOKEN)) {
					authToken = c.getValue();
				}
			}
		}

		String userId = Authenticator.getInstance().getUserFromToken(authToken);

		Message errorMessages = checkErrorsNewPlay(gameId, date, winnerId, userId);

		if (errorMessages.getErrors().size() > 0) {
			return Response.status(Status.BAD_REQUEST).entity(errorMessages).build();
		}

		log.info("POST play: userId: " + userId + " gameId: " + gameId);

		Play play = storePlay(gameId, date, timeToComplete, numPlayers, winnerId, userId);

		return Response.created(URI.create(play.getUri())).entity(play).build();

	}

	/**
	 * Checks if the parameters allow to create a valid play
	 * @param gameId
	 * @param date
	 * @param winnerId
	 * @param userId
	 * @return message possibly containing errors
	 */
	private Message checkErrorsNewPlay(String gameId, String date, String winnerId, String userId) {
		Message errorMessages = new Message();

		if (UserStorage.instance.existsId(userId)) {
			if (date == null) {
				errorMessages.getErrors().add("Please insert date");
			} else if (date.length() == 0) {
				errorMessages.getErrors().add("Date field cannot be empty!");
			} else {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try{
					df.parse(date);
				}
				catch(ParseException e){
					errorMessages.getErrors().add("Date field should be in format yyyy-MM-dd");
				}
			}

			if (gameId == null) {
				errorMessages.getErrors().add("Please insert gameId");
			} else if (gameId.length() == 0){
				errorMessages.getErrors().add("GameId field cannot be empty!");
			} else if (!GameStorage.instance.existsId(gameId)) {
				errorMessages.getErrors().add("GameId doesn't exists");
			}
			
			if (winnerId != null && !UserStorage.instance.existsId(winnerId)) {
				errorMessages.getErrors().add("WinnerId doesn't exists");
			}
			
		} else {
			errorMessages.getErrors().add("Your user id doesn't exists!");
		}
		return errorMessages;
	}

	/**
	 * Creates and stores the play
	 * @param gameId
	 * @param date
	 * @param timeToComplete
	 * @param numPlayers
	 * @param winnerId
	 * @param userId
	 * @return
	 */
	private Play storePlay(String gameId, String date, String timeToComplete, String numPlayers, String winnerId,
			String userId) {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d;
		try {
			d = df.parse(date);
		} catch (ParseException e) {
			d = new Date();
		}
		
		Play play = new Play(userId, gameId, d);
		
		/*
		 * Complete optional fields
		 */
		if (timeToComplete != null && timeToComplete.length()>0) {
			try{
				play.setTimeToComplete(Integer.valueOf(timeToComplete));
			} catch(NumberFormatException e){
				// ignore field, since it's optional
			}
		}
		
		if (numPlayers != null) {
			try{
			play.setNumPlayers(Integer.valueOf(numPlayers));
			} catch(NumberFormatException e){
				// ignore field, since it's optional
			}
		}
		
		if (UserStorage.instance.existsId(winnerId)) {
			play.setWinnerId(winnerId);
		}

		PlayStorage.instance.storePlay(play);
		return play;
	}

	/**
	 * Retrieves a single play given the id
	 * 
	 * @param id
	 * @return
	 */
	@Path("{play}")
	public PlayResource getPlay(@PathParam("play") String id) {
		return new PlayResource(uriInfo, request, id);
	}
}
