<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.unitop.config.SystemConfig"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
	<head>
		<%
			request.setAttribute("rootCode", SystemConfig.getInstance().getRootCode());
		%>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
	</head>
	<frameset name="mainFrame" cols="*" border="2" frameborder="no" framespacing="0" bordercolor="#EEEEEE">
		<frame name="mainF" src="../voucherManage.do?method=load&netpointflag=${clerk.orgcode}"
			scrolling="yes" marginwidth="0" marginheight="0" frameborder="no">
	</frameset>
	<noframes>
		<body>
		</body>
	</noframes>
</html>