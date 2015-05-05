<%@ page contentType="text/html; charset=GBK" language="java"  errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>中石化对账系统</title>
<link href="style/right.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/shield.js"></script>
</head>
<body >
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="38"><img src="images/main_place_pic.gif" width="50" height="32"></td>
    <td background="images/main_place_bg.gif">工行测试 - 配置DPI</td>
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
<html:form styleId="form1" action="pingzcs.do?method=save" method="post">
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
            <th colspan="2" class="class1_thead th">新增系统凭证信息</th>
          </tr>
        </thead>
        <tbody>
        <tr>
          <td class="class1_td w250">系统标识：</td>
          <td class="class1_td alignleft"><html:text styleId="xitbs" property="xitbs" styleClass="inputField "/></td>
		</tr>
	    <tr>
          <td class="class1_td w250">凭证标识：</td>
          <td class="class1_td alignleft"><html:text styleId="pingzbs" property="pingzbs" styleClass="inputField "/></td>
        </tr>
        <tr>
          <td class="class1_td w250">凭证名称：</td>
          <td class="class1_td alignleft"><html:text styleId="pingzmc" property="pingzmc" styleClass="inputField "/></td>
        </tr>
　　 	<tr>
          <td class="class1_td w250">找章区：</td>
          <td class="class1_td alignleft"><html:text styleId="zhaozq" property="zhaozq" styleClass="inputField "/></td>
        </tr>
        <tr>
          <td class="class1_td w250">阀值区：</td>
          <td class="class1_td alignleft"><html:text styleId="fazq" property="fazq" styleClass="inputField "/></td>
        </tr>
        <tr>
          <td class="class1_td w250">验印级别：</td>
          <td class="class1_td alignleft"><html:text styleId="yanyjb" property="yanyjb" styleClass="inputField "/></td>
        </tr>
        <tr>
          <td class="class1_td w250">X分辨率：</td>
          <td class="class1_td alignleft"><html:text styleId="xfenbl" property="xfenbl" styleClass="inputField "/></td>
        </tr>
        <tr>
          <td class="class1_td w250">Y分辨率：</td>
          <td class="class1_td alignleft"><html:text styleId="yfenbl" property="yfenbl" styleClass="inputField "/></td>
        </tr>
        <tr>
          <td class="class1_td w250">X缩放：</td>
          <td class="class1_td alignleft"><html:text styleId="xsuof" property="xsuof" styleClass="inputField "/></td>
        </tr>
        <tr>
          <td class="class1_td w250">Y缩放：</td>
          <td class="class1_td alignleft"><html:text styleId="ysuof" property="ysuof" styleClass="inputField "/></td>
        </tr>
        </tbody>
    </table>
    </td>
    <td background="images/table_arrow_07.gif">&nbsp;</td>
  </tr>
  <tr>
    <td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
  </tr>
</table>
<div class="funbutton">
  <button type="submit" style="width:60px" type="submit" onmouseover="this.className='buttom2'" 
  onmouseout="this.className='buttom1'" class="buttom1" >
  <img src="images/save1.gif" width="12" height="12" align="middle"> 保存</button>&nbsp;&nbsp;&nbsp;
  <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" 
  onclick="history.back()"><img src="images/back1.gif" width="11" height="11" align="middle"> 取消</button>
</div>
</html:form>
</body>
</html>
