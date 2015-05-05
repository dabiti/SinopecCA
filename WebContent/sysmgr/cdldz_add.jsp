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
<script type="text/javascript" > 
	function select(){
			location.href='doMenu.do?method=select&gongnlx='+document.getElementById('gongnlx').value;		
		}
</script>
</head>
<body >
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="38"><img src="images/main_place_pic.gif" width="50" height="32"></td>
    <td background="images/main_place_bg.gif">配置工具 -${shangjmc}</td>
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

        </td>
        <td class="w70">
	      <select id="gongnlx" name="gongnlx" >
          	  <option>全部</option>
	          <c:forEach items="${group}" var="sys">
	            <option value="${sys}">${sys}</option>
	          </c:forEach>
          </select>
        </td>
        <td class="w70"><button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'"
        	  onClick="select();" class="buttom1" >
        	  <img src="images/add.gif" width="13" height="13" align="absmiddle"> 
          查询</button></td>
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
            <th colspan="7" class="class1_thead th">基础功能菜单列表</th>
          </tr>
          <tr> 
			<td class="class1_thead th">功能ID</td>
			<td class="class1_thead th">子功能ID</td>
			<td class="class1_thead th">功能名称</td>
			<td class="class1_thead th">功能URL</td>
			<td class="class1_thead th">功能顺序</td>
			<td class="class1_thead th">权限位置</td>	
			
			<td class="class1_thead th">操作</td>								
          </tr>
        </thead>
      	<c:forEach items="${list}" var="caid">
        <tr> 
          <td class="class1_td w60" >${caid.gongnid}</td>
          <td class="class1_td center">${caid.zignid}</td>
          <td class="class2_td">&nbsp;${caid.gongnmc}</td>
          <td class="class2_td">&nbsp;${caid.gongnurl}</td>
          <td class="class2_td">${caid.gongnsx}</td>
          <td class="class1_td center">${caid.quanxwz}</td>

          <td class="class1_td center">&nbsp;
		  <a href="javascript:if(confirm('你确定要添加此功能菜单吗?'))window.location.href='doMenu.do?method=doAdd&gongnid=${caid.gongnid}&zignid=${caid.zignid}&shangjgn=${shangjgn}'">添加</a>　
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
<div class="funbutton">
   <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" 
  onclick="history.back()"><img src="images/back1.gif" width="11" height="11" align="absmiddle"> 取消</button>
</div>
</BR>
</body>
</html>
