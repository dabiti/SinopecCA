<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>账户票据影像</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/autoImg.js" type="text/javascript"></script>
	</head>
	<body>
		<form id=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange">
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
		<OBJECT ID="WebBrowser1" WIDTH=0 HEIGHT=0 CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" VIEWASTEXT></OBJECT> 
		<script  type="text/javascript">
			function dyFunction(){
				var objPreview = document.getElementById( 'preview' );   
				objPreview.style.display = 'none';     
				WebBrowser1.ExecWB(7,1);
				objPreview.style.display = '';       
			}
			function errorImg(id){
				document.getElementById(id).innerHTML='下载影像失败!';
				var mydom=document.getElementById("img"+id);//获取指定ID的DOM对象
				if(mydom) mydom.removeNode(true);
			}
		</script>
		<div class="funbutton">
			<button id="preview" type="button" style="width:80px" onclick="javascript:dyFunction();" onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1">打印预览 </button>
		</div>
		<c:forEach items="${accountList}" var="list">
			<div style="height:25px;padding-top:5px;">账号:${list.id.zhangh}|凭证号：${list.id.wenjbh}|类型：${list.yxlx}</div>
			<font color="red" id=${list.id.zhangh}${list.id.wenjbh}2></font>
			<img id="img${list.id.zhangh}${list.id.wenjbh}2" onError="errorImg('${list.id.zhangh}${list.id.wenjbh}2')" src="accountinfo.do?method=getBillImage&&zhangh=${list.id.zhangh}&wenjbh=${list.id.wenjbh}" onload="autoPicSize(this,750,500)"/>
		</c:forEach>
	</body>
</html>