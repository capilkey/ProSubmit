<%
	if(session == null || session.getAttribute("userInfo") == null){
		response.sendRedirect("/ProSubmit/");
		return;
	}
	
	boolean adminOrProf = (session.getAttribute("isAdmin") != null && session.getAttribute("isAdmin").equals("1")) ||
			(session.getAttribute("isProfessor") != null && session.getAttribute("isProfessor").equals("1"));

	GroupManager groupManager = new GroupManager();

	String groupId = request.getParameter("group_id");
	HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
	HashMap<String,Object> group = groupManager.getGroup(groupId);
	
	
	
	ArrayList<HashMap<String,String>> students = (ArrayList<HashMap<String,String>>)group.get("students");
	String pageTitle = "Group - " + group.get("group_name");
%> 
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="prosubmit.controller.GroupManager" %>
<%@ page import="prosubmit.db.DBPool" %>

<jsp:include page="/header.jsp"></jsp:include>
<div id="page-content" class="hbox">
	<div class="flex4">
		<h1><%=group.get("group_name")%></h1>
		<p id=""><%=group.get("group_desc")%></p>
		
		
		<%
				if(userInfo.get("student_id") != null && (userInfo.get("group_id").equals(group.get("group_id")))){
					%>
					<div id='cont-edit-group-desc' class="cont_edit_student_bio">
						<textarea class="bio"><%=group.get("group_desc")%></textarea>
						<button type="button" class="btn btn-primary" onclick="return proSubmit.updateGroupDesc(null,<%=group.get("group_id")%>)">Done</button>
					</div>
		
					<a id="edit-group-desc-link" href="#" onclick="return proSubmit.updateGroupDesc(this)">Edit Description</a><%
				}
		%>
		
		<%
			Gson gson = new Gson();
			//out.println(gson.toJson(userInfo));
			for(int i=0;i<students.size();i++){
				%>
				<h3><%=students.get(i).get("student_firstname")+" " + students.get(i).get("student_lastname") %></h3>
				<% if (adminOrProf) { %>
				<button type="button" class="btn btn-primary" onclick="return proSubmit.removeStudent(<%= students.get(i).get("student_id") %>)">Remove</button>
				<% } %>
				<br/>
				<em><%=students.get(i).get("student_email") %></em>
				<p id='student_bio_<%=students.get(i).get("student_id")%>'><%=students.get(i).get("student_bio") %></p>
				<div id='cont_edit_student_bio_<%=students.get(i).get("student_id")%>' class="cont_edit_student_bio">
					<textarea class="bio"></textarea>
					<button type="button" class="btn btn-primary" onclick="return proSubmit.updateStudentBio(null,<%=students.get(i).get("student_id")%>)">Done</button>
				</div>
				<%
				if(userInfo.get("student_id") != null && (userInfo.get("student_id").equals(students.get(i).get("student_id")))){
					%><a id="edit_student_bio_link_<%=students.get(i).get("student_id")%>" href="#" onclick="return proSubmit.updateStudentBio(this,'<%=students.get(i).get("student_id")%>')">Edit Bio</a><%
				}
			}
		%>
		
		<% if (adminOrProf) { %>
		<a id="add-group-member-link" href="#">Add Group Member</a>
	  	<form id="add-group-member-info-form" action="">
			<div id="group-error-message" class="alert alert-danger"></div>
		
			<fieldset>
	    		<legend>New Student:</legend>
	    	</fieldset>
	    	<label for="studentusername">Student Username:</label>
	    	<input id="studentusername" type="text" class="form-control" maxLength="25" />
	    </form>
		<% } %>
		
	</div>
	<div id="" class="flex1">
		<jsp:include page="/right_section.jsp"></jsp:include>
	</div>
</div>
<jsp:include page="/footer.jsp"></jsp:include>