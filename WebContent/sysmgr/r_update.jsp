<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="js/autocompleteSet.js"></script>
		<%@ include file="/common/validator.jsp" %>	
		<script type="text/javascript">
			$(document).ready(function() {
				 $("#form").validate({
				   wrapper:"li",
				   submitHandler:function(form)
				   {
				   		form.submit();
				   }
				 })
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
					Ureport定制 - 报表管理
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
		<html:form styleId="form" action="ureportMgr.do?method=update"
			method="post">
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
										修改报表
									</th>
								</tr>
							</thead>

							<tr>
								<td class="class1_td w250">
									报表标识：
								</td>
								<td class="class1_td alignleft">
									<html:text styleClass="inputField required " property="map(BAOBBS)" readonly="true" />
									*主键，自定义
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									报表名称：
								</td>
								<td class="class1_td alignleft">
									<html:text property="map(BAOBMC)" styleClass="inputField required"/>
									*例如：验印日志查询
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									报表标题：
								</td>
								<td class="class1_td alignleft">
									<html:text  property="map(BAOBBT)"  styleClass="inputField required " />
									*报表表头前面的标题，例如：验印日志列表
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
								是否可用：
								</td>
								<td class="class1_td alignleft">
								<html:select property="map(SHIFKY)">
									<logic:equal name="CustomForm" property="map(SHIFKY)" value="是">
										<option value="是" selected=selected>是</option>
										<option value="否">否</option>
									</logic:equal>
									<logic:equal name="CustomForm" property="map(SHIFKY)" value="否">
										<option value="是">是</option>
										<option value="否" selected=selected>否</option>
									</logic:equal>
								</html:select>
								*功能是否启用
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
								查询条数：
								</td>
								<td class="class1_td alignleft">
								<html:text  property="map(FENYTJ)" styleClass="inputField required "/>
								*每次显示多少条数据
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
								数据获取标识：
								</td>
								<td class="class1_td alignleft">
								<html:textarea property="map(SHUJHQFS)" cols="80" rows="3"></html:textarea>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
								自定义类：
								</td>
								<td class="class1_td alignleft">
								<html:text  styleClass="inputField "   property="map(ZHIDYL)" style="width:499px; " />
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
				<button type="submit" style="width:60px" type="submit"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					保存
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="history.back()">
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					取消
				</button>
			</div>
		</html:form>
	</body>
</html>
