package it.polimi.moscowmule.boardgamemanager.authentication;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class RESTRequestFilter implements ContainerRequestFilter {

	private final static Logger log = Logger.getLogger(RESTRequestFilter.class.getName());

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String path = requestContext.getUriInfo().getPath();
		log.info("Filtering request path: " + path);

		if (requestContext.getRequest().getMethod().equals("OPTIONS")) {
			requestContext.abortWith(Response.ok().build());
			return;
		}

		Authenticator authenticator = Authenticator.getInstance();
		String authToken = requestContext.getHeaderString(HTTPHeaderNames.AUTH_TOKEN);
		
		log.info("auth_token="+authToken);
		
		/*
		 * Allow creation of content to power users only
		 */

		if (requestContext.getMethod().equals("POST")) {
			// if there's a POST request
			if (!path.startsWith("login")) {
				// and it is not about "login"
				if (!authenticator.isAuthTokenValid(authToken)) {
					// if the token is invalid
					log.info("Not authorized token");
					requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
				} else {
					// if the token is valid, but it doesn't belong to a poweruser
					if (!authenticator.isPowerUserToken(authToken)) {
						log.info("Not power user token");
						requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
					}
				}
			}

		}
	}

}
