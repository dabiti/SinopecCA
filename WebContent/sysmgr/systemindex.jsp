<%@page language="java" contentType="text/html;charset=GBK"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<HTML>
<HEAD>
<TITLE>配置控制台 V1.0</TITLE>
<META http-equiv=Content-Type content="text/html; charset=GBK">
<META http-equiv=Pragma content=no-cache>
<META http-equiv=Cache-Control content=no-cache>
<META http-equiv=Expires content=-1000>
<LINK href="../style/admin.css" type="text/css" rel="stylesheet">
</HEAD>
<FRAMESET border=0 frameSpacing=0 rows="60, *" frameBorder=0>
<FRAME name=header src="sysmgr/head.jsp" frameBorder=0 noResize scrolling=no>
<FRAMESET cols="170, *">
<FRAME name=menu src="sysmgr/menu.jsp" frameBorder=0 noResize>
<FRAME name=main src="sysmgr/main.htm" frameBorder=0 noResize scrolling=yes>
</FRAMESET>
</FRAMESET>
<noframes >
<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;' >
	<%      
	  response.setHeader("Pragma","No-cache");        
	  response.setHeader("Cache-Control","no-cache");        
	  response.setDateHeader("Expires",0);      
	%> 
</body>
</noframes>
</HTML>

