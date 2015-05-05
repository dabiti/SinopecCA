<%@ page contentType="text/html; charset=GBK" language="java"  errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>中石化对账系统</title>
<link href="style/right.css" rel="stylesheet" type="text/css">
</head>
<body >
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="38"><img src="images/main_place_pic.gif" width="50" height="32"></td>
    <td background="images/main_place_bg.gif">界面提示定制 - 界面提示信息列表</td>
  </tr>
</table>
    <form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange error">
						<div class="error orange">${status}</div>
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
<table width="97%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
    <td><table  border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td class="w70">
        <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1"
         onClick="location.href='tisxxConfig.do?method=add&msgId=${msgId}'"><img src="images/add.gif" width="13" height="13" align="absmiddle"> 
            添加</button>
            </td>
        <td class="w70">
        <button type="button" style="width:120px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1"
         onClick="location.href='tisxxConfig.do?method=sync&msgId=${msgId}'"><img src="images/add.gif" width="13" height="13" align="absmiddle"> 
     同步数据</button>
            </td>
        </tr>
      </table>

      </td>
  </tr>
  <tr>
    <td class="up-left"/><td class="up-middle"/><td class="up-right"/>
  </tr>
  <tr>
    <td background="images/table_arrow_05.gif">&nbsp;</td>
    <td>
	   <table width="100%"  border="0" style="table-layout:fixed;word-wrap:break-word;" cellspacing="1" cellpadding="0" class="class1_table">
		<thead style="background:white">
          <div style="text-align:center;"><b>界面提示信息列表</b></div>
          <tr> 
            <td class="class1_thead th">提示信息ID</td>
	        <td class="class1_thead th">提示信息内容</td>
	        <td class="class1_thead th">功能模块名称</td>
	        <td class="class1_thead th">功能名称</td>
	        <td class="class1_thead th">备注</td>
            <td class="class1_thead th">操作</td>
          </tr>
        </thead>
      	<c:forEach items="${list}" var="tisxx">
        <tr> 
          <td class="class1_td center">${tisxx.msgId}</td>
          <td class="class2_td">&nbsp;${tisxx.msgContent}</td>
      <td class="class2_td">&nbsp;${tisxx.functionArea}</td>
      <td class="class2_td">&nbsp;${tisxx.functionPoint}</td>
      <td class="class2_td">&nbsp;${tisxx.remark}</td>
          <td class="class1_td center">&nbsp;
		  <a href="tisxxConfig.do?method=modify&msgId=${tisxx.msgId}">修改</a>　
		  <a href="javascript:if(confirm('你确定要删除所配置的业务规则信息吗?'))window.location.href='tisxxConfig.do?method=delete&msgId=${tisxx.msgId}'">删除</a>　
		  </td>
        </tr>
        </c:forEach>
      </table></td>
    <td background="images/table_arrow_07.gif">&nbsp;</td>
  </tr>
  <tr>
    <td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
  </tr>
</table>

</body>
</html>
