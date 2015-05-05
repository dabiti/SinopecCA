<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script type="text/javascript" src="js/shield.js"></script>
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<%@ include file="/common/validator.jsp"%>
		<%@ include file="/common/yewgz.jsp"%>
		<script src="js/pagejs/valiateFormForImg.js" type="text/javascript"></script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'
		onload="document.getElementById('tu').innerHTML='';">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					统计分析 - 印鉴卡数量查询
				</td>
			</tr>
			<tr>
				<td class="class1_td" >
					&nbsp;
				</td>
				<td class="class1_td" style="text-align: left">
					<form id="form1" name="form1" method="post" action="yinjkOperate.do?method=queryyinjknum">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class=" w90">
									机构号：
								</td>
								<td class=" alignleft">
									<html:text property="netpointflag" styleId="netpointflag" name="YinjkForm" styleClass="inputField required"/><font color=red>*</font>
								</td>
								<input name='industrycharacter' type="hidden" value='全部' />
								<td class=" alignright w100">
									是否包含下级：
								</td>
								<td class=" alignleft">
									<html:select property="remark" name="YinjkForm">
										<html:option value="否">否</html:option>
										<html:option value="是">是</html:option>
									</html:select>
								</td>
							
								<td class="aligncenter w70">
									<button type="submit" style="width:60px"
										onmouseover="this.className='buttom2'"
										onmouseout="this.className='buttom1'" class="buttom1">
										<img src="images/search1.gif" width="13" height="13"
											align="middle">
										查询
									</button>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>

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
				<div id="tu"></div>
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
								<th colspan="4" class="class1_thead th">
									印鉴卡数量查询
								</th>
							</tr>
						</thead>
						<tr>
							<td colspan="4" align="right">
							</td>
						</tr>
						<tr>
							<td class="class1_td  w100 alignright">
								<b>印鉴卡总数<b/>
							</td>
							<td class="class1_td alignleft w220">
								&nbsp; ${YinjkNum} 个
							</td>
							<td class="class1_td  w100 alignright">
								&nbsp;
							</td>
							<td class="class1_td alignleft w220">
								&nbsp;
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

	</body>
</html>
