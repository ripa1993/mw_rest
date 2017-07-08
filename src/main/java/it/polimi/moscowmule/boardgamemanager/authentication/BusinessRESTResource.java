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

/**
 * Resource that handles authentication of the users
 * 
 * @author Simone Ripamonti
 * @version 1
 */
@Path("/")
public class BusinessRESTResource {

	private final static Logger log = Logger.getLogger(BusinessRESTResource.class.getName());


	/**
	 * Handles the login using the provided credentials
	 * @param username REQUIRED
	 * @param password REQUIRED 
	 * @return  OK: the generated authentication token {@link AuthToken}
	 * 			UNAUTHORIZED: login failure message {@link Message}
	 */
	@POST
	@Path("login")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response loginApp(@FormParam("username") String username, @FormParam("password") String password) {

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

	/**
	 * Handles the login using the provided credentials
	 * @param username REQUIRED
	 * @param password REQUIRED
	 * @param response
	 * @return  OK: receive cookie and redirect to homepage
	 * 			UNAUTHORIZED: login failure, show error message on login page
	 * @throws IOException
	 */
	@POST
	@Path("login")
	@Produces(MediaType.TEXT_HTML)
	public Response loginBrowser(@FormParam("username") String username, @FormParam("password") String password,
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

	/**
	 * Handles the logout 
	 * @param response
	 * @param authToken as a cookie parameter, if available
	 * @return OK: redirect to homepage
	 * @throws IOException
	 */
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

	/**
	 * Handles the logout
	 * @param httpHeaders
	 * @return  OK: the auth token is revoked {@link Message}
	 * 			NO_CONTENT: if the auth_token was already invalid {@link Message}
	 */
	@POST
	@Path("logout")
	public Response logoutApp(@Context HttpHeaders httpHeaders) {
		try {
			Authenticator authenticator = Authenticator.getInstance();
			String authToken = httpHeaders.getHeaderString(AUTH_TOKEN);
			log.info("Trying to logout auth_token=" + authToken);
			authenticator.logout(authToken);
			log.info("Logout successful");
			return Response.ok(new Message("Auth token revoked")).build();
		} catch (GeneralSecurityException e) {
			log.info("Logout failed");
			return Response.status(Status.NO_CONTENT).entity(new Message("Your auth token was already invalid")).build();
		}

	}

}
