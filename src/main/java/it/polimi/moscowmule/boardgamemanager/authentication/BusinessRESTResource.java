package it.polimi.moscowmule.boardgamemanager.authentication;

import static it.polimi.moscowmule.boardgamemanager.utils.Constants.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

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

import org.glassfish.jersey.server.mvc.Viewable;

import it.polimi.moscowmule.boardgamemanager.utils.Message;

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
			return Response.status(Status.UNAUTHORIZED).entity(new Message("Login failed! Check username and password")).build();
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
			Cookie cookie = new Cookie(AUTH_TOKEN, authToken);
			cookie.setDomain(COOKIE_DOMAIN);
			cookie.setPath(COOKIE_PATH);

			response.addCookie(cookie);
			response.sendRedirect(BASE_URL);
			return Response.ok().build();
		} catch (LoginException e) {
			log.info("Login failed");
			Map<String, Object> map = new HashMap<String, Object>();
			Message errorMessages = new Message();
			errorMessages.getErrors().add("Login failed! Check username and password");
			map.put("errors", errorMessages.getErrors());
			return Response.status(Status.UNAUTHORIZED).entity(new Viewable("/login", map)).build();
		}

	}

	@POST
	@Path("logout")
	@Produces(MediaType.TEXT_HTML)
	public Response logoutBrowser(@Context HttpServletResponse response, @CookieParam(AUTH_TOKEN) String authToken)
			throws IOException {
		Cookie cookie = new Cookie(AUTH_TOKEN, authToken);
		cookie.setDomain(COOKIE_DOMAIN);
		cookie.setPath(COOKIE_PATH);
		cookie.setMaxAge(COOKIE_AGE);
		response.addCookie(cookie);
		response.sendRedirect(BASE_URL);
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
			String authToken = httpHeaders.getHeaderString(AUTH_TOKEN);
			log.info("Trying to logout auth_token=" + authToken);
			authenticator.logout(authToken);
			log.info("Logout successful");
			return Response.noContent().build();
		} catch (GeneralSecurityException e) {
			log.info("Logout failed");
			return Response.status(Status.NO_CONTENT).entity(new Message("Your auth token was already invalid")).build();
		}

	}

}
