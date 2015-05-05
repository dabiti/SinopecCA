<%@ page contentType="text/html; charset=GBK" language="java"  isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script type="text/javascript" src="js/shield.js"></script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					帐户查询 - 帐户高级查询明细
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
									帐户简单查询
								</th>
							</tr>
						</thead>
						<tr>
							<td class="class1_td alignright w100 ">
								账号
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.account}&nbsp;
							</td>
							<td class="class1_td alignright w100 ">
							 印鉴卡影像
							</td>
							<td class="class1_td alignleft">
							<c:if test='${accountinfo.account!=null}' >
								<a href="accountinfo.do?method=getAcccountImageInfoList&account=${accountinfo.account}">浏览</a>
							</c:if>
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100 ">
								旧账号
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.oldaccount}&nbsp;
							</td>
							<td class="class1_td alignright w100">
								网点机构号
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.netpointflag}&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100">
								省行机构号
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.shenghjgh}&nbsp;
							</td>
							<td class="class1_td alignright w100">
								户名
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.accountname}&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100">
								地址
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.address}&nbsp;
							</td>
							<td class="class1_td alignright w100">
								邮政编码
							</td>

							<td class="class1_td alignleft w220">
								${accountinfo.postalcode}&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100">
								开户日期
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.opendate}&nbsp;
							</td>
							<td class="class1_td alignright w100">
								启用日期
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.startdate}&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100">
								客户号
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.englishname}&nbsp;
							</td>
							<td class="class1_td alignright w100">
								货币号
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.industrycharacter}&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100">
								通存通兑户
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.allexchange}&nbsp;
							</td>
							<td class="class1_td alignright w100">
								印鉴卡编号
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.checkperiod}	
								&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100">
								审核状态
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.sealstate}&nbsp;
							</td>
							<td class="class1_td alignright w100">
								印签标志
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.yinqbz}	
								&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100">
								帐户状态
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.accountstate}	&nbsp;
							</td>
							<td class="class1_td alignright w100">
								备注
							</td>
							<td class="class1_td alignleft w220">
								${accountinfo.remark}&nbsp;
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
			<button type="button" style="width:60px" onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1"
				onclick="history.back()">
				<img src="images/back1.gif" width="11" height="11" align="absmiddle">
				返回
			</button>
		</div>
	</body>
</html>
