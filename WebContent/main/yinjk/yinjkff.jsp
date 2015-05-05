<%@ page contentType="text/html;charset=GBK" language="java"
	isELIgnored="false"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ page import="com.unitop.config.SystemConfig"%>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%
	SystemConfig systemConfig = SystemConfig.getInstance();
	request.setAttribute("tesyw_shuangrhq", systemConfig
			.getValue("tesyw_shuangrhq"));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<script type="text/javascript" src="js/yinjk.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript"> 
		function checkFfType(){
			var ffType=$("#ffType").val();
			if(ffType==0){
			$("#oldpingz").hide();
			}else{
			$("#oldpingz").show();
			}
		}
		function startRequestForSearch() {
			
			var account = $("#zhangh").val();
			
			var math = Math.random();
			$.post("accountinfo.do?method=getAccountForCreate", {math:math, account:account}, function (data, textStatus) {
				if (textStatus = "success") {
					
					var accountinfo = data.split(",");
					if(accountinfo[0]=="不存在"){
						alert("账户不存在");
						return;
					}
					if(accountinfo[0]=="无权限"){
						alert("无权限操作该账户");return;}
					$("#zhangh").css('color','silver').attr('readonly','readonly');
					$("#hum").val(accountinfo[0]==""?" ":accountinfo[0]);
					$("#zhanghxz").val(accountinfo[1]);
					$("#linkman").val(accountinfo[4]==null?" ":accountinfo[4]);
					$("#phone").val(accountinfo[5]==""?" ":accountinfo[5]);
					$("#oldyinjkh").val(accountinfo[9]==""?" ":accountinfo[9]);
					$("#oldyinjkh2").val(accountinfo[11]==""?" ":accountinfo[11]);
					$("#oldyinjkh3").val(accountinfo[12]==""?" ":accountinfo[12]);
					$("#oldyinjkh4").val(accountinfo[13]==""?" ":accountinfo[13]);
					
				}
			}, "text");
		};
		function validateYinjkh(key){
			switch(key){
			case 0:
				var zhangh =$("#zhangh").val();
				if(zhangh==null||zhangh.length==0){
				$("#zhanghMsg").html("账号不可为空");
				return false;
				}
				var reg =/^[0-9]{12,22}$/;
				if(!reg.test(zhangh)){
					$("#zhanghMsg").html("账号格式不正确");
					return false;
				}
				$("#zhanghMsg").html('');
				return true;
			case 1:
				var value=$("#yinjkh").val();
				if(value.length==0){
					$('#yinjkbhmsg').text(" 印鉴卡号不能为空");
					return false;
				}
			
				var reg= /[0-9]{5,22}/;
				if(!reg.test(value)){
					$('#yinjkbhmsg').text(" 印鉴卡号格式不正确");
					return false;
				}
				$("#yinjkbhmsg").text("");
				return true;
			break;
			case 2:
				var value=$("#yinjkh2").val();
				if(value.length==0){
					$("#yinjkbh2msg").text("");
					return true;
				}
			
				var reg= /[0-9]{5,22}/;
				if(!reg.test(value)){
					$('#yinjkbh2msg').text(" 印鉴卡号2格式不正确");
					return false;
				}
				$("#yinjkbh2msg").text("");
				return true;
			break;
			case 3:
				var value=$("#yinjkh3").val();
				if(value.length==0){
					$("#yinjkbh3msg").text("");
					return true;
				}
				
				var reg= /[0-9]{5,22}/;
				if(!reg.test(value)){
					$('#yinjkbh3msg').text(" 印鉴卡号3格式不正确");
					return false;
				}
				$("#yinjkbh3msg").text("");
				return true;
				break;
			case 4:
				var value=$("#yinjkh4").val();
				if(value.length==0){
					$("#yinjkbh4msg").text("");
					return true;
				}
				var reg= /[0-9]{5,22}/;
				if(!reg.test(value)){
					$('#yinjkbh4msg').text(" 印鉴卡号4格式不正确");
					return false;
				}
				$("#yinjkbh4msg").text("");
				return true;
				break;
			}
		}
			
		function resetData(){
			window.location='yinjkOperate.do?method=yinjkffForView';
		}
		function submitForm(){
			if(!validateYinjkh(1))return;
			if(!validateYinjkh(2))return;
			if(!validateYinjkh(3))return;
			if(!validateYinjkh(4))return;
			if(!validateYinjkh(0))return;

			formSubmit();

		}
		$(function(){
			var fftype=$("#ffType").val();
			if(fftype=="0"){
			$("#oldpingz").hide();
			}else{

				$("#oldpingz").show();
				startRequestForSearch();
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
					空白印鉴卡管理- 印鉴卡发放
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
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange error">
						<div class="error orange" id="abc1"></div>
					</td>
				</tr>
				<tr>
					<td class="orange" id="abc">
						<html:errors />
					</td>
				</tr>
			</table>
		</form>
		<html:form styleId="form1" method="post"
			action="yinjkOperate.do?method=yinjkff">
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
									<th id="title" colspan="2" class="class1_thead th">
										印鉴卡发放
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td alignright">
									发放类型：
								</td>
								<td class="class1_td alignleft">
									<html:select property="ffType" styleId="ffType" tabindex="true"
										onclick="checkFfType();">
										<html:option value="0">开户</html:option>
										<html:option value="1">变更</html:option>
										<html:option value="2">替换</html:option>
									</html:select>
									<span style="color: red">*</span>
									<span id="ffTypeMsg" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									印鉴卡编号：
								</td>
								<td class="class1_td alignleft">

									<html:text styleId="yinjkh" property="yinjkh"
										styleClass="inputField account required "
										onkeydown="if(event.keyCode==13){if(!validateYinjkh(1)){this.focus();return false}}"
										onblur="validateYinjkh(1);" maxlength="22"
										style="width:180px; " />
									<span style="color: red">*&nbsp;</span>
									<span id="yinjkbhmsg" style="color: red"></span>
									<br>
									<html:text styleId="yinjkh2" property="yinjkh2"
										styleClass="inputField account required "
										onkeydown="if(event.keyCode==13){if(!validateYinjkh(2)){this.focus();return false}}"
										onblur="validateYinjkh(2);" maxlength="22"
										style="width:180px; " />
									<span id="yinjkbh2msg" style="color: red"></span>
									<br>
									<html:text styleId="yinjkh3" property="yinjkh3"
										styleClass="inputField account required "
										onkeydown="if(event.keyCode==13){if(!validateYinjkh(3)){this.focus();return false}}"
										onblur="validateYinjkh(3);" maxlength="22"
										style="width:180px; " />
									<span id="yinjkbh3msg" style="color: red"></span>
									<br>
									<html:text styleId="yinjkh4" property="yinjkh4"
										styleClass="inputField account required "
										onkeydown="if(event.keyCode==13){if(!validateYinjkh(4)){this.focus();return false}}"
										onblur="validateYinjkh(4);" maxlength="22"
										style="width:180px; " />
									<span id="yinjkbh4msg" style="color: red"></span>

								</td>
							</tr>
							<tr>
								<td width="42%" class="class1_td alignright">
									账号：
								</td>
								<td width="58%" class="class1_td alignleft">
									<html:text property="zhangh" styleId="zhangh"
										styleClass="inputField required accountname" maxlength="22"
										style="width:180px; "
										onkeydown="if(event.keyCode==13){if(!validateYinjkh(0)){this.focus();return false;}}"
										onblur="if(validateYinjkh(0)){startRequestForSearch(this);}"></html:text>
									<span style="color: red">*&nbsp;</span><span id="zhanghMsg"
										style="color: red"> </span>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									户名 ：
								</td>
								<td class="class1_td alignleft">
									<html:text property="hum" styleId="hum"
										styleClass="inputField required accountname"
										style="width:180px;color:silver;" readonly="readonly" />
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									财务联系人：
								</td>
								<td class="class1_td alignleft">
									<html:text property="linkman" styleId="linkman"
										styleClass="inputField required"
										style="width:180px;color:silver;" readonly="readonly" />
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									财务联系人电话：
								</td>
								<td class="class1_td alignleft">
									<html:text property="phone" styleId="phone"
										styleClass="inputField required"
										style="width:180px;color:silver;" readonly="readonly" />
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									账户性质：
								</td>
								<td class="class1_td alignleft">
									<html:text property="zhanghxz" styleId="zhanghxz"
										styleClass="inputField required"
										style="width:180px;color:silver;" readonly="readonly" />
								</td>
							</tr>
							<tr id="oldpingz">
								<td class="class1_td alignright">
									原印鉴卡编号：
								</td>
								<td class="class1_td alignleft">

									<html:text styleId="oldyinjkh" property="oldyinjkh"
										styleClass="inputField account required "
										style="width:180px; color:silver;" readonly="readonly" />
									<br>
									<html:text styleId="oldyinjkh2" property="oldyinjkh2"
										styleClass="inputField account required "
										style="width:180px;color:silver;" readonly="readonly" />
									<br>
									<html:text styleId="oldyinjkh3" property="oldyinjkh3"
										styleClass="inputField account required "
										style="width:180px;color:silver;" readonly="readonly" />
									<br>
									<html:text styleId="oldyinjkh4" property="oldyinjkh4"
										styleClass="inputField account required "
										style="width:180px;color:silver;" readonly="readonly" />
								</td>
							</tr>
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
				<button type="button" style="width: 60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="submitForm()"
					onkeydown="if(event.keyCode==13){submitForm();}">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					提交
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width: 60px" onclick="resetData()"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					取消
				</button>
			</div>
		</html:form>
	</body>
</html>