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
<jsp:include page="/header.jsp"></jsp:include>
<div id="page-content" class="hbox">
		<div class="flex4">
			<%
				PartnerManager partnerManager = new PartnerManager((DBAccess)session.getAttribute("dbAccess"));
				HashMap<String,Object> partner = new HashMap<String,Object>(); 
				partnerManager.getPartner(partner_id, partner,true);
				
				Gson gson = new Gson();
				//out.println(gson.toJson(partner));
			%>
			<h1>Account Information</h1>
			<div class="panel panel-default">
			  <div class="panel-body">
			    <div>
						<h3 style="display:inline"><%=partner.get("firstname") +" " + partner.get("lastname")%> (<%=partner.get("job_title")%>)</h3> 
						<span><%=partner.get("company_name")%></span><br/>
						<span><%=partner.get("email")%></span><br/>
						<span><%=partner.get("telephone")%> ex:<%=partner.get("extension")%></span><br/>
						<span><%=partner.get("company_address")%></span><br/>
					</div>
			  </div>
			  <div class="panel-footer">
			  	<a id="edit-partner-link" href="#">Edit</a>
			  	
			  	<form id="partner-edit-info-form" action="">
						<div id="partner-register-update-info-error-message" class="alert alert-danger"></div>
					
						<fieldset>
				    <legend>Personalia:</legend>
				    	<input id="partner_id" type="hidden" value="<%=partner.get("partner_id")%>"/>
				    	
							<label for="firstname">First Name:</label>
							<input id="firstname" type="text" class="form-control" maxLength="25" value="<%=partner.get("firstname")%>"/>
							
							<label for="lastname">Last Name:</label>
							<input id="lastname" type="text" class="form-control" maxLength="25" value="<%=partner.get("lastname")%>"/>
							
							<label for="email">Email:</label>
							<input id="email" type="email" class="form-control" value="<%=partner.get("email")%>"/>
						</fieldset><br/>
				
				    <fieldset>
				    <legend>Company:</legend>
				    
							<label for="company">Company:</label>
							<input id="company" type="text" class="form-control" maxLength="100" value="<%=partner.get("company_name")%>"/>
							
							<label for="url">URL:</label>
				      <input id="url" type="text" class="form-control" value="<%=partner.get("company_url")%>"/>
							
							<label for="industry">Industry:</label>
							<select id="industry" class="form-control">
				        <% 
				            SystemManager systemManager = new SystemManager((DBAccess)session.getAttribute("dbAccess"));
				        		ArrayList<HashMap<String,String>> industries = new ArrayList<HashMap<String,String>>();
				        		systemManager.getIndustries(industries); 
				        		for(int i =0;i<industries.size();i++){
				        			HashMap<String,String> industry = (HashMap<String,String>)industries.get(i);
				        			%>
				        				<option value='<%=industry.get("id")%>' <%if(industry.get("id").equals(partner.get("industry"))){%>selected<%}%>><%=industry.get("industry")%></option>
				        			<%
				        		}
				        		
				        %>
							</select>
							<label for="jobtitle">Job Title:</label>
							<input id="jobtitle" type="text" class="form-control" maxLength="50" value="<%=partner.get("job_title")%>"/>
							
							<label for="tel">Tel:</label>
							<input id="tel" type="text" class="form-control" maxLength="16" value="<%=partner.get("telephone")%>"/>
							
							<label for="extension">Ext:</label> 
					    <input id="extension" type="text" class="form-control" maxLength="10" value='<%=partner.get("extension")%>'/>
					    
							<label for="company-addredd">Company Address:</label>
							<input id="company-address" type="text" class="form-control" value='<%=partner.get("company_address")%>'/>
						</fieldset>
						<br/>
						<button type="button" class="btn btn-primary" onclick="return proSubmit.updatePartner()">Update Info!</button>
						<br/><br/>
					</form>
					
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
					  	<th>#</th>
					  	<th>Name</th>
					  	<th>Created</th>
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
			
			<div class="panel panel-default">
			  <div class="panel-body">
			  	<h3 style="display:inline">Change Password</h3>
			  	<br/><br/>
			  	<form id="partner-update-password-form">
			  		<div class="alert alert-danger no-show"></div>
			  		<div class="alert alert-info no-show"></div>
			  		
			  		<label for="current_password">Current Password</label>
			  		<input id="current_password" type="password" class="form-control" size="50"/>
			  		
			  		<label for="password">Passsword:</label>
			  		<input id="password" type="password" class="form-control" size="50"/>
			  		
			  		<label for="confirm_passord">Confirm Password:</label>
			  		<input id="confirm_password" type="password" class="form-control" size="50"/>
			  		<br/>
			  		<button type="button" class="btn btn-primary" onclick="return proSubmit.updatePassword()">Change Now!</button>
			  	</form>
			  </div>
			</div>
			<hr/>
			
			
			<div class="panel panel-warning">
			  <div class="panel-body">
			  	<h3 style="display:inline">Account Cancellation</h3>
			  	<div>
						<div class="alert alert-warning">
							Account cancellation is dependent on the status of your project. You will be notified
							Whether or not cancellation is capable or not.
							Please note, one you have cancelled your account it can no longer be retrieved.
						</div>
						<div id="cancellation-failure-message" class="alert alert-danger no-show"></div>
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