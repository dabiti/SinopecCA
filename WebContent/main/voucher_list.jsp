<%@page language="java" contentType="text/html;charset=gbk" isELIgnored="false"%>
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
					系统管理 - 凭证参数设置
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
										添加
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
           			<div style="text-align:center;"><b>凭证参数设置列表</b></div>
							<tr>
								<td class="class1_thead th">
									序号
								</td>
								<td class="class1_thead th">
									凭证标识
								</td>
								<td class="class1_thead th">
									凭证名称
								</td>
								<td class="class1_thead th">
									凭证号前缀
								</td>
								<td class="class1_thead th">
									每本张数
								</td>
								<td class="class1_thead th">
									是否重控
								</td>
								<td class="class1_thead th">
									是否启用
								</td>
								<td class="class1_thead th">
									备注说明
								</td>
								<td class="class1_thead th">
									操作
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
											否
										</c:if>
										<c:if test="${voucher.shifzk=='1'}">
											是
										</c:if>
								</td>
								<td class="class1_td w140">
										<c:if test="${voucher.shifqy=='0'}">
											否
										</c:if>
										<c:if test="${voucher.shifqy=='1'}">
											是
										</c:if>
								</td>
								<td class="class1_td w140">
										&nbsp;<bean:write name="voucher" property="yanyjb" />
								</td>
								<td class="class1_td w140">
									<unitop:HasPrivilegeForZignTag name="34000|3">
										<a href="javascript:if(confirm('确定要删除？'))window.location.href='voucherManage.do?method=delete&voucher=<bean:write name='voucher' property='pingzbs'/>'">删除</a>
									</unitop:HasPrivilegeForZignTag>
									<unitop:HasPrivilegeForZignTag name="34000|2">
										<a href="voucherManage.do?method=update&type=update&voucher=<bean:write name='voucher' property='pingzbs' />">修改</a>
									</unitop:HasPrivilegeForZignTag>
									   <!--<a href="voucherManage.do?method=voucherSetLink&type=update&voucher=<bean:write name='voucher' property='pingzbs' />">业务设置</a>-->
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