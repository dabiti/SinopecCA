<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/shield.js"></script>
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script src="js/pagejs/validateForm.js" type="text/javascript"></script>
		<script type="text/javascript" type="text/javascript">
		function validata(key){
			var reg="";
			if(key==1){
				reg=/^([1-9]|([1-9]\d)|([1][0-9][0-9])|([2][0-5][0-5])).(\d|([1-9]\d)|([1][0-9][0-9])|([2][0-5][0-5])).(\d|([1-9]\d)|([1][0-9][0-9])|([2][0-5][0-5])).(\d|([1-9]\d)|([1][0-9][0-9])|([2](([0-4][0-9])|([5][0-5]))))$/;
				$("#zidkzipMsg").html("");
				var zidkzip=$("#zidkzip").val();
				if(zidkzip==null||zidkzip.length==0){
					$("#zidkzipMsg").html("作业中心IP地址不能为空");
					return false;
				}
				if(!reg.test(zidkzip)){
					$("#zidkzipMsg").html("作业中心IP地址格式不正确");
					return false;
				}
				$("#zidkzipMsg").html("");
				return true;
				
			}
			if(key==2){
				reg=/^([1-9]|([1-9]\d)|([1][0-9][0-9])|([2][0-5][0-5])).(\d|([1-9]\d)|([1][0-9][0-9])|([2][0-5][0-5])).(\d|([1-9]\d)|([1][0-9][0-9])|([2][0-5][0-5])).(\d|([1-9]\d)|([1][0-9][0-9])|([2](([0-4][0-9])|([5][0-5]))))$/;
				$("#zidkzipbMsg").html("");
				var zidkzipb=$("#zidkzipb").val();
				if(zidkzipb==null||zidkzipb.length==0){
					return true;
				}
				if(!reg.test(zidkzipb)){
					$("#zidkzipbMsg").html("作业中心备用IP地址格式不正确");
					return false;
				}
				$("#zidkzipMsg").html("");
				return true;
			}
			if(key==3){
				reg=/^[0-9]{1,5}$/;
				$("#zidkzportMsg").html("");
				var port=$("#zidkzport").val();
				if(port==null||port.length==0){
					$("#zidkzportMsg").html("作业中心端口不能为空");
					return false;
				}
				if(!reg.test(port)){
					$("#zidkzportMsg").html("作业中心端口格式不正确");
					return false;
				}
				if(port<1||port>25535){
					$("#zidkzportMsg").html("作业中心端口格式不正确");
					return false;
				}
				$("#zidkzportMsg").html("");
				return true;
			}
			if(key==4){
				reg=/(^[0-9]$)|(^[1-9][0-9]$)/;
				$("#zidkzcsMsg").html("");
				var zidkzcs=$("#zidkzcs").val();
				if(zidkzcs==null||zidkzcs.length==0){
					$("#zidkzcsMsg").html("超时时长不能为空");
					return false;
				}
				if(!reg.test(zidkzcs)){
					$("#zidkzcsMsg").html("超时时长格式不正确");
					return false;
				}
				$("#zidkzcsMsg").html("");
				return true;
			}
			if(key==5){
				reg=/^[0-9]{1,10}$/;
				$("#nodeidMsg").html("");
				var port=$("#nodeid").val();
				if(port==null||port.length==0){
					$("#nodeidMsg").html("节点号不能为空");
					return false;
				}
				if(!reg.test(port)){
					$("#nodeidMsg").html("节点号格式不正确");
					return false;
				}
				$("#nodeidMsg").html("");
				return true;
			}
		}
		function checkData(){
			if(!validata(1)) return false;
			if(!validata(2)) return false;
			if(!validata(3)) return false;
			if(!validata(4)) return false;
			if(!validata(5)) return false;
			return true;
		}
		function formSubmit(){
			if(!checkData()) return false;
			//if(!validata(2)) return false;
			$("#form1").submit();
			}
		</script>
		
		<%@ include file="/common/yewgz.jsp"%>	
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;' >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					系统管理 - 修改异步验印参数信息
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
			<form id=error>
				<div id="orange" class="orange ">
					<html:errors />
				</div>
				<div id="orange" class="orange error">
				</div>
			</form>
		<form id="form1"   action="orgManage.do?method=updateAsynSealCheckConfig" method="post"  >
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
									<th colspan="2" class="class1_thead th">
										修改异步验印参数信息
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									机构号：
								</td>
								<td class="class1_td alignleft">
								<c:out value="${asynSealCheckConfig.jigh}"></c:out>
								<input name="jigh" type="hidden" value="${asynSealCheckConfig.jigh}">
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									机构名称：
								</td>
								<td class="class1_td alignleft">
									<c:out value="${asynSealCheckConfig.jigmc}"></c:out>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									作业中心IP地址：
								</td>
								<td class="class1_td alignleft">
								<input name="zidkzip" id="zidkzip" type="text" class="inputField" style="width:180px" value="${asynSealCheckConfig.zidkzip}" onblur="validata(1);"  onkeydown="if(event.keyCode==13){if(!validata(1)){this.focus();return false;}}">
								<span style="color: red">*&nbsp;</span><span id="zidkzipMsg" style="color: red"></span>
								</td>
								
							</tr>
							<tr>
								<td class="class1_td w250">
									作业中心备用IP地址：
								</td>
								<td class="class1_td alignleft">
								<input name="zidkzipb" id="zidkzipb" type="text" class="inputField" style="width:180px" value="${asynSealCheckConfig.zidkzipb}" onblur="validata(2);"  onkeydown="if(event.keyCode==13){if(!validata(2)){this.focus();return false;}}">
								<span style="color: red">&nbsp;&nbsp;</span><span id="zidkzipbMsg" style="color: red"></span>
								</td>
								
							</tr>
							<tr>
								<td class="class1_td w250">
									作业中心端口：
								</td>
								<td class="class1_td alignleft">
									<input name="zidkzport" id="zidkzport" maxlength="5" type="text" class="inputField" style="width:180px" value="${asynSealCheckConfig.zidkzport}" title="1~25535" onblur="validata(3);"  onkeydown="if(event.keyCode==13){if(!validata(3)){this.focus();return false;}}">
									<span style="color: red">*&nbsp;</span><span id="zidkzportMsg" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									超时时长(单位:秒)：
								</td>
								<td class="class1_td alignleft">
									<input name="zidkzcs" id="zidkzcs" maxlength="2" type="text" class="inputField" style="width:180px" value="${asynSealCheckConfig.zidkzcs}"  onblur="validata(4);"  title="0~99" onkeydown="if(event.keyCode==13){if(!validata(4)){this.focus();return false;}}">
									<span style="color: red">*&nbsp;</span><span id="zidkzcsMsg" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									节点号：
								</td>
								<td class="class1_td alignleft">
									<input name="nodeid" id="nodeid" maxlength="10" type="text" class="inputField" style="width:180px" value="${asynSealCheckConfig.nodeid}" title="0~9999999999"  onblur="validata(5);"  onkeydown="if(event.keyCode==13){if(!validata(5)){this.focus();return false;}}">
									<span style="color: red">*&nbsp;</span><span id="nodeidMsg" style="color: red"></span>
								</td>
							</tr>
						</table>
					</td>
					<td background="images/table_arrow_07.gif">&nbsp;</td> 
				</tr>
				<tr>
					<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
				</tr>
			</table>
		<div class="funbutton">
			<button type="button" style="width:60px" 
				onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1" onclick="formSubmit();" onkeydown="if(event.keyCode==13){formSubmit();}">
				<img src="images/save1.gif" width="12" height="12" align="absmiddle">
				保存
			</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button" style="width:60px" onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1"
				onclick="window.location.href='orgManage.do?method=forAsynSealCheckConfig'">
				<img src="images/back1.gif" width="11" height="11" align="absmiddle">
				取消
			</button>
		</div>
		</form>
	</body>
</html>