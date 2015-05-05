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
		<script src="js/pagejs/validateForm.js" type="text/javascript"></script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
	<input id="sessionURL" name="sessionURL" type=hidden value="${sessionURL}"/>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					日志查询 - 验印结果当日查询
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					<html:form styleId="form1" action="/sealchecklog" method="post">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="class1_td w70">
									账号
								</td>
								<td class="class1_td alignleft">
									<html:text property="account" styleId="account" styleClass="inputField required account" /><font color=red>*</font>
								</td>
								<td class="class1_td w70">
									凭证号
								</td>
								<td class="class1_td alignleft">
									<html:text property="checknum" styleId="checknum" styleClass="inputField required checknum" /><font color=red>*</font>
								</td>
								<td class="class1_td alignleft">
									&nbsp;
								</td>
								<td class="class1_td alignleft">
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
		<br>
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
					<ec:table filterable="false" sortable="false" title="验印结果"
						showPagination="true" view="compact" items="list"
						var="sealchecklog"  rowsDisplayed="${ec_yemjlts}" 
						action="${pageContext.request.contextPath}/sealchecklog.do"
						imagePath="${pageContext.request.contextPath}/images/table/*.gif">
						<c:choose>
							<c:when test="${size>=60000}">
								<ec:exportCsv fileName="sealchecklog.txt" tooltip="Export Text" />
							</c:when>
							<c:otherwise>
								<ec:exportXls fileName="sealchecklog.xls" tooltip="Export Excel" />
							</c:otherwise>
						</c:choose>
						<ec:row>
							<ec:column property="id.account" title="账号" />
							<ec:column property="id.sealinknum" title="印鉴编号" />
							<ec:column property="qiyrq" title="启用日期" />
							<ec:column property="id.sealinktype" title="印鉴类型" />
							<ec:column property="checkresult" title="验印结果" />
							<ec:column property="checkmode" title="验印方式" />
							<ec:column property="clerknum" title="柜员代码" />
							<ec:column property="id.checktime" title="验印时间" />
							<unitop:HasPrivilegeForSystemConfig name="yingjkll">
							<ec:column property="浏览" title="浏览预览印鉴" >
							<a href='javascript:try{obj.ShowOcxForm("{\"ocxid\":\"4\",\"guiyxx\":${jsonClerkrStr},\"ocxparam\":{\"zhangh\":\"${sealchecklog.id.account}\",\"yinjbh\":\"${sealchecklog.id.sealinknum}\",\"qiyrq\":\"${sealchecklog.qiyrq}\"}}");}catch(e){alert("系统检测：本机没有安装验印插件!");};'>浏览</a>
							</ec:column>
							</unitop:HasPrivilegeForSystemConfig>
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
	    <OBJECT ID="obj" CLASSID="clsid:87100b1f-19fa-4266-a03e-0536ffa3c8af" style="display:none"></OBJECT>
	</body>
</html>
