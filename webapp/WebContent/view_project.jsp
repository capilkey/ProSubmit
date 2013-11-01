<%
	if(session == null || session.getAttribute("userInfo") == null){
		response.sendRedirect("/ProSubmit/");
		return;
	}
	String pageTitle = "Professor - Home";
	String projectId = request.getParameter("project_id");
%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="prosubmit.db.DBAccess" %>
<%@ page import="prosubmit.db.DBPool" %>
<%@ page import="prosubmit.controller.SystemManager" %>
<%@ page import="prosubmit.controller.PartnerManager" %>
<%@ page import="prosubmit.controller.ProjectManager" %>
<%
  if(session!=null && session.getAttribute("dbAccess") == null){
    session.setAttribute("dbAccess",new DBAccess((DBPool)session.getServletContext().getAttribute("dbPool")));
  }
%>
<jsp:include page="/header.jsp"></jsp:include>
<div id="page-content" class="hbox">
	<%
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ProjectManager projectManager = new ProjectManager((DBPool)session.getServletContext().getAttribute("dbPool"));
		HashMap<String,Object> project = projectManager.getProject(projectId);
		HashMap<String,Object> group = (HashMap<String,Object>)project.get("group");
		//out.println(gson.toJson(project));
	%>
	<div id="" class="flex4">
		<h1><%=project.get("project_title")%></h1>
		<strong>Date Added</strong> <span><%=project.get("project_createdate")%></span><br/>
		<p><%=project.get("project_desc")%></p>
		
		<%
			if(group != null){
				
			}else{
				%>
					<div class="alert alert-info">There is currently no group assigned to this project.</div>
				<%
			}
		%>
	</div>
	
	<div id="" class="flex1">
	</div>


</div>
<jsp:include page="/footer.jsp"></jsp:include>