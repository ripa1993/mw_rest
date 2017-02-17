package it.polimi.moscowmule.boardgamemanager.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

@ApplicationPath("/")
public class MultiPartApp extends ResourceConfig {
	public MultiPartApp() {
		super(MultiPartFeature.class, JspMvcFeature.class );
	}

}
