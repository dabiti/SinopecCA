<%@page language="java" contentType="text/html;charset=GBK"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
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
			//账号
			if(1==key){
				reg =   /(^[0-9]{9}$)|(^[0-9]{17}$)|(^[0-9]{22}$)|(^[0-9]{10,13}$)/;
			}
			//凭证号
			if(2==key){
				reg = /^[0-9]{1,10}$/;
			}
			//金额
			if(3==key){
			//	reg = /^\d+$/;
				reg = /^(([0-9]+)|([0-9]+\.[0-9]{1,2}))$/;
			}
			//出票日期
			if(4==key){
				reg = /^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/;
			}
			if($("#"+id).val().length!=0&&$("#"+id).val()!=''){
				if(reg!=null&&!reg.test($("#"+id).val())){
					$("#"+id+"msg").text("* "+name+"格式不正确");
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
						$("#"+id+"msg").text("* "+name+"不能超过当日");
						return false; 
					}
				}
			}else{
				$("#"+id+"msg").text("* "+name+"不可为空");
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
			
			if(volidate(1,'zhangh','账号')){
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
							$("#zhanghmsg").text("登陆已超时");
							return;
						}
						if("fail001" == data){
							flag  =  false; 
							$("#zhanghmsg").text("该账户的通兑设置不允许在当前机构进行验印");
							return;
						}else if("fail002" == data){
							flag  =  false;
							$("#zhanghmsg").text("账户不存在");
							return;
						}else if("fail003" == data){
							flag  =  false;
							$("#zhanghmsg").text("查询账户失败");
							return;
						}else{
							var accountinfo = data.split(",");
							$("#zhangh").val(accountinfo[6]);
							if(accountinfo[0]==""){
								flag  =  false; 
								$("#zhanghmsg").text("账户不存在");
								return;
							}else{
								$("#hum").text(accountinfo[0]);
							}
							if(accountinfo[1]=="销户"){
								flag =  false; 
								$("#zhanghmsg").text("该账户状态不合法，无法验印");
								return;
							}
							if(accountinfo[4]!="1"){
								flag =  false; 
								$("#zhanghmsg").text("该账户不存在有效的已审印鉴，无法验印");
								return;
							}	
			//				if(accountinfo[3]=="有"){
			//					$("#jinetr").show();
			//					$("#zhanghmsg").text("请注意：该账户存在组合，若金额数额在组合范围内，将会影响验印规则");
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
						$("#zhanghmsg").text("查询失败");
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
							if(requestForZhangh()&& volidate(4,'chuprq','出票日期')&&volidate(3,'jine','金额')&&volidate(0,'yinjtx','凭证图像')){
								return true;
							}
						}else if(jine == 0){
							if(requestForZhangh()&& volidate(4,'chuprq','出票日期')&&volidate(0,'yinjtx','凭证图像')){
								return true;
							}
						}
					}else{
						if(jine == 1){
							if(requestForZhangh()&&volidate(2,'pingzh','凭证号')&&volidate(4,'chuprq','出票日期')&&volidate(3,'jine','金额')&&volidate(0,'yinjtx','凭证图像')){
								return true;
							}
						}else if(jine == 0){
							if(requestForZhangh()&&volidate(2,'pingzh','凭证号')&&volidate(4,'chuprq','出票日期')&&volidate(0,'yinjtx','凭证图像')){
								return true;
							}
						}
					}
				}else{
					if(pinglx=="Y900"){
						if(jine == 1){
							if(requestForZhangh()&&volidate(4,'chuprq','出票日期')&&volidate(3,'jine','金额')){
								return true;
							}
						}else if(jine == 0){
							if(requestForZhangh()&&volidate(4,'chuprq','出票日期')){
								return true;
							}
						}
					}else{
						if(jine == 1){
							if(requestForZhangh()&&volidate(2,'pingzh','凭证号')&&volidate(4,'chuprq','出票日期')&&volidate(3,'jine','金额')){
								return true;
							}
						}else if(jine == 0){
							if(requestForZhangh()&&volidate(2,'pingzh','凭证号')&&volidate(4,'chuprq','出票日期')){
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
					
					//普通
					if(cjlx==2){
						scanmode = 1;
						scantype = 3;
						scansealmode = 1;
						dpi = 200;
					//票影
					}else if(cjlx==4){
						scanmode = 1;
						scantype = 3;
						scansealmode = 1;
						dpi = 300;
					//平台
					}else if(cjlx==6){
						scanmode = 0;
						//scantype = 2;
						scantype = 6;
						scansealmode = 0;
						imagename = " ";
						dpi = 300;
					//本地
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
							$("#result").text("【通过】");
						}else if("1"==retObj.BILLRESULT){
							$("#result").text("【不通过】");
						}else if("2"==retObj.BILLRESULT){
							$("#result").text("【未验】");
						}
						
					}else{
						if(retObj.RETINFO!=null){
							alert(retObj.RETINFO);
						}
						$("#result").text("【验印失败】");
					}
				}
			}
			
			
			
			function cj(){
				var cjlx = $("#cjlx").select().val();
				//高拍仪
				if(cjlx==1){
					cjtx(1,300);
				//扫描仪
				}else if(cjlx==2){
					cjtx(16,200);
				//票影
				}else if(cjlx==4){
					cjtx(256,300);
				//本地
				}else if(cjlx==5){
					localtx();
				//平台
				}else if(cjlx==6){
					qiantyy();
				}
			}
			
			
			function localtx(){
				var alltx = CapImageOcx.LoadLocalImage();
				if(alltx!=""&&alltx.length!=0){
					var txsz = alltx.split("|");
					if(txsz.length>1){
						alert("最多只允许加载1张本地影像");
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
							alert("最多只允许采集1张影像");
						}else{
							$("#yinjtx").val(alltx);
							alltx = alltx.replace(/\|/ig,"\n\r");
							$("#txtext").val(alltx);
						}
					}
				}else{
					alert("调用采集控件失败，原因："+retJson.RETINFO);
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
					印鉴管理-前台验印
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
										前台验印
									</th>
								</tr>
							</thead>
							<tr>
								<td align="right">
								</td>
							</tr>
							<tr>
								<td width="45%" class="class1_td  alignright">
									账号：
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
									户名：
								</td>
								<td id="hum" width="55%" class="class1_td alignleft">&nbsp;
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									备注:
								</td>
								<td class="class1_td alignleft">
									<span id="beiz"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td  alignright">
									凭证号：
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
									凭证类型：
								</td>
								<td class="class1_td alignleft">
									<select name="pingzlx" id="pingzlx"
										class="select required yanylx">

										<c:forEach items="${pingzlxlist}" var="itt">
										<c:if test="${itt.shifxs eq '是'}">
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
									出票日期:
								</td>
								<td class="class1_td alignleft">
									<input name="chuprq" id="chuprq"
										class="inputField required chuprq" onfocus="WdatePicker()" onclick="WdatePicker()"
										onblur="volidate(4,'chuprq','出票日期')" style="width: 160px;" />
									<span id="chuprqmsg" style="color: red"> </span>
								</td>
							</tr>


							<tr id="jinetr" style="display: none">
								<td class="class1_td alignright" id="jinetr">
									金额：
								</td>
								<td class="class1_td alignleft">
									<input name="jine" id="jine" class="inputField required jine"
										type="text"
										onkeydown="if(event.keyCode==48|event.keyCode==96){var reg =/^([0-9]+)$/;if(this.value.indexOf('0')=='0'){return false;}if(reg.test($('#jine').val())){if(findcp(this)==0)return false;}}if(event.keyCode==110|event.keyCode==190)return false;if(event.keyCode==13){if(!volidate(3,'jine','金额')){this.focus();return false;}}if(event.keyCode==9){if(!volidate(3,'jine','金额')){this.focus();return false;}}if((event.keyCode>96&event.keyCode<106)|(event.keyCode>48&event.keyCode<58)){if(this.value.indexOf('0')=='0'){$('#jine').val($('#jine').val().substr(1));}}"
										onblur="volidate(3,'jine','金额')" 
										onfocus='if(this.value.length!=0){var reg = /^([0-9]+\.[0-9]{1,2})$/;if(reg.test(this.value)&&this.value.length<5){$("#jine").val($("#jine").val()*100);}else{$("#jine").val($("#jine").val().replace(/\./ig,""));}last();}'
										value=""
										style="width: 160px;" maxlength="14"/>
									<span id="jinemsg" style="color: red"> </span>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									验印类型：
								</td>
								<td class="class1_td alignleft">
									<select name="yanylx" id="yanylx"
										class="select required yanylx">
										<!--<option value="1">全部</option> 
										  	<option value="2">非公章</option>-->
										
										<!--	<option value="4">或公章</option>-->
										<option value="5">
											预留印鉴章
										</option>
										<option value="3">
											公章
										</option>
										<option value="6">
											公章或财务章
										</option>
										<option value="7">
											公章和法人章
										</option>
										<option value="0">根据凭证种类</option> 
										<!--<option value="0">根据凭证种类</option> -->
									</select>

								</td>
							</tr>

							<tr>
								<td class="class1_td alignright">
									采集类型：
								</td>
								<td class="class1_td alignleft">
									<select name="cjlx" id="cjlx" class="select required cjlx">
										<option value="2">
											扫描仪采集
										</option>
										<option value="4">
											票影采集
										</option>
										<option value="6">
											平台采集
										</option>
										<option value="5">
											本地
										</option>
									</select>
									<span id="cjlxmsg" style="color: red">若选择平台采集，则点击采集后将直接进入验印界面</span>
								</td>
							</tr>

							<tr>
								<td class="class1_td alignright">
									凭证图像：
								</td>
								<td class="class1_td alignleft">
									<input type="hidden" id="yinjtx" />
									<input type="button" value="采集" onclick="cj()" />
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
									验印结果:
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
					验印
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type = "button" style="width: 60px"
					onmouseover="this.className='buttom2'" onclick="resetPage();"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					取消
				</button>
			</div>
		</form>
	<script language="JavaScript" FOR="sealCheckocx" EVENT="BeforeSaveEvent(sender,e)">
			var temp = eval('('+e.jsontempResultout+')');
			if(temp.CHECKMODE==2||temp.CHECKMODE==3){
				   var tisxx = "";
				   if(temp.CHECKMODE==2){
						 tisxx = "人工";
				   }else if(temp.CHECKMODE==3){
				   		 tisxx = "实物";
				   }
			//	   alert("该"+tisxx+"验印需要更换10级柜员复核");
				   var obj = new Object();
				   if(temp.CHECKMODE==2){
				   	 obj.titleName = "人工验印";
				   }else if(temp.CHECKMODE==3){
				   	 obj.titleName = "实物验印";
				   }else{
				   	 obj.titleName = "验印";
				   }
				   obj.account = $("#zhangh").val();
				   obj.checktype="授权";
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
					   		e.jsonReturn = "{\"RETCODE\":\"1111\",\"RETINFO\":\"授权失败\"}";
					   }
				   }else{
					   		e.jsonReturn = "{\"RETCODE\":\"1111\",\"RETINFO\":\"授权失败\"}";
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
