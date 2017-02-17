package it.polimi.moscowmule.boardgamemanager.play;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import it.polimi.moscowmule.boardgamemanager.game.GameStorage;
import it.polimi.moscowmule.boardgamemanager.user.UserStorage;

@Path("/plays")
public class PlaysResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// app
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Play> getPlays() {
		List<Play> plays = new ArrayList<Play>();
		plays.addAll(PlayStorage.instance.getModel().values());
		return plays;
	}

	// browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Play> getPlaysBrowser() {
		List<Play> plays = new ArrayList<Play>();
		plays.addAll(PlayStorage.instance.getModel().values());
		return plays;
	}

	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = PlayStorage.instance.getModel().size();
		return String.valueOf(count);
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newPlay(@FormParam("id") String id,
						@FormParam("userId") String userId,
						@FormParam("gameId") String gameId,
						@FormParam("date") String date,
						@FormParam("timeToComplete") String timeToComplete,
						@FormParam("numPlayers") String numPlayers,
						@FormParam("winnerId") String winnerId,
						@Context HttpServletResponse servletResponse) throws IOException{
		if(UserStorage.instance.getModel().containsKey(userId) && GameStorage.instance.getModel().containsKey(gameId)){
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date d;
			try {
				d = df.parse(date);
			} catch (ParseException e) {
				d = new Date();
			}
			Play play = new Play(id, userId, gameId, d);
			if(timeToComplete!=null){
				play.setTimeToComplete(Integer.valueOf(timeToComplete));
			}
			if(numPlayers!=null){
				play.setNumPlayers(Integer.valueOf(numPlayers));
			}
			if(UserStorage.instance.getModel().containsKey(winnerId)){
				play.setWinnerId(winnerId);
			}
			servletResponse.sendRedirect("../create_play.html");
		}
	}
}
