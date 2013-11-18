<%
	if(session == null ||  
		!(
				session.getAttribute("isPartner") == "1" ||  
				session.getAttribute("isAdmin") == "1" || 
				session.getAttribute("isProfessor") == "1"
			) || 
		session.getAttribute("userInfo") == null){
			response.sendRedirect("/ProSubmit/");
			return;
	}

	String pageTitle = "Partner - Home";
%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="prosubmit.db.DBPool" %>
<%@ page import="prosubmit.controller.SystemManager" %>
<%@ page import="prosubmit.controller.PartnerManager" %>
<%@ page import="prosubmit.controller.ProjectManager" %>
<jsp:include page="/header.jsp"></jsp:include>
<%
	String partnerId = request.getParameter("partner_id") != null 
			? request.getParameter("partner_id") 
			: ((HashMap<String,String>)session.getAttribute("userInfo")).get("partner_id");
	PartnerManager partnerManager = new PartnerManager();
	HashMap<String,Object> partner = partnerManager.getPartner(partnerId,true);
%>
<div id="page-content" class="hbox">
	<div class="flex3">
			<div class="panel panel-default box-shadow">
			  <div class="panel-body">
			    <div class="hbox">
			    	<div class="flex1">
							<img src="/ProSubmit/resources/img/no-avatar.png" alt="..." class="img-circle partner-avatar">
						</div>
			    	<div class="flex3" style="padding:10px;">
							<h3 style="display:inline"><%=partner.get("firstname") +" " + partner.get("lastname")%> (<%=partner.get("job_title")%>)</h3> 
							<span><%=partner.get("company_name")%></span><br/>
							<span><%=partner.get("email")%></span><br/>
							<span><%=partner.get("telephone")%> ex:<%=partner.get("extension")%></span><br/>
							<span><%=partner.get("company_address")%></span>
						</div>
					</div>
			  </div>
		</div>
		<hr/>
			<%
				ArrayList<HashMap<String,Object>> projects = (ArrayList<HashMap<String,Object>>) partner.get("projects");
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
					  	<th width="5%">#</th>
					  	<th>Name</th>
					  	<th>Created</th>
					  	<th>Group</th>
					  	<th>Status</th>
					  </tr>
					<%
					for(int i = 0;i<projects.size();i++){
							HashMap<String,Object> project = projects.get(i);
							%>
								<tr>
									<td>#<%=i+1%></td>
									<td><a href="/ProSubmit/project/<%=project.get("project_id") + "-" + ((String)project.get("project_title")).replace(" ","_") %>"/><%=project.get("project_title")%></a></td>
									<td><%=project.get("project_createdate")%></td>
									<td><%=project.get("group_name")%></td>
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
	
	</div>
	
	<div class="flex1 box-shadow">
		<jsp:include page="/right_section.jsp"></jsp:include>
	</div>
</div>
<jsp:include page="/footer.jsp"></jsp:include>