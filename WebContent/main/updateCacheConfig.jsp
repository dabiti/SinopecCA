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
				$("#ipMsg").html("");
				var ip=$("#ip").val();
				if(ip==null||ip.length==0){
					$("#ipMsg").html("影像缓存IP地址不能为空");
					return false;
				}
				if(!reg.test(ip)){
					$("#ipMsg").html("影像缓存IP地址格式不正确");
					return false;
				}
				$("#ipMsg").html("");
				return true;
				
			}
			
			if(key==2){
				reg=/^[0-9]{1,5}$/;
				$("#portMsg").html("");
				var port=$("#port").val();
				if(port==null||port.length==0){
					$("#portMsg").html("影像缓存端口不能为空");
					return false;
				}
				if(!reg.test(port)){
					$("#portMsg").html("影像缓存端口格式不正确");
					return false;
				}
				if(port<1||port>25535){
					$("#portMsg").html("影像缓存端口格式不正确");
					return false;
				}
				$("#portMsg").html("");
				return true;
			}
				if(key==3){
				reg=/(^[0-9]$)|(^[1-9][0-9]{1,4}$)/;
				$("#timeoutMsg").html("");
				var timeout=$("#timeout").val();
				if(timeout==null||timeout.length==0){
					$("#timeoutMsg").html("超时时长不能为空");
					return false;
				}
				if(!reg.test(timeout)){
					$("#timeoutMsg").html("超时时长格式不正确");
					return false;
				}
				$("#timeoutMsg").html("");
				return true;
			}
			
		}
		function checkData(){
			if(!validata(1)) return false;
			if(!validata(2)) return false;
			if(!validata(3)) return false;
			return true;
		}
		function formSubmit(){
			if(!checkData()) return false;
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
					系统管理 - 修改影像缓存参数信息
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
		<form id="form1"   action="orgManage.do?method=updateCacheConfig" method="post"  >
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
										修改影像缓存参数信息
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									机构号：
								</td>
								<td class="class1_td alignleft">
								<c:out value="${cacheConfig.jigh}"></c:out>
								<input name="jigh" type="hidden" value="${cacheConfig.jigh}">
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									机构名称：
								</td>
								<td class="class1_td alignleft">
									<c:out value="${cacheConfig.jigmc}"></c:out>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									影像缓存IP地址：
								</td>
								<td class="class1_td alignleft">
								<input name="ip" id="ip" type="text" class="inputField" style="width:180px" value="${cacheConfig.ip}" onblur="validata(1);"  onkeydown="if(event.keyCode==13){if(!validata(1)){this.focus();return false;}}">
								<span style="color: red">*&nbsp;</span><span id="ipMsg" style="color: red"></span>
								</td>
								
							</tr>
							
							<tr>
								<td class="class1_td w250">
									影像缓存端口：
								</td>
								<td class="class1_td alignleft">
									<input name="port" id="port" maxlength="5" type="text" class="inputField" style="width:180px" value="${cacheConfig.port}" title="1~25535" onblur="validata(2);"  onkeydown="if(event.keyCode==13){if(!validata(2)){this.focus();return false;}}">
									<span style="color: red">*&nbsp;</span><span id="portMsg" style="color: red"></span>
								</td>
							</tr>
							
							<tr>
								<td class="class1_td w250">
									超时时长(单位:毫秒)：
								</td>
								<td class="class1_td alignleft">
									<input name="timeout" id="timeout" maxlength="5" type="text" class="inputField" style="width:180px" value="${cacheConfig.timeout}"  onblur="validata(3);"  title="0~99" onkeydown="if(event.keyCode==13){if(!validata(3)){this.focus();return false;}}">
									<span style="color: red">*&nbsp;</span><span id="timeoutMsg" style="color: red"></span>
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
				onclick="window.location.href='orgManage.do?method=forCacheConfig'">
				<img src="images/back1.gif" width="11" height="11" align="absmiddle">
				取消
			</button>
		</div>
		</form>
	</body>
</html>