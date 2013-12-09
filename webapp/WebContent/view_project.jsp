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
		ProjectManager projectManager = new ProjectManager(); 
		HashMap<String,Object> project = projectManager.getProject(projectId);
		HashMap<String,Object> group = (HashMap<String,Object>)project.get("group");
		ArrayList<HashMap<String,Object>> comments = (ArrayList<HashMap<String,Object>>)project.get("comments");
	%>
	<div id="" class="flex4">
	<%
	//out.println(gson.toJson(project));
	%>
		<h1><%=project.get("project_title")%></h1>
		<div class="panel panel-default">
			<div class="panel-body">
				
					<strong>Project Category: </strong><span><%= project.get("projcategory_name") %></span><br />
					<strong>Project Status: </strong><span><%= project.get("projstatus_name") %></span><br />
					<strong>Created By: </strong><span><%= (String)project.get("firstname") + " " + (String)project.get("lastname") %></span><br />
					<strong>Created: </strong> <span><%=project.get("project_createdate")%></span><br/>
					<strong>Last Edited: </strong> <span><%=(project.get("project_editdate") == null ? "N/A" : project.get("project_editdate"))%></span><br/>
					<br/>
					<p><%=project.get("project_desc")%></p>
					
					<%
						if(group != null){
							
						}else{
							%>
								<div class="alert alert-info">There is currently no group assigned to this project.</div>
							<%
						}
					%>
				<% if (session.getAttribute("isProfessor") != null || 
						(session.getAttribute("isPartner")!=null && 
							((HashMap<String,Object>)session.getAttribute("userInfo")).get("partner_id").equals(project.get("partner_id")))) { %>
				<div class="panel-footer">
				  	<a id="edit-project-link" href="#">Edit</a>
				  	
				  	<form id="project-edit-info-form" action="">
						<div id="project-error-message" class="alert alert-danger"></div>
					
						<fieldset>
				    		<legend>Update:</legend>
				    		<input type="hidden" id="projectid" value="<%= projectId %>"/>
				    		
				    	<% if (session.getAttribute("isProfessor") != null) {
				    		ArrayList<HashMap<String,String>> projectcats = projectManager.getAllCategoryInfo();
				    		ArrayList<HashMap<String,String>> projectstatuses = projectManager.getAllStatusInfo();
				    		%>
				    		<label for="projcat">Project Category:</label>
							<select id="projcat" class="form-control">
							<%
								for (int i=0; i < projectcats.size(); ++i) {
								%>
								<option value="<%= projectcats.get(i).get("projcategory_id") %>" <%= (projectcats.get(i).get("projcategory_id").equals(project.get("projcategory_id")) ? "selected='selected'" : "") %>><%= projectcats.get(i).get("projcategory_name") %></option>
								<%
								}
							%>
							</select>
							
							<label for="projstat">Project Status:</label>
							<select id="projstat" class="form-control">
							<%
								for (int i=0; i < projectstatuses.size(); ++i) { %>
								<option value="<%= projectstatuses.get(i).get("projstatus_id") %>" <%= (projectstatuses.get(i).get("projstatus_id").equals(project.get("projstatus_id")) ? "selected='selected'" : "") %>><%= projectstatuses.get(i).get("projstatus_name") %></option>
								<%
								}
							%>
							</select>
						</fieldset><br/>
				
						<br/>
						<button type="button" class="btn btn-primary" onclick="return proSubmit.updateProjectCatStat()">Update Info!</button>
				    	<% } else { %>
				    		<label for="projdesc">Project Description:</label>
							<textarea id="projdesc" class="form-control" rows="25"><%=project.get("project_desc")%></textarea>
						</fieldset><br/>
				
						<br/>
						<button type="button" class="btn btn-primary" onclick="return proSubmit.updateProjectDesc()">Update Info!</button>
						<% } %>
						<br/><br/>
					</form>
				</div>
				<% } %>
			</div>
		</div>
		
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