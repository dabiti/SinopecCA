<%@ page contentType="text/html; charset=GBK" language="java"  isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<script language="javascript">
		function delkag(kagid,kagzt){
			if(kagzt!='0')
			{
				alert("卡柜已使用，不允许删除！");
			} else{
				if(confirm('你确定要删除卡柜吗?')){
					window.location.href='kag.do?method=delete&kagid='+kagid;
				}
			} 
		}					
		</script>
		<link href="style/right.css" rel="stylesheet" type="text/css">
	</head>
	<body >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
				 	印鉴卡管理-卡柜空间展示
				</td>
			</tr>
		</table>
		<br>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
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
					<table width="100%" border="0" cellspacing="1" cellpadding="0"
						class="class1_table">
						<thead class="class1_thead">
							<tr>
								<th colspan="10" class="class1_thead th">
									卡柜列表
								</th>
							</tr>
							<tr>		
								<c:forEach items="${tableheadlist}" var="chout">		
									<td class="class1_thead th">${chout}</td>	
								</c:forEach>
							</tr>
						</thead>
						<c:forEach items="${cenglist}" var="choutlist">	
							<tr>		
								<c:forEach items="${choutlist}" var="chout">			
									<td  class="class1_td center">${chout}</td>		
								</c:forEach>	
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