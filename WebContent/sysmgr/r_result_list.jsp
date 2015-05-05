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
		<title>��ʯ������ϵͳ</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		function setDefaultResult(baobbs){
			if(confirm('��ȷ��Ҫ����ȱʡ���Ҫ����?')){
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
			if(confirm('��ȷ��Ҫɾ����ѡ��ı�Ҫ����?')){
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
				alert("��ѡ��Ҫ�����ı���");
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
					������ - ������� - ���Ҫ�ع���
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
					�������Ҫ��
				</a>
				&nbsp;
				&nbsp;
				<a type="button" id="button2" style="width:75px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onClick="setDefaultResult('${baobbs}');">
					<img src="images/search1.gif" align="middle">
					����ȱʡ���Ҫ��
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
									������Ҫ������
								</th>
							</tr>
							<tr>
								<td class="class1_thead th">
									�����ʶ
								</td>
								<td class="class1_thead th">
									Ҫ�ر�ʶ
								</td>
								<td class="class1_thead th">
									Ҫ������
								</td>
								<td class="class1_thead th">
									Ҫ�ر���
								</td>
								<td class="class1_thead th">
									��ʾ����
								</td>
								<td class="class1_thead th">
									�Ƿ���ʾ
								</td>
								<td class="class1_thead th">
									����ASD${SHUJHQFS}
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
										<a href="javascript:doDictMgr('${list['ZHIDBS']}');">�ֵ䶨��</a>
									</c:if>
									<c:if test="${list['ZHIDBS']!=''}">
										<a href="javascript:doDictMgr('${list['ZHIDBS']}');">${list['ZHIDBS']}</a>
									</c:if> -->
								<td class="class1_td center">
									<a
										href="javascript:doUpdResult('${list['baobbs']}','${list['yaosbs']}');">[�޸�]</a>
									<a
										href="javascript:doDelResult('${list['baobbs']}','${list['yaosbs']}');">[ɾ��]</a>
									<!-- <a href="javascript:doDictMgr('${list['ZHIDBS'] }');">�ֵ䶨��</a> -->
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
