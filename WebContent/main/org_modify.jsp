<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/shield.js"></script>
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script src="js/pagejs/validateForm.js" type="text/javascript"></script>
		<%@ include file="/common/yewgz.jsp"%>	
		<script type="text/javascript" language="javascript">
		function modiLeix(){
		if($("#chulizx").attr("checked")){
			$("#leixbs").val("4");
		}else{
			$("#leixbs").val("1");
		}

		}
		
		</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;' >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					系统管理 - 机构管理
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
			<form id=error>
				<div id="orange" class="orange ">
					<html:errors />
				</div>
				<div id="orange" class="orange error">
				</div>
			</form>
		<html:form  styleId="form1"  action="orgManage.do?method=updateOrg" method="post">
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
									<th colspan="2" class="class1_thead th">
										修改机构
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									上级机构代码：
								</td>
								<td class="class1_td alignleft">
									<bean:write name="orgForm" property="parentcode" />
									<html:hidden property="parentcode" />
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									上级机构名称：
								</td>
								<td class="class1_td alignleft">
									<bean:write name="orgForm" property="parentname" />
								</td>
								<html:hidden property="parentname" />
							</tr>
							<tr>
								<td class="class1_td w250">
									机构号：
								</td>
								<td class="class1_td alignleft">
									<bean:write name="orgForm" property="code" />
								</td>
								<html:hidden property="code" />
							</tr>
							<tr>
								<td class="class1_td w250">
									机构名称：
								</td>
								<td class="class1_td alignleft">
									<html:text property="name" styleId="organname" styleClass="inputField"/>
									
									<c:if test="${org.wdflag eq 3}">
										<c:if test="${org.leixbs eq 4}">
											<input type="checkbox" id="chulizx" checked='checked' onclick="modiLeix()"/><label for="chulizx" onclick="modiLeix()" >集中作业中心</label>
										</c:if>
										<c:if test="${org.leixbs eq 1}">
											<input type="checkbox" id="chulizx"  onclick="modiLeix()"/><label for="chulizx"  onclick="modiLeix()">集中作业中心</label>
										</c:if>
										
									</c:if>
									<html:hidden styleId="leixbs" property="leixbs" value='${org.leixbs}'/>
								</td>
							</tr>
							<!-- 	<tr>
									<td class="class1_td w250">
										人行支付系统行号：
									</td>
									<td class="class1_td alignleft">
										<html:text styleId="paymentCode"  property="paymentCode"  
											 styleClass="inputField required paymentCode" />
									</td>
								</tr> -->
						</table>
					</td>
					<td background="images/table_arrow_07.gif">&nbsp;</td> 
				</tr>
				<tr>
					<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
				</tr>
			</table>
		<div class="funbutton">
			<button type="submit" style="width:60px" 
				onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1">
				<img src="images/save1.gif" width="12" height="12" align="absmiddle">
				保存
			</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button" style="width:60px" onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1"
				onclick="history.back()">
				<img src="images/back1.gif" width="11" height="11" align="absmiddle">
				取消
			</button>
		</div>
		</html:form>
	</body>
</html>