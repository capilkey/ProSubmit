<%
	if(session == null || session.getAttribute("isStudent") != "1" || session.getAttribute("userInfo") == null){
		response.sendRedirect("/ProSubmit/login.jsp");
	}
%>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="prosubmit.controller.GroupHandler" %>
<%@ page import="prosubmit.db.DBAccess" %>

<jsp:include page="../header.jsp"></jsp:include>
<div id="page-content" class="hbox">
	<%
		HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
		HashMap<String,Object> group = new HashMap<String,Object>();
		GroupHandler gh = new GroupHandler((DBAccess)session.getAttribute("dbAccess"));
		gh.getGroup(group,userInfo.get("group_id"));
		ArrayList<HashMap<String,String>> students = (ArrayList<HashMap<String,String>>)group.get("students");
		Gson gson = new Gson();
		out.println(gson.toJson(group));
	%>
	<div class="flex4">
		<h1><%=group.get("group_name")%></h1>
	</div>
	<div class="flex1">
	</div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>