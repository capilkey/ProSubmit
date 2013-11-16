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
		String sql = "SELECT *,DATE_FORMAT(project_createdate,'%M %D %Y') as project_createdate FROM project JOIN project_status USING(projstatus_id) WHERE project_id = ?";
		String [] params = {projectId};
		queryDB(sql, params,project);
		project.put("group",getGroup((String)project.get("group_id")));
		project.put("comments",getProjectComments((String)project.get("project_id")));
		return project;
	}
	
	/**
	 * 
	 * @param projectIds
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> getProjects(ArrayList<String> projectIds){
		ArrayList<HashMap<String,Object>> projects = new ArrayList<HashMap<String,Object>>();
		for(int i = 0;i<projectIds.size();i++){
			projects.add(getProject(projectIds.get(i)));
		}
		return projects;
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
	
	/**
	 * 
	 * @param categoryName
	 * @param categoryDesc
	 * @return
	 */
	public boolean addProjectCategory(String categoryName, String categoryDesc) {
		// TODO Auto-generated method stub
		boolean success = false;
		String sql = "INSERT INTO project_category (projcategory_name,projcategory_desc) VALUES(?,?)";
		String [] params = {categoryName,categoryDesc};
		success = updateDB(sql,params);
		return success; 
	}

	/**
	 * 
	 * @param projcategory_id
	 * @return
	 */
	public boolean deleteProjectCategory(String projcategory_id) {
		// TODO Auto-generated method stub
		boolean success = false;
		String sql = "DELETE FROM project_category WHERE projcategory_id = ?";
		String [] params = {projcategory_id};
		success = updateDB(sql,params);
		return success;
	}
	
	/**
	 * 
	 * @param statusName
	 * @param statusDesc
	 * @return
	 */
	public boolean addProjectStatus(String statusName, String statusDesc) {
		// TODO Auto-generated method stub
		boolean success = false;
		String sql = "INSERT INTO project_status (projstatus_name,projstatus_desc) VALUES(?,?)";
		String [] params = {statusName,statusDesc};
		success = updateDB(sql,params);
		return success;
	} 
	
	/**
	 * 
	 * @param projstatusId
	 * @return
	 */
	public boolean deleteProjectStatus(String projstatusId) {
		// TODO Auto-generated method stub
		boolean success = false;
		String sql = "DELETE FROM project_status WHERE projstatus_id = ?";
		String [] params = {projstatusId};
		success = updateDB(sql,params);
		return success;
	}
	
	/**
	 * 
	 * @param sqlOptions
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> searchProjects( HashMap<String,Object> sqlOptions) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		
		ArrayList<HashMap<String,String>> projects = new ArrayList<HashMap<String,String>>();
		ArrayList<String> projectIds = new ArrayList<String>();
		ArrayList<HashMap<String,String>> projectIdsResult = new ArrayList<HashMap<String,String>>();
		
		String keywords = (String)sqlOptions.get("keywords");
		String from = (String)sqlOptions.get("from_date");
		String to = (String)sqlOptions.get("to_date");
		ArrayList categories = (ArrayList)sqlOptions.get("categories");
		ArrayList statuses = (ArrayList)sqlOptions.get("statuses");
		
		sql.append("SELECT DISTINCT(project_id) \nFROM project,project_category,project_status \nWHERE ");
		
		if(!keywords.equals("")){
			sql.append("\n(\n");
			String[] searches = keywords.split(",");
			for(int i =0;i<searches.length;i++){
				sql.append(" project_title LIKE ('%" + searches[i] + "%')");
				sql.append("\n OR \n");
				sql.append(" project_desc LIKE ('%" + searches[i] + "%')");
				if(i < (searches.length -1)){
					sql.append("\n OR \n");
				}
			}
			sql.append("\n) OR ");
		}
		sql.append("1 = 1");
		
		if(categories != null && categories.size() > 0){		
			StringBuilder cats = new StringBuilder();
			for(int i = 0;i<categories.size();i++){
				cats.append(categories.get(i));
				if(i < (categories.size() - 1)){
					cats.append(",");
				}
			}
			sql.append("\nAND project.projcategory_id IN (" + cats.toString() + ")");
		}
		
		if(statuses!=null && statuses.size() > 0){		
			StringBuilder stats = new StringBuilder();
			for(int i = 0;i<statuses.size();i++){
				stats.append(statuses.get(i));
				if(i < (statuses.size() - 1)){
					stats.append(",");
				}
			}
			sql.append("\nAND project.projstatus_id IN (" + stats.toString() + ")");
		}
		
		if(!from.equals("") && !to.equals("")){
			sql.append("\nAND project_createdate BETWEEN "+from+" AND " + to);
		}else if(!from.equals("") && to.equals("")){
			sql.append("\nAND project_createdate >= "+from);
		}else if(from.equals("") && !to.equals("")){
			sql.append("\nAND project_createdate <= " + to);
		}
		
		
		sql.append("\nORDER BY project_createdate,project_editdate");
		System.out.println(sql.toString());
		queryDB(sql.toString(),projectIdsResult);
		
		for(int i =0;i<projectIdsResult.size();i++){
			projectIds.add(projectIdsResult.get(i).get("project_id"));
		}
		return getProjects(projectIds);
	}
	
	
}
