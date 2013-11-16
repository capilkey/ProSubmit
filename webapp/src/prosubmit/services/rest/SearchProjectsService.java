/**
 * 
 */
package prosubmit.services.rest;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.QueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author ramone
 *
 */
@Path("/SearchProjects")
public class SearchProjectsService {
	  
	/**
	 * 
	 * @param options
	 * @return
	 */
	  @GET
	  @Produces(MediaType.TEXT_PLAIN)
	  @Path("/search")
	  public ArrayList<HashMap<String,Object>> search(
			  @QueryParam("options") String options
			  ) {
		  ArrayList<HashMap<String,Object>> projects = new ArrayList<HashMap<String,Object>>();
		  
		  //System.out.println(options);
		  return projects;
	  }
}
