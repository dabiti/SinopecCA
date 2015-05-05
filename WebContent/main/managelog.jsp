<%@ page language="java" contentType="text/html;charset=gbk"  isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript" src="js/date/time.js"></script>
		<script src="js/pagejs/validateForm.js" type="text/javascript"></script>
		<script src="js/exportPdf.js" type="text/javascript"></script>
		<%@ include file="/common/validator.jsp"%>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript" language="javascript">
		function exportPdf(){
			var jlist='${jlist}';
			var jsql=	$("#jsql").val();
			if(jlist==null||jlist.length==0){
				return;
				}
			if($("#className").val()==null||$("#className").val().length==0){
				return;
				}
			$("#jlist").val(encodeURI(jlist));
			if(jsql.indexOf("%20")==-1){
				$("#jsql").val(encodeURI(jsql));
			}
			$("#exportForm").submit();
	}
		</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>		
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					日志查询 - 管理员日志查询 
				</td>
			</tr>
			<tr>
				<td  class=" class1_td">
					&nbsp;
				</td>
				<td class="class1_td" style="text-align: left">
					<html:form styleId="form1" method="post" action="/managelog">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr class="alignleft">
								<td class="alignleft class1_td"></td>
								<td class=" alignright w90">
									柜员号：
								</td>
								<td class="alignleft ">
									<html:text styleId="clerknum" property="admincode" styleClass="inputField required" /><font color=red>*</font>
								</td>
								<td class="alignleft "></td>
								<td class=" alignright w90">
										起止日期：
								</td>
								<td class=" alignleft">
									
								<html:text property="begindate" styleId="begindate"
													styleClass="inputField date_input required" maxlength="10"
													style="width:62px;" onclick="WdatePicker()" />
												－
								<html:text property="enddate" styleId="enddate"
													styleClass="inputField date_input required" maxlength="10"
													style="width:62px;" onclick="WdatePicker()" />
								</td>
								<td class=" aligncenter w70">
									<button type="submit" style="width:60px"
										onmouseover="this.className='buttom2'"
										onmouseout="this.className='buttom1'" class="buttom1">
										<img src="images/search1.gif" width="13" height="13"
											align="middle">
										查询
									</button>
								</td>
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
						<div class="error orange"></div>
					</td>
				</tr>
				<logic:messagesPresent>
					<tr>
						<td class="orange">
							<div id=abc class="abc"><html:errors /></div>
						</td>
					</tr>
				</logic:messagesPresent>
			</table>
		</form>
		<br/>
		<form action="ajax.do?method=exportPdf" id="exportForm" method="post">
		<input type="hidden" id="jlist" name="jlist" />
		<input type="hidden" id="jsql" name="jsql"  value="${jsql}"/>
		<input type="hidden" id="className" name="className" value="SystemManageLog"/>
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
				<% 
				if(request.getAttribute("totalRows")==null)
					request.setAttribute("totalRows",new Integer(0));
				%>
			<!-- 	<button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onClick="exportPdf();">
									<img src="images/add.gif" width="13" height="13" align="absmiddle"/>导出</button> -->
					<ec:table 
					  retrieveRowsCallback="org.extremecomponents.table.callback.LimitCallback"
        			  filterRowsCallback="org.extremecomponents.table.callback.LimitCallback" 
			          sortRowsCallback="org.extremecomponents.table.callback.LimitCallback"
						filterable="false" sortable="false"
						title="管理员日志查询结果" showPagination="true" view="compact"
						items="list" var="adminlog"  rowsDisplayed="${ec_yemjlts}" 
						action="${pageContext.request.contextPath}/managelog.do"
						imagePath="${pageContext.request.contextPath}/images/table/*.gif">
						<ec:exportXls fileName="amdimlog.xls" tooltip="Export Excel" />
						<ec:row>
							<ec:column property="admincode" title="柜员号" cell="org.extremecomponents.table.cell.DisplayCell" />
							<ec:column property="content" title="操作内容" />
							<ec:column property="operdate" title="操作时间" />
							<ec:column property="ip" title="IP地址" />
						</ec:row>
					</ec:table>
				</td>
				<td background="images/table_arrow_07.gif">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
			</tr>
		</table>
		<div class="stat"></div>
	</body>
</html>