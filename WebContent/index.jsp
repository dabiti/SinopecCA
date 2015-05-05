<%@page language="java" contentType="text/html;charset=GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">   
	<%@ include file="/common/title.jsp"%>
	<script type="text/javascript" src="js/shield.js"></script>
</head>
<frameset rows="75,*,20" frameborder="NO" border="0" framespacing="0" >
  <frame src="head.jsp" name="topFrame" scrolling="NO"  noresize="noresize">
  <frame src="mid.htm" name="mainFrame" scrolling="NO">
  <frame src="tail.html" name="tailFrame" scrolling="NO"  noresize="noresize">
</frameset>
<noframes >
<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'">
</body>
</noframes>
</html>
