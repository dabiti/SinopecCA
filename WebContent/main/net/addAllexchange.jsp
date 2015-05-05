<%@page language="java" contentType="text/html;charset=gbk"
	isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.unitop.config.SystemConfig"%>
<%
	SystemConfig systemConfig = SystemConfig.getInstance();
	request.setAttribute("tesyw_shuangrhq", systemConfig.getValue("tesyw_shuangrhq"));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>
	<script src="js/pagejs/validateForm.js" type="text/javascript"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript">

		var orgFlag=false;
		function validate(){
			var orgCode = $("#orgCode").val();
			reg = /^[0-9]{4}$/;
			if (!reg.test(orgCode)) {
				$("#orgMsg").text("机构号格式不正确");
				// $("#receiveOrgCode").focus();
				return false;
			}
			$("#orgMsg").text('');
			return true;

		}
		function submitForm(){
			if(!orgFlag)return;
			if(!validate())return;
			if(orgFlag){
				<c:if  test='${tesyw_shuangrhq==1}'>
				   var obj = new Object(); 
				   obj.titleName = "添加通兑";
				   obj.account = $("#zhangh").val();obj.checktype="授权";obj.quanxbs="TDSZ_002";
				   var backNum = window.showModalDialog('authorized.jsp',obj,"dialogWidth=10px;dialogHeight=10px;status=no;scroll=no"); 
				   if(backNum=='1')
				   {
					  $("#form1").submit();
				   }
				</c:if>
				<c:if test='${tesyw_shuangrhq!=1}'>
					$("#form1").submit();
				</c:if>
				
			}
		}
		function getOrgName(){
			$("#orgMsg").text("");
			$("#abc").text('');
			var orgCode=$("#orgCode").val();
			if(orgCode==null||orgCode.length==0){
				return;
				}
			var tongd =$("#tongd").val();
			var tongdArray=tongd.split("|");
			if(tongdArray.length>=10){
				$("#orgMsg").text("通兑机构已超过10个!");
				orgFlag=false;
				return;
			}
			var currOrg='${zhanghb.jigh}';
			if(currOrg==orgCode){
				$("#orgMsg").text("不能添加账户所属机构!");
				orgFlag=false;
				return;
			}
			for(var i=0;i<tongdArray.length;i++){
				if(orgCode==tongdArray[i]){
					$("#orgMsg").text("已设置该机构!");
					orgFlag=false;
					return;
				}
			}
			$("#orgName").html('');
			$.post("yinjkOperate.do?method=getOrgInfo",{orgCode:orgCode},function(data, textStatus){
			if(textStatus=="success"){
				if(data.indexOf('<html>')!=-1){
					window.location.href='timeOutlogin.jsp';
					return;
				}
				var result=data;
				var info=result.split(',');
				if(info[0]=="0"){
					$("#orgMsg").text("机构不存在!");
					 orgFlag=false;
					return;
					}
				if(info[2]!=3){
					$("#orgMsg").text("只能添加支行!");
					orgFlag=false;
					$("#orgCode").focus();
					return;
					}
				$("#orgName").html(info[1]);
				 orgFlag=true;
				}
			},"text");
		}
		
		$(function(){
		
			


			
			})
		</script>
	</head>
	<body onkeydown='if (event.keyCode==78&& event.ctrlKey)
		return false;'>

		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					账户通兑信息设置- 添加通兑机构
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
		<html:form styleId="form1"
			action="accountinfo.do?method=addAllexchange" method="post" >
			<table width="97%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="up-left" />
						<td class="up-middle" />
							<td class="up-right" />
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
									<th colspan="2" class="class1_thead th">
										新增通兑机构
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td  alignright">
									账号：
								</td>
								<td class="class1_td alignleft" >
									<html:hidden property="account" styleId="zhangh"
								value="${zhanghb.zhangh}" name="accountinfoForm" />
									${zhanghb.zhangh}
								</td>
							</tr>
							<tr>
								<td class="class1_td  alignright">
									户名：
								</td>
								<td class="class1_td alignleft" >
									${zhanghb.hum}
								</td>
							</tr>
							<tr>
								<td class="class1_td  alignright">
									机构号：
								</td>
								<td class="class1_td alignleft" >
									<html:text styleId="orgCode" property="orgcode"
										name="accountinfoForm" onkeydown="if(event.keyCode==13){if(!validate()){this.focus();return false;}}"
										onblur="if(validate()){getOrgName();}"
										styleClass="inputField required orgCode" maxlength="4" /><span id="orgMsg" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td width="45%" class="class2_td  alignright">
									机构名称：
								</td>
								<td width="55%" class="class2_td alignleft w220" id="orgName">
								</td>
							</tr>
							<html:hidden property="include" styleId="tongd"
								value="${zhanghb.tongdsz}" name="accountinfoForm" />
							
						</table>
					</td>
					<td background="images/table_arrow_07.gif">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bottom-left" />
						<td class="bottom-middle" />
							<td class="bottom-right" />
				</tr>
			</table>
			<div class="funbutton">
				<button type="button" style="width: 60px" onmouseover="this.className='buttom2'" onkeydown="if(event.keyCode==13){submitForm();}"
					onmouseout="this.className='buttom1'" class="buttom1" onclick="submitForm();"class="buttom1">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					保存
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="window.location.href='accountinfo.do?method=getAccountForAllexchange&zhangh=${zhanghb.zhangh}'">
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					取消
				</button>
			</div>
		</html:form>
	</body>
</html>