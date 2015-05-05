<%@ page language="java" contentType="text/html;charset=gbk"  isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript" src="js/date/time.js"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script src="js/pagejs/validateForm.js" type="text/javascript"></script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					账户信息定制查询 - 新增定制查询
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
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange error">
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
		<html:form styleId="form1" method="post" action="accountcustom.do?method=save&TaskFlag=${time}">
			<table width="97%" border="0" align="left" cellpadding="0" cellspacing="0">
				<tr class="class1_td w70" >
					任务描述:<input type="text" id="rengwms" name="rengwms"  size=100 />
				</tr>
			</table>
			<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
				</tr>
				<tr>
					<td background="images/table_arrow_05.gif">
						&nbsp;
					</td>
					<td>
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<tr>
								<td class="class1_td w70">
									账号
								</td>
								<td class="class1_td alignleft">
									<html:text styleId="account" property="account" 
										styleClass="inputField account" />
								</td>
								<td class="class1_td w70">
									机构号
								</td>
								<td class="class1_td alignleft">
									<html:text styleId="orgCode" property="netpointflag"
										styleClass="inputField netpointflag" />
								</td>
								<td class="class1_td w70">
									客户号
								</td>
								<td class="class1_td alignleft">
									<html:text styleId="englishname" property="englishname"
									styleClass="inputField englishname" />
								</td>
								<td class="class1_td w70">

								</td>
								<td class="class1_td alignleft">

								</td>
							</tr>
							<tr>
								<td class="class1_td w70">
									货币号
								</td>
								<td class="class1_td alignleft">
									<html:select property="industrycharacter">
										<html:option value="全部" >
											全部
										</html:option>
										<c:forEach items="${industrycharacterlist}"
											var="industrycharacterlist">
											<html:option value="${industrycharacterlist.industrycharacter}">
												${industrycharacterlist.industrycharacter}
											</html:option>
										</c:forEach>
									</html:select>
								</td>
								<td class="class1_td w70">
									开户时间段
								</td>
								<td class="class1_td alignleft">
									<html:text styleId="opendate1" property="opendate" 
										 styleClass="inputField date_input"  style = 'width:62px;'  onclick="WdatePicker()" value="${clerk.loginDate}" />
									-
									<html:text styleId="opendate2" property="opendate1" 
										styleClass="inputField date_input"   style = 'width:62px;'  onclick="WdatePicker()" value="${clerk.loginDate}" />
								</td>
								<td class="class1_td w70">
									启用时间段
								</td>
								<td class="class1_td alignleft">
									<html:text styleId="startdate1" property="startdate" 
										styleClass="inputField date_input"  onclick="WdatePicker()"  style = 'width:62px;' value="${clerk.loginDate}" />
									-
									<html:text styleId="startdate2" property="startdate1" 
										styleClass="inputField date_input"  onclick="WdatePicker()"  style = 'width:62px;' value="${clerk.loginDate}" />
								</td>
								<td class="class1_td w70">
								</td>
								<td class="class1_td alignleft">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td class="class1_td w70">
									帐户状态
								</td>
								<td class="class1_td alignleft">
									<html:select property="accountstate">
												<html:option value="全部">
													全部
												</html:option>
												<html:option value="有效" >
													有效
												</html:option>
												<html:option value="账户冻结">
													冻结
												</html:option>
												<html:option value="销户">
													销户
												</html:option>
									</html:select>
								</td>
								<td class="class1_td w70">
									审核状态
								</td>
								<td class="class1_td alignleft">
									<html:select property="sealstate">
												<html:option value="全部">
													全部
												</html:option>
												<html:option value="已审">
													已审
												</html:option>
												<html:option value="未审" >
													未审
												</html:option>
									</html:select>
								</td>
								<td class="class1_td w70">
									印签标志
								</td>
								<td class="class1_td alignleft">
									<html:select property="yinqbz">
												<html:option value="全部">
													全部
												</html:option>
												<html:option value="有" >
													有
												</html:option>
												<html:option value="无">
													无
												</html:option>
									</html:select>
								</td>
									<td class="class1_td w70">
								</td>
								<td class="class1_td alignleft">
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
				<img src="images/save1.gif" width="12" height="12" align="absmiddle">
				保存
			</button>
			&nbsp;&nbsp;&nbsp;
			<button style="width:60px" onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1" type="button"
				onclick="history.back()">
				<img src="images/back1.gif" width="11" height="11" align="absmiddle">
				取消
			</button>
		</div>
		</html:form>
	</body>
</html>
