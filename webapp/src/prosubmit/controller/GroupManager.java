package prosubmit.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 
 */

import prosubmit.db.DBAccess;
import prosubmit.db.DBPool;

/**
 * @author ramone
 *
 */
public class GroupManager extends DBAccess{
	static ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
	
	public GroupManager(){
		super((DBPool)context.getBean("dbPool"));
	}


	 /**
	  * 
	  * @return
	  */
	public ArrayList<HashMap<String,Object>> getGroups(boolean withStudents){
		ArrayList<HashMap<String,String>> groupIds = new ArrayList<HashMap<String,String>>();
		ArrayList<HashMap<String,Object>> groups = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT group_id FROM `group`";
		queryDB(sql,groupIds);
		for(int i = 0;i<groupIds.size();i++){
			groups.add(getGroup((String)groupIds.get(i).get("group_id")));
		}
		return groups;
	}
	
	/**
	 * get a specific group from the database
	 * @param results
	 * @param groupID
	 * @return
	 */
	public HashMap<String,Object> getGroup(String groupID){
		HashMap<String,Object> group = new HashMap<String,Object>();
		String sql = "SELECT *,IF(project_title,project_title,'N/A') as project_name,group.group_id as group_id FROM `group` LEFT JOIN project ON project.group_id = group.group_id WHERE group.group_id = " + groupID;
		ArrayList<HashMap<String,String>> students = new ArrayList<HashMap<String,String>>(); 
		queryDB(sql,group);
		getStudentsByGroup(students, groupID);
		group.put("students",students);
		return group;
	}
	
	/**
	 * get all of the rankings that a group has made
	 * @param results
	 * @param groupID
	 * @return
	 */
	public boolean getGroupRanks(HashMap<String,Object> results, String groupID){
		String sql = "SELECT * FROM project_rank WHERE group_id = " + groupID;
		return queryDB(sql,results);
	}
	
	/**
	 * get all of the students in the database
	 * @param results
	 * @return
	 */
	public boolean getAllStudents(HashMap<String,Object> results){
		String sql = "SELECT * FROM student";
		return queryDB(sql,results);
	}
	
	/**
	 * get a specific student from the database
	 * @param results
	 * @param studentID
	 * @return
	 */
	public boolean getStudent(HashMap<String,Object> results, String studentID){
		String sql = "SELECT * FROM student WHERE student_id = " + studentID;
		return queryDB(sql,results);
	}
	
	/**
	 * get all of the students in a group from the database
	 * @param students
	 * @param groupID
	 * @return
	 */
	public boolean getStudentsByGroup(ArrayList<HashMap<String,String>> students, String groupID){
		String sql = "SELECT * FROM student WHERE group_id = " + groupID;
		return queryDB(sql,students);
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
		return updateDB(sql);
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
		return updateDB(sql);
	}
	
	/**
	 * create a new rank in the database
	 * @param rank
	 * @return
	 */
	public boolean createRank(HashMap<String,String> rank){
		String sql = "INSERT INTO project_rank VALUES("+rank.get("group_id")+
				","+rank.get("project_id")+","+rank.get("projrank_val");
		return updateDB(sql);
	}
	
	/**
	 * remove a group from the database
	 * @param groupID
	 * @return
	 */
	public boolean removeGroup(String groupID){
		String sql = "DELETE FROM `group` WHERE group_id = " + groupID;
		return updateDB(sql);
	}
	
	/**
	 * remove a student from the database
	 * @param studentID
	 * @return
	 */
	public boolean removeStudent(String studentID){
		String sql = "DELETE FROM student WHERE student_id = " + studentID;
		return updateDB(sql);
	}
	
	/**
	 * remove all of the students belonging to a specific group from the database
	 * @param groupID
	 * @return
	 */
	public boolean removeStudentsByGroup(String groupID){
		String sql = "DELETE FROM student WHERE group_id = " + groupID;
		return updateDB(sql);
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
		return updateDB(sql);
	}
	
	/**
	 * remove all of the rankings that a group has made
	 * @param groupID
	 * @return
	 */
	public boolean removeRankByGroup(String groupID){
		String sql = "DELETE FROM project_rank WHERE group_id = " + groupID;
		return updateDB(sql);
	}
	
	/**
	 * 
	 * @param studentId
	 * @return
	 */
	public boolean updateStudentBio(String studentId,String bio){
		String sql = "UPDATE student SET student_bio = ? WHERE student_id = ?";
		String [] params = {bio,studentId};
		return updateDB(sql,params);
	}
}
