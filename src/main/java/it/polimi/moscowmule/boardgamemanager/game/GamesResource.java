package it.polimi.moscowmule.boardgamemanager.game;

import static it.polimi.moscowmule.boardgamemanager.utils.Constants.IMG_STORAGE;

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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.mvc.Viewable;

import it.polimi.moscowmule.boardgamemanager.utils.Message;

/**
 * Resource representing the collection of games
 *
 * @author Simone Ripamonti
 * @version 1
 */
@Path("/games")
public class GamesResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	private final static Logger log = Logger.getLogger(GamesResource.class.getName());

	/**
	 * Retrieves a list of games matching the criterias
	 * 
	 * @param name
	 *            of the game (optional)
	 * @param players
	 *            number of players that want to play (optional)
	 * @param time
	 *            maximum play time (optional)
	 * @param age
	 *            minimum age of the players (optional)
	 * @param difficulty
	 *            maximum difficulty of the game (optional)
	 * @param designer
	 *            name of the deisgner who partecipated in game creation
	 *            (optional)
	 * @param artist
	 *            name of the artist who partecipated in game creation
	 *            (optional)
	 * @param publisher
	 *            name of the publisher who published the game (optional)
	 * @param orderby
	 *            value in [name, minPlayers, maxPlayers, playTime, minAge,
	 *            difficulty, designer, artist, publisher]
	 * @param order
	 *            value in [asc, desc]
	 * @return 	OK: XML or JSON list of games matching the criteria {@link Game}
	 */
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getGamesApp(@DefaultValue("") @QueryParam("name") String name,
			@DefaultValue("") @QueryParam("players") String players, @DefaultValue("") @QueryParam("time") String time,
			@DefaultValue("") @QueryParam("age") String age,
			@DefaultValue("") @QueryParam("difficulty") String difficulty,
			@DefaultValue("") @QueryParam("designer") String designer,
			@DefaultValue("") @QueryParam("artist") String artist,
			@DefaultValue("") @QueryParam("publisher") String publisher,
			@DefaultValue("id") @QueryParam("orderby") String orderby,
			@DefaultValue("asc") @QueryParam("order") String order) {
		List<Game> games = getGamesCommon(name, players, time, age, difficulty, designer, artist, publisher, orderby,
				order);
		GenericEntity<List<Game>> gameGeneric = new GenericEntity<List<Game>>(games) {
		};
		return Response.ok(gameGeneric).build();
	}

	/**
	 * Retrieves a list of games matching the criterias
	 * 
	 * @param name
	 *            of the game (optional)
	 * @param players
	 *            number of players that want to play (optional)
	 * @param time
	 *            maximum play time (optional)
	 * @param age
	 *            minimum age of the players (optional)
	 * @param difficulty
	 *            maximum difficulty of the game (optional)
	 * @param designer
	 *            name of the deisgner who partecipated in game creation
	 *            (optional)
	 * @param artist
	 *            name of the artist who partecipated in game creation
	 *            (optional)
	 * @param publisher
	 *            name of the publisher who published the game (optional)
	 * @param orderby
	 *            value in [name, minPlayers, maxPlayers, playTime, minAge,
	 *            difficulty, designer, artist, publisher]
	 * @param order
	 *            value in [asc, desc]
	 * @return 	OK: HTML representation of the list of games matching the criteria
	 */
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
		List<Game> games = getGamesCommon(name, players, time, age, difficulty, designer, artist, publisher, orderby,
				order);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("games", games);
		return Response.ok(new Viewable("/game_list", map)).build();
	}

	/**
	 * Retrieves a list of games matching the criterias
	 * 
	 * @param name
	 *            of the game (optional)
	 * @param players
	 *            number of players that want to play (optional)
	 * @param time
	 *            maximum play time (optional)
	 * @param age
	 *            minimum age of the players (optional)
	 * @param difficulty
	 *            maximum difficulty of the game (optional)
	 * @param designer
	 *            name of the deisgner who partecipated in game creation
	 *            (optional)
	 * @param artist
	 *            name of the artist who partecipated in game creation
	 *            (optional)
	 * @param publisher
	 *            name of the publisher who published the game (optional)
	 * @param orderby
	 *            value in [name, minPlayers, maxPlayers, playTime, minAge,
	 *            difficulty, designer, artist, publisher]
	 * @param order
	 *            value in [asc, desc]
	 * @return list of {@link Game} matching the criterias
	 */
	private List<Game> getGamesCommon(String name, String players, String time, String age, String difficulty,
			String designer, String artist, String publisher, String orderby, String order) {
		// add all games
		List<Game> games = new ArrayList<Game>();
		games.addAll(GameStorage.instance.getAllGames());

		if (!name.equals("")) {
			log.info("Filtering name");
			games.removeIf(g -> !g.getName().toLowerCase().contains(name.toLowerCase()));
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
			games.removeIf(g -> !g.getDesigner().toLowerCase().contains(designer.toLowerCase()));
		}

		if (!artist.equals("")) {
			games.removeIf(g -> !g.getArtist().toLowerCase().contains(artist.toLowerCase()));

		}
		if (!publisher.equals("")) {
			games.removeIf(g -> !g.getPublisher().toLowerCase().contains(publisher.toLowerCase()));
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
		return games;
	}

	/**
	 * Gets the count of games available
	 * 
	 * @return	OK: the count
	 */
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getCount() {
		int count = GameStorage.instance.getCount();
		return Response.ok(String.valueOf(count)).build();
	}

	/**
	 * Creates a new game and adds it to the storage
	 * 
	 * @param name
	 * @param minPlayers
	 * @param maxPlayers
	 * @param playTime
	 * @param minAge
	 * @param difficulty
	 * @param designer
	 * @param artist
	 * @param publisher
	 * @param file
	 *            the image representing the cover art of the game
	 * @param header
	 * @param servletResponse
	 * @return	BAD_REQUEST: if something went wrong
	 * 			CREATED: if ok
	 * @throws IOException
	 */
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response newGameBrowser(@FormDataParam("name") String name, @FormDataParam("minPlayers") String minPlayers,
			@FormDataParam("maxPlayers") String maxPlayers, @FormDataParam("playTime") String playTime,
			@FormDataParam("minAge") String minAge, @FormDataParam("difficulty") String difficulty,
			@FormDataParam("designer") String designer, @FormDataParam("artist") String artist,
			@FormDataParam("publisher") String publisher, @FormDataParam("file") InputStream file,
			@FormDataParam("file") FormDataContentDisposition header, @Context HttpServletResponse servletResponse)
			throws IOException {

		Message errorMessages = checkErrorsNewGame(name, designer, file, header);

		if (errorMessages.getErrors().size() > 0) {
			log.info("errors:" + errorMessages.getErrors().size());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("errors", errorMessages.getErrors());
			return Response.status(Status.BAD_REQUEST).entity(new Viewable("/create_game", map)).build();
		}

		Game game = storeGame(name, minPlayers, maxPlayers, playTime, minAge, difficulty, designer, artist, publisher,
				file);

		servletResponse.sendRedirect(game.getUri());
		return Response.created(URI.create(game.getUri())).build();
	}

	/**
	 * 
	 * @param name
	 * @param minPlayers
	 * @param maxPlayers
	 * @param playTime
	 * @param minAge
	 * @param difficulty
	 * @param designer
	 * @param artist
	 * @param publisher
	 * @param file the image representing the cover art of the game
	 * @param header
	 * @param servletResponse
	 * @return	BAD_REQUEST: if something went wrong {@link Message}
	 * 			CREATED: if ok {@link Message}
	 * @throws IOException
	 */
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response newGameApp(@FormDataParam("name") String name, @FormDataParam("minPlayers") String minPlayers,
			@FormDataParam("maxPlayers") String maxPlayers, @FormDataParam("playTime") String playTime,
			@FormDataParam("minAge") String minAge, @FormDataParam("difficulty") String difficulty,
			@FormDataParam("designer") String designer, @FormDataParam("artist") String artist,
			@FormDataParam("publisher") String publisher, @FormDataParam("file") InputStream file,
			@FormDataParam("file") FormDataContentDisposition header, @Context HttpServletResponse servletResponse)
			throws IOException {

		Message errorMessages = checkErrorsNewGame(name, designer, file, header);

		if (errorMessages.getErrors().size() > 0) {
			return Response.status(Status.BAD_REQUEST).entity(errorMessages).build();
		}

		Game game = storeGame(name, minPlayers, maxPlayers, playTime, minAge, difficulty, designer, artist, publisher,
				file);

		return Response.created(URI.create(game.getUri())).entity(game).build();
	}

	/**
	 * Checks if there are errors in creating a game with the given parameters
	 * @param name
	 * @param designer
	 * @param file
	 * @param header
	 * @return {@link Message} containing possible errors
	 */
	private Message checkErrorsNewGame(String name, String designer, InputStream file,
			FormDataContentDisposition header) {
		Message errorMessages = new Message();
		if (name == null) {
			errorMessages.getErrors().add("Field name is required!");
		}
		if (designer == null) {
			errorMessages.getErrors().add("Designer names are required!");
		}
		if (file == null) {
			errorMessages.getErrors().add("Game cover art is required!");
		}
		if (name.length() == 0) {
			errorMessages.getErrors().add("Field name can't be empty!");
		}
		if (designer.length() == 0) {
			errorMessages.getErrors().add("Designer names field can't be empty!");
		}
		if (file != null && (!header.getFileName().toLowerCase().endsWith(".jpg")
				|| !header.getFileName().toLowerCase().endsWith(".jpeg"))) {
			errorMessages.getErrors().add("Game cover art should be in .jpg or .jpeg extension");
		}
		return errorMessages;
	}

	/**
	 * Creates and stores a game with the given characteristcs
	 * @param name
	 * @param minPlayers
	 * @param maxPlayers
	 * @param playTime
	 * @param minAge
	 * @param difficulty
	 * @param designer
	 * @param artist
	 * @param publisher
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private Game storeGame(String name, String minPlayers, String maxPlayers, String playTime, String minAge,
			String difficulty, String designer, String artist, String publisher, InputStream file) throws IOException {
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
		GameStorage.instance.storeGame(game);

		/*
		 * Handle the image
		 */

		FileOutputStream os = FileUtils.openOutputStream(new File(IMG_STORAGE + id + ".jpg"));

		byte[] buf = new byte[1024];
		int len;

		while ((len = file.read(buf)) > 0) {
			os.write(buf, 0, len);
		}
		os.flush();
		os.close();
		file.close();
		return game;
	}

	/**
	 * Retrieves a single game
	 * 
	 * @param id
	 *            of the game
	 * @return
	 */
	@Path("{game}")
	public GameResource getGame(@PathParam("game") String id) {
		return new GameResource(uriInfo, request, id);
	}

}
