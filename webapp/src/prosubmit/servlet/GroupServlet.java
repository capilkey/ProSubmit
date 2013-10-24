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

import prosubmit.controller.GroupHandler;
import prosubmit.db.DBAccess;



/**
 * Servlet implementation class GroupServlet
 * @author ramone
 */
public class GroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GroupHandler gh = null;
	private final String CONTENT_TYPE = "application/json";
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupServlet() {
        super();
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
		if(gh == null){
			gh = new GroupHandler((DBAccess)request.getSession().getAttribute("dbAccess"));
		}
		if(action != null){
			if(action.equals("updateStudentBio")){
				if(updateStudentBio(request.getParameter("id"),request.getParameter("bio")) == true){
					result.put("success","1");
					result.put("message","Student biography successfully updated");
				}else{
					result.put("message","Unable to update student biography");
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
	private boolean updateStudentBio(String studentId,String bio) {
		// TODO Auto-generated method stub
		return gh.updateStudentBio(studentId, bio);
	}

}
