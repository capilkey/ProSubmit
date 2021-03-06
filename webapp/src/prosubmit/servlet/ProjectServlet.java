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
    	projectManager  =  new ProjectManager();
    	
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
			}else if(action.equals("addprojectcategory")){
				if(projectManager.addProjectCategory(request.getParameter("category_name"),request.getParameter("category_description"))){
					result.put("success","1");
					result.put("message","Project category successfully added");
				}else{
					result.put("message","Unable to add project category");
				}
			}else if(action.equals("deleteprojectcategory")){
				if(projectManager.deleteProjectCategory(request.getParameter("projcategory_id"))){
					result.put("success","1");
					result.put("message","Project category successfully added");
				}else{
					result.put("message","Unable to add project category");
				}
			}else if(action.equals("addprojectstatus")){
				if(projectManager.addProjectStatus(request.getParameter("status_name"),request.getParameter("status_description"))){
					result.put("success","1");
					result.put("message","Project status successfully added");
				}else{
					result.put("message","Unable to add project status");
				}
			}else if(action.equals("deleteprojectstatus")){
				if(projectManager.deleteProjectStatus(request.getParameter("projstatus_id"))){
					result.put("success","1");
					result.put("message","Project status successfully added");
				}else{
					result.put("message","Unable to add project status");
				} 
			}else if(action.equals("addproject")){
                System.out.println("Project Create recieved");
                if (projectManager.addProject(request.getParameter("projecttitle"), request.getParameter("projectdescription"), 
                								request.getParameter("projectcategory"), request.getParameter("partner_id"))) {
                        result.put("success","1");
                        result.put("message","Project successfully created");
                } else {
                        result.put("message","Project could not be created");
                }
			}else if(action.equals("updateprojectdesc")){
				System.out.println("Project desc update recieved");
				if (projectManager.updateDescByProjectID(request.getParameter("projectid"),request.getParameter("projectdescription"))) {
					result.put("success","1");
					result.put("message","Project successfully updated");
				} else {
					result.put("message","Project could not be updated");
				}
			}else if(action.equals("updateprojectcatstat")){
				System.out.println("Project cat stat update recieved");
				if (projectManager.updateCatStatByProjectID(request.getParameter("projectid"),request.getParameter("projectcategory"),request.getParameter("projectstatus"))) {
					result.put("success","1");
					result.put("message","Project successfully updated");
				} else {
					result.put("message","Project could not be updated");
				}
			}else{ 
				result.put("message","Unknown action");
			}
		}
		out.println(gson.toJson(result));
	}

}
