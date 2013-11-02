<%
	if(session != null && session.getAttribute("isPartner")!=null && ((String)session.getAttribute("isPartner")).equals("1")){
		response.sendRedirect("/ProSubmit/partner");
	} 
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="prosubmit.db.DBPool" %>
<%@ page import="prosubmit.controller.SystemManager" %>
<%@ page import="prosubmit.controller.PartnerManager" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register Partner</title>
<link rel="icon" href="/ProSubmit/favicon.ico"> 
<link type="text/css" rel="stylesheet" href="/ProSubmit/resources/lib/bootstrap/css/bootstrap.css"> 
<link type="text/css" rel="stylesheet" href="/ProSubmit/resources/css/prosubmit.css"> 

<script type="text/javascript" src="/ProSubmit/resources/js/jquery.js"></script>
<script type="text/javascript" src="/ProSubmit/resources/js/md5.js"></script>
<script type="text/javascript" src="/ProSubmit/resources/js/prosubmit.js"></script>
<script type="text/javascript" src="/ProSubmit/resources/lib/bootstrap/js/bootstrap.js"></script>
</head>
<body>
<div id="body-mask"></div>
	<a id="home-icon-link" href="/ProSubmit/" title="Home">
		<img src="/ProSubmit/resources/icons/home.png" alt="Home"/>
	</a>
	<%if(request.getParameter("registered") == null){ %>
	<form id="partner-register" action="" class="box-shadow">
		<img src="/ProSubmit/resources/icons/logo-lg.png"/>
		<h4>Register to become a partner with us</h4>
		<div id="partner-register-error-message" class="alert alert-danger"></div>
		
		<fieldset>
    <legend>Personalia:</legend>
    
			<label for="firstname">First Name:</label>
			<input id="firstname" type="text" class="form-control" maxLength="25" value="Damion"/>
			
			<label for="lastname">Last Name:</label>
			<input id="lastname" type="text" class="form-control" maxLength="25" value="Marley"/>
			
			<label for="email">Email:</label>
			<input id="email" type="email" class="form-control" value="burrellramone2@gmail.com"/>
							
			<label for="password">Password:</label>
			<input id="password" type="password" class="form-control" maxLength="50" value="prosubmit123"/>
			
			<label for="confirm-password">Confirm Password:</label>
			<input id="confirm-password" type="password" class="form-control" maxLength="50" value="prosubmit123"/>
		</fieldset><br/>

    <fieldset>
    <legend>Company:</legend>
    
			<label for="company">Company:</label>
			<input id="company" type="text" class="form-control" maxLength="100" value="Husla Inc."/>
			
			<label for="url">URL:</label>
      <input id="url" type="text" class="form-control"/>
			
			<label for="industry">Industry:</label>
			<select id="industry" class="form-control">
        <% 
            SystemManager systemManager = new SystemManager((DBPool)session.getServletContext().getAttribute("dbPool"));
        		ArrayList<HashMap<String,String>> industries = new ArrayList<HashMap<String,String>>();
        		systemManager.getIndustries(industries); 
        		for(int i =0;i<industries.size();i++){
        			%>
        				<option value='<%=industries.get(i).get("id")%>'><%=industries.get(i).get("industry")%></option>
        			<%
        		}
        		
        %>
			</select>
			<label for="jobtitle">Job Title:</label>
			<input id="jobtitle" type="text" class="form-control" maxLength="16" value="Manager"/>
			
			<label for="tel">Tel:</label>
			<input id="tel" type="text" class="form-control" maxLength="16" value="1-416-555-3333"/>
			
			<label for="extension">Ext:</label>
	    <input id="extension" type="text" class="form-control" maxLength="10"/>
	    
			<label for="company-addredd">Company Address:</label>
			<input id="company-address" type="text" class="form-control" value="1232 Terrence Ave. Toronto,Ontario M1Y-2K8"/>
		</fieldset>
		<br/>
		<button type="button" class="btn btn-primary" onclick="return proSubmit.registerPartner()">Register Now!</button>
		<br/><br/>
	</form>
	<%}else{
			HashMap<String,String> registrationInfo = (HashMap<String,String>)session.getAttribute("registrationInfo");		
			if(registrationInfo == null || registrationInfo.isEmpty()){	
				response.sendRedirect("/ProSubmit/");
				return;
			}else{
				if(request.getParameter("token") == null){
					//Gson gson = new Gson();
					//System.out.println(gson.toJson(registrationInfo));
					%>
						<div class="registration-info box-shadow">
							<h1><%=registrationInfo.get("firstname") +" "+ registrationInfo.get("lastname")%></h1>
							<p>Your account has successfully been created. Your registration link, which will expire at <strong><%=registrationInfo.get("expires")%></strong>, 
							has been sent to the address <strong><%=registrationInfo.get("email")%></strong>. Please open the link to complete your registration.</p>
						</div>
					<%
				}else{
					PartnerManager partnerManager = new PartnerManager((DBPool)session.getServletContext().getAttribute("dbPool"));
					HashMap<String,Object> info = new HashMap<String,Object>();
					if(partnerManager.completeRegistration(request.getParameter("token"),info)){ 
						session.removeAttribute("registrationInfo");
						response.sendRedirect("/ProSubmit/Authenticate?v=login&ajax=0&username="+ info.get("email") + "&password=" + info.get("password"));
						return;
					}else{
						%>	
						<div class="registration-info box-shadow">
							<p>Unable to complete registration. Link is either invalid or it has expired</p>
						</div>
						<%
					}
				}
			}
	}%>
</body>
</html>