<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳͳ</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<%@ include file="/common/validator.jsp"%>
		<%@ include file="/common/yewgz.jsp"%>
		<script language="javascript" type="text/javascript" src="js/date/time.js"></script>
		<script src="js/pagejs/valiateFormForImg.js" type="text/javascript"></script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;' onload="document.getElementById('tu').innerHTML='';">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					��־��ѯ - ����ƾ֤��־��ѯ
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					<html:form styleId="form1" method="post" action="orgVoucher.do?method=select">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr class="alignleft">
								<td class="alignleft class1_td"></td>
								<td class="class1_td alignright w90">
									�����ţ�
								</td>
								<td class="alignleft class1_td">
									<html:text styleId="orgCode" property="orgCode" styleClass="inputField netpointflag required" /><font color=red>*</font>
								</td>
								<td class="alignleft class1_td"></td>
								<td class="class1_td alignright w90">
									��ӡ���ڣ�
								</td>
								<td class="class1_td alignleft">
										<html:text property="date" styleId="begindate"  onclick="WdatePicker()" styleClass="inputField date_input required" />
								</td>
								<td class=" aligncenter w70">
									<button type="submit" style="width:60px"
										onmouseover="this.className='buttom2'"
										onmouseout="this.className='buttom1'" class="buttom1">
										<img src="images/search1.gif" width="13" height="13"
											align="middle">
										��ѯ
									</button>
								</td>
							</tr>
						</table>
					</html:form>
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
		<div id="tu"></div>
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
					<ec:table filterable="false" sortable="false" title="����ƾ֤��־��ѯ"
						showPagination="true" view="compact" items="list"
						var="logTglForm"  rowsDisplayed="${ec_yemjlts}"  
						action="orgVoucher.do?method=select" 
						imagePath="images/table/*.gif">
						<ec:exportXls fileName="log.xls" tooltip="Export Excel" />
						<ec:row>
							<ec:column property="id.account" title="�˺�" />
							<ec:column property="id.checknum" title="ƾ֤��" />
							<ec:column property="id.money" title="���(��λ:Ԫ)" />
							<ec:column property="chuprq" title="��Ʊ����" />
							<ec:column property="id.checkdate" title="��ӡ����" />
							<ec:column property="clerknum" title="��Ա����" />
							<ec:column property="clerkname" title="��Ա����" />
							<ec:column property="checkmode" title="��ӡ����" />
							<ec:column property="checkresult" title="��ӡ���" />
							<unitop:HasPrivilegeForSystemConfig name="pingzyxll">
							<ec:column property="���" title="���Ʊ��Ӱ��" >
							<a href="accountinfo.do?method=getPiaojImageList&zhangh=${logTglForm.id.account}&pingzbsm=${logTglForm.pingzbsm}" target="_blank">���</a>
							</ec:column>
							</unitop:HasPrivilegeForSystemConfig>
						</ec:row>
					</ec:table>
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