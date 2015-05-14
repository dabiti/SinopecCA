<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<script src="js/pagejs/validateForm.js" type="text/javascript"></script>
		<script src="js/authorized.js" type="text/javascript"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript">

		
		
		function logout(){
			var clerktype = $("#clerkType").val();
			if(validateInput(clerktype)){
					form1.submit();
			}
		}
		
		function validateInput(clerktype){
			var reg;
			if($("#clerknum_7")==null||$("#clerknum_7").val()==""){
					alert("用户号不可为空");
					return false;
			}else{
					reg = /^\d{5,7}$/;
					if(reg!=null&&!reg.test($("#clerknum_7").val())){
						alert("用户号格式不正确");
						return false; 
					}
			};
				if($("#password")==null||$("#password").val()==""){
					alert("密码不可为空");
					return false;
				}else{
					reg = /\w{5,8}/;
					if(reg!=null&&!reg.test($("#password").val())){
						alert("密码格式不正确");
						return false;
					}
				};
				return true;
			//核心用户验证
		}
		</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<br>
		<logic:messagesPresent>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange">
						<html:errors />
					</td>
				</tr>
			</table>
		</logic:messagesPresent>
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange error">
						<div class="error orange"></div>
					</td>
				</tr>
			</table>
		</form>
		<html:form styleId="form1" action="login.do?method=forcedtosign" method="post">
			<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
				</tr>
				<tr>
					<td background="images/table_arrow_05.gif">&nbsp;
						
					</td>
					<td>
						<table width="100%" border="0" cellspacing="1" cellpadding="0"class="class1_table">
							<thead class="class1_thead">
								<tr>
									<th colspan="2" class="class1_thead th">
										强制签退
									</th>
								</tr>
							</thead>
							<tr>
								<td width="44%" class="class1_td alignright">
									用户代码:								</td>
								<td width="56%" class="class1_td alignleft">
									<html:text styleId="clerknum_7" property="code"  styleClass="inputField" onkeydown="if(event.keyCode==13)event.keyCode=9" />
							  </td>
							</tr>
								<tr>
									<td class="class1_td alignright">
										登录密码: 
									</td>
									<td class="class1_td alignleft">
										<html:password styleId="password" property="password" styleClass="inputField password" />
									</td>
								</tr>
							
										<tr>
												<td height="40">
												<input id="clerkType"  name="clerkType" type="hidden" value="0"/>
												</td>
										</tr>
						</table>
					</td>
					<td background="images/table_arrow_07.gif">&nbsp;
						
					</td>
				</tr>
				<tr>
					<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
				</tr>
			</table>
		<div class="funbutton">
			<button type="button" style="width:60px"
				onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1" onclick="logout();">
				<img src="images/save1.gif" width="12" height="12" align="absmiddle">
				确定
			</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button" style="width:90px" onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1"
				 onclick="javascript:window.location.href='login.jsp';" >
				<img src="images/back1.gif" width="11" height="11" "align="absmiddle">返回登录界面</button>
</html:form>
<object classid="clsid:7D131444-0A2F-4F37-9605-6E1BF067AF18" width="0" height="0" id="dtm" codebase="JZT998_For_Huaxia.ocx">
</object>
	</body>
</html>
