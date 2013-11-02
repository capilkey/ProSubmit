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
<title>Reset Password</title>
<link type="text/css" rel="stylesheet" href="/ProSubmit/resources/lib/bootstrap/css/bootstrap.css"> 
<link type="text/css" rel="stylesheet" href="/ProSubmit/resources/css/prosubmit.css"> 

<script type="text/javascript" src="/ProSubmit/resources/js/md5.js"></script>
<script type="text/javascript" src="/ProSubmit/resources/js/jquery.js"></script>
<script type="text/javascript" src="/ProSubmit/resources/lib/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="/ProSubmit/resources/js/prosubmit.js"></script>
</head>
<body>
	<a id="home-icon-link" href="/ProSubmit/" title="Home">
		<img src="/ProSubmit/resources/icons/home.png" alt="Home"/>
	</a>
	<%if(request.getParameter("token") == null){%>
		<div class="registration-info box-shadow">
				<h3>Give us your email</h3>
				<p>Enter your email address. We will send you a link which you will use to
					reset your password.
				</p>
				<form id="partner-reset-password-email-form">
					<div class="alert alert-info no-show"></div>
					<div class="alert alert-danger no-show"></div>
					<input id="email" type="email" class="form-control" value="burrellramone@gmail.com"/>
				</form>
				<br/>
				<button type="button" class="btn btn-primary" onclick="return proSubmit.resetPassword()">Go!</button>
		</div>
	<%}else{%>
		<div class="registration-info box-shadow">
	  	<h3 style="display:inline">Create New Password</h3>
	  	<br/><br/>
  		<form id="partner-create-password-form">
		  		<div class="alert alert-danger no-show"></div>
		  		<div class="alert alert-info no-show"></div>
		  		
		  		<input id="token" type="hidden" value="<%=request.getParameter("token")%>"/>
		  		
		  		<label for="password">Password:</label>
		  		<input id="password" type="password" class="form-control" size="50"/>
		  		
		  		<label for="confirm_passord">Confirm Password:</label>
		  		<input id="confirm_password" type="password" class="form-control" size="50"/>
		  		<br/>
		  		<button type="button" class="btn btn-primary" onclick="return proSubmit.completePasswordReset()">Change Now!</button>
		  	</form>
	  </div>
	  <%}%>
</body>
</html>