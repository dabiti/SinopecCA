<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					Ureport���� - �������
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
								<th colspan="7" class="class1_thead th">
									���Ʒ����б�
								</th>
							</tr>
							<tr>
								<td class="class1_thead th">
									ѡ��
								</td>
								<td class="class1_thead th">
									�����ʶ
								</td>
								<td class="class1_thead th">
									��������
								</td>
								<td class="class1_thead th">
									�������
								</td>
								<td class="class1_thead th">
									�Ƿ����
								</td>
							</tr>
						</thead>
						<c:forEach items="${list}" var="list">
							<tr>
								<td class="class1_td center">
									<a
										href="reportService.do?method=doReport&baobbs=${list['BAOBBS']}">����</a>
								</td>
								<td class="class1_td center">
									${list['BAOBBS']}
								</td>
								<td class="class1_td center">
									${list['BAOBMC']}
								</td>
								<td class="class1_td center">
									${list['BAOBBT']}
								</td>
								<td class="class1_td center">
									${list['SHIFKY']}
								</td>
							</tr>
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
