<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="prosubmit.controller.GroupManager" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${pageTitle}</title>
<link type="text/css" rel="stylesheet" href="/ProSubmit/resources/lib/bootstrap/css/bootstrap.css"> 
<link type="text/css" rel="stylesheet" href="/ProSubmit/resources/css/header.css"> 
<link type="text/css" rel="stylesheet" href="/ProSubmit/resources/css/footer.css"> 
<link type="text/css" rel="stylesheet" href="/ProSubmit/resources/css/prosubmit.css"> 

<script type="text/javascript" src="/ProSubmit/resources/js/md5.js"></script>
<script type="text/javascript" src="/ProSubmit/resources/js/Base64.js"></script>
<script type="text/javascript" src="/ProSubmit/resources/js/jquery.js"></script>
<script type="text/javascript" src="/ProSubmit/resources/lib/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="/ProSubmit/resources/js/prosubmit.js"></script>
</head>
<body>
<div id="body-mask"></div>
<nav id="nav-bar" class="navbar navbar-default blue-gradient" role="navigation">
<div id="nav-contents-container" class="hbox">
	<!--  <div class="flex1"><img src="/ProSubmit/resources/icons/logos/logo-small.png" alt="LOGO"/></div>-->
	 <div class="collapse navbar-collapse navbar-ex1-collapse flex3">
		<ul class="nav navbar-nav">
				<li><a href="/ProSubmit/">Home</a></li>
			<% if (session.getAttribute("isPartner") != null){ %>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">Projects <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="/ProSubmit/projects/">View All Projects</a></li>
						<li><a href="/ProSubmit/project/create">Create Project</a></li>
					</ul>
				</li>
			<% } else { %>
				<li><a href="/ProSubmit/projects/">Projects</a></li>
			<% } %>
		  	<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">Groups <b class="caret"></b></a>
				<ul class="dropdown-menu">
					<%  
						GroupManager groupManager = new GroupManager();
						ArrayList<HashMap<String,Object>> groups = groupManager.getGroups(false);
						for(int i = 0;i<groups.size();i++){
							HashMap<String,Object> group = groups.get(i);
							%>
							<li><a href="/ProSubmit/group/<%=group.get("group_id")+ "-"+ ((String)group.get("group_name")).replace(" ","_")%>"><%=group.get("group_name")%></a></li>
							<% 
						}
					%>
				</ul>
		    </li>
		  </ul>
	   </div><!-- /.navbar-collapse -->
			
	    <div id="log-in-register-content" class="flex2">
	    	<% //
	    		HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
	    		if(session.getAttribute("isPartner") != null){
	    			out.println("<a href='/ProSubmit/partner/account/'>");
	    			out.println(userInfo.get("username"));
    				out.println("<span class='badge'>Partner</span></a>");
	    		}else if(session.getAttribute("isAdmin") != null){
	    			out.println("<a href='/ProSubmit/admin/'>");
	    			out.println(userInfo.get("user_id"));
    				out.println("<span class='badge'>Admin</span></a>");
	    		}else if(session.getAttribute("isProfessor") != null){
	    			out.println("<a href='/ProSubmit/professor/'>");
	    			out.println(userInfo.get("professor_username"));
    				out.println("<span class='badge'>Teacher</span></a>");
	    		}else if(session.getAttribute("isStudent") != null){
	    			out.println("<a href='/ProSubmit/professor/'>");
	    			out.println(userInfo.get("username"));
    				out.println("<span class='badge'>Student</span></a>");
	    		}else{
	    			out.println(userInfo.get("username"));
	    		}
	   		%> | 
	    	<a href="/ProSubmit/Authenticate?v=logout">Logout</a>
		</div>
			  
	</div>
</nav>