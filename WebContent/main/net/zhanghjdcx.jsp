<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link href="style/jquery.autocomplete.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="js/autocompleteSet.js"></script>
		<script src="js/pagejs/ocx.js" type="text/javascript"></script>
		<%@ include file="/common/yewgz.jsp"%>
<script language="javascript">  
var type=null;
$(document).ready(function(){
	$("#form1").validate({
   	errorLabelContainer:"#error div.error",
   	wrapper:"li",
   	submitHandler:function(){
		document.form1.action = "accountinfo.do?method=net_select";
		if(type=='excel'){
	   	document.form1.action = "accountinfo.do?method=net_excel";
		type=null;
		}
		document.form1.submit();
   },
   debug:false
});
$("#clear").click(function(){
		$(":input").unautocomplete();
	});
})
function excel(type,account){
			this.type=type;
			document.form1.button1.click();
}
function submitEnter() {
	if (window.event.keyCode == 13) {
		document.form1.button1.click();
	}
}
</script>
<SCRIPT language=Javascript> 
	function onChange(i){
		childSort=document.all("child" + i);
		obj_image=document.all("image" + i);
		if(childSort.style.display=="none")
		{
			childSort.style.display="";
		}else{
			childSort.style.display="none";
		}
	}
</SCRIPT>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					账户查询 - 账户简单查询
				</td>
			</tr>
			<tr>
				<td>&nbsp;
					
				</td>
				<td>
					<form id="form1" name="form1" method="post">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="class1_td w70">
									账号
								</td>
								<td class="class1_td alignleft">
									<input id="account" name="zhangh" type="text" class="inputField account required" value="${Zhanghb.zhangh}" onkeydown="autoComplete()"/><font color=red>*</font> 
								</td>
								<td class="class1_td w70">&nbsp;
								</td>
								<td class="w70">
									<button type="submit" id="button1" style="width:60px"
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
		<br>
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
							<html:errors />
						</td>
					</tr>
				</logic:messagesPresent>
			</table>
		</form>
				<table width="97%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">&nbsp;
					
				</td>
				<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="0" style="table-layout:fixed;"  class="class1_table">
						<thead class="class1_thead">
							<tr>
								<th colspan="4" class="class1_thead th">
									账户简单查询 
								</th>
							</tr>
						</thead>
						<c:if test='${Zhanghb.zhuzh != null&&Zhanghb.zhuzh!=""}' >
							<tr>
								<td class="class1_td alignright w100">主账号</td>
								<td class="class1_td alignleft">${Zhanghb.zhuzh}</td>
								<td class="class1_td alignright w100">&nbsp;</td>
								<td class="class1_td alignleft">&nbsp;</td>
							</tr>
						</c:if>
						<tr>
							<td width="11%"  class="class1_td alignright w100" >
								账号
							</td>
							<td  width="46%"  class="class1_td alignleft">
								${Zhanghb.zhangh}&nbsp;
							</td>
							<td width="10%" class="class1_td alignright w100">
							<unitop:HasPrivilegeForZignTag name="00012|2">
								账户预留印鉴
							</unitop:HasPrivilegeForZignTag>
							</td>
							<td  width="33%"  class="class1_td alignleft ">
							<unitop:HasPrivilegeForZignTag name="00012|2">
							    <c:if test='${Zhanghb.zhangh!=null}' >
								<a href='javascript:try{obj.ShowOcxForm("{\"ocxid\":\"3\",\"guiyxx\":${jsonClerkrStr},\"ocxparam\":{\"zhangh\":\"${Zhanghb.zhangh}\"}}");}catch(e){alert("系统检测：本机没有安装验印插件!");};'>浏览</a>
								</c:if>
							</unitop:HasPrivilegeForZignTag>&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100 ">
							<unitop:HasPrivilegeForZignTag name="00012|3">
								点击下载账号资料
							</unitop:HasPrivilegeForZignTag>
							</td>
							<td class="class1_td alignleft ">
							<unitop:HasPrivilegeForZignTag name="00012|3">
							<c:if test='${Zhanghb.zhangh!=null}' >
								<img src="images/table/xls.gif" onclick="excel('excel','${Zhanghb.zhangh}')">
							</c:if>&nbsp;
							</unitop:HasPrivilegeForZignTag>
							</td>
							<td class="class1_td alignright w100">
							<unitop:HasPrivilegeForZignTag name="00012|1">
								 印鉴卡影像
							</unitop:HasPrivilegeForZignTag>
							</td>
							<td class="class1_td alignleft ">
							<unitop:HasPrivilegeForZignTag name="00012|1">
								<c:if test='${Zhanghb.zhangh!=null}' >
									<a href="accountinfo.do?method=getAcccountYjkImageList&account=${Zhanghb.zhangh}" target="_blank">浏览</a>
								</c:if>
							</unitop:HasPrivilegeForZignTag>
							&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100">
							机构号
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.jigh}&nbsp;
							</td>
							<td class="class1_td alignright w100">
								户名
							</td>
							<td class="class1_td alignleft " style="word-wrap:break-word;" >
								${Zhanghb.hum}&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100">
								地址
							</td>
							<td class="class1_td alignleft " style="word-wrap:break-word;" >
								${Zhanghb.diz}&nbsp;
							</td>
							<td class="class1_td alignright w100">
								邮政编码
							</td>

							<td class="class1_td alignleft ">
								${Zhanghb.youzbm}&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100">
								开户日期
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.kaihrq}&nbsp;
							</td>
							<td class="class1_td alignright w100">
								账户状态
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.zhanghzt}	&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100">
								客户号
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.kehh}&nbsp;
							</td>
							<td class="class1_td alignright w100">
								货币号
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.huobh}&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100">
								是否有印鉴
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.youwyj}&nbsp;
							</td>
							<td class="class1_td alignright w100">
								是否有印鉴组合
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.youwzh}	
								&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100">
								审核状态
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.zhanghshzt}&nbsp;
							</td>
							<td class="class1_td alignright w100">
								印签标志
							</td>
							<td class="class1_td alignleft ">
								${Zhanghb.yinjshzt}	
								&nbsp;
							</td>
						</tr>
						<tr>
							<td class="class1_td alignright w100" >
								备注
							</td>
							<td class="class1_td alignleft " style="word-wrap:break-word;" >
								${Zhanghb.beiz}&nbsp;
							</td>
							<td class="class1_td alignright w100">
							<unitop:HasPrivilegeForZignTag name="00012|1">
								印鉴卡编号
							</unitop:HasPrivilegeForZignTag>
							</td>
							<td class="class1_td alignleft ">
							<unitop:HasPrivilegeForZignTag name="00012|1">
								${Zhanghb.yinjkbh}&nbsp;
							</unitop:HasPrivilegeForZignTag>
							</td>
						</tr>
					</table>
				</td>
				<td background="images/table_arrow_07.gif">&nbsp;
				</td>
			</tr>
			<tr>
				<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
			</tr>
		</table>
	 	<script language="JavaScript" FOR="obj" EVENT="SendEvent(sendMessage)">
	 		var rMessage = SendSesssion("uniDBInterface.jsp",sendMessage,"text");
    		return rMessage;
	    </script>
	    <OBJECT ID="obj" CLASSID="clsid:87100b1f-19fa-4266-a03e-0536ffa3c8af" style="display:none"/>
	</body>
</html>