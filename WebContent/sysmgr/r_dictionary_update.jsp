<%@ page contentType="text/html; charset=GBK" language="java"
	errorPage="" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base target="_self">
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
		function doSubmit(){
			window.close();
			return;
		}
		</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					Ureport定制 - 报表管理 - 字典要素配置
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
		<html:form styleId="form" action="dictionaryMgr.do?method=updateDictionary"
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
										修改字典要素
									</th>
								</tr>
							</thead>

							<tr>
								<td class="class1_td w250">
									字典标识：
								</td>
								<td class="class1_td alignleft">
									<html:text  property="map(ZHIDBS)" maxlength="20" style="width:145px; "/>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									索引值：
								</td>
								<td class="class1_td alignleft">
									<html:text property="map(SUOYZ)"
										maxlength="20" style="width:145px;"/>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									转换值：
								</td>
								<td class="class1_td alignleft">
									<html:text  property="map(ZHUANHZ)"
										maxlength="20" style="width:145px; " />
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
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="doSubmit();">
					<img src="images/save1.gif" width="12" height="12"
						align="middle">
					保存
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="window.close();">
					<img src="images/back1.gif" width="11" height="11"
						align="middle">
					取消
				</button>
			</div>
		</html:form>
	</body>
</html>
