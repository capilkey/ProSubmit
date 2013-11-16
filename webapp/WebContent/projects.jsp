<%
	if(session == null || session.getAttribute("userInfo") == null){
		response.sendRedirect("/ProSubmit/");
		return;
	}
	String pageTitle = "Partner - Home";
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
		SystemManager systemMamager = new SystemManager((DBPool)session.getServletContext().getAttribute("dbPool"));
		
		ArrayList<HashMap<String,String>> projectStatuses = systemMamager.getProjectStatuses();
		ArrayList<HashMap<String,String>> projectCategories = systemMamager.getProjectCategories();
	%>
	<div id="" class="flex4">
		<h1>Search Projects</h1>
		<div class="panel panel-default">
			  <div class="panel-body">
			  	 <table class="table">
					  <tr>
					  	<th>Keyword <em>(Separated by commas)</em></th>
					  	<th>Category</th>
					  	<th>Status</th>
					  </tr>
					  <tr>
					  	<td>
					  		<input id="keywords" type="text" class="form-control"/>
					  	</td>
					  	<td>
					  		<select id="category" class="form-control" multiple>
					  			<%for(int i = 0;i<projectCategories.size();i++){
						  			HashMap<String,String> category = projectCategories.get(i);
						  		%>
						  			<option value="<%=category.get("projcategory_id")%>"><%=category.get("projcategory_name")%></option>
						  		<%}%>
					  		</select>
					  	</td>
					  	<td>
					  		<select id="status" class="form-control" multiple>
					  			<%for(int i = 0;i<projectStatuses.size();i++){
						  			HashMap<String,String> status = projectStatuses.get(i);
						  		%>
						  			<option value="<%=status.get("projstatus_id")%>"><%=status.get("projstatus_name")%></option>
						  		<%}%>
					  		</select>
					  	</td>
					  </tr>
					  <tr>
					  	<th>From Date:</th>
					  	<th>TO Date:</th>
					  	<th></th>
					  </tr>
					  <tr>
					  	<td><input id="from_date" type="date" class="form-control"/></td>
					  	<td><input id="to_date" type="date" class="form-control"/></td>
					  	<td></td>
					  </tr>
					  
					  </table>
					  <button type="button" class="btn btn-primary" onclick="return proSubmit.searchProjects()">Search!</button>
				</div>
		</div>
		<div id="search-results">
			<%
				ArrayList<HashMap<String,String>> projects = projectManager.getProjects();
				//out.println(gson.toJson(projects));
				if(projects.size() > 0){
					%>
						<div class="panel panel-default">
					  <!-- Default panel contents -->
					  <div class="panel-body">
					  <h3 style="display:inline"><%=projects.size()%> Project(s)</h3>
					  </div>
				   	<!-- Table -->
					  <table class="table">
					  <tr>
					  	<th>#</th>
					  	<th>Name</th>
					  	<th>Created</th>
					  	<th>Status</th>
					  </tr>
					<%
					//out.println(gson.toJson(projects));
					for(int i = 0;i<projects.size();i++){
							HashMap<String,String> project = projects.get(i);
							%>
								<tr>
									<td>#<%=i+1%></td>
									<td><a href="/ProSubmit/project/<%=project.get("project_id") + "-" + ((String)project.get("project_title")).replace(" ","_") %>"/><%=project.get("project_title")%></a></td>
									<td><%=project.get("project_createdate")%></td>
									<td><%=project.get("projstatus_name")%></td>
								</tr>
							<%
					}
					%>
				</table>
				</div>
				<%}%>
		</div>
	
	</div>
	
	<div id="" class="flex1">
	</div>

</div>
<jsp:include page="/footer.jsp"></jsp:include>