/**
 * 
 */
package prosubmit.controller;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author ramone
 *
 */
public class SMTPAuthenticator extends Authenticator {

	/**
	 * 
	 */
	public SMTPAuthenticator() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public PasswordAuthentication getPasswordAuthentication() {
	 String username = "raburrell";
	 String password = "*******";
	    if ((username != null) && (username.length() > 0) && (password != null) 
	      && (password.length   () > 0)) {
	        return new PasswordAuthentication(username, password);
	    }
	    return null;
	}
}
