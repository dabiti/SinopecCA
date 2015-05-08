<%@ page language="java" contentType="text/html;charset=gbk"  isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setAttribute("bo","Shouqrzb"); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript" src="js/date/time.js"></script>
		<script src="js/pagejs/validateForm.js" type="text/javascript"></script>
		<script src="js/exportPdf.js" type="text/javascript"></script>
	<script type="text/javascript" src="js/shield.js"></script>	
		<%@ include file="/common/validator.jsp"%>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript">
		$(function(){
		if($("#terminal_id").val()==null||$("#terminal_id").val().length==0){
				$("#terminal_id").val('${clerk.terminal_id}');
				} 
			
			 //验证
			 $("#form1").validate({
			    errorLabelContainer:"#error div.error",
			    wrapper:"li",
			    submitHandler:function(form){
				   
			   		 form.submit();
			   		//$("#tu").html('<table align="center"><tr><td><img border="1" src="images/appInstall_animated.gif"/></td></tr></table>');
			   }
			 });
			})
		function checkendDate(){
			
			var begindate=$("#begindate").val();
	
			if(begindate==null||begindate.length==0){
				$("#abc").html('起始日期不能为空!');
				return false;
			}
			var enddate=$("#enddate").val();
			if(enddate==null||enddate.length==0){
				$("#abc").html('终止日期不能为空!');
				return false;
			}
			if(enddate < begindate){
				$("#abc1").html('终止日期不能早于起始日期');
				//alert("111111");
				return false;
				}else{
				$("#abc").html('');
				return true;
			}
		}

		function checkAccount(){
			var zhangh = $("#zhangh").val();
			var reg =   /(^[0-9]{9}$)|(^[0-9]{17}$)|(^[0-9]{22}$)|(^[0-9]{10,13}$)/;
			if(zhangh==null||zhangh.length==0){
				$("#zhanghMsg").text("");
				return true;
				}
			if(!reg.test(zhangh)){
				$('#zhanghMsg').text(" 账号格式不正确");
				document.getElementById("zhangh").select();
				return false;
			}
			$("#zhanghMsg").text("");
			return true;

			}

		function checkOrg(){
			var orgcode = $("#orgcode").val();
			var reg=  /^[0-9]{4}$/;
			if(orgcode==null||orgcode.length==0){
				$("#orgcodeMsg").text("");
				return true;
				}
			if(!reg.test(orgcode)){
				$('#orgcodeMsg').text(" 机构号格式不正确");
				document.getElementById("orgcode").select();
				return false;
			}
			$("#orgcodeMsg").text("");
			return true;

			}
		function onsubmit1(){
		//	 if(!checkendDate())return;
			 $("#form1").submit();

		}
		function exportPdf(){
			var jlist='${jlist}';
			if(jlist==null||jlist.length==0){
				return;
				}
			if($("#className").val()==null||$("#className").val().length==0){
				return;
				}
			$("#jlist").val(encodeURI(jlist));
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
					报表查看 
				</td>
			</tr>
			<tr>
				<td class="class1_td">
					&nbsp;
				</td>
				<td class="class1_td" style="text-align: left">
					<html:form styleId="form1" method="post" action="checkaccount.do?method=list">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="class1_td alignright">石化网点名称：</td>
								<td class="class1_td alignleft">
									<html:text property="legalname" styleId="legalname" styleClass="inputField legalname"/>
								</td>		
								<td class="class1_td alignright">终端号：</td>
								<td class="class1_td alignleft">
									<html:text property="terminal_id" styleId="terminal_id" styleClass="inputField terminal_id"/>
								</td>		
							<td class="class1_td alignright">交易卡号：</td>
								<td class="class1_td alignleft">
									<html:text property="card_id" styleId="card_id" styleClass="inputField card_id"/>
								</td>	
								<td></td>
								<td></td>
								
								
							</tr>
							<tr>
								<td class="class1_td alignright">交易金额：</td>
								<td class="class1_td alignleft">
										<html:text property="beginamount" styleId="beginamount" styleClass="inputField beginamount required"   />－<html:text property="endamount" styleId="endamount" styleClass="inputField endamount required"  />
								</td>
								<td class="class1_td alignright">交易时间：</td>
								<td class="class1_td alignleft">
										<html:text property="beginseal_date" styleId="begindate" styleClass="inputField date_input required"  onclick="WdatePicker()" />－<html:text property="endseal_date" styleId="enddate" styleClass="inputField date_input required"  onclick="WdatePicker()"  />
								</td>
								
								<td class="class1_td alignright">交易类型：</td>
								<td class="class1_td alignleft">
									<html:select property="seal_type" styleId="seal_type" >
										<html:option value="">全部</html:option>
										<html:option value="1">消费</html:option>
										<html:option value="2">积分兑换</html:option>
									</html:select>
								</td>
								<td class="class1_td alignleft">&nbsp;</td>
								<td class="class1_td alignleft">
									<button id="select" type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onclick="onsubmit1();">
										<img src="images/search1.gif" width="13" height="13" align="middle">查询
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
						<div class="error orange" id="msg"></div>
						<span id="abc1" class="abc"></span>
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
		<input type="hidden" id="temp" name="temp" value="${jlist}" />
		<input type="hidden" id="className" name="className" value="Shouqrzb"/>
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
<%--				<button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onClick="exportPdf();">--%>
<%--									<img src="images/add.gif" width="13" height="13" align="absmiddle"/>导出</button>--%>
					<ec:table 
					  retrieveRowsCallback="org.extremecomponents.table.callback.LimitCallback"
        			  filterRowsCallback="org.extremecomponents.table.callback.LimitCallback" 
			          sortRowsCallback="org.extremecomponents.table.callback.LimitCallback"
						filterable="false" sortable="false" 
						title="交易查询结果" showPagination="true" view="compact"
						items="list" var="zhanghtbb"  rowsDisplayed="${ec_yemjlts}" 
						action="${pageContext.request.contextPath}/accountlog.do"
						imagePath="${pageContext.request.contextPath}/images/table/*.gif">
<%--						<ec:exportXls fileName="zhanghtbrz.xls" tooltip="Export Exl" ></ec:exportXls>--%>
						
						<ec:row >
							<ec:column property="legalname" title="石化网点名称" cell="org.extremecomponents.table.cell.DisplayCell" />
							<ec:column property="terminal_id" title="终端号" />
							<ec:column property="card_id" title="交易卡号" />
							<ec:column property="amount" title="交易金额" />
							<ec:column property="poundage" title="交易手续费"  />
							<ec:column property="seal_date" title="交易日期"  />
							<ec:column property="seal_time" title="交易时间"  />
							<ec:column property="seal_type" title="交易类型"  />
							<ec:column property="card_type" title="卡类型"  />
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