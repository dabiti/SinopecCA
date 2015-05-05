<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base target="_self">
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link href="style/jquery.autocomplete.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="js/autocompleteSet.js"></script>
		<%@ include file="/common/validator.jsp" %>	
		<script type="text/javascript">
			$(document).ready(function() {
				 $("#form").validate({
				   wrapper:"li",
				   submitHandler:function(form)
				   {
						form.submit();
					 	window.close();
				   }
				 })
				 $('#shurlx').unbind().bind('change', function() {
					 if($('#shurlx').val()=='������ʾ'||$('#shurlx').val()=='��������'||$('#shurlx').val()=='ģʽ����'){
						  $('#shurlxList').html("����:<textarea  name ='map(beiz)'  cols='30' rows='3'/>");
					 }else{
						  $('#shurlxList').html(null);
						  if($('#shurlx').val()=='�����ʾ')
						  {
							  $("#zdmc").val('rowid \"rowid\"');
							  $("#zdmc").attr("disabled",true);
						  }
					 }
				 });
				 
			})
		</script> 
		<script type="text/javascript">
			function doAddDict(){
				var zhidbs = document.getElementById('zhidbs').value;
				if(zhidbs==''){alert('�ֵ��ʶ����Ϊ�գ������������!');return;};
				if(zhidbs == undefined)
				{
					return;
				}
				window.showModalDialog('dictionaryMgr.do?method=listDictionary&zhidbs='+zhidbs,"dialogWidth:650px;dialogHeight:700px;help:no;scroll:yes;center:yes;status:no");
				return;
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
					Ureport���� - ������� - �������
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		<html:form styleId="form" action="r_resultMgr.do?method=save&action=${action}"
			method="post">
			<table width="97%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
				</tr>
				<tr>
					<td background="images/table_arrow_05.gif">
						&nbsp;
					</td>
					<td>
						<table width="100%" border="0" cellspacing="1" cellpadding="0"
							class="class1_table">
							<thead class="class1_thead">
								<tr>
									<th colspan="3" class="class1_thead th">
										�޸Ľ��Ҫ��
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									�����ʶ��
								</td>
								<td class="class1_td alignleft">
									<html:text styleClass="inputField required BAOBBS" property="map(baobbs)" readonly="true" />
									*����ID
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									Ҫ�ر�ʶ��
								</td>
								<td class="class1_td alignleft">
									<html:text property="map(yaosbs)" styleClass="inputField required BAOBBS"  readonly="true" />
									*�������Զ���
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									�ֶ����ƣ�
								</td>
								<td class="class1_td alignleft">
									<html:text property="map(yaosmc)" styleId='zdmc'  styleClass="inputField required BAOBBS" onfocus="javaScript:autoCompleteForTableLine('zdmc',{tableName:this.form.SHUJHQFS.value});" />
									*���磺������־��ѯ
									<input id="SHUJHQFS" type="hidden" value="${SHUJHQFS}"/>
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									�ֶ����ͣ�
								</td>
								<td class="class1_td alignleft">
									<html:select property="map(yaosgs)">
										<html:option value="string">�ַ�����</html:option>
										<html:option value="int">����</html:option>
										<html:option value="float">������[float]</html:option>
										<html:option value="double">������[double]</html:option>
									</html:select>
									*���磺���ݿ��ֶ�����
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									Ҫ�ر��⣺
								</td>
								<td class="class1_td alignleft">
									<html:text property="map(yaosbt)" styleClass="inputField required" />
									*���磺��ѯչʾ��λ����
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									��ʾ���ͣ�
								</td>
								<td class="class1_td alignleft">
									<html:select property="map(xianslx)" styleId="shurlx">
										<html:option value="��ͨ��ʾ">��ͨ��ʾ</html:option>
										<html:option value="�����ʾ">�����ʾ </html:option>
										<html:option value="ģʽ����">ģʽ����</html:option>
										<html:option value="������ʾ">������ʾ</html:option>
										<html:option value="��������">�������� </html:option>
									</html:select>
									*���磺��λ���ͣ�ѡ�� ������ʾ  ����ʵ����תչʾģʽ
									<span id ='addInput'></span> 
									<span id ='delInput'></span>   
									<div id ='shurlxList'><textarea  name ='map(beiz)'  cols='30' rows='3'><bean:write name='CustomForm' property='map(beiz)' /></textarea></div> 
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									��ʾ˳��
								</td>
								<td class="class1_td alignleft">
									<html:text property="map(xianssx)" styleClass="inputField required BAOBBS"/>
									*���磺������λ��ʾ˳��
									<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									�Ƿ���ʾ��
								</td>
								<td class="class1_td alignleft">
									<html:select property="map(shifxs)">
										<logic:equal name="CustomForm" property="map(shifxs)" value="��">
											<option value="��" selected=selected>
												��
											</option>
											<option value="��">
												��
											</option>
										</logic:equal>
										<logic:equal name="CustomForm" property="map(shifxs)"
											value="��">
											<option value="��">
												��
											</option>
											<option value="��" selected=selected>
												��
											</option>
										</logic:equal>
									</html:select>
									*���磺�Ƿ���ʾ����λ�ڲ�ѯ�����
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									�ֵ��ʶ��
								</td>
								<td class="class1_td alignleft">
									<html:text styleId="zhidbs" property="map(zhidbs)" styleClass="inputField" /> <a href="javascript:doAddDict();">����ֵ�Ҫ��</a>
								</td>
							</tr>
						</table>
					</td>
					<td background="images/table_arrow_07.gif">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
				</tr>
			</table>
			<div class="funbutton">
				<button type="submit" style="width: 60px" type="submit"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1" >
					<img src="images/save1.gif" width="12" height="12" align="middle">
					����
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width: 60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="window.close();">
					<img src="images/back1.gif" width="11" height="11" align="middle">
					ȡ��
				</button>
			</div>
		</html:form>
	</body>
</html>
