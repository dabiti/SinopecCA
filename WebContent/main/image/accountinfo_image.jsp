<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>�˻���ѯӰ��</title>
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
				var objPreview = document.getElementById('preview');
				var closePage = document.getElementById('closePage');   
				objPreview.style.display = 'none';
				closePage.style.display = 'none';     
				WebBrowser1.ExecWB(7,1);
				objPreview.style.display = '';
				closePage.style.display = '';       
			}
			function errorImg(id){
				document.getElementById(id).innerHTML='����Ӱ��ʧ��!';
				var mydom=document.getElementById("img"+id);//��ȡָ��ID��DOM����
				if(mydom) mydom.removeNode(true);
			}
		</script>
		<div class="funbutton">
			<button id="preview" type="button" style="width:80px" onclick="javascript:dyFunction();" onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1">��ӡԤ�� </button>
			<button id="closePage" type="button" style="width:80px" onclick="javascript:window.close();" onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1">�ر�ҳ��</button>
		</div>
		<c:forEach items="${accountList}" var="list">
			<div style="height:25px;padding-top:5px;">�˺�:${list.yinjkid.zhangh}|ӡ������ţ�${list.yinjkid.yinjkh}|ӡ���������棺����</div>
			<c:if test="${list.zhengmwjm!=null&&list.zhengmwjm!=''}">
				<font color="red"  id=${list.yinjkid.zhangh}${list.yinjkid.yinjkh}1></font>
				<img id="img${list.yinjkid.zhangh}${list.yinjkid.yinjkh}1"  onError="errorImg('${list.yinjkid.zhangh}${list.yinjkid.yinjkh}1')"  src="accountinfo.do?method=downloadYinjkImage&zhangh=${list.yinjkid.zhangh}&yinjkh=${list.yinjkid.yinjkh}&billcm=1" onload="autoPicSize(this,750,500)"/>
			</c:if>
			<c:if test="${list.zhengmwjm==null||list.zhengmwjm==''}">
				<font color="red">û���ҵ�ӡ��������Ӱ��!</font>
			</c:if>
			<div style="height:25px;padding-top:5px;">�˺�:${list.yinjkid.zhangh}|ӡ������ţ�${list.yinjkid.yinjkh}|ӡ���������棺����</div>
			<c:if test="${list.fanmwjm!=null&&list.fanmwjm!=''}">
				<font color="red"  id=${list.yinjkid.zhangh}${list.yinjkid.yinjkh}2></font>
				<img id="img${list.yinjkid.zhangh}${list.yinjkid.yinjkh}2" onError="errorImg('${list.yinjkid.zhangh}${list.yinjkid.yinjkh}2')" src="accountinfo.do?method=downloadYinjkImage&zhangh=${list.yinjkid.zhangh}&yinjkh=${list.yinjkid.yinjkh}&billcm=2" onload="autoPicSize(this,750,500)"/>
			</c:if>
			<c:if test="${list.fanmwjm==null||list.fanmwjm==''}">
				<font color="red">û���ҵ�ӡ��������Ӱ��!</font>
			</c:if>
		</c:forEach>
	</body>
</html>