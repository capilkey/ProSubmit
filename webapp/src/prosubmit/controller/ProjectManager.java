/**
 * 
 */
package prosubmit.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import prosubmit.db.DBAccess;
import prosubmit.db.DBPool;

/**
 * @author ramone
 *
 */
@SuppressWarnings("all")
public final class ProjectManager extends DBAccess {
	static ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	
	/**
	 * 
	 * @param pool
	 */
	public ProjectManager(){
		super((DBPool)context.getBean("dbPool"));
	}
	
	/**
	 * Gets a list of projects for a particular partner
	 * @param partner_id
	 * @param projects
	 * @return
	 */
	public boolean getPartnerProjects(String partner_id,ArrayList<HashMap<String,Object>> projects){
		ArrayList<HashMap<String,Object>> projectIds = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT project_id FROM project WHERE partner_id = ?";
		String [] params = {partner_id};
		queryDB(sql, params,projectIds);
		for(int i =0;i<projectIds.size();i++){
			projects.add(getProject((String)projectIds.get(i).get("project_id")));
		}
		return true;
	}
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	public HashMap<String,Object> getProject(String projectId){
		HashMap<String,Object> project = new HashMap<String,Object>();
		String sql = "SELECT *,IF(group_name,group_name,'N/A') AS group_name, DATE_FORMAT(project_createdate,'%M %D %Y') as project_createdate, DATE_FORMAT(project_editdate,'%M %D %Y') as project_editdate " + 
				"FROM project " + 
				"LEFT JOIN project_status ON project.projstatus_id = project_status.projstatus_id " + 
				"LEFT JOIN `group` ON group.group_id = project.group_id " + 
				"JOIN project_category USING(projcategory_id) " + 
				"JOIN partner USING(partner_id) " +
				"WHERE project_id = ?";
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
	 * Create a single Project record
	 * Fields:<p>
	 * (0)project_id (1)project_title (2)project_desc (3)projstatus_id (4)projcategory_id
	 * @param project_title
	 * @param project_desc
	 * @param projcategory_id
	 * @param partner_id
	 * @return <boolean> true if the operation was successful but false otherwise
	 */
	public boolean addProject(String project_title, String project_desc, String projcategory_id, String partner_id){
        String sql = "INSERT INTO project (partner_id,project_title,project_desc,projstatus_id,projcategory_id) "+ ""
                        + "VALUES ("+partner_id+",\""+project_title+"\",\""+project_desc+"\",1,"+projcategory_id+")";
		return updateDB(sql);
	}

	/**
	 * Update project_title field of Project by project_id, project_title
	 * Fields:<p>
	 * (0) project_id (1) project_title
	 * @param project_id
	 * @param project_title
	 * @return
	 */
	public boolean updateTitleByProjectID(String project_id, String project_title){
		String sql = "UPDATE Project " +
					" SET project_title=? " +
					" WHERE project_id=? ";
		String [] params = {
							project_title,
							project_id
						};
		return updateDB(sql, params);
	}

	/**
	 * Update project_desc field of Project by project_id, project_desc
	 * Fields:<p>
	 * (0) project_id (1) project_desc
	 * @param project_id
	 * @param project_desc
	 * @return
	 */
	public boolean updateDescByProjectID(String project_id, String project_desc){
		String sql = "UPDATE Project " +
					" SET project_desc=? " +
					" WHERE project_id=? ";
		String [] params = {
							project_desc,
							project_id
						};
		return updateDB(sql, params);
	}

	/**
	 * Update projstatus_id field of Project by project_id, projstatus_id
	 * Fields:<p>
	 * (0) project_id (1) projstatus_id
	 * @param project_id
	 * @param projstatus_id
	 * @return
	 */
	public boolean updateStatusByProjectID(String project_id, String projstatus_id){
		String sql = "UPDATE Project " +
					" SET projstatus_id=? " +
					" WHERE project_id=? ";
		String [] params = {
							projstatus_id,
							project_id
						};
		return updateDB(sql, params);
	}

	/**
	 * Update projcategory_id field of Project by project_id, projcategory_id
	 * Fields:<p>
	 * (0) project_id (1) projcategory_id
	 * @param project_id
	 * @param projcategory_id
	 * @return
	 */
	public boolean updateCategoryByProjectID(String project_id, String projcategory_id){
		String sql = "UPDATE Project " +
					" SET projcategory_id=? " +
					" WHERE project_id=? ";
		String [] params = {
							projcategory_id,
							project_id
						};
		return updateDB(sql, params);
	}

	/**
	 * Update group_id field of Project by project_id
	 * Fields:<p>
	 * (0) project_id (1) group_id
	 * @param project_id
	 * @param group_id
	 * @return
	 */
	public boolean updateGroupByProjectID(String project_id, String group_id){
		String sql = "UPDATE Project " +
					" SET group_id=? " +
					" WHERE project_id=? ";
		String [] params = {
							group_id,
							project_id
						};
		return updateDB(sql, params);
	}

	/**
	 * Update project_editdate field of Project by project_id
	 * Fields:<p>
	 * (0) project_id
	 * @param project_id
	 * @return
	 */
	public boolean updateEditDateByProjectID(String project_id){
		String project_editdate = "GETDATE()";
		String sql = "UPDATE Project " +
					" SET project_editdate =?" +
					" WHERE project_id=? ";
		String [] params = {
							project_editdate,
							project_id
						};
		return updateDB(sql, params);
	}

	/**
	 * Delete project record from Project by project_id
	 * Fields:<p>
	 * (0) project_id
	 * @param project_id
	 * @return
	 */
	public boolean removeProjectByProjectID(String project_id)	{
		String sql = "DELETE FROM Project " +
					" WHERE project_id=? ";
		String [] params = {project_id};
		return updateDB(sql, params);
	}

	/**
	 * Delete project records from Project by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id
	 * @param projstatus_id
	 * @return
	 */
	public boolean removeProjectByStatus(String projstatus_id){
		String sql = "DELETE FROM Project " +
					" WHERE projstatus_id=? ";
		String [] params = {projstatus_id};
		return updateDB(sql, params);
	}

	/**
	 * Delete project records from Project by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id
	 * @param projcategory_id
	 * @return
	 */
	public boolean removeProjectByCategory(String projcategory_id){
		String sql = "DELETE FROM Project " +
					" WHERE projcategory_id=? ";
		String [] params = {projcategory_id};
		return updateDB(sql, params);
	}

	/**
	 * Get all project records from Project
	 * @return
	 */
	public boolean getAllProject(){
		String sql = "SELECT * FROM Project";
		return updateDB(sql);
	}

	/**
	 * Get project record from Project by project_id
	 * Fields:<p>
	 * (0) project_id
	 * @param project_id
	 * @return
	 */
	public boolean getProjectInfoByID(String project_id){
		String sql = "SELECT * FROM Project" +
					" WHERE project_id=? ";
		String [] params = {project_id};
		return updateDB(sql, params);
	}

	/**
	 * Get project record from Project by project_title
	 * Fields:<p>
	 * (0) project_title
	 * @param project_title
	 * @return
	 */
	public boolean getProjectInfoByTitle(String project_title){
		String sql = "SELECT * FROM Project" +
					" WHERE project_title=? ";
		String [] params = {project_title};
		return updateDB(sql, params);
	}

	/**
	 * Get project record from Project by createdate
	 * Fields:<p>
	 * (0) createdate
	 * @param createdate
	 * @return
	 */
	public boolean getProjectInfoByCreatedate(String createdate){
		String sql = "SELECT * FROM Project" +
					" WHERE createdate=? ";
		String [] params = {createdate};
		return updateDB(sql, params);
	}

	/**
	 * Get project record from Project by editdate
	 * Fields:<p>
	 * (0) editdate
	 * @param editdate
	 * @return
	 */
	public boolean getProjectInfoByEditDate(String editdate){
		String sql = "SELECT * FROM Project" +
					" WHERE editdate=? ";
		String [] params = {editdate};
		return updateDB(sql, params);
	}

	/**
	 * Get project record from Project by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id
	 * @param projstatus_id
	 * @return
	 */
	public boolean getProjectInfoByStatusID(String projstatus_id){
		String sql = "SELECT * FROM Project" +
					" WHERE projstatus=? ";
		String [] params = {projstatus_id};
		return updateDB(sql, params);
	}

	/**
	 * Get project record from Project by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id
	 * @param projcategory_id
	 * @return
	 *
	public boolean getProjectInfoByCategoryID(String projcategory_id){
		String sql = "SELECT * FROM Project" +
					" WHERE projcategory=? ";
		String [] params = {projcategory};
		return updateDB(sql, params);
	}*/

	/**
	 * Get project record from Project by group_id
	 * Fields:<p>
	 * (0) group_id
	 * @param group_id
	 * @return
	 */
	public boolean getProjectInfoByGroupID(String group_id){
		String sql = "SELECT * FROM Project" +
					" WHERE group_id=? ";
		String [] params = {group_id};
		return updateDB(sql, params);
	}

	/**
	 * Create a single project status record
	 * Fields:<p>
	 * (0)projstatus_id (1)projstatus_name (2)projstatus_desc
	 * @param projstatus_id
	 * @param projstatus_name
	 * @param projstatus_desc
	 * @return
	 */
	public boolean addProjectStatus(String projstatus_id, String projstatus_name, String projstatus_desc){
		String sql = "INSERT INTO project_status VALUES(?,?,?)";
		String [] params = {
							projstatus_id,
							projstatus_name,
							projstatus_desc
						};
		return updateDB(sql, params);
	}

	/**
	 * Update projstatus_name field of project_status by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id (1) projstatus_name
	 * @param projstatus_id
	 * @param projstatus_name
	 * @return
	 */
	public boolean updateNameByStatusID(String projstatus_id, String projstatus_name){
		String sql = "UPDATE project_status " +
					" SET projstatus_name=? " +
					" WHERE projstatus_id=?";
		String [] params = {
							projstatus_name,
							projstatus_id
						};
		return updateDB(sql, params);
	}

	/**
	 * Update projstatus_desc field of project_status by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id (1) projstatus_desc
	 * @param projstatus_id
	 * @param projstatus_desc
	 * @return
	 *
	public boolean updateDescByStatusID(String projstatus_id, String projstatus_name){
		String sql = "UPDATE project_status " +
					" SET projstatus_desc=? " +
					" WHERE projstatus_id=?";
		String [] params = {
							projstatus_desc,
							projstatus_id
						};
		return updateDB(sql, params);
	}*/

	/**
	 * Delete all project status records from project_status
	 * @return
	 */
	public boolean removeAllStatus()	{
		String sql = "DELETE * FROM project_status";
		return updateDB(sql);
	}

	/**
	 * Delete project status record from project_status by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id
	 * @param projstatus_id
	 * @return
	 */
	public boolean removeStatusByStatusID(String projstatus_id)	{
		String sql = "DELETE FROM project_status " +
					" WHERE projstatus_id=? ";
		String [] params = {projstatus_id};
		return updateDB(sql, params);
	}

	/**
	 * Get all status record from project_status
	 * @return
	 */
	public boolean getAllStatusInfo(){
		String sql = "SELECT * FROM project_status";
		return updateDB(sql);
	}

	/**
	 * Get all status names from project_status
	 * @return
	 */
	public boolean getAllStatusName(){
		String sql = "SELECT projstatus_name FROM project_status";
		return updateDB(sql);
	}

	/**
	 * Get status name from Project by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id
	 * @param projstatus_id
	 * @return
	 */
	public boolean getNameByStatusID(String projstatus_id){
		String sql = "SELECT projstatus_name FROM project_status" +
					" WHERE projstatus_id=? ";
		String [] params = {projstatus_id};
		return updateDB(sql, params);
	}

	/**
	 * Get status desc from Project by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id
	 * @param projstatus_id
	 * @return
	 */
	public boolean getDescByStatusID(String projstatus_id){
		String sql = "SELECT projstatus_desc FROM project_status" +
					" WHERE projstatus_id=? ";
		String [] params = {projstatus_id};
		return updateDB(sql, params);
	}


	/**
	 * Create a single project category record
	 * Fields:<p>
	 * (0)projcategory_id (1)projcategory_name (2)projcategory_desc
	 * @param projcategory_id
	 * @param projcategory_name
	 * @param projcategory_desc
	 * @return
	 */
	public boolean addProjectCategory(String projcategory_id, String projcategory_name, String projcategory_desc){
		String sql = "INSERT INTO project_category VALUES(?,?,?)";
		String [] params = {
							projcategory_id,
							projcategory_name,
							projcategory_desc
						};
		return updateDB(sql, params);
	}

	/**
	 * Update projcategory_name field of project_category by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id (1) projcategory_name
	 * @param projcategory_id
	 * @param projcategory_name
	 * @return
	 */
	public boolean updateNameByCategoryID(String projcategory_id, String projcategory_name){
		String sql = "UPDATE project_category " +
					" SET projcategory_name=? " +
					" WHERE projcategory_id=?";
		String [] params = {
							projcategory_name,
							projcategory_id
						};
		return updateDB(sql, params);
	}

	/**
	 * Update projcategory_desc field of project_category by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id (1) projcategory_desc
	 * @param projcategory_id
	 * @param projcategory_desc
	 * @return
	 *
	public boolean updateDescByCategoryID(String projcategory_id, String projcategory_name){
		String sql = "UPDATE project_category " +
					" SET projcategory_desc=? " +
					" WHERE projcategory_id=?";
		String [] params = {
							projcategory_desc,
							projcategory_id
						};
		return updateDB(sql, params);
	}*/

	/**
	 * Delete all project category records from project_category
	 * @return
	 */
	public boolean removeAllCategory()	{
		String sql = "DELETE * FROM project_category";
		return updateDB(sql);
	}

	/**
	 * Delete project category record from project_category by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id
	 * @param projcategory_id
	 * @return
	 */
	public boolean removeCategoryByCategoryID(String projcategory_id)	{
		String sql = "DELETE FROM project_category " +
					" WHERE projcategory_id=? ";
		String [] params = {projcategory_id};
		return updateDB(sql, params);
	}

	/**
	 * Get all category record from project_category
	 * @return
	 */
	public ArrayList<HashMap<String,String>> getAllCategoryInfo(){
		ArrayList<HashMap<String,String>> categories = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT * FROM project_category";
		queryDB(sql,categories);
		return categories;
	}

	/**
	 * Get all category name from project_category
	 * @return
	 */
	public boolean getAllCategoryName(){
		String sql = "SELECT projcategory_id FROM project_category";
		return updateDB(sql);
	}

	/**
	 * Get category name from project_category by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id
	 * @param projcategory_id
	 * @return
	 */
	public boolean getNameByCategoryID(String projcategory_id){
		String sql = "SELECT projcategory_name FROM project_category" +
					" WHERE projcategory_id=? ";
		String [] params = {projcategory_id};
		return updateDB(sql, params);
	}

	/**
	 * Get category desc from project_category by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id
	 * @param projcategory_id
	 * @return
	 */
	public boolean getDescByCategoryID(String projcategory_id){
		String sql = "SELECT projcategory_desc FROM project_category" +
					" WHERE projcategory_id=? ";
		String [] params = {projcategory_id};
		return updateDB(sql, params);
	}
	
	/**
	 * Create a single project comment
	 * Fields:<p>
	 * (0) projcom_id (1) project_id (2) projcom_text (3) projcom_date (4) professor_id
	 * @param projcom_id
	 * @param project_id
	 * @param projcom_text
	 * @param projcom_date
	 * @param professor_id
	 * @return
	 */
	public boolean addProjectComment(String projcom_id, String project_id,
		String projcom_text, String projcom_date, String professor_id){
		String sql = "INSERT INTO project_comment VALUES(?,?,?,?,?)";
		String [] params = {
							projcom_id,
							project_id,
							projcom_text,
							projcom_date,
							professor_id
							};
		return updateDB(sql, params);
	}
	
	/**
	 * Update a single project comment
	 * Fields:<p>
	 * (0) projcom_id (1) project_id (2) projcom_text (3) projcom_date (4) professor_id
	 * @param projcom_id
	 * @param project_id
	 * @param projcom_text
	 * @param projcom_date
	 * @param professor_id
	 * @return
	 */
	public boolean updateProjectComment(String projcom_id, String project_id,
		String projcom_text, String projcom_date, String professor_id){
		String sql = "UPDATE project_comment" +
					" SET projcom_text=?" + "," +
					" projcom_date=?" + "," +
					" professor_id=?" +
					" WHERE projcom_id=?" +
					" AND " +
					" project_id=?";
		String [] params = {
							projcom_text,
							projcom_date,
							professor_id,
							projcom_id,
							project_id
						};
		return updateDB(sql, params);
	}

	/**
	 * Get a single project comment by projcom_id and project_id
	 * Fields:<p>
	 * (0) projcom_id (1) project_id
	 * @param projcom_id
	 * @param project_id
	 * @return
	 */
	public boolean getCommentInfoByID(String projcom_id, String project_id){
		String sql = "SELECT projcom_text, projcom_date, professor_id FROM project_comment" +
					" WHERE procom_id=?" +
					" AND" +
					" project_id=?";
		String [] params = {
							projcom_id,
							project_id
							};
		return updateDB(sql, params);
	}

	/**
	 * Get the info of project comment by projcom_id
	 * Fields:<p>
	 * (0) projcom_id
	 * @param projcom_id
	 * @return
	 */
	public boolean getCommentInfoByProjComID(String projcom_id){
		String sql = "SELECT project_id, projcom_text, projcom_date, professor_id FROM project_comment" +
					" WHERE projcom_id=?";
		String [] params = {projcom_id};
		return updateDB(sql, params);
	}
	
	/**
	 * Get the info of all the project comments by project_id
	 * Fields:<p>
	 * (0) project_id
	 * @param project_id
	 * @return
	 */
	public boolean getAllCommentByProjectID(String project_id){
		String sql = "SELECT projcom_id, projcom_text, projcom_date, professor_id FROM project_comment" +
					" WHERE project_id=?";
		String [] params = {project_id};
		return updateDB(sql, params);
	}

	/**
	 * Get the info of all the project comments by professor_id
	 * Fields:<p>
	 * (0) professor_id
	 * @param professor_id
	 * @return
	 */
	public boolean getAllCommentByProfessorID(String professor_id){
		String sql = "SELECT projcom_id, project_id, projcom_text, projcom_date FROM project_comment" +
					" WHERE professor_id=?";
		String [] params = {professor_id};
		return updateDB(sql, params);
	}

	/**
	 * Get the info of all the project comments by projcom_date
	 * Fields:<p>
	 * (0) projcom_date
	 * @param projcom_date
	 * @return
	 */
	public boolean getAllCommentByDate(String projcom_date){
		String sql = "SELECT projcom_id, project_id, projcom_text, professor_id FROM project_comment" +
					" WHERE projcom_date=?";
		String [] params = {projcom_date};
		return updateDB(sql, params);
	}

	/**
	 * Remove a project comment by projcom_id and project_id
	 * Fields:<p>
	 * (0) projcom_id (1) project_id
	 * @param projcom_id
	 * @param project_id
	 * @return
	 */
	public boolean removeCommentByID(String projcom_id, String project_id){
		String sql = "DELETE FROM project_comment " +
					" WHERE projcom_id=? " +
					" project_id=?";
		String [] params = {
							projcom_id,
							project_id
							};
		return updateDB(sql, params);
	}
	
	/**
	 * Remove all the project comments by project_id
	 * Fields:<p>
	 * (0) project_id
	 * @param project_id
	 * @return
	 */
	public boolean removeAllCommentsByProjectID(String project_id){
		String sql = "DELETE FROM project_comment " +
					" WHERE project_id=?";
		String [] params = {project_id};
		return updateDB(sql, params);
	}
	
	/**
	 * Remove all the project comments by professor_id
	 * Fields:<p>
	 * (0) professor_id
	 * @param professor_id
	 * @return
	 */
	public boolean removeAllCommentsByProfessorID(String professor_id){
		String sql = "DELETE FROM project_comment " +
					" WHERE professor_id=?";
		String [] params = {professor_id};
		return updateDB(sql, params);
	}
	
	/**
	 * Remove all the project comments by projcom_date
	 * Fields:<p>
	 * (0) projcom_date
	 * @param projcom_date
	 * @return
	 */
	public boolean removeAllCommentsByDate(String projcom_date){
		String sql = "DELETE FROM project_comment " +
					" WHERE projcom_date=?";
		String [] params = {projcom_date};
		return updateDB(sql, params);
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
