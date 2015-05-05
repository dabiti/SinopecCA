<%@ page contentType="text/html; charset=GBK" language="java"
	errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<%@ include file="/common/validator.jsp"%>
		<script type="text/javascript">
$(document).ready(function() {
	$("#form1").validate({
	   errorLabelContainer:"#error div.error",
	   wrapper:"li",
	   submitHandler:function(form){
		form.submit();
		window.close();
		}
	});
	})
</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					票据销售-打印服务维护
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		<br>
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td bgcolor="#FFCEFF" class="orange error">
						<div style="background-color: #FFCEFF;" class="error orange"></div>
					</td>
				</tr>
				<logic:messagesPresent>
					<tr>
						<td bgcolor="#FFCEFF" class="orange">
							<div id=abc class="abc">
								<html:errors />
							</div>
						</td>
					</tr>
				</logic:messagesPresent>
			</table>
		</form>
		<html:form styleId="form1" method="post"
			action="dayfw.do?method=update">
			<table width="97%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="15" height="14">
						<img src="images/table_arrow_01.gif" width="15" height="14">
					</td>
					<td background="images/table_arrow_08.gif" style="height: 14px;"></td>
					<td width="14" height="14">
						<img src="images/table_arrow_02.gif" width="14" height="14">
					</td>
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
										修改打印服务参数
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									打印服务ID：
								</td>
								<td class="class1_td alignleft">
								${dayfwcs.dayfwid}
							         <input id="dayfwid" name="dayfwid" type="hidden"
										value="${dayfwcs.dayfwid}" class="inputField2 "
										style="width: 120px;" />
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									打印服务名称：
								</td>
								<td class="class1_td alignleft">
								${dayfwcs.dayfwmc}
									<input id="dayfwmc" name="dayfwmc" type="hidden"
										value="${dayfwcs.dayfwmc}" class="inputField2 "
										style="width: 120px;" />
										*打印驱动名称
								</td>
							</tr>
							
							<tr>
								<td class="class1_td w250">
									打印服务Port：
								</td>
								<td class="class1_td alignleft">
								${dayfwcs.dayfwport} 
									<input id="dayfwport" name="dayfwport" type="hidden"
										value="${dayfwcs.dayfwport}" class="inputField2"
										style="width: 120px;" />
								</td>
							</tr>
							
							<tr>
								<td class="class1_td w250">
									已打印数量：
								</td>
								<td class="class1_td alignleft">
									<input id="dangqsl" name="dangqsl" type="text"
										value="${dayfwcs.dangqsl}" class="inputField2"
										style="width: 120px;" />
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									最大打印数量数：
								</td>
								<td class="class1_td alignleft">
									<input id="zuidsl" name="zuidsl" type="text"
										value="${dayfwcs.zuidsl}" class="inputField2"
										style="width: 120px;" />
								</td>
							</tr>
						</table>
					</td>
					<td background="images/table_arrow_07.gif">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="15" height="14">
						<img src="images/table_arrow_03.gif" width="15" height="14">
					</td>
					<td background="images/table_arrow_09.gif" style="height: 14px;"></td>
					<td width="14" height="14">
						<img src="images/table_arrow_04.gif" width="14" height="14">
					</td>
				</tr>
			</table>
			<div class="funbutton">
				<button type="submit" style="width: 60px" type="submit"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					保存
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width: 60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="history.back();">
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					取消
				</button>
			</div>
		</html:form>
	</body>
</html>
