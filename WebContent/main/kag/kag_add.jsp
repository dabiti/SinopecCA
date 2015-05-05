<%@page language="java" contentType="text/html;charset=GBK" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript">
		$(document).ready(function(){
			$("#form1").validate({
				errorLabelContainer : "#error div.error",
				wrapper : "li",
				submitHandler : function(form){
				form.submit();
				}
			})
		});
	</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					印鉴卡管理-卡柜维护
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
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td id="errorMessage"class="orange error">
					<div class="error orange"></div>
				</td>
			</tr>
		</table>
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange error">
						<div class="error orange">
							${msg}
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
		<html:form styleId="form1" action="kag.do?method=save"
			method="post">
			<table width="100%" border="0" align="center" cellpadding="0"
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
									<th colspan="2" class="class1_thead th">
										初始化卡柜
									</th>
								</tr>
							</thead>
							<tr>
								<td align="right">
								</td>
							</tr>
							<tr>
								<td width="45%" class="class2_td  alignright">
									卡柜ID：
								</td>
								<td  width="55%" class="class2_td alignleft w220">
									<html:text property="kagid" styleId="kagid" 
										styleClass="inputField required kagid"
										 />
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									卡柜名称：
								</td>
								<td class="class1_td alignleft">
									<html:text property="kagmc" styleId="kagmc" 
										styleClass="inputField required kagmc "
									/>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									卡柜IP:
								</td>
								<td class="class1_td alignleft">
									<html:text property="kagip" styleId="kagip" 
										styleClass="inputField required kagip"
										 />
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									卡柜层数：
								</td>
								<td class="class1_td alignleft">
									<html:text property="cengs" styleId="cengs"
										styleClass="inputField required" />
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									抽屉数：
								</td>
								<td class="class1_td alignleft">
									<html:text property="chouts" styleId="chouts"
										styleClass="inputField required"
										/>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									抽屉空间：
								</td>
								<td class="class1_td alignleft">
									<html:text property="choutkj" styleId="choutkj"
										styleClass="inputField required "
										 />
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
