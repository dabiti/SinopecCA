<%@page language="java" contentType="text/html;charset=GBK" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/shield.js"></script>	
		<script src='js/date/WdatePicker.js' type='text/javascript'></script>
		<script type="text/javascript">
		$(function(){
			$("#zhangh").focus();
			});
		
		function volidate(key,id,name){
			var reg;
			//账号
			if(1==key){
				reg =   /(^[0-9]{9}$)|(^[0-9]{17}$)|(^[0-9]{22}$)|(^[0-9]{10,13}$)/;
			}
			//凭证号
			if(2==key){
				reg = /^[0-9]{10}$/;
			}
			//金额
			if(3==key){
				reg = /^\d+$/;
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
			$("#yinjtxmsg").text("");
			$("#hum").html("");
			$("#result").html("");
			if(volidate(1,'zhangh','账号')){
				var account = $("#zhangh").val();
				$.ajax({
					type:"post",
					async:false,
					cache:false,
					url:"accountinfo.do?method=getAccountForAjax",
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
							$("#zhanghmsg").text("对该账号没有操作权限");
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
							$("#zhangh").val(accountinfo[30]);
							if(accountinfo[0]==""){
								flag  =  false; 
								$("#zhanghmsg").text("账户不存在");
								return;
							}else{
								$("#hum").text(accountinfo[0]);
							}
							if(accountinfo[14]=="销户"){
								flag =  false; 
								$("#zhanghmsg").text("该账户状态不合法，无法进行审核");
								return;
							}
							if(accountinfo[15]=="已审"){
								flag =  false; 
								$("#zhanghmsg").text("该账户已审核通过，无需再进行审核");
								return;
							}
							if(accountinfo[10]=="1"){
								flag =  false; 
								$("#zhanghmsg").text("该账户公用其他账户印鉴，无法进行审核");
								return;
							}	
							if(accountinfo[19]=="无"){
								flag =  false; 
								$("#zhanghmsg").text("该账户不存在印鉴，请先建库");
								return;
								}
							var clerkcode='${clerk.code}';
							if(accountinfo[22]==clerkcode){
								flag =  false; 
								$("#zhanghmsg").text("审核柜员与建库柜员不能为同一人");
								return;
								}
							if(accountinfo[16]=="有"){
								$("#zhanghmsg").text("请注意：该账户存在组合");
							//	$("#jinetr").show();
							}else{
							//	$("#jinetr").hide();
							}
							$("#accorgno").val(accountinfo[18]);
						}
	
					}else{
						$("#zhanghmsg").text("查询失败");
						flag  =  false;	
					}
				}});

				//若无已审印鉴，则TASKDATE为当前日期，若有，则为开户日期（统一都传开户日期）
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
				var cjlx = $("#cjlx").select().val();
				if(cjlx!=6){
					if(requestForZhangh()&&volidate(0,'yinjtx','印鉴图像')){
						return true;
					}
				}else{
					if(requestForZhangh()){
						return true;
					}
				}
				return false;
			}
		


		function yinjgl(){
			if(verifyAll()){
				var tellno = '${clerk.code}';
				var tellorgno = '${clerk.orgcode}';
				var accno = $("#zhangh").val();
				var accorgno = $("#accorgno").val();
				var imagename = $("#yinjtx").val();
				imagename = imagename.replace(/\\/ig,"/");
				var yyserviceurl = '${yyserviceurl}';	
				
				
				var cjlx = $("#cjlx").select().val();
				var scanmode;
				var scantype;
				var scansealmode;
				var dpi;
				
				//扫描仪
				if(cjlx==1){
					scanmode = 1;
					scantype = 3;
					scansealmode = 1;
					dpi = 300;
				//扫描仪
				}else if(cjlx==2){
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
				//本地
				}else if(cjlx==5){
					scanmode = 1;
					scantype = 3;
					scansealmode = 1;
					dpi = 200;
				//平台
				}else if(cjlx==6){
					scanmode = 0;
					scantype = 6;
					scansealmode = 0;
					imagename = " ";
					dpi = 300;
				}
				
				var str ="{'SEALAPPURL':'http://"+yyserviceurl+"','ACCNO':'"+accno+"','MANAGETYPE':'2','ACCORGNO':'"+accorgno+"','TELLNO':'"+tellno+"','TELLORGNO':'"+tellorgno+"','CHECKTYPE':'1','IMAGENAME':'"+imagename+"','DPI':'"+dpi+"','SCANMODE':'"+scanmode+"','SCANTYPE':'"+scantype+"','SCANSEALMODE':'"+scansealmode+"'}";
				ret = AccountManageCom.InterfaceAccInfoCheck(str);
				var retObj = eval('('+ret+')');
				var result = retObj.RETCODE;
				
				if("0000"==retObj.RETCODE){
					$("#zhanghmsg").text("");
					$("#pingzhmsg").text("");
					$("#qiyrqmsg").text("");
					$("#jinemsg").text("");
					$("#yinjtxmsg").text("");
					if("0"==retObj.BILLRESULT){
						$("#result").text("【审核通过】");
					}else if("1"==retObj.BILLRESULT){
						$("#result").text("【审核不通过】");
					}else if("2"==retObj.BILLRESULT){
						$("#result").text("【未审核】");
					}
					
				}else{
					if(retObj.RETINFO!=null){
					alert(retObj.RETINFO);
					}
					$("#result").text("【审核失败】");
				}
			}
			
			}
			
			
			function localtx(){
				var alltx = CapImageOcx.LoadLocalImage();
				if(alltx!=""&&alltx.length!=0){
					var txsz = alltx.split("|");
					if(txsz.length>4){
						alert("最多只允许加载4张本地影像");
					}else{
						$("#yinjtx").val(alltx);
						alltx = alltx.replace(/\|/ig,"\n\r");
						$("#txtext").val(alltx);
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
					yinjgl();
				}
			}
			
			function cjtx(devtype,dpi){
				var reqjson = '{"DevType":"'+devtype+'","IsMulty":"1","DPI":"'+dpi+'"}';
				var retObj = AccountManageCom.ScanImages(reqjson);
				retObj  = retObj.replace(/\\/ig,"\\\\");
				var retJson = eval('('+retObj+')');
				
				if(retJson.RETCODE=="0000"){
					var alltx = retJson.RETINFO;
					if(alltx!=""&&alltx.length!=0){
						var txsz = alltx.split("|");
						if(txsz.length>4){
							alert("最多只允许采集4张影像");
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
				window.location.href="yinjManage.do?method=foryinjsh";
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
					印鉴管理-印鉴审核
				</td>
			</tr>
			
		</table>
		
		<form action="foryinjgl.do" method="post" name="form1" id="form1">
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="up-left"></td><td class="up-middle"></td><td class="up-right"></td>
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
										印鉴审核
									</th>
								</tr>
							</thead>
							<tr>
								<td align="right">
								</td>
							</tr>
							<tr>
								<td  class="class2_td  alignright">
									账号：
									<%String zhangh=request.getParameter("zhangh");
									zhangh=zhangh==null?"":zhangh;
									%>
								</td>
								<td   class="class2_td alignleft ">
									<input name="zhangh" id="zhangh" 
										class="inputField required zhangh" maxlength="22" onkeydown=" if(event.keyCode==13){if(!requestForZhangh()){this.focus();return false;}}if(event.keyCode==9){if(!requestForZhangh()){this.focus();return false;}}" 
										 onblur="requestForZhangh()" style="width: 160px;" value="<%=zhangh %>"/>
									<span id="zhanghmsg" style="color:red"></span>
									<input type="hidden" id="accorgno" name="accorgno">
									<input type="hidden" id="taskdate" name="taskdate">
								</td>
							</tr>
							<tr>
								<td width="45%" class="class2_td  alignright">
									户名：
								</td>
								<td  id="hum" width="55%" class="class2_td alignleft">
								</td>
							</tr>
							
							
							<tr>
								<td class="class1_td alignright">
									采集类型：
								</td>
								<td class="class1_td alignleft">
									<select name="cjlx" id="cjlx" class="select required cjlx">
									<!-- <option value="1">
											高拍仪采集
										</option> -->	
										<option value="2">
											扫描仪采集
										</option>
									<!--	<option value="4">
											票影采集
										</option>  -->
									<option value="5">
											本地
										</option> 	
										<option value="6">
											平台采集
										</option>
										
									</select>
									<span id="cjlxmsg" style="color: red">若选择平台采集，则点击采集后将直接进入审核界面</span>
								</td>
							</tr>
							
							
 							<tr>
								<td class="class1_td alignright">
									印鉴卡图像：
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
 									<textarea id="txtext" rows="8" cols="85" readonly="readonly"></textarea>
 								</td>
 							</tr>
 							<tr>
 								<td class="class1_td alignright">操作结果:</td>
 								<td class="class1_td alignleft"><b  id="result" style="color: red"></b></td>
 							</tr>
					</table>	
					</td>
					<td background="images/table_arrow_07.gif">
						&nbsp;
					</td>
					
				</tr>
				<tr>
					<td class="bottom-left"/>
					<td class="bottom-middle"/>
					<td class="bottom-right"/>
				</tr>
			</table>
			<div class="funbutton">
				<button type="button" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1" onclick = "yinjgl()">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					审核
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width:60px" 
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
				   var obj = new Object();
				   if(temp.CHECKMODE==2){
				   	 obj.titleName = "人工审核";
				   }else if(temp.CHECKMODE==3){
				   	 obj.titleName = "实物审核";
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
				   
				   var backNum = window.showModalDialog('authorized.jsp',obj,"dialogWidth=10px;dialogHeight=10px;status=no;scroll=no"); 
				   if(typeof backNum!="undefined"){
				   if(backNum=='1')
				   {	
						e.jsonReturn = "{\"RETCODE\":\"0000\",\"RETINFO\":\"授权失败\"}";
				   }else{
				   		e.jsonReturn = "{\"RETCODE\":\"1111\",\"RETINFO\":\"授权失败\"}";
				   }
				   
				   }else{
					   		e.jsonReturn = "{\"RETCODE\":\"1111\",\"RETINFO\":\"授权失败\"}";
				   }
			}else{
					e.jsonReturn = "{\"RETCODE\":\"0000\",\"RETINFO\":\"授权失败\"}";
			}
	
	</script>
	<object id="AccountManageCom" align = "middle" hspace=0 vspace =0 height=0 width = 0
   classid="clsid:9d2ca12e-a504-4574-a9d4-0940b37abbb2"> </object>
   <OBJECT ID="CapImageOcx" WIDTH="1" HEIGHT="1" CLASSID="CLSID:D0911113-0014-4CB1-AD13-9581CC656507" ></OBJECT>

</body>
</html>
