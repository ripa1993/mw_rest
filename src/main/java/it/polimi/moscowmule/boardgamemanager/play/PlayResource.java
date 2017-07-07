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

import org.glassfish.jersey.server.mvc.Viewable;

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
	 * @return XML or JSON representation of the play
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getPlay() {
		Play play = PlayStorage.instance.getPlay(id);
		if (play == null)
			throw new RuntimeException("Get: Play with " + id + " not found");
		return Response.ok(play).build();
	}

	/***
	 * Retrieves a play
	 * @return HTML representation of the play
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getPlayBrowser() {
		Play play = PlayStorage.instance.getPlay(id);
		if (play == null)
			throw new RuntimeException("Get: Play with " + id + " not found");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("play", play);
		return Response.ok(new Viewable("/play_detail", map)).build();
	}
	
	
}
