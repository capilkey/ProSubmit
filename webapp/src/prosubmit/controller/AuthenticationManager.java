/**
 * 
 */
package prosubmit.controller;

import java.util.HashMap;

import com.google.gson.Gson;

import prosubmit.db.DBAccess;
import prosubmit.ldap.LDAPAuthenticate;

/**
 * @author ramone
 *
 */
@SuppressWarnings("all")
public class AuthenticationManager {
	private DBAccess dbAccess = null;
	private LDAPAuthenticate ldap = new LDAPAuthenticate(); 
	
	public AuthenticationManager(DBAccess dbAccess) throws NullPointerException{
		// TODO Auto-generated constructor stub
		if(dbAccess != null){
			this.dbAccess = dbAccess;
		}else{
			throw new NullPointerException("Unable to set dbAccess in class AuthenticationManager upon instantiation. Parameter dbAccess is null");
		}
	}
    
	/**
	 * Validate a student with the given username and password
	 * @param username 
	 * @param password
	 * @param info
	 * @return {boolean} true if the user is validated with the given username
	 * and password, false otherwise
	 */
	public boolean validateStudent(String username, String password,HashMap<String,Object> info) {
		// TODO Auto-generated method stub
		boolean validated = false;
		//if(ldap.search(username,password)){
			String sql = "SELECT *, CONCAT(student_firstname,' ',student_lastname) as username FROM student WHERE student_username  = '" + username + "'";
			dbAccess.queryDB(sql,info);
			if(info.isEmpty() == false){
				validated = true;
			}
		//}
		return validated;
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param info
	 * @return{boolean} true if the user is validated with the given username
	 * and password, false otherwise
	 */
	public boolean validatePofessor(String username, String password,HashMap<String,Object> info) {
		// TODO Auto-generated method stub
		boolean validated = false;
		//if(ldap.search(username,password)){
			String sql = "SELECT *,professor_username as username FROM professor WHERE professor_username  = '" + username +"'";
			dbAccess.queryDB(sql,info);
			if(info.isEmpty() == false){
				validated = true;
			}
		//}
		return validated;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param info
	 * @return {boolean} true if the user is validated with the given username
	 * and password, false otherwise
	 */
	public boolean validatePartner(String username, String password,HashMap<String,Object> info) {
		// TODO Auto-generated method stub
		boolean validated = false;
		String sql = "select *,CONCAT(firstname,' ',lastname) as username FROM partner WHERE email  = '" + username + "' AND password = '" + password + "'";
		dbAccess.queryDB(sql,info);
		if(info.isEmpty() == false){
			validated = true;
		}
		return validated;
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param record
	 * @return {boolean} true if the user is validated with the given username
	 * and password, false otherwise
	 */
	public boolean validateAdmin(String username, String password,HashMap<String,Object> info) {
		// TODO Auto-generated method stub
		boolean validated = false;
		//if(ldap.search(username,password)){
			String sql = "SELECT user_id AS `username` FROM system_admin WHERE user_id  = '" + username+"'";
			dbAccess.queryDB(sql,info);
			if(info.isEmpty() == false){
				validated = true;
			}
		//}
		return validated;
	}

}
