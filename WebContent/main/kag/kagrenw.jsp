<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false"%>
<%@page import="java.util.*"%>
<%@page import="com.unitop.sysmgr.bo.KagRenw"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript" src="js/date/time.js"></script>
		<script type="text/javascript" src="js/special-business.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script language="javascript">
		$(document).ready(function() {
			 $("#KagRenwForm").validate({
			   onsubmit:true,
			   onfocusout:true,
			   onkeyup :true,
			   errorLabelContainer:"#error div.error",
			   wrapper:"li"
			   });
			   });
			//�򿪿���
			function openKag(renwbs,yinjks){
				//�����˻�ӡ�������ڿ�����λ�ã�����Ҫ����ռ䡣
				var zuob=document.getElementById("zuob_"+renwbs).value;
				var math = Math.random();
				$.post("handleKagRenw.do?method=openKag", {math:math,renwbs:renwbs,yinjks:yinjks,zuob:zuob}, function (data, textStatus) {
					if (textStatus == "success") {
						 data = data.replace(/(^\s*)|(\s*$)/g, "");//ȥ�����ɼ��ַ�
						 document.getElementById("zuob_"+renwbs).value=data;
						 if(data=="1"){
								alert("["+zuob+"]��������ʹ��!");
						 }else{
						    var zuobArr = data.split(",");
						     var result =""; 
							// result = open(zuobArr[0],zuobArr[1],zuobArr[2])//�򿪿���Ӳ��
							if(result==""){
								document.getElementById("openkag_"+renwbs).disabled="disabled";
								document.getElementById("closekag_"+renwbs).disabled="";
							}
						}
					}
				}, "text");
			}	
			//�رտ���
			function closeKag(obj,renwbs,yinjks){
				var zuob = document.getElementById("zuob_"+renwbs).value;
				/*
				var zuoArr = zuob.split(",");
				var ceng=zuoArr[0];
				var choutwz=zuoArr[1]
				close(choutwz,ceng);
				*/
				var math = Math.random();
				$.post("handleKagRenw.do?method=closeKag", {math:math,renwbs:renwbs,yinjks:yinjks,zuob:zuob}, function (data, textStatus) {
					if (textStatus = "success") {
						data = data.replace(/(^\s*)|(\s*$)/g, "");//ȥ�����ɼ��ַ�
						if(data==""){
							obj.parentNode.parentNode.removeNode(true); 
						}
					}
				}, "text");
			}
			//���򿪳���ӿ�
			function open(kagid,ceng,choutwz){
				var open = 0;//�����������û��ǲ����ã�
				var close = 1;
				//��ת���ӣ�choutwz����ת���ڼ��У�
				var openretrotate = TEST_OCX.tank_rotate(choutwz,kagid, open, open, open, open, open, open, open, open, close,close);
				var openrettank_out = 0;
				//�Ƴ����ӣ�ceng�Ƴ��ڼ���
			    openrettank_out = TEST_OCX.tank_out(ceng,kagid);
				if(openrettank_out == -1)
				{
					alert(TEST_OCX.getlasterrortext());
					return "-1"
				}else if(openrettank_out == 0)
				{
					alert("����ִ��ʧ��,û�������������!");
					return "0"
				}else{
					alert("���!");
					return "";
				}
			}
			//���رճ���ӿ�
		function close(choutwz,ceng){
			var openrettank_out = 0;
			var openrettank_out = TEST_OCX.tank_in(choutwz,ceng);
			if(openrettank_out == -1)
			{
				alert(TEST_OCX.getlasterrortext());
			}
			else if(openrettank_out == 0)
			{
				alert("����ִ��ʧ��,û�������������!");
			}else{
				alert("���!");
			}		

		}		
		function deltask(obj,renwbs){
			var math = Math.random();
			$.post("handleKagRenw.do?method=deleteTask", {math:math,renwbs:renwbs}, function (data, textStatus) {
				if (textStatus == "success") {
					   data = data.replace(/(^\s*)|(\s*$)/g, "");//ȥ�����ɼ��ַ�
					   if(data!=""){
						  alert("ɾ����������쳣");
					   }else{
						  obj.parentNode.parentNode.removeNode(true); 
					   }
					}
				}, "text");
		}
		</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>	
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td  background="images/main_place_bg.gif">
					ӡ��������-ʵ���ȡ��				
				</td>
			</tr>
			<tr>
			<td>
					&nbsp;
				</td>
				<td>
					<html:form styleId="KagRenwForm" action="/handleKagRenw.do?method=searchTask" method="post">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="class1_td w70">
									�˺�
								</td>
								<td class="class1_td alignleft">
									<html:text property="zhangh" styleId="zhangh" styleClass="inputField" />
								</td>
								
                                  
								<td class="class1_td alignleft">
									&nbsp;
								</td>
								<td class="class1_td alignleft">
									&nbsp;
								</td>
								<td class="w70">
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
					<td bgcolor="#FFCEFF" class="orange error">
						<div style="background-color:#FFCEFF;" class="error orange"></div>
					</td>
				</tr>
				<logic:messagesPresent>
					<tr>
						<td bgcolor="#FFCEFF" class="orange">
							<div id=abc class="abc"><html:errors /></div>
						</td>
					</tr>
				</logic:messagesPresent>
			</table>
		</form>
		<table width="97%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="15" height="14">
					<img src="images/table_arrow_01.gif" width="15" height="14">
				</td>
				<td background="images/table_arrow_08.gif" style="height:14px;"></td>
				<td width="14" height="14">
					<img src="images/table_arrow_02.gif" width="14" height="14">
				</td>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">&nbsp;
			  </td>
				<td>
					<ec:table
						retrieveRowsCallback="org.extremecomponents.table.callback.LimitCallback"
						filterRowsCallback="org.extremecomponents.table.callback.LimitCallback"
						sortRowsCallback="org.extremecomponents.table.callback.LimitCallback"
						filterable="false" sortable="false" title="�����б�" 
						showPagination="true" view="compact" items="list" var="renw"
						action="${pageContext.request.contextPath}/handleKagRenw.do?method=searchTask"
						imagePath="${pageContext.request.contextPath}/images/table/*.gif"  rowsDisplayed="${ec_yemjlts}">
						<ec:row>
					    	<ec:column property="renwbs" title="�����ʶ"/>
							<ec:column property="zhangh" title="�˺�"  />
							<ec:column property="qiyrq" title="ӡ������������"  />
							<ec:column property="hum" title="����" />
							<ec:column property="jigh" title="������" />
							<ec:column property="renwrq" title="��������" />
							<ec:column property="renwsj" title="����ʱ��" />
							<ec:column property="yewlx" title="ҵ������" />
							<ec:column property="yinjks" title="ӡ������" >
							    <input id="zuob_${renw.renwbs}" type="hidden" value = "${renw.zuob}"/>
							</ec:column>
							<ec:column property="����" title="����" >
							    <c:if test="${renw.renwzt=='0'}">
									<a id="openkag_${renw.renwbs}" href="#" onclick="javascript:if(confirm('ȷ���򿪿���?'))openKag('${renw.renwbs}','${renw.yinjks}');" style="color: blue;text-decoration:underline;">�򿪿���</a>
									<a id="closekag_${renw.renwbs}"  disabled="disabled" href="#" onclick="javascript:if(confirm('ȷ���رտ���?'))closeKag(this,'${renw.renwbs}','${renw.yinjks}');"style="color: blue;text-decoration:underline;">�رտ���</a>
								</c:if>
								<c:if test="${renw.renwzt=='2'}">
									<a id="openkag_${renw.renwbs}"  disabled="disabled" href="#" onclick="javascript:if(confirm('ȷ���򿪿���?'))openKag('${renw.renwbs}','${renw.yinjks}');" style="color: blue;text-decoration:underline;">�򿪿���</a>
									<a id="closekag_${renw.renwbs}"  href="#" onclick="javascript:if(confirm('ȷ���رտ���?'))closeKag(this,'${renw.renwbs}','${renw.yinjks}');"style="color: blue;text-decoration:underline;">�رտ���</a>
								</c:if>
								<a id="checkpic_${renw.renwbs}" href="accountinfo.do?method=getYinjkListByQiyrq&zhangh=${renw.zhangh}&qiyrq=${renw.qiyrq}" style="color: blue;text-decoration:underline;" target="_blank">�鿴Ӱ��</a>
								<a id="deltask_${renw.renwbs}" href="#" onclick="javascript:if(confirm('ȷ��ȡ����������?'))deltask(this,'${renw.renwbs}');" style="color: blue;text-decoration:underline;">ȡ������</a>
							</ec:column>
						</ec:row>
					</ec:table>
				</td>
				<td background="images/table_arrow_07.gif">&nbsp;
			  </td>
			</tr>
			<tr>
				<td width="15" height="14">
					<img src="images/table_arrow_03.gif" width="15" height="14">
				</td>
				<td background="images/table_arrow_09.gif" style="height:14px;"></td>
				<td width="14" height="14">
					<img src="images/table_arrow_04.gif" width="14" height="14">
				</td>
			</tr>
		</table>
	   			<div>
                     <OBJECT ID="TEST_OCX" WIDTH="0" HEIGHT="0" CLASSID="CLSID:65C11E08-C534-4649-9AE9-C3424BB5C336" >
                     <PARAM name="LogLevel" value="0">
                     </OBJECT>
                 </div>
	
</body>
</html>