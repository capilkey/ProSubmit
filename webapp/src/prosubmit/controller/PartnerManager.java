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
					"(company_name,industry,company_url,email,firstname,lastname,jobtitle,telephone,extension,companyaddress,password,authtoken,createdate,expires) " +
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
			
			sql = "INSERT INTO partner (company_name,industry,company_url,email,firstname,lastname,jobtitle,telephone,extension,companyaddress,password,createdate) " +
					"VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			params = new String [] {
					(String)tempPartner.get("company_name"),
					(String)tempPartner.get("industry"),
					(String)tempPartner.get("company_url"),
					(String)tempPartner.get("email"),
					(String)tempPartner.get("firstname"),
					(String)tempPartner.get("lastname"),
					(String)tempPartner.get("jobtitle"),
					(String)tempPartner.get("telephone"),
					(String)tempPartner.get("extension"),
					(String)tempPartner.get("companyaddress"),
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
}
