<%@ page language="java" contentType="text/html;charset=gbk" isELIgnored="false"%>
<%@page import="com.unitop.config.SystemConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ include file="/common/struts1.2.jsp"%>
<%SystemConfig systemConfig = SystemConfig.getInstance();
String yanyinLogType =  systemConfig.getValue("yanyinlogType");
String[] yanyinlogs = yanyinLogType.split("\\|");
request.setAttribute("yanyinlogs",yanyinlogs); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>	
		<script src="js/exportPdf.js" type="text/javascript"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script language="javascript" type="text/javascript" src="js/date/time.js"></script>
		<script src="js/pagejs/validateForm.js" type="text/javascript"></script>
		<script type="text/javascript">   

		function checkZhangh(){
			var zhangh =$("#zhangh").val();
			
			if(zhangh==null||zhangh==""){
				$("#zhanghMsg").html('');
				return true;
			}else{
				var reg =   /(^[0-9]{9}$)|(^[0-9]{17}$)|(^[0-9]{22}$)|(^[0-9]{10,13}$)/;
				if(!reg.test(zhangh)){
					$("#zhanghMsg").html("账号格式不正确");
					return false;
				}else{
					$("#zhanghMsg").html('');
					return true;
				}
			}
		}
		
		function exportPdf(){
			//alert(1);
			var jlist='${jlist}';
			var jsql=$("#jsql").val();
			if(jsql==null||jsql.length==0){
				return;
				}
			if($("#className").val()==null||$("#className").val().length==0){
				return;
				}
			if(jlist=="{}"){
				$("#jlist").val(jlist);
				}else{
			$("#jlist").val(encodeURI(jlist));
			}
			if(jsql.indexOf("%20")==-1){
				$("#jsql").val(encodeURI(jsql));
			}
			$("#exportForm").submit();
	}
		 $(document).ready(function() {	
		 var date = '${clerk.loginDate}';
		 //验证
		 $("#form2").validate({
		    errorLabelContainer:"#error div.error",
		    wrapper:"li",
		    submitHandler:function(form){
		   	 form.submit();
		   		$("#tu").html('<table align="center"><tr><td><img border="1" src="images/appInstall_animated.gif"/></td></tr></table>');
		   }
		 });
		/* $('#account').blur(function(){
		 var code = $("#account").val();
		 $.post("accountlog.do?method=getaccountname", {code:code}, function (data,textStatus) {
		 if (textStatus = "success") {
				$("#accountname").empty();
				$("#accountname").html(data);
		 }
		},"text");
		});*/
		});
		</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;' onload="document.getElementById('tu').innerHTML = '';" >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					日志查询 - 账户变动日志查询  
				</td>
			</tr>
			<tr>
			<!-- onsubmit="if(!checkZhangh()){return false;}" -->
					<html:form styleId="form1" action="accountlog.do?method=find" method="post" >
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
							<td width="2%" class="class1_td">
							&nbsp;
							</td>
								<td  class="class1_td">
									账号：
								</td>
								<td  class="alignleft class1_td">
									<input type="text" id="zhangh"  name="account" class="inputField account " value="${zhangh}"  title="17或22位数字"/>&nbsp;&nbsp;<span style="color: red" id="zhanghMsg"></span>
							  	</td>
								<td  class=" class1_td">
								操作类型：
								</td>
								<td   class="alignleft class1_td">
									<html:select  property="managetype"  >
										<html:option value="全部">全部</html:option>
										<c:forEach items="${yanyinlogs}" var="yanyinlog" varStatus="i">
										<c:if test="${yanyinlog !=''}">
											<html:option value="${i.index}">${yanyinlog}</html:option></c:if>
										</c:forEach>
									</html:select>
							  </td>
							
								<td   class=" class1_td">操作日期：</td>
								<td  class="alignleft class1_td" >
										<html:text property="begindate" styleId="begindate" styleClass="inputField date_input required" maxlength="10" style="width:62px;" onclick="WdatePicker()" />-<html:text property="enddate" styleId="enddate" styleClass="inputField date_input required" maxlength="10" style="width:62px;" onclick="WdatePicker()" />
								</td>
								<td class=" class1_td" >
									<button type="submit" style="width:60px" onmouseover="this.className='buttom2'" onclick="" onmouseout="this.className='buttom1'" class="buttom1">
										<img src="images/search1.gif" width="13" height="13" align="middle">查询
									</button>
								</td>
								<td width="45%" class=" class1_td">&nbsp;</td>
							</tr>
						</table>
					</html:form>
				</td>
			</tr>
		</table>
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange error">
						<div class="error orange">
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
		<form action="ajax.do?method=exportPdf" id="exportForm" method="post">
		<input type="hidden" id="jlist" name="jlist" />
		<input type="hidden" id="jsql" name="jsql"  value="${sql}"/>
		<input type="hidden" id="className" name="className" value="AccountLog"/>
		</form>
		<div id="tu"></div>
		<table width="97%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">&nbsp;
					
				</td>
				<td>
					<%
							if (request.getAttribute("totalRows") == null)
							request.setAttribute("totalRows", new Integer(0));
					%>
<%--					<button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onClick="exportPdf();">
								<img src="images/add.gif" width="13" height="13" align="absmiddle"/>导出</button>--%>
					<ec:table
						retrieveRowsCallback="org.extremecomponents.table.callback.LimitCallback"
						filterRowsCallback="org.extremecomponents.table.callback.LimitCallback"
						sortRowsCallback="org.extremecomponents.table.callback.LimitCallback"
						filterable="false" sortable="false" title="账户日志查询结果"
						showPagination="true" view="compact" items="list" var="accountlog"
						action="${pageContext.request.contextPath}/accountlog.do?method=find"
						imagePath="${pageContext.request.contextPath}/images/table/*.gif">
						<c:choose>
							<c:when test="${totalRows>=60000}">
								<ec:exportCsv fileName="log.txt" tooltip="Export Text" />
							</c:when>
							<c:otherwise>
								<ec:exportXls fileName="log.xls" tooltip="Export Excel" />
							</c:otherwise>
						</c:choose>
						<ec:row>
							<ec:column property="NETPOINTFLAG" title="机构号" />
							<ec:column property="account" title="账号" />
							<ec:column property="clerknum" title="柜员代码" />
							<ec:column property="caozlx" title="操作类型" />
							<ec:column property="operdate" title="操作时间" />
							<ec:column property="content" title="操作内容" />
						</ec:row>
					</ec:table>
				</td>
				<td background="images/table_arrow_07.gif">&nbsp;
				</td>
			</tr>
			<tr>
				<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
			</tr>
		</table>
		<div class="stat"></div>
	</body>
</html>