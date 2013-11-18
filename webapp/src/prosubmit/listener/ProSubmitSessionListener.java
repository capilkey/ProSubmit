package prosubmit.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import prosubmit.db.DBAccess;
import prosubmit.db.DBPool;

/**
 * Application Lifecycle Listener implementation class ProSubmitSessionListener
 *
 */
public class ProSubmitSessionListener implements  HttpSessionListener {

    /**
     * Default constructor. 
     */
    public ProSubmitSessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent evt) {
        // TODO Auto-generated method stub
    	DBAccess dbAccess = new DBAccess((DBPool)evt.getSession().getServletContext().getAttribute("dbPool"));
    	evt.getSession().setAttribute("dbAccess",dbAccess);
    	System.out.println("SESSION CREATED");
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }
}
