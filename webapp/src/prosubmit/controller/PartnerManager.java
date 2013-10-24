/**
 * 
 */
package prosubmit.controller;

import prosubmit.db.DBAccess;

/**
 * @author ramone
 *
 */
@SuppressWarnings("all")
public final class PartnerManager {
	DBAccess dbAccess = null;
	
	/**
	 * 
	 * @param dbAccess
	 */
	public PartnerManager(DBAccess dbAccess){
		this.dbAccess = dbAccess;
	}

	public boolean addPartner(String firstname, String lastname, String email,
			String password, String company, String tel, String companyAddress,
			String jobTitle, String industry) {
		// TODO Auto-generated method stub
		boolean partnerRegistered = false;
		
		
		
		return partnerRegistered;
	}
}
