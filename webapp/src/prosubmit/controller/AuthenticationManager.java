/**
 * 
 */
package prosubmit.controller;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;

import prosubmit.db.DBAccess;
import prosubmit.db.DBPool;
import prosubmit.ldap.LDAPAuthenticate;

/**
 * @author ramone
 *
 */
@SuppressWarnings("all")
public class AuthenticationManager extends DBAccess {
	private LDAPAuthenticate ldap = new LDAPAuthenticate(); 
	static ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	
	public AuthenticationManager(){
		// TODO Auto-generated constructor stub
		super((DBPool)context.getBean("dbPool"));
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
			queryDB(sql,info);
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
			queryDB(sql,info);
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
		queryDB(sql,info);
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
			queryDB(sql,info);
			if(info.isEmpty() == false){
				validated = true;
			}
		//}
		return validated;
	}

}
