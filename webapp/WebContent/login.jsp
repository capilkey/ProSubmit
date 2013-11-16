<%
	if(session != null){
		if(session.getAttribute("isStudent") == "1"){
			response.sendRedirect("student/");
		}if(session.getAttribute("isProfessor") == "1"){
			response.sendRedirect("professor/");
		}if(session.getAttribute("isPartner") == "1"){
			response.sendRedirect("partner/");
		}if(session.getAttribute("isAdmin") == "1"){
			response.sendRedirect("admin/");
		}
	}
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link type="text/css" rel="stylesheet" href="/ProSubmit/resources/lib/bootstrap/css/bootstrap.css"> 
<link type="text/css" rel="stylesheet" href="/ProSubmit/resources/css/prosubmit.css"> 
<link type="text/css" rel="stylesheet" href="/ProSubmit/resources/css/login.css">
<script type="text/javascript" src="/ProSubmit/resources/js/jquery.js"></script>
<script type="text/javascript" src="/ProSubmit/resources/js/md5.js"></script>
<script type="text/javascript" src="/ProSubmit/resources/js/prosubmit.js"></script>
<script type="text/javascript" src="/ProSubmit/resources/lib/bootstrap/js/bootstrap.js"></script>
</head>
<body>
		<form id="loginForm" class="">
			<h2>ProSubmit Login</h2>
			<label for="username">Username:</label>
			<input type="text" id="username" placeHolder="Username" class="form-control" value="burrellramone@gmail.com"/>
			<label for="password">Password:</label>
			<input type="password" id="password" placeHolder="Password" class="form-control" value="prosubmit123"/><br/><br/>
			<button type="button" class="btn btn-primary" onclick="return proSubmit.login()">Login</button>
			<br/><br/>
			Do you want to be a partner but not registered? Click <a href="/ProSubmit/partner/register/">here</a>.
			Are you a partner and cannot log in? Click <a href="/ProSubmit/partner/reset_password/">here</a>
		</form>
</body>
</html>