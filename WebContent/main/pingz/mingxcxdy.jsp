<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ page import="com.unitop.config.SystemConfig"%>
<%
	SystemConfig systemConfig = SystemConfig.getInstance();
	request.setAttribute("tesyw_shuangrhq", systemConfig.getValue("tesyw_shuangrhq"));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="style/extremecomponents.css" />
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript" src="js/date/time.js"></script>
		<script type="text/javascript" src="js/special-business.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script language="javascript">
		var countsNum = 0;//����ɾ������
		$(document).ready(function() {
			$("#form1").validate({
   			onsubmit:true,
   			onfocusout:true,
   			onkeyup :true,
   			errorLabelContainer:"#error div.error",
   			wrapper:"li",
   			submitHandler:function(form){
			form.submit();
			$("#tu").html('<table align="center"><tr><td><img border="1" src="images/appInstall_animated.gif"/></td></tr></table>');
			$("#abc").empty();
 	 	 }
  	 	});
  	 	})  
  	 	
  	 	

        //����ӡ��
        function printchk(flag,str0,str1){
        	var math = Math.random();
			var dayfwmsg = QRCODE_OCX.GetDefaultPrintSource();
			dayfwmsg = encodeURI(dayfwmsg);
			$.post("dayfw.do?method=printchk", {math:math, dayfwmsg:dayfwmsg}, function (data, textStatus) {
				if (textStatus = "success") {
					if(data=='f'){
						if(confirm('��ǰĬ�ϴ�ӡ����δע�ᣬ�Ƿ�ע�᣿')){
							dayfwmsg = encodeURI(dayfwmsg);
							 window.location.href = "dayfw.do?method=save&dayfwid="+dayfwmsg;						
						}
					}else {
						if(flag=="batch"){
						  batchprint(dayfwmsg,data);
						}else{
						  if(data<1){
							  alert('��ī�ۺ������꣬�뼰ʱ������');
		                      return;
						  }
						  dayinForOnce(dayfwmsg,str0,str1);
						}
					}
				}
			}, "text");
        }
		//��ӡ 	
  	 	function batchprint(dayfwmsg,data){
			var jsonArray = ${erwmMsgList};
			if((jsonArray.length-countsNum)<=0)
			{
				alert("���Ȳ�ѯ��Ҫ��ӡ��ƾ֤��¼!");
				return;
			}
			var head = "<?xml version='1.0' encoding='gbk'?><ROOT FILEMODE='"+jsonArray[0][0].filename+"' NUM='1'>";
			var foot = "</ROOT>";
			var xmlString = "";
            if(jsonArray.length-countsNum>data){
                      alert('��ī�ۺл��ܴ�ӡ['+(data)+']�ţ��뼰ʱ������');
                      return;
              }
			if(!confirm('�����δ�ӡ['+(jsonArray.length-countsNum)+']��ƾ֤���Ƿ�ȷ��?'))
			{
				return;
			}

			for(var i = 0;i<jsonArray.length;i++){
				var printContent = "<PAGE><BARCODEDATA><BARINFO KEYWORD='{erwmImge}'>"+jsonArray[i][0].erwmMsg+"</BARINFO></BARCODEDATA><PRINTDATA><DATAINFO KEYWORD='{zhangh}' VALUE='"+jsonArray[i][0].zhangh+"'></DATAINFO><DATAINFO KEYWORD='{hum}' VALUE='"+jsonArray[i][0].hum+"'></DATAINFO></PRINTDATA></PAGE>";
				xmlString += printContent;
			}
			 xmlString = head+xmlString+foot;
			 try{
				 ret = QRCODE_OCX.PrintEx(xmlString,0);
			 }catch(e){
				 alert('��ӡʧ��!������Ϣ:'+e);
				 return ;
			 }
			
			if(ret != 0){
				alert(QRCODE_OCX.GetLastErrorDescript());
			 }else{
				 updateZhuant("updateZhuangt",jsonArray[0][0].pingzh,jsonArray[jsonArray.length-1][0].pingzh,dayfwmsg,jsonArray.length-countsNum)
					 for(var i = 0;i<jsonArray.length;i++){
						 $("#erwm_"+jsonArray[i][0].pingzh).html('�Ѵ�ӡ');
						$("#erwm_"+jsonArray[i][0].pingzh).attr("href","#");
					 }
			}
		}
  	 	function printErwm(dayfwmsg,str0,msg,preSee){
			try{
				ret = QRCODE_OCX.PrintEx(msg,preSee);
			}catch(e){
				alert('��ӡʧ��!������Ϣ:'+e);
				return;
			}
			if(ret != 0)
			{
				alert(QRCODE_OCX.GetLastErrorDescript());
			}else{
				updateZhuant("updateZhuangt",str0,str0,dayfwmsg,1);
				$("#erwm_"+str0).html('�Ѵ�ӡ');
				$("#erwm_"+str0).attr("href","#");
			}
		}
		//���ʴ�ӡ
		function dayinForOnce(dayfwmsg,str0,str1){
			var dyzhuangt=$("#erwm_"+str0).html();
			if(dyzhuangt=="�Ѵ�ӡ"){
				alert("��ƾ֤�Ѵ�ӡ��");
			}else{
				if(confirm('ȷ����ӡ?'))
				{
				    var xmlString = jsonTOxml(str1);
					//���ʹ�ӡ
					printErwm(dayfwmsg,str0,xmlString,1);
				}
			}
		}
		function jsonTOxml(json){
			var xmlModel = "<?xml version='1.0' encoding='gbk'?><ROOT FILEMODE='"+json[0].filename+"' NUM='1'><PAGE><BARCODEDATA><BARINFO KEYWORD='{erwmImge}'>"+json[0].erwmMsg+"</BARINFO></BARCODEDATA><PRINTDATA><DATAINFO KEYWORD='{zhangh}' VALUE='"+json[0].zhangh+"'></DATAINFO><DATAINFO KEYWORD='{hum}' VALUE='"+json[0].hum+"'></DATAINFO></PRINTDATA></PAGE></ROOT>"
			return xmlModel;
		}
		function deleteRow(obj,str0){
			var dayfwmsg = QRCODE_OCX.GetDefaultPrintSource();
			dayfwmsg = encodeURI(dayfwmsg);
	  	 	 <c:if  test='${tesyw_shuangrhq==1}'>
	  			var backNum= window.showModalDialog('shuangrhq.jsp',"dialogWidth=10px;dialogHeight=10px;status=no;scroll=no"); 
	  			if(backNum=='1')
	  			{
	  				deleteOrUpdate("delete",str0,dayfwmsg);
	  				obj.parentNode.parentNode.removeNode(true); 
					$("#row_"+str0).html();
					countsNum++;
	  			}
	  		   </c:if>
	  	 	  <c:if test='${tesyw_shuangrhq!=1}'>
	  	 			deleteOrUpdate("delete",str0,dayfwmsg);
	  	 			obj.parentNode.parentNode.removeNode(true); 
					$("#row_"+str0).html();
					countsNum++;
	  	  	  </c:if> 
			}	
       //����ɾ��
		function batchDelete(){
			//var dayfwmsg = "{'dayfwmc':'Samsung ML-331x Series','dayfwip':'12.12.13.12','dayfwport':'1232','dayfwmac':'12dd3dda'}";
			var dayfwmsg = QRCODE_OCX.GetDefaultPrintSource();
			var pingzhList = document.getElementsByName("pingzhList");
			if((pingzhList.length-countsNum)<=0)
			{
				alert("���Ȳ�ѯ��Ҫɾ����ƾ֤��¼!");
				return;
			}

			if(!confirm('��ȷ��Ҫɾ����ѯ�����['+(pingzhList.length-countsNum)+']��ƾ֤��?'))
			{
				return;
			}
			<c:if  test='${tesyw_shuangrhq==1}'>
				var pingzlx = $("#pingzlx").val();
				var zhangh = $("#zhangh").val();
	  			var qispzh = $("#qispzh").val();
	  			var zhongzpzh =  $("#zhongzpzh").val();
	  			var begindate =$("#begindate").val(); 
	  			var enddate = $("#startdate").val();
	  			var guiyh =  $("#guiyh").val();
	  			var jigh =   $("#jigh").val(); 
	  			var zhuangt = $("#zhuangt").val();
	  			var paix = $("#paix").val();
	  			
	  			var selectString ="";
	  			if(pingzlx!='')selectString=selectString+"ƾ֤����:"+pingzlx;
	  			if(zhangh!='')selectString=selectString+",�ʺ�:"+zhangh;
	  			if(qispzh!='')selectString=selectString+",��ʼƾ֤��:"+qispzh;
	  			if(zhongzpzh!='')selectString=selectString+",��ֹƾ֤��:"+zhongzpzh;
	  			if(begindate!='')selectString=selectString+",��ʼ����:"+begindate;
	  			if(enddate!='')selectString=selectString+",��������:"+enddate;
	  			if(guiyh!='')selectString=selectString+",��Ա��:"+guiyh;
	  			if(jigh!='')selectString=selectString+",������:"+jigh;
	  			if(zhuangt!='')selectString=selectString+",״̬:"+zhuangt;
	  			if(paix!='')selectString=selectString+",����:"+paix;		
		  		var backNum= window.showModalDialog('shuangrhq.jsp',"dialogWidth=10px;dialogHeight=10px;status=no;scroll=no"); 
		  		if(backNum=='1')
		  		{
			  		var pingzhString = "";
		  			for(var i = 0; i< pingzhList.length;i++)
		  			{
		  				pingzhString += pingzhList[i].value + ",";
		  			}
		  			
		  			bathdeleteOrUpdate("bathDelete",pingzhString,selectString,dayfwmsg);
		  			$("#form1").submit();
		  		}
	  		</c:if>
	  	 	<c:if test='${tesyw_shuangrhq!=1}'>
		  	 	var pingzhString = "";
	  			for(var i = 0; i< pingzhList.length;i++)
	  			{
	  				pingzhString += pingzhList[i].value + ",";
	  			}
	  			bathdeleteOrUpdate("bathDelete",pingzhString,selectString,dayfwmsg);
	  			$("#form1").submit();
	  	  	</c:if>
		}
