<%@page language="java" contentType="text/html;charset=GBK" isELIgnored="false" pageEncoding="GBK"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>中石化对账系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript" src="js/pagejs/shuangrhq.js"></script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<html:form styleId="form1" action="changepwd.do" method="post">
			<table width="100%" border="0" align="center" cellpadding="0"
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
							class="class1_table" align="center">
							<thead class="class1_thead">
								<tr>
									<th colspan="2" class="class1_thead th">
										双人会签
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									用户号：
								</td>
								<td class="class1_td alignleft">
									<input id=clerknum name=clerknum type='text' class="inputField required clerknum"  style="width:150px;"/>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									用户密码：


								</td>
								<td class="class1_td alignleft">
									<input id=password name=password type='password' class="inputField required clerk_password"  style="width:150px;"/>
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
				<br>
			</table>
			<table>
			<tr>
			<td width="30%">
				&nbsp;
			</td>
			<td width="25%" >
				<button type="submit" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12" align="absmiddle">
					会签
				</button>
			</td>
			<td width="15%">
				&nbsp;
			</td>
			<td >
				<button type="button" style="width:60px" onclick="window.close()"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12" align="absmiddle">
					取消
				</button>
			</td>
			</tr>
			</table>
</html:form>
	</body>
</html>
