<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<link rel="stylesheet" type="text/css" href="style/date_input.css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<script src="js/pagejs/valiateFormForImg.js" type="text/javascript"></script>
		<%@ include file="/common/DateTimeMask.jsp"%>
		<%@ include file="/common/yewgz.jsp"%>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					系统管理 - 清除机构下柜员IP限制
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					<form id="form1" name="form1" method="post" action="clearClerkIP.do?method=getOrgclerkIP">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr class="alignleft w100">
								<td class="class1_td w70">
									机构号
								</td>
								<td class="class1_td">
									<input type="text" id="netpointflag" name="netpointflag"
										class="inputField required"
										style="width:65px;" value="${orgCode}"/>
								</td>
								<td>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td>
									<button type="submit" style="width:60px"
										onmouseover="this.className='buttom2'"
										onmouseout="this.className='buttom1'" class="buttom1">
										<img src="images/search1.gif" width="13" height="13"
											align="absmiddle">
										查询
									</button>
								</td>
								<td>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td>
								<c:if test="${orgCode!=null}">
									<button type="button" style="width:140px"
										onmouseover="this.className='buttom2'"
										onmouseout="this.className='buttom1'" class="buttom1" onclick="javascript:window.location.href='clearClerkIP.do?method=clearAllOrgclerkIP&clerkCode=${orgCode}'">
										<img src="images/search1.gif" width="13" height="13"
											align="absmiddle">
										清除所有柜员IP锁定
									</button>
								</c:if>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
		<div id="tu"></div>
		<form id=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange">
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
								<th colspan="7" class="class1_thead th">
									柜员列表
								</th>
							</tr>
							<tr>
								<td class="class1_thead th w60">
									序号
								</td>
								<td class="class1_thead th">
									柜员号
								</td>
								<td class="class1_thead th">
									柜员名称
								</td>
								<td class="class1_thead th">
									所属机构
								</td>
								<td class="class1_thead th">
									柜员锁定IP
								</td>
								<td class="class1_thead th">
									操作
								</td>
							</tr>
						</thead>
						<logic:iterate id="clerk" indexId="ind" name="list" type="com.unitop.sysmgr.bo.Clerk">
							<tr>
								<td class="class1_td w60">
									<%=ind.intValue() + 1%>
								</td>
								<td class="class1_td w40">
									<bean:write name="clerk" property="code" />
								</td>
								<td class="class1_td center">
									<bean:write name="clerk" property="name" />
								</td>
								<td class="class1_td center">
										<bean:write name="clerk" property="orgname" />
								</td>
								<td class="class1_td center">
										<bean:write name="clerk" property="ip" />
								</td>
								<td class="class1_td alignright">
									<div align="center">
										<a href="javascript:if(confirm('你确定要清除所选择柜员的IP吗?'))window.location.href='clearClerkIP.do?method=clearOrgclerkIP&clerkCode=<bean:write name="clerk" property="code"/>'">清除</a>
									</div>
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
