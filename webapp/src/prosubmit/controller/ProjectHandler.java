/**
 * @(#)ProjectHandlers.java
 *
 *
 * @author Chaobo Xie
 * @version 1.00 2013/9/27
 */

package prosubmit.controller;

import java.util.ArrayList;
import java.util.HashMap;

import prosubmit.db.DBAccess;

//import db.DBAccess;

/**
 * <p>
 * SQL Method prefix Legend: <p>
 * 1. add
 * 2. modify
 * 3. remove
 * 4. get
 */

public class ProjectHandler {
	DBAccess dbAccess = null;

	/**
	 * Empty constructor
	 */
	public ProjectHandler(){
	}

	/**
	 * One argument constructor
	 * @param dbAccess
	 */
    public ProjectHandler(DBAccess dbAccess) {
    	this.dbAccess = dbAccess;
    }

	/**
	 * Create a single Project record
	 * Fields:<p>
	 * (0)project_id (1)project_title (2)project_desc (3)projstatus_id (4)projcategory_id
	 * @param result
	 * @param project_id
	 * @param project_title
	 * @param project_desc
	 * @param projstatus_id
	 * @param projcategory_id
	 * @return <boolean> true if the operation was successful but false
	 * otherwise
	 */
	public boolean addProject(String project_title, String project_desc, String projcategory_id, String partner_id){
		String sql = "INSERT INTO project (partner_id,project_title,project_desc,projstatus_id,projcategory_id) "+ ""
				+ "VALUES ("+partner_id+",\""+project_title+"\",\""+project_desc+"\",1,"+projcategory_id+")";
		return dbAccess.updateDB(sql);
	}

