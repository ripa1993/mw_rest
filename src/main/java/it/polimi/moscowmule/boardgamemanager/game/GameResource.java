package it.polimi.moscowmule.boardgamemanager.game;

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

public class GameResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	
	public GameResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	// application
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Game getGame() {
		Game game = GameStorage.instance.getModel().get(id);
		if (game == null)
			throw new RuntimeException("Get: Game with " + id + " not found");
		return game;
	}

	// browser
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getGameBrowser() {
		Game game = GameStorage.instance.getModel().get(id);
		if (id == null)
			throw new RuntimeException("Get: Game with " + id + " not found");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("game", game);
		return Response.ok(new Viewable("/game_detail", map)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putGame(JAXBElement<Game> game){
		Game g = game.getValue();
		Response res;
		if(GameStorage.instance.getModel().containsKey(g.getId())){
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		GameStorage.instance.getModel().put(g.getId(), g);
		return res;
	}
	
	@DELETE
	public void deleteGame(){
		Game g = GameStorage.instance.getModel().remove(id);
		if(g==null){
			throw new RuntimeException("Delete: Game with "+id+" not found");
		}
	}
	
}
