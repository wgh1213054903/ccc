<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<frameset rows="20%,*,10%">
	<frame id="topFrame" name="topFrame" src="${pageContext.request.contextPath }/top.jsp">
	<frameset cols="20%, *">
		<frame id="leftFrame" name="leftFrame" src="${pageContext.request.contextPath }/layout/left.jsp">
		<frame id="mainFrame" name="mainFrame">
	</frameset>
	<frame id="bottomFrame" name="bottomFrame" src="${pageContext.request.contextPath }/down.jsp">
</frameset>
</html>