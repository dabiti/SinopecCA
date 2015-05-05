<%@ page language="java" contentType="text/html;charset=gbk"  isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
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
		<script language="javascript" type="text/javascript" src="js/date/time.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript">   
			 $(document).ready(function() {
			 if($("#orgCode").val()==null||$("#orgCode").val().length==0){
			 	$("#orgCode").val('${clerk.orgcode}');
			 }
			//验证
			$("#sealchecklogForm").validate({
			   errorLabelContainer:"#error div.error",
			   wrapper:"li",
			   submitHandler:function(form){
			   $("#select").attr("disabled","disabled");
			   	form.submit();
			   	 $("#tu").html('<table align="center"><tr><td><img border="1" src="images/appInstall_animated.gif"/><b> 检索中，请稍等......<b></td></tr></table>');
			   }
			});
			getPingzlist();
			})
			
			//获取凭证种类
			function getPingzlist(){
				 $.post("yinjManage.do?method=queryPingzlist", {}, function (data, textStatus) {
						if (textStatus = "success") {
							if(data.indexOf('<html>')!=-1){
								window.location.href='timeOutlogin.jsp';
								return ;
							}
							if(data=="fail001"){
								return ;
							}
							var checktype='${checktype}';
							if(data.indexOf('{')!=-1){
								var pingzlist=eval('('+data+')');
								var insertstr=" selected='selected' ";
								for(var i=0;i<pingzlist.length;i++){
										if(pingzlist[i].pingzbs==checktype){
											$("#checktype").append("<option value='"+pingzlist[i].pingzbs+"'"+insertstr+" >"+pingzlist[i].pingzbs+pingzlist[i].pingzmc+"</option>");
										}else{
											$("#checktype").append("<option value='"+pingzlist[i].pingzbs+"' >"+pingzlist[i].pingzbs+pingzlist[i].pingzmc+"</option>");
											}
									}
								
								return;
							}
						}
					}, "text");
				}
			function exportPdf(){
					var jlist='${jlist}';
					var jsql=$("#jsql").val();
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
			$("#daochu").attr("disabled","disabled");
			setTimeout(function(){
			$("#daochu").attr("disabled","");
			},3000)
			
	}
	
		function submitForm(){
		//$("#select").attr("disabled","disabled");
			$("#sealchecklogForm").submit();
		}
			
		//	alert($("#checknum").val());
		</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td> 
				<td background="images/main_place_bg.gif">
					日志查询 - 整票验印日志查询
				</td>
			</tr>
			<tr>
				<td >
				<table  width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
				<td class="class1_td alignright">&nbsp;</td>
					<td class="class1_td alignleft">&nbsp;
								</td>						
				</tr>
				<tr>
				<td class="class1_td alignright">&nbsp;</td>
								<td class="class1_td alignleft">&nbsp;
								</td>		
				</tr>
				
				</table>
				</td>
				
				<td style="text-align: left">
						<html:form styleId="sealchecklogForm" action="/voucherchecklog.do?method=execute"
						method="post">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="class1_td alignright">机构号：</td>
								<td class="class1_td alignleft">
									<html:text property="orgCode" styleId="orgCode" styleClass="inputField required orgCode"/><span style="color:red">*</span>
								</td>		
								<td class="class1_td alignright">账号：</td>
								<td class="class1_td alignleft">
									<html:text property="account" styleId="account" styleClass="inputField account"/>
								</td>		
							<td class="class1_td alignright">凭证类型：</td>
								<td class="class1_td alignleft">
									<html:select property="checktype" styleId="checktype" style="width:260px;">
										<html:option value="">全部</html:option>
									</html:select>
								</td>
								<td class="class1_td alignright">凭证号：</td>
								<td class="class1_td alignleft">
									<html:text property="checknum" styleId="checknum" styleClass="inputField"/>
								</td>
								
								<td class="class1_td alignright">渠道：</td>
								<td class="class1_td alignleft">
									<html:select property="canal" styleId="canal" >
										<html:option value="">全部</html:option>
										<html:option value="YY">单笔验印</html:option>
										<html:option value="AB">流程验印</html:option>
										<html:option value="PL">批量验印</html:option>
									</html:select>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">验印日期：</td>
								<td class="class1_td alignleft">
									<html:text property="begindate" styleId="begindate" styleClass="inputField date_input required"  onclick="WdatePicker()" />－<html:text property="enddate" styleId="enddate" styleClass="inputField date_input required"  onclick="WdatePicker()"  />
								<span style="color:red">*</span>
								</td>
								<td class="class1_td alignright">柜员代码：</td>
								<td class="class1_td alignleft">
									<html:text property="clerknum" styleId="clerknum" styleClass="inputField clerknum"/>
								</td>
								<td class="class1_td alignright">验印结果：</td>
								<td class="class1_td alignleft">
									<html:select property="checkresult" style="width:65px;">
										<html:option value="">全部</html:option>
										<html:option value="通过">通过</html:option>
										<html:option value="未验">未验</html:option>
										<html:option value="未过">未过</html:option>
										<html:option value="未找到">未找到</html:option>
									</html:select>
								</td>
								<td class="class1_td alignright">验印方式：</td>
								<td class="class1_td alignleft">
									<html:select property="checkmode" style="width:65px;">
										<html:option value="">全部</html:option>
										<html:option value="自动">自动</html:option>
										<html:option value="辅助">辅助</html:option>
										<html:option value="人工">人工</html:option>
										<html:option value="实物">实物</html:option>
									</html:select>
								</td>
								<td class="class1_td alignleft">&nbsp;</td>
								<td class="class1_td alignleft">
									<button id="select" type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onclick="submitForm();">
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
		<form action="ajax.do?method=exportPdf" id="exportForm" method="post">
		<input type="hidden" id="jlist" name="jlist" />
		<input type="hidden" id="jsql" name="jsql" value="${jsql}"/>
		<input type="hidden" id="className" name="className" value="SealcheckLog2"/>
		</form>
			<div id="tu"></div>
		<br>
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
					<button id="daochu" type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onClick="exportPdf();">
									<img src="images/add.gif" width="13" height="13" align="absmiddle"/>导出</button>
									<c:if test="${autopasscount != null}">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>共验印${autopasscount.total}&nbsp;笔,自动通过${autopasscount.autopassnum}&nbsp;笔,自动通过率为${autopasscount.passrate}</span>
					</c:if>
					<ec:table retrieveRowsCallback="org.extremecomponents.table.callback.LimitCallback"
        			  filterRowsCallback="org.extremecomponents.table.callback.LimitCallback" 
			          sortRowsCallback="org.extremecomponents.table.callback.LimitCallback"
					filterable="false" sortable="false" title="凭证验印结果"
						showPagination="true" view="compact" items="list"
						var="sealchecklog" rowsDisplayed="${ec_yemjlts}" 
						action="${pageContext.request.contextPath}/voucherchecklog.do"
						imagePath="${pageContext.request.contextPath}/images/table/*.gif">
						
						<%//<!--<c:choose>-->
							//<!--<c:when test="${list_size>=60000}">-->
							//	<!--<ec:exportCsv fileName="sealchecklog.txt" tooltip="Export Text" />-->
							//<!--</c:when>-->
							//<!--<c:otherwise>-->
							//	<!--<ec:exportXls fileName="sealchecklog.xls" tooltip="Export Excel" />-->
							//<!--</c:otherwise>-->
						//<!--</c:choose>-->
						%>
						<ec:row>
							<ec:column property="account" title="账号" />
							<ec:column property="remark" title="验印机构" />
							<ec:column property="pingzlx" title="凭证类型" />
							<ec:column property="checknum" title="凭证号" />
							<ec:column property="money" title="金额(单位:元)" />
							<ec:column property="chuprq" title="出票日期" />
							<ec:column property="checkdate" title="验印时间" />
							<ec:column property="clerknum" title="验印柜员代码" />
							<ec:column property="doublesignatureclerknum" title="复核柜员代码" />
							<ec:column property="checkresult" title="验印结果" />
							<ec:column property="checkmode" title="验印方式" />
							<ec:column property="batchinfo" title="单章验印信息" />
							<ec:column property="canal" title="渠道" />
					<% 	//	<!--<unitop:HasPrivilegeForSystemConfig name="pingzyxll">
						//		<ec:column property="浏览" title="浏览票据影像" >
						//		<a href="accountinfo.do?method=getPiaojImageList&zhangh=${sealchecklog.account}&pingzbsm=${sealchecklog.pingzbsm}" target="_blank">浏览</a>
						//		</ec:column>
						//	</unitop:HasPrivilegeForSystemConfig>--> %>
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
