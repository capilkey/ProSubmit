package prosubmit.controller;

import java.util.ArrayList;
import java.util.HashMap;

import prosubmit.db.DBAccess;

public class GroupHandler {
	DBAccess dbAccess = null;
	
	public GroupHandler(DBAccess dbAccess) throws NullPointerException {
		if(dbAccess != null){
			this.dbAccess = dbAccess;
		}else{
			throw new NullPointerException("Unable to set dbAccess in class GroupHandler upon instantiation. Parameter dbAccess is null");
		}
	}
	
	/**
	 * get all of the groups in the database
	 * @param results
	 * @return
	 */
	public boolean getAllGroups(ArrayList<HashMap<String,String>> results){
		String sql = "SELECT * FROM `group`";
		return dbAccess.queryDB(sql,results);
	}
	
	/**
	 * get a specific group from the database
	 * @param results
	 * @param groupID
	 * @return
	 */
	public boolean getGroup(HashMap<String,Object> group, String groupID){
		boolean success = false;
		String sql = "SELECT * FROM `group` WHERE group_id = " + groupID;
		ArrayList<HashMap<String,String>> students = new ArrayList<HashMap<String,String>>(); 
		success = dbAccess.queryDB(sql,group);
		success = getStudentsByGroup(students, groupID);
		group.put("students",students);
		return success;
	}
	
	/**
	 * get all of the rankings that a group has made
	 * @param results
	 * @param groupID
	 * @return
	 */
	public boolean getGroupRanks(HashMap<String,Object> results, String groupID){
		String sql = "SELECT * FROM project_rank WHERE group_id = " + groupID;
		return dbAccess.queryDB(sql,results);
	}
	
	/**
	 * get all of the students in the database
	 * @param results
	 * @return
	 */
	public boolean getAllStudents(HashMap<String,Object> results){
		String sql = "SELECT * FROM student";
		return dbAccess.queryDB(sql,results);
	}
	
	/**
	 * get a specific student from the database
	 * @param results
	 * @param studentID
	 * @return
	 */
	public boolean getStudent(HashMap<String,Object> results, String studentID){
		String sql = "SELECT * FROM student WHERE student_id = " + studentID;
		return dbAccess.queryDB(sql,results);
	}
	
	/**
	 * get all of the students in a group from the database
	 * @param students
	 * @param groupID
	 * @return
	 */
	public boolean getStudentsByGroup(ArrayList<HashMap<String,String>> students, String groupID){
		String sql = "SELECT * FROM student WHERE group_id = " + groupID;
		return dbAccess.queryDB(sql,students);
	}
	
	/**
	 * create a new group in the database
	 * @param group
	 * @return
	 */
	public boolean createGroup(HashMap<String,String> group){
		String sql = "INSERT INTO group VALUES("+group.get("group_name")+
				","+group.get("group_number")+","+group.get("group_desc")+
				","+group.get("semester_code")+","+group.get("course_id")+")";
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * create a new student in the database
	 * @param student
	 * @return
	 */
	public boolean createStudent(HashMap<String,String> student){
		String sql = "INSERT INTO student VALUES("+student.get("student_bio")+
				","+student.get("student_firstname")+","+student.get("student_lastname")+
				","+student.get("student_email")+","+student.get("student_username")+
				","+student.get("group_id")+")";
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * create a new rank in the database
	 * @param rank
	 * @return
	 */
	public boolean createRank(HashMap<String,String> rank){
		String sql = "INSERT INTO project_rank VALUES("+rank.get("group_id")+
				","+rank.get("project_id")+","+rank.get("projrank_val");
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * remove a group from the database
	 * @param groupID
	 * @return
	 */
	public boolean removeGroup(String groupID){
		String sql = "DELETE FROM group WHERE group_id = " + groupID;
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * remove a student from the database
	 * @param studentID
	 * @return
	 */
	public boolean removeStudent(String studentID){
		String sql = "DELETE FROM student WHERE student_id = " + studentID;
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * remove all of the students belonging to a specific group from the database
	 * @param groupID
	 * @return
	 */
	public boolean removeStudentsByGroup(String groupID){
		String sql = "DELETE FROM student WHERE group_id = " + groupID;
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * remove a ranking from the database
	 * @param groupID
	 * @param projectID
	 * @return
	 */
	public boolean removeRank(String groupID, String projectID){
		String sql = "DELETE FROM project_rank WHERE group_ID = " + groupID +
				" AND project_id = " + projectID;
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * remove all of the rankings that a group has made
	 * @param groupID
	 * @return
	 */
	public boolean removeRankByGroup(String groupID){
		String sql = "DELETE FROM project_rank WHERE group_id = " + groupID;
		return dbAccess.updateDB(sql);
	}
	
	/**
	 * 
	 * @param studentId
	 * @return
	 */
	public boolean updateStudentBio(String studentId,String bio){
		String sql = "UPDATE student SET student_bio = ? WHERE student_id = ?";
		String [] params = {bio,studentId};
		return dbAccess.updateDB(sql,params);
	}
	
}
