<%
	String partner_id = null;
	if(session == null || session.getAttribute("isPartner") != "1" || session.getAttribute("userInfo") == null){
		response.sendRedirect("/ProSubmit/");
		return;
	}else{
		partner_id = ((HashMap<String,String>)session.getAttribute("userInfo")).get("partner_id");
	}
	String pageTitle = "Partner - Account";
%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="prosubmit.db.DBAccess" %>
<%@ page import="prosubmit.db.DBConnectionPool" %>
<%@ page import="prosubmit.controller.SystemManager" %>
<%@ page import="prosubmit.controller.PartnerManager" %>
<%@ page import="prosubmit.controller.ProjectManager" %>
<jsp:include page="../header.jsp"></jsp:include>
<div id="page-content" class="hbox">
		<div class="flex4">
			<%
				HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
				Gson gson = new Gson();
				//out.println(gson.toJson(userInfo));
			%>
			<h1>Account Information</h1>
			<div class="panel panel-default">
			  <div class="panel-body">
			    <div>
						<h3><%=userInfo.get("username")%> (<%=userInfo.get("job_title")%>)</h3> 
						<span><%=userInfo.get("company_name")%></span><br/>
						<span><%=userInfo.get("email")%></span><br/>
						<span><%=userInfo.get("telephone")%> ex:<%=userInfo.get("extension")%></span><br/>
						<span><%=userInfo.get("company_address")%></span><br/>
					</div>
			  </div>
			  <div class="panel-footer"><a href="#">Edit</a></div>
			</div>
			<hr/>
			
			
			<%
				ProjectManager projectManager = new ProjectManager((DBAccess)session.getAttribute("dbAccess"));
				ArrayList<HashMap<String,String>> projects = new ArrayList<HashMap<String,String>>();
				projectManager.getPartnerProjects(partner_id, projects);
				//out.println(gson.toJson(projects));
				if(projects.size() > 0){
					%>
						<div class="panel panel-default">
					  <!-- Default panel contents -->
					  <div class="panel-body">
					  <h3 style="display:inline">My Projects (<%=projects.size()%>)</h3>
					    <p>
					    	The below lists the various projects which you have submitted to ProSubmit.
					    	
					    </p>
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
					for(int i = 0;i<projects.size();i++){
							HashMap<String,String> project = projects.get(i);
							%>
								<tr>
									<td>#<%=i+1%></td>
									<td><a href="/ProSubmit/project/<%=project.get("project_id") + "-" + project.get("project_title").replace(" ","_") %>"/><%=project.get("project_title")%></a></td>
									<td><%=project.get("project_createdate")%></td>
									<td><%=project.get("projstatus_name")%></td>
								</tr>
							<%
					}
					%>
					</table>
					</div>
					<%
				}else{
					%>
						<div class="alert alert-info">You currently do not have any projects with us</div>
					<%
				}
			%>
			<hr/>
			
			
			<div class="panel panel-warning">
			  <div class="panel-body">
			  	<h3>Account Cancellation</h3>
			  	<div>
						<div class="alert alert-warning">
							Account cancellation is dependent on the status of your project. You will be notified
							Whether or not cancellation is capable or not.
							Please note, one you have cancelled your account it can no longer be retrieved.
						</div>
						<div id="cancellation-failure-message" class="alert alert-danger hidden"></div>
						<label>Reason</label><em>**Please tell us why you want you want to cancel your account**</em>
						<textarea id="cancellation-reason"></textarea>
						<a id="account-cancel-link" href="#" class="btn btn-danger">Cancel</a> 
					</div>
			  </div>
			</div>

			
			
			
			
		</div>	
		<div class="flex1">
		</div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>