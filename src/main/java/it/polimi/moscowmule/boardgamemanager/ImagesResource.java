package it.polimi.moscowmule.boardgamemanager;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/img")
public class ImagesResource {
	private static final String FOLDER = "C://boardgamemanager//img//";
	
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
			response = Response.status(404).entity("FILE NOT FOUND: "+fileLocation).build();
		}
		
		return response;
	}
}
