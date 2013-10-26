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
import java.util.HashMap;

import javax.servlet.ServletContext;

import com.google.gson.Gson;

import prosubmit.controller.*;

/**
 * @author ramone
 *
 */
@SuppressWarnings("all")
public class DBAccess {
	private DBConnectionPool dbPool = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;
    private Connection connection = null;
    private ServletContext context;

	public DBAccess(DBConnectionPool dbPool) throws NullPointerException{
        if(dbPool != null){
        	this.dbPool = dbPool;
		}else{
			throw new NullPointerException("Unable to set dbPool in class DBAccess upon instantiation. Parameter dbPool is null");
		}
    }
	
	/**
	 * 
	 * @return
	 */
	private boolean openConnection(){
		boolean opened = false;
			connection = dbPool.getConnection();
		try {
			if(connection != null && connection.isClosed() == false){
				opened = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opened;
	}
    
	/**
	 * 
	 * @return
	 */
    private boolean closeConnection() {
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
     * @param query
     * @param result
     * @return <boolean> true if the operation was successful
     * but false otherwise
     */
    public boolean queryDB(String query,ArrayList<HashMap<String,String>> result) {
    	boolean success = true;
	    try {
	    	openConnection();
	        statement = connection.prepareStatement(query);
	        resultSet = statement.executeQuery();
	        int colCount = resultSet.getMetaData().getColumnCount();
	        while (resultSet.next()){
	        	HashMap<String,String> row = new HashMap<String,String>();
	            for (int i=1; i<=colCount; i++) {
	                row.put(resultSet.getMetaData().getColumnName(i),resultSet.getString(i));
	            }
	            result.add(row);
	        }
	    }catch (SQLException e) {
	    	System.out.println(e.getMessage());
	    	e.printStackTrace();
	    	success = false;
	    }finally {
	    	closeConnection();
	    }
	    return success;
    }
    
    /**
	 * 
	 * @param sql
	 * @param params
	 * @param result
	 * @return
	 */
	public boolean queryDB(String sql, String[] params,HashMap<String, Object> result) {
		// TODO Auto-generated method stub
		boolean success = true;
		ArrayList<HashMap<String,Object>> results = new ArrayList<HashMap<String,Object>>();
    	success  = queryDB(sql,params,results);
    	if(results.size() > 0){
			result.putAll(results.get(0));
		}
		return success;
	}
    
	/**
	 * 
	 * @param sql
	 * @param params
	 * @param results
	 * @return
	 */
	public boolean queryDB(String sql, String[] params,ArrayList<HashMap<String,Object>> results) {
		boolean success = true;
		try{
			openConnection();
			PreparedStatement prpStmt = connection.prepareStatement(sql);
			for(int i =1;i<=params.length;i++){
				prpStmt.setString(i,params[i-1]);
			}
			resultSet = prpStmt.executeQuery();
			int colCount = resultSet.getMetaData().getColumnCount();
	        while (resultSet.next()){
	        	HashMap<String,Object> row = new HashMap<String,Object>();
	            for (int i=1; i<=colCount; i++) {
	                row.put(resultSet.getMetaData().getColumnName(i),resultSet.getString(i));
	            }
	            results.add(row);
	        }
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		}finally{
			closeConnection();
		}
		return success;
	}
    /**
     * Gets a single record of an entity from the database
     * @param sql
     * @param record
     * @return <boolean> true if the operation was successful but
     * false otherwise
     */
    public boolean queryDB(String sql,HashMap<String, Object> record) {
		// TODO Auto-generated method stub
		boolean success = false;
    	ArrayList<HashMap<String,String>> results = new ArrayList<HashMap<String,String>>();
    	success  = queryDB(sql,results);
    	if(results.size() > 0){
			record.putAll(results.get(0));
		}
    	return success;
	}
    
    /**
     * @param statement
     * @return <boolean> true if the operation was successful but
     * false otherwise
     */ 
    public boolean updateDB(String sql) {
    	boolean success = true;
        try {
        	openConnection();
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }catch (SQLException e) {
        	e.printStackTrace();
        	success = false;
        }finally {
            closeConnection();
        }
        return success;
    }
    
    

    /**
     * 
     * @param sql
     * @param params
     * @param keys
     * @return <boolean> true if the operation was successful 
     * but false otherwise
     */
	public boolean updateDB(String sql, String[] params,HashMap<String,String> keys) {
		// TODO Auto-generated method stub
		boolean success = true;
		try{
			openConnection();
			PreparedStatement prpStmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			for(int i =1;i<=params.length;i++){
				prpStmt.setString(i,params[i-1]);
			}
			prpStmt.executeUpdate();
			
			if(keys != null){
				int i = 1;
				ResultSet rs = prpStmt.getGeneratedKeys();
				while(rs.next()){
					keys.put(rs.getMetaData().getColumnName(i),rs.getString(i));
					i++;
				}
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		}finally{
			closeConnection();
		}
		return success;
	}
	
	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public boolean updateDB(String sql, String[] params) {
		// TODO Auto-generated method stub
		return updateDB(sql,params,null);
	}

	public DBConnectionPool getPool() {
		// TODO Auto-generated method stub
		return this.dbPool;
	}
	
	
}
