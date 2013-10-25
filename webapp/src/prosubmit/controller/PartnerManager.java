/**
 * 
 */
package prosubmit.controller;

import java.util.HashMap;

import org.apache.commons.codec.binary.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import prosubmit.db.DBAccess;

/**
 * @author ramone
 *
 */
@SuppressWarnings("all")
public final class PartnerManager {
	private DBAccess dbAccess = null;
	private Base64 b64 = new Base64();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private SystemManager systemManager = null;
	
	/**
	 * 
	 * @param dbAccess
	 */
	public PartnerManager(DBAccess dbAccess){
		this.dbAccess = dbAccess;
		systemManager = new SystemManager(dbAccess);
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
			String password, String company, String telephone, String companyAddress,
			String jobTitle, String industry, String extension, String url, HashMap<String, Object> info) {
		// TODO Auto-generated method stub
		boolean success = false;
		if(!emailExists(email)){
			HashMap<String,String> keys = new HashMap<String,String>();
			String authToken = b64.encodeBase64String(Long.toString(System.currentTimeMillis()).getBytes());
			String sql = "INSERT INTO temppartner " +
					"(company_name,industry,company_url,email,firstname,lastname,job_title,telephone,extension,company_address,password,authtoken,createdate,expires) " +
					"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp,NOW() + INTERVAL 2 HOUR)";
		
			String [] params = new String [] {company,industry,url,email,firstname,
								lastname,jobTitle,telephone,extension,
								companyAddress,password,authToken};
			success = dbAccess.updateDB(sql,params,keys);
			if(success && !keys.isEmpty()){
				sql = "SELECT * FROM temppartner WHERE temppartner_id = ?";
				params = new String [] {keys.get("GENERATED_KEY")};
				success = dbAccess.queryDB(sql,params,info);
				
				if(success){
					String to = email;
					String subject = "Registration Completion";
					String body = "";
					  
					systemManager.sendEmail(to,subject,body);
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
		completed = dbAccess.queryDB(sql, params,tempPartner);
		
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
			completed = dbAccess.updateDB(sql, params, keys);
			if(completed){
				sql = "DELETE FROM temppartner WHERE authtoken = ?";
				params = new String [] {token};
				completed = dbAccess.updateDB(sql, params);
				if(completed){
					sql = "SELECT * FROM partner WHERE partner_id = ?";
					params = new String [] {keys.get("GENERATED_KEY")};
					completed = dbAccess.queryDB(sql,params,info);
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
	public boolean getPartner(String partnerId, HashMap<String,Object> info) {
		// TODO Auto-generated method stub
		String sql = "SELECT * from partner WHERE partner_id = " + partnerId;
		return dbAccess.queryDB(sql,info);
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
		dbAccess.queryDB(sql,params,result);
		 
		if(result.get("email") != null){
			exists = true;
		}if(!exists){
			sql = "SELECT email FROM partner WHERE email = ?";
			params = new String [] {email};
			dbAccess.queryDB(sql,params,result);
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
			HashMap<String,Object> result = new HashMap<String,Object>();
			String sql = "SELECT partner_id FROM partner WHERE partner_id = ? AND active = FALSE";
			String [] params = {partner_id};
			dbAccess.queryDB(sql, params,result);
			if(result.isEmpty()){
				result.clear();
				sql = "UPDATE partner SET active = FALSE WHERE partner_id = ?";
				params = new String[] {partner_id};
				if(dbAccess.queryDB(sql, params,result)){
					result.put("message","Partner successfully deleted");
					deleted = true;
				}
			}else{
				info.put("message","Unable to delete partner which is already inactive");
			}
		}else{
			info.put("message","Unable to delete partner that does not exist");
		}
		return deleted;
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
								company_address};
			if(dbAccess.updateDB(sql, params)){
				info.put("message","Partner successfully updated");
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
		dbAccess.queryDB(sql, params, result);
		if(!result.isEmpty()){
			exists = true;
		}
		return exists;
	}
}
