package prosubmit.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import prosubmit.controller.GroupHandler;
import prosubmit.controller.ProjectManager;
import prosubmit.db.DBPool;

/**
 * Servlet implementation class ProjectServlet
 * @author ramone
 * 
 */
@SuppressWarnings("all")
public final class ProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "application/json";
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private ProjectManager projectManager = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    public void init(ServletConfig config) throws ServletException{
    	projectManager  =  new ProjectManager((DBPool)config.getServletContext().getAttribute("dbPool"));
    	
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
		HttpSession session = request.getSession(true);
		response.setHeader("Content-Type",CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		HashMap<String,String> result = new HashMap<String,String>();
		result.put("success","0");
		String action = request.getParameter("v");

		

		if(action != null){
			if(action.equals("addcomment")){
				String professorId  = ((HashMap<String,String>)session.getAttribute("userInfo")).get("professor_id");	
				if(projectManager.addComment(request.getParameter("project_id"),professorId,request.getParameter("comment"))){
					result.put("success","1");
				}else{
					result.put("message","Unable to add comment to project");
				}
			}else if(action.equals("deletecomment")){
				if(projectManager.deleteComment(request.getParameter("comment_id"))){
					result.put("success","1");
					result.put("message","Comment successfully removed");
				}else{
					result.put("message","Unable to delete comment");
				}
			}else{
				result.put("message","Unknown action");
			}
		}
		out.println(gson.toJson(result));
	}

}
