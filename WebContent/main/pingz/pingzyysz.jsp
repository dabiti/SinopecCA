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
<script type="text/javascript" language="javascript">
	function toModiPingz(pingzbs){
		var data=pingzbs;
		
		if(data.indexOf('#')!=-1){
			data=data.replace("#","|");
			}
		window.location.href='yinjManage.do?method=toModiPingzGz&pingzbs='+encodeURI(data);

		}

	function toDeletePingz(pingzbs){
		var data=pingzbs;
		
		if(data.indexOf('#')!=-1){
			data=data.replace("#","|");
			}
		window.location.href='yinjManage.do?method=toDeletePingzGz&pingzbs='+encodeURI(data);

		}
</script>
</head>
<body >
<table width="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="38"><img src="images/main_place_pic.gif" width="38" height="32"></td>
    <td background="images/main_place_bg.gif">印鉴管理-凭证验印设置</td>
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
           			<div style="text-align:center;"><b>凭证列表</b></div>
           			<button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onClick="location.href='yinjManage.do?method=toAddPingzxx'"><img src="images/add.gif" width="13" height="13" align="absmiddle">添加</button>
          			
          <tr> 
            <td class="class1_thead th w60">凭证标识</td>
            <td class="class1_thead th">凭证名称</td>
	   		<td class="class1_thead th">验印规则</td>
	   		<td class="class1_thead th">是否显示</td>
	        <td class="class1_thead th">操作</td>
          </tr>
        </thead>
        <%int i=0; %>
      	<c:forEach items="${pingzList}" var="pingz">
	        <tr> 
		  	  <td class="class1_td center">&nbsp;${pingz.pingzbs}</td>
		  	  <td class="class1_td center">&nbsp;${pingz.pingzmc}</td>
		  	  <td class="class1_td center" title="${pingz.yanygz.yanyingz}">&nbsp;${pingz.yanygz.guizmc}</td>
		  	  <td class="class1_td center">&nbsp;${pingz.shifxs}</td>
		  	  <td class="class1_td center">&nbsp;
<%--		  	 <a href="" onclick="toModiPingz(${pingz.pingzbs});">修改</a>--%>
		  	  <button type="button"  style="width:60px"
										onmouseover="this.className='buttom2'" onclick="toModiPingz('${pingz.pingzbs}')"
										onmouseout="this.className='buttom1'" class="buttom1">
										<img src="images/edit.gif" width="13" height="13"
											align="middle">
										修改</button>
		  	  <button type="button"  style="width:60px"
										onmouseover="this.className='buttom2'" onclick="toDeletePingz('${pingz.pingzbs}')"
										onmouseout="this.className='buttom1'" class="buttom1">
										<img src="images/edit.gif" width="13" height="13"
											align="middle">
										删除</button>
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
