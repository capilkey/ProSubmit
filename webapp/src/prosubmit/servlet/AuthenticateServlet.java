package prosubmit.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import prosubmit.controller.AuthenticationManager;
import prosubmit.db.DBAccess;
import prosubmit.db.DBPool;

/**
 * @author ramone
 * Servlet implementation class AuthenticateServlet
 */
@SuppressWarnings("all")
public final class AuthenticateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AuthenticationManager authManager;
    private final String CONTENT_TYPE = "application/json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private ServletConfig config = null;   
    private ServletContext context = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthenticateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		context = config.getServletContext();
	}

	/**
	 * @see Servlet#getServletConfig()
	 */
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return config;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("v");
		if(action != null){
			if(action.equals("login")){
				login(request,response);
			}else if(action.equals("logout")){
				// TODO Auto-generated method stub
				HttpSession session = request.getSession();
				session.invalidate();
				response.sendRedirect("/ProSubmit/login/");
			}
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Content-Type",CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		
		if(authManager == null){
			authManager = new AuthenticationManager(new DBAccess(((DBPool)context.getAttribute("dbPool"))));
		}
		HashMap<String,Object> userInfo = new HashMap<String,Object>();
		HashMap<String,String> result   = new HashMap<String,String>();
		result.put("success","0");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ajax = request.getParameter("ajax");
		
		boolean validated = false;
		
		if(username == null || password == null || username.length() == 0 || password.length() == 0){
			result.put("message","Username and/or password is empty");
		}else{
			validated =  authManager.validateStudent(username,password,userInfo);
			if(validated){
				result.put("redirect","/ProSubmit/student/");
				session.setAttribute("isStudent","1");
			}
			
			if(!validated){
				validated =  authManager.validatePofessor(username,password,userInfo);
				if(validated){
					result.put("redirect","/ProSubmit/professor/");
					session.setAttribute("isProfessor","1");
				}
			}
			
			if(!validated){
				validated =  authManager.validatePartner(username,password,userInfo);
				if(validated){
					result.put("redirect","/ProSubmit/partner/");
					session.setAttribute("isPartner","1");
				}
			}
			
			if(!validated){
				validated =  authManager.validateAdmin(username,password,userInfo);
				if(validated){
					result.put("redirect","/ProSubmit/admin/");
					session.setAttribute("isAdmin","1");
				}
			}
			
			if(validated){
				result.put("success","1");
				session.setAttribute("userInfo",userInfo);
			}else{
				result.put("message","Incorrect username and/or password");
			}
		}

		if(ajax == null || ajax.equals("1")){
			out.println(gson.toJson(result)); 
		}else{
			response.sendRedirect(result.get("redirect"));
			return;
		}
		
		
	}
}
