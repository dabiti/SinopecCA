<%@page language="java" contentType="text/html;charset=GBK" isELIgnored="false" pageEncoding="GBK"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>��ʯ������ϵͳ</title>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<script src="js/authorized.js" type="text/javascript"></script>
		<%@ include file="/common/yewgz.jsp"%>

		<script type="text/javascript" language="javascript">

		var obj = window.dialogArguments;
		var checkType=obj.checktype;
		var clerkCode=obj.clerkCode;
		var titleName = obj.titleName;
		
		$(document).ready(function() {
			   //���õ�������λ��\��С
			   {
				   window.dialogTop = 200+"px"; 
				   window.dialogLeft = 300+"px"; 
				   window.dialogHeight = 200+"px"; 
				   window.dialogWidth = 260+"px"; 
			   }  
				
				if(clerkCode!=null&&clerkCode.length!=0){
				$("#clerknum").val(clerkCode);
				$("#clerknum").attr("readonly","readonly").css("color","silver");
				$("#password").focus();
				
					
				}
		
		   $("#yemtitle").html(checkType=="��Ȩ"?"����":checkType+"��Ա�����֤");
			   $("#form1").validate({
			   errorLabelContainer:"#error div.error",
			   wrapper:"li",
			   submitHandler:function(form){
				   validate();
				   return;
			   }
		}) 
		});

		
		function Feature_clicked()
		{
			 $("#clerknumMsg").text('');
			 var clerknum = $("#clerknum").val();
			 if(clerknum==null||clerknum.length==0){
				 $("#clerknumMsg").text('�������Ա��!');
					return;
				}
			 var strTZ =  getFingerMsg();
		 	/*//��ȡָ������
			//���ô���
			 dtm.SetPara("COM4;");
		  	strTZ = dtm.GetFeature();
		    if(strTZ == "")
		    {
		    	alert("�ɼ�ָ������ʧ��!");
		    	return ;
		    }
		    var factoryId=dtm.GetFactoryId();
		    //alert(factoryId);
		  	if(factoryId==null||factoryId.length==0){
		  		factoryId="1";
			  	}
		  	strTZ = factoryId+strTZ;*/
		  	 
		  	//alert(strTZ);
		    $("#fingerMsg").val(strTZ);
		   validate();
		}
		var error_counts = 0;
		function validate(){
		   var clerknum = $("#clerknum").val();
		   var password = $("#password").val();
		   var fingerMsg= $("#fingerMsg").val();
		   $.post("login.do?method=authorized",{clerkCode:clerknum,fingerMsg:fingerMsg,passwd:password,titleName:encodeURI(obj.titleName),checkType:encodeURI(checkType),account:obj.account,quanxbs:obj.quanxbs},function (data,textStatus){
			   if(textStatus=="success"){
				   if(checkType=="��Ȩ"){
					   checkType="����";
					   }
					   if(data==0)
					{
						alert(checkType+"��Ա������!");
					}
					if(data==4)
					{
						alert(checkType+"��Ա����Ϊ��¼��Ա!");
					}
					if(data==5)
					{
						alert("�����֤ʧ�ܣ������쳣!");
					}
					if(data==1){
						alert("�����ɹ�!");
						if(titleName=="��ӡ"||titleName=="�˹���ӡ"||titleName=="ʵ����ӡ"){
							window.returnValue="1^"+clerknum;
						}else{
							window.returnValue="1";
						}
			         	window.close();
			         	return;
					}
					if(data.substring(0,1)==2)
					{
						alert("�����֤δͨ��:"+data.substring(1));
					}
					if(data==3)
					{
						alert("�������!");
					}
					if(data==6)
					{
						alert("��Աû�и��˵�Ȩ��!");
					}
					error_counts++;
					if(error_counts>=3)
					{
						alert(checkType+"�����������,����ʧ��,�رմ���!");
						window.returnValue='0';
			         	window.close();
			         	return;
					}
			   }
		   }, "text");
		}
		</script>
	</head>
<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<html:form styleId="form1" action="changepwd.do" method="post">
			<table width="100%" border="0" align="center" cellpadding="0"
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
							class="class1_table" align="center">
							<thead class="class1_thead">
								<tr>
									<th colspan="2" class="class1_thead th" id="yemtitle">
										
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									��Ա�ţ�
								</td>
								<td class="class1_td alignleft">
									<input id=clerknum name=clerknum type='text' class="inputField required clerknum"  style="width:150px;"/><span style="color: red" id="clerknumMsg"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									��Ա���룺
								</td>
								<td class="class1_td alignleft">
									<input id=password name=password type='password' class="inputField required clerk_password"  style="width:150px;"/>
								</td>
									<input type="hidden"  id="fingerMsg" />					
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
			<table>
			<tr>
			<td width="25%">
				&nbsp;
			</td>
			<td width="25%" >
				<button type="submit" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12" align="absmiddle">
					ȷ��
				</button>
			</td>
			<td width="25%">
				<button type="button" style="width:80px"
					onmouseover="this.className='buttom2'" onclick="Feature_clicked()";
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="15" height="12" align="absmiddle">
					ָ����֤
				</button>
			</td>
			<td width="25%">
				<button type="button" style="width:60px" onclick="window.close()"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/back1.gif" width="12" height="12" align="absmiddle">
					ȡ��
				</button>
			</td>
			</tr>
			</table>

</html:form>
			<object classid="clsid:7D131444-0A2F-4F37-9605-6E1BF067AF18" width="0" height="0" id="dtm" codebase="JZT998_For_Huaxia.ocx">
</object>
	</body>
</html>

