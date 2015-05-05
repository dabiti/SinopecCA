<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳͳ</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/shield.js"></script>
		<link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="js/themes/icon.css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="js/easyloader.js"></script>
	</head>
	<body>
		<%@ include file="/common/jicgn.jsp"%>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="50" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					����ά�� - ����ά������
				</td>
			</tr>
		</table>
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td class="orange error">
						<div class="error orange">
							${status}
						</div>
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
		<form action="danbwh.do?method=select" method="post">
			<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="w70">
									<button type="button" style="width: 60px"
										onmouseover="this.className='buttom2'"
										onmouseout="this.className='buttom1'" class="buttom1"
										onClick="location.href='danbwh.do?method=add&gongnid=${danb.gongnid}'">
										<img src="images/add.gif" width="13" height="13"
											align="absmiddle">
										���
									</button>
								</td>
								<td class="w70">
								</td>
							</tr>
						</table>

					</td>
				</tr>
				<tr>
					<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
				</tr>
				<tr>
					<td background="images/table_arrow_05.gif">&nbsp;</td>
					<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="0" class="class1_table">
						<thead style="background:white">
           					<div style="text-align:center;"><b>����ά�������б�</b></div>
								<tr>
									<td class="class1_thead th">
										����ID
									</td>
									<td class="class1_thead th">
										��������
									</td>
									<td class="class1_thead th">
										ά������
									</td>
									<td class="class1_thead th">
										�Ƿ񱣴�
									</td>
									<td class="class1_thead th">
										�Ƿ�༭
									</td>
									<td class="class1_thead th">
										�Ƿ�ɾ��
									</td>
									<td class="class1_thead th">
										����URL
									</td>
									<td class="class1_thead th">
										����
									</td>
								</tr>
							</thead>
							<c:forEach items="${list}" var="danb">
								<tr>
									<td class="class1_td center">
										${danb.gongnid}
									</td>
									<td class="class2_td">
										${danb.gongnmc}
									</td>
									<td class="class2_td">
										${danb.weihbm}
									</td>
									<td class="class1_td center">
										${danb.shifbc}
									</td>
									<td class="class1_td center">
										${danb.shifbj}
									</td>
									<td class="class1_td center">
										${danb.shifsc}
									</td>
									<td class="class2_td">
										${danb.gongnurl}
									</td>
									<td class="class1_td center">
										<a href="${danb.gongnurl}">Ԥ��</a>
										<a href="danbwh.do?method=find&gongnid=${danb.gongnid}">�޸�</a>
										<a href="javascript:if(confirm('��ȷ��Ҫɾ�������õĵ���ά��������?'))window.location.href='danbwh.do?method=delete&gongnid=${danb.gongnid}'">ɾ��</a>
										<a href="danbwhbd.do?method=list&gongnid=${danb.gongnid}">�ֵ�Ҫ������</a>
										<a href="danbwhGuanxb.do?method=list&gongnid=${danb.gongnid}">���ӹ�ϵ����</a>
										<a href="#" onclick="addJCGN('����ά��','${danb.gongnmc}','${danb.gongnurl}');">����������ܿ�</a>
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
		</form>
		</BR>
	</body>
</html>