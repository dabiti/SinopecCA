<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>�˻�ӡ��Ӱ��</title>
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
		<script  type="text/javascript">
		
		</script>
		<div class="funbutton">
			
			<button id="closePage" type="button" style="width:80px" onclick="javascript:window.close();" onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1">�ر�ҳ��</button>
		</div>
		
		<c:forEach items="${yinjList}" var="list">
			<div style="height:25px;padding-top:5px;">�˺ţ�${list.yinjid.zhangh }&nbsp;|&nbsp;�������ڣ�${list.yinjid.qiyrq }&nbsp;|&nbsp;ͣ������:${list.tingyrq }&nbsp;|&nbsp;ӡ�����ͣ�${list.yinjlx }&nbsp;|&nbsp;ӡ�����״̬��${list.yinjshzt }</div>
			<c:if test="${list.yinjtp!=null&&list.yinjtp!=''}">
				<font color="red"  id=${list.yinjid.zhangh}${list.yinjid.yinjbh}1></font>
				<img id="img${list.yinjid.zhangh}${list.yinjid.yinjbh}"   src="accountinfo.do?method=getYinjImage&zhangh=${list.yinjid.zhangh}&yinjbh=${list.yinjid.yinjbh}&qiyrq=${list.yinjid.qiyrq}"  onload="autoPicSize(this,750,500)"/>
			</c:if>
			<c:if test="${list.yinjtp==null||list.yinjtp==''}">
				<font color="red">û���ҵ�ӡ��Ӱ��!</font>
			</c:if>
		</c:forEach>
	</body>
</html>