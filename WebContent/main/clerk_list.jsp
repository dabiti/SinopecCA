<%@ page contentType="text/html; charset=GBK" language="java"  isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<script language="javascript">
		//alert('${errorpasswordtimes}');
			function delClerk(clerkCode){
				$.post("clerkManage.do?method=validateClerks",{denglguiy:'${clerk.code}',caozguiy:clerkCode},function(data,textStatus){
					if (textStatus = "success") 
					{
						if("0"==data)
						{
							alert('û��Ȩ�޲����˹�Ա![ԭ��:�˹�Ա��ɫ�ȼ��ȵ�¼��Ա��ɫ�ȼ���]');
						}else{
							if(clerkCode=='${clerk.code}')
							{
								if(confirm('��Ҫɾ����ԱΪ����ǰ������¼��Ա��,��ȷ��Ҫɾ����?'))
								{
									window.location.href='clerkManage.do?method=delete&code='+clerkCode;
								}
								return ;
							}
							
							if(confirm('��ȷ��Ҫɾ����ѡ��Ĺ�Ա��?'))
							{
								window.location.href='clerkManage.do?method=delete&code='+clerkCode;
							}
						}
					}
				}, "text");
			}
			function doClerk(caozguiy)
			{
				var denglguiy = '${clerk.code}';//��¼��Ա
				$.post("clerkManage.do?method=validateClerks",{denglguiy:denglguiy,caozguiy:caozguiy},function(data,textStatus){
					if (textStatus = "success") 
					{
						if("1"==data)
						{
							window.location.href='clerkManage.do?method=forwardupdate&code='+caozguiy;
						}else{
							alert('û��Ȩ�޲����˹�Ա![ԭ��:�˹�Ա��ɫ�ȼ��ȵ�¼��Ա��ɫ�ȼ���]');
							return false;
						}
					}
				}, "text");
			}
		</script>
	</head>
	<body>
	 <html:form action="clerkManage.do?method=importClerk&orgcode=${orgcode}"  method="POST" enctype="multipart/form-data">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					ϵͳ���� - ��Ա����
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					<table width="90%">
						<tr>
							<td class="w70">
								<c:if test="${addflag=='1'}">
									<unitop:HasPrivilegeForZignTag name="31000|1">
										<button type="button" style="width:60px"
											onmouseover="this.className='buttom2'"
											onmouseout="this.className='buttom1'" class="buttom1"
											onClick="location.href='clerkManage.do?method=forwardCreate&orgcode=${orgcode}'">
											<img src="images/add.gif" align="absmiddle">���</button>
									</unitop:HasPrivilegeForZignTag>
								</c:if>
							</td>
						</tr>
						<tr>
							<td width="50%" class="W70">	
								<unitop:HasPrivilegeForZignTag name="31000|5">	
								�Ƿ�����¼�������	
								<select id="include" name="include">										
									<option value="0" >��</option>
									<option value="-1">��</option>
								</select>											
								<button type="button" style="width:60px"
									onmouseover="this.className='buttom2'"
									onmouseout="this.className='buttom1'" class="buttom1"
									onClick="location.href='clerkManage.do?method=download&include='+document.getElementById('include').value+'&orgcode=<bean:write name="clerkForm" property="orgcode"/>'">
									<img src="images/add.gif" width="13" height="13" align="absmiddle">����</button>	
								</unitop:HasPrivilegeForZignTag>				
							</td>	
							<td width="50%" class="W70">		
								<unitop:HasPrivilegeForZignTag name="31000|4">							
								    <html:file property="file" size="12"/>
								   	 <button type="submit" style="width:60px"
											onmouseover="this.className='buttom2'"
											onmouseout="this.className='buttom1'" class="buttom1">
											<img src="images/add.gif" width="13" height="13" align="absmiddle">����</button>
								</unitop:HasPrivilegeForZignTag>					
							</td>													
						</tr>
					</table>
				</td>
			</tr>
		</table>
</html:form>
		<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
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
		<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">
					&nbsp;
				</td>
				<td>
					<table width="100%"  border="0" style="table-layout:fixed;word-wrap:break-word;" cellspacing="1" cellpadding="0" class="class1_table">
       				 <thead style="background:white">
           			<div style="text-align:center;"><b>��Ա�б�</b></div>
							<tr>
								<td class="class1_thead th w60">
									���
								</td>
								<td class="class1_thead th">
									��Ա���
								</td>
								<td class="class1_thead th">
									��Ա����
								</td>
								<td class="class1_thead th">
									��Ա����
								</td>
								<td class="class1_thead th">
									��ɫ 
								</td>
								<td class="class1_thead th">
									����
								</td>
							</tr>
						</thead>
						<c:forEach items="${list}" var="clerk"   varStatus="a">
						<c:if test="${clerk.leixbs eq '1'}">
							<tr>
								<td class="class1_td center">
									&nbsp;${a.index +1}
								</td>
								<td class="class1_td center">
									&nbsp;${clerk.code }
								</td>
								<td class="class1_td center">
									&nbsp;${clerk.name }
								</td>
								<td class="class1_td center">
									���Ĺ�Ա
								</td>
								<td class="class1_td center">
									&nbsp;${clerk.postName }
								</td>
								<td class="class1_td alignceneter">
										<unitop:HasPrivilegeForZignTag name="31000|2">
											<a href="javascript:doClerk('${clerk.code }');">�޸�</a>
									    </unitop:HasPrivilegeForZignTag>
									    <unitop:HasPrivilegeForZignTag name="31000|3">
											<a href="javascript:delClerk('${clerk.code }');">ɾ��</a>
										</unitop:HasPrivilegeForZignTag>
										<c:if test="${clerk.errortime >= errorpasswordtimes}">
											<a href="javascript:if(confirm('��ȷ��Ҫ������ѡ��Ĺ�Ա��?'))window.location.href='clerkManage.do?method=unlock&code=<bean:write name="clerk" property="code" />'">����</a>
										</c:if>
								</td>
							</tr>
							</c:if>
							<c:if test="${clerk.leixbs eq '0'}">
								<unitop:HasPrivilegeForZignTag name="31000|YYGY_001">
							<tr>
								<td class="class1_td center">
									&nbsp;${a.index +1}
								</td>
								<td class="class1_td center">
									&nbsp;${clerk.code }
								</td>
								<td class="class1_td center">
									&nbsp;${clerk.name }
								</td>
								<td class="class1_td center">
									��ӡ��Ա
								</td>
								<td class="class1_td center">
									&nbsp;${clerk.postName }
								</td>
								<td class="class1_td alignceneter">
										<unitop:HasPrivilegeForZignTag name="31000|2">
											<a href="javascript:doClerk('${clerk.code }');">�޸�</a>
									    </unitop:HasPrivilegeForZignTag>
									    <unitop:HasPrivilegeForZignTag name="31000|3">
											<a href="javascript:delClerk('${clerk.code }');">ɾ��</a>
										</unitop:HasPrivilegeForZignTag>
										<c:if test="${clerk.errortime >= errorpasswordtimes}">
											<a href="javascript:if(confirm('��ȷ��Ҫ������ѡ��Ĺ�Ա��?'))window.location.href='clerkManage.do?method=unlock&code=<bean:write name="clerk" property="code" />'">����</a>
										</c:if>
								</td>
							</tr>
							</unitop:HasPrivilegeForZignTag>
							</c:if>
						</c:forEach>
						
						
					</table>
				</td>
				<td background="images/table_arrow_07.gif">&nbsp;</td>
			</tr>
			<tr>
				<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
			</tr>
		</table>
	</body>
</html>