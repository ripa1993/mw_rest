package it.polimi.moscowmule.boardgamemanager.authentication;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
@PreMatching
public class RESTResponseFilter implements ContainerResponseFilter {

	private final static Logger log = Logger.getLogger(RESTResponseFilter.class.getName());
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		log.info("Filtering REST Response");
		
		responseContext.getHeaders().add( "Access-Control-Allow-Origin", "*" );    // You may further limit certain client IPs with Access-Control-Allow-Origin instead of '*'
        responseContext.getHeaders().add( "Access-Control-Allow-Credentials", "true" );
        responseContext.getHeaders().add( "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT" );
        responseContext.getHeaders().add( "Access-Control-Allow-Headers", HTTPHeaderNames.USERNAME + ", " + HTTPHeaderNames.AUTH_TOKEN );	}

}
