<%@ page contentType="text/html; charset=GBK" language="java"  errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>��ʯ������ϵͳ</title>
<link href="style/right.css" rel="stylesheet" type="text/css">
</head>
<body >
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="38"><img src="images/main_place_pic.gif" width="50" height="32"></td>
    <td background="images/main_place_bg.gif">����ά�� - ��Ҫ������</td>
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
        <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onClick="location.href='danbwhbd.do?method=add&gongnid=${gongnid}'"><img src="images/add.gif" width="13" height="13" align="absmiddle"> 
            ���</button>
            </td>
        <td class="w70">
		   <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" 
		  onclick="history.back()"><img src="images/back1.gif" width="11" height="11" align="absmiddle"> ȡ��</button>	        
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
			<div style="text-align:center;"><b>����ά�������б�</b></div>
          <tr> 
            <td class="class1_thead th">����ID</td>
	        <td class="class1_thead th">�ֶ�����</td>
	        <td class="class1_thead th">չʾ����</td>
	        <td class="class1_thead th">�ֶ�����</td>
	        <td class="class1_thead th">��������</td>
	        <td class="class1_thead th">Ĭ��ֵ</td>
	        <td class="class1_thead th">�Ƿ��ڱ����޸�ҳ��չʾ</td>
            <td class="class1_thead th">�Ƿ��ڲ�ѯҳ��չʾ</td>
            <td class="class1_thead th">�Ƿ�����</td>
             <td class="class1_thead th">��ʾ˳��</td>
            <td class="class1_thead th">����</td>
          </tr>
        </thead>
      	<c:forEach items="${list}" var="danb">
        <tr> 
          <td class="class1_td center">${danb.id.gongnid}</td>
          <td class="class2_td">&nbsp;${danb.id.zidmc}</td>
           <td class="class2_td">&nbsp;${danb.zhansmc}</td>
          <td class="class2_td">&nbsp;${danb.zidlx}</td>
          <td class="class2_td">${danb.shurlx}</td>
          <td class="class1_td center">${danb.morz}</td>
          <td class="class1_td center">${danb.shifbc}</td>
          <td class="class1_td center">${danb.shifbj}</td>
          <td class="class1_td center">${danb.shifzj}</td>
           <td class="class1_td center">${danb.xianssx}</td>
          <td class="class1_td center">
		  <a href="danbwhbd.do?method=find&gongnid=${danb.id.gongnid}&zidmc=${danb.id.zidmc}&gongnid=${gongnid}">�޸�</a>��
		  <a href="javascript:if(confirm('��ȷ��Ҫɾ�������õı�Ҫ��������?'))window.location.href='danbwhbd.do?method=delete&gongnid=${danb.id.gongnid}&zidmc=${danb.id.zidmc}'">ɾ��</a>��
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