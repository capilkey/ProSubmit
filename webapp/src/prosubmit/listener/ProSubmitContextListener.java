package prosubmit.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import prosubmit.controller.DBConnectionPool;
 
/**
 * Application Lifecycle Listener implementation class ProSubmitContextListener
 *
 */
public final class ProSubmitContextListener implements ServletContextListener{
	ServletContext context = null;
	
    /**
     * Default constructor. 
     */ 
    public ProSubmitContextListener() {
        // TODO Auto-generated constructor stub
    }

	

	@Override
	public void contextDestroyed(ServletContextEvent ctxEvnt) {
		// TODO Auto-generated method stub
		context = ctxEvnt.getServletContext();
	}

	@Override
	public void contextInitialized(ServletContextEvent ctxEvnt) {
		// TODO Auto-generated method stub
		context = ctxEvnt.getServletContext();
		context.setAttribute("DBConnectionPool",new DBConnectionPool());
		
	}
	
}
