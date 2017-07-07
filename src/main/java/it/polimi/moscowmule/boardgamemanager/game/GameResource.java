package it.polimi.moscowmule.boardgamemanager.game;

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
	 * @return XML or JSON representation of the game
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getGame() {
		Game game = GameStorage.instance.getGame(id);
		if (game == null)
			throw new RuntimeException("Get: Game with " + id + " not found");
		return Response.ok(game).build();
	}

	/**
	 * Retrieves a game
	 * @return HTML representation of the game
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getGameBrowser() {
		Game game = GameStorage.instance.getGame(id);
		if (id == null)
			throw new RuntimeException("Get: Game with " + id + " not found");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("game", game);
		return Response.ok(new Viewable("/game_detail", map)).build();
	}

}
