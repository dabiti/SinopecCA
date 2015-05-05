<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ page import="com.unitop.config.SystemConfig"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/title.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
		<link href="style/default.css" rel="stylesheet" type="text/css" />
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script src="js/pagejs/validateForm.js" type="text/javascript"></script>
		<script src="js/authorized.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>	
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript">
		
	
		
		
		function login(){
				var clerktype = $("#clerkType").val();
				if(validateInput(clerktype)){
							form1.submit();
				}
		}
		
		function validateInput(clerktype){
			var reg;
			if($("#clerknum_7")==null||$("#clerknum_7").val()==""){
					alert("柜员号不可为空");
					return false;
			}else{
					reg = /^\d{5,7}$/;
					if(reg!=null&&!reg.test($("#clerknum_7").val())){
						alert("柜员号格式不正确");
						return false; 
					}
			};
				if($("#password")==null||$("#password").val()==""){
					alert("密码不可为空");
					return false;
				}else{
					reg = /\w{5,8}/;
					if(reg!=null&&!reg.test($("#password").val())){
						alert("密码格式不正确");
						return false;
					}
				};
				return true;
			return false;
		}
		
		
		</script>
		
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
	
		<table width="1145" border="0" cellspacing="0" cellpadding="0" align="center">
			<tr>
				<td class="login">
				
					<html:form styleId="form1" action="login.do?method=loginsys" method="post">
						<table width="400" border="0" cellspacing="0" cellpadding="0"
							class="input_table">
							<tr>
								<td>
								<!--  <img src="images/ui/logo01.gif" />-->	
								</td>
								<td align="right">
									<!-- <img src="images/ui/logo02.gif" /> -->
								</td>
							</tr>
							<tr>
								<td colspan="2">
									
									<table width="260" border="0" cellspacing="0" cellpadding="0" class="input_content">
										<tr>
											<td height="40">
												登录代码 
												<html:text styleId = "clerknum_7" property="code"  styleClass="ipt required" onkeydown="if(event.keyCode==13)event.keyCode=9"/>
											</td>
										</tr>
										<c:if test='<%=request.getAttribute("logintype")==null%>'>
										<tr>
												<td height="40">
												登录密码
												<input id="password" name="password" type="password" class="ipt" maxlength="140"  />
												</td>
											
										</tr>
								
										</c:if>
										<tr>
												<td height="40">
												<input id="clerkType"  name="clerkType" type="hidden" value="0"/>
												</td>
										</tr>
										
										<tr style="display:none">
											<td height="40">
												<input name="typeofrequest" id="typeofrequest" type="text" value=""/>
												<textarea rows="5" cols="5" id="xiaohxx" name="xiaohxx"></textarea>
												<textarea rows="5" cols="5" id="jiaoywxx" name="jiaoywxx"></textarea>
												<textarea rows="5" cols="5" id="kaihxx" name="kaihxx"></textarea>
												<textarea rows="5" cols="5" id="zilxgxx" name="zilxgxx"></textarea>
											</td>
										</tr>
									</table>
									
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<table width="240" border="0" cellspacing="0" cellpadding="0"
										align="right">
										<tr>
											<c:if test='<%=request.getAttribute("logintype")==null%>'>
											<br/>
											<td width="91">
												<input type="button" name="dl"  title="登陆系统" value=""
													class="input_button01" onclick="login();" onkeydown="if(event.keyCode==13){login();}" />
											</td>
											<td width="169">
											<%
												SystemConfig systemConfig = SystemConfig.getInstance();
												String forcedtosign = systemConfig.getValue("forcedtosign");
												request.setAttribute("forcedtosign", forcedtosign);
											%>
											<c:if test='${forcedtosign==1}'>
												<input name="qzqt" type="button" value="" title="强制签退" class="input_button02" onclick="javascript:window.location.href='forcedtosign.jsp';" />
											</c:if>
											</td>
											</c:if>
										</tr>
										
										
										<tr>
											<td  colspan="2">
												<br/>
												<form id=error method=post>
												<span id="orange" class="orange">
													<html:errors />
												</span>
												</form>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</html:form>	
					<div class="div">
					</div>
					<p class="txt">
<%--						版权所有：     系统版本：中石化对账系统 V1.0--%>
					</p>
				</td>
			</tr>
		</table>
		<object classid="clsid:7D131444-0A2F-4F37-9605-6E1BF067AF18" width="0" height="0" id="dtm" codebase="JZT998_For_Huaxia.ocx">
</object>
<object width="0" height="0" id="updateocx"
			classid="clsid:33575fd4-da11-4d96-b441-d506992128fa"></object>
	</body>
	<script type="text/javascript">
	</script>
</html>