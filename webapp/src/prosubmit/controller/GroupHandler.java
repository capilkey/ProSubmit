package prosubmit.controller;

import java.util.HashMap;

import prosubmit.db.DBAccess;

public class GroupHandler {
	DBAccess dbAccess = null;
	
	public GroupHandler(DBAccess dbAccess){
		this.dbAccess = dbAccess;
	}
	
	/**
	 * retrieves all of the projects
	 * @param results
	 * @return
	 */
	public boolean getAllProjects(HashMap<String,String> results){
		String sql = "";
		return dbAccess.queryDB(results,sql);
	}
	
	/**
	 * retrieves all of the accepted projects
	 * @param results
	 * @return
	 */
	public boolean getAllAcceptedProjects(HashMap<String,String> results){
		String sql = "";
		return dbAccess.queryDB(results,sql);
	}
	
	/**
	 * get a specific project by projectID
	 * @param results
	 * @param projectID
	 * @return
	 */
	public boolean getProject(HashMap<String,String> results, String projectID) {
		String sql = "";
		return dbAccess.queryDB(results, sql);
	}
	
	/**
	 * get all of the groups in the database
	 * @param results
	 * @return
	 */
	public boolean getAllGroups(HashMap<String,String> results){
		String sql = "";
		return dbAccess.queryDB(results,sql);
	}
	
	/**
	 * get a specific group from the database
	 * @param results
	 * @param groupID
	 * @return
	 */
	public boolean getGroup(HashMap<String,String> results, String groupID){
		String sql = "";
		return dbAccess.queryDB(results,sql);
	}
	
	/**
	 * get all of the rankings that a group has made
	 * @param results
	 * @param groupID
	 * @return
	 */
	public boolean getGroupRanks(HashMap<String,String> results, String groupID){
		String sql = "";
		return dbAccess.queryDB(results,sql);
	}
	
	/**
	 * get all of the students in the database
	 * @param results
	 * @return
	 */
	public boolean getAllStudents(HashMap<String,String> results){
		String sql = "";
		return dbAccess.queryDB(results,sql);
	}
	
	/**
	 * get a specific student from the database
	 * @param results
	 * @param studentID
	 * @return
	 */
	public boolean getStudent(HashMap<String,String> results, String studentID){
		String sql = "";
		return dbAccess.queryDB(results,sql);
	}
	
	/**
	 * get all of the students in a group from the database
	 * @param results
	 * @param groupID
	 * @return
	 */
	public boolean getStudentsByGroup(HashMap<String,String> results, String groupID){
		String sql = "";
		return dbAccess.queryDB(results,sql);
	}
	
	/**
	 * create a new group in the database
	 * @param group
	 * @return
	 */
	public boolean createGroup(HashMap<String,String> group){
		String sql = "";
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * create a new student in the database
	 * @param student
	 * @return
	 */
	public boolean createStudent(HashMap<String,String> student){
		String sql = "";
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * create a new rank in the database
	 * @param rank
	 * @return
	 */
	public boolean createRank(HashMap<String,String> rank){
		String sql = "";
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * remove a group from the database
	 * @param groupID
	 * @return
	 */
	public boolean removeGroup(String groupID){
		String sql = "";
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * remove a student from the database
	 * @param studentID
	 * @return
	 */
	public boolean removeStudent(String studentID){
		String sql = "";
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * remove all of the students belonging to a specific group from the database
	 * @param studentID
	 * @return
	 */
	public boolean removeStudentsByGroup(String groupID){
		String sql = "";
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * remove a ranking from the database
	 * @param rankID
	 * @return
	 */
	public boolean removeRank(String rankID){
		String sql = "";
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * remove all of the rankings that a group has made
	 * @param groupID
	 * @return
	 */
	public boolean removeRankByGroup(String groupID){
		String sql = "";
		return dbAccess.updateDB(sql);
	}
	
}
