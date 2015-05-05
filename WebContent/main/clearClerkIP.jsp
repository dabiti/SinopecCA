<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<%@ include file="/common/validator.jsp"%>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript" src="js/pagejs/clearClerkIP.js"></script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					系统管理 - 清除柜员IP限制
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<br>
					&nbsp;&nbsp;
					<!-- <button type="button" style="width:80px"
						onmouseover="this.className='buttom2'"
						onmouseout="this.className='buttom1'" class="buttom1"
						onclick="javascript:window.location.href='clearClerkIP.do?method=clerkOrgclerkIP'">
						按机构清除
					</button> -->
				</td>
			</tr>
		</table>
		<br>
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange error">
						<div class="error orange"></div>
					</td>
				</tr>
				<logic:messagesPresent>
					<tr>
						<td class="orange">
							<div id=abc class="abc">
								<html:errors />
							</div>
						</td>
					</tr>
				</logic:messagesPresent>
			</table>
		</form>
		<form id="form1" name="form1" method="post"
			action="clearClerkIP.do?method=clear">
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
							<thead class="class1_thead">
								<tr>
									<th colspan="2" class="class1_thead th">
										清除柜员IP限制
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									柜员代码
								</td>
								<td class="class1_td alignleft">
									<input id="clerknum" name="clerkCode" type="text"
										onkeydown='if(event.keyCode==13){submitEnter(this)}'	
										class="inputField required"/>
									&nbsp;
									<button type="button" style="width:60px"
										onmouseover="this.className='buttom2'" 
										onmouseout="this.className='buttom1'" class="buttom1" onclick="submitEnter(this)" >
										查询
									</button> 
									&nbsp;
									<button type="submit" style="width:60px"
										onmouseover="this.className='buttom2'"
										onmouseout="this.className='buttom1'" class="buttom1">
										清除
									</button>
									<span id="error_clerk" style="color: red;"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									柜员名称
								</td>
								<td id="clerkName" class="class1_td alignleft">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									所属机构
								</td>
								<td id="clerkOrg" class="class1_td alignleft">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									锁定 IP
								</td>
								<td id="clerkIP" class="class1_td alignleft">
									&nbsp;
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
		</form>
	</body>
</html>