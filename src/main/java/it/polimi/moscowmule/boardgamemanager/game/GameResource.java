package it.polimi.moscowmule.boardgamemanager.game;

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
 * Resource representing a single game
 *
 * @author Simone Ripamonti
 * @version 1
 */
public class GameResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	/**
	 * Of the game to return
	 */
	String id;
	
	public GameResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

	/**
	 * Retrieves a game
	 * @return 	OK: XML or JSON representation of the game {@link Game}
	 * 			NOT_FOUND: error message {@link Message}
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getGameApp() {
		Game game = GameStorage.instance.getGame(id);
		if (game == null)
			return Response.status(Status.NOT_FOUND).entity(new Message("GameId not found")).build();
		return Response.ok(game).build();
	}

	/**
	 * Retrieves a game
	 * @return 	OK: HTML representation of the game
	 * 			NOT_FOUND: 404 page
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getGameBrowser() {
		Game game = GameStorage.instance.getGame(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("game", game);
		if (game == null){
			map.put("not_found", true);
			return Response.status(Status.NOT_FOUND).entity(new Viewable("/game_detail", map)).build();
		} else {
			return Response.ok(new Viewable("/game_detail", map)).build();
		}
		
	}

}
