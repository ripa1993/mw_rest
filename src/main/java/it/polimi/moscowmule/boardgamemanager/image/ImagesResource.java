package it.polimi.moscowmule.boardgamemanager.image;

import java.io.File;
import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Resource representing an image file
 */
@Path("/img")
public class ImagesResource {
	/**
	 * Folder where images are stored
	 */
	private static final String FOLDER = "C://boardgamemanager//img//";
	/**
	 * Path to courtesy image when no cover art is available
	 */
	private static final String MISSING = FOLDER+"missing.jpg";

	/**
	 * Retrieves the list of images available in the folder
	 * @return string containing filenames
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getContent(){
		File f = new File(FOLDER);
		return Arrays.toString(f.list());
	}

	/**
	 * Retrieves an image file
	 * @param filename name of the image
	 * @return the image
	 */
	@GET
	@Path("{filename}")
	@Produces("image/jpg")
	public Response downloadFile(@PathParam("filename") String filename){
		String fileLocation = FOLDER + filename;
		Response response = null;
		
		File file = new File(fileLocation);
		if(file.exists()){
			javax.ws.rs.core.Response.ResponseBuilder builder = Response.ok(file);
			builder.header("Content-Disposition", "attachment; filename=" + file.getName());
			response = builder.build();
		} else {
			file = new File(MISSING);
			javax.ws.rs.core.Response.ResponseBuilder builder = Response.status(404).entity(file);
			builder.header("Content-Disposition", "attachment; filename=" + file.getName());
			response = builder.build();
		}
		
		return response;
	}
}
