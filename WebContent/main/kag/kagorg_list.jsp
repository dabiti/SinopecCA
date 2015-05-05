<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/shield.js"></script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					印鉴卡管理-卡柜维护
				</td>
			</tr>
			<tr>
				<td>&nbsp;
					
				</td>
				<td>

				</td>
			</tr>
		</table>
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
		<table width="97%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">&nbsp;
					
				</td>
				<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="0" 
						class="class1_table">
						<thead class="class1_thead">
							<tr>
								<th colspan="7" class="class1_thead th">
									&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									机构列表 &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
								
								</th>
							</tr>
							<tr>
								<td class="class1_thead th w60">
									序号
								</td>
								<td class="class1_thead th">
									机构号
								</td>
								<td class="class1_thead th">
									机构名称
								</td>
								<td class="class1_thead th">
									操作
								</td>
							</tr>
						</thead>
						<logic:iterate id="org" indexId="ind" name="orglist"
							type="com.unitop.sysmgr.bo.Org">
							<tr>
								<td class="class1_td w60">
									<%=ind.intValue() + 1%>
								</td>
								<td class="class1_td w80">
									<bean:write name="org" property="code" />
								</td>
								<td class="class1_td alignright">
									<div align="center">
										<bean:write name="org" property="name" />
									</div>
								</td>
								<td class="class1_td alignright">
									<div align="center">
									<a href="kag.do?method=kaglist&orgcode=<bean:write name="org" property="code" />">查看卡柜信息</a>
										
										&nbsp;
									</div>
								</td>
							</tr>
						</logic:iterate>
					</table>
				</td>
				<td background="images/table_arrow_07.gif">&nbsp;
					
				</td>
			</tr>
			<tr>
				<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
			</tr>
		</table>
	</body>
</html>