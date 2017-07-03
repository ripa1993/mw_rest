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
import java.util.logging.Logger;

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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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

	private final static Logger log = Logger.getLogger(GamesResource.class.getName());

	// application
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getGames(@DefaultValue("") @QueryParam("name") String name,
			@DefaultValue("") @QueryParam("players") String players, @DefaultValue("") @QueryParam("time") String time,
			@DefaultValue("") @QueryParam("age") String age,
			@DefaultValue("") @QueryParam("difficulty") String difficulty,
			@DefaultValue("") @QueryParam("designer") String designer,
			@DefaultValue("") @QueryParam("artist") String artist,
			@DefaultValue("") @QueryParam("publisher") String publisher,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		// add all games
		List<Game> games = new ArrayList<Game>();
		games.addAll(GameStorage.instance.getModel().values());

		if (!name.equals("")) {
			log.info("Filtering name");
			games.removeIf(g -> !g.getName().contains(name));
		}
		if (!players.equals("")) {
			log.info("Filtering players");
			try {
				int p = Integer.valueOf(players);

				games.removeIf(g -> p > g.getMaxPlayers());

				games.removeIf(g -> p < g.getMinPlayers());

			} catch (NumberFormatException e) {
				// do nothing :)
			}
		}

		if (!time.equals("")) {
			log.info("Filtering time");
			try {
				int t = Integer.valueOf(time);
				games.removeIf(g -> g.getPlayTime() > t);

			} catch (NumberFormatException e) {
				//
			}
		}

		if (!age.equals("")) {
			log.info("Filtering age");
			try {
				int a = Integer.valueOf(age);
				games.removeIf(g -> g.getMinAge() < a);
			} catch (NumberFormatException e) {
				// donothing
			}
		}
		if (!difficulty.equals("")) {
			log.info("Filtering difficulty");
			try {
				float d = Float.valueOf(difficulty);
				games.removeIf(g -> g.getDifficulty() > d);
			} catch (NumberFormatException e) {
				// nothing
			}
		}

		if (!designer.equals("")) {
			games.removeIf(g -> !g.getDesigner().contains(designer));
		}

		if (!artist.equals("")) {
			games.removeIf(g -> !g.getArtist().contains(artist));

		}
		if (!publisher.equals("")) {
			games.removeIf(g -> !g.getPublisher().contains(publisher));
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

		GenericEntity<List<Game>> gameGeneric = new GenericEntity<List<Game>>(games){};
		
		return Response.ok(gameGeneric).build();
	}

	// browser
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getGamesBrowser(@DefaultValue("") @QueryParam("name") String name,
			@DefaultValue("") @QueryParam("players") String players, @DefaultValue("") @QueryParam("time") String time,
			@DefaultValue("") @QueryParam("age") String age,
			@DefaultValue("") @QueryParam("difficulty") String difficulty,
			@DefaultValue("") @QueryParam("designer") String designer,
			@DefaultValue("") @QueryParam("artist") String artist,
			@DefaultValue("") @QueryParam("publisher") String publisher,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		// add all games
		List<Game> games = new ArrayList<Game>();
		games.addAll(GameStorage.instance.getModel().values());

		if (!name.equals("")) {
			log.info("Filtering name");
			games.removeIf(g -> !g.getName().contains(name));
		}
		if (!players.equals("")) {
			log.info("Filtering players");
			try {
				int p = Integer.valueOf(players);

				games.removeIf(g -> p > g.getMaxPlayers());

				games.removeIf(g -> p < g.getMinPlayers());

			} catch (NumberFormatException e) {
				// do nothing :)
			}
		}

		if (!time.equals("")) {
			log.info("Filtering time");
			try {
				int t = Integer.valueOf(time);
				games.removeIf(g -> g.getPlayTime() > t);

			} catch (NumberFormatException e) {
				//
			}
		}

		if (!age.equals("")) {
			log.info("Filtering age");
			try {
				int a = Integer.valueOf(age);
				games.removeIf(g -> g.getMinAge() < a);
			} catch (NumberFormatException e) {
				// donothing
			}
		}
		if (!difficulty.equals("")) {
			log.info("Filtering difficulty");
			try {
				float d = Float.valueOf(difficulty);
				games.removeIf(g -> g.getDifficulty() > d);
			} catch (NumberFormatException e) {
				// nothing
			}
		}

		if (!designer.equals("")) {
			games.removeIf(g -> !g.getDesigner().contains(designer));
		}

		if (!artist.equals("")) {
			games.removeIf(g -> !g.getArtist().contains(artist));

		}
		if (!publisher.equals("")) {
			games.removeIf(g -> !g.getPublisher().contains(publisher));
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

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response newGame(@FormDataParam("name") String name, @FormDataParam("minPlayers") String minPlayers,
			@FormDataParam("maxPlayers") String maxPlayers, @FormDataParam("playTime") String playTime,
			@FormDataParam("minAge") String minAge, @FormDataParam("difficulty") String difficulty,
			@FormDataParam("designer") String designer, @FormDataParam("artist") String artist,
			@FormDataParam("publisher") String publisher, @FormDataParam("file") InputStream file,
			@FormDataParam("file") FormDataContentDisposition header, @Context HttpServletResponse servletResponse)
			throws IOException {

		/*
		 * Create new game
		 */
		Game game = new Game(name);

		// obtain id of new game
		String id = game.getId();

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

		/*
		 * If all checks are successful add the game to the storage
		 */
		GameStorage.instance.getModel().put(id, game);

		/*
		 * Handle the image
		 */

		FileOutputStream os = FileUtils.openOutputStream(new File("C://boardgamemanager//img//" + id + ".jpg"));

		byte[] buf = new byte[1024];
		int len;

		while ((len = file.read(buf)) > 0) {
			os.write(buf, 0, len);
		}
		os.flush();
		os.close();
		file.close();

		// game.setCoverArt("http://localhost:8080/boardgameamanger/rest/img/" +
		// id);

		servletResponse.sendRedirect(game.getUri());
		return Response.created(URI.create("http://localhost:8080/boardgameamanger/rest/games/" + id)).build();
	}

	@Path("{game}")
	public GameResource getGame(@PathParam("game") String id) {
		return new GameResource(uriInfo, request, id);
	}

}
