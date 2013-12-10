/**
 * 
 */
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

import prosubmit.controller.SystemManager;

/**
 * @author Ramone
 *
 */
@SuppressWarnings("serial")
public final class SystemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String CONTENT_TYPE = "application/json";
	private SystemManager systemManager = new SystemManager();
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		response.setHeader("Content-Type",CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		
		HashMap<String,String> result = new HashMap<String,String>();
		HashMap<String,Object> info = new HashMap<String,Object>();
		
		result.put("success","0");
		String action = request.getParameter("v");
		String ajax = request.getParameter("ajax");
		
		if(action != null){
			if(action.equals("deleteadmin")){
				if(systemManager.deleteAdmin(request.getParameter("user_id"))){
					result.put("message","Admin was successfully deleted");
					result.put("success","1");
				}else{
					result.put("message","Unable to remove admin");
				}
			}else{
				result.put("message","Unknown action");
			}
		}
		
		out.println(gson.toJson(result));
	}
}
