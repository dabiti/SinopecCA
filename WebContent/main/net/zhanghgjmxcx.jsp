<%@ page contentType="text/html; charset=GBK" language="java"  isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script src="js/pagejs/zhanghjdmxcx.js" type="text/javascript"></script>
		<script src="js/pagejs/ocx.js" type="text/javascript"></script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<form id="form1" name="form1" method="post">
		</form>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					�˻���ѯ - �˻��߼���ѯ��ϸ
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		<br>
		<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">
					&nbsp;
				</td>
				<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="0" style="table-layout:fixed;"
						class="class1_table">
						<thead class="class1_thead">
							<tr>
								<th colspan="4" class="class1_thead th">
									�˻��߼���ѯ��ϸ 
								</th>
							</tr>
						</thead>
						<!--<c:if test='${Zhanghb.zhuzh != null&&Zhanghb.zhuzh!=""}' >
							<tr>
								<td class="class1_td alignright w100">���˺�</td>
								<td class="class1_td alignleft">${Zhanghb.zhuzh}</td>
								<td class="class1_td alignright w100">&nbsp;</td>
								<td class="class1_td alignleft">&nbsp;</td>
							</tr>
						</c:if>-->
						<tr>
							<td width="11%"  class="class1_td alignright">
								�˺�
							</td>
							<td  width="46%"  class="class1_td alignleft">
								${Zhanghb.zhangh}&nbsp;
							</td>
							
							<td class="class1_td alignright ">
								����
							</td>
							<td class="class1_td alignleft " style="word-wrap:break-word;">
								${Zhanghb.hum}&nbsp;
							</td>
							
						</tr>
							
							<tr>
								<td class="class1_td alignright">
									�˻�Ԥ��ӡ��
								</td>
								<td class="class1_td alignleft ">
									<a href='javascript:try{obj.ShowOcxForm("{\"ocxid\":\"3\",\"guiyxx\":${jsonClerkrStr},\"ocxparam\":{\"zhangh\":\"${Zhanghb.zhangh}\"}}");}catch(e){alert("ϵͳ��⣺����û�а�װ��ӡ���!");};'>���</a>
								</td>
								<td class="class1_td alignright ">
<%--							<unitop:HasPrivilegeForZignTag name="00012|1">--%>
								ӡ�������
<%--							</unitop:HasPrivilegeForZignTag>--%>
							</td>
							<td class="class1_td alignleft ">
<%--							<unitop:HasPrivilegeForZignTag name="00012|1">--%>
								${Zhanghb.yinjkbh}&nbsp;
