<%@page language="java" contentType="text/html;charset=GBK"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/shield.js"></script>
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<script src='js/date/WdatePicker.js' type='text/javascript'></script>
		<script type="text/javascript">
		$(function(){
			$("#chuprq").val('${date}');
			
			});
		function volidate(key,id,name){
			var reg;
			//�˺�
			if(1==key){
				reg =   /(^[0-9]{9}$)|(^[0-9]{17}$)|(^[0-9]{22}$)|(^[0-9]{10,13}$)/;
			}
			//ƾ֤��
			if(2==key){
				reg = /^[0-9]{1,10}$/;
			}
			//���
			if(3==key){
			//	reg = /^\d+$/;
				reg = /^(([0-9]+)|([0-9]+\.[0-9]{1,2}))$/;
			}
			//��Ʊ����
			if(4==key){
				reg = /^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/;
			}
			if($("#"+id).val().length!=0&&$("#"+id).val()!=''){
				if(reg!=null&&!reg.test($("#"+id).val())){
					$("#"+id+"msg").text("* "+name+"��ʽ����ȷ");
					return false; 
				}
				if(3==key){
					var reg2 = /^([0-9]+\.[0-9]{1,2})$/;
					var reg3 =/^([0-9]+)$/;
					var reg4 =/^([0-9]+\.[0-9]{1,1})$/;
					var val = $("#"+id).val();
					if(reg3.test(val)&&val.indexOf('0')=='0'){
						val = val.substr(1);
					}
					if(!reg2.test(val)){
						if(val.length>3){
							var val1 = val.substring(0,val.length-2);
							var val2 = val.substring(val.length-2);
							$("#"+id).val(val1+"."+val2);
						}else if(val.length==3){
							var val3 = val.substring(0,1);
							var val4 = val.substr(1);
							$("#"+id).val(val3+"."+val4);
						}else{
							var temp  = val/100;
							if(reg4.test(temp)){
								$("#"+id).val(temp+"0");
							}else{
								$("#"+id).val(temp);
							}
						}
					
					}
				}
				if(4==key){
					if($("#"+id).val()>'${date}'){
						$("#"+id+"msg").text("* "+name+"���ܳ�������");
						return false; 
					}
				}
			}else{
				$("#"+id+"msg").text("* "+name+"����Ϊ��");
				return false;
			}	
			
			$("#"+id+"msg").text("");
			return true;
			

		}
		
		function requestForZhangh() {
		 	var flag = true;
			$("#zhanghmsg").text("");
			$("#hum").html("");
			$("#pingzhmsg").text("");
			$("#chuprqmsg").text("");
			$("#jinemsg").text("");
			$("#yinjtxmsg").text("");
			$("#result").text("");
			
			if(volidate(1,'zhangh','�˺�')){
				var account = $("#zhangh").val();
				$.ajax({
					type:"post",
					async:false,
					cache:false,
					url:"accountinfo.do?method=getAccountForQiantyy",
					data:{account:account},
					dataType:"text",
					success: function (data,textStatus) {
					if (textStatus = "success") {
						if(data.length>500){
							flag  =  false; 
							$("#zhanghmsg").text("��½�ѳ�ʱ");
							return;
						}
						if("fail001" == data){
							flag  =  false; 
							$("#zhanghmsg").text("���˻���ͨ�����ò������ڵ�ǰ����������ӡ");
							return;
						}else if("fail002" == data){
							flag  =  false;
							$("#zhanghmsg").text("�˻�������");
							return;
						}else if("fail003" == data){
							flag  =  false;
							$("#zhanghmsg").text("��ѯ�˻�ʧ��");
							return;
						}else{
							var accountinfo = data.split(",");
							$("#zhangh").val(accountinfo[6]);
							if(accountinfo[0]==""){
								flag  =  false; 
								$("#zhanghmsg").text("�˻�������");
								return;
							}else{
								$("#hum").text(accountinfo[0]);
							}
							if(accountinfo[1]=="����"){
								flag =  false; 
								$("#zhanghmsg").text("���˻�״̬���Ϸ����޷���ӡ");
								return;
							}
							if(accountinfo[4]!="1"){
								flag =  false; 
								$("#zhanghmsg").text("���˻���������Ч������ӡ�����޷���ӡ");
								return;
							}	
			//				if(accountinfo[3]=="��"){
			//					$("#jinetr").show();
			//					$("#zhanghmsg").text("��ע�⣺���˻�������ϣ��������������Ϸ�Χ�ڣ�����Ӱ����ӡ����");
			//				}else{
			//					$("#jinetr").hide();
			//				}
			
							if(accountinfo[7]=="1"){
								$("#jinetr").show();
							}else{
								$("#jinetr").hide();
							}
							
							$("#accorgno").val(accountinfo[5]);
							$("#beiz").html(accountinfo[8]);
						}
	
					}else{
						$("#zhanghmsg").text("��ѯʧ��");
						flag  =  false;	
					}
				}});
				
			}else{
				flag  =  false;
			};
			if(flag==true){
				return true;
			}else{
				return false;
			}
			
			
			}
			
			
			
			function verifyAll(){
				var pinglx = $("#pingzlx").select().val();
				var cjlx = $("#cjlx").select().val();
				var jine ;
				if($("#jinetr").css("display")=="none"){
						jine = 0;
				}else{
						jine = 1;
				}
				
				if(cjlx!=6){
					if(pinglx=="Y900"){
						if(jine == 1){
							if(requestForZhangh()&& volidate(4,'chuprq','��Ʊ����')&&volidate(3,'jine','���')&&volidate(0,'yinjtx','ƾ֤ͼ��')){
								return true;
							}
						}else if(jine == 0){
							if(requestForZhangh()&& volidate(4,'chuprq','��Ʊ����')&&volidate(0,'yinjtx','ƾ֤ͼ��')){
								return true;
							}
						}
					}else{
						if(jine == 1){
							if(requestForZhangh()&&volidate(2,'pingzh','ƾ֤��')&&volidate(4,'chuprq','��Ʊ����')&&volidate(3,'jine','���')&&volidate(0,'yinjtx','ƾ֤ͼ��')){
								return true;
							}
						}else if(jine == 0){
							if(requestForZhangh()&&volidate(2,'pingzh','ƾ֤��')&&volidate(4,'chuprq','��Ʊ����')&&volidate(0,'yinjtx','ƾ֤ͼ��')){
								return true;
							}
						}
					}
				}else{
					if(pinglx=="Y900"){
						if(jine == 1){
							if(requestForZhangh()&&volidate(4,'chuprq','��Ʊ����')&&volidate(3,'jine','���')){
								return true;
							}
						}else if(jine == 0){
							if(requestForZhangh()&&volidate(4,'chuprq','��Ʊ����')){
								return true;
							}
						}
					}else{
						if(jine == 1){
							if(requestForZhangh()&&volidate(2,'pingzh','ƾ֤��')&&volidate(4,'chuprq','��Ʊ����')&&volidate(3,'jine','���')){
								return true;
							}
						}else if(jine == 0){
							if(requestForZhangh()&&volidate(2,'pingzh','ƾ֤��')&&volidate(4,'chuprq','��Ʊ����')){
								return true;
							}
						}
					}
				}
				return false;
			}

			function qiantyy(){
				if(verifyAll()){
					var checktype = $("#yanylx").val();
					var tellno = '${clerk.code}';
					var tellorgno = '${clerk.orgcode}';
					var accno = $("#zhangh").val();
					var accorgno = $("#accorgno").val();
					var billdate = $("#chuprq").val();
					var billtype = $("#pingzlx").val();
					billno =  $.trim($("#pingzh").val());
				//	var billno = "0";
				//	if(billtype!="Y900"){
				//		billno =  $("#pingzh").val();
				//	}
					if(billno==""){
						billno = "0000000000";
					}
					
					var yyserviceurl = '${yyserviceurl}';
					var amount;
					if($("#jinetr").css("display")=="none"){
						amount = 0;
					}else{
						amount = ($("#jine").val())*100;
					}
					if($("#yanylx").val()!="5"){
						amount = 0;
					}
					var imagename = $("#yinjtx").val();
					imagename = imagename.replace(/\\/ig,"/");
					var cjlx = $("#cjlx").select().val();
					
					var scanmode;
					var scantype;
					var dpi;
					var scansealmode;
					
					//��ͨ
					if(cjlx==2){
						scanmode = 1;
						scantype = 3;
						scansealmode = 1;
						dpi = 200;
					//ƱӰ
					}else if(cjlx==4){
						scanmode = 1;
						scantype = 3;
						scansealmode = 1;
						dpi = 300;
					//ƽ̨
					}else if(cjlx==6){
						scanmode = 0;
						//scantype = 2;
						scantype = 6;
						scansealmode = 0;
						imagename = " ";
						dpi = 300;
					//����
					}else if(cjlx==5){
						scanmode = 1;
						scantype = 3;
						scansealmode = 1;
					//	dpi = 240;
						dpi = 200;
					}
					var str ="{'SEALAPPURL':'http://"+yyserviceurl+"','SYSCODE':'YY','TASKID':'0','TASKTYPE':'0','CHECKID':'','NODECODE':'FSSYW','CHECKTYPE':'"+checktype+"','TELLNO':'"+tellno+"','TELLORGNO':'"+tellorgno+"','ACCNO':'"+accno+"','ACCORGNO':'"+accorgno+"','BILLDATE':'"+billdate+"','BILLTYPE':'"+billtype+"','BILLNO':'"+billno+"','AMOUNT':'"+amount+"','AUTOCHECK':'2','IMAGENAME':'"+imagename+"','DPI':'"+dpi+"','SCANMODE':'"+scanmode+"','SCANTYPE':'"+scantype+"','SCANSEALMODE':'"+scansealmode+"'}";
				//	return false;
					sealCheckocx.SetIsUseBeforeSaveEvent(true);
					ret = sealCheckocx.Check(str);
					var retObj = eval('('+ret+')');
					var result = retObj.RETCODE;
					
					if("0000"==retObj.RETCODE){
						$("#zhanghmsg").text("");
						$("#pingzhmsg").text("");
						$("#qiyrqmsg").text("");
						$("#jinemsg").text("");
						$("#yinjtxmsg").text("");
						if("0"==retObj.BILLRESULT){
							$("#result").text("��ͨ����");
						}else if("1"==retObj.BILLRESULT){
							$("#result").text("����ͨ����");
						}else if("2"==retObj.BILLRESULT){
							$("#result").text("��δ�顿");
						}
						
					}else{
						if(retObj.RETINFO!=null){
							alert(retObj.RETINFO);
						}
						$("#result").text("����ӡʧ�ܡ�");
					}
				}
			}
			
			
			
			function cj(){
				var cjlx = $("#cjlx").select().val();
				//������
				if(cjlx==1){
					cjtx(1,300);
				//ɨ����
				}else if(cjlx==2){
					cjtx(16,200);
				//ƱӰ
				}else if(cjlx==4){
					cjtx(256,300);
				//����
				}else if(cjlx==5){
					localtx();
				//ƽ̨
				}else if(cjlx==6){
					qiantyy();
				}
			}
			
			
			function localtx(){
				var alltx = CapImageOcx.LoadLocalImage();
				if(alltx!=""&&alltx.length!=0){
					var txsz = alltx.split("|");
					if(txsz.length>1){
						alert("���ֻ�������1�ű���Ӱ��");
					}else{
						$("#yinjtx").val(alltx);
						alltx = alltx.replace(/\|/ig,"\n\r");
						$("#txtext").val(alltx);
					}
				}
			}
			
			


			function cjtx(devtype,dpi){
				var reqjson = '{"DevType":"'+devtype+'","IsMulty":"0","DPI":"'+dpi+'"}';
				var retObj = sealCheckocx.ScanImages(reqjson);
				retObj  = retObj.replace(/\\/ig,"\\\\");
				var retJson = eval('('+retObj+')');
				
				if(retJson.RETCODE=="0000"){
					var alltx = retJson.RETINFO;
					if(alltx!=""&&alltx.length!=0){
						var txsz = alltx.split("|");
						if(txsz.length>1){
							alert("���ֻ����ɼ�1��Ӱ��");
						}else{
							$("#yinjtx").val(alltx);
							alltx = alltx.replace(/\|/ig,"\n\r");
							$("#txtext").val(alltx);
						}
					}
				}else{
					alert("���òɼ��ؼ�ʧ�ܣ�ԭ��"+retJson.RETINFO);
				}
				
			}

			function resetPage(){
				window.location.href="yinjManage.do?method=forqiantyy";
			}
			
			function last(){
				var e = event.srcElement;
				var r = e.createTextRange();
				r.moveStart("character",e.value.length);
				r.collapse(true);
				r.select();
			}
			
			
				function findcp(obj){
				var rngSel = document.selection.createRange();
				var rngTxt = obj.createTextRange();
				var flag = rngSel.getBookmark();
				rngTxt.collapse();
				rngTxt.moveToBookmark(flag);
				rngTxt.moveStart('character',-obj.value.length);
				str = rngTxt.text.replace(/\r\n/g,'');
				return (str.length);
			
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
					ӡ������-ǰ̨��ӡ
				</td>
			</tr>

		</table>

		<form action="forqiantyy.do" method="post" name="form1" id="form1">
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="up-left"></td>
					<td class="up-middle"></td>
					<td class="up-right"></td>
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
										ǰ̨��ӡ
									</th>
								</tr>
							</thead>
							<tr>
								<td align="right">
								</td>
							</tr>
							<tr>
								<td width="45%" class="class1_td  alignright">
									�˺ţ�
								</td>
								<td width="55%" class="class1_td alignleft">
									<input name="zhangh" id="zhangh"
										class="inputField required zhangh" maxlength="22"
										onkeydown=" if(event.keyCode==13){if(!requestForZhangh()){this.focus();return false;}}if(event.keyCode==9){if(!requestForZhangh()){this.focus();return false;}}"
										onblur="requestForZhangh()" style="width: 160px;" />
									<span id="zhanghmsg" style="color: red"> </span>
									<input type="hidden" id="accorgno" name="accorgno">
								</td>
							</tr>
							<tr>
								<td width="45%" class="class1_td alignright">
									������
								</td>
								<td id="hum" width="55%" class="class1_td alignleft">&nbsp;
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									��ע:
								</td>
								<td class="class1_td alignleft">
									<span id="beiz"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td  alignright">
									ƾ֤�ţ�
								</td>
								<td class="class1_td alignleft">
									<input name="pingzh" id="pingzh"
										class="inputField required pingzh" maxlength="10"
										 style="width: 160px;" />
									<span id="pingzhmsg" style="color: red"> </span>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									ƾ֤���ͣ�
								</td>
								<td class="class1_td alignleft">
									<select name="pingzlx" id="pingzlx"
										class="select required yanylx">

										<c:forEach items="${pingzlxlist}" var="itt">
										<c:if test="${itt.shifxs eq '��'}">
											<option value="${itt.pingzbs}">
												${itt.pingzbs}${itt.pingzmc}
											</option>
											</c:if>
										</c:forEach>
									</select>

								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									��Ʊ����:
								</td>
								<td class="class1_td alignleft">
									<input name="chuprq" id="chuprq"
										class="inputField required chuprq" onfocus="WdatePicker()" onclick="WdatePicker()"
										onblur="volidate(4,'chuprq','��Ʊ����')" style="width: 160px;" />
									<span id="chuprqmsg" style="color: red"> </span>
								</td>
							</tr>


							<tr id="jinetr" style="display: none">
								<td class="class1_td alignright" id="jinetr">
									��
								</td>
								<td class="class1_td alignleft">
									<input name="jine" id="jine" class="inputField required jine"
										type="text"
										onkeydown="if(event.keyCode==48|event.keyCode==96){var reg =/^([0-9]+)$/;if(this.value.indexOf('0')=='0'){return false;}if(reg.test($('#jine').val())){if(findcp(this)==0)return false;}}if(event.keyCode==110|event.keyCode==190)return false;if(event.keyCode==13){if(!volidate(3,'jine','���')){this.focus();return false;}}if(event.keyCode==9){if(!volidate(3,'jine','���')){this.focus();return false;}}if((event.keyCode>96&event.keyCode<106)|(event.keyCode>48&event.keyCode<58)){if(this.value.indexOf('0')=='0'){$('#jine').val($('#jine').val().substr(1));}}"
										onblur="volidate(3,'jine','���')" 
										onfocus='if(this.value.length!=0){var reg = /^([0-9]+\.[0-9]{1,2})$/;if(reg.test(this.value)&&this.value.length<5){$("#jine").val($("#jine").val()*100);}else{$("#jine").val($("#jine").val().replace(/\./ig,""));}last();}'
										value=""
										style="width: 160px;" maxlength="14"/>
									<span id="jinemsg" style="color: red"> </span>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									��ӡ���ͣ�
								</td>
								<td class="class1_td alignleft">
									<select name="yanylx" id="yanylx"
										class="select required yanylx">
										<!--<option value="1">ȫ��</option> 
										  	<option value="2">�ǹ���</option>-->
										
										<!--	<option value="4">����</option>-->
										<option value="5">
											Ԥ��ӡ����
										</option>
										<option value="3">
											����
										</option>
										<option value="6">
											���»������
										</option>
										<option value="7">
											���ºͷ�����
										</option>
										<option value="0">����ƾ֤����</option> 
										<!--<option value="0">����ƾ֤����</option> -->
									</select>

								</td>
							</tr>

							<tr>
								<td class="class1_td alignright">
									�ɼ����ͣ�
								</td>
								<td class="class1_td alignleft">
									<select name="cjlx" id="cjlx" class="select required cjlx">
										<option value="2">
											ɨ���ǲɼ�
										</option>
										<option value="4">
											ƱӰ�ɼ�
										</option>
										<option value="6">
											ƽ̨�ɼ�
										</option>
										<option value="5">
											����
										</option>
									</select>
									<span id="cjlxmsg" style="color: red">��ѡ��ƽ̨�ɼ��������ɼ���ֱ�ӽ�����ӡ����</span>
								</td>
							</tr>

							<tr>
								<td class="class1_td alignright">
									ƾ֤ͼ��
								</td>
								<td class="class1_td alignleft">
									<input type="hidden" id="yinjtx" />
									<input type="button" value="�ɼ�" onclick="cj()" />
									<span id="yinjtxmsg" style="color: red"> </span>
								</td>
							</tr>
							
							
							<tr style="display: none">
								<td class="class1_td alignright"></td>
								<td class="class1_td alignleft">
									<textarea id="txtext" rows="3" cols="85" readonly="readonly"></textarea>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									��ӡ���:
								</td>
								<td class="class1_td alignleft">
									<b id="result" style="color: red">&nbsp;</b>
								</td>
							</tr>


						</table>
					</td>
					<td background="images/table_arrow_07.gif">
						&nbsp;
					</td>

				</tr>
				<tr>
					<td class="bottom-left" /></td>
					<td class="bottom-middle" /></td>
					<td class="bottom-right" /></td>
				</tr>
			</table>
			<div class="funbutton">
				<button type="button" style="width: 60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="qiantyy()" onkeydown="if(event.keyCode==13){this.click()}">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					��ӡ
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type = "button" style="width: 60px"
					onmouseover="this.className='buttom2'" onclick="resetPage();"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					ȡ��
				</button>
			</div>
		</form>
	<script language="JavaScript" FOR="sealCheckocx" EVENT="BeforeSaveEvent(sender,e)">
			var temp = eval('('+e.jsontempResultout+')');
			if(temp.CHECKMODE==2||temp.CHECKMODE==3){
				   var tisxx = "";
				   if(temp.CHECKMODE==2){
						 tisxx = "�˹�";
				   }else if(temp.CHECKMODE==3){
				   		 tisxx = "ʵ��";
				   }
			//	   alert("��"+tisxx+"��ӡ��Ҫ����10����Ա����");
				   var obj = new Object();
				   if(temp.CHECKMODE==2){
				   	 obj.titleName = "�˹���ӡ";
				   }else if(temp.CHECKMODE==3){
				   	 obj.titleName = "ʵ����ӡ";
				   }else{
				   	 obj.titleName = "��ӡ";
				   }
				   obj.account = $("#zhangh").val();
				   obj.checktype="��Ȩ";
				   if(temp.CHECKMODE==2){
				   		 obj.quanxbs="20000|RG_001";
				   }else if(temp.CHECKMODE==3){
				   		 obj.quanxbs="20000|SW_001";
				   }
				   
				   var backNum  = window.showModalDialog('authorized.jsp',obj,"dialogWidth=10px;dialogHeight=10px;status=no;scroll=no"); 
				   if(typeof backNum!="undefined"){
					   if(backNum.substr(0,1) =='1')
					   {	
					   		var clerkid2 =  backNum.substr(2);
							e.jsonReturn = "{\"RETCODE\":\"0000\",\"RETINFO\":\"000000000\",\"DOUBLESIGNATURECLERKNUM\":\""+clerkid2+"\"}";
					   }else{
					   		e.jsonReturn = "{\"RETCODE\":\"1111\",\"RETINFO\":\"��Ȩʧ��\"}";
					   }
				   }else{
					   		e.jsonReturn = "{\"RETCODE\":\"1111\",\"RETINFO\":\"��Ȩʧ��\"}";
				   }
				   
				  

			}else{
				e.jsonReturn = "{\"RETCODE\":\"0000\",\"RETINFO\":\"000000000\"}";
			}
	
	</script>
		
		<object width="0" height="0" id="sealCheckocx"
			classid="clsid:9d2ca12e-a504-4574-a9d4-0940b371c0a4"></object>
		<OBJECT ID="CapImageOcx" WIDTH="1" HEIGHT="1"
			CLASSID="CLSID:D0911113-0014-4CB1-AD13-9581CC656507"></OBJECT>
	</body>
</html>
