/**
 * 
 */
package prosubmit.controller;

import java.util.HashMap;

import prosubmit.db.DBAccess;

/**
 * @author ramone
 *
 */
public class SystemManager {
	DBAccess dbAccess  = null;
	
	/**
	 * 
	 * @param dbAccess
	 */
	public SystemManager(DBAccess dbAccess){
		this.dbAccess = dbAccess;
	}
	
	/**
	 * Creates a single system_admin record
	 * @param user_id
	 * @return <boolean> true if the operation was successful but false
	 * otherwise
	 */
	public boolean createAdmin(String user_id){
		String sql = "INSERT INTO system_admin VALUES(?)";
		String [] params = {user_id};
		return dbAccess.updateDB(sql,params);
	}
	
	/**
	 * Removes a single system_admin record identified by id
	 * @param user_id
	 * @return <boolean> true if the operation was successful but
	 * false otherwise
	 */
	public boolean removeAdmin(String user_id){
		String sql = "DELETE FROM system_admin WHERE user_id = " + user_id;
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * Creates single professor record
	 * @param professor_username
	 * @param professor_firstname
	 * @param professor_lastname
	 * @return <boolean> true if the operation was successful but
	 * false otherwise
	 */
	public boolean createProfessor(String professor_username,String professor_firstname,String professor_lastname){
		String sql = "INSERT INTO professor VALUES(?,?,?)";
		String [] params = {
							professor_username,
							professor_firstname,
							professor_lastname
						};
		return dbAccess.updateDB(sql, params);
	}
	
	/**
	 * Gets a single professor identified by id
	 * @param professor
	 * @param professor_id
	 * @return <boolean> true if the query was successful but false
	 * otherwise
	 */
	public boolean getProfessor(HashMap<String,String> professor,String professor_id){
		String sql = "SELECT *" +
				     "FROM professor" +
				     "WHERE professor_id = " + professor_id;
		return dbAccess.queryDB(professor,sql);
	}
	
	/**
	 * Updates a single professor record identified by the 
	 * id
	 * @param professor_id
	 * @param professor_username
	 * @param professor_firstname
	 * @param professor_lastname
	 * @return <boolean> true if the update was successful
	 * and false otherwise
	 */
	public boolean updateProfessor(String professor_id,String professor_username,String professor_firstname,String professor_lastname){
		String sql = "UPDATE professor " +
					" SET professor_username = ? ," +
					" professor_firstname = ?" +
					" professor_lastname = ?" +
					" WHERE professor_id = ?;";
		String [] params = {
							professor_username,
							professor_firstname,
							professor_lastname,
							professor_id
						};
		
		
		return dbAccess.updateDB(sql,params);
	}
	
	/**
	 * Removes a single professor identified by the 
	 * id
	 * @param professor_id
	 * @return <boolean> if the the update ran successfully
	 * and false otherwise
	 */
	public boolean removeProfessor(int professor_id){
		String sql = "DELETE FROM professor" +
					"	WHERE professor_id = " + professor_id;
		return dbAccess.updateDB(sql);
	}
}
