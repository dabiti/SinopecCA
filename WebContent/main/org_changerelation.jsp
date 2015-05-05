<%@ page language="java" contentType="text/html;charset=gbk" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>中石化对账系统</title>
<link href="style/right.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/shield.js"></script>
<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="js/pagejs/jiggbgsx.js" type="text/javascript"></script>
<%@ include file="/common/validator.jsp"%>
		<%@ include file="/common/yewgz.jsp"%>	
</head>
<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;' >
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="38"><img src="images/main_place_pic.gif" width="38" height="32"></td>
    <td background="images/main_place_bg.gif">系统管理 - 机构管理</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
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
<html:form styleId="form1" action="orgManage.do?method=changeOrg" method="post">
<html:hidden  name="code" value="${code}" property="code"/>
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
            <th colspan="2" class="class1_thead th" id="t">修改机构归属关系</th>
          </tr>
        </thead>
	<tr>
          <td class="class1_td w250">上级机构代码：</td>
          <td class="class1_td alignleft"> 
          <input id="orgCode" name="parentcode" value="<bean:write name="orgForm" property="parentcode"/>" class="inputField required netpointflag" maxlength="4">
          </td>
        </tr>
 	<tr>
          <td class="class1_td w250">上级机构名称：</td>
          <td class="class1_td alignleft">&nbsp;<span id="parentname"><bean:write name="orgForm" property="parentname"/></span></td>
        </tr>
		<tr>
          <td class="class1_td w250">机构号：</td>
          <td class="class1_td alignleft"><bean:write name="orgForm" property="code"/>
          <html:hidden property="code"/>
          </td>
        </tr>
 		<tr>
          <td class="class1_td w250">机构名称：</td>
          <td class="class1_td alignleft"><bean:write name="orgForm" property="name"/></td>
        </tr>
    </table></td>
    <td background="images/table_arrow_07.gif">&nbsp;</td>
  </tr>
  <tr>
    <td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
  </tr>
</table>
<div class="funbutton">
  <button type="submit" style="width:60px" onmouseover="this.className='buttom2'" 
  onmouseout="this.className='buttom1'" class="buttom1" ><img src="images/save1.gif" width="12" height="12" 
  align="absmiddle"> 保存</button>&nbsp;&nbsp;&nbsp;
  <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" 
  onclick="history.back()"><img src="images/back1.gif" width="11" height="11" align="absmiddle">返回</button>
</div>
</html:form>
</body>
</html>
