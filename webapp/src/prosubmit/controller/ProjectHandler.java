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
	public boolean addProject(String project_id, String project_title, String project_desc, String projcategory_id){
		String projstatus_id = "Unmatched";
		String project_createdate = "GETDATE()";
		String sql = "INSERT INTO Project (project_id, project_title, project_desc, project_createdate, projstatus_id, projcategory_id) VALUES(?,?,?,?,?)";
		String [] params = {
							project_id,
							project_title,
							project_desc,
							project_createdate,
							projstatus_id,
							projcategory_id
						};
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Get all project records from Project
	 * @return
	 */
	public boolean getAllProject(){
		String sql = "SELECT * FROM Project";
		return dbAccess.updateDB(sql);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
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
		return dbAccess.updateDB(sql, params);
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
		String [] params = {projstatus};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Get project record from Project by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id
	 * @param projcategory_id
	 * @return
	 */
	public boolean getProjectInfoByCategoryID(String projcategory_id){
		String sql = "SELECT * FROM Project" +
					" WHERE projcategory=? ";
		String [] params = {projcategory};
		return dbAccess.updateDB(sql, params);
	}

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
		return dbAccess.updateDB(sql, params);
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
		String sql = "INSERT INTO Project_Status VALUES(?,?,?)";
		String [] params = {
							projstatus_id,
							projstatus_name,
							projstatus_desc
						};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Update projstatus_name field of Project_Status by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id (1) projstatus_name
	 * @param projstatus_id
	 * @param projstatus_name
	 * @return
	 */
	public boolean updateNameByStatusID(String projstatus_id, String projstatus_name){
		String sql = "UPDATE Project_Status " +
					" SET projstatus_name=? " +
					" WHERE projstatus_id=?";
		String [] params = {
							projstatus_name,
							projstatus_id
						};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Update projstatus_desc field of Project_Status by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id (1) projstatus_desc
	 * @param projstatus_id
	 * @param projstatus_desc
	 * @return
	 */
	public boolean updateDescByStatusID(String projstatus_id, String projstatus_name){
		String sql = "UPDATE Project_Status " +
					" SET projstatus_desc=? " +
					" WHERE projstatus_id=?";
		String [] params = {
							projstatus_desc,
							projstatus_id
						};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Delete all project status records from Project_Status
	 * @return
	 */
	public boolean removeAllStatus()	{
		String sql = "DELETE * FROM Project_Status";
		return dbAccess.updateDB(sql);
	}

	/**
	 * Delete project status record from Project_Status by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id
	 * @param projstatus_id
	 * @return
	 */
	public boolean removeStatusByStatusID(String projstatus_id)	{
		String sql = "DELETE FROM Project_Status " +
					" WHERE projstatus_id=? ";
		String [] params = {projstatus_id};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Get all status record from Project_Status
	 * @return
	 */
	public boolean getAllStatusInfo(){
		String sql = "SELECT * FROM Project_Status";
		return dbAccess.updateDB(sql);
	}

	/**
	 * Get all status names from Project_Status
	 * @return
	 */
	public boolean getAllStatusName(){
		String sql = "SELECT projstatus_name FROM Project_Status";
		return dbAccess.updateDB(sql);
	}

	/**
	 * Get status name from Project by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id
	 * @param projstatus_id
	 * @return
	 */
	public boolean getNameByStatusID(String projstatus_id){
		String sql = "SELECT projstatus_name FROM Project_Status" +
					" WHERE projstatus_id=? ";
		String [] params = {projstatus_id};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Get status desc from Project by projstatus_id
	 * Fields:<p>
	 * (0) projstatus_id
	 * @param projstatus_id
	 * @return
	 */
	public boolean getDescByStatusID(String projstatus_id){
		String sql = "SELECT projstatus_desc FROM Project_Status" +
					" WHERE projstatus_id=? ";
		String [] params = {projstatus_id};
		return dbAccess.updateDB(sql, params);
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
		String sql = "INSERT INTO Project_Category VALUES(?,?,?)";
		String [] params = {
							projcategory_id,
							projcategory_name,
							projcategory_desc
						};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Update projcategory_name field of Project_Category by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id (1) projcategory_name
	 * @param projcategory_id
	 * @param projcategory_name
	 * @return
	 */
	public boolean updateNameByCategoryID(String projcategory_id, String projcategory_name){
		String sql = "UPDATE Project_Category " +
					" SET projcategory_name=? " +
					" WHERE projcategory_id=?";
		String [] params = {
							projcategory_name,
							projcategory_id
						};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Update projcategory_desc field of Project_Category by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id (1) projcategory_desc
	 * @param projcategory_id
	 * @param projcategory_desc
	 * @return
	 */
	public boolean updateDescByCategoryID(String projcategory_id, String projcategory_name){
		String sql = "UPDATE Project_Category " +
					" SET projcategory_desc=? " +
					" WHERE projcategory_id=?";
		String [] params = {
							projcategory_desc,
							projcategory_id
						};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Delete all project category records from Project_Category
	 * @return
	 */
	public boolean removeAllCategory()	{
		String sql = "DELETE * FROM Project_Category";
		return dbAccess.updateDB(sql);
	}

	/**
	 * Delete project category record from Project_Category by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id
	 * @param projcategory_id
	 * @return
	 */
	public boolean removeCategoryByCategoryID(String projcategory_id)	{
		String sql = "DELETE FROM Project_Category " +
					" WHERE projcategory_id=? ";
		String [] params = {projcategory_id};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Get all category record from Project_Category
	 * @return
	 */
	public boolean getAllCategoryInfo(){
		String sql = "SELECT * FROM Project_Category";
		return dbAccess.updateDB(sql);
	}

	/**
	 * Get all category name from Project_Category
	 * @return
	 */
	public boolean getAllCategoryName(){
		String sql = "SELECT projcategory_id FROM Project_Category";
		return dbAccess.updateDB(sql);
	}

	/**
	 * Get category name from Project_Category by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id
	 * @param projcategory_id
	 * @return
	 */
	public boolean getNameByCategoryID(String projcategory_id){
		String sql = "SELECT projcategory_name FROM Project_Category" +
					" WHERE projcategory_id=? ";
		String [] params = {projcategory_id};
		return dbAccess.updateDB(sql, params);
	}

	/**
	 * Get category desc from Project_Category by projcategory_id
	 * Fields:<p>
	 * (0) projcategory_id
	 * @param projcategory_id
	 * @return
	 */
	public boolean getDescByCategoryID(String projcategory_id){
		String sql = "SELECT projcategory_desc FROM Project_Category" +
					" WHERE projcategory_id=? ";
		String [] params = {projcategory_id};
		return dbAccess.updateDB(sql, params);
	}


	public boolean generateProjectReport()
	{
	}
}