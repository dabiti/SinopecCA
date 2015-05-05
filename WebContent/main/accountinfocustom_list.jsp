<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/shield.js"></script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					账户查询 - 账户信息定制查询
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="w70">
								<button type="button" style="width:60px"
									onmouseover="this.className='buttom2'"
									onmouseout="this.className='buttom1'" class="buttom1"
									onClick="location.href='accountcustom.do?method=add'">
									<img src="images/add.gif" width="13" height="13"
										align="absmiddle">
									添加
								</button>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br>
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
								<th colspan="8" class="class1_thead th">
									账户信息定制查询列表
								</th>
							</tr>
							<tr>
								<td class="class1_thead th w60 center">
									序号
								</td>
								<!--
								 <td class="class1_thead th w60 center">
									任务标识
								</td>
								 -->
								<td class="class1_thead th w60 center">
									任务描述
								</td>
								<td class="class1_thead th w60 center">
									创建时间
								</td>
								<td class="class1_thead th w60 center">
									处理时间
								</td>
								<td class="class1_thead th w60 center">
									处理状态
								</td>
								<td class="class1_thead th w60 center">
									处理信息
								</td>
								<td class="class1_thead th w60 center">
									下载时间
								</td>
								<td class="class1_thead th w60 center">
									操作
								</td>
							</tr>

						</thead>
						<logic:iterate id="base" name="list" indexId="ind" type="com.unitop.sysmgr.bo.Dzcxinfo">
							<tr>
								<td class="class1_td ">
									<%=ind.intValue()+1%>
								</td>
								
								<td class="class1_td ">
									&nbsp;
									<bean:write name="base" property="description" />
									&nbsp;
								</td>
								<td class="class1_td ">
									&nbsp;
									<bean:write name="base" property="cj_time" />
									&nbsp;
								</td>
								<td class="class1_td ">
									&nbsp;
									<bean:write name="base" property="wc_time" />
								</td>
								<td class="class1_td ">
									<logic:equal name="base" property="statue" value="1">
										待处理
									</logic:equal>
									<logic:equal name="base" property="statue" value="0">
										<b><font color="red">处理完成</font></b>
									</logic:equal>
									<logic:equal name="base" property="statue" value="2">
										错误
									</logic:equal>
									&nbsp;
								</td>
									<td class="class1_td ">
									&nbsp;
									<bean:write name="base" property="error" />
								</td>
								<td class="class1_td ">
									&nbsp;
									<bean:write name="base" property="xz_time" />
								</td>
								<td class="class1_td ">
										<a href="javascript:if(confirm('确定要删除？'))window.location.href='accountcustom.do?method=delete&id=<bean:write name="base" property="id" />'">删除</a>
									<logic:equal name="base" property="statue" value="0">
										<a href="accountcustom.do?method=download&id=<bean:write name='base' property='id'/>">下载</a>
									</logic:equal>
								</td>
							</tr>
						</logic:iterate>
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
	</body>
</html>