	/**
	 * Update project_title field of project by project_id, project_title
	 * Fields:<p>
	 * (0) project_id (1) project_title
	 * @param project_id
	 * @param project_title
	 * @return
	 */
	public boolean updateTitleByprojectID(String project_id, String project_title){
		String sql = "UPDATE project " +
					" SET project_title=? " +
					" WHERE project_id=? ";
		String [] params = {
							project_title,
							project_id
						};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Update project_desc field of project by project_id, project_desc
	 * Fields:<p>
	 * (0) project_id (1) project_desc
	 * @param project_id
	 * @param project_desc
	 * @return
	 */
	public boolean updateDescByProjectID(String project_id, String project_desc){
		String sql = "UPDATE project " +
					" SET project_desc=? " +
					" WHERE project_id=? ";
		String [] params = {
							project_desc,
							project_id
						};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Update projstatus_id field of project by project_id, projstatus_id
	 * Fields:<p>
	 * (0) project_id (1) projstatus_id
	 * @param project_id
	 * @param projstatus_id
	 * @return
	 */
	public boolean updateStatusByProjectID(String project_id, String projstatus_id){
		String sql = "UPDATE project " +
					" SET projstatus_id=? " +
					" WHERE project_id=? ";
		String [] params = {
							projstatus_id,
							project_id
						};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Update projcategory_id field of project by project_id, projcategory_id
	 * Fields:<p>
	 * (0) project_id (1) projcategory_id
	 * @param project_id
	 * @param projcategory_id
	 * @return
	 */
	public boolean updateCategoryByProjectID(String project_id, String projcategory_id){
		String sql = "UPDATE project " +
					" SET projcategory_id=? " +
					" WHERE project_id=? ";
		String [] params = {
							projcategory_id,
							project_id
						};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Update group_id field of project by project_id
	 * Fields:<p>
	 * (0) project_id (1) group_id
	 * @param project_id
	 * @param group_id
	 * @return
	 */
	public boolean updateGroupByProjectID(String project_id, String group_id){
		String sql = "UPDATE project " +
					" SET group_id=? " +
					" WHERE project_id=? ";
		String [] params = {
							group_id,
							project_id
						};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Update project_editdate field of project by project_id
	 * Fields:<p>
	 * (0) project_id
	 * @param project_id
	 * @return
	 */
	public boolean updateEditDateByProjectID(String project_id){
		String project_editdate = "GETDATE()";
		String sql = "UPDATE project " +
					" SET project_editdate =?" +
					" WHERE project_id=? ";
		String [] params = {
							project_editdate,
							project_id
						};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Delete project record from project by project_id
	 * Fields:<p>
	 * (0) project_id
	 * @param project_id
	 * @return
	 */
	public boolean removeProjectByProjectID(String project_id)	{
		String sql = "DELETE FROM project " +
					" WHERE project_id=? ";
		String [] params = {project_id};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Delete project records from project by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id
	 * @param projstatus_id
	 * @return
	 */
	public boolean removeProjectByStatus(String projstatus_id){
		String sql = "DELETE FROM project " +
					" WHERE projstatus_id=? ";
		String [] params = {projstatus_id};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Delete project records from project by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id
	 * @param projcategory_id
	 * @return
	 */
	public boolean removeProjectByCategory(String projcategory_id){
		String sql = "DELETE FROM project " +
					" WHERE projcategory_id=? ";
		String [] params = {projcategory_id};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Get all project records from project
	 * @return
	 */
	public boolean getAllProject(ArrayList<HashMap<String,String>> results){
		String sql = "SELECT *,projstatus_name as status,projcategory_name as category FROM project JOIN project_status USING(projstatus_id) JOIN project_category USING(projcategory_id)";
		return dbAccess.queryDB(sql, results);
	}

	/**
	 * Get project record from project by project_id
	 * Fields:<p>
	 * (0) project_id
	 * @param project_id
	 * @return
	 */
	public boolean getProjectInfoByID(String project_id, HashMap<String,Object> result){
		String sql = "SELECT * FROM project" +
					" JOIN project_status USING(projstatus_id) " +
					" JOIN project_category USING(projcategory_id) " +
					" JOIN partner USING(partner_id) " +
					" WHERE project_id=? ";
		String [] params = {project_id};
		return dbAccess.queryDB(sql, params, result);
	}

	/**
	 * Get project record from project by project_title
	 * Fields:<p>
	 * (0) project_title
	 * @param project_title
	 * @return
	 */
	public boolean getProjectInfoByTitle(String project_title, HashMap<String,Object> result){
		String sql = "SELECT * FROM project" +
					" WHERE project_title=? ";
		String [] params = {project_title};
		return dbAccess.queryDB(sql, params, result);
	}

	/**
	 * Get project record from project by createdate
	 * Fields:<p>
	 * (0) createdate
	 * @param createdate
	 * @return
	 */
	public boolean getProjectInfoByCreatedate(String createdate, ArrayList<HashMap<String,Object>> results){
		String sql = "SELECT * FROM project" +
					" WHERE createdate=? ";
		String [] params = {createdate};
		return dbAccess.queryDB(sql, params, results);
	}

	/**
	 * Get project record from project by editdate
	 * Fields:<p>
	 * (0) editdate
	 * @param editdate
	 * @return
	 */
	public boolean getProjectInfoByEditDate(String editdate, ArrayList<HashMap<String,Object>> results){
		String sql = "SELECT * FROM project" +
					" WHERE editdate=? ";
		String [] params = {editdate};
		return dbAccess.queryDB(sql, params, results);
	}

	/**
	 * Get project record from project by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id
	 * @param projstatus_id
	 * @return
	 */
	public boolean getProjectInfoByStatusID(String projstatus_id, ArrayList<HashMap<String,Object>> results){
		String sql = "SELECT * FROM project" +
					" WHERE projstatus=? ";
		String [] params = {projstatus_id};
		return dbAccess.queryDB(sql, params, results);
	}

	/**
	 * Get project record from project by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id
	 * @param projcategory_id
	 * @return
	 */
	public boolean getProjectInfoByCategoryID(String projcategory_id, ArrayList<HashMap<String,Object>> results){
		String sql = "SELECT * FROM project" +
					" WHERE projcategory=? ";
		String [] params = {projcategory_id};
		return dbAccess.queryDB(sql, params, results);
	}

	/**
	 * Get project record from project by group_id
	 * Fields:<p>
	 * (0) group_id
	 * @param group_id
	 * @return
	 */
	public boolean getProjectInfoByGroupID(String group_id, HashMap<String,Object> result){
		String sql = "SELECT * FROM project" +
					" WHERE group_id=? ";
		String [] params = {group_id};
		return dbAccess.queryDB(sql, params, result);
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
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Update projstatus_name field of project_Status by projstatus_id
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
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Update projstatus_desc field of project_Status by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id (1) projstatus_desc
	 * @param projstatus_id
	 * @param projstatus_desc
	 * @return
	 */
	public boolean updateDescByStatusID(String projstatus_id, String projstatus_desc){
		String sql = "UPDATE project_status " +
					" SET projstatus_desc=? " +
					" WHERE projstatus_id=?";
		String [] params = {
							projstatus_desc,
							projstatus_id
						};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Delete all project status records from project_status
	 * @return
	 */
	public boolean removeAllStatus()	{
		String sql = "DELETE * FROM project_status";
		return dbAccess.updateDB(sql);
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
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Get all status record from project_status
	 * @return
	 */
	public boolean getAllStatusInfo(ArrayList<HashMap<String,String>> results){
		String sql = "SELECT * FROM project_status";
		return dbAccess.queryDB(sql, results);
	}

	/**
	 * Get all status names from project_status
	 * @return
	 */
	public boolean getAllStatusName(ArrayList<HashMap<String,String>> results){
		String sql = "SELECT projstatus_name FROM project_status";
		return dbAccess.queryDB(sql, results);
	}

	/**
	 * Get status name from Project by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id
	 * @param projstatus_id
	 * @return
	 */
	public boolean getNameByStatusID(String projstatus_id, HashMap<String,Object> result){
		String sql = "SELECT projstatus_name FROM project_status" +
					" WHERE projstatus_id=? ";
		String [] params = {projstatus_id};
		return dbAccess.queryDB(sql, params, result);
	}

	/**
	 * Get status desc from Project by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id
	 * @param projstatus_id
	 * @return
	 */
	public boolean getDescByStatusID(String projstatus_id, HashMap<String,Object> result){
		String sql = "SELECT projstatus_desc FROM project_status" +
					" WHERE projstatus_id=? ";
		String [] params = {projstatus_id};
		return dbAccess.queryDB(sql, params, result);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Update projcategory_desc field of project_category by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id (1) projcategory_desc
	 * @param projcategory_id
	 * @param projcategory_desc
	 * @return
	 */
	public boolean updateDescByCategoryID(String projcategory_id, String projcategory_desc){
		String sql = "UPDATE project_category " +
					" SET projcategory_desc=? " +
					" WHERE projcategory_id=?";
		String [] params = {
							projcategory_desc,
							projcategory_id
						};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Delete all project category records from project_category
	 * @return
	 */
	public boolean removeAllCategory()	{
		String sql = "DELETE * FROM project_category";
		return dbAccess.updateDB(sql);
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
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Get all category record from project_category
	 * @return
	 */
	public boolean getAllCategoryInfo(ArrayList<HashMap<String,String>> results){
		String sql = "SELECT * FROM project_category";
		return dbAccess.queryDB(sql, results);
	}

	/**
	 * Get all category name from project_category
	 * @return
	 */
	public boolean getAllCategoryName(ArrayList<HashMap<String,String>> results){
		String sql = "SELECT projcategory_id FROM project_category";
		return dbAccess.queryDB(sql, results);
	}

	/**
	 * Get category name from project_category by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id
	 * @param projcategory_id
	 * @return
	 */
	public boolean getNameByCategoryID(String projcategory_id, HashMap<String,Object> result){
		String sql = "SELECT projcategory_name FROM project_category" +
					" WHERE projcategory_id=? ";
		String [] params = {projcategory_id};
		return dbAccess.queryDB(sql, params, result);
	}

	/**
	 * Get category desc from project_category by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id
	 * @param projcategory_id
	 * @return
	 */
	public boolean getDescByCategoryID(String projcategory_id, HashMap<String,Object> result){
		String sql = "SELECT projcategory_desc FROM project_category" +
					" WHERE projcategory_id=? ";
		String [] params = {projcategory_id};
		return dbAccess.queryDB(sql, params, result);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Get a single project comment by projcom_id and project_id
	 * Fields:<p>
	 * (0) projcom_id (1) project_id
	 * @param projcom_id
	 * @param project_id
	 * @return
	 */
	public boolean getCommentInfoByID(String projcom_id, String project_id, HashMap<String,Object> result){
		String sql = "SELECT projcom_text, projcom_date, professor_id FROM project_comment" +
					" WHERE procom_id=?" +
					" AND" +
					" project_id=?";
		String [] params = {
							projcom_id,
							project_id
							};
		return dbAccess.queryDB(sql, params, result);
	}

	/**
	 * Get the info of project comment by projcom_id
	 * Fields:<p>
	 * (0) projcom_id
	 * @param projcom_id
	 * @return
	 */
	public boolean getCommentInfoByProjComID(String projcom_id, HashMap<String,Object> result){
		String sql = "SELECT project_id, projcom_text, projcom_date, professor_id FROM project_comment" +
					" WHERE projcom_id=?";
		String [] params = {projcom_id};
		return dbAccess.queryDB(sql, params, result);
	}
	
	/**
	 * Get the info of all the project comments by project_id
	 * Fields:<p>
	 * (0) project_id
	 * @param project_id
	 * @return
	 */
	public boolean getAllCommentByProjectID(String project_id, ArrayList<HashMap<String,Object>> results){
		String sql = "SELECT projcom_id, projcom_text, projcom_date, professor_id FROM project_comment" +
					" WHERE project_id=?";
		String [] params = {project_id};
		return dbAccess.queryDB(sql, params, results);
	}

	/**
	 * Get the info of all the project comments by professor_id
	 * Fields:<p>
	 * (0) professor_id
	 * @param professor_id
	 * @return
	 */
	public boolean getAllCommentByProfessorID(String professor_id, ArrayList<HashMap<String,Object>> results){
		String sql = "SELECT projcom_id, project_id, projcom_text, projcom_date FROM project_comment" +
					" WHERE professor_id=?";
		String [] params = {professor_id};
		return dbAccess.queryDB(sql, params, results);
	}

	/**
	 * Get the info of all the project comments by projcom_date
	 * Fields:<p>
	 * (0) projcom_date
	 * @param projcom_date
	 * @return
	 */
	public boolean getAllCommentByDate(String projcom_date, ArrayList<HashMap<String,Object>> results){
		String sql = "SELECT projcom_id, project_id, projcom_text, professor_id FROM project_comment" +
					" WHERE projcom_date=?";
		String [] params = {projcom_date};
		return dbAccess.queryDB(sql, params, results);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
	}
}