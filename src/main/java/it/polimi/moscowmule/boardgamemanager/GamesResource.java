package it.polimi.moscowmule.boardgamemanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/games")
public class GamesResource {
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

	@GET
	@Path("location")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLocation(){
		java.nio.file.Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		return s;
	}
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void newGame(@FormDataParam("id") String id, @FormDataParam("name") String name,
			@FormDataParam("minPlayers") String minPlayers, @FormDataParam("maxPlayers") String maxPlayers,
			@FormDataParam("playTime") String playTime, @FormDataParam("minAge") String minAge, 
			@FormDataParam("difficulty") String difficulty, @FormDataParam("designer") String designer,
			@FormDataParam("artist") String artist, @FormDataParam("publisher") String publisher,
			@FormDataParam("file") InputStream file, @FormDataParam("file") FormDataContentDisposition header,
			@Context HttpServletResponse servletResponse)
			throws IOException {
				
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
	}

	@Path("{game}")
	public GameResource getGame(@PathParam("game") String id) {
		return new GameResource(uriInfo, request, id);
	}

}
