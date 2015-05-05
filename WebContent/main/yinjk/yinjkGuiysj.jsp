<%@page language="java" contentType="text/html;charset=GBK"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<script type="text/javascript" src="js/yinjk.js"></script>
		<script type="text/javascript">
		var clerkFlag=false;
		var clerkCodeCache="";
		//计算终止凭证号
	function checkClerk(){
		if(clerkFlag==true&&clerkCodeCache==$("#lyClerkCode").val())
			return;
		  var obj = new Object(); 
		   obj.titleName = "柜员上缴";
		   obj.account = "";
		   obj.checktype="上缴";
		   obj.clerkCode=$("#lyClerkCode").val();
		   var backNum = window.showModalDialog('authorized.jsp',obj,"dialogWidth=10px;dialogHeight=10px;status=no;scroll=no"); 
		   if(backNum=='1')
		   {	clerkFlag=true;
		   $("#clerkMsg").text('');
		   clerkCodeCache=$("#lyClerkCode").val();
			  return true;
		   }else{
			   clerkFlag=false;
				$("#clerkMsg").text("需要通过身份验证");
				return false;
			}
	}

	function submitForm() {
		if (!validate(0))
			return;
		if (!validate(1))
			return;
		
		if (!validate(3))
			return;
		if(clerkFlag){
			formSubmit();
		}else{
			$("#clerkMsg").text("需要通过身份验证");
			}
	}

	
$(function(){
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
					空白印鉴卡管理-柜员上缴
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
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td id="errorMessage" class="orange error">
					<div class="error orange"></div>
				</td>
			</tr>
		</table>
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange error">
						<div class="error orange">
							${msg}
						</div>
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
		<html:form action="yinjkOperate.do?method=saveGuiysj" method="post"
			styleId="form1">
			<table width="100%" border="0" align="center" cellpadding="0"
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
										柜员上缴
									</th>
								</tr>
							</thead>
							<tr>
								<td align="right">
								</td>
							</tr>

							<tr>
								<td width="45%" class="class2_td  alignright">
									起始凭证号：
								</td>
								<td  width="55%" class="class2_td alignleft w220">
									<html:text property="yinjkh" styleId="yinjkh" styleClass="inputField required " maxlength="22"  style="width:160px;" onkeydown="if(event.keyCode==13){if(!validate(0)){this.focus();return false;}}if(event.keyCode==9){if(!validate(0)){this.focus();return false;}}" 
										 onblur="validate(0);addNum();"/>
										 
									<span style="color: red">*</span>
									<span id="yinjkhMsg" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									数量：
								</td>
								<td class="class1_td alignleft">
								<html:text property="num" styleId="num"  maxlength="5" onkeydown="if(event.keyCode==13){if(!validate(1)){this.focus();return false;}}" onblur="validate(1);addNum();"></html:text>
									<span style="color: red">*</span>
									<span id="numMsg" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									终止凭证号：
								</td>
								<td class="class1_td alignleft" >
								<html:text property="endYinjkh" styleId="endYinjkh" styleClass="inputField required"  maxlength="22" style="width:160px;color: silver;" readonly="readonly" ></html:text>
						
								</td>
							</tr>  
							<tr>
								<td width="45%" class="class2_td  alignright">
									上缴柜员号：
								</td>
								<td width="55%" class="class2_td alignleft w220">
								<html:text property="lyClerkCode" styleId="lyClerkCode" styleClass="inputField required" maxlength="7"
										style="width: 160px;"
										onkeydown="if(event.keyCode==13){if(!validate(3)){this.focus();return false;}}"
										onblur="if(!validate(3)){this.focus();return false;}else{checkClerk();}" ></html:text>
								
									
									<span style="color: red">*</span>
									<span id="clerkMsg" style="color: red"></span>
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
