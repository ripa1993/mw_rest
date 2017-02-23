package it.polimi.moscowmule.boardgamemanager.authentication;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Local
@Path("/")
public interface BusinessRESTResourceProxy extends Serializable {
	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("username") String username,
			@FormParam("password") String password);

	@POST
	@Path("logout")
	public Response logout(@Context HttpHeaders httpHeaders);

}
