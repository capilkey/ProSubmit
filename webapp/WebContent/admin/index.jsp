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
<jsp:include page="/header.jsp"></jsp:include>
<div id="page-content" class="hbox">
	<div class="flex4">
		<%
			Gson gson = new Gson();
			out.println(gson.toJson(session.getAttribute("userInfo")));
		%>
	</div>
	
	<div class="flex1">
	</div>



</div>
<jsp:include page="/footer.jsp"></jsp:include>