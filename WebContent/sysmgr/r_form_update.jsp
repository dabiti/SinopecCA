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
		var i=0;
		$(document).ready(function() {
			 $("#form").validate({
			   wrapper:"li",
			   submitHandler:function(form)
			   {
				 	form.submit();
				 	window.close();
			   }
			 });
			var jtdxk = $('#beiz').val();
			if($('#shurlx').val()!='��̬��ѡ��'&&$('#shurlx').val()!='��������'&&$('#shurlx').val()!='�Ӽ�����'){
				 $('#shurlxList').html(null);
			}
			if($('#shurlx').val()=='��̬��ѡ��'){
				var jtd = jtdxk.split('|');
				for(i=0;i<jtd.length-1;i++)
				{
					var id_name = jtd[i].split('=');
					var delInput = "<a href='javascript:deleteInput("+i+")'>ɾ��</a>";
					var html=$('#shurlxList').html();
					$('#shurlxList').html(html+"<div id=shurlxList"+i+">ѡ��:<input name ='shurlxName'  class='inputField' value='"+id_name[0]+"'/> ���ݿⱣ��:<input name ='shurlxValue'  class='inputField' value='"+id_name[1]+"'/>"+delInput+"</div>");
				}
				$('#addInput').html("<input type='button'  onclick='addInput()' value='���'/>")
			}
			$('#shurlx').unbind().bind('change', function(){
					  if($('#shurlx').val()=='��̬��ѡ��')
					  {
						  var delInput = "<a href='javascript:deleteInput("+i+")'>ɾ��</a>";
						  $('#shurlxList').html("<div id=shurlxList"+i+">ѡ��:<input name ='shurlxName'  class='inputField'/> ���ݿⱣ��:<input name ='shurlxValue'  class='inputField'/>"+delInput+"</div>");
						  $('#addInput').html("<input type='button'  onclick='addInput()' value='���'/>")
					  }else{
						  $('#shurlxList').html(null);
						  $('#addInput').html(null);
					  }
					  
					  if($('#shurlx').val()=='��̬��ѡ��'||$('#shurlx').val()=='��������'||$('#shurlx').val()=='�Ӽ�����'){
						  $('#shurlxList').html("����Դ:<textarea  name ='shujy'  cols='30' rows='3'/>"+"</textarea><div>ע��:����Դ���ݼ��ϱ��������У��ֱ�Ϊ:'key'(��ֵ),'name'(ת��)");
					  }else{
						  $('#shurlxList').html(null);
					  }
					});
		})
	
	function addInput()
	{
		    var html=$('#shurlxList').html();
		    i++;
		    var delInput = "<a href='javascript:deleteInput("+i+")'>ɾ��</a>";
			$('#shurlxList').html(html+"<div id=shurlxList"+i+">ѡ��:<input name ='shurlxName'  class='inputField required'/> ���ݿⱣ��:<input name ='shurlxValue'  class='inputField'/>"+delInput+"</div>");
	}
	function deleteInput(i){
		$('#shurlxList'+i).html(null);
	}
		function doAddDict()
		{
			var zhidbs = document.getElementById('zhidbs').value;
			if(zhidbs == undefined)
			{
				return;
			}
			window.showModalDialog('dictionaryMgr.do?method=listDictionary&zhidbs='+zhidbs,"dialogWidth:650px;dialogHeight:700px;help:no;scroll:yes;center:yes;status:no");
			return;
	}
		</script> 
		<script type="text/javascript">
			function doAddDict()
			{
				var zhidbs = document.getElementById('zhidbs').value;
				if(zhidbs == undefined)
				{
					return;
				}
				window.showModalDialog('dictionaryMgr.do?method=listDictionary&zhidbs='+zhidbs,"dialogWidth:650px;dialogHeight:700px;help:no;scroll:yes;center:yes;status:no");
				return;
			}
			function doAddYewgz()
			{
				var yaosbs = document.getElementById('yaosbs').value;
				if(yaosbs == undefined)
				{
					return;
				}
				window.showModalDialog('yewgzConfig.do?method=modify&type=ureport&yuansid='+yaosbs,"dialogWidth:650px;dialogHeight:700px;help:no;scroll:yes;center:yes;status:no");
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
					Ureport���� - ������� - ��Ҫ������
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
		<html:form styleId="form" action="r_formMgr.do?method=updateForm" method="post">
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
										�޸ı�
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									�����ʶ��
								</td>
								<td class="class1_td alignleft">
									<html:text  property="map(BAOBBS)" styleClass="inputField required BAOBBS" readonly="true"/>
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									Ҫ�ر�ʶ��
								</td>
								<td class="class1_td alignleft">
									<html:text property="map(YAOSBS)" styleId="yaosbs" styleClass="inputField required BAOBBS"  readonly="true"/>
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									�ֶ����ƣ�
								</td>
								<td class="class1_td alignleft">
									<html:text property="map(YAOSMC)"  styleId='zdmc'   styleClass="inputField required BAOBBS" onfocus="javaScript:autoCompleteForTableLine('zdmc',{tableName:this.form.SHUJHQFS.value});" />
									<input id="SHUJHQFS" type="hidden" value="${SHUJHQFS}"/>
									*���ݿ��б���ֶ������磺jigh
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									Ҫ�����ƣ�
								</td>
								<td class="class1_td alignleft">
									<html:text  property="map(YAOSBT)" styleClass="inputField required" />
									*��ѯ�������ƣ��磺������
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									�ֶ����ͣ�
								</td>
								<td class="class1_td alignleft">
									<html:select property="map(YAOSGS)">
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
									Ҫ�����ͣ�
								</td>
								<td class="class1_td alignleft">
									<html:select styleId="shurlx"  property="map(YAOSLX)">
										<html:option value="�ı�����">�ı�����</html:option>
										<html:option value="��������">��������</html:option>
										<html:option value="ģ������">ģ������</html:option>
										<html:option value="��������">��������</html:option>
										<html:option value="�Ӽ�����">�Ӽ�����</html:option>
										<html:option value="��������">��������</html:option>
										<html:option value="���ڷ�Χ">���ڷ�Χ</html:option>
										<html:option value="��̬��ѡ��">��̬��ѡ�� </html:option>
										<html:option value="��̬��ѡ��">��̬��ѡ�� </html:option>	
									</html:select>
									<span id ='addInput'></span> 
									<span id ='delInput'></span>          
									<div id ='shurlxList'><textarea  name ='beiz' id ='beiz'  cols='30' rows='3'/><bean:write name='CustomForm' property='map(BEIZ)' /></textarea><div></>ע��:����Դ���ݼ��ϱ��������У��ֱ�Ϊ:'key'(��ֵ),'name'(ת��)</div></div>
									*ѡ���Ҫ�����ͣ��磺�ı��������򣬶�ѡ���
								</td>
								<td class="class1_td alignleft">
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
								�Ƿ���
								</td>
								<td class="class1_td alignleft">
								<html:select property="map(SHIFBT)">
									<logic:equal name="CustomForm" property="map(SHIFBT)"
											value="��">
											<option value="��" selected=selected>
												��
											</option>
											<option value="��">
												��
											</option>
										</logic:equal>
										<logic:equal name="CustomForm" property="map(SHIFBT)"
											value="��">
											<option value="��">
												��
											</option>
											<option value="��" selected=selected>
												��
											</option>
										</logic:equal>
								</html:select>
								</td>
								<td class="class1_td alignleft"></td>
							<!--
								<tr>
									<td class="class1_td w250">
										Ҫ�س��ȣ�
									</td>
									<td class="class1_td alignleft">
										<html:text  property="map(YAOSCD)" styleClass="inputField required BAOBBS"/>
									</td>
									<td class="class1_td alignleft"></td>
								</tr>
							-->
							<tr>
								<td class="class1_td w250">
									Ĭ �� ֵ��
								</td>
								<td class="class1_td alignleft">
									<html:text  property="map(MOYZ)" styleClass="inputField"/>
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									��ʾ˳��
								</td>
								<td class="class1_td alignleft">
									<html:text  property="map(XIANSSX)" styleClass="inputField required BAOBBS" />
									*��Ҫ�ص�����˳�� 
								</td>								
								
							</tr>
							<tr>
								<td class="class1_td w250">
									 
								</td>
							<td class="class1_td alignleft">
									<html:button property="button" onclick="doAddYewgz();" value="���ҵ�������Ϣ"></html:button> 
								</td>
							</tr>
							<!--
							<tr>
								<td class="class1_td w250">
									�ֵ��ʶ��
								</td>
								<td class="class1_td alignleft">
									<html:text styleId="zhidbs" property="map(ZHIDBS)" styleClass="inputField required BAOBBS" />
								</td>
								<td class="class1_td alignleft">
									<html:button property="button" onclick="doAddDict();" value="����ֵ�Ҫ��"></html:button> 
								</td>
							</tr>
							-->
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
				<button type="submit" style="width:60px" type="submit"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12"
						align="middle">
					����
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="window.close();">
					<img src="images/back1.gif" width="11" height="11"
						align="middle">
					ȡ��
				</button>
			</div>
		</html:form>
	</body>
</html>
