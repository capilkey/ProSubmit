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
import prosubmit.db.DBPool;

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
		String partner_id = request.getParameter("partner_id");
		
		result.put("success","0");
		String action = request.getParameter("v");
		String ajax = request.getParameter("ajax");
		
		if(partnerManager == null){
			partnerManager = new PartnerManager((DBPool)request.getSession(true).getServletContext().getAttribute("dbPool"));
		} 
		if(action != null){
			if(action.equals("register")){
				StringBuilder partnerId	= null;
				String firstname = request.getParameter("firstname");
				String lastname = request.getParameter("lastname");
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				String company 	= request.getParameter("company_name");
				String tel	= request.getParameter("telephone");
				String companyAddress = request.getParameter("company_address");
				String jobTitle = request.getParameter("job_title");
				String industry = request.getParameter("industry");
				String extension = request.getParameter("extension");
				String url = request.getParameter("company_url");
				
				if(partnerManager.addPartner(partnerId,firstname,lastname,email,password,company,tel,companyAddress,jobTitle,industry,extension,url,info)){
					result.put("success","1");
					result.put("message","Partner successfully registered");
					session.setAttribute("registrationInfo",info);
				}else{
					result.put("message",(String)info.get("message"));
				}
			}else if(action.equals("delete") || action.equals("deletePartner")){
				partner_id = partner_id == null ? ((HashMap<String,String>)session.getAttribute("userInfo")).get("partner_id") : request.getParameter("partner_id");
				if(partnerManager.deleteParter(partner_id,info)){
					result.put("success","1");
				}
				result.put("message",(String)info.get("message"));
			}else if(action.equals("update") || action.equals("updatePartner")){
				if(partnerManager.updateParter(partner_id,request.getParameter("company_name"),
						request.getParameter("industry"),request.getParameter("company_url"),
						request.getParameter("email"),request.getParameter("firstname"),
						request.getParameter("lastname"),request.getParameter("job_title"),
						request.getParameter("telephone"),request.getParameter("extension"),
						request.getParameter("company_address"),info)){
					if(session.getAttribute("isPartner") != null){
						HashMap<String,Object> partnerInfo = new HashMap<String,Object>(); 
						partnerManager.getPartner(partner_id,partnerInfo);
						session.setAttribute("userInfo",partnerInfo);
					}
					result.put("success","1");
				}
				result.put("message",(String)info.get("message"));
			}else if(action.equals("is_password")){
				String password = request.getParameter("password");
				if(partnerManager.isPassword(partner_id,password)){
					result.put("is_password","1");
				}else{
					result.put("is_password","0");
				}
				result.put("success","1");
			}else if(action.equals("update_password")){
				String current_password = request.getParameter("current_password");
				String password = request.getParameter("password");
				if(partnerManager.updatePassword(partner_id,current_password,password,info)){
					result.put("message","Password successfully updated");
					result.put("success","1");
				}else{
					result.put("message",(String)info.get("message"));
				}
			}else if(action.equals("send_reset_password_link")){
				if(partnerManager.resetPassword(request.getParameter("email"),info)){
					result.put("success","1");
				} 
				result.put("message",(String)info.get("message"));
			}else if(action.equals("complete_password_reset")){
				String token = request.getParameter("token");
				String password =  request.getParameter("password");
				if(partnerManager.completePasswordReset(token,password,info)){
					result.put("username",(String)info.get("username"));
					result.put("password",(String)info.get("password"));
					result.put("success","1"); 
				}
				result.put("message",(String)info.get("message"));
			}
			else{
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

}
