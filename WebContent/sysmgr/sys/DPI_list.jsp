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
    <td background="images/main_place_bg.gif">配置DPI</td>
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
<form action="peizDPI.do?method=select" method="post">
<br>
<table width="97%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
    <td><table  border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td class="w70">
        <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onClick="location.href='peizDPI.do?method=add'"><img src="images/add.gif" width="13" height="13" align="middle"> 
            添加</button>
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
            <th colspan="7" class="class1_thead th">DPI配置列表</th>
          </tr>    
          <tr>
            <td class="class1_thead th w60">编号</td>
            <td class="class1_thead th">地区号</td>
	        <td class="class1_thead th">网点号</td>
	        <td class="class1_thead th">业务类型</td>
	        <td class="class1_thead th">色彩标识</td>
	        <td class="class1_thead th">DPI</td>
	        <td class="class1_thead th">调优参数</td>
            <td class="class1_thead th">操作</td>
          </tr>
        </thead>
      	<c:forEach items="${listDPI}" var="dpis">
        <tr> 
          <td class="class1_td w60" >${dpis.dpiid}</td>
          <td class="class1_td w80">${dpis.diqh}</td>
          <td class="class2_td w80">&nbsp;${dpis.wangdh}</td>
          <td class="class2_td w80">&nbsp;${dpis.yewlx} </td>
          <td class="class2_td w80">${dpis.secbz}</td>
          <td class="class2_td w80">${dpis.dpi}</td>
          <td class="class2_td w1000">${dpis.tiaoycs}</td>
          <td class="class1_td w100">&nbsp;
		  <a href="peizDPI.do?method=find&dpiid=${dpis.dpiid }">修改</a>　
		  <a href="javascript:if(confirm('你确定要删除所选择的DPI设置吗?'))window.location.href='peizDPI.do?method=delete&dpiid=${dpis.dpiid }'">删除</a>　
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
</body>
</html>
