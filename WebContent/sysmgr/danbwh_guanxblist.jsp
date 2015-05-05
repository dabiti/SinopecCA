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
    <td background="images/main_place_bg.gif">单表维护 - 表单关系设置</td>
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
<form action="/danbwhGuanxb.do?method=add" method="post">
<table width="97%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
    <td><table  border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td class="w70">
        <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onClick="location.href='/danbwhGuanxb.do?method=add&gongnid=${gongnid}'"><img src="images/add.gif" width="13" height="13" align="absmiddle">添加</button>
            </td>
        <td class="w70">
		   <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" 
		  onclick="history.back()"><img src="images/back1.gif" width="11" height="11" align="absmiddle"> 取消</button>	        
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
	<table width="100%"  border="0" cellspacing="1" cellpadding="0" class="class1_table">
        <thead class="class1_thead">
          <tr> 
            <th colspan="7" class="class1_thead th">单表关系维护功能列表</th>
          </tr>
          <tr> 
            <td class="class1_thead th">主表ID</td>
	        <td class="class1_thead th">子表ID</td>
	        <td class="class1_thead th">主表字段</td>
	        <td class="class1_thead th">子表字段</td>
	        <td class="class1_thead th">子表维护名称</td>
            <td class="class1_thead th">操作</td>
          </tr>
        </thead>
      	<c:forEach items="${list}" var="danb">
        <tr> 
          <td class="class1_td center">${danb.zhubbh}</td>
          <td class="class2_td">&nbsp;${danb.zibbh}</td>
          <td class="class2_td">&nbsp;${danb.zhubzd}</td>
          <td class="class2_td">&nbsp;${danb.zibzd}</td>
          <td class="class2_td">&nbsp;${danb.zhibwhmc}</td>
          <td class="class1_td center">&nbsp;
		  <a href="danbwhGuanxb.do?method=find&id=${danb.id}">修改</a>　
		  <a href="javascript:if(confirm('你确定要删除吗?'))window.location.href='danbwhGuanxb.do?method=delete&id=${danb.id}'">删除</a>　
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
</form>
</BR>
</body>
</html>