<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base target="_self">
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
		function clickURL(){
    		window.parent.frames.tree.location="doMenu.do?method=show";
   		}
   		
		function doSubmit(){
			window.close();
			return;
		} 

		function doSubmit(){
			window.close();
			return;
		}
		function saveTree(){
			window.parent.frames.tree.saveJSON();
		}
		</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					菜单定义配置 - ${shangjmc}
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<br>
		<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td id="errorMessage"   class="orange error">
						<div class="error orange"></div>
					</td>
				</tr>
				</table>
<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange error">
						<div class="error orange"></div>
					</td>
				</tr>
				<logic:messagesPresent>
					<tr>
						<td class="orange">
							<div id=abc class="abc"><html:errors /></div>
						</td>
					</tr>
				</logic:messagesPresent>
			</table>
</form>
		<html:form styleId="form1" action="doMenu?method=doSave" method="post">
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
		            <th colspan="3" class="class1_thead th">修改菜单</th>
		          </tr>
		        </thead>
		        <tbody>		            
			    <tr>
		          <td class="class1_td w250">功能ID：</td>
		          <td class="class1_td alignleft" colspan="2">
		          	<bean:write name="MenuForm" property="gongnid" />
		          	<html:hidden property="gongnid" /></td>	
				</tr>
			    <tr>
		          <td class="class1_td w250">子功能功能ID：</td>
		          <td class="class1_td alignleft" colspan="2">
		            <bean:write name="MenuForm" property="zignid" />
		          	<html:hidden property="zignid"/></td>	
				</tr>
		        <tr>
		          <td class="class1_td w250">功能名称：</td>
		          <td class="class1_td alignleft" >
		            <html:text styleId="gongnmc" property="gongnmc" styleClass="inputField"/>
		          </td>	          	
		        </tr>
		        <tr>
		          <td class="class1_td w250">功能URL：</td>
		          <td class="class1_td alignleft" colspan="2">
		            <bean:write name="MenuForm" property="gongnurl" />
		          	<html:hidden property="gongnurl" /></td>
		        </tr>			
		        <tr>
		          <td class="class1_td w250">上级功能：</td>
		          <td class="class1_td alignleft" colspan="2">
		            <html:text styleId="shangjgn" property="shangjgn" styleClass="inputField"/>
		          	</td>
		        </tr>				        	        
		        <tr>
		          <td class="class1_td w250">权限位置：</td>
		          <td class="class1_td alignleft" colspan="2">
		          	<html:text styleId="quanxwz" property="quanxwz" styleClass="inputField"/>
		        </tr>	
		        <tr>
		          <td class="class1_td w250">功能顺序：</td>
		          <td class="class1_td alignleft" colspan="2">
		          	<html:text styleId="gongnsx" property="gongnsx" styleClass="inputField"/></td>
		        </tr>		        		        
		        <tr>
		          <td class="class1_td w250">功能状态：</td>
				  <td class="class1_td alignleft" colspan="2">
		          	<html:select styleId="zhuangt" property="zhuangt" style="width:120px;">
						<html:option value="0">0-隐藏</html:option>
						<html:option value="1">1-展现</html:option>
					</html:select> 
		          </td>
		        </tr>	
		        <tr>
		          <td class="class1_td w250">备注：</td>
		          <td class="class1_td alignleft" colspan="2">
		          	<html:text styleId="beiz" property="beiz" styleClass="inputField "/></td>
		        </tr>			        		        		                
		        </tbody>
		    </table>
		    </td>
		    <td background="images/table_arrow_07.gif">&nbsp;</td>
		  </tr>
		  <tr>
		    <td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
		  </tr>
		</table>
		<div class="funbutton">
		  <button type="submit" style="width:60px" type="submit" onmouseover="this.className='buttom2'" 
		  onmouseout="this.className='buttom1'" class="buttom1" >
		  <img src="images/save1.gif" width="12" height="12" align="middle"> 保存</button>&nbsp;&nbsp;&nbsp;
		  <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" 
		  onclick="history.back()"><img src="images/back1.gif" width="11" height="11" align="middle"> 取消</button>&nbsp;&nbsp;&nbsp;
		</div>
		</html:form>
	</body>
</html>
