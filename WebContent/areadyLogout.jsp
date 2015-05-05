<%@page language="java" contentType="text/html;charset=gbk"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>中石化对账系统</title>
<link href="<%=request.getContextPath() %>/style/right.css" rel="stylesheet" type="text/css">
<script type="text/javascript" language="javascript">

function logout2(){
			 	var newwin = parent.parent.window;
			 	newwin.location.href="<%=request.getContextPath() %>/login.jsp";
}

</script>
</head>
<body>
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="38"><img src="<%=request.getContextPath() %>/images/main_place_pic.gif" width="38" height="32"></td>
    <td background="<%=request.getContextPath() %>/images/main_place_bg.gif">系统消息</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="97%"  border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" >
  <tr>
    <td class="up-left"/><td class="up-middle"/><td class="up-right"/>
  </tr>
  <tr>
    <td background="<%=request.getContextPath() %>/images/table_arrow_05.gif" >&nbsp;</td>
    <td >
      <table width=60% border="0" align="center" cellpadding="0" cellspacing="1" >
        <thead >
        </thead>
        <tr>
          <td >
            <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="noline">
              <tr>
                <td width="30%" bgcolor="#FFFFFF" class="noline"><div align="right"><img src="<%=request.getContextPath() %>/images/7.png" width="128" height="128" align="absmiddle" /></div></td>
                <td width="3%"  bgcolor="#FFFFFF" class="noline"></td>
                <td width="50%"  bgcolor="#FFFFFF" class="place noline">
					<B>柜员已签退!</font><a class="user_icon02" onclick="logout2();" href="">重新登录!</a></>
                </td>
              </tr>
          </table></td>
        </tr>
      </table></td>
    <td background="<%=request.getContextPath() %>/images/table_arrow_07.gif">&nbsp;</td>
  </tr>
  <tr>
    <td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
  </tr>
</table>
</body>
</html>
