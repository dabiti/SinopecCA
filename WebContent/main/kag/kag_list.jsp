<%@ page contentType="text/html; charset=GBK" language="java"  isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
		<script language="javascript">
		function delkag(kagid,kagzt){
				if(confirm('��ȷ��Ҫɾ��������?')){
					window.location.href='kag.do?method=delete&kagid='+kagid;
				}
		}
		function gotozero(){
			var open = 0;
			var close = 1;
			var ret = 0;
			ret = TEST_OCX.tank_gotozero(1, open, open, open, open, open, open, open, open, close, close);
			if(ret == -1)
				{
					alert(TEST_OCX.getlasterrortext());
				}
				else if(ret == 0)
				{
					alert("����ִ��ʧ��,û�������������!");
				}
				else{
					alert("�����ʼ�����!");
				}
		}	
		function gotozero(kagid)
		{
			var open = 0;
			var close = 1;
			var ret = 0;
			ret = TEST_OCX.tank_gotozero(kagid, open, open, open, open, open, open, open, open, close, close);
			if(ret == -1)
			{
				alert(TEST_OCX.getlasterrortext());
			}else if(ret == 0){
				alert("����ִ��ʧ��,û�������������!");
			}else{
				alert("�����ʼ�����!");
			}
		}				
		</script>
		<link href="style/right.css" rel="stylesheet" type="text/css">
	</head>
	<body >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
				 	ӡ��������-����ά��
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td  width="50%" class="w70">
								<c:if test="${ifSameOrg=='0'}">
										<button type="button" style="width:60px"
											onmouseover="this.className='buttom2'"
											onmouseout="this.className='buttom1'" class="buttom1"
											onClick="location.href='kag.do?method=add'">
											<img src="images/add.gif" width="13" height="13"
												align="absmiddle">
											���
										</button>
								</c:if>
								</td>	
						
								<td  width="50%"  class="W70">	
							 	 <button type="submit" style="width:150px"
											onmouseover="this.className='buttom2'"
											onmouseout="this.className='buttom1'" class="buttom1"
											onClick="location.href='kag.do?method=getKagOrgs'">
											<img src="images/add.gif" width="13" height="13" align="absmiddle">��������ѯ
								 </button>	
							</td>
						</tr>
					
					</table>
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
								<th colspan="10" class="class1_thead th">
									�����б�
								</th>
							</tr>
							<tr>
								<td class="class1_thead th w60">
									���
								</td>
								<td class="class1_thead th w60">
									����ID
								</td>
								<td class="class1_thead th">
									��������
								</td>
								<td class="class1_thead th">
									������
								</td>
								<td class="class1_thead th">
									����IP
								</td>
								<td class="class1_thead th">
									������� 
								</td>
								<td class="class1_thead th">
									������
								</td>
								<td class="class1_thead th">
									����ռ�
								</td>
								<td class="class1_thead th">
									�ܿռ�
								</td>
								<td class="class1_thead th">
									����
								</td>
							</tr>
						</thead>
						<logic:iterate id="kag" indexId="ind" name="kaglist"
							type="com.unitop.sysmgr.bo.Kag">
							<tr>
								<td class="class1_td w60">
									<%=ind.intValue() + 1%>
								</td>
								<td class="class1_td w40">
								    <bean:write name="kag" property="kagid" />
								</td> 
								<td class="class1_td center">
									<bean:write name="kag" property="kagmc" />
								</td>
								<td class="class1_td center">
									<bean:write name="kag" property="jigh" />
								</td>
								<td class="class1_td center">
									<bean:write name="kag" property="kagip" />
								</td>
								<td class="class1_td center">
									<bean:write name="kag" property="cengs" />
								</td>
								<td class="class1_td center">
									<bean:write name="kag" property="chouts" />
								</td>
								<td class="class1_td center">
									<bean:write name="kag" property="choutkj" />
								</td>
								<td class="class1_td center">
									<bean:write name="kag" property="zongkj" />
								</td>
								<td class="class1_td alignright">
									<c:if test="${ifSameOrg=='0'}">
										<div align="center">
			  						   	<a href="javascript:delkag('<bean:write name="kag" property="kagid"/>','<bean:write name="kag" property="kagzt"/>');">
												ɾ��</a>��
										 <a href="kag.do?method=countKagSpace&kagid=<bean:write name="kag" property="kagid" />">����ռ��ѯ</a>
										<a href="javascript:gotozero('${kag.kagid}');">�������</a>��
										</div>
									</c:if>
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
			<tr><td>
			 <div>
                     <OBJECT ID="TEST_OCX" WIDTH="0" HEIGHT="0" CLASSID="CLSID:65C11E08-C534-4649-9AE9-C3424BB5C336" >
                     <PARAM name="LogLevel" value="0">
                     </OBJECT>
                </div>
			</td></tr>
		</table>
	</body>
</html>