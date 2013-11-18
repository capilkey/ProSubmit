<%@page import="prosubmit.db.DBAccess"%>
<%@page import="prosubmit.controller.ProjectHandler"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<jsp:include page="/header.jsp"></jsp:include>
<div id="page-content" class="hbox">
	<div class="flex4">
		<h1>Project</h1>
		<div class="panel panel-default">
			<div class="panel-body">
				<%
					ProjectHandler projectHandler = new ProjectHandler((DBAccess)session.getAttribute("dbAccess"));
					HashMap<String,Object> project = new HashMap<String,Object>();
					String project_id = request.getParameter("project_id");
					if (project_id != null) {
						projectHandler.getProjectInfoByID(request.getParameter("project_id"), project);
					}
				%>
				<label>Project Title:</label>
				<span><%= project.get("project_title") %></span>
				<br /><br />
				<label>Project Description:</label>
				<span><%= project.get("project_desc") %></span>
				<br /><br />
				<label>Project Category:</label>
				<span><%= project.get("projcategory_name") %></span>
				<br /><br />
				<label>Project Status:</label>
				<span><%= project.get("projstatus_name") %></span>
				<br /><br />
				<label>Created By:</label>
				<span><%= (String)project.get("firstname") + " " + (String)project.get("lastname") %></span>
				<br /><br />
				<label>Created On:</label>
				<span><%= project.get("project_createdate") %></span>
				<br /><br />
				<label>Last Edited On:</label>
				<span><%= (project.get("project_editdate") == null ? "Never" : project.get("project_editdate")) %></span>
				<br />
			</div>
		</div>
	</div>
	<div class="flex1">
	</div>
</div>
<jsp:include page="/footer.jsp"></jsp:include>