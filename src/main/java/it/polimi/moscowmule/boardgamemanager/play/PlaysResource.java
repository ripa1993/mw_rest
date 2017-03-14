package it.polimi.moscowmule.boardgamemanager.play;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Viewable;

import it.polimi.moscowmule.boardgamemanager.authentication.Authenticator;
import it.polimi.moscowmule.boardgamemanager.authentication.HTTPHeaderNames;
import it.polimi.moscowmule.boardgamemanager.game.GameStorage;
import it.polimi.moscowmule.boardgamemanager.user.UserStorage;

@Path("/plays")
public class PlaysResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	private final static Logger log = Logger.getLogger(PlaysResource.class.getName());


	// app
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getPlays(@DefaultValue("") @QueryParam("date") String date,
			@DefaultValue("") @QueryParam("game") String game,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		List<Play> plays = new ArrayList<Play>();
		plays.addAll(PlayStorage.instance.getModel().values());

		// filter
		if (!date.equals("")) {
			log.info("Filtering date");
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
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
		return Response.ok(plays).build();
	}

	// browser
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getPlaysBrowser(@DefaultValue("") @QueryParam("date") String date,
			@DefaultValue("") @QueryParam("game") String game,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		List<Play> plays = new ArrayList<Play>();
		plays.addAll(PlayStorage.instance.getModel().values());

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

	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getCount() {
		int count = PlayStorage.instance.getModel().size();
		return Response.ok(String.valueOf(count)).build();
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newPlay(
			// @FormParam("userId") String userId,
			@FormParam("gameId") String gameId, @FormParam("date") String date,
			@FormParam("timeToComplete") String timeToComplete, @FormParam("numPlayers") String numPlayers,
			@FormParam("winnerId") String winnerId, @Context HttpServletResponse servletResponse,
			@Context HttpServletRequest servletRequest) throws IOException {
		String authToken = servletRequest.getHeader(HTTPHeaderNames.AUTH_TOKEN);
		if (authToken == null) {
			javax.servlet.http.Cookie[] cookies = servletRequest.getCookies();
			for (javax.servlet.http.Cookie c : cookies) {
				if (c.getName().equals(HTTPHeaderNames.AUTH_TOKEN)) {
					authToken = c.getValue();
				}
			}
		}
		String userId = Authenticator.getInstance().getUserFromToken(authToken);

		if (UserStorage.instance.getModel().containsKey(userId)
				&& GameStorage.instance.getModel().containsKey(gameId)) {
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date d;
			try {
				d = df.parse(date);
			} catch (ParseException e) {
				d = new Date();
			}
			Play play = new Play(userId, gameId, d);
			if (timeToComplete != null) {
				play.setTimeToComplete(Integer.valueOf(timeToComplete));
			}
			if (numPlayers != null) {
				play.setNumPlayers(Integer.valueOf(numPlayers));
			}
			if (UserStorage.instance.getModel().containsKey(winnerId)) {
				play.setWinnerId(winnerId);
			}

			PlayStorage.instance.getModel().put(play.getId(), play);

			servletResponse.sendRedirect(play.getUri());
			return Response.created(URI.create(play.getUri())).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@Path("{play}")
	public PlayResource getPlay(@PathParam("play") String id) {
		return new PlayResource(uriInfo, request, id);
	}
}
