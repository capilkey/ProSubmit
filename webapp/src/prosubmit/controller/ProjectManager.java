/**
 * 
 */
package prosubmit.controller;

import java.util.ArrayList;
import java.util.HashMap;

import prosubmit.db.DBAccess;
import prosubmit.db.DBPool;

/**
 * @author ramone
 *
 */
public final class ProjectManager extends DBAccess {
	
	/**
	 * 
	 * @param pool
	 */
	public ProjectManager(DBPool pool){
		super(pool);
	}
	
	/**
	 * Gets a list of projects for a particular partner
	 * @param partner_id
	 * @param projects
	 * @return
	 */
	public boolean getPartnerProjects(String partner_id,ArrayList<HashMap<String,Object>> projects){
		String sql = "SELECT *,projstatus_name as status FROM project JOIN project_status USING(projstatus_id) WHERE project.partner_id = ?";
		String [] params = {partner_id};
		return queryDB(sql, params,projects);
	}
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	public HashMap<String,Object> getProject(String projectId){
		HashMap<String,Object> project = new HashMap<String,Object>();
		String sql = "SELECT *,projstatus_name as status FROM project JOIN project_status USING(projstatus_id) WHERE project_id = ?";
		String [] params = {projectId};
		queryDB(sql, params,project);
		project.put("group",getGroup((String)project.get("group_id")));
		return project;
	}
	
	
	/**
	 * 
	 * @param groupId
	 * @return
	 */
	private HashMap<String,Object> getGroup(String groupId) {
		// TODO Auto-generated method stub
		HashMap<String,Object> group = null;
		if(groupId != null){
			group = new HashMap<String,Object>();
			String sql = "SELECT * FROM group WHERE group_id = ?";
			String [] params = {groupId};
			queryDB(sql, params,group);
		}
		return group;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<HashMap<String,String>> getProjects(){
		ArrayList<HashMap<String,String>> projects = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT *,DATE_FORMAT(project_createdate,'%M %D %Y') as project_createdate FROM project LEFT JOIN project_status USING (projstatus_id)";
		queryDB(sql,projects);
		return projects;
	}
}
