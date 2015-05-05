<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
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
<html:form action="orgManage.do?method=importOrg&parentcode=${parentcode}" method="post" enctype="multipart/form-data">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					վ�����
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<logic:equal name="guanlms" value="1">
						<table width="90%">
							<tr>
								<td class="w70">
								
								<unitop:HasPrivilegeForZignTag name="30000|1">
							<c:if test="${wdflag lt lastWdflag}">
									<button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onClick="location.href='orgManage.do?method=add&parentcode=${parentcode}'"><img src="images/add.gif" width="13" height="13" align="absmiddle">���</button>
								</c:if>
								</unitop:HasPrivilegeForZignTag>							  </td>	
							</tr>
							<tr>
								<td width="50%"  class="W70"/>
								   <unitop:HasPrivilegeForZignTag name="30000|7">
									�Ƿ�����¼�վ�㣺	
									<select id="include" name="include">										
										<option value="0" >��</option>
										<option value="-1">��</option>
									</select>
									<button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onClick="location.href='orgManage.do?method=download&include='+document.getElementById('include').value+'&parentcode=${parentcode}'">
									<img src="images/add.gif" width="13" height="13" align="absmiddle"/>����</button>
								  </unitop:HasPrivilegeForZignTag>
								</td>
								<td width="50%"  class="W70">	
								  <unitop:HasPrivilegeForZignTag name="30000|6">
								   <html:file property="file" size="12"/>
								   <button type="submit" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1"><img src="images/add.gif" width="13" height="13" align="absmiddle">����</button>
								  </unitop:HasPrivilegeForZignTag>
								</td>
							</tr>
					  </table>
					</logic:equal>
				</td>
			</tr>
		</table>
 </html:form>
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
		<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">&nbsp;</td>
				<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="0" 
						class="class1_table">
						<thead class="class1_thead">
							<tr>
								<th colspan="7" class="class1_thead th">
									&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									վ���б� &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
									վ��������  ${org } &nbsp;&nbsp;�����¼�վ�������� ${all-1}
								</th>
							</tr>
							<tr>
								<td class="class1_thead th">
									���
								</td>
								<td class="class1_thead th">
									վ���
								</td>
								<td class="class1_thead th">
									վ������
								</td>
								<td class="class1_thead th">
									����
								</td>
							</tr>
						</thead>
						<logic:iterate id="org" indexId="ind" name="list" type="com.unitop.sysmgr.bo.Org">
							<tr>
								<td class="class1_td">
									<%=ind.intValue() + 1%>
								</td>
								<td class="class1_td">
									<bean:write name="org" property="code" />
								</td>
								<td class="class1_td">
									<div align="center">
										<bean:write name="org" property="name" />
									</div>
								</td>
								<td class="class1_td aligncenter">
										<c:if test="${guanlms==1||(org.parentCode==rootcode||clerk.orgcode!=rootcode)}">
										<unitop:HasPrivilegeForZignTag name="30000|2">
											<a href="javascript:if(confirm('ɾ��վ��ͬʱ��ɾ��վ�������˻���ӡ����ӡ����ϡ���Ա��ƾ֤��������Ϣ���Ƿ�ȷ��ɾ��?'))window.location.href='orgManage.do?method=delete&code=<bean:write name="org" property="code" />'">ɾ��</a>
										</unitop:HasPrivilegeForZignTag>
										<unitop:HasPrivilegeForZignTag name="30000|3">
											<a href="orgManage.do?method=forwardupdate&code=<bean:write name="org" property="code" />">�޸�</a>
										</unitop:HasPrivilegeForZignTag>
										<unitop:HasPrivilegeForZignTag name="30000|4">
											<a href="orgManage.do?method=changerelation&code=<bean:write name="org" property="code" />">�ı������ϵ</a>
										</unitop:HasPrivilegeForZignTag>
											<c:if test="${(lastWdflag) eq org.wdflag}">
<%--											<logic:equal value='${lastWdflag}' name="org" property="wdflag">--%>
												<unitop:HasPrivilegeForZignTag name="30000|5">
													<a href="orgManage.do?method=forwardmerge&code=<bean:write name="org" property="code" />">����</a>
												</unitop:HasPrivilegeForZignTag>
<%--											</logic:equal>--%>
											</c:if>
										</c:if>
										&nbsp;
								</td>
							</tr>
						</logic:iterate>
					</table>
				</td>
				<td background="images/table_arrow_07.gif">&nbsp;
				</td>
			</tr>
			<tr>
				<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
			</tr>
		</table>
	</body>
</html>