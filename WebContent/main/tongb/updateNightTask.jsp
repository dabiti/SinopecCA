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
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<script src="js/pagejs/validateForm.js" type="text/javascript"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript">
		
		function validateForTime(){
			var timetype = $("#timetype").val();
			var reg ;
			if(timetype=='天'){
				reg = /^([0-1]{1}[0-9]{1})|20|21|22|23$/;
			}else if(timetype=='周'){
				reg = /^0{1}[1-7]{1}$/;
			}else if(timetype=='月'){
				reg = /^([0-1]{1}[1-9]{1})|(2{1}[1-8]{1})$/;
			}else if(timetype=='年'){
				reg = /^(0{1}[1-9]{1})|10|11|12$/;
			}
		
			var id = "timevalue";
			var name = "时间";
			if($("#"+id).val().length!=0&&$("#"+id).val()!=''){
				if(reg!=null&&!reg.test($("#"+id).val())){
					alert(" "+name+"格式不正确");
					return false; 
				}
			}else{
				alert("* "+name+"不可为空");
				return false;
			}	
			return true;
		}
		
		
		function changeTip(){
			var timetype=$("#timetype").val();
			if(timetype =="天"){
				$("#tip").html("点");
			}else if(timetype =="周"){
				$("#tip").html("&nbsp;");
			}else if(timetype =="月"){
				$("#tip").html("日");
			}else if(timetype =="年"){
				$("#tip").html("月");
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
					批处理程序-修改程序参数配置
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
										修改程序参数配置
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td alignright">
									任务ID：
								</td>
								<td class="class1_td alignleft" >
									<input type="hidden" name="id" id="id" value="${task.id}">
									${task.id}
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									任务名称：
								</td>
								<td class="class1_td alignleft" >
								<input type="hidden" name="name" id="name" value="${task.taskname}">
									${task.taskname}
									</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									执行周期：
								</td>
								<td class="class1_td alignleft" >
									<select name="timetype" id="timetype"  class="select required yanylx"  onchange="changeTip();">
											<option value="天" title="每日执行一次" ${task.timetype == "天"?'selected="selected"':'' }>
												天
											</option>
											<option value="周" title="每周执行一次" ${task.timetype == "周"?'selected="selected"':'' }>
												周
											</option>
											<option value="月" title="每月执行一次" ${task.timetype == "月"?'selected="selected"':'' }>
												月
											</option>
											<option value="年" title="每年执行一次" ${task.timetype == "年"?'selected="selected"':'' }>
												年
											</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<td class="class1_td alignright">
									执行时间：
								</td>
								<td class="class1_td alignleft" >
									<input type="text" id="timevalue" maxlength="2" class="inputField" style="width:40px" name="timevalue" onblur="validateForTime();"  onkeydown="if(event.keyCode==13){if(!validateForTime()){this.focus();return false;}}" value="${task.timevalue}"/><span id="tip">${task.timetype eq '天'?'点':task.timetype eq '周'?' ':task.timetype eq '月'?'号':'月'}</span><span id="timevaluemsg" style="color: red"> </span>
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
					保存
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="window.location.href='tongb.do?method=forBizView'">
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					取消
				</button>
			</div>
		</form>
	</body>
</html>