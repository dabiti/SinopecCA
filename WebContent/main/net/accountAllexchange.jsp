<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ page import="com.unitop.config.SystemConfig"%>
<%
	SystemConfig systemConfig = SystemConfig.getInstance();
	request.setAttribute("tesyw_shuangrhq", systemConfig.getValue("tesyw_shuangrhq"));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="style/right.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/shield.js"></script>
<%@ include file="/common/yewgz.jsp"%>
<script type="text/javascript"> 

	function validateZhangh(){
		var zhangh = $("#zhangh").val();
		if(zhangh==null||zhangh.length==0){

			$("#zhanghMsg").html("�˺Ų���Ϊ��!");
			return false;
		}
		var reg =   /(^[0-9]{9}$)|(^[0-9]{17}$)|(^[0-9]{22}$)|(^[0-9]{10,13}$)/;
		if(!reg.test(zhangh)){
		
			$("#zhanghMsg").html("�˺Ÿ�ʽ����ȷ!");
			return false;
		}
		$("#zhanghMsg").html('');
		return true;
	}

 function returnAccountInfo() {
	if(!validateZhangh())return;
	var zhangh = $("#zhangh").val();

	var math = Math.random();
	var url='accountinfo.do?method=getAccountForAllexchange&zhangh='+zhangh
	window.location.href=url;
}
 function deleteTdjg(orgnum){
		var zhanghao = $("#zhangh").val();
	var orgCode=$("#"+orgnum).val();
	
	//alert(orgCode);
	 <c:if  test='${tesyw_shuangrhq==1}'>
	   var obj = new Object(); 
	   obj.titleName = "ɾ��ͨ��";
	   obj.account = $("#zhangh").val();obj.checktype="��Ȩ";obj.quanxbs="TDSZ_001";
	   var backNum = window.showModalDialog('authorized.jsp',obj,"dialogWidth=10px;dialogHeight=10px;status=no;scroll=no"); 
	   if(backNum=='1')
	   {
		   window.location.href='accountinfo.do?method=deleteTongdsz&zhangh='+zhanghao+'&orgCode='+orgCode;
	   }
	</c:if>
	<c:if test='${tesyw_shuangrhq!=1}'>
	 window.location.href='accountinfo.do?method=deleteTongdsz&zhangh='+zhanghao+'&orgCode='+orgCode;
	</c:if>

	 }
 function toAdd(){
	 var zhanghzt='${zhanghb.zhanghzt}';
	 var tongd= '${zhanghb.tongctd}';
		var youwyj='${zhanghb.youwyj}';
		var yinjshzt='${zhanghb.yinjshzt}';
		if(tongd=='ȫ��'){
			$("#addMsg").html("���˻�ͨ�ұ�־Ϊȫ�����޷����!");
			return;
		}
		if(youwyj=='��'){
			$("#addMsg").html("���˻�����ӡ�������Ƚ���!");
			return;
		}
		if(yinjshzt=='δ��'){
			$("#addMsg").html("���˻�����δ��ӡ�����������!");
			return;
		}
	 if(zhanghzt !="��Ч"){
		$("#addMsg").html("�˻�״̬���Ϸ����޷����!");
		return;
		 }
	 if('${orgNum}'==10){
			$("#addMsg").html("�����10���������޷��������!");
			return;
			 }
		$("#addMsg").html('');
	 location.href='accountinfo.do?method=addAllexchangeView&zhangh=${zhanghb.zhangh}';
	 }
 $(function(){
	var pass ='${pass}';
	if(pass=='0'){
		return;
	}
})



</script>
</head>
<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					�˻����� - �˻�ͨ����Ϣ����
				</td>
			</tr>
			<tr>
				<td class="class1_td" style="text-align: left">
					&nbsp;
				</td>
				<td class="class1_td" style="text-align: left">	
					<form id="form1" name="form1" method="post" action="accountinfo.do?method=getAccountForAllexchange">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr >
								<td class=" ">
									�˺ţ�
								</td>
								<td class=" alignleft">
								
								<html:text property="account" styleId="zhangh" name="accountinfoForm" styleClass="inputField account required"  value="${zhanghb.zhangh}" onkeydown="if(event.keyCode==13){if(!validateZhangh()){this.focus();return false;}}"  onblur="returnAccountInfo();"></html:text>
								<span style="color: red">*</span><span id="zhanghMsg" style="color: red"></span>
								</td>
								<td class=" ">&nbsp;
								</td>
								<td class="">
									ͨ�ұ�־��
								</td>
								<td class=" alignleft"  style="width:100px">${zhanghb.tongctd}&nbsp;
								</td>
								<td class=" ">&nbsp;
								</td>
								
								<td class="">
									�˻�״̬��
								</td>
								<td class=" alignleft"  style="width:100px">${zhanghb.zhanghzt}&nbsp;
								</td>
								<td class=" ">&nbsp;
								</td>
								<td class="">
									<button type="button" id="button1" style="width:60px"
										onmouseover="this.className='buttom2'" onclick="returnAccountInfo();"
										onmouseout="this.className='buttom1'" class="buttom1">
										<img src="images/search1.gif" width="13" height="13"
											align="middle">
										��ѯ
									</button>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
		<br>
<form id=error name=error>
  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td class="orange error"><div  class="error orange"></div></td>
    </tr>
    <tr>
      <td class="orange"><html:errors /></td>
    </tr>
  </table>
</form>
			
		<table width="97%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
			</tr>

			<tr>
					<td background="images/table_arrow_05.gif">&nbsp;</td>
				<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="0" 
						class="class1_table">
							<div align="left">
							<button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onClick="toAdd();"><img src="images/add.gif" width="13" height="13" align="absmiddle">���</button>
								<span id="addMsg" style="color:red"></span>
								</div>
								
						<thead class="class1_thead">
							<tr>
								<th colspan="7" class="class1_thead th">
								
									ͨ�һ����б� &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
									ͨ�һ���������	<c:if test="${zhanghb.tongctd!=null && zhanghb.tongctd!='ȫ��'}"> ${orgNum}</c:if>&nbsp;&nbsp; 
								</th>
							</tr>
							<tr>
								<td class="class1_thead th w60">
									���
								</td>
								<td class="class1_thead th">
									��������
								</td>
								<td class="class1_thead th">
									��������
								</td>
								<td class="class1_thead th">
									����
								</td>
							</tr>
						</thead>
						<c:if test="${zhanghb.tongctd!=null && zhanghb.tongctd!='ȫ��'}">
						<% int a =1 ;%>
							<c:forEach items="${orgList}" var="org"  varStatus="a">
							<tr>
								<td class="class1_td">
								<%=a%>
								</td>
								<td class="class1_td">
									${org.code }
									<input type="hidden" value="${org.code}" id="orgnum<%=a%>">
								</td>
								<td class="class1_td ">
									<div align="center">
									${org.name }
									</div>
								</td>
								<td class="class1_td aligncenter">
									<div align="center">
											<a href="javascript:;" onclick="deleteTdjg('orgnum<%=a%>');">ɾ��</a>	
									</div>
								</td>
							</tr>
							<%a++; %>
						</c:forEach>
						</c:if>
					
					</table>
				</td>
				<td background="images/table_arrow_07.gif">
					&nbsp;
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