<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript"> 
		 $(document).ready(function() {
		 $("#zhangh").focus();
		  //初始化时加载默认凭证类型的张数
		 getMeibzs();
		 document.getElementById("bens").value="1";
		 init_input(this);
		   $("#form1").validate({
		   errorLabelContainer:"#error div.error",
		   wrapper:"li",
		   submitHandler:function(form){
		   if (confirm("确定要保存该领用信息？"))
		   {
		      form.submit();
		      $("#tu").html('<table align="center"><tr><td><img border="1" src="images/appInstall_animated.gif"/><b>领用信息录入中,请稍等......<b></td></tr></table>');
		   }
		   }
		 }) 
		 });
		 //本数必须是数字校验
		 function test(){
		 	var obj2 = document.getElementById("bens").value;//本数
		 	//判断输入本数是否是纯数字形式的，正则表达式校验
                var reg=/^\d+$/;
                if(reg.test(obj2)==true)
                {   
                  return true;
                }
                else
                {   
                  alert("本数不是数字！请重新输入本数！");    
                  return false;
                }
			 	if(obj1==""||obj2=="")
				{
					return;
			 	}
		 }
		 var meibzs = "1";
		 //获取各种不同凭证类型的每本张数
		 function getMeibzs() {
				var pingzbs = document.getElementById("pingzlx").value;//凭证名称数据库中以ID形式保存
				$.post("zhongkpz.do?method=getPingzmbzs", {pingzbs:pingzbs}, function (data, textStatus) {
					if (textStatus = "success") 
					{
						meibzs = data;
					}
				}, "text");
		 }
		 
		 function init_input(input){
		 	var linygyStr="<td class=\"class2_td alignright\"><b>领用柜员</b></td> <td class=\"class2_td alignleft w220\"><input name=\"guiyh\" id=\"guiyh\" maxLength=\"7\" class=\"inputField required\"/></td>";
		 	var lingyjgStr="<td class=\"class2_td  alignright\"><b>领用机构</b></td><td class=\"class2_td alignleft w220\"><input name=\"lingyjg\" id=\"lingyjg\" maxLength=\"6\" class=\"inputField required\" /></td>";
		 	var jiglyfzrStr="<td class=\"class2_td  alignright\"><b>机构领用负责人</b></td><td class=\"class2_td alignleft w220\"><input name=\"jiglyfzr\" id=\"jiglyfzr\" maxLength=\"7\" class=\"inputField required\" /></td>";
		 	if('个人'==input.value)
		 	{
		 	    //个人时隐藏领用机构和领用机构负责人
		 	    $("#jigly").html('');
		 	    $("#jiglyr").html('');
		 	    //个人时添加领用柜员
		 		$("#linygy").html(linygyStr);
		 		
		 	}
		 	if('机构'==input.value)
		 	{ 
		 	    //机构时隐藏领用柜员
		 		$("#linygy").html('');
		 		//机构时添加领用机构、领用机构负责人
		 		$("#jigly").html(lingyjgStr);
		 		$("#jiglyr").html(jiglyfzrStr);
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
					重空凭证 - 凭证领用
				</td>
				
			</tr>
		</table>
		<br>
		<div id="tu"></div>
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
		<html:form styleId="form1" action="zhongkpz.do?method=saveLingy"
			method="post">
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
										凭证领用信息
									</th>
								</tr>
							</thead>
							<tr>
								<td align="right">
								</td>
							</tr>
							<tr>
							<td width="45%" class="class2_td  alignright">
									<b>领用方式<b/>
								</td>
								<td class="class2_td alignleft">
									<html:select property="lingymode" value="机构" style="width: 135px;" onchange="init_input(this)">
										<html:option value="机构">机构</html:option>
										<html:option value="个人">个人</html:option>
									</html:select>
								</td>
								</tr>
							<tr>
								<td width="45%" class="class2_td  alignright">
									<b>凭证类型</b>
								</td>
								<td class="class2_td alignleft">
									<select id="pingzlx" name="pingzlx" onchange="getMeibzs();" style="width: 135px;">
										<c:forEach items="${pingzlxlist}" var="plist">
											<option value="${plist.pingzbs}">${plist.pingzmc}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr id="jigly">
								<td class="class2_td  alignright">
									<b>领用机构</b>
								</td>
								<td class="class2_td alignleft w220">
								 <!--<html:text property="lingyjg" value ="${orgcode}" styleId="lingyjg" styleClass="inputField required"/>-->
								    <input name="lingyjg" id="lingyjg" value ="${orgcode}" class="inputField required" />
								</td>
							</tr>
							<tr id="jiglyr">
								<td class="class2_td  alignright">
									<b>机构领用负责人</b>
								</td>
								<td class="class2_td alignleft w220">
									<!--<html:text property="jiglyfzr"  styleId="jiglyfzr" styleClass="inputField required"/>-->
									<input name="jiglyfzr" id="jiglyfzr" class="inputField required" />
								</td>
							</tr>
							<tr id="linygy">
								<!--<td class="class2_td  alignright">
									<b>领用柜员</b>
								</td>
								<td class="class2_td alignleft w220">
									<input name="guiyh" id="guiyh" class="inputField " />
								</td>
							--></tr>
							<tr>
								<td class="class2_td  alignright">
									<b>本数</b>
								</td>
								<td class="class2_td alignleft w220">
									<html:text property="bens" styleId="bens" onblur="test();"styleClass="inputField required"/>
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
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					保存
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width: 60px"
					onclick='javascript:form1.reset();document.getElementById("bens").value="1";'
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