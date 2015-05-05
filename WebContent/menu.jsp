<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link href="style/default.css" rel="stylesheet" type="text/css"/>
		<script src="js/pagejs/menu.js" type="text/javascript"></script>
		<script src="js/pagejs/ocx.js" type="text/javascript"></script>
	</head>
<body STYLE="BACKGROUND-COLOR:#ebf8ff;">
	<img src="images/ui/images/icon_open.png"  style="display:none" >
	<img src="images/ui/images/icon_close.png" style="display:none" >
	<% int i=0; %>
	<c:forEach items="${list}" var="list">
		<c:if test='${list.isShow==1}'>
		<unitop:hasPrivilege name="${list.hasPrivateNameString}">
			<table width="207" height="30" border="0" cellpadding="0" cellspacing="0" style="background-image:url(images/ui/column_bg.gif);">
				<tr>
					<td width="25" height="25" align="center">
						<A class="menu_top" href="javascript:onChange(<%=i %>)"><img id=image<%=i %> src="images/ui/icon_close.png" width="16" height="14" border="0"></A>
					</td>
					<td height="25" colspan="3">
						<c:if test='${list.url!=""&&list.classid!=""}'>
							<A href="javascript:void(<%=i %>)"  class="menu_top" onclick='javascript:try{obj.${list.url};}catch(e){alert("系统检测：本机没有安装验印插件!");};' ><strong>${list.name}</strong></A>
						</c:if>
						<c:if test='${list.url==""&&list.classid==""}'>
							<A href="javascript:void(<%=i %>)"  class="menu_top" onclick="onChange(<%=i %>)"><strong>${list.name}</strong></A>
						</c:if>
					</td>
				</tr>
			</table>
		</unitop:hasPrivilege>
		<SPAN id=child<%=i %> style="DISPLAY: none">
		<c:forEach items="${list.list}" var="glist">
		<c:if test='${glist.isShow==1}'>
			<unitop:hasPrivilege name="${glist.id}">
					<table width="207" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="20" height="25">
									&nbsp;
								</td>
								<td width="20" height="25">
									<img src="images/ui/icon_content.gif" width="8" height="8">
								</td>
								<td width="128" height="25">
									<a class="menu_in" href="${glist.url}" target="mainF0" >${glist.name}</a>
								</td>
							</tr>
					</table>
			</unitop:hasPrivilege>
		</c:if>
		</c:forEach>
		</SPAN><%i++; %>
	</c:if>
	</c:forEach>
	<script language="JavaScript" FOR="obj" EVENT="SendEvent(sendMessage)">
	 		var rMessage = SendSesssion("uniDBInterface.jsp",sendMessage,"text");
    		return rMessage;
	</script>
	<OBJECT ID="obj" CLASSID="clsid:87100b1f-19fa-4266-a03e-0536ffa3c8af" style="display:none"/>
	</body>
</html>