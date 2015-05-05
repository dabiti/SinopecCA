<%@ page contentType="text/html; charset=GBK" language="java"
	errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script language="javascript">
	function tianjdyfw(){
      //  var dayfw = "{'dayfwmc':'Samsung ML-331x我们Series','dayfwip':'12.12.13.12','dayfwport':'1232','dayfwmac':'12dd3dda'}";
      try{
	      var dayfw = QRCODE_OCX.SelectPrintSource();
	      dayfw = encodeURI(encodeURI(dayfw))
	      if(dayfw!=''){
		        window.location.href = "dayfw.do?method=save&dayfwid="+dayfw;
	      }
      }catch(e){
          alert('请安装打印配置插件!');
      }
	}
</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="50" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					票据销售 - 打印服务参数列表
				</td>
			</tr>
		</table>
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td bgcolor="#FFCEFF" class="orange error">
						<div style="background-color: #FFCEFF;" class="error orange">
							${status}
						</div>
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
		<table width="97%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td class="w70">
								<button type="button" style="width: 60px"
									onmouseover="this.className='buttom2'"
									onmouseout="this.className='buttom1'" class="buttom1"
									onclick="tianjdyfw();">
									<img src="images/add.gif" width="13" height="13"
										align="absmiddle">
									添加
								</button>
							</td>
							<td class="w70">
					</table>

				</td>
			</tr>
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
								<th colspan="7" class="class1_thead th">
									打印服务参数列表
								</th>
							</tr>
							<tr>
								<td class="class1_thead th">
									打印服务ID
								</td>
								<td class="class1_thead th">
									打印服务名称
								</td>
								<td class="class1_thead th">
									打印服务端口
								</td>
								<td class="class1_thead th">
									已打印数量
								</td>
								<td class="class1_thead th">
									最大打印数量
								</td>
								<td class="class1_thead th">
									操作
								</td>
							</tr>
						</thead>
						<c:forEach items="${dayfwlist}" var="dayfwcs">
							<tr>
								<td class="class1_td center">
									${dayfwcs.dayfwid}
								</td>
								<td class="class2_td">
									&nbsp;${dayfwcs.dayfwmc}
								</td>
								<td class="class2_td">
									&nbsp;${dayfwcs.dayfwport}
								</td>
								<td class="class2_td">
									&nbsp;${dayfwcs.dangqsl}
								</td>
								<td class="class1_td center">
									${dayfwcs.zuidsl}
								</td>
								<td class="class1_td center">
									&nbsp;
									<a
										href='dayfw.do?method=forwardModify&dayfwid=${dayfwcs.dayfwid}'>修改</a>
									<a
										href="javascript:if(confirm('你确定要删除这项打印服务吗?'))window.location.href='dayfw.do?method=delete&dayfwid=${dayfwcs.dayfwid}'">删除</a>
								</td>
							</tr>
						</c:forEach>
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
		<OBJECT ID="QRCODE_OCX" WIDTH="0" HEIGHT="0"
			CLASSID="CLSID:9A1466AD-5639-4958-941C-6C6A39651B1C"></OBJECT>
	</body>
</html>
