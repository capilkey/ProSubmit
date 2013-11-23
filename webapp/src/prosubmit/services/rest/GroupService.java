/**
 * 
 */
package prosubmit.services.rest;

import java.util.HashMap;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import prosubmit.controller.GroupManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Ramone
 *
 */
@SuppressWarnings("all")
@Path("/groups")
public final class GroupService {
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private GroupManager groupManager;
	private HashMap<String,String> response;
	
	public GroupService(){
		groupManager = new GroupManager();
		response = new HashMap<String,String>();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/group/{groupId}")
	public String deleteGroup(@PathParam("groupId") String groupId){
		response.put("success","0");
		if(groupManager.removeGroup(groupId)){
			response.put("success","1");
		}else{
			response.put("success","0");
			response.put("message","Unable to delete group");
		}
		return gson.toJson(response); 
	}
	
}
