<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
<%--		<script type="text/javascript" src="js/shield.js"></script>--%>
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
	<script type="text/javascript">
	function importExcel(){
	 $("#tu").html('<table align="center"><tr><td><img border="1" src="images/appInstall_animated.gif"/><b> 导入中，请稍等......<b></td></tr></table>');
	 $("#submit").attr("disabled","disabled");
	 //$("#form1").submit();
	 return true;
	}
	function getErrorMsg(){
		$("#type").val("0");
		$("#form2").submit();
	}
	$(function (){

		var file='${file}';
		if(file=="0"){
			getErrorMsg();
			}
		});
	</script>

	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
	<logic:messagesPresent>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange">
						<html:errors />
					</td>
				</tr>
			</table>
		</logic:messagesPresent>
<form >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					数据移植-存量账户信息导入
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
						<table width="90%">
							<tr>
								
							</tr>
					  </table>
				</td>
			</tr>
		</table>
</form> 
	<div id="tu"></div>
	<form action="accountinfo.do?method=getZhanghb_Error" method="post" id="form2">
		<input type="hidden" value="0" name="type" id="type">
	
	 </form>
<html:form action="accountinfo.do?method=importAccountinfo_excel" method="post" onsubmit="if(importExcel()){return true;}"  enctype="multipart/form-data" styleId="form1">
		<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">&nbsp;</td>
				<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="0" 
						class="class1_table">
			<thead class="class1_thead">
								<tr>
									<th id="title" colspan="2" class="class1_thead th">存量账户信息导入</th>
								</tr>
							</thead>
							<tr>
								<td colspan="7" class="class1_td">
									 <html:file property="file" size="35"/>
								  	 <button id="submit" type="submit" style="width:60px" onmouseover="this.className='buttom2'"  onmouseout="this.className='buttom1'" class="buttom1" ><img src="images/add.gif" width="13" height="13" align="absmiddle" ><span>导入</span></button>
								<span style="color:red;">目前只支持导入97-2003版excel,其他版本不支持；导入前请检查文件后缀名是否为".xls"。</span>
								</td>
								
							</tr>
							<tr>
								<td colspan="7" class="class1_td">
								  	 <button id="importerror" type="button" style="width:100px" onmouseover="this.className='buttom2'" onclick="getErrorMsg();"  onmouseout="this.className='buttom1'" class="buttom1" ><img src="images/search1.gif" width="13" height="13" align="absmiddle" ><span>导出错误信息</span></button>
								</td>
								</tr>
					</table>
				</td>
				<td background="images/table_arrow_07.gif">&nbsp;
				</td>
			</tr>
			<tr>
			<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
		</tr>
		</table>
		</html:form>
	</body>
</html>