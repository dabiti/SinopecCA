<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ page import="com.unitop.config.SystemConfig"%>
<%
	SystemConfig systemConfig = SystemConfig.getInstance();
	request.setAttribute("tesyw_shuangrhq", systemConfig.getValue("tesyw_shuangrhq"));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link href="style/right.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/special-business.js"></script>
<script type="text/javascript" src="js/shield.js"></script>
<%@ include file="/common/yewgz.jsp"%>
<script type="text/javascript"> 
function getAccountInfoForResume() {
	var account = $("#zhangh").val();
	var reg =   /(^[0-9]{9}$)|(^[0-9]{17}$)|(^[0-9]{22}$)|(^[0-9]{10,13}$)/;
	if(!reg.test(account)){
		alert(" �˺Ÿ�ʽ����ȷ!");
		document.getElementById("zhangh").select();
		return false;
	}
	var math = Math.random();
	$.post("accountinfo.do?method=getAccountInfoForResume", {math:math, account:account}, function (data, textStatus) {
		if (textStatus = "success") {
			var accountinfo = data.split(",");
			
			$("#accountname").html('&nbsp'+'');
			$("#allexchange").html('&nbsp'+'');
			$("#startdate1").html('&nbsp'+'');
			$("#accountstate").html('&nbsp'+'');
			$("#hum").html('&nbsp'+' ');
			$("#zhangh").val(accountinfo[8]);
			if(accountinfo[0]==''||accountinfo[0]==null){alert('�˻������ڻ�û�в�ѯ�˻���Ϣ!');return};
			//if(accountinfo[3]==1){alert('�˻�['+account+']Ϊ���˻�����������ҵ��!');return};
			if(accountinfo[6].charAt(0)=='0'){alert('��Ȩ�޲鿴���˻�');return;}
			$("#accountname").html(accountinfo[0]);
			$("#allexchange").html(accountinfo[1]);
			$("#startdate1").html(accountinfo[4]);
			$("#yzhanghzt").val(accountinfo[7]);
			$("#accountstate").html(accountinfo[2]);
			$("#today").val(accountinfo[5]);
			
		}
	}, "text");
}
 $(document).ready(function() {
 $("#zhangh").focus();
   $("#form1").validate({
   errorLabelContainer:"#error div.error",
   wrapper:"li",
   submitHandler:function(form){
	   var accountname = $("#accountname").html();
	   var accountstate = $("#accountstate").html();
	   if(accountname=='&nbsp;'||accountname=='&nbsp; '){return};
	   if(accountstate.indexOf('����')==-1){alert('�˻�״̬Ϊ��'+accountstate+',�޷������ָ�');return};
	   var yzhanghzt=$("#yzhanghzt").val();
	   if(yzhanghzt=="��������"){alert("���˻����������棬�޷����������ָ�");return;}
		var today=$("#today").val();
		var xiaohdate=$("#startdate1").html();
		if(today!=xiaohdate){alert('ֻ�е����������˻����ܽ��������ָ�');return;}	
		//   if (confirm("ȷ��Ҫ�ָ��˻���"))
	   //{
   <c:if  test='${tesyw_shuangrhq==1}'>
		   var obj = new Object(); 
		   obj.titleName = "�����ָ�";obj.checktype="��Ȩ";obj.quanxbs="XHHF_001";
		   obj.account = $("#zhangh").val();
		   var backNum = window.showModalDialog('authorized.jsp',obj,"dialogWidth=10px;dialogHeight=10px;status=no;scroll=no"); 
		   if(backNum=='1')
		   {
			   form.submit();
		   }
   </c:if>
   <c:if test='${tesyw_shuangrhq!=1}'>
		   form.submit();
   </c:if>
	  // }
   }
 }) 
 });
</script>
</head>
<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="38"><img src="images/main_place_pic.gif" width="38" height="32"></td>
    <td background="images/main_place_bg.gif">����ҵ�� - �����ָ�</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<br> 
<form id=error name=error>
  <table width="95%"  border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td class="orange error"><div  class="error orange"></div></td>
    </tr>
    <tr>
      <td class="orange"><html:errors /></td>
    </tr>
  </table>
</form>
<form id="form1" name="form1"  method="post" action="accountinfo.do?method=recoverAccountForNet">    
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
            <th colspan="2" class="class1_thead th">�����ָ�</th>
          </tr>
        </thead>
		<tr>
          <td width="42%" class="class1_td alignright">�˺�<input type="hidden" id="today"></td>          
          <td width="58%" class="class1_td alignleft">
          	<input id="zhangh" name="account" type="text" onkeydown='if(event.keyCode==13){getAccountInfoForResume(this)}' maxlength="25"  class="inputField required account" style="width:140px;"  />	
          <button type="button" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1" onclick="getAccountInfoForResume(this)" >
					��ѯ
			</button> 
			
          </td>
          
        </tr>
        <tr>  
          <td class="class1_td alignright">����</td>
          <td id="accountname" class="class1_td alignleft">&nbsp;</td>
        </tr>
        <tr>
          <td class="class1_td alignright">ͨ��ͨ��</td>
          <td id="allexchange" class="class1_td alignleft">&nbsp;</td>
        </tr>
        <tr>
          <td class="class1_td alignright">�˻�״̬</td>
          <td id="accountstate" class="class1_td alignleft">&nbsp;</td>
        </tr>
         <tr>
          <td class="class1_td alignright">����ʱ��<input type="hidden" id="yzhanghzt"/></td>
          <td id="startdate1" class="class1_td alignleft">&nbsp;</td>
          
        </tr>
    </table>
    </td>
    <td background="images/table_arrow_07.gif">&nbsp;</td>
  </tr>
  <tr>
    <td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
  </tr>
</table>
<div class="funbutton">
 <button type="submit" style="width:70px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom2">�����ָ�</button>&nbsp;<button type="reset" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onclick="resetInfo(this)"><img src="images/back1.gif" width="11" height="11" align="absmiddle">ȡ��</button>
</div>
</form>
</body>
</html>