/**
 * 
 */
package prosubmit.db;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;

import prosubmit.config.Config;

/**
 * @author ramone
 *
 */
@SuppressWarnings("all")
public class DBConnectionPool {

    private DataSource pool = null; 
    
    /**
     * 
     */
    public DBConnectionPool() {
    	pool = new DataSource();
    	pool.setUrl(Config.getProperty("url"));
    	pool.setUsername(Config.getProperty("username"));
    	pool.setPassword(Config.getProperty("password"));
    	
    }
    
    /**
     * 
     * @return
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = pool.getConnection();
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
        return connection;
    }
}
