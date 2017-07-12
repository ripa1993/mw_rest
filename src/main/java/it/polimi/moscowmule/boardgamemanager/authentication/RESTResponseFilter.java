package it.polimi.moscowmule.boardgamemanager.authentication;

import static it.polimi.moscowmule.boardgamemanager.utils.Constants.AUTH_TOKEN;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import java.util.logging.Logger;

/**
 * Rest response filter, used to add headers to the response
 *
 * @author Simone Ripamonti
 * @version 1
 */
@Provider
@PreMatching
public class RESTResponseFilter implements ContainerResponseFilter {

	private final static Logger log = Logger.getLogger(RESTResponseFilter.class.getName());

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		log.info("Filtering REST Response");
		
		responseContext.getHeaders().add( "Access-Control-Allow-Origin", "*" ); 
        responseContext.getHeaders().add( "Access-Control-Allow-Credentials", "true" );
        responseContext.getHeaders().add( "Access-Control-Allow-Methods", "GET, POST" );
        responseContext.getHeaders().add( "Access-Control-Allow-Headers", AUTH_TOKEN );	}

}
