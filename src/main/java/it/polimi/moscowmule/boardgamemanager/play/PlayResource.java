package it.polimi.moscowmule.boardgamemanager.play;

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

public class PlayResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	
	public PlayResource(UriInfo uriInfo, Request request, String id){
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	// app
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Play getPlay(){
		Play play = PlayStorage.instance.getModel().get(id);
		if(play==null)
			throw new RuntimeException("Get: Play with " + id + " not found");
		return play;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Play getPlayBrowser(){
		Play play = PlayStorage.instance.getModel().get(id);
		if(play==null)
			throw new RuntimeException("Get: Play with " + id + " not found");
		return play;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putPlay(JAXBElement<Play> play){
		Play p = play.getValue();
		Response res;
		if (PlayStorage.instance.getModel().containsKey(p.getId())){
			res = Response.noContent().build();
		} else {
			res=Response.created(uriInfo.getAbsolutePath()).build();
		}
		PlayStorage.instance.getModel().put(p.getId(), p);
		return res;
	}
	
	@DELETE
	public void deletePlay(){
		Play p = PlayStorage.instance.getModel().remove(id);
		if(p == null)
			throw new RuntimeException("Delete: Play with "+id+" not found");
	}
	
}
