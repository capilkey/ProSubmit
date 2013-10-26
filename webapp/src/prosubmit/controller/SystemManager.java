/**
 * 
 */
package prosubmit.controller;

import java.util.ArrayList;
import java.util.HashMap;

import prosubmit.db.DBAccess;
import prosubmit.db.DBConnectionPool;

/**
 * @author ramone
 *
 */
@SuppressWarnings("all")
public class SystemManager extends DBAccess{	
	/**
	 * 
	 * @param dbAccess
	 */
	public SystemManager(DBConnectionPool pool){
		super(pool);
	}
	
	/**
	 * Creates a single system_admin record
	 * @param user_id
	 * @return <boolean> true if the operation was successful but false
	 * otherwise
	 */
	public boolean createAdmin(String user_id){
		String sql = "INSERT INTO system_admin VALUES(?)";
		String [] params = {user_id};
		return updateDB(sql,params);
	}
	
	/**
	 * Removes a single system_admin record identified by id
	 * @param user_id
	 * @return <boolean> true if the operation was successful but
	 * false otherwise
	 */
	public boolean removeAdmin(String user_id){
		String sql = "DELETE FROM system_admin WHERE user_id = " + user_id;
		return updateDB(sql);
	}
	
	/**
	 * Creates single professor record
	 * @param professor_username
	 * @param professor_firstname
	 * @param professor_lastname
	 * @return <boolean> true if the operation was successful but
	 * false otherwise
	 */
	public boolean createProfessor(String professor_username,String professor_firstname,String professor_lastname){
		String sql = "INSERT INTO professor VALUES(?,?,?)";
		String [] params = {
			professor_username,
			professor_firstname,
			professor_lastname
		};
		return updateDB(sql, params);
	}
	
	/**
	 * Gets a single professor identified by id
	 * @param professor
	 * @param professor_id
	 * @return <boolean> true if the query was successful but false
	 * otherwise
	 */
	public boolean getProfessor(HashMap<String,Object> professor,String professor_id){
		String sql = "SELECT *" +
				     "FROM professor" + 
				     "WHERE professor_id = " + professor_id;
		return queryDB(sql,professor);
	}
	
	/**
	 * Updates a single professor record identified by the 
	 * id
	 * @param professor_id
	 * @param professor_username
	 * @param professor_firstname
	 * @param professor_lastname
	 * @return <boolean> true if the update was successful
	 * and false otherwise
	 */
	public boolean updateProfessor(String professor_id,String professor_username,String professor_firstname,String professor_lastname){
		String sql = "UPDATE professor " +
					" SET professor_username = ? ," +
					" professor_firstname = ?," +
					" professor_lastname = ?," +
					" WHERE professor_id = ?;";
		String [] params = {
			professor_username,
			professor_firstname,
			professor_lastname,
			professor_id
		};
		return updateDB(sql,params);
	}
	
	/**
	 * Removes a single professor identified by the 
	 * id
	 * @param professor_id
	 * @return <boolean> if the the update ran successfully
	 * and false otherwise
	 */
	public boolean removeProfessor(int professor_id){
		String sql = "DELETE FROM professor" +
					"	WHERE professor_id = " + professor_id;
		return updateDB(sql);
	}
	
	/**
	 * 
	 * @param semesterCode
	 * @param semesterName
	 * @return <boolean> true if the operation was successful in adding
	 * a semester, false otherwise
	 */
	public boolean addSemester(String semesterCode,String semesterName){
		String sql = "INSERT INTO semester VALUES(?,?)";
		String [] params = {semesterCode,semesterName};
		return updateDB(sql,params);
	}
	
	/**
	 * 
	 * @param semesterCode
	 * @return <boolean> true if the operation was successful in removing a
	 * a semester (if the semester exists), false otherwise.
	 */
	public boolean removeSemester(String semesterCode){
		String sql = "DELETE FROM semester WHERE semester_code = ?";
		String [] params = {semesterCode};
		return updateDB(sql,params);
	}
	
	/**
	 * 
	 * @param semesterCode
	 * @param semesterName
	 * @return <boolean> true if the operation was successful in updating
	 * a semester (if the semester exists) , false otherwise.
	 */
	public boolean updateSemester(String semesterCode,String semesterName){
		String sql = "UPDATE semester SET semester_name = ? WHERE semester_code = ?";
		String [] params = {semesterName,semesterCode}; 
		return updateDB(sql,params);
	}
	
	/**
	 * 
	 * @param semesters
	 * @return
	 */
	public boolean getSemesters(ArrayList<HashMap<String,String>> semesters){
		String sql = "SELECT * FROM semester";
		return queryDB(sql,semesters);
	}
	
	/**
	 * 
	 * @param courseId
	 * @param courseName
	 * @return <boolean> true if the operation was successful in adding
	 * a course, false otherwise
	 */
	public boolean addCourse(String courseId,String courseName){
		String sql = "INSERT INTO course VALUES(?,?)";
		String [] params = {courseId,courseName};
		return updateDB(sql,params);
	}
	
	/**
	 * 
	 * @param courseId
	 * @return <boolean> true if the operation was successful in removing
	 * a course (if the course exists), false otherwise
	 */
	public boolean removeCourse(String courseId){
		String sql = "DELETE FROM course WHERE course_id = ?";
		String [] params = {courseId};
		return updateDB(sql,params);
	}
	
	/**
	 * 
	 * @param courseId
	 * @param courseName
	 * @return <boolean> true if the operation was successful in updating a
	 * course, false otherwise
	 */
	public boolean updateCourse(String courseId,String courseName){
		String sql = "UPDATE course SET course_name = ? WHERE course_id = ?";
		String [] params = {courseName,courseId};
		return updateDB(sql,params);
	}
	
	/**
	 * 
	 * @param courses
	 * @return
	 */
	public boolean getCourses(ArrayList<HashMap<String,String>> courses){
		String sql = "SELECT * from course";
		return queryDB(sql,courses);
	}
	
	/**
	 * 
	 * @param industries
	 * @return
	 */
	public boolean getIndustries(ArrayList<HashMap<String,String>> industries){
		String sql = "SELECT * from industry";
		return queryDB(sql,industries);
	}
	
	/**
	 * 
	 * @param categories
	 * @return
	 */
	public ArrayList<HashMap<String,String>>  getProjectCategories(){
		ArrayList<HashMap<String,String>> categories = new ArrayList<HashMap<String,String>> ();
		String sql = "SELECT * FROM project_category";
		queryDB(sql,categories);
		return categories;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<HashMap<String,String>> getProjectStatuses(){
		ArrayList<HashMap<String,String>> statuses = new ArrayList<HashMap<String,String>> ();
		String sql = "SELECT * FROM project_status";
		queryDB(sql,statuses);
		return statuses;
	}
	/**
	 * 
	 * @param to
	 * @param subject
	 * @param message
	 * @return
	 */
	public boolean sendEmail(String to, String subject, StringBuilder message) {
		// TODO Auto-generated method stub
		boolean sent = true; 
		
		return sent;
	}
}
