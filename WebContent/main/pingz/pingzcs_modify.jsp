<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script type="text/javascript" src="js/shield.js"></script>
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<%@ include file="/common/yewgz.jsp"%>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					凭证销售 - 凭证出售
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					<form id="form1" name="form1" method="post" action="">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="aligncenter w70">
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
		<br>
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
		<html:form styleId="form1" action="pingz.do?method=update"
			method="post">
			<table width="97%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<html:hidden property="jigh" value="${clerk.orgcode}"/>
				<html:hidden property="guiyh" value="${clerk.code}"/>
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
									<th colspan="2" class="class1_thead th">
										凭证出售信息
									</th>
								</tr>
							</thead>
							<tr>
								<td  align="right">
								</td>
							</tr>
							
							<tr>
								<td class="class1_td w220  alignright">
									<b>柜员号&nbsp;&nbsp;&nbsp;&nbsp;</b>
								</td>
								<td class="class1_td alignleft w220">
									${clerk.code}
								</td>
							</tr>
							<tr>
								<td class="class1_td w220  alignright">
									<b>凭证号&nbsp;&nbsp;&nbsp;&nbsp;</b>
								</td>
								<td class="class1_td alignleft w220">
								${pingzmx.pingzh}
									<input type="hidden" name="pingzh" id="pingzh" value="${pingzmx.pingzh}"/>
								</td>
							</tr>
							<tr>
								<td class="class1_td w220  alignright">
									<b>提交日期&nbsp;&nbsp;&nbsp;&nbsp;</b>
								</td>
								<td class="class1_td alignleft w220">
								${pingzmx.riq}
									<input type="hidden" name="pingzh" id="pingzh" value="${pingzmx.riq}"/>
								</td>
							</tr>
							<tr>
								<td class="class1_td w220  alignright">
									<b>凭证类型&nbsp;&nbsp;&nbsp;&nbsp;</b>
								</td>
								<td  class="class1_td alignleft w220">
									<select id="pingzlx" name="pingzlx" style="width:135px;">
											<option value ="支票">支票</option>
											<option value ="汇票">汇票</option>
									</select>
								</td>
							</tr>
							<tr>
								<td id="zhangh" name="zhangh" class="class1_td w220  alignright">
									<b>账号&nbsp;&nbsp;&nbsp;&nbsp;<b/>
								</td>
								<td class="class1_td alignleft w220">
									<input type="text" name="zhangh" id="zhangh" value="${pingzmx.zhangh}" styleClass="inputField required acocunt"/>
								</td>
							</tr>
							<tr>
								<td class="class1_td w220  alignright">
									<b>户名&nbsp;&nbsp;&nbsp;&nbsp;<b/>
								</td>
								<td class="class1_td alignleft w220">
									<input type="text" name="hum" id="hum"  styleClass="inputField" value="${pingzmx.hum}"/>
								</td>
							</tr>
							<tr>
								<td class="class1_td w220  alignright">
									<b>状态&nbsp;&nbsp;&nbsp;&nbsp;<b/>
								</td>
								<td class="class1_td alignleft w220">
									<input type="text" name="zhuangt" id="zhuangt"  styleClass="inputField" value="${pingzmx.zhuangt}"/>
								</td>
							</tr>
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
				<button type="submit" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					保存
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width:60px" onclick="history.back()"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					取消
				</button>
			</div>
		</html:form>
	</body>
</html>
