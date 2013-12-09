/**
 * 
 */
package prosubmit.controller;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import prosubmit.config.Config;
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
	 String username = Config.getProperty("mail.username");
	 String password = Config.getProperty("mail.password");
	    if ((username != null) && (username.length() > 0) && (password != null) 
	      && (password.length   () > 0)) {
	        return new PasswordAuthentication(username, password);
	    }
	    return null;
	}
}
