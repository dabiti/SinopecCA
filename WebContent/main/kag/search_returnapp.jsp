<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false"%>
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
			 $("#YinjkForm").validate({
			   onsubmit:true,
			   onfocusout:true,
			   onkeyup :true,
			   errorLabelContainer:"#error div.error",
			   wrapper:"li"
			   });
			   });
		function searchApp(zhangh,kagid,ceng,choutwz,yewlx,qiyrq,yinjkh){
			//�����е�ҵ��������ָ����ӡ���������������Ǳ��ҵ��ġ�
			var shifzkID = "a_"+zhangh+"_"+yewlx+"_"+qiyrq;
			var hrefid =  "b_"+zhangh+"_"+yewlx+"_"+qiyrq;
			var shifzkValue=document.getElementById(shifzkID).value;
			if(shifzkValue=='0'){
				alert("ӡ�������ڿ����޷�������ģ�");
				return;
			}
			var yewlx = "2";//���ҵ�����ͣ���ָ���������ҵ�����ͣ���������������ģ��黹��
			var math = Math.random();
			$.post("handleKagRenw.do?method=createKagRenw", {math:math,zhangh:zhangh,kagid:kagid,ceng:ceng,choutwz:choutwz,yewlx:yewlx,qiyrq:qiyrq,yinjkh:yinjkh}, function (data, textStatus) {
				if (textStatus = "success") {
						document.getElementById(shifzkID).value = "0";
						 $("#"+hrefid).html('��������ɹ�');
					}
			}, "text");
		}		
		function returnApp(zhangh,kagid,ceng,choutwz,yewlx,qiyrq,yinjkh){
			//�����е�ҵ��������ָ����ӡ���������������Ǳ��ҵ��ġ�
			var shifzkID = "a_"+zhangh+"_"+yewlx+"_"+qiyrq;
			var hrefid =  "c_"+zhangh+"_"+yewlx+"_"+qiyrq;
			var shifzkValue=document.getElementById(shifzkID).value;
			if(shifzkValue=='1'){
				alert("ӡ�����ڿ����У�������й黹��");
				return;
			}
			var yewlx = "3";//���ҵ�����ͣ���ָ���������ҵ�����ͣ���������������ģ��黹��
			var math = Math.random();
			$.post("handleKagRenw.do?method=createKagRenw", {math:math,zhangh:zhangh,kagid:kagid,ceng:ceng,choutwz:choutwz,yewlx:yewlx,qiyrq:qiyrq,yinjkh:yinjkh}, function (data, textStatus) {
				if (textStatus = "success") {
						document.getElementById(shifzkID).value = "1";
						 $("#"+hrefid).html('�黹����ɹ�');
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
					ӡ��������-������黹����			
				</td>
			</tr>
			<tr>
			<td>
					&nbsp;
				</td>
				<td>
					<html:form styleId="YinjkForm" action="/yinjkOperate.do?method=getYinjk" method="post">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="class1_td w70">
									�˺�
								</td>
								<td class="class1_td alignleft">
									<html:text property="zhangh" styleId="zhangh" styleClass="inputField"/>
								</td>
								<td class="class1_td w70">
									������
								</td>
								<td class="class1_td alignleft">
									<html:text property="jigh" styleId="jigh" styleClass="inputField"/>
								</td>
									<td class="class1_td w70">
								״̬
								</td>
								<td class="class1_td alignleft">
									<html:select  styleId="yewlx"  property="yewlx">
										<html:option value="">ȫ��</html:option>
										<html:option value="0">����</html:option>
										<html:option value="1">��ʷ</html:option>
									</html:select>
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
						showPagination="true" view="compact" items="list" var="yinjk"
						action="${pageContext.request.contextPath}/yinjkOperate.do?method=getYinjk"
						imagePath="${pageContext.request.contextPath}/images/table/*.gif" rowsDisplayed="${ec_yemjlts}">
						<ec:row>
							<ec:column property="yinjkid.zhangh" title="�˺�"/>
							<ec:column property="jigh" title="������" />
							<ec:column property="yewlx" title="״̬" />
							<ec:column property="kagid" title="������" />
							<ec:column property="ceng" title="��" />
							<ec:column property="choutwz" title="����" />
							<ec:column property="yinjkid.qiyrq" title="ӡ������������" />
							<ec:column property="yinjkid.yinjkh" title="ӡ�������" />
							
							<ec:column property="����" title="����" >
								<input id="a_${yinjk.yinjkid.zhangh}_${yinjk.yewlx}_${yinjk.yinjkid.qiyrq}" type="hidden" value="${yinjk.shifzk}"/>
							    <c:if test="${yinjk.shifzk=='1'}">
									<a id="b_${yinjk.yinjkid.zhangh}_${yinjk.yewlx}_${yinjk.yinjkid.qiyrq}" href="#" onclick="javascript:if(confirm('ȷ���ύ��������?'))searchApp('${yinjk.yinjkid.zhangh}','${yinjk.kagid}','${yinjk.ceng}','${yinjk.choutwz}','${yinjk.yewlx}','${yinjk.yinjkid.qiyrq}','${yinjk.yinjkid.yinjkh}');" style="color: blue;text-decoration:underline;">��������</a>
								</c:if>
								<c:if test="${yinjk.shifzk=='0'}">
									<a id="c_${yinjk.yinjkid.zhangh}_${yinjk.yewlx}_${yinjk.yinjkid.qiyrq}" href="#" onclick="javascript:if(confirm('ȷ���ύ�黹����?'))returnApp('${yinjk.yinjkid.zhangh}','${yinjk.kagid}','${yinjk.ceng}','${yinjk.choutwz}','${yinjk.yewlx}','${yinjk.yinjkid.qiyrq}','${yinjk.yinjkid.yinjkh}');" style="color: blue;text-decoration:underline;">�黹����</a>
								</c:if>
								<a id="checkpic_${yinjk.yinjkid.zhangh}_${yinjk.yewlx}_${yinjk.yinjkid.qiyrq}" href="/accountinfo.do?method=getYinjkListByQiyrq&zhangh=${yinjk.yinjkid.zhangh}&qiyrq=${yinjk.yinjkid.qiyrq}" style="color: blue;text-decoration:underline;" target="_blank">�鿴Ӱ��</a>	    
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
</body>
</html>