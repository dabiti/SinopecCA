<%@page language="java" contentType="text/html;charset=GBK" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/shield.js"></script>	
		<script type="text/javascript" src="js/yinjk.js"></script>	
		<script type="text/javascript">

		function submitForm(){
		
			
			if (!validate(4))
				return;
			if (!validate(5))
				return;
			if (!validate(6))
				return;
			if (!validate(0))
				return;
			
			formSubmit();


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
					�հ�ӡ��������-ӡ����Ԥ����
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
				<td id="errorMessage"class="orange error">
					<div class="error orange"></div>
				</td>
			</tr>
		</table>
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange error">
						<div class="error orange" id="abc1">
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
		<html:form  action="yinjkOperate.do?method=saveYinjkyff" method="post"  styleId="form1">
			<table width="100%" border="0" align="center" cellpadding="0"
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
									<th colspan="2" class="class1_thead th">
										ӡ����Ԥ����
									</th>
								</tr>
							</thead>
							<tr>
								<td align="right">
								</td>
							</tr>
							<tr>
								<td width="45%" class="class2_td  alignright">
									������
								</td>
								<td  width="55%" class="class2_td alignleft w220">
									<html:text property="hum" styleId="hum" styleClass="inputField required "   style="width:160px;" onkeydown="if(event.keyCode==13){if(!validate(4)){this.focus();return false;}}if(event.keyCode==9){if(!validate(4)){this.focus();return false;}}" 
										 onblur="validate(4)"/>
										 
									<span style="color: red">*</span>
									<span id="humMsg" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									�ͻ�����1��
								</td>
								<td class="class1_td alignleft">
								<html:text property="kehjl1" styleId="kehjl1"  maxlength="10" style="width:160px;" onkeydown="if(event.keyCode==13){if(!validate(5)){this.focus();return false;}}" onblur="validate(5)"></html:text>
									<span style="color: red">*</span>
									<span id="kehjl1Msg" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									�ͻ�����2��
								</td>
								<td class="class1_td alignleft" >
								<html:text property="kehjl2" styleId="kehjl2" styleClass="inputField required"  onkeydown="if(event.keyCode==13){if(!validate(6)){this.focus();return false;}}" onblur="validate(6)"  maxlength="10" style="width:160px;" ></html:text>
								<span style="color: red">*</span>
									<span id="kehjl2Msg" style="color: red"></span>
								</td>
							</tr> 
							<tr>
								<td width="45%" class="class2_td  alignright">
									ӡ�����ţ�
								</td>
								<td  width="55%" class="class2_td alignleft w220">
									<html:text property="yinjkh" styleId="yinjkh" styleClass="inputField required " maxlength="22"  style="width:160px;" onkeydown="if(event.keyCode==13){if(!validate(0)){this.focus();return false;}}if(event.keyCode==9){if(!validate(0)){this.focus();return false;}}" 
										 onblur="validate(0);"/>
										 
									<span style="color: red">*</span>
									<span id="yinjkhMsg" style="color: red"></span>
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
							<td class="bottom-right" /></tr>
			</table>
			<div class="funbutton">
				<button type="button" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1" onclick = "submitForm()" onkeydown="if(event.keyCode==13){submitForm();}">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					�ύ
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width:60px" onclick="resetData()"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/back1.gif" width="11" height="11" 
						align="absmiddle">
					ȡ��
				</button>
			</div>
		</html:form>
	</body>
</html>
