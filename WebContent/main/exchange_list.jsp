<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/shield.js"></script>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					系统管理 - 通存通兑区域管理
				</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="0">
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
									通存通兑区域列表
								</th>
							</tr>
							<tr>
								<td class="class1_thead th">
									省行名称
								</td>
								<td class="class1_thead th">
									机构号
								</td>
								<td class="class1_thead th">
									通存通兑区域
								</td>
								<td class="class1_thead th">
									操作
								</td>
							</tr>
						</thead>
						<c:forEach items="${tctdList}" var="list">
							<form name="myform"
								action="exchange.do?method=change&orgCode=${list.code}&orgName=${list.name}"
								method="post" onSubmit="return confirm('确认修改？')">
								<tr>
									<td class="class1_td center">
										${list.name}
									</td>
									<td class="class1_td center">
										${list.code}
									</td>
									<td class="class1_td center">
										[全国
										<input type="radio" name="tctd" value="全国"
											<c:if test="${list.tctd=='全国'}"> checked= "checked"</c:if>
											<c:if test="${clerk.wdFlag!=0}"> disabled=false </c:if> />
										] [一级分行
										<input type="radio" name="tctd" value="一级分行"
											<c:if test="${list.tctd=='一级分行'}"> checked= "checked"</c:if> />
										] [二级分行
										<input type="radio" name="tctd" value="二级分行"
											<c:if test="${list.tctd=='二级分行'}"> checked= "checked"</c:if> />
										][不通兑
										<input type="radio" name="tctd" value="不通兑"
											<c:if test="${list.tctd=='不通兑'}"> checked= "checked"</c:if> />
										]
									</td>
									<td class="class1_td center">
									<unitop:HasPrivilegeForZignTag name="35000|1">
										<input type="submit" name="tj" value="开通" />
									</unitop:HasPrivilegeForZignTag>
									</td>
								</tr>
							</form>
						</c:forEach>
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
