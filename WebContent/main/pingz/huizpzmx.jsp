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
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>		
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					凭证明细查询
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
				</td>
			</tr>
		</table>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" style="width:60px"
				onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1"
		        onClick="history.back()">
				<img src="images/back1.gif" width="13" height="13"
					 align="absmiddle">
				退出
		   </button>
		   <br/>
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
						request.setAttribute("totalRows",new Integer(1));
				%>
					<ec:table 
					  retrieveRowsCallback="org.extremecomponents.table.callback.LimitCallback"
        			  filterRowsCallback="org.extremecomponents.table.callback.LimitCallback" 
			          sortRowsCallback="org.extremecomponents.table.callback.LimitCallback"
					  filterable="false" sortable="false"  rowsDisplayed="${ec_yemjlts}" 
					  action="pingz.do?method=getPingzListByPich"
					  title="凭证明细查询列表" showPagination="true" view="compact"
					  items="list"
					  imagePath="${pageContext.request.contextPath}/images/table/*.gif">
						<ec:exportXls fileName="pingzmxcx.xls" tooltip="Export Excel" />
						<ec:row>
							<ec:column property="pich" title="批次号" />
							<ec:column property="pingzh" title="凭证号" />
							<ec:column property="jigh" title="机构号" />
							<ec:column property="guiyh" title="柜员号" />
							<ec:column property="zhangh" title="账号" />
							<ec:column property="hum" title="户名" />
							<ec:column property="pingzlx" title="凭证类型" />
							<ec:column property="riq" title="日期" />
							<ec:column property="shij" title="时间" />
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
		<br/>
		<div class="stat"></div>
	</body>
</html>