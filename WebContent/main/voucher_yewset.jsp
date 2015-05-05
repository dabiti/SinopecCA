<%@ page contentType="text/html; charset=GBK" language="java"  isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript" src="js/pagejs/voucher.js">
</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					系统管理 - 凭证参数设置
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
				<td id="errorMessage" class="orange error">
					<div class="error orange"></div>
				</td>
			</tr>
		</table>
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
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
		<html:form styleId="form1" action="voucherManage.do?method=voucherSet&pingzh=${vouch.pingzbs}" method="post">
			<table width="97%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="up-left" />
						<td class="up-middle" />
							<td class="up-right" />
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
										凭证业务设置
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									凭证标识：
								</td>
								<td class="class1_td alignleft">
									${vouch.pingzbs}
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									凭证名称：
								</td>
								<td class="class1_td alignleft">
									${vouch.pingzmc}
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									系统类型：
								</td>
								<td class="class1_td alignleft">
									<table>
										<tr>
											<td>
												<img src="images/people.gif" width="12" height="12"
													align="absmiddle">
												类型列表
											</td>
											<td>
											</td>
											<td>
												<img src="images/people.gif" width="12" height="12"
													align="absmiddle">
												所选列表
											</td>
										</tr>
										<tr>
											<td>
												<select name="s1" id="s1" multiple="multiple"
													style="width: 150px; height: 100px;"
													ondblclick="moveTo('s1','s2');">
													<c:forEach items="${systemTypeList}" var="systemTypeList">
														<option value="${systemTypeList.xitlx}">
															${systemTypeList.xitlx}
														</option>
													</c:forEach>
												</select>
											</td>
											<td>
												<input type="button" value="--&gt;" style="width: 100px;"
													onclick="moveTo('s1','s2');" />
												<br />
												<input type="button" value="--&gt;&gt;"
													style="width: 100px;" onclick="moveAllTo('s1','s2');" />
												<br />
												<input type="button" value="&lt;--" style="width: 100px;"
													onclick="moveTo('s2','s1');" />
												<br />
												<input type="button" value="&lt;&lt;--"
													style="width: 100px;" onclick="moveAllTo('s2','s1');" />
												<br />
											</td>
											<td>
												<select name="s2" id="s2" multiple="multiple"
													style="width: 150px; height: 100px;"
													ondblclick="moveTo('s2','s1');">
													<c:forEach items="${xitList}" var="xit">
																<option value="${xit.id.xitlx}">
																	${xit.id.xitlx}
																</option>
															</c:forEach>
												</select>
											</td>
										</tr>
									</table>
									<tr>
										<td class="class1_td w250">
											印章选择：
										</td>
										<td class="class1_td alignleft">
											<table>
												<tr>
													<td>
														<img src="images/people.gif" width="12" height="12"
															align="absmiddle">
														印章列表
													</td>
													<td>
													</td>
													<td>
														<img src="images/people.gif" width="12" height="12"
															align="absmiddle">
														所选列表
													</td>
												</tr>
												<tr>
													<td>
														<select name="s3" id="s3" multiple="multiple"
															style="width: 150px; height: 100px;"
															ondblclick="moveTo('s3','s4');">
															<c:forEach items="${YinjTypeList}" var="YinjType">
																<option value="${YinjType.yinjlx}">
																	${YinjType.yinjlx}
																</option>
															</c:forEach>
														</select>
													</td>
													<td>
														<input type="button" value="--&gt;" style="width: 100px;"
															onclick="moveTo('s3','s4');" />
														<br />
														<input type="button" value="--&gt;&gt;"
															style="width: 100px;" onclick="moveAllTo('s3','s4');" />
														<br />
														<input type="button" value="&lt;--" style="width: 100px;"
															onclick="moveTo('s4','s3');" />
														<br />
														<input type="button" value="&lt;&lt;--"
															style="width: 100px;" onclick="moveAllTo('s4','s3');" />
														<br />
													</td>
													<td>
														<select name="s4" id="s4" multiple="multiple" style="width: 150px; height: 100px;" ondblclick="moveTo('s4','s3');">
															<c:forEach items="${yinjList}" var="YinjType">
																<option value="${YinjType.id.yinjlx}">
																	${YinjType.id.yinjlx}
																</option>
															</c:forEach>
														</select>
													</td>
												</tr>
											</table>
										</td>
									</tr>
						</table>
					</td>
					<td background="images/table_arrow_07.gif">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bottom-left" />
						<td class="bottom-middle" />
							<td class="bottom-right" />
				</tr>
			</table>
			<div class="funbutton">
				<button type="submit" style="width: 60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1" onclick="tijiaoEvent();">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					保存
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width: 60px" onclick="history.back()"
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