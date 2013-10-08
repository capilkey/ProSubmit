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
	public boolean validateStudent(String username, String password,HashMap<String,String> info) {
		// TODO Auto-generated method stub
		boolean validated = false;
		//if(ldap.search(username,password)){
			String sql = "SELECT * FROM student WHERE student_username  = '" + username + "'";
			dbAccess.queryDB(info, sql);
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
	public boolean validatePofessor(String username, String password,HashMap<String,String> info) {
		// TODO Auto-generated method stub
		boolean validated = false;
		//if(ldap.search(username,password)){
			String sql = "select * FROM professor WHERE professor_username  = '" + username +"'";
			dbAccess.queryDB(info, sql);
			if(info.get("professor_username")!= null){
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
	public boolean validatePartner(String username, String password,HashMap<String,String> info) {
		// TODO Auto-generated method stub
		boolean validated = false;
		String sql = "select * FROM partner WHERE partner_email  = '" + username + "' AND partner_hashpassword = SHA1('" + password + "')";
		dbAccess.queryDB(info, sql);
		if(info.get("partner_id")!= null){
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
	public boolean validateAdmin(String username, String password,HashMap<String,String> info) {
		// TODO Auto-generated method stub
		boolean validated = false;
		//if(ldap.search(username,password)){
			String sql = "select * FROM system_admin WHERE user_id  = '" + username+"'";
			dbAccess.queryDB(info, sql);
			if(info.get("user_id")!= null){
				validated = true;
			}
		//}
		return validated;
	}

}
