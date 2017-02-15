package it.polimi.moscowmule.boardgamemanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/games")
public class GamesStorage {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// application
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Game> getGames() {
		List<Game> games = new ArrayList<Game>();
		games.addAll(GameStorage.instance.getModel().values());
		return games;
	}

	// browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Game> getGamesBrowser() {
		List<Game> games = new ArrayList<Game>();
		games.addAll(GameStorage.instance.getModel().values());
		return games;
	}

	// count
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = GameStorage.instance.getModel().size();
		return String.valueOf(count);
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newGame(@FormParam("id") String id, @FormParam("name") String name,
			@FormParam("minPlayers") String minPlayers, @FormParam("maxPlayers") String maxPlayers,
			@FormParam("playTime") String playTime, @FormParam("minAge") String minAge,
			@FormParam("difficulty") String difficulty, @FormParam("designer") String designer,
			@FormParam("artist") String artist, @FormParam("publisher") String publisher,
			@Context HttpServletResponse servletResponse) throws IOException {
		Game game = new Game(id, name);
		GameStorage.instance.getModel().put(id, game);
		if (minPlayers != null) {
			game.setMinPlayers(Integer.valueOf(minPlayers));
		}
		if (maxPlayers != null) {
			game.setMaxPlayers(Integer.valueOf(maxPlayers));
		}
		if (playTime != null) {
			game.setPlayTime(Integer.valueOf(playTime));
		}
		if (minAge != null) {
			game.setMinAge(Integer.valueOf(minAge));
		}
		if (difficulty != null) {
			game.setDifficulty(Float.valueOf(difficulty));
		}
		if (designer != null) {
			game.setDesigner(designer);
		}
		if (artist != null) {
			game.setArtist(artist);
		}
		if (publisher != null) {
			game.setPublisher(publisher);
		}
	}

	@Path("{game}")
	public GameResource getGame(@PathParam("game") String id) {
		return new GameResource(uriInfo, request, id);
	}

}
