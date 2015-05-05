<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script type="text/javascript" src="js/shield.js"></script>
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript" src="js/date/time.js"></script>
		
		<%@ include file="/common/validator.jsp"%>
		<%@ include file="/common/yewgz.jsp"%>
		<script src="js/pagejs/valiateFormForImg.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript">
	/*	function tbOrgAndClerk(){
			$("#tbMsg").text("正在同步，请稍后……");
								$(":button").attr("disabled","disabled");
							
			$.ajax({
				url:"tongb.do?method=tongbOrgAndClerk",
				type:"post",
				dataType:"text",
				data:{math:1},
				cache:false,
				success:function(data){
						var result =data;
						$(":button").attr("disabled","");
						if(result.charAt(0) =="1"){
							$("#tbMsg").text("同步成功");
							return;
						}
						if(result.charAt(0) =="0"){
							$("#tbMsg").text("同步失败"+data.substring(0));
							return;
						}
						
					}
				}
			);
		}
		function zcTongbYj(){
			$("#zfMsg").text("正在同步，请稍后……");
								$("#button1").attr("disabled","disabled");
								$("#button2").attr("disabled","disabled");
								$("#button3").attr("disabled","disabled");
							
			$.ajax({
				url:"tongb.do?method=zcTongbYj",
				type:"post",
				dataType:"text",
				data:{math:1},
				cache:false,
				success:function(data){
						var result =data;
						$("#button1").attr("disabled","");
						$("#button2").attr("disabled","");
						$("#button3").attr("disabled","");
						if(result.charAt(0) =="1"){
							$("#zfMsg").text("同步成功");
							return;
						}
						if(result.charAt(0) =="0"){
							$("#zfMsg").text("同步失败"+data.substring(0));
							return;
						}
						
					}
				}
			);
		}
		function tglTongj(){
			$("#tjMsg").text("正在统计，请稍后……");
			$(":button").attr("disabled","disabled");
							
			$.ajax({
				url:"tongb.do?method=tglTongj",
				type:"post",
				dataType:"text",
				data:{math:1},
				cache:false,
				success:function(data){
						var result =data;
						$(":button").attr("disabled","");
						if(result.charAt(0) =="1"){
							$("#tjMsg").text("统计成功");
							return;
						}
						if(result.charAt(0) =="0"){
							$("#tjMsg").text("统计失败"+data.substring(0));
							return;
						}
						
					}
				}
			);
		}*/

		function checkWorkDate(){
				var workdate=$("#workdate").val();
				var yestoday='${today}';
				if(workdate>yestoday){
					return false;
				}
				return true;
		}
				
		function run(url,name){
			if(name==5){
				if(!checkWorkDate()){
					alert("导出日期不能晚于昨天");
					return;
					}
				url=url+"&workdate="+$("#workdate").val();
				}
			$("#"+name+"Msg").text("正在执行");
			$(":button").attr("disabled","disabled");

			$.ajax({
				url:url,
				type:"post",
				dataType:"text",
				data:{math:1},
				cache:false,
				success:function(data){
						var result =data;
						$(":button").attr("disabled","");
						if(result.charAt(0) =="0"){
							$("#"+name+"Msg").text("执行成功");
						}
						if(result.charAt(0) =="1"){
							$("#"+name+"Msg").text("执行失败");
						}
						setTimeout(function(){
							window.location.href='tongb.do?method=forBizView';
						},1000);
						
					}
				}
			);
		}
		
		
		
		</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'
		onload="document.getElementById('tu').innerHTML='';">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					系统管理 - 批处理程序
				</td>
			</tr>
		</table>
		<div id="tu"></div>
		<form id=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange">
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

		<table width="97%" border="0" align="center" cellpadding="0"
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
								<th colspan="8" class="class1_thead th">
									批处理程序
								</th>
							</tr>
							
						</thead>
						<tr>
							<td colspan="4" align="right">
							</td>
						</tr>
						  <tr> 
			            <td class="class1_thead th w60">编号</td>
			            <td class="class1_thead th">名称</td>
				   		<td class="class1_thead th">最后执行日期</td>
				   		<td class="class1_thead th">最后执行结果</td>
				        <td class="class1_thead th">执行周期</td>
				        <td class="class1_thead th">执行时间</td>
				        <td class="class1_thead th">操作</td>
				        <td class="class1_thead th">状态</td>
	         			 </tr>
       
      	<c:forEach items="${tasklist}" var="task">
	        <tr> 
	          <td class="class1_td " >${task.id}</td>
	          <td class="class1_td center">${task.taskname}</td>
		  	  <td class="class1_td center">${task.lastruntime}</td>
		  	  <td class="class1_td center">${task.lastrunresult eq 0?'执行成功':'执行失败'}</td>
		  	  <td class="class1_td center">每${task.timetype}</td>
		  	  
		  	  <td class="class1_td center"> ${task.timevalue }${task.timetype eq '天'?'点':task.timetype eq '周'?'':task.timetype eq '月'?'日':'月'}</td>
		  	  <td class="class1_td center">
		  	  <c:if test="${task.id eq 5}">
		  	  <input type="text" maxlength="10" style="width:75px;left: 50px" id="workdate" name="workdate" value="${today}" onfocus="WdatePicker();"/>
		  	  </c:if>
		  	  <c:if test="${task.id ne 5}">
		  	   
		  	   	</c:if>
		  	   	 <button type="button" id="${task.id}Button" style="width:60px"
										onmouseover="this.className='buttom2'" onclick="run('${task.taskurl}','${task.id}');"
										onmouseout="this.className='buttom1'" class="buttom1">
										<img src="images/search1.gif" width="13" height="13"
											align="middle">
										启动</button>
		  	   <button type="button"  style="width:60px"
										onmouseover="this.className='buttom2'" onclick="javascript:window.location.href='tongb.do?method=toModiTaskConfig&id=${task.id}';"
										onmouseout="this.className='buttom1'" class="buttom1">
										<img src="images/edit.gif" width="13" height="13"
											align="middle">
										修改</button>
		  	 </td>
		  	  <td class="class1_td center" style="width: 200px">&nbsp;<span id="${task.id}Msg"></span></td>
	        </tr>
        </c:forEach>
						
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

	</body>
</html>
