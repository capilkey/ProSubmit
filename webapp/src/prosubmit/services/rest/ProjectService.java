/**
 * 
 */
package prosubmit.services.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.QueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import prosubmit.controller.ProjectManager;
import prosubmit.db.DBPool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import sun.misc.BASE64Decoder;



/**
 * @author ramone
 *
 */
@SuppressWarnings("all")
@Path("/projects")
public class ProjectService {
	private Gson gson = null;
	private ProjectManager projectManager = null;
	BASE64Decoder base64Decoder = new BASE64Decoder();
	
	
	public ProjectService(){
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		
		gson = new GsonBuilder().setPrettyPrinting().create();
		projectManager = new ProjectManager();
	}
	
	/**
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test")
	public String test() {
		HashMap<String,String> response = new HashMap<String,String>();
		response.put("success","true");
		response.put("message","SALL GOOD");
		return gson.toJson(response);
	}
	
	/**
	 * 
	 * @param options
	 * @return
	 */
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  @Path("/search")
	  public String search(
			  @QueryParam("options") String options
			  ) {
		  HashMap<String,Object>  sqlOptions = new HashMap<String,Object>();
		  ArrayList<HashMap<String,Object>> projects = new ArrayList<HashMap<String,Object>>();
		  
		  try {
			byte[] decodedBytes = base64Decoder.decodeBuffer(options);
			options = new String(decodedBytes);
			sqlOptions = gson.fromJson(options,HashMap.class);
			projects = projectManager.searchProjects(sqlOptions);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  //System.out.println(options);
		  return gson.toJson(projects);
	  }
	  
	  
}
