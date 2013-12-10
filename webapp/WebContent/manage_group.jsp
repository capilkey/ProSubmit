<%
	if(session == null || session.getAttribute("userInfo") == null){
		response.sendRedirect("/ProSubmit/");
		return;
	}
	String pageTitle = "Manage Groups";
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
<%@ page import="prosubmit.controller.GroupManager" %>
<jsp:include page="/header.jsp"></jsp:include>
<div id="page-content" class="hbox">
	<div id="" class="flex3">
		<h1>Manage Groups</h1>
		<%
		SystemManager systemManager = new SystemManager();
		GroupManager groupManager = new GroupManager();
		
		ArrayList<HashMap<String,String>> semesters = new ArrayList<HashMap<String,String>>();
		systemManager.getSemesters(semesters);
		ArrayList<HashMap<String,String>> courses = new ArrayList<HashMap<String,String>>();
		systemManager.getCourses(courses);
		
		ArrayList<HashMap<String,Object>> groups = groupManager.getGroups(false);
		%>
		<div class="panel panel-default box-shadow">
			<div class="panel-body">
				<a id="add-group-link" href="#">Add</a>
				<form id="add-group-info-form" action="">
					<div id="add-group-error-message" class="alert alert-danger"></div>
					<fieldset>
						<legend>Information:</legend>
						<label for="groupname">Group Name:</label>
						<input id="groupname" type="text" class="form-control" maxLength="25" />
						
						<label for="groupnumber">Group Number:</label>
						<input id="groupnumber" type="text" class="form-control" maxLength="10" />
						
						<label for="groupdesc">Group Description:</label>
						<input id="groupdesc" type="text" class="form-control" maxLength="100" />
						
						<label for="groupsemester">Semester Code:</label>
						<select id="groupsemester" class="form-control">
						<%
							for (int i=0; i < semesters.size(); ++i) {
							%>
							<option value="<%= semesters.get(i).get("semester_code") %>"><%= semesters.get(i).get("semester_code") %></option>
							<%
							}
						%>
						</select>
						
						<label for="groupcourse">Course Id:</label>
						<select id="groupcourse" class="form-control">
						<%
							for (int i=0; i < courses.size(); ++i) {
							%>
							<option value="<%= courses.get(i).get("course_id") %>"><%= courses.get(i).get("course_name") %></option>
							<%
							}
						%>
						</select>
					</fieldset>
					<br/>
					<button type="button" class="btn btn-primary" onclick="return proSubmit.addGroup()">Add</button>
					<br/>
				</form>
				<br/>
				<br/>
				<h3>Groups</h3>
				<% if (groups.size() > 0) { %>
				<table class="table">
					<tr>
						<th>Name</th>
						<th>#</th>
						<th>Semester</th>
						<th>Course</th>
						<th>Options</th>
					</tr>
					<%
					//out.println(gson.toJson(projects));
					for(int i = 0;i<groups.size();i++){
							HashMap<String,Object> group = groups.get(i);
							%>
								<tr class="search-result">
									<td><a href="/ProSubmit/group/<%=group.get("group_id") + "-" + ((String)group.get("group_name")).replace(" ","_") %>"><%=group.get("group_name")%></a></td>
									<td><%=group.get("group_number")%></td>
									<td><%=group.get("semester_code")%></td>
									<td><%=group.get("course_id")%></td>
									<td><button class="btn btn-danger btn-sm" onclick="return proSubmit.deleteGroup(<%=group.get("group_id")%>)">Delete</button></td>
								</tr>
							<%
					}
					%>
				</table>
				<% } else { %>
					<div class="alert alert-info">There are currently no groups</div>
				<% } %>
			</div>
		</div>
	</div>
	<div id="" class="flex1 box-shadow">
		<jsp:include page="/right_section.jsp"></jsp:include>
	</div>

</div>
<jsp:include page="/footer.jsp"></jsp:include>