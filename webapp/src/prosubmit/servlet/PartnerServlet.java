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

import prosubmit.controller.PartnerManager;
import prosubmit.db.DBAccess;

/**
 * Servlet implementation class PartnerServlet
 * @author ramone
 */
@SuppressWarnings("all")
public class PartnerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String CONTENT_TYPE = "application/json";
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private PartnerManager partnerManager = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PartnerServlet() {
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
		HttpSession session = request.getSession(true);
		response.setHeader("Content-Type",CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		
		HashMap<String,String> result = new HashMap<String,String>();
		HashMap<String,Object> info = new HashMap<String,Object>();
		
		result.put("success","0");
		String action = request.getParameter("v");
		
		if(partnerManager == null){
			partnerManager = new PartnerManager((DBAccess)request.getSession().getAttribute("dbAccess"));
		}
		if(action != null){
			if(action.equals("registerPartner")){
				StringBuilder partnerId	= null;
				String firstname = request.getParameter("firstname");
				String lastname = request.getParameter("lastname");
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				String company 	= request.getParameter("company");
				String tel	= request.getParameter("tel");
				String companyAddress = request.getParameter("companyAddress");
				String jobTitle = request.getParameter("jobTitle");
				String industry = request.getParameter("industry");
				 
				if(partnerManager.addPartner(partnerId,firstname,lastname,email,password,company,tel,companyAddress,jobTitle,industry,info)){
					result.put("success","1");
					result.put("message","Partner successfully registered");
					session.setAttribute("registrationInfo",info);
				}else{
					result.put("message","Unable to create partner");
				}
			}else{
				result.put("message","Unknown action");
			}
		}
		out.println(gson.toJson(result));
	}

}
