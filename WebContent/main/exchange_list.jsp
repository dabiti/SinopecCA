<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/shield.js"></script>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					ϵͳ���� - ͨ��ͨ���������
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
									ͨ��ͨ�������б�
								</th>
							</tr>
							<tr>
								<td class="class1_thead th">
									ʡ������
								</td>
								<td class="class1_thead th">
									������
								</td>
								<td class="class1_thead th">
									ͨ��ͨ������
								</td>
								<td class="class1_thead th">
									����
								</td>
							</tr>
						</thead>
						<c:forEach items="${tctdList}" var="list">
							<form name="myform"
								action="exchange.do?method=change&orgCode=${list.code}&orgName=${list.name}"
								method="post" onSubmit="return confirm('ȷ���޸ģ�')">
								<tr>
									<td class="class1_td center">
										${list.name}
									</td>
									<td class="class1_td center">
										${list.code}
									</td>
									<td class="class1_td center">
										[ȫ��
										<input type="radio" name="tctd" value="ȫ��"
											<c:if test="${list.tctd=='ȫ��'}"> checked= "checked"</c:if>
											<c:if test="${clerk.wdFlag!=0}"> disabled=false </c:if> />
										] [һ������
										<input type="radio" name="tctd" value="һ������"
											<c:if test="${list.tctd=='һ������'}"> checked= "checked"</c:if> />
										] [��������
										<input type="radio" name="tctd" value="��������"
											<c:if test="${list.tctd=='��������'}"> checked= "checked"</c:if> />
										][��ͨ��
										<input type="radio" name="tctd" value="��ͨ��"
											<c:if test="${list.tctd=='��ͨ��'}"> checked= "checked"</c:if> />
										]
									</td>
									<td class="class1_td center">
									<unitop:HasPrivilegeForZignTag name="35000|1">
										<input type="submit" name="tj" value="��ͨ" />
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