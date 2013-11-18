<%@page import="prosubmit.db.DBAccess"%>
<%@page import="prosubmit.controller.ProjectHandler"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<jsp:include page="/header.jsp"></jsp:include>
<div id="page-content" class="hbox">
		<div class="flex4">
			<h1>Projects</h1>
			<%
				ProjectHandler projectHandler = new ProjectHandler((DBAccess)session.getAttribute("dbAccess"));
				ArrayList<HashMap<String,String>> projects = new ArrayList<HashMap<String,String>>();
				projectHandler.getAllProject(projects);
				//out.println(gson.toJson(projects));
				if(projects.size() > 0){
					%>
						<div class="panel panel-default">
					  <!-- Default panel contents -->
					  <div class="panel-body">
					    <p>
					    	The below lists the various projects which have been submitted to ProSubmit.
					    	
					    </p>
					  </div>
				   	<!-- Table -->
					  <table class="table">
					  <tr>
					  	<th>#</th>
					  	<th>Name</th>
					  	<th>Created</th>
					  	<th>Status</th>
					  	<th>Category</th>
					  	<th>Group</th>
					  </tr>
					<%
					for(int i = 0;i<projects.size();i++){
							HashMap<String,String> project = projects.get(i);
							%>
								<tr>
									<td>#<%=i+1%></td>
									<td><a href="/ProSubmit/project/<%=project.get("project_id") + "-" + ((String)project.get("project_title")).replace(" ","_") %>"><%=project.get("project_title")%></a></td>
									<td><%=project.get("project_createdate")%></td>
									<td><%=project.get("projstatus_name")%></td>
									<td><%=project.get("projcategory_name")%>
									<td><%=(project.get("group_id")==null?"Unassigned":"Assigned")%></td>
								</tr>
							<%
					}
					%>
					</table>
					</div>
					<%
				}else{
					%>
						<div class="alert alert-info">No projects could be retrieved.</div>
					<%
				}
			%>
		</div>
		<div class="flex1">
		</div>
</div>
<jsp:include page="/footer.jsp"></jsp:include>