<%@page language="java" contentType="text/html;charset=gbk" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript"> 
			function backHome() {
				if(this.window.location==this.window.top.location){
					window.opener  =  null; 
					window.open("","_self");
					window.close();
					window.open('logout.do','_blank');
				}else{
					history.back();
				}
				
			}
		 $(document).ready(function() {
		   $("#form1").validate({
		   errorLabelContainer:"#error div.error",
		   wrapper:"li",
		   submitHandler:function(form){
		   form.submit();
		   }
		 }) 
		 });
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
		<html:form styleId="form1" action="changepwd.do" method="post">
			<table width="97%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
				</tr>
				<tr>
					<td background="images/table_arrow_05.gif">
						&nbsp;
					</td>
					<td>
						<table width="100%" border="0" cellspacing="1" cellpadding="0"
							class="class1_table">
								<tr>
									<th colspan="2" class="class1_thead th">
										�޸�����
									</th>
								</tr>
							<tr>
								<td class="class1_td w250">
									�����룺
								</td>
								<td class="class1_td alignleft">
									<html:password styleId="password2" property="password" styleClass="inputField required clerk_password"
										style="width:150px; " />
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									�����룺
								</td>
								<td class="class1_td alignleft">
									<html:password styleId="password" property="newpassword" styleClass="inputField required clerk_password"
										style="width:150px; " />
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									ȷ�������룺
								</td>
								<td class="class1_td alignleft">
									<html:password styleId="password1" property="newpassword1" styleClass="inputField required clerk_password"
										style="width:150px; " />
								</td>
							</tr>
						</table>
					</td>
					<td background="images/table_arrow_07.gif">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
				</tr>
			</table>
		<div class="funbutton">
			<button type="submit" style="width:60px"
				onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1">
				<img src="images/save1.gif" width="12" height="12" align="absmiddle">
				ȷ��
			</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button" style="width:60px" onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1"
				onclick="backHome()">
				<img src="images/back1.gif" width="11" height="11"
					" align="absmiddle">
				����
			</button>
</html:form>
	</body>
</html>