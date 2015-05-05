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
		 $(document).ready(function() {
		 $("#zhangh").focus();
		   $("#form1").validate({
		   errorLabelContainer:"#error div.error",
		   wrapper:"li",
		   
		   submitHandler:function(form){
		   if (confirm("确定要保存出售信息？"))
		   	form.submit();
		   }
		 }) 
		 });
		 
		 	function inputpz(){
		 		getHumForNet();
			 	var obj1 = document.getElementById("pingzlx").value;
			 	var obj2 = document.getElementById("qispzh");
		 		var obj3 = document.getElementById("zhongzpzh");
		 		//产品中不应该包含项目中特色
		 		//if(obj1=="转账支票"){
		 		//	obj2.value = 'JZ';
			 	//	obj3.value = 'JZ';
				// }else{
				//	obj2.value = 'JX';
				// 	obj3.value = 'JX';
				// }
			 }
			 
		 	function countPz(){
			 	var obj1 = document.getElementById("qispzh").value;
			 	var obj2 = document.getElementById("zhongzpzh").value;

			 	if(obj1==""||obj2=="")
				{
					return;
			 	}
			 	
		 		obj1 = obj1.substring(2);
		 		obj2 = obj2.substring(2);
		 		var obj3 = document.getElementById("zhangs");
		 		var num=obj2-obj1+1;
		 		if(isNaN(num))
			 	{
		 			num = " ";
		 		}
		 		obj3.innerHTML=num;
		 		var obj3 = document.getElementById("zhangs_").value=num;
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
					凭证销售 - 凭证出售
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
		<html:form styleId="form1" action="pingz.do?method=saveHuiz"
			method="post">
			<table width="97%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<html:hidden property="jigh" value="${clerk.orgcode}" />
				<html:hidden property="guiyh" value="${clerk.code}" />
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
										凭证出售信息
									</th>
								</tr>
							</thead>
							<tr>
								<td align="right">
								</td>
							</tr>
							<tr>
								<td width="45%" class="class2_td  alignright">
									<b>机构号&nbsp;&nbsp;&nbsp;&nbsp;</b>
								</td>
								<td width="55%" class="class2_td alignleft w220">
									${clerk.orgcode}
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									<b>柜员号&nbsp;&nbsp;&nbsp;&nbsp;</b>
								</td>
								<td class="class2_td alignleft w220">
									${clerk.code}
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									<b>凭证类型&nbsp;&nbsp;&nbsp;&nbsp;</b>
								</td>
								<td class="class2_td alignleft">
									<select id="pingzlx" name="pingzlx" style="width: 135px;">
										<c:forEach items="${pingzlxlist}" var="plist">
											<option value="${plist.pingzmc}">
												${plist.pingzmc}
											</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									<b>账号&nbsp;&nbsp;&nbsp;&nbsp;<b />
								</td>
								<td class="class2_td alignleft">
									<input name="zhangh" id="zhangh" onblur="inputpz();"
										class="inputField required zhangh" />
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									<b>户名&nbsp;&nbsp;&nbsp;&nbsp;<b />
								</td>
								<td  class="class2_td alignleft w220">
								&nbsp;<span id="hum"></span>
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									<b>起始凭证号&nbsp;&nbsp;&nbsp;&nbsp;</b>
								</td>
								<td class="class2_td alignleft w220">
									<input name="qispzh" id="qispzh"
										class="inputField required qispzh" />
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									<b>终止凭证号&nbsp;&nbsp;&nbsp;&nbsp;</b>
								</td>
								<td class="class2_td alignleft w220">
									<input name="zhongzpzh" id="zhongzpzh" onblur="countPz();" class="inputField zhongzpzh" />
									<span> <input type="hidden" id="pzerror" /> </span>
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									<b>张数&nbsp;&nbsp;&nbsp;&nbsp;</b>
								</td>
								<td class="class2_td alignleft w220">
								&nbsp;<span id="zhangs"></span>
								</td>
							</tr>
							<input type="hidden" id="zhangs_" name="zhangs_"/>
							<!-- 
							<tr>
								<td class="class2_td w220  alignright">
									<b>本数&nbsp;&nbsp;&nbsp;&nbsp;</b>
								</td>
								<td class="class2_td alignleft w220">
									<input name="bens" id="bens"  styleClass="inputField required bens"/>本
								</td>
							</tr>
							 -->
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
					onclick='javascript:form1.reset();document.getElementById("zhangs").innerHTML="";document.getElementById("hum").innerHTML="";'
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