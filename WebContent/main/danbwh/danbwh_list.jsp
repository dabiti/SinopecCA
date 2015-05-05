<%@ page contentType="text/html; charset=GBK" language="java"  errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/WEB-INF/tld/danbwh.tld" prefix="danbwh"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>中石化对账系统</title>
<link href="style/right.css" rel="stylesheet" type="text/css">
<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script LANGUAGE="JavaScript">
	function zhucongb(xuh,zbid)
	{
		var danbwForm =document.getElementById("danbwForm");
		danbwForm.setAttribute("method","post");
		danbwForm.setAttribute("action","doDanbwh.do?method=listForZhucb&zhub_gongnid="+'${table.id}'+'&zib_gongnid='+zbid);
		danbwForm.innerHTML = "";

		var zid = '<c:forEach items="${table.fieldForShow}" var="field">${field.key},</c:forEach>';
		var zids = zid.split('\,');
		for(var i=0;i<zids.length-1;i++)
		{
			var input =document.createElement("input");
			input.setAttribute("type","hidden");
			input.setAttribute("name","key_"+zids[i]);
			input.setAttribute("value",$("#"+zids[i]+xuh).html());
			danbwForm.appendChild(input);
		}
		danbwForm.submit();
	}
	//字典控制
	function setDictionaryDatad(id,type){
		//字典信息
		var dictionaryDatad = ${dictionaryDatad};
		var maxlength = dictionaryDatad[0][id.value].columnSize;
		if(type==1)
		$('#jiansxx').val('');
		$('#jiansxx').attr('maxlength',maxlength);
    }
</script>
</head>
<body onload="setDictionaryDatad(document.getElementById('jianslx',0));">
<table width="100%" cellpadding="0" cellspacing="0">
  <tr>
    <td width="38"><img src="images/main_place_pic.gif"></td>
    <td background="images/main_place_bg.gif">${table.name}</td>
  </tr>
</table>
	<form id='danbwForm' name='danbwForm'></form>
	<div id=error style="margin-left:3%;margin-right:50%;"  class="orange"><logic:messagesPresent><html:errors/></logic:messagesPresent></div>
<table width="97%"  border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td>&nbsp;</td>
    <td><table border="0" cellpadding="0" cellspacing="0" >
        <tr> 
        <c:if test='${table.save}'>
	        <form action="doDanbwh.do?method=forwardAdd&gongn_id=${table.id}" method="post">
	        <danbwh:tableForZhucb />
        </c:if>
        <td class="w70">
		    	<button type="submit" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1"><img src="images/add.gif" width="13" height="13" align="middle">添加</button>
	    </td>
	        </form>
	        <td class="w70"></td>
	        <td class="w70"></td>
	        <td class="w70"></td>
	        <td class="w70"></td>
	        <td class="w70"></td>
	        <td class="w70"></td>
	        <td class="w70"></td>
	        <td class="w70"></td>
	        <td class="w70"></td>
	        <td class="w70"></td>
	        <td class="w70"></td>
	        <td class="w70"></td>
	        <td class="w70"></td>
	        <form action="doDanbwh.do?method=listForSelect&gongnid=${table.id}" method="post">
	        <danbwh:tableForZhucb />
	        <td class="w70">
		    	<input id="jiansxx" name="jiansxx" type='text' class="inputField" value="${jiansxx}" />
	        </td>
	        <td class="w70">
		    		<select name="jianslx" id="jianslx"  onchange='setDictionaryDatad(this,1);'>
		    		<c:forEach items="${table.fieldForShow}" var="field">
          	 			<option value="${field.key}">${field.value}</option>
         		 	</c:forEach>
		    		</select>
		    		<script>var jianslxValue='${jianslx}'; if(jianslxValue!='')document.getElementById("jianslx").value=jianslxValue;</script>
	        </td>
	        <td class="w70">
	         <button type="submit" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1"><img src="images/search1.gif" width="13" height="13" align="middle">检索</button>
	    	</td>
	    	</form>
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
	<table width=100% style="table-layout:fixed;word-wrap:break-word;" border="0" cellspacing="1" cellpadding="0" class="class1_table">	
        <thead style="background:white">
           <div style="text-align:center;"><b>${table.name}列表</b></div>
          <tr>
            <td class="class1_thead">序号</td>
          <c:forEach items="${table.fieldForShow}" var="field">
          	  <td class="class1_thead">${field.value}</td>
          </c:forEach>
          	<td class="class1_thead">操作 </td>
          </tr>
        </thead>
        <%int i=0; %>
      	<c:forEach items="${list}" var="list">
	        <tr> 
	          <td class="class1_td" ><%=++i %></td>
	          <c:forEach items="${table.fieldForShow}" var="field">
					<td class="class1_td center"><a id='${field.key}<%=i%>' style="text-decoration:none">${list[field.key]}</a>&nbsp;</td>
	          </c:forEach>
	          <td class="class1_td center"  >
	          <c:if test='${table.update}'>
	          	 <button type="button" onclick="document.getElementById('formUpdate<%=i %>').submit();" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1">修改</button>
	          </c:if>
	          <c:if test='${table.delete}'>
	         	 <button type="button" onclick="if(confirm('确定删除记录?'))document.getElementById('formDelete<%=i %>').submit();"  onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1">删除</button>
			  </c:if>
			  <danbwh:tableTagForzhiblianj name="<%=i %>" />
			  </td>
	        </tr>
	        <form id="formUpdate<%=i %>" action="doDanbwh.do?method=forwardUpdate&gongn_id=${table.id}" method="post">
			 	<danbwh:tableForZhucb />
				 <c:forEach items="${table.tableKeys}" var="keys">
				 	<input name='${keys.key}' type='hidden' value='${list[keys.key]}' />
				 </c:forEach>
			</form>
			<form id="formDelete<%=i %>" action="doDanbwh.do?method=delete&gongn_id=${table.id}" method="post" onsubmit="return onsubmitForForm()">
				<danbwh:tableForZhucb />
				  <c:forEach items="${table.tableKeys}" var="keys">
				    <input name='${keys.key}' type='hidden' value='${list[keys.key]}' />
				  </c:forEach>
			</form>
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