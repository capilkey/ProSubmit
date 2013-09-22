/**
 * 
 */
package prosubmit.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletContext;

import prosubmit.controller.*;

/**
 * @author ramone
 *
 */
@SuppressWarnings("all")
public class DBAccess {
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;
    private Connection connection = null;
    private ServletContext context;

	public DBAccess(ServletContext ctx) {
        context = ctx;
    }
    
    
    public boolean closeConnection() {
        boolean success = false;
        try {
            if (statement  != null) { statement.close(); }
            if (resultSet  != null) { resultSet.close(); }
            if (connection != null) { connection.close();}
        }catch (SQLException e) {
        	System.out.println(e.getMessage());
        }finally{
        	success = true;
        }
        return success;
    }



    /**
     * @param result
     * @param query
     * @return
     */
    public boolean queryDB(ArrayList<ArrayList<String>> result, String query) {
    	boolean success = false;
	    try {
	    	connection= ((DBConnectionPool)context.getAttribute("DBConnectionPool")).getConnection();
	        statement = connection.prepareStatement(query);
	        resultSet = statement.executeQuery();
	        int colCount = resultSet.getMetaData().getColumnCount();
	        while (resultSet.next()) {
	            ArrayList<String> row = new ArrayList<String>();
	            for (int i=1; i<=colCount; i++) {
	                row.add(resultSet.getString(i));
	            }
	            result.add(row);
	        }
	    }
	    catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    }
	    finally {
	       //IT SALL GOOD 
	    	closeConnection();
	    	success = true;
	    }
	    return success;
    }
    
    /**
     * @param statement
     * @return
     */ 
    public boolean updateDB(String sql) {
    	boolean success = false;
        try {
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
        finally {
            success = true;
        }
        return success;
    }
}
