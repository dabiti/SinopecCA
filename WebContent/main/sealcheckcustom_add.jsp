<%@ page language="java" contentType="text/html;charset=gbk" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script language="javascript" type="text/javascript" src="js/date/time.js"></script>
<script language="javascript"> 
 $(document).ready(function() {
 var date = '${clerk.loginDate}';
 $("#begindate").val(date.substr(0,8)+'01');
 //验证
 $("#sealchecklogForm").validate({
   errorLabelContainer:"#error div.error",
   wrapper:"li",
   submitHandler:function(form){
   	form.submit();
   },
   errorPlacement: function(error, element) {
  	 var name=element.attr("name");
     if (name == "account" || name == "clerknum")
       error.insertAfter("#error");
     else
       error.insertAfter(element);
   }
});
})
</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					日志查询 - 验印日志定制查询
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
							<div id=abc class="abc">
								<html:errors />
							</div>
						</td>
					</tr>
				</logic:messagesPresent>
			</table>
		</form>
		<html:form styleId="sealchecklogForm" method="post" action="sealcheckcustom.do?method=save&TaskFlag=${time}">
		<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">
					&nbsp;
				</td>
				<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr class="class1_td w70">
							<td class="class1_td alignright w80">
								任务描述:
							</td>
							<td colspan=7 class="class1_td alignleft">
								<html:text styleId="rengwms" property="oldaccount" style="width:80%" styleClass="inputField required" />
							</td>
						</tr>
							<tr>
								<td class="class1_td alignright w80">
									账 号：
								</td>
								<td class="alignleft class1_td">
									<html:text styleId="account" property="account" styleClass="inputField account||clerknum account"/>
								</td>
								<td class="class1_td alignright w80">
									柜员代码：
								</td>
								<td class="alignleft class1_td">
									<html:text styleId="clerknum" property="clerknum" styleClass="inputField account||clerknum clerknum"/>
								</td>
								<td class="class1_td alignright w80">
									验印方式：
								</td>
								<td class="alignleft class1_td">
									<html:select property="checkmode" style="width:65px;">
										<html:option value="">全部</html:option>
										<html:option value="自动">自动</html:option>
										<html:option value="人工">人工</html:option>
										<html:option value="辅助">辅助</html:option>
									</html:select>
								</td>
								
								<td class="class1_td alignright w80">
								</td>
								<td class="alignleft class1_td"></td>
								
							</tr>
							<tr>
								<td class="class1_td alignright w80">
									凭证号：
								</td>
								<td class="alignleft class1_td">
									<html:text styleId="checknum" property="checknum"
										styleClass="inputField billnum" />
								</td>
								<td class="class1_td alignright w80">
									验印日期：
								</td>
								<td class="alignleft class1_td">
									<table width="250" border="0" cellspacing="0" cellpadding="0">
										<td>
											<html:text property="begindate" styleId="begindate"
												styleClass="inputField date_input"  onclick="WdatePicker()" value="${clerk.loginDate}" />
											－
											<html:text property="enddate" styleId="enddate"
												styleClass="inputField date_input" onclick="WdatePicker()" value="${clerk.loginDate}"/>
										</td>
									</table>
								</td>
								<td class="class1_td alignright w80">
									验印结果：
								</td>
								<td class="alignleft class1_td">
									<html:select property="checkresult" style="width:65px;">
										<html:option value="">全部</html:option>
										<html:option value="0">通过</html:option>
										<html:option value="1">未过</html:option>
									</html:select>
								</td>
								
								<td class="class1_td alignright w80">
								
								</td>
								<td class="alignleft class1_td">
								
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
		<div class="stat"></div>
		<div class="funbutton">
			<button type="submit" style="width:60px"
				onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1">
				<img src="images/save1.gif" width="12" height="12" align="absmiddle">
				保存
			</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button" style="width:60px" onmouseover="this.className='buttom2'"
				onmouseout="this.className='buttom1'" class="buttom1" onclick="history.back()">
				<img src="images/back1.gif" width="11" height="11" align="absmiddle">
				取消
			</button>
		</div>
		</html:form>

	</body>
</html>
