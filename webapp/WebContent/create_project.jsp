<%
    if(session.getAttribute("isPartner") == null || session.getAttribute("isPartner") != "1" || session.getAttribute("userInfo") == null){
    	response.sendRedirect("/ProSubmit/");
    	return;
    }
%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="prosubmit.db.DBAccess"%>
<%@page import="prosubmit.controller.ProjectManager"%>
<jsp:include page="/header.jsp"></jsp:include>
<div id="page-content" class="hbox">
	<div class="flex4">
		<%
			ProjectManager projectManager = new ProjectManager();
			ArrayList<HashMap<String,String>> projectcats = projectManager.getAllCategoryInfo();
			
			HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
		%>
		<h1>Project Proposal</h1>
		<div id="project-error-message" class="alert alert-danger" style="display:none"></div>
		<div class="panel panel-default">
			<div class="panel-body">
				<fieldset>
					<label for="projtitle">Project Title:</label>
					<input type="text" id="projtitle" maxLength="25" class="form-control" />
					<br />
					<label for="projdesc">Project Description:</label>
					<textarea id="projdesc" class="form-control" rows="25"> </textarea>
					<br />
					<label for="projcat">Project Category:</label>
					<select id="projcat" class="form-control">
						<%
							for (int i=0; i < projectcats.size(); ++i) {
							%>
								<option value="<%= projectcats.get(i).get("projcategory_id") %>"><%= projectcats.get(i).get("projcategory_name") %></option>
							<%
							}
						%>
					</select>
					<br />
					<input type="hidden" id="partner_id" value="<%=userInfo.get("partner_id")%>"/>
				</fieldset>
			</div>
			<div class="panel-footer">
				<input type="button" value="Create Project" class="btn btn-primary" onclick="return proSubmit.createProject('/ProSubmit/')" />
				<br />
				<br />
			</div>
		</div>
	</div>
	<div id="" class="flex1 box-shadow">
		<jsp:include page="/right_section.jsp"></jsp:include>
	</div>
</div>
<jsp:include page="/footer.jsp"></jsp:include>