<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
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
<%@ include file="/common/validator.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
$("#form1").validate({
   errorLabelContainer:"#error div.error",
   wrapper:"li",
   submitHandler:function(form){
	form.submit();
	}
});
})
function doAddYewgz()
{
	var zdmc = document.getElementById('zidmc').value;
	if(zdmc == undefined)
	{
		return;
	}
	window.showModalDialog('yewgzConfig.do?method=modify&type=ureport&yuansid='+zdmc,"dialogWidth:650px;dialogHeight:700px;help:no;scroll:yes;center:yes;status:no");
	return;
}
</script>
</head>
<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;' >
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="38"><img src="images/main_place_pic.gif" width="38" height="32"></td>
    <td background="images/main_place_bg.gif">单表维护 - 修改单表维护配置</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
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
							<div id=abc class="abc"><html:errors /></div>
						</td>
					</tr>
				</logic:messagesPresent>
			</table>
</form>
<html:form styleId="form1"  method="post" action="danbwhGuanxb.do?method=update">
<html:hidden property="id"/>
<table width="97%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td class="up-left"/><td class="up-middle"/><td class="up-right"/>
  </tr>
  <tr>
    <td background="images/table_arrow_05.gif">&nbsp;</td>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="0" class="class1_table">
        <thead class="class1_thead">
          <tr>
            <th colspan="2" class="class1_thead th">修改单表维护配置</th>
          </tr>
        </thead>
        <tr>
          <td class="class1_td w250">主表功能ID：</td>
          <td class="class1_td alignleft">
         	<html:text property="zhubbh" styleClass="inputField required"  readonly="true"/>
          </td>
        </tr>
	    <tr>
          <td class="class1_td w250">主表字段：</td>
          <td class="class1_td alignleft">
          	<html:text property="zhubzd" styleClass="inputField" /> (数据库字段名称)
          </td>
        </tr>
        <tr>
          <td class="class1_td w250">子表ID：</td>
          <td class="class1_td alignleft">
         	<html:text property="zibbh" styleClass="inputField" /> (功能字段展示名称)
          </td>
        </tr>        
        <tr>
          <td class="class1_td w250">字表字段：</td>
          <td class="class1_td alignleft">
          	<html:text property="zibzd" styleClass="inputField" />
        </tr> 
        <tr>
          <td class="class1_td w250">子表维护名称：</td>
          <td class="class1_td alignleft">
          	<html:text property="zhibwhmc" styleClass="inputField" />
        </tr> 
        <tr>
          <td class="class1_td w250">是否链接字段：</td>
          <td class="class1_td alignleft">
          <html:select styleId="shifljzd" property="shifljzd" style="width:120px;">
				<html:option value="1">1-是</html:option>
				<html:option value="0">0-否</html:option>
		  </html:select>    
		</td>
        </tr>
    </table></td>
    <td background="images/table_arrow_07.gif">&nbsp;</td>
  </tr>
  <tr>
    <td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
  </tr>
</table>
<div class="funbutton">
  <button type="submit" style="width:60px" type="submit" onmouseover="this.className='buttom2'" 
  onmouseout="this.className='buttom1'" class="buttom1" >
  <img src="images/save1.gif" width="12" height="12" align="absmiddle"> 保存</button>&nbsp;&nbsp;&nbsp;
  <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" 
  onclick="history.back()"><img src="images/back1.gif" width="11" height="11" align="absmiddle"> 取消</button>
</div>
</html:form>
</body>
</html>