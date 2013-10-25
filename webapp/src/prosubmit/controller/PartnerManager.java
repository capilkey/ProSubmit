/**
 * 
 */
package prosubmit.controller;

import java.util.HashMap;

import org.apache.commons.codec.binary.*;

import prosubmit.db.DBAccess;

/**
 * @author ramone
 *
 */
@SuppressWarnings("all")
public final class PartnerManager {
	DBAccess dbAccess = null;
	Base64 b64 = new Base64();
	
	/**
	 * 
	 * @param dbAccess
	 */
	public PartnerManager(DBAccess dbAccess){
		this.dbAccess = dbAccess;
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
		boolean partnerRegistered = false;
		HashMap<String,String> keys = new HashMap<String,String>();
		String authToken = b64.encodeBase64String(Long.toString(System.currentTimeMillis()).getBytes());
		String sql = "INSERT INTO temppartner " +
				"(company_name,industry,company_url,email,firstname,lastname,jobtitle,telephone,extension,companyaddress,hashpassword,authtoken,createdate,expires) " +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,current_timestamp,NOW() + INTERVAL 2 HOUR)";
	
		String [] params = new String [] {company,industry,url,email,firstname,
							lastname,jobTitle,telephone,extension,
							companyAddress,password,authToken};
		partnerRegistered = dbAccess.updateDB(sql,params,keys);
		
		sql = "SELECT * FROM temppartner WHERE temppartner_id = ?";
		params = new String [] {keys.get("temppartner_id")};
		partnerRegistered = dbAccess.queryDB(info,sql);
		return partnerRegistered;
	}
	
	/**
	 * 
	 * @param partnerId
	 * @param info
	 */
	public boolean getPartner(String partnerId, HashMap<String,Object> info) {
		// TODO Auto-generated method stub
		String sql = "SELECT * from partner WHERE partner_id = " + partnerId;
		return dbAccess.queryDB(info, sql);
	}
}
