package it.polimi.moscowmule.boardgamemanager.play;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.mvc.Viewable;

import it.polimi.moscowmule.boardgamemanager.utils.Message;

/**
 * Resource representing a play
 *
 * @author Simone Ripamonti
 * @version 1
 */
public class PlayResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;

	public PlayResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

	/**
	 * Retrieves a play
	 * @return 	OK: XML or JSON representation of the play {@link Play}
	 * 			NOT_FOUND: error message {@link Message}
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getPlayApp() {
		Play play = PlayStorage.instance.getPlay(id);
		if (play == null)
			return Response.status(Status.NOT_FOUND).entity(new Message("PlayId not found")).build();
		return Response.ok(play).build();
	}

	/***
	 * Retrieves a play
	 * @return 	OK: HTML representation of the play
	 * 			NOT_FOUND: 404 page
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getPlayBrowser() {
		Play play = PlayStorage.instance.getPlay(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("play", play);
		if (play == null){
			map.put("not_found", true);
			return Response.status(Status.NOT_FOUND).entity(new Viewable("/play_detail", map)).build();
		} else {
			return Response.ok(new Viewable("/play_detail", map)).build();
		}		
	}
	
	
}
