/**
 * 
 */
package prosubmit.controller;

import java.util.ArrayList;
import java.util.HashMap;

import prosubmit.db.DBAccess;

/**
 * @author ramone
 *
 */
public final class ProjectManager {
	private DBAccess dbAccess = null;
	
	public ProjectManager(DBAccess dbAccess){
		this.dbAccess = dbAccess;
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
		return dbAccess.queryDB(sql, params,projects);
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<HashMap<String,String>> getProjects(){
		ArrayList<HashMap<String,String>> projects = new ArrayList<HashMap<String,String>>();
		String sql = "SELECT * FROM project";
		dbAccess.queryDB(sql,projects);
		return projects;
	}
}
