<%
	if(session == null || session.getAttribute("isPartner") != "1" || session.getAttribute("userInfo") == null){
		response.sendRedirect("/ProSubmit/");
		return;
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
<jsp:include page="../header.jsp"></jsp:include>
<div id="page-content" class="hbox">
		<div class="flex4">
			<%
				HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
				Gson gson = new Gson();
				out.println(gson.toJson(userInfo));
			%>
			<h1>Account Information</h1>
			<div>
				<h3><%=userInfo.get("username")%> (<%=userInfo.get("job_title")%>)</h3> 
				<span><%=userInfo.get("email")%></span><br/>
				<span><%=userInfo.get("telephone")%> ex:<%=userInfo.get("extension")%></span><br/>
				<span><%=userInfo.get("company_name")%></span><br/>
				<span><%=userInfo.get("company_address")%></span><br/>
			
			</div>
			
			
			<h3>Projects</h3>
			
		</div>
		<div class="flex1">
		</div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>