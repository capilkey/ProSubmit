/**
 * 
 */
package prosubmit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import prosubmit.db.DBAccess;
import prosubmit.db.DBPool;

/**
 * @author ramone
 *
 */
@SuppressWarnings("all")
public final class PartnerManager extends DBAccess{
	private Base64 b64 = new Base64();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private SystemManager systemManager = null;
	private ProjectManager projectManager = null;
	static ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	
	
	public PartnerManager(){
		super((DBPool)context.getBean("dbPool"));
<<<<<<< HEAD
		systemManager = new SystemManager();
		projectManager = new ProjectManager();
=======
		
		projectManager = new ProjectManager();
		systemManager = new SystemManager();
>>>>>>> 1e9362142677f4e486231cad699e70370393530f
	}
	 
	/**
	 * @param partnerId
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param password
	 * @param company
	 * @param telephone
	 * @param companyAddress
	 * @param jobTitle
	 * @param industry 
	 * @param url 
	 * @param extension 
	 * @param info 
	 * @return
	 */
	public boolean addPartner(StringBuilder partnerId,String firstname, String lastname, String email,
			String password, String company_name, String telephone, String company_address,
			String job_title, String industry, String extension, String url, HashMap<String, Object> info) {
		// TODO Auto-generated method stub
		boolean success = false;
		if(!emailExists(email)){
			HashMap<String,String> keys = new HashMap<String,String>();
			String authToken = getToken();
			String sql = "INSERT INTO temppartner " +
					"(company_name,industry,company_url,email,firstname,lastname,job_title,telephone,extension,company_address,password,authtoken,createdate,expires) " +
					"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp,NOW() + INTERVAL 2 HOUR)";
		
			String [] params = new String [] {company_name,industry,url,email,firstname,
								lastname,job_title,telephone,extension,
								company_address,password,authToken};
			success = updateDB(sql,params,keys);
			if(success && !keys.isEmpty()){
				sql = "SELECT *,DATE_FORMAT(expires,'%M %D %Y %r') as expires FROM temppartner WHERE temppartner_id = ?";
				params = new String [] {keys.get("GENERATED_KEY")};
				success = queryDB(sql,params,info);
				
				if(success){
					String to = email;
					String subject = "Registration Completion";
					StringBuilder body = new StringBuilder();
					body.append("We have recieved you registration request.");
					body.append("Please click the link below within the next hour to complete your registration.");
					body.append("Thanks!<br/><br/>");
					body.append("<a href='http://localhost:8080/ProSubmit/Partner/register/?registered=1&token="+info.get("authtoken")+"'>Complete Registration</a>");
					
					if(!systemManager.sendEmail(to,subject,body)){
						sql = "DELETE FROM temppartner WHERE temppartner_id = ?";
						params = new String [] {keys.get("GENERATED_KEY")};
						updateDB(sql,params);
						success = true;
					}
				}
			}
		}else{
			info.put("message","Unable to create partner. Email address already exists");
		}
		return success;
	}
	
