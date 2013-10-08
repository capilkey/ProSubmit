package prosubmit.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import prosubmit.db.*;

/**
 * Application Lifecycle Listener implementation class ProSubmitSessionListener
 *
 */
public class ProSubmitSessionListener implements HttpSessionListener {
	HttpSession session = null;
	
    /**
     * Default constructor. 
     */
    public ProSubmitSessionListener() {
        // TODO Auto-generated constructor stub
    	super();
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent evt) {
    	System.out.println(">>>>>>>>>>>>  SESSION CREATED  <<<<<<<<<<");
        // TODO Auto-generated method stub
    	session = evt.getSession();
    	session.setAttribute("dbAccess",new DBAccess((DBConnectionPool)session.getServletContext().getAttribute("dbPool")));
    }
 
	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent evt) {
        // TODO Auto-generated method stubs
    }
	
}
