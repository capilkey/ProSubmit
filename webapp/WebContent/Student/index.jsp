<%
	if(session == null || session.getAttribute("isStudent") != "1" || session.getAttribute("userInfo") == null){
		response.sendRedirect("/ProSubmit/login.jsp");
	}
%>
<jsp:include page="../header.jsp"></jsp:include>
<div id="page-content" class="hbox">
	<div class="flex4">
	</div>
	<div class="flex1">
	</div>
</div>
<jsp:include page="../footer.jsp"></jsp:include>