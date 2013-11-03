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
		project.put("comments",getProjectComments((String)project.get("project_id")));
		return project;
	}
	
	/**
	 * 
	 * @param string
	 * @return
	 */
	private ArrayList<HashMap<String,Object>> getProjectComments(String projectId) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String,Object>> comments = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT *,CONCAT(professor_firstname,' ',professor_lastname) as professor_name,DATE_FORMAT(projcom_date,'%M %D %Y') AS projcom_date  FROM project_comment LEFT JOIN professor USING(professor_id) WHERE project_id = ?";
		String [] params = {projectId};
		queryDB(sql,params,comments);
		return comments;
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
	
	/**
	 * 
	 * @param projectId
	 * @param profId
	 * @param comment
	 * @return
	 */
	public boolean addComment(String projectId,String profId,String comment){
		String sql = "INSERT INTO project_comment (project_id,projcom_text,projcom_date,professor_id) VALUES(?,?,current_timestamp,?)";
		String [] params = {projectId,comment,profId};
		return updateDB(sql,params);
	}
	
	/**
	 * 
	 * @param commentId
	 * @return
	 */
	public boolean deleteComment(String commentId) {
		// TODO Auto-generated method stub
		String sql = "DELETE from project_comment WHERE projcom_id = ?";
		String [] params = {commentId};
		return updateDB(sql,params);
	}
	
	
	/**
	 * 
	 * @param count
	 * @return
	 */
	public ArrayList<HashMap<String,String>> getActiveProjects(int count){
		ArrayList<HashMap<String,String>> projects = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT *,DATE_FORMAT(project_createdate,'%M %D %Y') as project_createdate FROM project LEFT JOIN project_status USING (projstatus_id) WHERE projstatus_id NOT IN (1,2,4,6,8) ORDER BY project_createdate DESC LIMIT " + count;
		queryDB(sql,projects);
		return projects;
	}
	
	/**
	 * 
	 * @param count
	 * @return
	 */
	public ArrayList<HashMap<String,String>> getMostRecentProjects(int count){
		ArrayList<HashMap<String,String>> projects = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT *,DATE_FORMAT(project_createdate,'%M %D %Y') as project_createdate FROM project LEFT JOIN project_status USING (projstatus_id) ORDER BY project_createdate DESC LIMIT " + count;
		queryDB(sql,projects);
		return projects;
	}
	
	
	/**
	 * 
	 * @param count
	 * @return
	 */
	public ArrayList<HashMap<String,String>> getCompletedProjects(int count){
		ArrayList<HashMap<String,String>> projects = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT *,DATE_FORMAT(project_createdate,'%M %D %Y') as project_createdate FROM project LEFT JOIN project_status USING (projstatus_id) WHERE projstatus_id = 8 ORDER BY project_createdate DESC LIMIT " + count;
		queryDB(sql,projects);
		return projects;
	}
	
	
}
