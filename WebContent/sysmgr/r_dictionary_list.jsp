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
		<script type="text/javascript">
		function add(zhidbs){
			if(zhidbs==undefined){
				var d_result = window.showModalDialog('dictionaryMgr.do?method=addDictionary' + "&rid=" + new Date(), "","dialogWidth:650px;dialogHeight:500px;help:no;scroll:yes;center:yes;status:no"); 
			}else	
			var d_result = window.showModalDialog('dictionaryMgr.do?method=addDictionary&zhidbs=' + zhidbs + "&rid=" + new Date(), "","dialogWidth:650px;dialogHeight:500px;help:no;scroll:yes;center:yes;status:no"); 
			reload.click();
		}
		function doDelete(zhidbs,suoyz){
			if(zhidbs==undefined || suoyz==undefined)
				return;
			if(confirm("你确认删除所选择的字典要素吗?"))
				sendTrade('dictionaryMgr.do?method=deleteDictionary&zhidbs=' + zhidbs + '&suoyz=' + suoyz +  "&rid=" + new Date()); 
			reload.click();
		}
		function doUpdate(zhidbs,suoyz){
			if(zhidbs==undefined || suoyz==undefined)
				return;
			var d_result = window.showModalDialog('dictionaryMgr.do?method=getDictionary&zhidbs=' + zhidbs + '&suoyz=' + suoyz + "&rid=" + new Date(), "","dialogWidth:650px;dialogHeight:500px;help:no;scroll:yes;center:yes;status:no"); 
			reload.click();
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
  		function doBack(){
			window.close();
			return;
		}
</script>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<a id="reload" href="dictionaryMgr.do?method=listDictionary&zhidbs=${zhidbs}"
			style="display:none"></a>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					报表定制 - 报表管理 - 字典要素管理
				</td>
			</tr>
		</table>
		<br>
		<table width="97%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<a type="button" id="button1" style="width:100px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onClick="add('${zhidbs }');">
				<img src="images/search1.gif" width="13" height="13" align="middle"> 新增字典要素 </a> 
				&nbsp;
				<!-- <a type="button" id="button1" style="width:100px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onClick="doUpdate(${suoyz });"> 
					<img src="images/search1.gif" width="13" height="13" align="middle" /> 修改 </a> 
				&nbsp;
				<a type="button" id="button1" style="width:100px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onClick="doDelete(${suoyz });"> 
					<img src="images/search1.gif" width="13" height="13" align="middle" /> 删除 </a> -->
			</tr>
			<br />
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
								<th colspan="7" class="class1_thead th">
									字典列表
								</th>
							</tr>
							<tr>
								<td class="class1_thead th">
									字典标识
								</td>
								<td class="class1_thead th">
									索引值
								</td>
								<td class="class1_thead th">
									转换值
								</td>
								<td class="class1_thead th">
									操作
								</td>
							</tr>
						</thead>
						<c:forEach items="${list}" var="list">
							<tr>
								<td class="class1_td center">
									${list['ZHIDBS']}
								</td>
								<td class="class1_td center">
									${list['SUOYZ']}
								</td>
								<td class="class1_td center">
									${list['ZHUANHZ']}
								</td>
								<td class="class1_td center">
									<a
										href="javascript:doUpdate('${list['ZHIDBS']}','${list['SUOYZ']}');">[修改]</a>
									<a
										href="javascript:doDelete('${list['ZHIDBS']}','${list['SUOYZ']}');">[删除]</a>
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
		<div class="funbutton">
			&nbsp;&nbsp;&nbsp;
			<button type="button" style="width:60px"
				onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1"
				onclick="doBack();">
				<img src="images/back1.gif" width="11" height="11"
					align="middle">
				返回
			</button>
		</div>
	</body>
</html>
