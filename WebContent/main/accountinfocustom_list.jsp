<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
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
					�˻���ѯ - �˻���Ϣ���Ʋ�ѯ
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
									���
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
									�˻���Ϣ���Ʋ�ѯ�б�
								</th>
							</tr>
							<tr>
								<td class="class1_thead th w60 center">
									���
								</td>
								<!--
								 <td class="class1_thead th w60 center">
									�����ʶ
								</td>
								 -->
								<td class="class1_thead th w60 center">
									��������
								</td>
								<td class="class1_thead th w60 center">
									����ʱ��
								</td>
								<td class="class1_thead th w60 center">
									����ʱ��
								</td>
								<td class="class1_thead th w60 center">
									����״̬
								</td>
								<td class="class1_thead th w60 center">
									������Ϣ
								</td>
								<td class="class1_thead th w60 center">
									����ʱ��
								</td>
								<td class="class1_thead th w60 center">
									����
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
										������
									</logic:equal>
									<logic:equal name="base" property="statue" value="0">
										<b><font color="red">�������</font></b>
									</logic:equal>
									<logic:equal name="base" property="statue" value="2">
										����
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
										<a href="javascript:if(confirm('ȷ��Ҫɾ����'))window.location.href='accountcustom.do?method=delete&id=<bean:write name="base" property="id" />'">ɾ��</a>
									<logic:equal name="base" property="statue" value="0">
										<a href="accountcustom.do?method=download&id=<bean:write name='base' property='id'/>">����</a>
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
