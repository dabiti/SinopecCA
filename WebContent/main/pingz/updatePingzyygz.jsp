<%@page language="java" contentType="text/html;charset=gbk"
	isELIgnored="false"%>
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
<%--		<%@ include file="/common/yewgz.jsp"%>--%>
		<script type="text/javascript">
		function validata(key){
			var reg="";
			if(key==1){
				$("#pingzmcMsg").html("");
				var pingzmc=$("#pingzmc").val();
				if(pingzmc==null||pingzmc.length==0){
					$("#pingzmcMsg").html("ƾ֤���Ʋ���Ϊ��!");
					return false;
				}
				
				$("#pingzmcMsg").html("");
				return true;
			}
			if(key==2){
				$("#shunxMsg").html("");
				var shunx=$("#xianssx").val();
				if(shunx==null||shunx.length==0){
					$("#shunxMsg").html("");
					return true;
				}
				reg=/^[0-9]{3}$/;
				if(!reg.test(shunx)){
					$("#shunxMsg").html("˳���Ÿ�ʽ����ȷ!");
					return false;
				}
				$("#shunxMsg").html("");
				return true;
			}
		}

		function formSubmit(){
			if(!validata(1))return;
			if(!validata(2))return;
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
					ƾ֤��ӡ��������-�޸�ƾ֤��ӡ����
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
		<form id="form1" action="yinjManage.do?method=updatePingzyygz" method="post"  >
			
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
										�޸�ƾ֤��ӡ����
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td alignright">
									ƾ֤��ʶ��
								</td>
								<td class="class1_td alignleft" >
									<input type="hidden" name="pingzbs" id="pingzbs" value="${pingz.pingzbs}">
									${pingz.pingzbs}
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									ƾ֤���ƣ�
								</td>
								<td class="class1_td alignleft" >
									<input type="text" id="pingzmc"  class="inputField" style="width:180px" name="pingzmc" onblur="validata(1);"  onkeydown="if(event.keyCode==13){if(!validata(1)){this.focus();return false;}}" value="${pingz.pingzmc}"/><span style="color:red">*&nbsp;</span><span style="color:red" id="pingzmcMsg"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									��ӡ����
								</td>
								<td class="class1_td alignleft" >
									<select name="guizbh" id="guizbh"  class="select required yanylx" >
										<c:forEach items="${gzlist}" var="itt">
											<option value="${itt.guizbh}" title="${itt.yanyingz}" ${pingz.yanygz.guizbh == itt.guizbh?'selected="selected"':'' }>
												${itt.guizmc}
											</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									�Ƿ���ʾ��
								</td>
								<td class="class1_td alignleft" >
									<select name="shifxs" id="shifxs"  class="select required yanylx" >
											<option value="��"  ${pingz.shifxs == "��"?'selected="selected"':'' }>
												��
											</option>
											<option value="��"  ${pingz.shifxs == "��"?'selected="selected"':'' }>
												��
											</option>
									</select><span>&nbsp;&nbsp;�Ƿ���ǰ̨��ӡҳ����ʾ��ƾ֤</span>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									˳���ţ�
								</td>
								<td class="class1_td alignleft" >
									<input type="text" id="xianssx"  class="inputField" style="width:45px" name="xianssx" onblur="validata(2);"  onkeydown="if(event.keyCode==13){if(!validata(2)){this.focus();return false;}}" value="${pingz.xianssx}"/><span style="color:red" id="shunxMsg"></span>
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
					onclick="window.location.href='yinjManage.do?method=forPingzgz'">
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					ȡ��
				</button>
			</div>
		</form>
	</body>
</html>