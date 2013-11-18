package prosubmit.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import prosubmit.controller.PartnerManager;
import prosubmit.controller.ProjectHandler;
import prosubmit.db.DBAccess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class ProjectServlet
 */
public class ProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String CONTENT_TYPE = "application/json";
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private ProjectHandler projectHandler = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
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
		String partner_id = request.getParameter("partner_id");
		String projectdesc = request.getParameter("projectdescription");
		String projecttitle = request.getParameter("projecttitle");
		String projectcat = request.getParameter("projectcategory");
		
		result.put("success","0");
		String action = request.getParameter("v");
		String ajax = request.getParameter("ajax");
		
		if(projectHandler == null){
			projectHandler = new ProjectHandler((DBAccess)session.getAttribute("dbAccess"));
		}
		if(action != null){
			if(action.equals("create")){
				System.out.println("Project Create recieved");
				if (projectHandler.addProject(projecttitle, projectdesc, projectcat, partner_id)) {
					result.put("success","1");
					result.put("message","Project successfully created");
				} else {
					result.put("message","Project could not be created");
				}
			} else if (action.equals("update")){
				System.out.println("Project Update recieved");
				///TODO DO UPDATE
			} else{
				result.put("message","Unknown action");
			} 
		}
		
		if(ajax == null || ajax.equals("1")){
			out.println(gson.toJson(result)); 
		}else{
			if(result.get("redirect") != null){
				response.sendRedirect(result.get("redirect"));
			}
			return;
		}
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
