<%
	if(session == null || session.getAttribute("isProfessor") != "1" ||  session.getAttribute("userInfo") == null){
		response.sendRedirect("/ProSubmit/");
		return;
	}
	String pageTitle = "Professor - Home";
%>
<jsp:include page="/header.jsp"></jsp:include>
<div id="page-content" class="hbox">




</div>
<jsp:include page="/footer.jsp"></jsp:include>