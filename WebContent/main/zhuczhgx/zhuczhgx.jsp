<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script type="text/javascript" src="js/special-business.js"></script>
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script> 
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript"> 
		$(document).ready(function(){
			$("#newaccount").focus();
			$("#form1").validate({
			errorLabelContainer:"#error div.error",
			wrapper:"li",
			submitHandler:function(form){
				form.submit();
			}
			})
	   });
	   function doQux(czhangh,zzhangh){
			if(confirm('你确定要取消主从关系吗?'))
			{
				window.location.href='zhuczh.do?method=quxgx&czhangh='+czhangh+'&zzhangh='+zzhangh;
			}
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
					特殊业务 - 主从账户关系维护
				</td>
			</tr>
				</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<html:form styleId="form1" method="post" action="/zhuczh.do?method=weihzczhgx">
					    <table border="0" cellpadding="0" cellspacing="0">
							<tr>
							    <td class="class1_td w70">账号：</td>
								<td width="50%" class="class1_td alignleft">
								<input id="zhangh" name="zhangh" type="text" maxlength="12"  class="inputField zhangh" style="width:145px;" />
								&nbsp;&nbsp;&nbsp;
								<button type="submit" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onclick="return list(this)">
								<img src="images/search1.gif" align="middle">查询</button>
								</td>
								<td class="class1_td alignleft"  width="150px">
								<button type="button" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onclick="location.href='zhuczh.do?method=add'">
								<img src="images/add.gif"  align="middle">新建主从账户关系</button>
								</td>		
							</tr>
							</table>
				</html:form>
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
							<div id=abc class="abc"><html:errors /></div>
						</td>
					</tr>
				</logic:messagesPresent>
			</table>
		</form>
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
									<th colspan="6" class="class1_thead th">
										账户信息列表
									</th>
								</tr>
								<tr>
									<td class="class1_thead th">
										账号
									</td>
									<td class="class1_thead th">
										所属主账号
									</td>
									<td class="class1_thead th">
										户名
									</td>
									<td class="class1_thead th">
										账户状态
									</td>
									<td class="class1_thead th">
										开户日期
									</td>
									<td class="class1_thead th">
										操作
									</td>
								</tr>
							</thead>
							<c:forEach items="${list}" var="list">
									<tr>
										<td class="class1_td center">
											${list.zhangh}
										</td>
										<td class="class1_td center">
											&nbsp;${list.zhuzh}
										</td>
										<td class="class1_td center">
											${list.hum}
										</td>
										<td class="class1_td center">
											${list.zhanghzt}
										</td>
										<td class="class1_td center">
											${list.kaihrq}
										</td>
										<td class="class1_td center">
											<a href="javascript:doQux('${list.zhangh}','${list.zhuzh}');">取消主从关系</a>
										<!--<a href="zhuczh.do?method=quxgx&czhangh=${list.zhangh}&zzhangh=${list.zhuzh}">取消主从关系</a>-->
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
					<td class="bottom-left" />
						<td class="bottom-middle" />
							<td class="bottom-right" />
				</tr>
			</table>
		<div class="stat"></div>
	</body>
</html>