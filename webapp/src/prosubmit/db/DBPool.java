/**
 * 
 */
package prosubmit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import com.mysql.jdbc.Driver;

import prosubmit.config.Config;

/**
 * @author ramone
 *
 */
@SuppressWarnings("all")
public class DBPool {

    private DataSource pool = null; 
    
    /**
     * 
     */
    public DBPool() {
    	pool = new DataSource();
    	pool.setDriverClassName("com.mysql.jdbc.Driver");
    	pool.setUrl(Config.getProperty("url"));
    	pool.setUsername(Config.getProperty("username"));
    	pool.setPassword(Config.getProperty("password"));
    	pool.setMaxActive(20);
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
        	System.out.println("Unable to make connection to database. " + e.getMessage() + " " + e.getErrorCode());
        	e.printStackTrace();
        }
        return connection;
    }
}
