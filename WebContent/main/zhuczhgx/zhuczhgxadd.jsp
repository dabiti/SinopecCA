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
		<script type="text/javascript" src="js/shield.js"></script>
		<script language="javascript" type="text/javascript" src="js/date/time.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript">
		$(document).ready(function() {
		 $("#zhangh").focus();
		 $("#form1").validate({
		   errorLabelContainer:"#error div.error",
		   wrapper:"li",
		   submitHandler:function(form){
		   if (confirm("确定要新建主从账户关系？"))
		   	form.submit();
		   }
		 }) 
		 });
		 //获取从账户信息
	   function getcongzh() {
	var congzh = $("#congzh").val();
	var math = Math.random();
	$.post("zhuczh.do?method=getcongzh", {math:math, congzh:congzh}, function (data, textStatus) {
		if (textStatus = "success") {
			var accountinfo = data.split(",");
			$("#congzhname").html('&nbsp'+'');
			$("#congzhState").html('&nbsp'+'');
			$("#congzhVerifyState").html('&nbsp'+'');
			$("#youwyj").html('&nbsp'+'');
			if(accountinfo[0]==''||accountinfo[0]==null){alert('账户不存在或没有查询账户信息!');return};
			$("#congzhname").html(accountinfo[0]);
			$("#congzhState").html(accountinfo[1]);
			$("#congzhVerifyState").html(accountinfo[2]);
			$("#youwyj").html(accountinfo[3]);
		}
	}, "text");
   }
      //获取主账户信息
	   function getzhuzh() {
	var mainAccount = $("#mainAccount").val();
	var math = Math.random();
	$.post("zhuczh.do?method=getzhuzh", {math:math, mainAccount:mainAccount}, function (data, textStatus) {
		if (textStatus = "success") {
			var accountinfo = data.split(",");
			$("#mainAccountName").html('&nbsp'+'');
			$("#mainAccountState").html('&nbsp'+'');
			$("#mainAccountVerifyState").html('&nbsp'+'');
			$("#mainYouwyj").html('&nbsp'+'');
			if(accountinfo[0]==''||accountinfo[0]==null){alert('账户不存在或没有查询账户信息!');return};
			$("#mainAccountName").html(accountinfo[0]);
			$("#mainAccountState").html(accountinfo[1]);
			$("#mainAccountVerifyState").html(accountinfo[2]);
			$("#mainYouwyj").html(accountinfo[3]);
		}
	}, "text");
} 
	  </script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					特殊业务  - 主从账户关系维护
				</td>
			</tr>
		</table>
		<br>
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
		<html:form styleId="form1" action="zhuczh.do?method=xinjgx" method="post">
			<table width="97%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="up-left" />
						<td class="up-middle" />
							<td class="up-right" />
				</tr>
				<tr>
					<td background="${pageContext.request.contextPath}/images/table_arrow_05.gif">
					&nbsp;
				</td>
					<td>
						<table width="100%" border="0" cellspacing="1" cellpadding="0"
							class="class1_table">
							<thead class="class1_thead">
								<tr>
									<th colspan="2" class="class1_thead th">
										新建主从关系信息
									</th>
								</tr>
							</thead>
							<tr>
								<td align="right">
								</td>
							</tr>
						   <tr>
			          	  <td width="42%" class="class1_td alignright">从账号</td>          
				          <td width="58%" class="class1_td alignleft">
          	                  <input id="congzh" name="congzh" type="text" onkeydown='if(event.keyCode==13){getcongzh(this)}' maxlength="25"  class="inputField required" style="width:175px;"  />
			          	</tr>
			          	<tr>
			          		<td width="42%" class="class1_td alignright">户名</td>          
				        	<td width="58%" class="class1_td alignleft" id="congzhname">&nbsp;</td>
			          	</tr>
			          	<tr>
			          		<td width="42%" class="class1_td alignright">账户状态</td>          
				        	<td width="58%" class="class1_td alignleft" id="congzhState">&nbsp;</td>
			          	</tr>
			          	<tr>
			          		<td width="42%" class="class1_td alignright">印鉴审核状态</td>          
				        	<td width="58%" class="class1_td alignleft" id="congzhVerifyState">&nbsp;</td>
			          	</tr>
			          	<tr>
			          		<td width="42%" class="class1_td alignright">有无印鉴</td>          
				        	<td width="58%" class="class1_td alignleft" id="youwyj">&nbsp;</td>
			          	</tr>
			          	<tr>
			          		<td width="42%" class="class1_td alignright">主账号</td>          
				            <td width="58%" class="class1_td alignleft">
          	                  <input id="mainAccount" name="mainAccount" type="text" onkeydown='if(event.keyCode==13){getzhuzh(this)}'maxlength="25"  class="inputField required" style="width:175px;"  />
			          	</tr>
			          	<tr>
			          		<td width="42%" class="class1_td alignright">户名</td>          
				        	<td width="58%" class="class1_td alignleft" id="mainAccountName">&nbsp;</td>
			          	</tr>
			          	<tr>
			          		<td width="42%" class="class1_td alignright">账户状态</td>          
				        	<td width="58%" class="class1_td alignleft" id="mainAccountState">&nbsp;</td>
			          	</tr>
			          	<tr>
			          		<td width="42%" class="class1_td alignright">账户审核状态</td>          
				        	<td width="58%" class="class1_td alignleft" id="mainAccountVerifyState">&nbsp;</td>
			          	</tr>
			          	<tr>
			          		<td width="42%" class="class1_td alignright">有无印鉴</td>          
				        	<td width="58%" class="class1_td alignleft" id="mainYouwyj">&nbsp;</td>
			          	</tr>
			          	<tr>
			          		<td width="42%" class="class1_td alignright">复用日期</td>    
				        	<td width="58%" class="class1_td alignleft">
				          	<input id="fuyrq" name="fuyrq" type="text" value="${date}" onclick="WdatePicker()" class="inputField fuyrq" style="width:85px;"/></td>
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
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					保存
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width: 60px"
					onclick='javascript:form1.reset();document.getElementById("zhongzpzh").innerHTML="";'
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/delete.gif" width="11" height="11"
						align="absmiddle">
					清空
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