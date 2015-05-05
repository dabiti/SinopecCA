<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/shield.js"></script>
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/pagejs/mergeorg.js"></script>
		<%@ include file="/common/validator.jsp"%>
				<%@ include file="/common/yewgz.jsp"%>	
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
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
				<td>&nbsp;
					
				</td>
				<td>&nbsp;
					
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
		<html:form styleId="form1" action="/orgManage.do?method=mergeOrg" method="post">
			<table width="97%"  border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td class="up-left" /><td class="up-middle" /><td class="up-right" />
				</tr>
				<tr>
					<td background="images/table_arrow_05.gif">&nbsp;
						
					</td>
					<td>
						 <table width="100%"  border="0" cellspacing="1" cellpadding="0" class="class1_table">
							<thead class="class1_thead">
								<tr>
									<th colspan="4" class="class1_thead th">撤并机构</th>
								</tr>
							</thead>
							<tr>
								<td  class="class1_td w250">原机构号:</td>
								<td class="class1_td alignleft" ><span id="oldOrgCode"><bean:write name="orgForm" property="code" /></span></td>
							</tr>
							<tr>
								<td class="class1_td w250">原机构名称:</td>
								<td class="class1_td alignleft"><bean:write name="orgForm" property="name" /></td>
							</tr>
							<tr>
								<td class="class1_td w250">新机构号:</td>
								<td class="class1_td alignleft"><html:text styleId="orgCode" property="newcode" styleClass="inputField required netpointflag" style="width:150px;" maxlength='4' onchange="getOrgName()" />
									<span style="color:red;" id="orgMsg"></span>
								</td>
							</tr>
							<input type="hidden" name="code" value="<bean:write name="orgForm" property="code"/>" />
							<tr>
								<td class="class1_td w250">新机构名称:</td>
								<td class="class1_td alignleft">&nbsp;<span id="orgName"></span></td>
							</tr>
						</table>
					</td>
					<td background="images/table_arrow_07.gif">&nbsp;</td>
				</tr>
				<tr>
					<td class="bottom-left" />
						<td class="bottom-middle" />
							<td class="bottom-right" />
				<br></td><br></td><br></tr>
			</table>
			<div class="funbutton">
				<button type="button" style="width: 60px"
					onmouseover="this.className='buttom2'" onclick="formSubmit();"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					确定
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width: 60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="history.back()">
					<img src="images/back1.gif" width="11" height="11" align="absmiddle">
					返回
				</button>
			</div>
		</html:form>
	</body>
</html>