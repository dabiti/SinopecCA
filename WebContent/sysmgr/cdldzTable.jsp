<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base target="_self">
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		
		<%@ include file="/common/validator.jsp"%>
		<script type="text/javascript">
		$(document).ready(function() {
			$("#form1").validate({
			   errorLabelContainer:"#error div.error",
			   wrapper:"li",
			   submitHandler:function(form){
				form.submit();
				}
			});
		})
		
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
					�˵��������� - ${shangjmc}
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
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
		<html:form styleId="form1" action="doMenu?method=save&shangjgn=${shangjgn}" method="post">
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
		            <th colspan="3" class="class1_thead th">�½��˵�</th>
		          </tr>
		        </thead>
		        <tbody>		            
			    <tr>
		          <td class="class1_td w250">����ID��</td>
		          <td class="class1_td alignleft" colspan="2">
		          	<html:text styleId="gongnid" property="gongnid" styleClass="inputField required gongnid" />
		          	 *����5λ����
		          	</td>	
				</tr>
			    <tr>
		          <td class="class1_td w250">�ӹ��ܹ���ID��</td>
		          <td class="class1_td alignleft" colspan="2">
		          	<html:text styleId="zignid" property="zignid" styleClass="inputField required gongnid" />
		          	 *����1λ���֣�һ��Ϊ0������˵�
		          	</td>	
				</tr>
		        <tr>
		          <td class="class1_td w250">�������ƣ�</td>
		          <td class="class1_td alignleft" >
		          	<html:text styleId="gongnmc" property="gongnmc" styleClass="inputField required" />
		          	*�Զ��壬����
		          	</td>		          	
		        </tr>
		        <tr>
		          <td class="class1_td w250">����URL��</td>
		          <td class="class1_td alignleft" colspan="2">
		          	<html:text styleId="gongnurl" property="gongnurl" styleClass="inputField gongnid" style="width:350px" />
		          	 *�����ʾ�ļ���
		          	</td>
		        </tr>		
		        <tr>
		          <td class="class1_td w250">����˳��</td>
		          <td class="class1_td alignleft" colspan="2"> 
		          	<html:text styleId="gongnsx" property="gongnsx" styleClass="inputField required gongnid"/></td>
		        </tr>	
		        <tr>
		          <td class="class1_td w250">�������ͣ�</td>
		          <td class="class1_td alignleft" colspan="2">
		          	<html:select styleId="gongnlx" property="gongnlx" style="width:120px;">
						<html:option value="0">0-�˵�</html:option>
						<html:option value="1">1-���ܵ�</html:option>
					</html:select> 
		        </tr>		        
		        <tr>
		          <td class="class1_td w250">�ϼ����ܣ�</td>
		          <td class="class1_td alignleft" colspan="2">
		          	<html:text styleId="shangjgn" property="shangjgn" value="${shangjgn}" readonly="true" styleClass="inputField"/>
		          	 *����˵������Զ���ȡ
		          	</td>
		        </tr>		
		
				        
		          	<html:hidden styleId="quanxwz" property="quanxwz" />
		          
		        <tr>
		          <td class="class1_td w250">����״̬��</td>
				  <td class="class1_td alignleft" colspan="2">
		          	<html:select styleId="zhuangt" property="zhuangt" style="width:120px;">
						<html:option value="1">1-չ��</html:option>
						<html:option value="0">0-����</html:option>
					</html:select> 
		          </td>
		        </tr>	
		        <tr>
		          <td class="class1_td w250">��ע��</td>
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
		  <img src="images/save1.gif" width="12" height="12" align="middle"> ����</button>&nbsp;&nbsp;&nbsp;
		  <button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" 
		  onclick="history.back()"><img src="images/back1.gif" width="11" height="11" align="middle"> ȡ��</button>&nbsp;&nbsp;&nbsp;
		</div>
		</html:form>
	</body>
</html>
