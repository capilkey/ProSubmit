<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="/ProSubmit/resources/lib/bootstrap/css/bootstrap.css"> 
<link type="text/css" rel="stylesheet" href="/ProSubmit/resources/css/header.css"> 
<link type="text/css" rel="stylesheet" href="/ProSubmit/resources/css/footer.css"> 
<link type="text/css" rel="stylesheet" href="/ProSubmit/resources/css/prosubmit.css"> 

<script type="text/javascript" src="/ProSubmit/resources/js/jquery.js"></script>
<script type="text/javascript" src="/ProSubmit/resources/lib/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="/ProSubmit/resources/js/prosubmit.js"></script>
</head>
<body>
<nav id="nav-bar" class="navbar navbar-default" role="navigation">
<div id="nav-contents-container" class="hbox">
	<div class="flex1"><img src="/ProSubmit/resources/icons/logo-medium.png" alt="LOGO"/></div>
	 <div class="collapse navbar-collapse navbar-ex1-collapse flex3">
		<ul class="nav navbar-nav">
			<li><a href="/ProSubmit/projects/">Projects</a></li>
		  	<li><a href="/ProSubmit/groups/">Groups</a></li>
		  	<!-- 
		  	<li class="dropdown" style="display: none">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">TEXT <b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href=""></a></li>	
				</ul>
		    </li>-->
		    	
		    	<li><a href="/">Link</a></li>
		    	<li><a href="/">Link</a></li>
		    	<li><a href="/">Link</a></li>
		    	<li><a href="/">Link</a></li>
		  </ul>
	   </div><!-- /.navbar-collapse -->
			
	    <div id="log-in-register-content" class="flex1">
	    	<% 
	    		HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
	    	%>
	    	<% out.println(userInfo.get("username")); %>
	    	|
	    	<a href="../Authenticate?v=logout">Logout</a>
		</div>
			  
	</div>
</nav>