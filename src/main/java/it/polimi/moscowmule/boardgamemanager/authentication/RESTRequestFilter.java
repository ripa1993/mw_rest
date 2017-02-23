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
//		String username = requestContext.getHeaderString(HTTPHeaderNames.USERNAME);
		String authToken = requestContext.getHeaderString(HTTPHeaderNames.AUTH_TOKEN);
		
		log.info("auth_token="+authToken);
		
		// TODO: usare questo per filtrare le richieste
		// bisogna permettere la creazione (POST) ai soli superuser

		if (requestContext.getMethod().equals("POST")) {
			// se sto creando un contenuto
			if (!path.startsWith("business-resource/login")) {
				// se non sto facendo il login
				if (!authenticator.isAuthTokenValid(authToken)) {
					// se il mio token non è valido
					log.info("Not authorized token");
					requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
					// non autorizzare la richiest
				} else {
					// il mio token è valido, ma non sono un super user
					if (!authenticator.isPowerUserToken(authToken)) {
						log.info("Not power user token");
						requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
					}
				}
			}

		}

//		if (!path.startsWith("business-resource/login")) {
//			if (!authenticator.isAuthTokenValid(authToken)) {
//				requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
//			}
//		}
	}

}
