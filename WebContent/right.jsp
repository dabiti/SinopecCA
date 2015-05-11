<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>中石化对账系统</title>
<link href="style/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/shield.js"></script>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<c:if test='${clerk==null}'>
		<script type="text/javascript">
			if(parent != null)
				parent.location.href= "login.jsp";//当前页面可能是登陆后菜单页面 
			else
				self.location.href= "login.jsp";//当前页面是具体操作的某页面 
		</script>
		</c:if>
		<c:if test='${logintype=="ztzilxg"}'>
		<script type="text/javascript">
				self.location.href= "accountinfo.do?method=zhanghkhView";//当前页面是具体操作的某页面 
		</script>
		</c:if>
		<c:if test='${logintype=="ztkaih"}'>
		<script type="text/javascript">
				self.location.href= "accountinfo.do?method=zhanghkhView";//当前页面是具体操作的某页面 
		</script>
		</c:if>
		<c:if test='${logintype=="ztxiaoh"}'>
		<script type="text/javascript">
				self.location.href= "accountinfo.do?method=coverAccountForNetView";//当前页面是具体操作的某页面 
		</script>
		</c:if>
		
		<c:if test='${logintype=="ztqiantyy"}'>
		<script type="text/javascript">
				self.location.href= "yinjManage.do?method=forqiantyy";//当前页面是具体操作的某页面 
		</script>
		</c:if>
<%@ include file="/common/message.jsp"%>
<script type="text/javascript">

		function show(){
			$.messager.show({
				title:'【欢迎使用中石化对账系统】',
				height:200,
				msg:'<br><br>${clerk.orgname}<br><br>IP:${clerk.ip}',
				timeout:5000,
				showType:'show'
			});
		}
</script>
</head>
<body  class="body_main" onload="show();" 
	onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;' 
	onload="document.getElementById('welcome').focus()">
	<div id="welcome" class="div_main">
		<img src="images/ui/welcome.jpg" /> 
	</div>
</body>
</html>