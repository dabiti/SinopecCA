<%@ page contentType="text/html; charset=GBK" language="java"  errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>中石化对账系统</title>
<link href="style/right.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function exit(){
		window.top.open("","_self");
		window.top.close();
		}
</script>
</head>
<body onload="exit();">
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="38"><img src="images/main_place_pic.gif" width="50" height="32"></td>
    <td background="images/main_place_bg.gif">业务规则定制 - 业务规则信息列表</td>
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
<form action="danbwh.do?method=list" method="post">
<table width="97%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
    <td><table  border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td class="w70">
      
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
            <th colspan="7" class="class1_thead th">业务规则信息列表</th>
          </tr>
          <tr> 
            <td class="class1_thead th">元素ID</td>
	        <td class="class1_thead th">元素名称</td>
	        <td class="class1_thead th">元素样式</td>
	        <td class="class1_thead th">最大输入字符数</td>
	        <td class="class1_thead th">校验规则</td>
	        <td class="class1_thead th">是否只读</td>
	        <td class="class1_thead th">提示信息id</td>
	        <td class="class1_thead th">备注</td>
          </tr>
        </thead>
      	<c:forEach items="${list}" var="yewgz">
        <tr> 
          <td class="class1_td center">${yewgz.yuansid}</td>
          <td class="class2_td">&nbsp;${yewgz.yuansname}</td>
           <td class="class2_td">&nbsp;${yewgz.yuansstyle}</td>
          <td class="class2_td">&nbsp;${yewgz.maxlength}</td>
          <td class="class2_td">&nbsp;${yewgz.validaterule}</td>
          <td class="class1_td center">
		   <c:choose>
		   <c:when test="${yewgz.isreadonly==''}">    
			   是
		   </c:when>
		   <c:otherwise>  
			   否
		   </c:otherwise>
		   </c:choose>
          
          </td>           
          <td class="class1_td center">${yewgz.msgid}</td>
          <td class="class1_td center">${yewgz.remark}</td>
          <td class="class1_td center">&nbsp;
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
<button type="button" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="exit();">
					<img src="images/back1.gif" width="11" height="11"
						align="middle">
					返回
				</button>
</body>
</html>
