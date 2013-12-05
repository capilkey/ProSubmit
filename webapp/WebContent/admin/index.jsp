<%
	if(session == null || session.getAttribute("isAdmin") == null || session.getAttribute("userInfo") == null){
		response.sendRedirect("/ProSubmit/");
		return;
	}
	String pageTitle = "Professor - Home";
%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="prosubmit.controller.*" %>

<jsp:include page="/header.jsp"></jsp:include>
<div id="page-content">
	<div class="flex1">
	<div style="width:900px; margin-left:auto;margin-right:auto">
		<%
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			SystemManager systemManager = new SystemManager();
			PartnerManager partnerManager = new PartnerManager(); 
			GroupManager groupManager = new GroupManager();
			ArrayList<HashMap<String,String>> projectCategories = systemManager.getProjectCategories();
			ArrayList<HashMap<String,String>> projectStatuses = systemManager.getProjectStatuses();
			
			ArrayList<HashMap<String,Object>> partners = partnerManager.getPartners();
			ArrayList<HashMap<String,Object>> groups = groupManager.getGroups(false); 
			//out.println(gson.toJson(groups));
		%>   
		<!-- <h1>Groups</h1>-->
		<h1>Groups</h1>
		<%if(groups.size() > 0){%>
			<table class="table">
				<tr>
					<th width="5%">#</th>
					<th width="30%">Name</th>
					<th width="30%">Semester</th>
					<th width="">Project</th>
					<th width="">Options</th>
				</tr>
		<%
			for(int i = 0;i<groups.size();i++){
				HashMap<String,Object> group = groups.get(i);
		%>
			<tr>
				<td><%=i+1%></td>
				<td><%=group.get("group_name")%></td>
				<td><%=group.get("semester_code")%></td>
				<td><%=group.get("project_name")%></td>
				<td><button class="btn btn-danger btn-sm" onclick="return proSubmit.deleteGroup(<%=group.get("group_id")%>)">Delete</button></td>
			</tr>
		<%}%>
		</table>	
		<%}else{%>
			<div class="alert alert-info">There are currently no groups</div>
		<%}%>
		
		<hr/>
		
		<h1>Partners</h1>
		<%if(partners.size() > 0){%>
			<table class="table">
					<tr>
						<th width="5%">#</th>
						<th width="15%">Name</th>
						<th width="20%">Company</th>
						<th width="15%">Projects</th>
					</tr>
		<%
			for(int i =0;i<partners.size();i++){
				HashMap<String,Object> partner = partners.get(i);
		%>
					<tr>
						<td><%=i+1%></td>
						<td><a href='/ProSubmit/partner/<%=partner.get("partner_id")%>/'><%=partner.get("fullname")%></a></td> 
						<td><%=partner.get("company_name")%></td>
						<td><%=partner.get("projects")%></td>
					</tr>
		<%}%>
			</table>
			<hr/>
		<%}%>
		
		
		
		 
		
		
		
		<h1>Project Categories</h1>
		<%
			if(projectCategories.size() > 0){ %>
				<table class="table">
					<tr>
						<th width="5%">#</th>
						<th width="15%">Name</th>
						<th>Description</th>
						<th width="10%"># Projects</th>
						<th width="15%">Options</th>
					</tr>
			<%
				for(int i = 0;i<projectCategories.size();i++){
					HashMap<String,String> category = projectCategories.get(i);
		%>
					<tr>
						<th><%=i+1%></th>
						<td><%=category.get("projcategory_name")%></td>
						<td><%=category.get("projcategory_desc")%></td>
						<td><%=category.get("project_count")%></td>
						<td><button class="btn btn-primary btn-sm">Edit</button> 
								<button class="btn btn-danger btn-sm" onclick="return proSubmit.deleteProjectCategory(<%=category.get("projcategory_id")%>)" <%if(category.get("project_count").equals("0") == false){%>disabled title="Unable to delete since there is at least one project within this category"<%}%>>Delete</button></td>
					</tr>
		<%}%>
			<tr id="new-projcat-row" style="display:none">
				<td></td>
				<td><input type="text" id="new-projcat-name" class="form-control"></td>
				<td><textarea  id="new-projcat-desc" class="form-control"></textarea></td>
				<td><button class="btn btn-success btn-sm" onclick="return proSubmit.addProjectCategory();">Done</button> </td>
			</tr>
			<tr>
				<td><button id="new-projcat-btn" class="btn btn-success btn-sm">New</button> </td>
			</tr>
			</table>
			<hr/>
		<%}%>
		
		<h1>Project Statuses</h1>
		<%
			if(projectStatuses.size() > 0){ %>
				<table class="table">
					<tr>
						<th width="5%">#</th>
						<th width="15%">Name</th>
						<th>Description</th>
						<th width="10%"># Projects</th>
						<th width="15%">Options</th>
					</tr>
			<%
				for(int i = 0;i<projectStatuses.size();i++){
					HashMap<String,String> status = projectStatuses.get(i);
		%>
					<tr>
						<th><%=i+1%></th>
						<td><%=status.get("projstatus_name")%></td>
						<td><%=status.get("projstatus_desc")%></td>
						<td><%=status.get("project_count")%></td>
						<td><button class="btn btn-primary btn-sm">Edit</button> 
								<button class="btn btn-danger btn-sm" onclick="return proSubmit.deleteProjectStatus(<%=status.get("projstatus_id")%>);" <%if(status.get("project_count").equals("0") == false){%>disabled title="Unable to delete since there is at least one project with this status"<%}%>>Delete</button></td>
					</tr>
		<%}%>
			<tr id="new-projstatus-row" style="display:none">
				<td></td>
				<td><input type="text" id="new-projstatus-name" class="form-control"></td>
				<td><textarea  id="new-projstatus-desc" class="form-control"></textarea></td>
				<td><button class="btn btn-success btn-sm" onclick="return proSubmit.addProjectStatus();">Done</button> </td>
			</tr>
			<tr>
				<td><button id="new-projstatus-btn" class="btn btn-success btn-sm">New</button> </td>
			</tr>
			</table>
			<hr/>
		<%}%>
	</div>	
	</div>
 	
</div>
<jsp:include page="/footer.jsp"></jsp:include>