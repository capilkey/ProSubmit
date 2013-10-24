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
	 * @param info 
	 * @return
	 */
	public boolean addPartner(StringBuilder partnerId,String firstname, String lastname, String email,
			String password, String company, String telephone, String companyAddress,
			String jobTitle, String industry, HashMap<String, Object> info) {
		// TODO Auto-generated method stub
		boolean partnerRegistered = false;
		String authToken = b64.encodeBase64String(Long.toString(System.currentTimeMillis()).getBytes());
		String sql = "INSERT INTO temppartner VALUES('',?,?,?,?,?,?,?,?,?,current_timestamp,?)";

		String [] params = {company,industry,email,firstname,
							lastname,jobTitle,telephone,companyAddress,
							password,authToken};
		dbAccess.updateDB(sql, params);
		
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
