<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<%@ page import="com.unitop.sysmgr.service.SystemMgrService,javax.servlet.ServletContext,org.springframework.web.context.WebApplicationContext,org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	ServletContext servletContext = this.getServletConfig().getServletContext(); ;
	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	SystemMgrService sys = (SystemMgrService) wac.getBean("systemMgrService");
	String time = sys.getSystetemNowDate();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link href="style/default.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/development_date.js"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/head.js"></script>
		<c:if test='${clerk==null}'>
		<script type="text/javascript">
			if(parent != null)parent.location.href= "login.jsp";//当前页面可能是登陆后菜单页面 
			else self.location.href= "login.jsp";//当前页面是具体操作的某页面
		</script>
		</c:if>
		<c:if test='${clerk!=null}'>
		<script type="text/javascript">
			function closeWindow2(){
				var xmlHttp = null;
				if (window.ActiveXObject) {
					if (xmlHttp == null)
						xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				} else if (window.XMLHttpRequest) {
					if (xmlHttp == null)
						xmlHttp = new XMLHttpRequest();
				}
				var xmlDoc=new ActiveXObject("Msxml2.DOMDocument.3.0");
				xmlHttp.open("get", "logout.do?id="+new Date(), true);
				xmlHttp.send();
			}
			function logout2(){
				var jigh ='${clerk.orgcode}';
				if(confirm('你确定要退出吗?')){	 
					var returnData=0;
					$.ajax({
						 	 async:false,
						 	 url:"accountinfo.do?method=getTask",
							 dataType:"text",
							 data: {jigh:jigh},
							 cache:false,
							 success:function (data,textStatus){
								 if(textStatus=="success")
									 returnData=data;
							 }
					 	}
					 );
					 if(returnData >0&&confirm('本机构存在'+returnData+'个待建库或审核账号，是否查看')){
						var url='accountinfo.do?method=queryToDoZhangh&jigh='+jigh;
						var all=window.parent.document.frames["mainFrame"].document.frames["mainF0"];
						all.location.href=url;
						window.event.returnValue = false;
						return;					 	
					 }else{
						 var loginType ='<%=session.getAttribute("logintype")%>';
						 if(loginType!=null&&loginType!="null"){
						 	parent.window.status ='AGREE_EXIT';
						 	newwin = parent.window.open("","_parent","");
						 	newwin.close();
						 }else{
						 	closeWindow2();
						 }
					}
				}else{
					window.event.returnValue = false;
					return;
				}
			}
			
//		window.onunload = null;
//			window.onbeforeunload = onbeforeunload_handler;
//			function onbeforeunload_handler(){
//				var jigh ='${clerk.orgcode}';
//					var returnData=0;
//				$.ajax({
//					 	 async:false,
//					 	 url:"accountinfo.do?method=getTask",
//						 dataType:"text",
//						 data: {jigh:jigh},
//						 cache:false,
//						 success:function (data,textStatus){
//							 if(textStatus=="success")
//								 returnData=data;
//						 }
//					 	}
//					 );
//					 if(returnData >0&&confirm('本机构存在'+returnData+'个待建库或审核账号，是否查看')){
//						var url='accountinfo.do?method=queryToDoZhangh&jigh='+jigh;
//						var all=window.parent.document.frames["mainFrame"].document.frames["mainF0"];
//						all.location.href=url;
//						window.event.returnValue = false;
//						return ;					 	
//					 }else{
//						 var loginType ='<%=session.getAttribute("logintype")%>';
//						 if(loginType!=null&&loginType!="null"){
//						 	parent.window.status ='AGREE_EXIT';
//						 	newwin = parent.window.open("","_parent","");
//						 	newwin.close();
//						 }else{
//						 	closeWindow2();
//						 }
//					}
				
				
//			}
		</script>
		</c:if>
	</head>
	<body class="head" onunload="" onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;' onload="syncSvrTime('time','clock')">
		<div class="head_left">
		    <div class="head_logo"></div>
		</div>
		<table width="95%" class="head_right">
			<tr>
				<td align="right" class="user">
					<span id='clock'></span>
					<span id="thetime"></span>
					<input id=time type="hidden" value='<%=time%>'/>
				</td>
			</tr>
			<tr>
				<td align="right" class="user">
				<a class="menu_in"  target="mainF0"  id="chaxun" ></a>
					<c:if test="${clerk.wdFlag !='0'}" >
					分行机构：${clerk.shOrgName}[${clerk.shOrgCode}]
					</c:if>
				</td>
			</tr>
			<tr align="right">
				  <TD width="95%" colspan=2 class="user2" valign="bottom" >
					站点：${clerk.orgname}[${clerk.orgcode}]
					用户：${clerk.name}[${clerk.code}]
					<!-- 角色: ${clerk.postName} -->
					<!-- <b class="user_icon01"/><a class="user2" href="commonProblem.jsp" target="mainF0">常见问题解答</a> -->
<%-- 					<c:if test="${clerk.leixbs eq '0'}">
					<b class="user_icon02"/><a class="user2" href="forwordchangepwd.do" target="mainF0">修改密码</a>
					</c:if> --%>
					<b class="user_icon02"/><a class="user2" href="login.jsp" onclick="logout2();" target="_parent">安全退出</a>
				  </TD>							
			</tr>	
			<tr height="10"></tr>					
		</table>
	</body>
</html>