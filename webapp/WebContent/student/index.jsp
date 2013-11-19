<%
	if(session == null || session.getAttribute("isStudent") != "1" || session.getAttribute("userInfo") == null){
		response.sendRedirect("/ProSubmit/");
		return;
	}
	String pageTitle = "Home - Student";
%>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="prosubmit.controller.GroupManager" %>
<%@ page import="prosubmit.db.DBPool" %>

<jsp:include page="../header.jsp"></jsp:include>
<div id="page-content" class="hbox">
	<div class="flex3">
	</div>
	<div class="flex1">
	</div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>