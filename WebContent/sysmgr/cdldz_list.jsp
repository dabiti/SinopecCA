<%@page language="java" contentType="text/html;charset=gbk" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/shield.js"></script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;' leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" style="overflow-x:hidden">
	
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					功能菜单定制 -${shangjmc}
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
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
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="w70">
								 <button type="button" style="width:60px"
										onmouseover="this.className='buttom2'"
										onmouseout="this.className='buttom1'" class="buttom1"
										onClick="location.href='doMenu.do?method=addOrModify&gongnid=${shangjgn}'">
										<img src="images/add.gif" width="13" height="13"
											align="absmiddle">
										添加 
									</button>
								</td>
								<td class="w100">
									<button type="button" style="width:60px"
										onmouseover="this.className='buttom2'"
										onmouseout="this.className='buttom1'" class="buttom1"
										onClick="location.href='doMenu.do?method=create&shangjgn=${shangjgn}'">
										<img src="images/add.gif" width="13" height="13"
											align="absmiddle">
										新建
									</button>								
								</td>
								<td class="w100">
									<button type="button" style="width:60px"
										onmouseover="this.className='buttom2'"
										onmouseout="this.className='buttom1'" class="buttom1"
										onClick="history.back()">
										<img src="images/back1.gif" width="13" height="13"
											align="absmiddle">
										返回
									</button>								
								</td>								
							</tr>
						</table>
				</td>
			</tr>
		</table>
		<br>
		<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">
					&nbsp;
				</td>
				<td>
				   <table width="100%"  border="0" style="table-layout:fixed;word-wrap:break-word;" cellspacing="1" cellpadding="0" class="class1_table">
					<thead style="background:white">
			          <div style="text-align:center;"><b>菜单功能列表 -【${shangjgn}</b></div>
					<tr>
								<td class="class1_thead th">
									功能ID
								</td>
								<td class="class1_thead th">
									子功能ID
								</td>
								<td class="class1_thead th">
									功能名称
								</td>
								<td class="class1_thead th">
									功能URL
								</td>
								<td class="class1_thead th">
									功能顺序
								</td>
								<td class="class1_thead th">
									上级功能
								</td>
								<td class="class1_thead th">
									功能状态
								</td>																
								<td class="class1_thead th">
									操作
								</td>
							</tr>
						</thead>
						<logic:iterate id="menu" name="list" indexId="ind" type="com.unitop.sysmgr.bo.Menu">
							<tr>
								<td class="class1_td center">
									<bean:write name="menu" property="gongnid" />
								</td>
								<td class="class1_td center">
									<bean:write name="menu" property="zignid" />
								</td>
								<td class="class1_td center">
									<bean:write name="menu" property="gongnmc" />
								</td>
								<td class="class1_td center">
									<bean:write name="menu" property="gongnurl" />
								</td>
								<td class="class1_td center">
									<bean:write name="menu" property="gongnsx" />
								</td>
								<td class="class1_td center">
									<bean:write name="menu" property="shangjgn" />
								</td>
								<td class="class1_td center">
									<bean:write name="menu" property="zhuangt" />
								</td>																								
								<td class="class1_td center">
									<a href="doMenu.do?method=find&gongnid=<bean:write name='menu' property='gongnid' />&zignid=<bean:write name='menu' property='zignid' />">修改</a>
<a href="javascript:if(confirm('确定要删除？'))window.location.href='doMenu.do?method=delete&gongnid=<bean:write name='menu' property='gongnid' />&zignid=<bean:write name='menu' property='zignid' />&shangjgn=${shangjgn}'">删除</a>
								</td>
							</tr>
						</logic:iterate>
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
