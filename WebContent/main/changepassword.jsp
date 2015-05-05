<%@page language="java" contentType="text/html;charset=gbk"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="../style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/shield.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
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
		<html:form action="changepwd.do" method="post">
			<table width="97%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
				</tr>
				<tr>
					<td background="../images/table_arrow_05.gif">
						&nbsp;
					</td>
					<td>
						<table width="100%" border="0" cellspacing="1" cellpadding="0"
							class="class1_table">
							<thead class="class1_thead">
								<tr>
									<th colspan="2" class="class1_thead th">
										修改密码
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									原密码：
								</td>
								<td class="class1_td alignleft">
									<html:password styleId="password2" property="password" styleClass="inputField"
										/>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									新密码：
								</td>
								<td class="class1_td alignleft">
									<html:password styleId="password" property="newpassword" styleClass="inputField"
										/>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									确认新密码：
								</td>
								<td class="class1_td alignleft">
									<html:password styleId="password1" property="newpassword1" styleClass="inputField"
										/>
								</td>
							</tr>
						</table>
					</td>
					<td background="../images/table_arrow_07.gif">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
				</tr>
			</table>
		</html:form>
		<div class="funbutton">
			<button type="button" style="width:60px" onclick="document.forms[0].submit()"
				onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1">
				<img src="../images/save1.gif" width="12" height="12"
					align="absmiddle">
				确定
			</button>
			&nbsp;&nbsp;&nbsp;
			<button style="width:60px" onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1"
				onclick="window.close();">
				<img src="../images/back1.gif" width="11" height="11"
					onclick="history.back()" align="absmiddle">
				取消
			</button>
		</div>
	</body>
</html>
