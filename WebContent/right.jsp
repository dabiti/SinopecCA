<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>��ʯ������ϵͳ</title>
<link href="style/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/shield.js"></script>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<c:if test='${clerk==null}'>
		<script type="text/javascript">
			if(parent != null)
				parent.location.href= "login.jsp";//��ǰҳ������ǵ�½��˵�ҳ�� 
			else
				self.location.href= "login.jsp";//��ǰҳ���Ǿ��������ĳҳ�� 
		</script>
		</c:if>
		<c:if test='${logintype=="ztzilxg"}'>
		<script type="text/javascript">
				self.location.href= "accountinfo.do?method=zhanghkhView";//��ǰҳ���Ǿ��������ĳҳ�� 
		</script>
		</c:if>
		<c:if test='${logintype=="ztkaih"}'>
		<script type="text/javascript">
				self.location.href= "accountinfo.do?method=zhanghkhView";//��ǰҳ���Ǿ��������ĳҳ�� 
		</script>
		</c:if>
		<c:if test='${logintype=="ztxiaoh"}'>
		<script type="text/javascript">
				self.location.href= "accountinfo.do?method=coverAccountForNetView";//��ǰҳ���Ǿ��������ĳҳ�� 
		</script>
		</c:if>
		
		<c:if test='${logintype=="ztqiantyy"}'>
		<script type="text/javascript">
				self.location.href= "yinjManage.do?method=forqiantyy";//��ǰҳ���Ǿ��������ĳҳ�� 
		</script>
		</c:if>
<%@ include file="/common/message.jsp"%>
<script type="text/javascript">

		function show(){
			$.messager.show({
				title:'����ӭʹ����ʯ������ϵͳ��',
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