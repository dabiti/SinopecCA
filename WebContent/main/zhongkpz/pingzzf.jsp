<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script type="text/javascript" src="js/special-business.js"></script>
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript"> 
		 $(document).ready(function() {
		 $("#zhangh").focus();
		 //��ʼ������ʱ�������
		 document.getElementById("qispzh_").value="";
		 document.getElementById("bens").value="0";
		 document.getElementById("guiyh").value="";
		 document.getElementById("zhongzpzh").innerHTML="";
		 // ��ʼ��ʱ����ÿ����������
		 getMeibzs();
		   $("#form1").validate({
		   errorLabelContainer:"#error div.error",
		   wrapper:"li",
		   submitHandler:function(form){
		   if (confirm("ȷ��Ҫ����������Ϣ��"))
		   	{
		   	  form.submit();
		   	  $("#tu").html('<table align="center"><tr><td><img border="1" src="images/appInstall_animated.gif"/><b>������Ϣ¼����,���Ե�......<b></td></tr></table>');
		   	}
		   }
		 }) 
		 });
		 	var meibzs = "1";
			//��ȡ���ֲ�ͬƾ֤���͵�ÿ������
		    function getMeibzs() {
				var pingzbs = document.getElementById("pingzlx").value;//ƾ֤�������ݿ�����ID��ʽ����
				$.post("zhongkpz.do?method=getPingzmbzs", {pingzbs:pingzbs}, function (data, textStatus) {
					if (textStatus = "success") 
					{
						meibzs = data;
					}
				}, "text");
				countPz();
			}
		 	function countPz(){
			 	var obj1 = document.getElementById("qispzh_").value;
			 	var obj2 = document.getElementById("bens").value;//����
			 	var obj3 = document.getElementById("zhongzpzh");
                var obj4 = document.getElementById("pingzlx").value;//ƾ֤����
                var obj1_=parseFloat(obj1);
                var obj2_=parseFloat(obj2);
                var obj3_=parseFloat(obj3);
                var meibzs_=parseFloat(meibzs);
                //�ж����뱾���Ƿ��Ǵ�������ʽ�ģ��������ʽУ��
                var reg=/^\d+$/;
                if(!reg.test(obj2))
                {   
                   alert("�����������֣����������뱾����"); 
                   return false; 
                }
			 	if(obj1==""||obj2=="")
				{
					return;
			 	}
			 	//��ֹƾ֤��=��ʼƾ֤��+����*��Long q = Long.parseLong(qispzh);
			 	if(obj2_=="0")
		 		  {
		 		     var num1 = obj1_ ;//��ֹƾ֤�ŵ�����ֵ
		 		     var cha = obj1.length - String(num1).length;
		 		     var str = '0000000000';
		 	         var num = str.substring(0,cha) + num1;
		 		  }else
		 		  {
		 		    var num1 = obj1_ + obj2_ * meibzs_ - 1;//��ֹƾ֤�ŵ�����ֵ
		 		    var cha = obj1.length - String(num1).length;
		 		    var str = '0000000000';
		 	        var num = str.substring(0,cha) + num1;
		 		  }
		 		if(isNaN(num))
			 	{
		 			num = " ";
		 		}
		 		obj3.innerHTML=num;
		 		var obj3 = document.getElementById("zhongzpzh_").value=num;
		 		return true;
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
					�ؿ�ƾ֤ - ƾ֤����
				</td>
			</tr>
		</table>
		<br>
		<div id="tu"></div>
		<form id=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange">
						<div class="error orange"></div>
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
		<html:form styleId="form1" action="zhongkpz.do?method=saveZuof"
			method="post">
			<table width="97%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="up-left" />
						<td class="up-middle" />
							<td class="up-right" />
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
									<th colspan="2" class="class1_thead th">
										ƾ֤������Ϣ
									</th>
								</tr>
							</thead>
							<tr>
								<td align="right">
								</td>
							</tr>
							<tr>
								<td width="45%" class="class2_td  alignright">
									<b>ƾ֤����</b>
								</td>
								<td class="class2_td alignleft">
									<select id="pingzlx" name="pingzlx" onchange="getMeibzs();" style="width: 135px;">
										<c:forEach items="${pingzlxlist}" var="plist">
											<option value="${plist.pingzbs}">${plist.pingzmc}</option>//���ݿ��б���ƾ֤��ʶ��������ʾƾ֤����
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									<b>���Ϲ�Ա</b>
								</td>
								<td class="class2_td alignleft w220">
								    <html:text property="guiyh" styleId="guiyh" styleClass="inputField required"/>
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									<b>��ʼƾ֤��</b>
								</td>
								<td class="class2_td alignleft w220">
									<html:text property="qispzh_" styleId="qispzh_" styleClass="inputField required"/>
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									<b>����</b>
								</td>
								<td class="class2_td alignleft w220">
								    <html:text property="bens" styleId="bens" onblur="countPz();" styleClass="inputField required"/>
								</td>
							</tr>
							<tr>
								<td class="class2_td  alignright">
									<b>��ֹƾ֤��</b>
								</td>
								<td class="class2_td alignleft w220">
								&nbsp;<span id="zhongzpzh"></span>
								</td>
							</tr>
							<input type="hidden" id="zhongzpzh_" name="zhongzpzh_"/>
						</table>
					</td>
					<td background="images/table_arrow_07.gif">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bottom-left" />
						<td class="bottom-middle" />
							<td class="bottom-right" />
				</tr>
			</table>
			<div class="funbutton">
				<button type="submit" style="width: 60px" onclick="javascript:if(!countPz()){return false;}else{return true;}"
					onmouseover="this.className='buttom2'" 
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					����
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width: 60px"
					onclick='javascript:form1.reset();document.getElementById("zhongzpzh").innerHTML="";document.getElementById("qispzh_").value="";document.getElementById("bens").value="0";document.getElementById("guiyh").value="";'
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/delete.gif" width="11" height="11"
						align="absmiddle">
					���
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width: 60px" onclick="history.back()"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					ȡ��
				</button>
			</div>
		</html:form>
	</body>
</html>