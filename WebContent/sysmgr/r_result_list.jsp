<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base target="_self">
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<meta http-equiv="pragma" CONTENT="no-cache"> 
		<meta http-equiv="Cache-Control" CONTENT="no-cache, must-revalidate"> 
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		function setDefaultResult(baobbs){
			if(confirm('你确定要设置缺省结果要素吗?')){
				sendTrade('r_resultMgr.do?method=setDeFaultResult&baobbs=' + baobbs + "&rid=" + new Date()); 
			}
			reload.click();
			return;			
		}
		function doAddResult(baobbs){
			var d_result = window.showModalDialog('r_resultMgr.do?method=add&baobbs=' + baobbs+"&action=add&rid=" + new Date(),"","dialogWidth:650px;dialogHeight:500px;help:no;scroll:yes;center:yes;status:no"); 
			reload.click();
			return;
		}
		function doDelResult(baobbs,yaosbs){
			var cur= window.location.href;
			if(baobbs==undefined||yaosbs==undefined)
				return;
			if(confirm('你确定要删除所选择的表单要素吗?')){
				sendTrade('r_resultMgr.do?method=delete&baobbs=' + baobbs + "&yaosbs=" + yaosbs + "&rid=" + new Date()); 
			}
			reload.click();
			return;
		}
		function doUpdResult(baobbs, yaosbs){
			var d_result = window.showModalDialog('r_resultMgr.do?method=update&baobbs=' + baobbs + "&yaosbs=" + yaosbs + "&action=upd&rid=" + new Date(), "","dialogWidth:650px;dialogHeight:500px;help:no;scroll:yes;center:yes;status:no"); 
			reload.click();
			return;
		}
		function doDictMgr(zhidbs){
			if(zhidbs==undefined){
				alert("请选择要操作的表单！");
				return;
				}
			window.showModalDialog('dictionaryMgr.do?method=listDictionary&zhidbs='+zhidbs,"dialogWidth:650px;dialogHeight:700px;help:no;scroll:yes;center:yes;status:no");
			reload.click();
			return;
		}
		function sendTrade(URL){	
    		var xmlDoc=new ActiveXObject("Msxml2.DOMDocument.3.0");
			var xh=new ActiveXObject("MSXML2.XMLHTTP");
			xh.open("POST",URL,false);
    		xh.setRequestHeader("Content-Type","text/xml");
    		xh.setRequestHeader("charset","UTF-8");
    		xh.send("");
    		if (xh.readyState == 4){
      			try{
          			return  xh.responseText;
        		} catch(e){
					alert(e);
        		}  	
    		}
  		}
</script>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<a id="reload" href="r_resultMgr.do?method=list&baobbs=${baobbs}" style="display:none"></a>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					报表定制 - 报表管理 - 结果要素管理
				</td>
			</tr>
			<tr>
				<td>
					<form id=error name=error>
					<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
						<div class="error orange"></div>
						<logic:messagesPresent>
							<tr>
								<td class="orange">
									<html:errors />
								</td>
							</tr>
						</logic:messagesPresent>
					</table>
					</form>
				</td>
			</tr>
		</table>
		<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<a type="button" id="button1" style="width:75px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onClick="doAddResult('${baobbs}');">
					<img src="images/search1.gif" width="13" height="13" align="middle">
					新增结果要素
				</a>
				&nbsp;
				&nbsp;
				<a type="button" id="button2" style="width:75px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onClick="setDefaultResult('${baobbs}');">
					<img src="images/search1.gif" align="middle">
					设置缺省结果要素
				</a>
			</tr>
				<br/>
			<tr>
				<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">
					&nbsp;
				</td>
				<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="0"
						class="class1_table">
						<thead class="class1_thead">
							<tr>
								<th colspan="9" class="class1_thead th">
									报表结果要素配置
								</th>
							</tr>
							<tr>
								<td class="class1_thead th">
									报表标识
								</td>
								<td class="class1_thead th">
									要素标识
								</td>
								<td class="class1_thead th">
									要素名称
								</td>
								<td class="class1_thead th">
									要素标题
								</td>
								<td class="class1_thead th">
									显示类型
								</td>
								<td class="class1_thead th">
									是否显示
								</td>
								<td class="class1_thead th">
									操作ASD${SHUJHQFS}
								</td>
							</tr>
						</thead>
						<c:forEach items="${list}" var="list">
							<tr>
								<td class="class1_td center">
									${list['baobbs']}
								</td>
								<td class="class1_td center">
									${list['yaosbs']}
								</td>
								<td class="class1_td center">
									${list['yaosmc']}
								</td>
								<td class="class1_td center">
									${list['yaosbt']}
								</td>
								<td class="class1_td center">
									${list['xianslx']}
								</td>
								<td class="class1_td center">
									${list['shifxs']}
								</td>
								<!-- <td class="class1_td center">
									<c:if test="${list['ZHIDBS']==''}">
										<a href="javascript:doDictMgr('${list['ZHIDBS']}');">字典定义</a>
									</c:if>
									<c:if test="${list['ZHIDBS']!=''}">
										<a href="javascript:doDictMgr('${list['ZHIDBS']}');">${list['ZHIDBS']}</a>
									</c:if> -->
								<td class="class1_td center">
									<a
										href="javascript:doUpdResult('${list['baobbs']}','${list['yaosbs']}');">[修改]</a>
									<a
										href="javascript:doDelResult('${list['baobbs']}','${list['yaosbs']}');">[删除]</a>
									<!-- <a href="javascript:doDictMgr('${list['ZHIDBS'] }');">字典定义</a> -->
								</td>
							</tr>
						</c:forEach>

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
