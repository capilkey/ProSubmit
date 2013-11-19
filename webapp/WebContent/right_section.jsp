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
	ProjectManager projectManager = new ProjectManager();
	ArrayList<HashMap<String,String>> activeProjects = projectManager.getActiveProjects(10); 
	ArrayList<HashMap<String,String>> mostRecentProjects = projectManager.getMostRecentProjects(10);
	ArrayList<HashMap<String,String>> completedProjects = projectManager.getCompletedProjects(10);
	//out.println(gson.toJson(mostRecentProjects));
%>
<div>  
	<h4  class="underlined">Active Projects</h4>
	<%
		if(activeProjects.size() > 0){
			for(int i=0;i<activeProjects.size();i++){
				HashMap<String,String> project = activeProjects.get(i); 
				%>
					<div>
						<h5><a href="/ProSubmit/project/<%=project.get("project_id") + "-" + project.get("project_title")%>"><%=project.get("project_title")%></a></h5>
						<h6><%=project.get("project_createdate")%></h6>
						<p><%=project.get("project_desc").substring(0,(project.get("project_desc")).length() >= 100 ? 100 : project.get("project_desc").length())%></p>
					</div>
					<hr/>
				<%			
				}
		}else{
			%>
				<em>There currently are no active projects</em>
			<%
		}
	%>
</div>

<div>
	<h4 class="underlined">Most Recent Projects</h4>
		<%
		if(mostRecentProjects.size() > 0){
			for(int i=0;i<mostRecentProjects.size();i++){
				HashMap<String,String> _project = mostRecentProjects.get(i); 
			%>
				<div>
					<h5><a href="/ProSubmit/project/<%=_project.get("project_id") + "-" + _project.get("project_title")%>"><%=_project.get("project_title")%></a></h5>
					<h6><%=_project.get("project_createdate")%></h6>
					<p><%=_project.get("project_desc").substring(0,(_project.get("project_desc")).length() >= 100 ? 100 : _project.get("project_desc").length())%></p>
				</div>
				<hr/>
			<%
			}
		}else{
			%>
			<em>There currently are no recent projects</em>
			<%
		}
	%>
</div>

<div>
	<h4  class="underlined">Completed Projects</h4>
		<%
		if(completedProjects.size() > 0){
			for(int i=0;i<completedProjects.size();i++){
			%>
				
			<%
			}
		}else{
			%>
				<em>There currently are no completed projects</em>
			<%
		}
	%>
</div>