	/**
	 * 
	 * @param token
	 * @param info
	 * @return
	 */
	public boolean completeRegistration(String token,HashMap<String,Object> info){
		boolean completed = false;
		HashMap<String,Object> tempPartner = new HashMap<String,Object>();
		HashMap<String,String> keys = new HashMap<String,String>();
		
		String sql = "SELECT * FROM temppartner WHERE authtoken = ?";
		String [] params = {token};
		completed = queryDB(sql, params,tempPartner);
		
		if(completed){
			
			sql = "INSERT INTO partner (company_name,industry,company_url,email,firstname,lastname,job_title,telephone,extension,company_address,password,createdate) " +
					"VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			params = new String [] {
					(String)tempPartner.get("company_name"),
					(String)tempPartner.get("industry"),
					(String)tempPartner.get("company_url"),
					(String)tempPartner.get("email"),
					(String)tempPartner.get("firstname"),
					(String)tempPartner.get("lastname"),
					(String)tempPartner.get("job_title"),
					(String)tempPartner.get("telephone"),
					(String)tempPartner.get("extension"),
					(String)tempPartner.get("company_address"),
					(String)tempPartner.get("password"),
					(String)tempPartner.get("createdate")};
			completed = updateDB(sql, params, keys);
			if(completed){
				sql = "DELETE FROM temppartner WHERE authtoken = ?";
				params = new String [] {token};
				completed = updateDB(sql, params);
				if(completed){
					sql = "SELECT * FROM partner WHERE partner_id = ?";
					params = new String [] {keys.get("GENERATED_KEY")};
					completed = queryDB(sql,params,info);
				}
			}
		}
		return completed;
	}
	
	/**
	 * 
	 * @param partnerId
	 * @param info
	 */
	public HashMap<String,Object> getPartner(String partnerId) {
		// TODO Auto-generated method stub
		HashMap<String,Object> partner = new HashMap<String,Object>();
		String sql = "SELECT *,partner.partner_id AS partner_id, CONCAT(firstname,' ',lastname) as fullname,COUNT(project_id) as projects FROM partner LEFT JOIN project USING(partner_id) WHERE partner.partner_id = " + partnerId;
		queryDB(sql,partner);
		return partner;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> getPartners(){
		ArrayList<HashMap<String,Object>> partners = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT partner_id FROM partner";
		ArrayList<HashMap<String,String>> partnerIds = new ArrayList<HashMap<String,String>>();
		queryDB(sql,partnerIds);
		for(int i =0;i<partnerIds.size();i++){
			partners.add(getPartner(partnerIds.get(i).get("partner_id")));
		}
		return partners;
	}
	
	
	/**
	 * 
	 * @param partnerId
	 * @param partner
	 * @param getProjects
	 * @return
	 */
	public HashMap<String,Object> getPartner(String partnerId,boolean getProjects){
		boolean success = false;
		HashMap<String,Object> partner = getPartner(partnerId);
		if(!partner.isEmpty()){
			ArrayList<HashMap<String,Object>> projects = new ArrayList<HashMap<String,Object>>();
			projectManager.getPartnerProjects(partnerId, projects);
			partner.put("projects",projects);
		}
		return partner;
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	private HashMap<? extends String, ? extends Object> getByEmail(String email) {
		// TODO Auto-generated method stub
		HashMap<String,Object> partner = new HashMap<String,Object>();
		String sql = "SELECT * from partner WHERE email = ? AND active = TRUE";
		String [] params = {email};
		queryDB(sql, params,partner);
		return partner;
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	public boolean emailExists(String email){
		boolean exists = false;
		String sql = "SELECT email FROM temppartner WHERE email = ?";
		String [] params = {email};
		HashMap<String,Object> result = new HashMap<String,Object>();
		queryDB(sql,params,result);
		 
		if(result.get("email") != null){
			exists = true;
		}if(!exists){
			sql = "SELECT email FROM partner WHERE email = ? AND active = TRUE";
			params = new String [] {email};
			queryDB(sql,params,result);
			if(result.get("email") != null){
				exists = true;
			}
		}
		return exists;
	}
	
	   
	 
	/**
	 *  
	 * @param partner_id
	 * @param info 
	 * @return
	 */
	public boolean deleteParter(String partner_id, HashMap<String, Object> info) {
		// TODO Auto-generated method stub
		boolean deleted = false;
		if(partnerExists(partner_id)){
			if(!hasActiveProjects(partner_id)){
				HashMap<String,Object> result = new HashMap<String,Object>();
				String sql = "SELECT partner_id FROM partner WHERE partner_id = ? AND active = FALSE";
				String [] params = {partner_id};
				queryDB(sql, params,result);
				if(result.isEmpty()){
					result.clear();
					sql = "UPDATE partner SET active = FALSE WHERE partner_id = ?";
					params = new String[] {partner_id};
					if(updateDB(sql, params)){
						result.put("message","Partner successfully deleted");
						deleted = true;
					}
				}else{
					info.put("message","Unable to delete partner which is already inactive");
				}
			}else{
				info.put("message","Unable to delete. There is at least one active project.");
			}
			
		}else{
			info.put("message","Unable to delete partner that does not exist");
		}
		return deleted;
	}
	
	/**
	 * 
	 * @param partner_id
	 * @return
	 */
	private boolean hasActiveProjects(String partner_id) {
		// TODO Auto-generated method stub
		boolean  hasProjects = false;
		HashMap<String,Object> project = new HashMap<String,Object>();
		String sql = "SELECT project_id FROM project WHERE partner_id = ? LIMIT 1";
		String []  params = {partner_id};
		queryDB(sql, params,project);
		if(!project.isEmpty()){
			hasProjects = true;
		}
		return hasProjects;
	}

	/**
	 * 
	 * @param partner_id
	 * @param company_name
	 * @param industry
	 * @param company_url
	 * @param email
	 * @param firstname
	 * @param lastname
	 * @param job_title
	 * @param telephone
	 * @param extension
	 * @param company_address
	 * @param info
	 * @return
	 */
	public boolean updateParter(String partner_id, String company_name,
			String industry, String company_url, String email,
			String firstname, String lastname, String job_title,
			String telephone, String extension, String company_address,
			HashMap<String, Object> info) {
		// TODO Auto-generated method stub
		boolean updated = false;
		if(partnerExists(partner_id)){
			String sql = "UPDATE partner SET company_name = ?,industry = ?,company_url = ?,email = ?,firstname = ?, lastname = ?,job_title = ?, telephone = ?,extension = ?,company_address = ? WHERE partner_id = ?";
			String [] params = {company_name,
								industry,
								company_url,
								email,
								firstname,
								lastname,
								job_title,
								telephone,
								extension,
								company_address,
								partner_id};
			if(updateDB(sql, params)){
				info.put("message","Partner successfully updated");
				updated = true;
			}else{
				info.put("message","Unable to update partner");
			}
		}else{
			info.put("message","Partner does not exist");
		}
		return updated;  
	}
	
	/**
	 * 
	 * @param partner_id
	 * @return
	 */
	private boolean partnerExists(String partner_id) {
		// TODO Auto-generated method stub
		boolean exists = false;
		HashMap<String,Object> result = new HashMap<String,Object>();
		String sql = "SELECT partner_id FROM partner WHERE partner_id = ?";
		String [] params = {partner_id};
		queryDB(sql, params, result);
		if(!result.isEmpty()){
			exists = true;
		}
		return exists; 
	}
	
	/**
	 *  
	 * @param partner_id
	 * @param password
	 * @return
	 */
	public boolean isPassword(String partner_id, String password) {
		// TODO Auto-generated method stub
		boolean isPassword = false;
		HashMap<String,Object> partner = new HashMap<String,Object>();
		String sql = "SELECT password from partner WHERE password = ? AND partner_id = ?";
		String [] params = {password,partner_id};
		queryDB(sql, params, partner);
		if(!partner.isEmpty()){
			isPassword = true;
		}
		return isPassword;
	}

	/**
	 * 
	 * @param partner_id
	 * @param current_password
	 * @param password
	 * @param info
	 * @return
	 */
	public boolean updatePassword(String partner_id, String current_password,
		String password, HashMap<String, Object> info) {
		// TODO Auto-generated method stub
		boolean updated = false;
		if(partnerExists(partner_id)){
			if(isPassword(partner_id,current_password)){
				String sql = "UPDATE partner SET password = ? WHERE partner_id = ?";
				String [] params = {password,partner_id};
				if(updateDB(sql,params)){
					updated = true;
				}
			}else{
				info.put("message","Current password does not match the one speccified");
			}
		}else{
			info.put("message","Partner does not exist");
		}
		return updated; 
	}
	
	/**
	 * 
	 * @param email
	 * @param info
	 * @return
	 */
	public boolean resetPassword(String email, HashMap<String, Object> info) {
		// TODO Auto-generated method stub
		boolean success = false;
		if(emailExists(email)){
			String to = email;
			String subject = "Password Reset Request";
			StringBuilder message = new StringBuilder();
			if(createPasswordResetRequest(email)){
				if(systemManager.sendEmail(to,subject,message)){
					info.put("message","Link successfully sent to " + email + ". Please visit this link within 2 hours before it expires.");
					success = true;
				}else{
					info.put("message","Error while sending email to " + email);
				}
			}else{
				info.put("message","Unable to create password reset request");
			}
		}else{
			info.put("message","Email address could not be found in our system");
		}
		return success;
	}
	
	 
	/**
	 * 
	 * @param email
	 * @return
	 */
	private boolean createPasswordResetRequest(String email) {
		// TODO Auto-generated method stub
		boolean created = false;
		String token = getToken();
		String sql = "INSERT INTO password_reset_request (email,token,expires) VALUES(?,?,NOW() + INTERVAL 2 HOUR)";
		String [] params = {email,token};
		if(updateDB(sql, params)){
			created = true;
		}
		return created;
	}
	
	/**
	 * 
	 * @param token
	 * @param password
	 * @param info
	 * @return
	 */
	public boolean completePasswordReset(String token, String password,
			HashMap<String, Object> info) {
		// TODO Auto-generated method stub
		boolean success = false;
		HashMap<String,Object> passwordResetRequest = getPasswordResetRequest(token);
		if(!passwordResetRequest.isEmpty()){
			String email = (String)passwordResetRequest.get("email");
			passwordResetRequest.putAll(getByEmail(email));
			String sql = "UPDATE partner SET password = ? WHERE email = ? AND active = TRUE";
			String [] params = {password,email};
			if(updateDB(sql, params)){
				if(deletePasswordResetRequest(token)){
					info.put("username",email);
					info.put("password",password);
					info.put("message","Your password has successfully been reset");
					success = true;
				}
			}else{
				info.put("message","Unable to change password");
			}
		}else{
			info.put("message","Unable to find password reset request. The token might have expired. Please send another request to reset your password");
		}
		return success;
	}
	
	
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	private boolean deletePasswordResetRequest(String token) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM password_reset_request WHERE token = ?";
		String [] params = {token};
		return updateDB(sql,params);
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	private HashMap<String, Object> getPasswordResetRequest(String token) {
		// TODO Auto-generated method stub
		HashMap<String,Object> passwordResetRequest = new HashMap<String,Object>();
		String sql = "SELECT * from password_reset_request WHERE token = ?";
		String [] params = {token};
		queryDB(sql,params,passwordResetRequest);
		return passwordResetRequest;
	}

	/**
	 * 
	 * @return
	 */
	private String getToken(){
		return b64.encodeBase64String(Long.toString(System.currentTimeMillis()).getBytes());
	} 
	
}
