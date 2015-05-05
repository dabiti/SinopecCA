<%@ page contentType="text/html; charset=GBK" language="java"  errorPage="" isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>中石化对账系统</title>
</head>
	<frameset name="mainFrame"  cols="217,8,*" border="2" frameborder="no" framespacing="0" bordercolor="#EEEEEE">
		<frame name="tree" src="clerk_org_tree.jsp" scrolling="auto" marginwidth="0" marginheight="0" frameborder="no">
		<frame name="bar" src="bar.htm" noResize scrolling="no">
		<frame name="mainF" src="../clerkManage.do?method=load&orgcode=${clerk.orgcode}" scrolling="yes" marginwidth="0" marginheight="0" frameborder="no">
 	</frameset>
<noframes><body>
</body></noframes>
</html>
