<%@page language="java" contentType="text/html;charset=gbk"
	isELIgnored="false"%>
<%@page import="com.unitop.config.PingzpzConfig"%>
<%@page import="com.unitop.sysmgr.bo.Yanygz"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.unitop.config.SystemConfig"%>
<%
	SystemConfig systemConfig = SystemConfig.getInstance();
	request.setAttribute("tesyw_shuangrhq", systemConfig.getValue("tesyw_shuangrhq"));
	//List<Yanygz> yygzList=PingzpzConfig.getYangzList();
	//request.setAttribute("yyzgList", yygzList);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<script src="js/pagejs/validateForm.js" type="text/javascript"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript">
		
		function validateForTime(){
			var timetype = $("#timetype").val();
			var reg ;
			if(timetype=='��'){
				reg = /^([0-1]{1}[0-9]{1})|20|21|22|23$/;
			}else if(timetype=='��'){
				reg = /^0{1}[1-7]{1}$/;
			}else if(timetype=='��'){
				reg = /^([0-1]{1}[1-9]{1})|(2{1}[1-8]{1})$/;
			}else if(timetype=='��'){
				reg = /^(0{1}[1-9]{1})|10|11|12$/;
			}
		
			var id = "timevalue";
			var name = "ʱ��";
			if($("#"+id).val().length!=0&&$("#"+id).val()!=''){
				if(reg!=null&&!reg.test($("#"+id).val())){
					alert(" "+name+"��ʽ����ȷ");
					return false; 
				}
			}else{
				alert("* "+name+"����Ϊ��");
				return false;
			}	
			return true;
		}
		
		
		function changeTip(){
			var timetype=$("#timetype").val();
			if(timetype =="��"){
				$("#tip").html("��");
			}else if(timetype =="��"){
				$("#tip").html("&nbsp;");
			}else if(timetype =="��"){
				$("#tip").html("��");
			}else if(timetype =="��"){
				$("#tip").html("��");
			}
		}
		
		
		function formSubmit(){
			if(!validateForTime())return;
			$("#form1").submit();

			}
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
					���������-�޸ĳ����������
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
		<form id="form1" action="tongb.do?method=updateTaskConfig" method="post"  >
			
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
										�޸ĳ����������
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td alignright">
									����ID��
								</td>
								<td class="class1_td alignleft" >
									<input type="hidden" name="id" id="id" value="${task.id}">
									${task.id}
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									�������ƣ�
								</td>
								<td class="class1_td alignleft" >
								<input type="hidden" name="name" id="name" value="${task.taskname}">
									${task.taskname}
									</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									ִ�����ڣ�
								</td>
								<td class="class1_td alignleft" >
									<select name="timetype" id="timetype"  class="select required yanylx"  onchange="changeTip();">
											<option value="��" title="ÿ��ִ��һ��" ${task.timetype == "��"?'selected="selected"':'' }>
												��
											</option>
											<option value="��" title="ÿ��ִ��һ��" ${task.timetype == "��"?'selected="selected"':'' }>
												��
											</option>
											<option value="��" title="ÿ��ִ��һ��" ${task.timetype == "��"?'selected="selected"':'' }>
												��
											</option>
											<option value="��" title="ÿ��ִ��һ��" ${task.timetype == "��"?'selected="selected"':'' }>
												��
											</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<td class="class1_td alignright">
									ִ��ʱ�䣺
								</td>
								<td class="class1_td alignleft" >
									<input type="text" id="timevalue" maxlength="2" class="inputField" style="width:40px" name="timevalue" onblur="validateForTime();"  onkeydown="if(event.keyCode==13){if(!validateForTime()){this.focus();return false;}}" value="${task.timevalue}"/><span id="tip">${task.timetype eq '��'?'��':task.timetype eq '��'?' ':task.timetype eq '��'?'��':'��'}</span><span id="timevaluemsg" style="color: red"> </span>
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
				<button type="button" style="width: 60px" onmouseover="this.className='buttom2'" 
					onmouseout="this.className='buttom1'" class="buttom1" class="buttom1" onclick="formSubmit();" onkeydown="if(event.keyCode==13){formSubmit();}">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					����
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="window.location.href='tongb.do?method=forBizView'">
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					ȡ��
				</button>
			</div>
		</form>
	</body>
</html>