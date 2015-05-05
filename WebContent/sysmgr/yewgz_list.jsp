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
<table width="97%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
    <td><table  border="0" cellpadding="0" cellspacing="0">
        <tr> 
        <td class="w70">
        <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1"
         onClick="location.href='yewgzConfig.do?method=add&yuansid=${yuansid}'"><img src="images/add.gif" width="13" height="13" align="absmiddle"> 
            添加</button>
            </td>
                    <td class="w70">
        <button type="button" style="width:120px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1"
         onClick="location.href='yewgzConfig.do?method=sync'"><img src="images/add.gif" width="13" height="13" align="absmiddle"> 
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
          <div style="text-align:center;"><b>业务规则信息列表</b></div>
          <tr> 
            <td class="class1_thead th">元素ID</td>
	        <td class="class1_thead th">元素名称</td>
	        <td class="class1_thead th">说明</td>
	        <td class="class1_thead th">元素样式</td>
	        <td class="class1_thead th">最大输入字符数</td>
	        <td class="class1_thead th">校验规则</td>
	        <td class="class1_thead th">是否只读</td>
	        <td class="class1_thead th">提示信息id</td>
            <td class="class1_thead th">操作</td>
          </tr>
        </thead>
      	<c:forEach items="${list}" var="yewgz">
        <tr> 
          <td class="class1_td center">${yewgz.yuansid}</td>
          <td class="class2_td">&nbsp;${yewgz.yuansname}</td>
          <td class="class1_td center">${yewgz.remark}</td>
           <td class="class2_td">&nbsp;${yewgz.yuansstyle}</td>
          <td class="class2_td">&nbsp;${yewgz.maxlength}</td>
          <td class="class2_td">&nbsp;${yewgz.validaterule}</td>
          <td class="class1_td center">
		   <c:choose>
			   <c:when test="${yewgz.isreadonly=='readonly'}">    
				   是
			   </c:when>
			   <c:otherwise>  
				   否
			   </c:otherwise>
		   </c:choose>
          </td>           
          <td class="class1_td center">${yewgz.msgid}</td>
          <td class="class1_td center">&nbsp;
		  <a href="yewgzConfig.do?method=modify&yuansid=${yewgz.yuansid}">修改</a>　
		  <a href="javascript:if(confirm('你确定要删除所配置的业务规则信息吗?'))window.location.href='yewgzConfig.do?method=delete&yuansid=${yewgz.yuansid}'">删除</a>　
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