package it.polimi.moscowmule.boardgamemanager.game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
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
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.mvc.Viewable;

@Path("/games")
public class GamesResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// application
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getGames(@DefaultValue("") @QueryParam("filter") String filter,
			@DefaultValue("") @QueryParam("value") String value,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		// add all games
		List<Game> games = new ArrayList<Game>();
		games.addAll(GameStorage.instance.getModel().values());

		if (!value.equals("")) {
			switch (filter) {
			case "name":
				games.removeIf(g -> !g.getName().contains(value));
				break;
			case "players":
				games.removeIf(g -> !(g.getMaxPlayers() > Integer.valueOf(value))
						|| !(g.getMinPlayers() < Integer.valueOf(value)));
				break;
			case "playTime":
				games.removeIf(g -> g.getPlayTime() > Integer.valueOf(value));
				break;
			case "age":
				games.removeIf(g -> g.getMinAge() < Integer.valueOf(value));
				break;
			case "difficulty":
				games.removeIf(g -> g.getDifficulty() > Integer.valueOf(value));
				break;
			case "designer":
				games.removeIf(g -> !g.getDesigner().contains(value));
				break;
			case "artist":
				games.removeIf(g -> !g.getArtist().contains(value));
				break;
			case "publisher":
				games.removeIf(g -> !g.getPublisher().contains(value));
				break;
			}
		}

		// order
		if (!orderby.equals("") || !orderby.equals("id")) {
			Comparator<Game> comp = (Game a, Game b) -> a.getId().compareTo(b.getId());
			switch (orderby) {
			case "name":
				comp = (Game a, Game b) -> a.getName().compareTo(b.getName());
				break;
			case "minPlayers":
				comp = (Game a, Game b) -> Integer.valueOf(a.getMinPlayers()).compareTo(b.getMinPlayers());
				break;
			case "maxPlayers":
				comp = (Game a, Game b) -> Integer.valueOf(a.getMaxPlayers()).compareTo(b.getMaxPlayers());
				break;
			case "playTime":
				comp = (Game a, Game b) -> Integer.valueOf(a.getPlayTime()).compareTo(b.getPlayTime());
				break;
			case "minAge":
				comp = (Game a, Game b) -> Integer.valueOf(a.getMinAge()).compareTo(b.getMinAge());
				break;
			case "difficulty":
				comp = (Game a, Game b) -> Float.valueOf(a.getDifficulty()).compareTo(b.getDifficulty());
				break;
			case "designer":
				comp = (Game a, Game b) -> a.getDesigner().compareTo(b.getDesigner());
				break;
			case "artist":
				comp = (Game a, Game b) -> a.getArtist().compareTo(b.getArtist());
				break;
			case "publisher":
				comp = (Game a, Game b) -> a.getPublisher().compareTo(b.getPublisher());
				break;
			}
			Collections.sort(games, comp);
		}

		// if descending
		if (order.equals("desc")) {
			Collections.reverse(games);
		}

		return Response.ok(games).build();
	}

	// browser
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getGamesBrowser(@DefaultValue("") @QueryParam("filter") String filter,
			@DefaultValue("") @QueryParam("value") String value,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		// add all games
		List<Game> games = new ArrayList<Game>();
		games.addAll(GameStorage.instance.getModel().values());

		if (!value.equals("")) {
			switch (filter) {
			case "name":
				games.removeIf(g -> !g.getName().contains(value));
				break;
			case "players":
				games.removeIf(g -> !(g.getMaxPlayers() > Integer.valueOf(value))
						|| !(g.getMinPlayers() < Integer.valueOf(value)));
				break;
			case "playTime":
				games.removeIf(g -> g.getPlayTime() > Integer.valueOf(value));
				break;
			case "age":
				games.removeIf(g -> g.getMinAge() < Integer.valueOf(value));
				break;
			case "difficulty":
				games.removeIf(g -> g.getDifficulty() > Integer.valueOf(value));
				break;
			case "designer":
				games.removeIf(g -> !g.getDesigner().contains(value));
				break;
			case "artist":
				games.removeIf(g -> !g.getArtist().contains(value));
				break;
			case "publisher":
				games.removeIf(g -> !g.getPublisher().contains(value));
				break;
			}
		}

		// order
		if (!orderby.equals("") || !orderby.equals("id")) {
			Comparator<Game> comp = (Game a, Game b) -> a.getId().compareTo(b.getId());
			switch (orderby) {
			case "name":
				comp = (Game a, Game b) -> a.getName().compareTo(b.getName());
				break;
			case "minPlayers":
				comp = (Game a, Game b) -> Integer.valueOf(a.getMinPlayers()).compareTo(b.getMinPlayers());
				break;
			case "maxPlayers":
				comp = (Game a, Game b) -> Integer.valueOf(a.getMaxPlayers()).compareTo(b.getMaxPlayers());
				break;
			case "playTime":
				comp = (Game a, Game b) -> Integer.valueOf(a.getPlayTime()).compareTo(b.getPlayTime());
				break;
			case "minAge":
				comp = (Game a, Game b) -> Integer.valueOf(a.getMinAge()).compareTo(b.getMinAge());
				break;
			case "difficulty":
				comp = (Game a, Game b) -> Float.valueOf(a.getDifficulty()).compareTo(b.getDifficulty());
				break;
			case "designer":
				comp = (Game a, Game b) -> a.getDesigner().compareTo(b.getDesigner());
				break;
			case "artist":
				comp = (Game a, Game b) -> a.getArtist().compareTo(b.getArtist());
				break;
			case "publisher":
				comp = (Game a, Game b) -> a.getPublisher().compareTo(b.getPublisher());
				break;
			}
			Collections.sort(games, comp);
		}

		// if descending
		if (order.equals("desc")) {
			Collections.reverse(games);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("games", games);
		return Response.ok(new Viewable("/game_list", map)).build();
	}

	// count
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getCount() {
		int count = GameStorage.instance.getModel().size();
		return Response.ok(String.valueOf(count)).build();
	}

	// TODO: game id should be generated
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response newGame(@FormDataParam("id") String id, @FormDataParam("name") String name,
			@FormDataParam("minPlayers") String minPlayers, @FormDataParam("maxPlayers") String maxPlayers,
			@FormDataParam("playTime") String playTime, @FormDataParam("minAge") String minAge,
			@FormDataParam("difficulty") String difficulty, @FormDataParam("designer") String designer,
			@FormDataParam("artist") String artist, @FormDataParam("publisher") String publisher,
			@FormDataParam("file") InputStream file, @FormDataParam("file") FormDataContentDisposition header,
			@Context HttpServletResponse servletResponse) throws IOException {
		/*
		 * Check if id is already used
		 */
		if (GameStorage.instance.getModel().containsKey(id)){
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		/*
		 * Create new game
		 */
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

		// handle the image

		FileOutputStream os = FileUtils.openOutputStream(new File("C://boardgamemanager//img//" + id + ".jpg"));

		byte[] buf = new byte[1024];
		int len;

		while ((len = file.read(buf)) > 0) {
			os.write(buf, 0, len);
		}
		os.flush();
		os.close();
		file.close();
		game.setCoverArt("http://localhost:8080/boardgameamanger/rest/img/" + id);

		servletResponse.sendRedirect("../create_game.html");
		return Response.created(URI.create("http://localhost:8080/boardgameamanger/rest/games/"+id)).build();
	}

	@Path("{game}")
	public GameResource getGame(@PathParam("game") String id) {
		return new GameResource(uriInfo, request, id);
	}

}
