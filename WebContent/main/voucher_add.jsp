<%@page language="java" contentType="text/html;charset=gbk" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<script src="js/pagejs/validateForm.js" type="text/javascript"></script>
		<%@ include file="/common/yewgz.jsp"%>	
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					系统管理 - 凭证参数设置
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
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
<html:form styleId="form1" action="voucherManage.do?method=createVoucher" method="post">
			<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
				</tr>
				<tr>
					<td background="images/table_arrow_05.gif">
						&nbsp;
					</td>
					<td>
						<table width="100%" border="0" cellspacing="1" cellpadding="0" class="class1_table">
							<thead class="class1_thead">
								<tr>
									<th colspan="2" class="class1_thead th"> 
										新增凭证
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									凭证标识：
								</td>
								<td class="class1_td alignleft">
									<html:text styleId="pingzbs" property="pingzbs" styleClass="inputField required"/>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									凭证名称：
								</td>
								<td class="class1_td alignleft">
									<html:text styleId="pingzmc" property="pingzmc" styleClass="inputField required"/>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									凭证前缀：
								</td>
								<td class="class1_td alignleft">
									<html:text styleId="pingzhqz" property="pingzhqz" styleClass="inputField" />
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									每本张数：
								</td>
								<td class="class1_td alignleft">
									<html:text styleId = "meibzs" property="meibzs" styleClass="inputField"/>
								</td>
							</tr>
							<tr>
					          <td class="class1_td w250">是否重控：</td>
					          <td class="class1_td alignleft">
								  <html:select styleId="shifzk" property="shifzk">
								  	<html:option value="0">否</html:option>
								  	<html:option value="1">是</html:option>
								  </html:select>
							  </td>
					        </tr>
					        <tr>
					          <td class="class1_td w250">是否启用：</td>
					          <td class="class1_td alignleft">
							   <html:select styleId="shifqy" property="shifqy">
								  	<html:option value="0">否</html:option>
								  	<html:option value="1">是</html:option>
							   </html:select>
					        </tr>
					        <tr>
								<td class="class1_td w250">
									备注说明：
								</td>
								<td class="class1_td alignleft">
									<html:text styleId = "yanyjb" property="yanyjb" styleClass="inputField"/>
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
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					保存
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="history.back()">
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					取消
				</button>
			</div>
</html:form>
</body>
</html>