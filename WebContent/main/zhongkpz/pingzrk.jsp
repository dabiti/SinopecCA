 <%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ page import="com.unitop.config.SystemConfig"%>
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
		 //初始化时加载默认凭证类型的张数
		 getMeibzs();
		 //初始化时加载机构名称
		 getjigmc();
		 //初始化时清空数据
		 document.getElementById("qispzh_").value="";
		 document.getElementById("bens").value="1";
		 document.getElementById("zhongzpzh").innerHTML="";
		 var obj1 = document.getElementById("qispzh_").value;
		 obj1="";
		 $("#form1").validate({
		   errorLabelContainer:"#error div.error",
		   wrapper:"li",
		   submitHandler:function(form){
			   if (confirm("确定要保存入库信息？"))
			   {
			   	  form.submit();
			   	  $("#tu").html('<table align="center"><tr><td><img border="1" src="images/appInstall_animated.gif"/><b>入库信息录入中,请稍等......<b></td></tr></table>');
			   }
		   }
		 }) 
		 });
		 var meibzs = "1";
			//获取各种不同凭证类型的每本张数
		    function getMeibzs() {
				var pingzbs = document.getElementById("pingzlx").value;//凭证名称数据库中以ID形式保存
				$.post("zhongkpz.do?method=getPingzmbzs", {pingzbs:pingzbs}, function (data, textStatus) {
					if (textStatus = "success") 
					{
						//alert(data);
						meibzs = data;
					}
				}, "text");
				countPz();
			}
		 	function countPz(){
			 	var obj1 = document.getElementById("qispzh_").value;
			 	var obj2 = document.getElementById("bens").value;//本数
			 	var obj3 = document.getElementById("zhongzpzh");
                var obj4 = document.getElementById("pingzlx").value;//凭证类型
                var obj1_=parseFloat(obj1);
                var obj2_=parseFloat(obj2);
                var obj3_=parseFloat(obj3);
                var meibzs_=parseFloat(meibzs);
                //判断输入本数是否是纯数字形式的，正则表达式校验
                var reg=/^\d+$/;
                if(!reg.test(obj2))
                {   
                   alert("本数不是数字！请重新输入本数！"); 
                   return false; 
                }
                
			 	if(obj1==""||obj2=="")
				{
					return;
			 	}
			 	//终止凭证号=起始凭证号+本数*张Long q = Long.parseLong(qispzh);
			 	var num1 = obj1_ + obj2_ * meibzs_ - 1;//终止凭证号的整数值
		 		var cha = obj1.length - String(num1).length;
		 		var str = '0000000000';
		 	    var num = str.substring(0,cha) + num1;
		 		if(isNaN(num))
			 	{
		 			num = " ";
		 		}
		 		obj3.innerHTML=num;
		 		document.getElementById("zhongzpzh_").value=num;
		 		return true;
			 }	
			 
		    //获取机构信息（AJAX）
		    function getjigmc() {
				var code = $("#rukjg").val();
				var math = Math.random();
				$.post("orgManage.do?method=getOrgname", {math:math, code:code}, function (data, textStatus) {
					if (textStatus = "success") {
						var accountinfo = data.split(",");
						$("#jigmc").html('&nbsp'+'');
						if(accountinfo[0]==''||accountinfo[0]==null){alert('机构号不存在!');return};
						$("#jigmc").html(accountinfo[0]);
					}
				}, "text");
		   } 
	  	</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;' >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					重空凭证 - 凭证入库
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
		<html:form styleId="form1" action="zhongkpz.do?method=save"
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
										凭证入库信息
									</th>
								</tr>
							</thead>
							<tr>
								<td align="right">
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
							<tr>
								<td class="class2_td  alignright">
									<b>起始凭证号</b>
								</td>
								<td class="class2_td alignleft w220">
								    <html:text property="qispzh_" styleId="qispzh_"  styleClass="inputField required "  />
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									<b>本数</b>
								</td>
								<td class="class2_td alignleft w220">
								    <html:text property="bens" styleId="bens" value="1" onblur="countPz();" styleClass="inputField required "  />
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									<b>终止凭证号</b>
								</td>
								<td class="class2_td alignleft w220">
								&nbsp;<span id="zhongzpzh"></span>
								</td>
							</tr>
							<input type="hidden" id="zhongzpzh_" name="zhongzpzh_"/>
							<tr>
								<td class="class2_td  alignright">
									<b>入库机构</b>
								</td>
								<td class="class2_td alignleft w220">
								<html:text property="rukjg" styleId="rukjg" value ="${orgcode}" onblur='getjigmc(this)' styleClass="inputField required "  />
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									<b>机构名称</b>
								</td>
								<td class="class2_td alignleft w220">&nbsp;<span id="jigmc"></span>
								</td>
							</tr>
							<input type="hidden" id="jigmc" name="jigmc"/>
							<tr>
								<td class="class2_td  alignright">
									<b>入库时间</b>
								</td>
							<td class="class2_td alignleft w220">
							<input id="rukrq" name="rukrq" value ="${date}" type="text" maxlength="10" onclick="WdatePicker()"   class="inputField required rukrq" /></td>
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
				<button type="submit" style="width:60px" onclick="javascript:if(!countPz()){return false;}else{return true;}"
					onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12" align="absmiddle">
					保存
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width: 60px" 
					onclick='javascript:form1.reset();document.getElementById("zhongzpzh").innerHTML="";document.getElementById("qispzh_").value="";document.getElementById("bens").value="1";'
					onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/delete.gif" width="11" height="11" align="absmiddle">
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
				<%
				   SystemConfig systemConfig = SystemConfig.getInstance();
				   String shancrk = systemConfig.getValue("shancrk");
				   request.setAttribute("shancrk", shancrk);
											%>
			  <c:if test='${shancrk==1}'>
			  &nbsp;&nbsp;&nbsp;
				<button type="button" style="width: 60px" onclick="javascript:window.location.href='/zhongkpz.do?method=ruksc';"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					删除
				</button>
			  </c:if>
			</div>
		</html:form>
	</body>
</html>