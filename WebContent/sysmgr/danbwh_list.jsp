<%@ page contentType="text/html; charset=gbk" language="java" errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/shield.js"></script>
		<link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="js/themes/icon.css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="js/easyloader.js"></script>
	</head>
	<body>
		<%@ include file="/common/jicgn.jsp"%>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="50" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					单表维护 - 单表维护设置
				</td>
			</tr>
		</table>
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td class="orange error">
						<div class="error orange">
							${status}
						</div>
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
		<form action="danbwh.do?method=select" method="post">
			<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="w70">
									<button type="button" style="width: 60px"
										onmouseover="this.className='buttom2'"
										onmouseout="this.className='buttom1'" class="buttom1"
										onClick="location.href='danbwh.do?method=add&gongnid=${danb.gongnid}'">
										<img src="images/add.gif" width="13" height="13"
											align="absmiddle">
										添加
									</button>
								</td>
								<td class="w70">
								</td>
							</tr>
						</table>

					</td>
				</tr>
				<tr>
					<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
				</tr>
				<tr>
					<td background="images/table_arrow_05.gif">&nbsp;</td>
					<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="0" class="class1_table">
						<thead style="background:white">
           					<div style="text-align:center;"><b>单表维护功能列表</b></div>
								<tr>
									<td class="class1_thead th">
										功能ID
									</td>
									<td class="class1_thead th">
										功能名称
									</td>
									<td class="class1_thead th">
										维护表名
									</td>
									<td class="class1_thead th">
										是否保存
									</td>
									<td class="class1_thead th">
										是否编辑
									</td>
									<td class="class1_thead th">
										是否删除
									</td>
									<td class="class1_thead th">
										功能URL
									</td>
									<td class="class1_thead th">
										操作
									</td>
								</tr>
							</thead>
							<c:forEach items="${list}" var="danb">
								<tr>
									<td class="class1_td center">
										${danb.gongnid}
									</td>
									<td class="class2_td">
										${danb.gongnmc}
									</td>
									<td class="class2_td">
										${danb.weihbm}
									</td>
									<td class="class1_td center">
										${danb.shifbc}
									</td>
									<td class="class1_td center">
										${danb.shifbj}
									</td>
									<td class="class1_td center">
										${danb.shifsc}
									</td>
									<td class="class2_td">
										${danb.gongnurl}
									</td>
									<td class="class1_td center">
										<a href="${danb.gongnurl}">预览</a>
										<a href="danbwh.do?method=find&gongnid=${danb.gongnid}">修改</a>
										<a href="javascript:if(confirm('你确定要删除所配置的单表维护设置吗?'))window.location.href='danbwh.do?method=delete&gongnid=${danb.gongnid}'">删除</a>
										<a href="danbwhbd.do?method=list&gongnid=${danb.gongnid}">字典要素设置</a>
										<a href="danbwhGuanxb.do?method=list&gongnid=${danb.gongnid}">主从关系设置</a>
										<a href="#" onclick="addJCGN('单表维护','${danb.gongnmc}','${danb.gongnurl}');">导入基础功能库</a>
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
		</form>
		</BR>
	</body>
</html>