</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>	
		</form>	
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td  background="images/main_place_bg.gif">
					ƾ֤���� - ��ϸ��ѯ				
				</td>
			</tr>
			<tr>
					<html:form styleId="form1" method="post" action="pingz.do?method=findPingzmx">
						<table  width="95%"  border="0" cellpadding="0" cellspacing="0" align="center">
						<tr class="alignleft">
								<td width="3" class=" aligncenter w70"></td>
									<td width="62" class=" aligncenter w70">
									<button type="submit" style="width:60px"
										onmouseover="this.className='buttom2'"
										onmouseout="this.className='buttom1'" class="buttom1"
										onclick="return list(this)">
										<img src="images/search1.gif" width="13" height="13"
											align="middle">��ѯ</button>	
									</td>
									<td class=" aligncenter w70">
									<button type="button" style="width:75px" id = "batch"
										onmouseover="this.className='buttom2'"
										onmouseout="this.className='buttom1'" class="buttom1"
										onclick="printchk('batch','1','1');">
										<img src="images/search1.gif" width="13" height="14" align="middle">������ӡ</button>
									</td>
									<td class=" aligncenter w70">
										<button type="button" style="width:75px" id = "batch" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onclick="batchDelete();">
											<img src="images/search1.gif" width="13" height="14" align="middle">����ɾ��</button>
									</td>
									
							</tr>
							<tr>
								<td></td>
								<td class="alignright class1_td">
								ƾ֤����:					
								</td>
								<td class="class1_td alignleft">
								  <div align="left">
								  	<html:select styleId='pingzlx' property="pingzlx" style="width:85px;">
								  		<c:forEach items="${pingzlxlist}" var="lx">
								  		<html:option value="${lx.pingzmc}">${lx.pingzmc}</html:option>
							          	</c:forEach>
									</html:select>
                                  </div></td>
								<td  width="11%"  class="class1_td alignright">
									<span>�˺�:<span/>
								</td>
								<td  class="alignleft class1_td">
									<html:text styleId='zhangh' property="zhangh" styleClass="inputField zhangh" style="width:80px"/>
							  	</td>
								<td  width="16%"  class="class1_td alignright">
							  		��ʼƾ֤��:
							  	</td>
								<td class="alignleft class1_td">
									<html:text styleId='qispzh'  property="qispzh" styleClass="inputField qispzh" style="width:80px" />
							  	</td>
								<td  width="17%"   class="class1_td alignright">
									��ֹƾ֤��:
								</td>
								<td   class="alignleft class1_td">
									<html:text styleId='zhongzpzh'  property="zhongzpzh" styleClass="inputField zhongzpzh" style="width:80px" />
							  	<td   class="class1_td alignleft">
								</td>
								<td  width="10%"  class="class1_td alignleft">
								</td>
							</tr>
							<tr class="alignleft">
							<td></td>
							<td  width="10%" class="alignright class1_td">
							��������:
							</td>
								<td class="class1_td alignleft">
								  <table width="102" border="0" cellspacing="0" cellpadding="0">
									  <tr>
										  <td>
											  <html:text property="begindate" styleId="begindate" styleClass="inputField date_input"  onclick="WdatePicker()" />
										  </td>
										  <td>
										   <td>
										    ��  
										    </td>
										  <td>
											   <html:text property="enddate" styleId="startdate"  style="width:65px"  styleClass="inputField date_input" onclick="WdatePicker()" />		
										  </td>										</td>
									  </tr>
								  </table>	
  							  </td>
								<td class="class1_td alignright ">��Ա��:</td>
								<td class="alignleft class1_td">
								 <html:text styleId='guiyh'  property="guiyh" styleClass="inputField guiyh" style="width:80px" />
								</td>
								<td class="alignright class1_td">������:</td>
								<td class="class1_td alignright w90"> 
									<html:text styleId="jigh"  property="jigh" styleClass="inputField jigh" style="width:80px" />
							    </td>
								<td class="class1_td alignright">
										״̬��							
								</td>
								<td class="class1_td alignleft">
								<html:select styleId='zhuangt' property="zhuangt" >
								  	<html:option value="">ȫ��</html:option>
								  	<html:option value="δ��ӡ">δ��ӡ</html:option>
								  	<html:option value="�Ѵ�ӡ">�Ѵ�ӡ</html:option>
								  	<html:option value="�ѻ���">�ѻ���</html:option>
								</html:select>
								</td>
								<td  width="8%"  class="class1_td alignright">
										����:		
								</td>
								<td class="class1_td alignleft">
								<html:select styleId='paix' property="paix" >
								  	<html:option value="����">����</html:option>
								  	<html:option value="����">����</html:option>
								</html:select>
								</td>
							</tr>
					  </table>
					</html:form>
				</td>
			</tr>
