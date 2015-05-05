<%@ page contentType="text/html;charset=GBK" language="java"  errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/common/title.jsp"%>
<link href="style/right.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/shield.js"></script>
</head>
<body >
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="38"><img src="images/main_place_pic.gif" width="38" height="32"></td>
    <td background="images/main_place_bg.gif">����������˻��б�</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><table  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td class="w70">
            </td>
        </tr>
      </table></td>
  </tr>
</table>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
				<logic:messagesPresent>
					<tr>
						<td class="orange">
							<div id=abc class="abc"><html:errors /></div>
						</td>
					</tr>
				</logic:messagesPresent>
</table>
<table width="97%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td class="up-left"/><td class="up-middle"/><td class="up-right"/>
  </tr>
  <tr>
    <td background="images/table_arrow_05.gif">&nbsp;</td>
    <td>
<table width="100%"  border="0" style="table-layout:fixed;word-wrap:break-word;" cellspacing="1" cellpadding="0" class="class1_table">
       				 <thead style="background:white">
           			<div style="text-align:center;"><b>�������˻��б�</b></div>
          <tr> 
            <td class="class1_thead th w60">���</td>
            <td class="class1_thead th">�˺�</td>
	   		<td class="class1_thead th">�����Ա</td>
	   		<td class="class1_thead th">����</td>
	        <td class="class1_thead th">��������</td>
	        <td class="class1_thead th">ͨ�ұ�־</td>
	        <td class="class1_thead th">�˻�״̬</td>
	        <td class="class1_thead th">ӡ����־</td>
	        <td class="class1_thead th">ӡ�����״̬</td>
	        <td class="class1_thead th">����</td>
          </tr>
        </thead>
        <%int i=0; %>
      	<c:forEach items="${zhanghbList}" var="zhanghb">
	        <tr> 
	          <td class="class1_td w60" ><%=++i %></td>
	          <td class="class1_td center">&nbsp;<a href="accountinfo.do?method=scanAccountinfo&zhangh=${zhanghb.zhangh}">${zhanghb.zhangh}</a></td>
		  	  <td class="class1_td center">&nbsp;
		  	   	<c:if test="${zhanghb.youwyj eq '��'}">
		  		  ${zhanghb.kaihgy}
		  	  	</c:if>
		  	  	<c:if test="${zhanghb.youwyj eq '��'}">
		  	  	  ${zhanghb.jiankgy }
		  	  	</c:if>
		  	  </td>
		  	  <td class="class1_td center">&nbsp;${zhanghb.hum}</td>
		  	  <td class="class1_td center">&nbsp;${zhanghb.kaihrq}</td>
		  	  <td class="class1_td center">&nbsp;${zhanghb.tongctd}</td>
		  	  <td class="class1_td center">&nbsp;${zhanghb.zhanghzt}</td>
		  	  <td class="class1_td center">&nbsp;${zhanghb.youwyj}</td>
		  	  <td class="class1_td center">&nbsp;${zhanghb.yinjshzt}</td>
		  	  <td class="class1_td center">&nbsp;
		  	  <c:if test="${zhanghb.youwyj eq '��'}">
				<unitop:HasPrivilegeForZignTag name="41200">
		  	  		<a href="yinjManage.do?method=foryinjgl&zhangh=${zhanghb.zhangh }">����</a>
		  	  	</unitop:HasPrivilegeForZignTag>
		  	  	</c:if>
		  	  <c:if test="${zhanghb.youwyj eq '��'}">
		  	 	<unitop:HasPrivilegeForZignTag name="41300">
		  	 	 <a href="yinjManage.do?method=foryinjsh&zhangh=${zhanghb.zhangh }">���</a>
		  	   	</unitop:HasPrivilegeForZignTag>
		  	   	</c:if>
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
