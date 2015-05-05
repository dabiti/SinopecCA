<%@page language="java" contentType="text/html;charset=gbk" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script src="js/pagejs/ocx.js" type="text/javascript"></script>
				<script src="js/exportPdf.js" type="text/javascript"></script>
		<script src="js/pagejs/validateForm.js" type="text/javascript"></script>
		<script type="text/javascript" language="javascript">
		
		function exportPdf(){
			var jlist='${jlist}';
			var jsql=$("#jsql").val();
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
	<input id="sessionURL" name="sessionURL" type=hidden value="${sessionURL}"/>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					印鉴卡共用查询
				</td>
			</tr>
			<tr>
				<td class=" class1_td">
					&nbsp;
				</td>
				<td class=" class1_td" style="text-align: left">
					<html:form styleId="form1" action="accountinfo.do?method=queryyinjkshare" method="post" >
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td >
									机构号：
								</td>
								<td class=" alignleft">
									<html:text property="orgcode" styleId="jigh" styleClass="inputField required jigh" /><font color=red>*</font>
								</td>
								<td >
									账号：
								</td>
								<td class=" alignleft">
									<html:text property="account" styleId="account" styleClass="inputField account" />
								</td>
								<td >
									印鉴卡编号：
								</td>
								<td class=" alignleft">
									<html:text property="yinjkbh" styleId="yinjkbh"  styleClass="inputField yinjkbh" maxlength="22"/>
								</td>
								<td class=" alignleft">
									&nbsp;
								</td>
								<td class=" alignleft">
									&nbsp;
								</td>
								<td class="w70">
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
							<div id=abc class="abc">
								<html:errors />
							</div>
						</td>
					</tr>
				</logic:messagesPresent>
			</table>
		</form>
		<br/>
		<form action="ajax.do?method=exportPdf" id="exportForm" method="post">
		<input type="hidden" id="jlist" name="jlist" />
		<input type="hidden" id="jsql" name="jsql"  value="${jsql}"/>
		<input type="hidden" id="className" name="className" value="Zhanghb"/>
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
					<!-- <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onClick="exportPdf();">
									<img src="images/add.gif" width="13" height="13" align="absmiddle"/>导出</button> -->
					<ec:table  retrieveRowsCallback="org.extremecomponents.table.callback.LimitCallback"
        			  filterRowsCallback="org.extremecomponents.table.callback.LimitCallback" 
			          sortRowsCallback="org.extremecomponents.table.callback.LimitCallback"
			           filterable="false" sortable="false" title="查询结果"
						showPagination="true" view="compact" items="list"
						var="zhanghb"  rowsDisplayed="${ec_yemjlts}" 
						action="${pageContext.request.contextPath}/accountinfo.do?method=queryyinjkshare"
						imagePath="${pageContext.request.contextPath}/images/table/*.gif">
						<c:choose>
							<c:when test="${size>=60000}">
								<ec:exportCsv fileName="yinjkshare.txt" tooltip="Export Text" />
							</c:when>
							<c:otherwise>
								<ec:exportXls fileName="yinjkshare.xls" tooltip="Export Excel" />
							</c:otherwise>
						</c:choose>
						<ec:row>
							<ec:column property="zhangh" title="账号" />
							<ec:column property="hum" title="户名" />
							<ec:column property="zhuzh" title="共用账号" />
							<ec:column property="yinjkbh" title="印鉴卡编号" />
							<ec:column property="zhanghxz" title="账户性质" />
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
	    	<script language="JavaScript" FOR="obj" EVENT="SendEvent(sendMessage)">
	 		var rMessage = SendSesssion("uniDBInterface.jsp",sendMessage,"text");
    		return rMessage;
	    </script>
	</body>
</html>
