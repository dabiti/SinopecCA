<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>中石化对账系统</title>
<link href="style/right.css" rel="stylesheet" type="text/css">
<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
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
 function showUrl(){
		var gnid = document.getElementById("gongnid").value;
		document.getElementById("gongnurl").value = "doDanbwh.do?method=list&gongnid="+gnid;
		document.getElementById("gurl").innerHTML ="";
		document.getElementById("gurl").innerHTML = "doDanbwh.do?method=list&gongnid="+gnid;
			
}
</script>
</head>
<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;' >
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="38"><img src="images/main_place_pic.gif" width="38" height="32"></td>
    <td background="images/main_place_bg.gif">单表维护 - 新增单表维护配置</td>
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
<html:form styleId="form1" method="post" action="danbwh.do?method=save">
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
            <th colspan="2" class="class1_thead th">新增单表维护配置</th>
          </tr>
        </thead>
        <tr>
          <td class="class1_td w250">功能ID：</td>
          <td class="class1_td alignleft"><input id="gongnid" name="gongnid" type="text"  class="inputField required" onblur = "showUrl();"/>
       	  *主键，不要与其他单表维护定制出来的功能冲突！
          </td>
        </tr>
	    <tr>
          <td class="class1_td w250">功能名称：</td>
          <td class="class1_td alignleft"><input id="gongnmc"  name="gongnmc" type="text" class="inputField required"/>
          *所要定制的功能名称，如：用户信息查询
          </td> 
        </tr>
        <tr>
          <td class="class1_td w250">功能URL：</td>
          <td class="class1_td alignleft"> 
          	<div id = "gurl"> </div>
          <input id="gongnurl" name="gongnurl" type="hidden"  class="inputField2" />
          </td>
        </tr>
         <tr>
          <td class="class1_td w250">维护表名：</td>
          <td class="class1_td alignleft" ><input id="weihbm"  name="weihbm" type="text" class="inputField" />
          *此功能维护的数据库的表名，如：clerktable
          </td>
        </tr>
        <tr>
          <td class="class1_td w250">是否保存：</td>
          <td class="class1_td alignleft">
          	<html:select styleId="shifbc" property="shifbc" style="width:120px;">
				<html:option value="1">1-是</html:option>
				<html:option value="0">0-否</html:option>
			</html:select>     
			*保存按钮是否显示      
          </td>
        </tr>
        <tr>
          <td class="class1_td w250">是否编辑：</td>
          <td class="class1_td alignleft">
          	<html:select styleId="shifbj" property="shifbj" style="width:120px;">
				<html:option value="1">1-是</html:option>
				<html:option value="0">0-否</html:option>
			</html:select>    
			*是否允许编辑信息       
          </td>
        </tr>
         <tr>
          <td class="class1_td w250">是否删除：</td>
          <td class="class1_td alignleft">
          	<html:select styleId="shifsc" property="shifsc" style="width:120px;">
				<html:option value="1">1-是</html:option>
				<html:option value="0">0-否</html:option>
			</html:select>     
			*是否允许删除信息      
          </td>
        </tr>
        <tr>
          <td class="class1_td w250">是否主表：</td>
          <td class="class1_td alignleft">
          	<html:select styleId="shifzb" property="shifzb" style="width:120px;">
				<html:option value="1">1-是</html:option>
				<html:option value="0">0-否</html:option>
			</html:select>     
			*是否进行管理
          </td>
        </tr>
        <tr>
          <td class="class1_td w250">自定义类</td>
          <td class="class1_td alignleft" ><input id="zhidyl"  name="zhidyl" type="text" class="inputField" />
          *个性化开发自定义类
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
