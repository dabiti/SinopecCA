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
		<script type="text/javascript">
		function updForm(baobbs,yaosbs){
			if(baobbs==undefined||yaosbs==undefined){
				return;
			}
			var d_result = window.showModalDialog('r_formMgr.do?method=getForm&baobbs=' + baobbs + '&yaosbs=' + yaosbs + "&rid=" + new Date(), "","dialogWidth:650px;dialogHeight:500px;help:no;scroll:yes;center:yes;status:no"); 
			reload.click();
			return;
		}
		function delForm(baobbs,yaosbs){
			var del = window.location.href;
			if(baobbs==undefined||yaosbs==undefined)
				return;
			if(confirm('��ȷ��Ҫɾ����ѡ��ı�Ҫ����?')){
				sendTrade('r_formMgr.do?method=deleteForm&baobbs=' + baobbs + '&yaosbs=' + yaosbs + "&rid=" + new Date()); 
				reload.click();
				}
			return;		
		}
		function doAdd(baobbs){
			if(baobbs==undefined)
				return;
			var d_result = window.showModalDialog('r_formMgr.do?method=add&baobbs=' + baobbs + "&rid=" + new Date(), "","dialogWidth:650px;dialogHeight:500px;help:no;scroll:yes;center:yes;status:no"); 
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
		<a id="reload" href="r_formMgr.do?method=listForm&baobbs=${baobbs}" style="display:none"></a>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					Ureport���� - ������� - ��Ҫ�ع���
				</td>
			</tr>
		</table>
		<br>
		<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<a type="button" id="button1" style="width:100px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onClick="doAdd('${baobbs}');">
					<img src="images/search1.gif" width="13" height="13" align="middle" />
					������Ҫ��
				</a>
				&nbsp;
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
								<th colspan="7" class="class1_thead th">
									���Ʒ����б�
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
									�ֶ�����
								</td>
								<td class="class1_thead th">
									Ҫ�ر���
								</td>
								<td class="class1_thead th">
									��ʾ˳��
								</td>
								<td class="class1_thead th">
									����
								</td>
							</tr>
						</thead>
						<c:forEach items="${list}" var="list">
							<tr>
								<td class="class1_td center">
									${list['BAOBBS']}
								</td>
								<td class="class1_td center">
									${list['YAOSBS']}
								</td>
								<td class="class1_td center">
									${list['YAOSMC']}
								</td>
								<td class="class1_td center">
									${list['YAOSBT']}
								</td>
								<td class="class1_td center">
									${list['XIANSSX']}
								</td>
								<td class="class1_td center">
									<a href="javascript:updForm('${list['BAOBBS']}','${list['YAOSBS']}');">[�޸�]</a>
									<a href="javascript:delForm('${list['BAOBBS']}','${list['YAOSBS']}');">[ɾ��]</a>
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