<%--							</unitop:HasPrivilegeForZignTag>--%>
							</td>
					<!--  		<td class="class1_td alignright ">
								 ӡ��Ӱ��
							</td>
							<td class="class1_td alignleft ">
								<c:if test='${Zhanghb.zhangh!=null}' >
									<a href="accountinfo.do?method=getAcccountYjImageList&account=${Zhanghb.zhangh}" target="_blank">���</a>
								</c:if>
							</td>-->	
							
							</tr>
						<unitop:HasPrivilegeForZignTag name="00012|2">
							</unitop:HasPrivilegeForZignTag>
						<tr>
							<unitop:HasPrivilegeForZignTag name="00012|3">
							<td class="class1_td alignright  ">
								��������˻�����
							</td>
							</unitop:HasPrivilegeForZignTag>
							<unitop:HasPrivilegeForZignTag name="00012|3">
							<td class="class1_td alignleft ">
							<c:if test='${Zhanghb.zhangh!=null}' >
								<img src="images/table/xls.gif" onclick="excel('${Zhanghb.zhangh}')">
							</c:if>
							</td>
							</unitop:HasPrivilegeForZignTag>
							<unitop:HasPrivilegeForZignTag name="00012|1">
							<td class="class1_td alignright ">
								 ӡ����Ӱ��
							</td>
							</unitop:HasPrivilegeForZignTag>
							<unitop:HasPrivilegeForZignTag name="00012|1">
							<td class="class1_td alignleft ">
								<c:if test='${Zhanghb.zhangh!=null}' >
									<a href="accountinfo.do?method=getAcccountYjkImageList&account=${Zhanghb.zhangh}" target="_blank">���</a>
								</c:if>
							</td>
							</unitop:HasPrivilegeForZignTag>
						</tr>
						<tr>
							<td class="class1_td alignright ">
								���������
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.jigh}&nbsp;
							</td>
							<td class="class1_td alignright ">
								�˻�����
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.zhanghxz}&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright ">
								ͨ�ұ�־
							</td>
							<td class="class1_td alignleft " style="word-wrap:break-word;">
								${Zhanghb.tongctd}&nbsp;
							</td>
							<td class="class1_td alignright ">
								��������
							</td>

							<td class="class1_td alignleft ">
								${Zhanghb.kaihrq}&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright ">
								�˻�״̬
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.zhanghzt}&nbsp;
							</td>
							<td class="class1_td alignright ">
								�˻����״̬
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.zhanghshzt}&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright ">
								�Ƿ���ӡ��
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.youwyj}&nbsp;
							</td>
							<td class="class1_td alignright ">
								ӡ�����״̬
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.yinjshzt}	&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright ">
								�Ƿ���ӡ�����
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.youwzh}&nbsp;
							</td>
							<td class="class1_td alignright ">
								������״̬
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.zuhshzt}	&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright ">
								��ϵ��1
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.lianxr}&nbsp;
							</td>
							<td class="class1_td alignright ">
								��ϵ��1�绰
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.dianh}	
								&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright ">
								��ϵ��2
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.lianxr2}&nbsp;
							</td>
							<td class="class1_td alignright ">
								��ϵ��2�绰
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.dianh2}	
								&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright ">
								������
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.fuzr}&nbsp;
							</td>
							<td class="class1_td alignright ">
								�����˵绰
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.fuzrdh}	
								&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright ">
								������2
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.fuzr2}&nbsp;
							</td>
							<td class="class1_td alignright ">
								������2�绰
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.fuzrdh2}	
								&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright ">
								�Ƿ���Ҫ���
							</td>
							<td class="class1_td alignleft " style="word-wrap:break-word;">
							<c:if test="${Zhanghb.zhanghxz !='�ڲ���'&& Zhanghb.zhanghxz !='�������ʻ�'}">
								${Zhanghb.shifdh=='0'?'��':'��'}
								</c:if>
								&nbsp;
							</td>
							<td class="class1_td alignright ">
								�����˺�
							</td>
							<td class="class1_td alignleft " style="word-wrap:break-word;">
								${Zhanghb.zhuzh}&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright ">
								��ע
							</td>
							<td class="class1_td alignleft " style="word-wrap:break-word;">
								${Zhanghb.beiz}&nbsp;
							 </td>
							<td class="class1_td alignright ">
								&nbsp;
							</td>
							<td class="class1_td alignleft " style="word-wrap:break-word;">
								&nbsp;
							</td>
						</tr>
<%--						<tr>
							<td class="class1_td alignright ">
								��ע
							</td>
							<td class="class1_td alignleft " style="word-wrap:break-word;">
								${Zhanghb.beiz}&nbsp;
							</td>
							<td class="class1_td alignright ">
							</td>
							<td class="class1_td alignleft ">
							</td>
						</tr>--%>
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
		<div class="funbutton">
			<button type="button" style="width:60px" onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1"
				onclick="history.back()">
				<img src="images/back1.gif" width="11" height="11" align="absmiddle">
				����
			</button>
		</div>
	 	<script language="JavaScript" FOR="obj" EVENT="SendEvent(sendMessage)">
			var rMessage = SendSesssion("uniDBInterface.jsp",sendMessage);
    		return rMessage;
	    </script>
	    <OBJECT ID="obj" CLASSID="clsid:87100b1f-19fa-4266-a03e-0536ffa3c8af" style="display:none"/>
	</body>
</html>