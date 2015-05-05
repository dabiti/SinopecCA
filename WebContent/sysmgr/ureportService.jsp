<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link href="style/reportcss.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script src="js/jquery.date_input.js" type="text/javascript"></script>
		<script src="js/exportPdf.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript" src="js/date/time.js"></script>
			<%@ include file="/common/yewgz.jsp"%>
					<%@ include file="/common/validator.jsp"%>
		<script type="text/javascript">
		 $(document).ready(function() {
			 $("#form1").validate({
			   /*onsubmit:true,
			   onfocusout:true,
			   onkeyup :true,*/
			   errorLabelContainer:"#error div.error",
			   wrapper:"li",
			   submitHandler:function(form){
				   form.submit();
				   $("#tu").html('<table align="center"><tr><td><img border="1" src="images/appInstall_animated.gif"/><b> 检索中，请稍等......<b></td></tr></table>');
				   }
			   });
			   });
		  function toChangeFormLink(id){
			  var values = id.split("=");
			  $("#"+values[0].toUpperCase()).val(values[1]);
			  $("#form1").submit();
		  }
		</script>
		<script type="text/javascript">
			/* function startAjax(sql,message) {
					var math = Math.random();
					$.post("ajax.do?method=getMessage", {sql:sql,message:message.value}, function (data, textStatus)
					{
						if (textStatus = "success") 
						{
							$("#"+sql+"Message").empty();
							$("#"+sql+"Message").html(data);
						}
					}, "text");
				}*/
			 function exportPdf(){
					var jlist='${jlist}';
					//var jsql=$("#jsql").val();
					var jsql ='${jsql}';
					if(jsql==null||jsql.length==0){
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
	
		<form action="ajax.do?method=exportPdf" id="exportForm" method="post">
		<input type="hidden" id="jlist" name="jlist" />
		<input type="hidden" id="jsql" name="jsql" />
		<input type="hidden" id="className" name="className" value="Map${report.baobbs}"/>
		</form>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					${report.baobmc}
				</td>
			</tr>
		<tr>
			<td class="class1_td">&nbsp;</td>
			<td class="class1_td" style="text-align: left">
				<form id="form1" name="form1" method="post" action="reportService.do?method=exeReport&baobbs=${report.baobbs}">
					<table>
					<tr >
						<td >
							<table width="100%" border="0" cellspacing="1" cellpadding="0">
							<tr >
								${formStringForHTML}
								<td >
									<button type="submit" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1">
									<img src="images/search1.gif" width="13" height="13" align="middle"/>查询 </button>
								</td>
							</tr>
							</table>
						</td>
						</tr>
						
							
					</table>
				</form>
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
		<div id="tu"></div>
		<br/>
		<table width="97%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">
				</td>
				<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="0"
						class="class1_table">
						
							<tr>
								<!-- <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onClick="exportPdf();">
									<img src="images/add.gif" width="13" height="13" align="absmiddle"/>导出</button> -->
								<td>
									<ec:table 
										retrieveRowsCallback="org.extremecomponents.table.callback.LimitCallback"
										filterRowsCallback="org.extremecomponents.table.callback.LimitCallback"
										sortRowsCallback="org.extremecomponents.table.callback.LimitCallback"
										items="list" filterable="false" var="dataListForOne"  sortable="false"  title="查询列表"
										showPagination="true" rowsDisplayed="${report.fenytj}"  view="compact"
										action="reportService.do?method=exeReport&baobbs=${report.baobbs}"
										imagePath="${pageContext.request.contextPath}/images/table/*.gif">
										<c:if test="${report.baobbs eq '002'}">
										<unitop:HasPrivilegeForZignTag name="60001|EX_001">
										<ec:exportXls fileName="Excel.xls" tooltip="Excel数据导出" />
										</unitop:HasPrivilegeForZignTag>	
										</c:if>
										<c:if test="${report.baobbs != '002'}">
										<ec:exportXls fileName="Excel.xls" tooltip="Excel数据导出" />
										</c:if>
										<ec:row>
											<c:forEach items="${reportResult.reportResultList}" var="list">
												<c:if test="${list.yaosmc !='areacode'&&list.yaosmc !='areacode2'&&list.yaosmc !='areacode3'&&list.yaosmc !='areacode4'&&list.yaosmc !='extensionnumber'&&list.yaosmc !='extensionnumber2'&&list.yaosmc !='extensionnumber3'&&list.yaosmc !='extensionnumber4'}">
													<ec:column property="${list.yaosmc}" title="${list.yaosbt}"  style="${list.shifxs=='否'?'display:none':''}" headerStyle="${list.shifxs=='否'?'display:none':''}">
														<c:if test="${list['xianslx']=='链接显示'}">
															<a title='点击查看明细' href="${list.beiz}${dataListForOne[list['yaosmc']]}">${dataListForOne[list['yaosmc']]}</a>
														</c:if>
														<c:if test="${list['xianslx']=='报表链接'}">
															<a onclick="javascript:toChangeFormLink('${list['yaosmc']}=${dataListForOne[list['yaosmc']]}');"  href="#"; >${dataListForOne[list['yaosmc']]}</a>
														</c:if>
														<c:if test="${list['xianslx']=='序号显示'}">
															${dataListForOne[list['yaosmc']]}
														</c:if>
														<c:if test="${list['xianslx']=='模式窗口'}">
															<a onclick="javascript:window.showModalDialog('${list.beiz}${dataListForOne[list['yaosmc']]}&rid='+new Date(),'','dialogWidth:650px;dialogHeight:500px;help:no;scroll:yes;center:yes;status:no');" href='#'>${dataListForOne[list['yaosmc']]}</a>
														</c:if>
													</ec:column>
													</c:if>
											</c:forEach>
										</ec:row>
									</ec:table>
									</td>
									</tr>
									
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