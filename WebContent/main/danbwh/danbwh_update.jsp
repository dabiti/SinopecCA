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
<script language="javascript" type="text/javascript" src="js/date/time.js"></script>
<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="js/pagejs/danbwh.js" type="text/javascript"></script>
<%@ include file="/common/validator.jsp"%>
<script type="text/javascript">
	function setDictionaryDatad(name){
		//字典信息
		var dictionaryDatad = ${dictionaryDatad};
		name = name.substring(0,name.length-5);
		var maxlength = dictionaryDatad[0][name].columnSize;
		$('#'+name+"Value").attr('maxlength',maxlength);
    }
    //初始化FORM
    function initForm(){
		for(var i=0;i<form1.elements.length;i++)
		{
		   if("textarea"==form1.elements[i].type)
		   {
				this.setDictionaryDatad(form1.elements[i].name);
		   }
		   if("text"==form1.elements[i].type)
		   {
				this.setDictionaryDatad(form1.elements[i].name);
		   }
		}
    }
</script>
</head>
<body onload=initForm();>
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="38"><img src="images/main_place_pic.gif" width="50" height="32"></td>
    <td background="images/main_place_bg.gif">${table.name}</td>
</tr>
</table>
	<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
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
<form name="from1" id="form1" action="doDanbwh.do?method=update&gongn_id=${table.id}" method="post">
<table width="97%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td class="up-left"/><td class="up-middle"/><td class="up-right"/>
  </tr>
  <tr>
    <td background="images/table_arrow_05.gif">&nbsp;</td>
    <td>
      <table width="100%"  border="0" cellspacing="1" cellpadding="0" class="class1_table">
        <thead class="class1_thead">
          <tr>
            <th colspan="2" class="class1_thead th">修改-[${table.name}]</th>
          </tr>
        </thead>
        <tbody>
        <c:forEach items="${table.fieldForSaveOrUpdate}" var="fieldType">
	        <tr>
	          <td class="class1_td w250">${table.field[fieldType.key]}</td>
	          <c:if test="${fieldType.value=='文本框'}">
		         <td class="class1_td alignleft">
		         <c:choose> 
			         <c:when test="${table.tableKeys[fieldType.key]==null}">
			           <input id="${fieldType.key}Value" name="${fieldType.key}Value" type='text' class="inputField ${table.validateMap[fieldType.key]}" value="${map[fieldType.key]}" onFocus="changInput_size(this)" onselect="changInput_size(this)" onkeyup="changInput_size(this)"/>
			         </c:when>
			         <c:otherwise>
			         	${map[fieldType.key]}
			         	<input id="${fieldType.key}Value" name="${fieldType.key}Value" type='hidden' class="inputField ${table.validateMap[fieldType.key]}" value="${map[fieldType.key]}" onFocus="changInput_size(this)" onselect="changInput_size(this)" onkeyup="changInput_size(this)"/>
			         </c:otherwise>
		         </c:choose>  	
		         </td>
	          </c:if>
	          <c:if test="${fieldType.value=='文本域'}">
		         <td class="class1_td alignleft">
		         <c:choose> 
			         <c:when test="${table.tableKeys[fieldType.key]==null}">
			           <textarea rows="5" cols="50" id="${fieldType.key}Value" name="${fieldType.key}Value" type='text' class="${table.validateMap[fieldType.key]}" >${map[fieldType.key]}</textarea>
			         </c:when>
			         <c:otherwise>
			         	${map[fieldType.key]}
			         	<input id="${fieldType.key}Value" name="${fieldType.key}Value" type='hidden' class="inputField ${table.validateMap[fieldType.key]}" value="${map[fieldType.key]}" onFocus="changInput_size(this)" onselect="changInput_size(this)" onkeyup="changInput_size(this)"/>
			         </c:otherwise>
		         </c:choose>  	
		         </td>
	          </c:if>
	           <c:if test="${fieldType.value=='时间组件'}">
	           <td class="class1_td alignleft"><input id="${fieldType.key}Value" name="${fieldType.key}Value" type='text' class="inputField ${table.validateMap[fieldType.key]}" value="${map[fieldType.key]}" onclick="WdatePicker()"/></td>
	          </c:if>
	          <c:if test="${fieldType.value=='多选框'}">
	           <td class="class1_td alignleft"><input id="${fieldType.key}Value" name="${fieldType.key}Value" type='checkbox' class="${table.validateMap[fieldType.key]}" value="${map[fieldType.key]}" /></td>
	          </c:if>
	        </tr>
	     </c:forEach>
        </tbody>
    </table>
    </td>
    <td background="images/table_arrow_07.gif">&nbsp;</td>
  </tr>
  <tr>
    <td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
  </tr>
</table>
<c:forEach items="${table.tableKeys}" var="keys"><input name =${keys.key}ID type=hidden value=${map[keys.key]} /></c:forEach>
<div class="funbutton">
  <button type="submit" style="width:60px" type="submit" onmouseover="this.className='buttom2'" 
  onmouseout="this.className='buttom1'" class="buttom1" >
  <img src="images/save1.gif" width="12" height="12" align="middle"> 保存</button>&nbsp;&nbsp;&nbsp;
  <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" 
  onclick="history.back()"><img src="images/back1.gif" width="11" height="11" align="middle"> 取消</button>
</div>
<danbwh:tableForZhucb />
</form>
</body>
</html>
