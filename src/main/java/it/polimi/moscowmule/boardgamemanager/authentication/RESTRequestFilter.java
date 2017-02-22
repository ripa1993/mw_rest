package it.polimi.moscowmule.boardgamemanager.authentication;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class RESTRequestFilter implements ContainerRequestFilter {

	private final static Logger log = Logger.getLogger(RESTRequestFilter.class.getName());
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String path = requestContext.getUriInfo().getPath();
		log.info("Filtering request path: "+path);
		
		if(requestContext.getRequest().getMethod().equals("OPTIONS")){
			requestContext.abortWith(Response.ok().build());
			return;
		}
		
		Authenticator authenticator = Authenticator.getInstance();
		String username = requestContext.getHeaderString(HTTPHeaderNames.USERNAME);
		String authToken = requestContext.getHeaderString(HTTPHeaderNames.AUTH_TOKEN);
		
		if (!path.startsWith("/business-resource/login/")){
			if (!authenticator.isAuthTokenValid(authToken, username)){
				requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
			}
		}
	}

}