</table>
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td bgcolor="#FFCEFF" class="orange error">
						<div style="background-color:#FFCEFF;" class="error orange"></div>
					</td>
				</tr>
				<logic:messagesPresent>
					<tr>
						<td bgcolor="#FFCEFF" class="orange">
							<div id=abc class="abc"><html:errors /></div>
						</td>
					</tr>
				</logic:messagesPresent>
			</table>
		</form>
		<table width="97%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="15" height="14">
					<img src="images/table_arrow_01.gif" width="15" height="14">
				</td>
				<td background="images/table_arrow_08.gif" style="height:14px;"></td>
				<td width="14" height="14">
					<img src="images/table_arrow_02.gif" width="14" height="14">
				</td>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">&nbsp;
			  </td>
				<td>
					<ec:table filterable="false" sortable="false" title="��ӡ���"
						showPagination="true" view="compact" items="pingzlist" var="pingzmx"
						action="${pageContext.request.contextPath}/pingz.do?method=findPingzmx"
						imagePath="${pageContext.request.contextPath}/images/table/*.gif" rowsDisplayed="${ec_yemjlts}" >
						<ec:row>
							<ec:column property="pingzh" title="ƾ֤��"/>
							<ec:column property="zhangh" title="�˺�" cell="org.extremecomponents.table.cell.DisplayCell" />
							<ec:column property="hum" title="����" />
							<ec:column property="jigh" title="������" />
							<ec:column property="guiyh" title="��Ա��" />
							<ec:column property="riq" title="����" />
							<ec:column property="shij" title="ʱ��" />
							<ec:column property="pingzlx" title="ƾ֤����" />
							<ec:column property="zhuangt" title="״̬" />
							<ec:column property="����" title="����" >
							<a href="#" onclick="javascript:if(confirm('ȷ��ɾ��?'))deleteRow(this,'${pingzmx.pingzh}');" style="color: blue;text-decoration:underline;">ɾ��</a>
							<c:if test="${pingzmx.zhuangt=='δ��ӡ'}">
									<a id="erwm_${pingzmx.pingzh}"  href="#" style="color: blue;text-decoration:underline;" onclick=printchk('simple','${pingzmx.pingzh}',${pingzmx.erwmMsg}) >��ӡ</a>
							</c:if>
							<c:if test="${pingzmx.zhuangt=='�Ѵ�ӡ'}">
								�Ѵ�ӡ
							</c:if>
							<c:if test="${pingzmx.zhuangt=='�ѻ���'}">
								�ѻ���
							</c:if>
							</ec:column>
						</ec:row>
					</ec:table>
				</td>
				<td background="images/table_arrow_07.gif">&nbsp;
			  </td>
			</tr>
			<tr>
				<td width="15" height="14">
					<img src="images/table_arrow_03.gif" width="15" height="14">
				</td>
				<td background="images/table_arrow_09.gif" style="height:14px;"></td>
				<td width="14" height="14">
					<img src="images/table_arrow_04.gif" width="14" height="14">
				</td>
			</tr>
		</table>
		<OBJECT ID="QRCODE_OCX" WIDTH="0" HEIGHT="0" CLASSID="CLSID:9A1466AD-5639-4958-941C-6C6A39651B1C" ></OBJECT>
		<c:forEach items="${pingzlist}" var="pingzmx">
			<input type="hidden" name="pingzhList" value="${pingzmx.pingzh}">
		</c:forEach>
</body>
</html>