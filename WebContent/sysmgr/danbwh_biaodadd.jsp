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

$('#shurlx').unbind().bind('click', function() {
	  if($('#shurlx').val()=='静态多选框')
	  {
		  var delInput = "<a href='javascript:deleteInput("+i+")'>删除</a>";
		  $('#shurlxList').html("<div id=shurlxList"+i+">选项:<input name ='shurlxName'  class='inputField'/> 数据库保存:<input name ='shurlxValue'  class='inputField'/>"+delInput+"</div>");
		  $('#addInput').html("<input type='button'  onclick='addInput()' value='添加'/>")
	  }else{
		  $('#shurlxList').html(null);
		  $('#addInput').html(null);
	  }
	});
})
var i=0;
function addInput()
{
	    var html=$('#shurlxList').html();
	    i++;
	    var delInput = "<a href='javascript:deleteInput("+i+")'>删除</a>";
		$('#shurlxList').html(html+"<div id=shurlxList"+i+">选项:<input name ='shurlxName'  class='inputField'/> 数据库保存:<input name ='shurlxValue'  class='inputField'/>"+delInput+"</div>");
}
function deleteInput(i){
	$('#shurlxList'+i).html(null);
}
function doAddYewgz()
{
	var zdmc = document.getElementById('zdmc').value;
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
<html:form styleId="form1"  method="post" action="danbwhbd.do?method=save">
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
          <td class="class1_td alignleft">
     		<bean:write name="DanbwhBiaodForm" property="gongnid" />
       		<html:hidden property="gongnid"/></td>	
          </td>
        </tr>
	    <tr>
          <td class="class1_td w250">字段名称：</td>
          <td class="class1_td alignleft"><input id="zdmc"  name="zidmc" type="text"  class="inputField" onfocus="javaScript:autoCompleteForTableLine('zdmc',{tableName:this.form.SHUJHQFS.value});"/>
          (数据库字段名称)
          <input id="SHUJHQFS" type="hidden" value="${danbwh.weihbm}"/>
          </td>
        </tr>
        <tr>
          <td class="class1_td w250">展示名称：</td>
          <td class="class1_td alignleft"><input id="zhansmc" name="zhansmc" type="text"  class="inputField" class="required" />
           (功能字段展示名称)</td>
        </tr>        
        <tr>
          <td class="class1_td w250">字段类型：</td>
          <td class="class1_td alignleft">
          <html:select styleId="zidlx" property="zidlx">
				<html:option value="string">字符类型[string]</html:option>
				<html:option value="int">整型[int]</html:option>
				<html:option value="long">整型[long]</html:option>
				<html:option value="float">浮点型[float]</html:option>
				<html:option value="double">浮点型[double]</html:option>
		 </html:select>
		 </td>
        </tr>
         <tr>
          <td class="class1_td w250">输入类型：</td>
          <td class="class1_td alignleft">
          	<html:select styleId="shurlx" property="shurlx">
				<html:option value="文本框">文本框 </html:option>	
				<html:option value="文本域">文本域 </html:option>	
				<html:option value="静态多选框">静态多选框 </html:option>
				<html:option value="动态多选框">动态多选框 </html:option>		
				<html:option value="时间组件">时间组件</html:option>	
			</html:select>
			<span id ='addInput'></span> 
			<span id ='delInput'></span>          
			<div id ='shurlxList'></div>        
          </td>
        </tr>
         <tr>
         <!--  
          <td class="class1_td w250">取值范围：</td>
          <td class="class1_td alignleft"><input id="quzfw"  name="quzfw" type="text"  class="inputField" /></td>
          -->
          <input id="quzfw"  name="quzfw" type="hidden"  class="inputField" />
        </tr>
         <tr>
          <td class="class1_td w250">默认值：</td>
          <td class="class1_td alignleft"><input id="morz"  name="morz" type="text"  class="inputField" /></td>
        </tr>
         <!--  
         <tr>
          <td class="class1_td w250">校验规则：</td>
          <td class="class1_td alignleft"><input id="jiaoygz"  name="jiaoygz" type="text"  class="inputField" /></td>
        </tr>  
         -->
          <input id="jiaoygz"  name="jiaoygz" type="hidden"  class="inputField" />          
        <tr>
          <td class="class1_td w250">是否在保存修改页面展示：</td>
          <td class="class1_td alignleft">
          	<html:select styleId="shifbc" property="shifbc" style="width:120px;" >
          		<html:option value="1">1-是</html:option>
				<html:option value="0">0-否</html:option>	
			</html:select>           
          </td>
        </tr>
        <tr>
          <td class="class1_td w250">是否在查询页面展示：</td>
          <td class="class1_td alignleft">
          	<html:select styleId="shifbj" property="shifbj" style="width:120px;" >
				<html:option value="1">1-是</html:option>
				<html:option value="0">0-否</html:option>
			</html:select>           
          </td>
        </tr>
         <tr>
          <td class="class1_td w250">是否主键：</td>
          <td class="class1_td alignleft">
          	<html:select styleId="shifzj" property="shifzj" style="width:120px;" >
				<html:option value="0">0-否</html:option>
				<html:option value="1">1-是</html:option> 
			</html:select>           
          </td>
          <td class="class1_td alignleft">
				<html:button property="button" onclick="doAddYewgz();" value="添加业务规则信息"></html:button> 
		</td>
        </tr> 
          <tr>
          <td class="class1_td w250">显示顺序：</td>
          <td class="class1_td alignleft"><html:text styleId="xianssx"  property="xianssx"   styleClass="inputField number" maxlength="2" /></td>
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
</html:form >
</body>
</html>