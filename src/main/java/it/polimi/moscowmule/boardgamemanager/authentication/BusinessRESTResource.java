package it.polimi.moscowmule.boardgamemanager.authentication;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/")
public class BusinessRESTResource {

	private final static Logger log = Logger.getLogger(BusinessRESTResource.class.getName());

	/*
	 * Receives username and password Returns an authorization token in JSON
	 * format
	 */
	@POST
	@Path("login")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {

		Authenticator authenticator = Authenticator.getInstance();

		log.info("Trying to login " + username + ":" + password);

		try {
			String authToken = authenticator.login(username, password);
			log.info("Login successful");
			AuthToken at = new AuthToken(authToken);
			return Response.ok(at).build();
		} catch (LoginException e) {
			log.info("Login failed");
			return Response.status(Status.UNAUTHORIZED).entity("Login is incorrect").build();
		}

	}

	@POST
	@Path("login")
	@Produces(MediaType.TEXT_HTML)
	public Response loginWeb(@FormParam("username") String username, @FormParam("password") String password,
			@Context HttpServletResponse response) throws IOException {

		Authenticator authenticator = Authenticator.getInstance();

		log.info("Trying to login " + username + ":" + password);

		try {
			String authToken = authenticator.login(username, password);
			log.info("Login successful");
			Cookie cookie = new Cookie("auth_token", authToken);
			cookie.setDomain("localhost");
			cookie.setPath("/boardgamemanager");

			response.addCookie(cookie);
			response.sendRedirect("http://localhost:8080/boardgamemanager/");
			return Response.ok().build();
		} catch (LoginException e) {
			log.info("Login failed");
			response.sendRedirect("../login.jsp");
			return Response.status(Status.UNAUTHORIZED).build();
		}

	}

	@POST
	@Path("logout")
	@Produces(MediaType.TEXT_HTML)
	public Response logoutBrowser(@Context HttpServletResponse response, @CookieParam("auth_token") String authToken)
			throws IOException {
		Cookie cookie = new Cookie("auth_token", authToken);
		cookie.setDomain("localhost");
		cookie.setPath("/boardgamemanager");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		response.sendRedirect("http://localhost:8080/boardgamemanager/");
		return Response.ok().build();
	}

	/*
	 * Logs out, no content is returned INTERNAL_SERVER_ERROR if an incorrect
	 * auth_token is passed in the header
	 */
	@POST
	@Path("logout")
	public Response logout(@Context HttpHeaders httpHeaders) {
		try {
			Authenticator authenticator = Authenticator.getInstance();
			String authToken = httpHeaders.getHeaderString(HTTPHeaderNames.AUTH_TOKEN);
			log.info("Trying to logout auth_token=" + authToken);
			authenticator.logout(authToken);
			log.info("Logout successful");
			return Response.noContent().build();
		} catch (GeneralSecurityException e) {
			log.info("Logout failed");
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

}
