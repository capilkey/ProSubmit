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
<jsp:include page="/header.jsp"></jsp:include>
<div id="page-content" class="hbox">
	<%
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ProjectManager projectManager = new ProjectManager((DBPool)session.getServletContext().getAttribute("dbPool"));
		HashMap<String,Object> project = projectManager.getProject(projectId);
		HashMap<String,Object> group = (HashMap<String,Object>)project.get("group");
		ArrayList<HashMap<String,Object>> comments = (ArrayList<HashMap<String,Object>>)project.get("comments");
	%>
	<div id="" class="flex3">
	<%
	//out.println(gson.toJson(project));
	%>
		<h1><%=project.get("project_title")%></h1>
		<strong>Date Added: </strong> <span><%=project.get("project_createdate")%></span><br/>
		<p><%=project.get("project_desc")%></p>
		
		<%
			if(group != null){
				
			}else{
				%>
					<div class="alert alert-info">There is currently no group assigned to this project.</div>
				<%
			}
		%>
		
		<div id="project_comments">
			<h3><%=comments.size()%> Comment(s)</h3>
		<%for(int i =0;i<comments.size();i++){
			HashMap<String,Object> comment = (HashMap<String,Object>)comments.get(i);
		%>
			<div class="comment-container box-shadow round-corners">
				<div class="gray-gradient hbox">
						<div class="flex1"><strong>By: </strong><span><%=comment.get("professor_name")%></span></div>
						<div class="flex1"><strong>Added: </strong><span><%=comment.get("projcom_date")%></span></div>
				</div>
				<p class="comment"><%=comment.get("projcom_text")%></p>
				<%
					if(session.getAttribute("isAdmin") != null){
						%>
							<div><a href="#" title="Delete this comment" onclick="return proSubmit.deleteComment(<%=comment.get("projcom_id")%>)">Delete</a></div>
						<%
					}
				%>
			</div>
			
		<%}%>
		</div>
		
		
		<%if(session.getAttribute("isProfessor") != null){ %>
			<br/>
			<textarea id="comment"></textarea>
			<input id="project-id" type="hidden" value="<%=projectId%>"/>
			<button type="button" class="btn btn-primary" onclick="return proSubmit.addProjectComment()">Add Comment</button>
		<%}%>
	</div>
	
	<div id="" class="flex1">
		<jsp:include page="/right_section.jsp"></jsp:include>
	</div>


</div>
<jsp:include page="/footer.jsp"></jsp:include>