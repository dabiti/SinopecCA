<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>��ʯ������ϵͳ</title>
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
    <td background="images/main_place_bg.gif">����ά�� - ��������ά������</td>
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
            <th colspan="2" class="class1_thead th">��������ά������</th>
          </tr>
        </thead>
        <tr>
          <td class="class1_td w250">����ID��</td>
          <td class="class1_td alignleft"><input id="gongnid" name="gongnid" type="text"  class="inputField required" onblur = "showUrl();"/>
       	  *��������Ҫ����������ά�����Ƴ����Ĺ��ܳ�ͻ��
          </td>
        </tr>
	    <tr>
          <td class="class1_td w250">�������ƣ�</td>
          <td class="class1_td alignleft"><input id="gongnmc"  name="gongnmc" type="text" class="inputField required"/>
          *��Ҫ���ƵĹ������ƣ��磺�û���Ϣ��ѯ
          </td> 
        </tr>
        <tr>
          <td class="class1_td w250">����URL��</td>
          <td class="class1_td alignleft"> 
          	<div id = "gurl"> </div>
          <input id="gongnurl" name="gongnurl" type="hidden"  class="inputField2" />
          </td>
        </tr>
         <tr>
          <td class="class1_td w250">ά��������</td>
          <td class="class1_td alignleft" ><input id="weihbm"  name="weihbm" type="text" class="inputField" />
          *�˹���ά�������ݿ�ı������磺clerktable
          </td>
        </tr>
        <tr>
          <td class="class1_td w250">�Ƿ񱣴棺</td>
          <td class="class1_td alignleft">
          	<html:select styleId="shifbc" property="shifbc" style="width:120px;">
				<html:option value="1">1-��</html:option>
				<html:option value="0">0-��</html:option>
			</html:select>     
			*���水ť�Ƿ���ʾ      
          </td>
        </tr>
        <tr>
          <td class="class1_td w250">�Ƿ�༭��</td>
          <td class="class1_td alignleft">
          	<html:select styleId="shifbj" property="shifbj" style="width:120px;">
				<html:option value="1">1-��</html:option>
				<html:option value="0">0-��</html:option>
			</html:select>    
			*�Ƿ�����༭��Ϣ       
          </td>
        </tr>
         <tr>
          <td class="class1_td w250">�Ƿ�ɾ����</td>
          <td class="class1_td alignleft">
          	<html:select styleId="shifsc" property="shifsc" style="width:120px;">
				<html:option value="1">1-��</html:option>
				<html:option value="0">0-��</html:option>
			</html:select>     
			*�Ƿ�����ɾ����Ϣ      
          </td>
        </tr>
        <tr>
          <td class="class1_td w250">�Ƿ�����</td>
          <td class="class1_td alignleft">
          	<html:select styleId="shifzb" property="shifzb" style="width:120px;">
				<html:option value="1">1-��</html:option>
				<html:option value="0">0-��</html:option>
			</html:select>     
			*�Ƿ���й���
          </td>
        </tr>
        <tr>
          <td class="class1_td w250">�Զ�����</td>
          <td class="class1_td alignleft" ><input id="zhidyl"  name="zhidyl" type="text" class="inputField" />
          *���Ի������Զ�����
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
  <img src="images/save1.gif" width="12" height="12" align="absmiddle"> ����</button>&nbsp;&nbsp;&nbsp;
  <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" 
  onclick="history.back()"><img src="images/back1.gif" width="11" height="11" align="absmiddle"> ȡ��</button>
</div>
</html:form>
</body>
</html>
