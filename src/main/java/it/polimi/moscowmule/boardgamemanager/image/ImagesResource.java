package it.polimi.moscowmule.boardgamemanager.image;

import java.io.File;
import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/img")
public class ImagesResource {
	private static final String FOLDER = "C://boardgamemanager//img//";
	private static final String MISSING = FOLDER+"missing.jpg";
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getContent(){
		File f = new File(FOLDER);
		return Arrays.toString(f.list());
	}
	
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
