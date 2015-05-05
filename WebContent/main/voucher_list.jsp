<%@page language="java" contentType="text/html;charset=gbk" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
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
					ϵͳ���� - ƾ֤��������
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
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
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="w70">
								<unitop:HasPrivilegeForZignTag name="34000|1">
									<button type="button" style="width:60px"
										onmouseover="this.className='buttom2'"
										onmouseout="this.className='buttom1'" class="buttom1"
										onClick="location.href='voucherManage.do?method=add&type=add&netpointflag=${netpointflag}'">
										<img src="images/add.gif" width="13" height="13"
											align="absmiddle">
										���
									</button>
								</unitop:HasPrivilegeForZignTag>
								</td>
								<td class="w100"></td>
							</tr>
						</table>
				</td>
			</tr>
		</table>
		<br>
		<table width="97%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="up-left"/><td class="up-middle"/><td class="up-right"/></td></td></td>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">
					&nbsp;
				</td>
				<td>
		<table width="100%"  border="0" style="table-layout:fixed;word-wrap:break-word;" cellspacing="1" cellpadding="0" class="class1_table">
       			 <thead style="background:white">
           			<div style="text-align:center;"><b>ƾ֤���������б�</b></div>
							<tr>
								<td class="class1_thead th">
									���
								</td>
								<td class="class1_thead th">
									ƾ֤��ʶ
								</td>
								<td class="class1_thead th">
									ƾ֤����
								</td>
								<td class="class1_thead th">
									ƾ֤��ǰ׺
								</td>
								<td class="class1_thead th">
									ÿ������
								</td>
								<td class="class1_thead th">
									�Ƿ��ؿ�
								</td>
								<td class="class1_thead th">
									�Ƿ�����
								</td>
								<td class="class1_thead th">
									��ע˵��
								</td>
								<td class="class1_thead th">
									����
								</td>
							</tr>
						</thead>
						<logic:iterate id="voucher" name="list" indexId="ind" type="com.unitop.sysmgr.bo.Voucher">
							<tr>
								<td class="class1_td w140">
									<%=ind+1%>
								</td>
								<td class="class1_td w140">
										&nbsp;<bean:write name="voucher" property="pingzbs" />
								</td>
								<td class="class1_td w140">
										&nbsp;<bean:write name="voucher" property="pingzmc" />
								</td>
								<td class="class1_td w140">
										&nbsp;<bean:write name="voucher" property="pingzhqz" />
								</td>
								<td class="class1_td w140">
										&nbsp;<bean:write name="voucher" property="meibzs" />
								</td>
								<td class="class1_td w140">
										<c:if test="${voucher.shifzk=='0'}">
											��
										</c:if>
										<c:if test="${voucher.shifzk=='1'}">
											��
										</c:if>
								</td>
								<td class="class1_td w140">
										<c:if test="${voucher.shifqy=='0'}">
											��
										</c:if>
										<c:if test="${voucher.shifqy=='1'}">
											��
										</c:if>
								</td>
								<td class="class1_td w140">
										&nbsp;<bean:write name="voucher" property="yanyjb" />
								</td>
								<td class="class1_td w140">
									<unitop:HasPrivilegeForZignTag name="34000|3">
										<a href="javascript:if(confirm('ȷ��Ҫɾ����'))window.location.href='voucherManage.do?method=delete&voucher=<bean:write name='voucher' property='pingzbs'/>'">ɾ��</a>
									</unitop:HasPrivilegeForZignTag>
									<unitop:HasPrivilegeForZignTag name="34000|2">
										<a href="voucherManage.do?method=update&type=update&voucher=<bean:write name='voucher' property='pingzbs' />">�޸�</a>
									</unitop:HasPrivilegeForZignTag>
									   <!--<a href="voucherManage.do?method=voucherSetLink&type=update&voucher=<bean:write name='voucher' property='pingzbs' />">ҵ������</a>-->
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
				<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/></td></td></td>
			</tr>
		</table>
	</body>
</html>