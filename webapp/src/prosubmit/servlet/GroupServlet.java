package prosubmit.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import prosubmit.controller.GroupManager;
import prosubmit.db.DBPool;



/**
 * Servlet implementation class GroupServlet
 * @author ramone
 */
@SuppressWarnings("all")
public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GroupManager groupManager = null;
	private final String CONTENT_TYPE = "application/json";
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupServlet() {
        super();
        groupManager = new GroupManager();
        // TODO Auto-generated constructor stub
    }
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Content-Type",CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		HashMap<String,String> result = new HashMap<String,String>();
		result.put("success","0");
		String action = request.getParameter("v");
		
		if(action != null){
			if(action.equals("updateStudentBio")){
				StringBuilder bio = new StringBuilder(request.getParameter("bio"));
				if(updateStudentBio(request.getParameter("student_id"),bio)){
					result.put("success","1");
					result.put("message","Student biography successfully updated");
					result.put("bio",bio.toString());
				}else{
					result.put("message","Unable to update student biography");
				}
			} else if(action.equals("addgroup")){
				if(groupManager.createGroup(request.getParameter("groupname"),
						request.getParameter("groupnumber"),
						request.getParameter("groupdescription"),
						request.getParameter("groupsemester"),
						request.getParameter("groupcourse"))) {
					result.put("success","1");
					result.put("message","Group successfully created");
				}else{
					result.put("message", "Unable to create the group");
				}
			}else{
				result.put("message","Unknown action");
			}
		}
		out.println(gson.toJson(result));
	}
	

	/**
	 * 
	 * @param bio
	 * @return 
	 */
	private boolean updateStudentBio(String studentId,StringBuilder bio) {
		// TODO Auto-generated method stub
		if(studentId == null){
			return false;
		}else if(bio == null || bio.length() == 0){
			bio.append("No biography available for this student");
			System.out.println("CAME HERE");
		}
		return groupManager.updateStudentBio(studentId, bio.toString());
	}

}
