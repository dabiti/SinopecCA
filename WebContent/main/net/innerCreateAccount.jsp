<%@ page contentType="text/html;charset=GBK" language="java"
	isELIgnored="false"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.unitop.sysmgr.service.SystemMgrService"%>
<%@page import="javax.annotation.Resource"%>
<%@page import="com.unitop.sysmgr.service.impl.SystemMgrServiceImpl"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ page import="com.unitop.config.SystemConfig"%>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%
	
	SystemConfig systemConfig = SystemConfig.getInstance();
	request.setAttribute("tesyw_shuangrhq", systemConfig
			.getValue("tesyw_shuangrhq"));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="js/shield.js"></script>	
		<script src="js/pagejs/ocx.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript" src="js/date/time.js"></script>
		<script type="text/javascript" src="js/special-business.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
<script type="text/javascript"> 
		var yinjkhFlag=false;
		var needQuery=true;
		var zhanghFlag=true;
		function validateAccount(key,length){
			var zhangh = $("#zhangh").val();
			if(key==12){
				zhangh= $("#zhuzh").val();
				}
			if(zhangh!=null&&((zhangh.length>=10&&zhangh.length<=13)||zhangh.length==22)){
				 $.ajax({
				 	 async:false,
				 	 url:"accountinfo.do?method=getAccountFromShort",
					 dataType:"text",
					 data: {account:zhangh},
					 cache:false,
					 success:function (data,textStatus){
						 if(textStatus=="success"){
							 if(key==0){
							 $("#zhangh").val(data);
							}
						 	if(key==12){
							 $("#zhuzh").val(data);
						 	}
						 }
					 }
			 	});
					return lengthVolidate(key,length);
			}else{
				return lengthVolidate(key,length);
			}
		}
		function lengthVolidate(key,length){
			var keyName;
			var dianhReg=/^[0-9]{5,18}$/;

			var quhReg=/^0[1-9][0-9]{1,2}$/;
			var fenjhReg=/^[0-9]{1,6}$/;
			if(0==key){
				keyName = $("#zhangh").val();
				if(keyName.length==0 || keyName ==''){
					$("#zhanghmsg").text(" �˺Ų���Ϊ��");
					return false;
				}
				if(keyName.length>length){
					$("#zhanghmsg").text(" ������˺ų��ȳ���"+length);
					return false;
				}
				var reg=  /(^[0-9]{9}$)|(^[0-9]{17}$)|(^[0-9]{22}$)/;
				if(!reg.test(keyName)){
					$('#zhanghmsg').text(" �˺Ÿ�ʽ����ȷ");
					return false;
				}
				$("#zhanghmsg").text("");
				return true;
			}else if(1==key){
				keyName = $("#accountname").val();
				if(keyName.length==0 || keyName ==''){
					$("#accountnamemsg").text(" ��������Ϊ��");
					return false;
				}
				if(keyName.length>length){
					$("#accountnamemsg").text(" ����Ļ������ȳ���"+length);
					return false;
				}
				$("#accountnamemsg").text("");
				return true;
			}else if(2==key){
				keyName = document.getElementById("remark").value;
				if(keyName.length>length){
					$("#remarkmsg").text("* ����ı�ע���ȳ���"+length);
					return false;
				}
				if(keyName.indexOf(",")!=-1||keyName.indexOf("��")!=-1){
					$("#remarkMsg").text("* ����ı�ע���ж���,�뽫�����滻Ϊ�ֺŻ��߶ٺ�!");
					return false;
				}
				$("#remarkmsg").text("");
				return true;
			}else if(3==key){
				$("#zhanghxzmsg").text("");
				keyName = document.getElementById("zhanghxz").value;
				if(keyName.length==""){
					$("#zhanghxzmsg").text(" �˻����ʲ���Ϊ��");
					return false;
				}
				$("#zhanghxzmsg").text("");
				return true;
			}else if(4==key){
				keyName = document.getElementById("allexchange").value;
				if(keyName.length==""){
					$("#allexchangezmsg").text(" ͨ�ұ�־����Ϊ��");
					//document.getElementById("allexchange").focus();
					return false;
				}
				$("#allexchangezmsg").text("");
				return true;
			}else if(5==key){
				madeYinjkh();
				keyName=$("#yinjkbh").val();
				$("#yinjkbhmsg").text('');
				$("#yinjkbhmsg2").text('');
				if(keyName.length==0){
					$("#yinjkbhmsg").text(" ӡ�����Ų���Ϊ��");
					return false;
				}
				var reg= /^[0-9]{5,22}$/;
				if(!reg.test(keyName)){
					$('#yinjkbhmsg').text(" ӡ�����Ÿ�ʽ����ȷ");
					return false;
				}
				$("#yinjkbhmsg").text("");
				return true;
			}else if(6==key){
				keyName=$("#opendate").val();
				if(keyName.length==0){
					$("#opendateMsg").text(" �������ڲ���Ϊ��");
					return false;
				}
				
				var reg = /^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/;
				if(!reg.test(keyName)){
					$('#opendateMsg').text(" �������ڸ�ʽ����ȷ");
					return false;
				}
				$("#opendateMsg").text("");
				return true;
			}else if(11==key){
				keyName=$("#linkman").val();
				if(keyName.length==0){
					$("#lianxrMsg").text(" ��ϵ��1����Ϊ��");
					return false;
				}
				if(keyName.indexOf(",")!=-1||keyName.indexOf("��")!=-1){
					$("#lianxrMsg").text(" �������ϵ��1���ж���,�뽫�����滻Ϊ�ֺŻ��߶ٺ�!");
					return false;
				}
				$("#lianxrMsg").text("");
				return true;
			}else if(7==key){
				keyName=$("#phone").val();
				var quh=$("#lianxrqh").val();
				var fenjh=$("#lianxrfjh").val();
				if(length>=1){
					if(quh!=null&&quh.length!=0){
						if(!quhReg.test(quh)){
							$("#phoneMsg").text(" ���Ÿ�ʽ����ȷ");
							return false;
						}
					}
				}
				if(length>=2){
					if(keyName.length==0){
						$("#phoneMsg").text(" ��ϵ�˵绰����Ϊ��");
						return false;
					}
					if(!dianhReg.test(keyName)){
						$('#phoneMsg').text(" ��ϵ�˵绰��ʽ����ȷ");
						return false;
					}
				}
				if(length>=3){
					if(fenjh!=null&&fenjh.length!=0){
						if(!fenjhReg.test(fenjh)){
							$("#phoneMsg").text(" �ֻ��Ÿ�ʽ����ȷ");
							return false;
						}
					}
				}
				$("#phoneMsg").text("");
				return true;
			}else if(8==key){
				keyName=$("#fuzrdh").val();

				var quh=$("#fuzrqh").val();
				var fenjh=$("#fuzrfjh").val();
				if(length>=1){
					if(quh!=null&&quh.length!=0){
						if(!quhReg.test(quh)){
							$("#fuzrdhMsg").text(" ���Ÿ�ʽ����ȷ");
							return false;
						}
					}
				}
				if(length>=2){
					if(keyName.length==0&&quh.length!=0){
						$("#fuzrdhMsg").text(" ���������ţ��绰����Ϊ��");
						return false;
					}
					if(keyName.length!=0&&!dianhReg.test(keyName)){
						$('#fuzrdhMsg').text(" �����˵绰��ʽ����ȷ");
						return false;
					}
				}
				if(length>=3){
					if(fenjh!=null&&fenjh.length!=0){
						if(keyName.length==0){
							$("#fuzrdhMsg").text(" �绰Ϊ�գ���������ֻ���");
							return false;
						}
						if(!fenjhReg.test(fenjh)){
							$("#fuzrdhMsg").text(" �ֻ��Ÿ�ʽ����ȷ");
							return false;
						}
					}
				}
				
				$("#fuzrdhMsg").text("");
				return true;
			}else if(9==key){
				keyName=$("#phone2").val();
				//alert(keyName);
				var quh=$("#lianxrqh2").val();
				var fenjh=$("#lianxrfjh2").val();
				
				if(length>=1){
					if(quh!=null&&quh.length!=0){
						if(!quhReg.test(quh)){
							$("#phone2Msg").text(" ���Ÿ�ʽ����ȷ");
							return false;
						}
					}
				}
				if(length>=2){
					if(keyName.length==0&&quh.length!=0){
						$("#phone2Msg").text(" ���������ţ��绰����Ϊ��");
						return false;
					}
					if(keyName.length!=0&&!dianhReg.test(keyName)){
						$('#phone2Msg').text(" �����˵绰��ʽ����ȷ");
						return false;
					}
				}
				if(length>=3){
					if(fenjh!=null&&fenjh.length!=0){
						if(keyName.length==0){
							$("#phone2Msg").text(" �绰Ϊ�գ���������ֻ���");
							return false;
						}
						if(!fenjhReg.test(fenjh)){
							$("#phone2Msg").text(" �ֻ��Ÿ�ʽ����ȷ");
							return false;
						}
					}
				}
				$("#phone2Msg").text("");
				return true;
			}else if(10==key){
				keyName=$("#fuzrdh2").val();
				var quh=$("#fuzrqh2").val();
				var fenjh=$("#fuzrfjh2").val();
				if(length>=1){
					if(quh!=null&&quh.length!=0){
						if(!quhReg.test(quh)){
							$("#fuzrdh2Msg").text(" ���Ÿ�ʽ����ȷ");
							return false;
						}
					}
				}
				if(length>=2){
					if(keyName.length==0&&quh.length!=0){
						$("#fuzrdh2Msg").text(" ���������ţ��绰����Ϊ��");
						return false;
					}
					if(keyName.length!=0&&!dianhReg.test(keyName)){
						$('#fuzrdh2Msg').text(" ������2�绰��ʽ����ȷ");
						return false;
					}
				}
				if(length>=3){
					if(fenjh!=null&&fenjh.length!=0){
						if(keyName.length==0){
							$("#fuzrdh2Msg").text(" �绰Ϊ�գ���������ֻ���");
							return false;
						}
						if(!fenjhReg.test(fenjh)){
							$("#fuzrdh2Msg").text(" �ֻ��Ÿ�ʽ����ȷ");
							return false;
						}
					}
				}
				$("#fuzrdh2Msg").text("");
				return true;
			}else if(20==key){
				keyName = $("#linkman2").val();
				//$("#yinjkbh").attr("readonly","readonly");
			//	$("#yinjkbh").css("color","silver");
				if(keyName.length==0){
					$("#lianxr2Msg").text("");
					return true;
				}
				
				if(keyName.indexOf(",")!=-1||keyName.indexOf("��")!=-1){
					$("#lianxr2Msg").text(" �������ϵ��2���ж���,�뽫�����滻Ϊ�ֺŻ��߶ٺ�");
					return false;
				}
				$("#lianxr2Msg").text("");
				return true;
			}else if(21==key){
				keyName = $("#fuzr").val();
				if(keyName.length==0){
					$("#fuzrMsg").text("");
					return true;
				}
				if(keyName.indexOf(",")!=-1||keyName.indexOf("��")!=-1){
					$("#fuzrMsg").text(" ����ĸ����˺��ж���,�뽫�����滻Ϊ�ֺŻ��߶ٺ�");
					return false;
				}
				$("#fuzrMsg").text("");
				return true;
			}else if(22==key){
				keyName = $("#fuzr2").val();
			
				if(keyName.length==0){
					$("#fuzr2Msg").text("");
					return true;
				}
				
				if(keyName.indexOf(",")!=-1||keyName.indexOf("��")!=-1){
					$("#fuzr2Msg").text(" ����ĸ�����2���ж���,�뽫�����滻Ϊ�ֺŻ��߶ٺ�");
					return false;
				}
				$("#fuzr2Msg").text("");
				return true;
			}
		}
//�����˻���Ϣ
function startRequestForSearch() {
	var titlename=$("#title").html();
	if(titlename=="�ڲ��������޸�"){
		return;
			}
	resetErrMsg();
		var account = $("#zhangh").val();
		var math = Math.random();
		$.post("accountinfo.do?method=getAccountForAjax", {math:math, account:account}, function (data, textStatus) {
			if (textStatus = "success") {
				if(data.indexOf('<html>')!=-1){
					window.location.href='timeOutlogin.jsp';
					return;
				}
				var accountinfo = data.split(",");
				if(accountinfo[0]=="fail001"){
					$("#zhanghmsg").html("û��Ȩ�޲鿴���˻�!");
					zhanghFlag=false;
					return;
				}
				if(accountinfo[0]=="fail002"){
					if(account.length>17){
						$("#zhanghmsg").text('�����ڴ˾��˻�!');
						zhanghFlag=false;
					}
				if(account.length==9){
						$("#zhanghxz").val("�������ʻ�");
						zhanghFlag=true;
					}
					if(account.length==17){
						$("#zhanghxz").val("�ڲ���");
						zhanghFlag=true;
						}
					return;
				}
				if(accountinfo[1]!="�ڲ���"&&accountinfo[1]!="�������ʻ�"){
					$("#zhanghmsg").html("���˻������˻�!");
					zhanghFlag=false;
					return;
					}
				if(accountinfo[14]=="����"){
					$("#zhanghmsg").html("���˻�������!");
					zhanghFlag=false;
					return;
					}
				zhanghFlag=true;
				$("#accountname").val(accountinfo[0]==""?"":accountinfo[0]);
				$("#opendate").val(accountinfo[2]==""?"":accountinfo[2]);
				$("#zhanghxz").val(accountinfo[1]);
				$("#allexchange").val(accountinfo[3]==""?"":accountinfo[3]);
				$("#linkman").val(accountinfo[4]==null?"":accountinfo[4]);
				$("#phone").val(accountinfo[5]==""?"":accountinfo[5]);
				$("#fuzr").val(accountinfo[6]==""?"":accountinfo[6]);
				$("#fuzrdh").val(accountinfo[7]==""?"":accountinfo[7]);
				$("#remark").val(accountinfo[8]==""?"":accountinfo[8]);
				$("#yinjkbh").val(accountinfo[9]==""?"":accountinfo[9]);
				if($("#yinjkbh").val()!=null&&$("#yinjkbh").val().length!=0){
					needQuery=false;//��ӡ������֤��ʶ����Ϊͨ��
					}else{
						needQuery=true;//��ӡ������֤��ʶ����δͨ��
					}
				$("#linkman2").val(accountinfo[24]==null?"":accountinfo[24]);
				$("#phone2").val(accountinfo[25]==""?"":accountinfo[25]);
				$("#fuzr2").val(accountinfo[26]==""?"":accountinfo[26]);
				$("#fuzrdh2").val(accountinfo[27]==""?"":accountinfo[27]);
				$("#lianxrqh").val(accountinfo[31]);
				$("#lianxrfjh").val(accountinfo[32]);
				$("#lianxrqh2").val(accountinfo[33]);
				$("#lianxrfjh2").val(accountinfo[34]);
				$("#fuzrqh").val(accountinfo[35]);
				$("#fuzrfjh").val(accountinfo[36]);
				$("#fuzrqh2").val(accountinfo[37]);
				$("#fuzrfjh2").val(accountinfo[38]);
				$("#ischecksmall").val(accountinfo[39]);
				var shifdh=accountinfo[28];
				if(shifdh==1){
					$("#shifdh").attr("checked","");
					$("#shifdh2").attr("checked","checked");
					}else{
					$("#shifdh2").attr("checked","");
					$("#shifdh").attr("checked","checked");
					}
				var tongdbz=accountinfo[21];
				var tongdFlag='${tongdFlag}';
				if(tongdbz=='1'&&tongdFlag=='0'){
					$("option[value='��������']").html("һ�����б���").val("һ�����б���");
					/*var allexchange=document.getElementById("allexchange");
					if(allexchange.options.length==5){
						allexchange.options.remove(3);
						}*/
				}
				if(tongdbz=='0'&&tongdFlag=='1'){
					$("option[value='һ�����б���']").html("��������").val("��������");
					}
				turnToModi();
			}
		}, "text");
	};
	function madeYinjkh(){
		var yinjkh=$("#yinjkbh").val();

		if(yinjkh!=null&&yinjkh.length==8){
			var shh='${clerk.parentorg}';
			$("#yinjkbh").val(shh+'00000000'+yinjkh);
			
		}
	}
function turnToModi(){
	$("#commit").hide();
	$("#update").show();
	$("#title").html("�˻���Ϣ");
	$("#zhangh").attr("readonly","true").css("color","silver");
	$("#accountname").attr("readonly","").css("color","");
	$("#opendate").attr("readonly","true").css("color","silver");
	$("#update").click(function(){
		var math = Math.random();
		$("#form1").attr("action","accountinfo.do?method=updateInnerAccount&math="+math);
		$("#update").hide();
		$("#save").show();
		$("#commit").click(function(){
			formSubmit();
		});
		$("#title").html("�ڲ��������޸�");
		$("#allexchange").attr("readonly","").css("color","");
		$("#linkman").attr("readonly","").css("color","");
		$("#phone").attr("readonly","").css("color","");
		$("#fuzr").attr("readonly","").css("color","");
		$("#fuzrdh").attr("readonly","").css("color","");
		$("#remark").attr("readonly","").css("color","");
	});
	$("#update").click();
}

//���ӡ����״̬
function queryYinjk(){
	yinjkhFlag=false;//��ӡ������ʶ����Ϊδͨ��
	var zhangh=$("#zhangh").val();
	if(zhangh==null||zhangh.length==0){
		return ;
		}
	var yinjkh=$("#yinjkbh").val();
	var yinjkh2=$("#yinjkbh2").val();
	var yinjkh3=$("#yinjkbh3").val();
	var yinjkh4=$("#yinjkbh4").val();
	var yinjkStr="";
	if(yinjkh!=null&&yinjkh.length>0){
		yinjkStr+=yinjkh;
	}
	if(yinjkh2!=null&&yinjkh2.length>0){
		yinjkStr+=",";
		yinjkStr+=yinjkh2;
	}
	if(yinjkh3!=null&&yinjkh3.length>0){
		yinjkStr+=",";
		yinjkStr+=yinjkh3;
	}
	if(yinjkh4!=null&&yinjkh4.length>0){
		yinjkStr+=",";
		yinjkStr+=yinjkh4;
	}
	if(yinjkStr==null||yinjkStr==""){
	return;	
	}
	var zhanghxz=$("#zhanghxz").val();
	var zhanghxzFlag=zhanghxz=="�������ʻ�"||zhanghxz=="�ڲ���"?"0":"1";
	var zhangh=$("#zhangh").val();
	var math = Math.random();
	$.post("accountinfo.do?method=queryYinjk", {math:math,zhangh:zhangh,yinjkStr:yinjkStr,zhanghxz:zhanghxzFlag}, function (data, textStatus) {
		if(textStatus=="success"){
			if(data.indexOf('<html>')!=-1){
				window.location.href='timeOutlogin.jsp';
				return;
			}
			$("#yinjkbhmsg").text('');
			$("#yinjkbhmsg2").text('');
		var result=data;
		if(result=="fail001"){
			yinjkhFlag=false;
			$("#yinjkbhmsg").text("ӡ�����Ų���ȷ!");
			return;
			}
		if(result=="fail002"){
			yinjkhFlag=false;
			$("#yinjkbhmsg").text("ӡ�����ѱ�ʹ��");
			return;
			}
		if(result=="fail003"){
			yinjkhFlag=false;
			$("#yinjkbhmsg").text("��ѯʧ�ܣ������²���");
			return;
			}
		if(result=="fail004"){
			yinjkhFlag=false;
			$("#yinjkbhmsg").text("���˻�δ����");
			return;
			}
		if(result=="1"){
			$("#yinjkbhmsg2").text("����");
			yinjkhFlag=true;
			return;
			}
		if(result=="0"){
			$("#yinjkbhmsg2").text("ͨ��");
			yinjkhFlag=true;
			return;
			}
		$("#yinjkbhmsg").text(result);
		yinjkhFlag=false;
		return;
		}
	}, "text");
	
}

//�����ʾ��Ϣ
function resetErrMsg(){
	$("#zhanghmsg").text('');
	$("#accountnamemsg").text('');
	$("#zhanghxzmsg").text('');
	$("#opendatemsg").text('');
	$("#yinjkbhmsg").text('');
	$("#yinjkbhmsg2").text('');
	$("#lianxrMsg").text('');
	$("#phoneMsg").text('');
	$("#lianxr2Msg").text('');
	$("#phone2Msg").text('');
	$("#fuzrMsg").text('');
	$("#fuzrdhMsg").text('');
	$("#fuzr2Msg").text('');
	$("#fuzrdh2Msg").text('');
	$("#abc").text('');
	$("#abc1").text('');
	
}

//�ύ��
function formSubmit(){
	if(!zhanghFlag)return;
	if(needQuery){
	if(!yinjkhFlag){
		var errMsg=$("#yinjkbhmsg").html();
		if(errMsg!=null&&errMsg.length!=0){
			return;
			}
			$("#yinjkbhmsg").html("��Ҫͨ��ӡ������֤!");
			return;
		}else{
			$("#yinjkbhmsg").html('');
		}
	}
	if(!lengthVolidate(0,22))return;
	var title=$("#title").html();
	if(title.indexOf("����")!=-1){
		if(!lengthVolidate(0,17))return;
		}
	if(!lengthVolidate(1,40))return;
	if(!lengthVolidate(3,14))return;
	if(!lengthVolidate(2,200))return;
	if(!lengthVolidate(4,14))return;
	if(!lengthVolidate(5,22))return;
	if(!lengthVolidate(6,14))return;
	if(!lengthVolidate(11,14))return;
	if(!lengthVolidate(7,22))return;
	if(!lengthVolidate(8,14))return;
	if(!lengthVolidate(9,14))return;
	if(!lengthVolidate(10,22))return;
	if(!lengthVolidate(21,22))return;
//	alert(1111111);
	<c:if  test='${tesyw_shuangrhq==1}'>
	   var obj = new Object(); 
	   var title=$("#title").html();
	   if(title.indexOf("����")==-1){
	   	obj.titleName ='�����޸�';
		   }else{
		obj.titleName ='����';
		}
	   
	   obj.account = $("#zhangh").val();
	   obj.checktype="��Ȩ";
	   obj.quanxbs="9994|KH_001";
	   
	   if(obj.titleName=='�����޸�'){
		   obj.quanxbs="9994|ZLXG_001";
		   }
	   var backNum = window.showModalDialog('authorized.jsp',obj,"dialogWidth=10px;dialogHeight=10px;status=no;scroll=no"); 
	   if(backNum=='1')
	   {
			document.getElementById("form1").submit();
	   }
	</c:if>
	<c:if test='${tesyw_shuangrhq!=1}'>
	document.getElementById("form1").submit();
	</c:if>

}

function WdatePickered(){
	if(!$("#opendate").attr("readonly"))
	WdatePicker();
	
}

//��ʼ��ҳ��
$(function(){
	if(!$("#shifdh").attr("checked")&&!$("#shifdh2").attr("checked"))
		$("#shifdh").attr("checked","checked");
		var tongdFlag='${tongdFlag}';
		if(tongdFlag =='1'){
			
			var len=$("#allexchange").get(0).options.length;
			if(len==6){
				$("#allexchange").get(0).remove(4);
			}
		}
		//�˻����ʸ����˺ų����ж� �����޸�
		$("#zhanghxz").attr("readonly","true").css("color","silver");

		//��������
		if($("#opendate").val()==""||$("#opendate").val()==null){
		$("#opendate").val('${date}');
		}	
		var type=$("#type").val();
		//Ϊ������ť��ӵ���¼�
		$("#commit").click(function(){
			formSubmit();
		});
		//Ϊ���水ť��ӵ���¼�
		$("#save").click(function(){		
			formSubmit();
		});
		//���
		$("#reset").click(function(){
			location.href='accountinfo.do?method=innerZhanghkhView';
		
		});
		//��ʼ��ҳ�� 
		if(type=="kaih"){
			$("#title").html("�ڲ�������");
			//�����޸�ҳ���action
			$("#form1").attr("action","accountinfo.do?method=createInnerAccount");
			}
		
		if(type=="zilxg"){
		$("#title").html("�ڲ��������޸�");
		
			//�����޸�ҳ���action
			$("#form1").attr("action","accountinfo.do?method=updateInnerAccount");
			$("#zhangh").attr("readonly","readonly").css("color","silver");
			$("#accountname").attr("readonly","").css("color","");
			$("#opendate").attr("readonly","readonly").css("color","silver");
		
			$("#commit").hide();
			$("#save").show();
			$("#zhanghxz").focus();
			needQuery=false;
			//�����ɹ�֮���Ƿ����ӵ�����ҳ��
			var msg='${kaih}';
			if(msg=="kaih"){
				setTimeout(function(){
					if(confirm('�Ƿ���н���?'))
						window.location.href='yinjManage.do?method=foryinjgl&zhangh='+$("#zhangh").val();
				},100);
			}
	}
});
</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td  background="images/main_place_bg.gif">
					�˻�����-�ڲ��������������޸�
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
		<br>
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange error">
						<div class="error orange" id="abc1"></div>
					</td>
				</tr>
				<tr>
					<td class="orange" id="abc">
						<html:errors />
					</td>
				</tr>
			</table>
		</form>
		<html:form styleId="form1" method="post" action="accountinfo.do?method=createInnerAccount">
			<table width="97%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="up-left" />
						<td class="up-middle" />
							<td class="up-right" />
				</tr>
				<tr>
					<td background="images/table_arrow_05.gif">
						
					</td>
					<td>
					<button type="button" style="width:80px" class="buttom1" onclick="javascript:window.location.href='accountinfo.do?method=zhanghkhView'" >
									<img src="images/add.gif" width="13" height="13" align="absmiddle"/>������</button>
					<button type="button" style="width:80px" class="buttom2" >
									<img src="images/add.gif" width="13" height="13" align="absmiddle"/>�ڲ���</button>
						<table width="100%" border="0" cellspacing="1" cellpadding="0"
							class="class1_table">
							<thead class="class1_thead">
								<tr>
									<th id="title" colspan="2" class="class1_thead th">�ڲ�������</th>
									<html:hidden property="" styleId="type" value="${logintype}"/>
								</tr>
							</thead>
							<tr>
								 <td width="42%" class="class1_td alignright">�˺ţ�</td>          
        						  <td width="58%" class="class1_td alignleft">
          							<html:text property="account"  styleId="zhangh"   styleClass="inputField required accountname" maxlength="25"  title="9��17����22λ����" 
										style="width:160px;"  onkeydown="if(event.keyCode==13){if(!validateAccount(0,22)){this.focus();return false;}}"  onblur="if(validateAccount(0,22))startRequestForSearch(this);"></html:text>
          								<span style="color: red">*</span><span id="zhanghmsg" style="color: red"> </span>
          						</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									���� ��								</td>
								<td class="class1_td alignleft">
									<html:text property="accountname" styleId="accountname"
										name="accountinfoForm" 	maxlength="40" 						
										styleClass="inputField required accountname" 
										onkeydown="if(event.keyCode==13){if(!lengthVolidate(1,40)){this.focus();return false;}}"
										style="width:160px; " onblur="lengthVolidate(1,40);"  />
									<span style="color: red">*</span><span id="accountnamemsg" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									�˻����ʣ�
								</td>
								<td class="class1_td alignleft">
								<html:text property="zhanghxz" styleId="zhanghxz"
										name="accountinfoForm" 	maxlength="40" 						
										styleClass="inputField required " onblur="lengthVolidate(3,14);" onkeydown="if(event.keyCode==13){if(!lengthVolidate(3,14)){this.focus();return false;}}"
										style="width:160px;readonly:true" />
									<span style="color: red">*</span><span id="zhanghxzmsg" style="color:red"></span>
								</td>

							</tr>
							<tr>
								<td class="class1_td alignright">
									�������ڣ�
								</td>
								<td width="58%" class="class1_td alignleft" id="kaihrq_1">
									<html:text property="opendate" styleId="opendate"
										 name="accountinfoForm" 
										styleClass="inputField required opendate" maxlength="10"  title="yyyy-mm-dd"
										style="width:100px; "  onblur="lengthVolidate(6,14);"  onclick="WdatePickered()" />
									<span style="color: red">*</span><span id="opendateMsg" style="color: red"></span>
								
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									ͨ�ұ�־��
								</td>
								<td id="allexchangetd" class="class1_td alignleft">
									<html:select property="allexchange" styleId="allexchange"
										name="accountinfoForm"  onblur="lengthVolidate(4,14);" onkeydown="if(event.keyCode==13){if(!lengthVolidate(4,14)){this.focus();return false;}}">
										<html:option value="">��ѡ��</html:option>
										<html:option value="ȫ��">ȫ��</html:option>
										<html:option value="һ������ȫϽ">һ������ȫϽ</html:option>
										<c:if test="${tongdFlag eq '1'}"><html:option value="һ�����б���">һ�����б���</html:option></c:if>
										<c:if test="${tongdFlag eq '0'}"><html:option value="��������">��������</html:option></c:if>
										<html:option value="��ͨ��"  styleId="butd">��ͨ��</html:option>
									</html:select>
									<span style="color: red">*</span><span id="allexchangezmsg" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									�Ƿ�Ա�С�룺
								</td>
								<td class="class1_td alignleft">
									<html:select property="ischecksmall" styleId="ischecksmall"
										name="accountinfoForm" >
										<html:option value="0">��</html:option>
										<html:option value="1">��</html:option>
										</html:select>
									<span style="color: red">*</span>
								</td>
							</tr>
							<tr style="display:none">
								<td class="class1_td alignright">
									�Ƿ����ˣ�
								</td>
								<td class="class1_td alignleft">
									<html:radio styleId="shifdh"  property="shifdh" value="0" >��</html:radio>
									<html:radio styleId="shifdh2"  property="shifdh" value="1">��</html:radio>
									
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									������ϵ��1��
								</td>
								<td class="class1_td alignleft">
									<html:text property="linkman" styleId="linkman"
										name="accountinfoForm" onblur="lengthVolidate(11,14);" onkeydown="if(event.keyCode==13){if(!lengthVolidate(11,14)){this.focus();return false;}}"
										styleClass="inputField required englishname" maxlength="15"
										style="width:180px; " />
									<span style="color: red">*</span><span id="lianxrMsg" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									������ϵ�˵绰1��
								</td>
								
								<td class="class1_td alignleft">
									<html:text property="lianxrqh" styleId="lianxrqh"
										name="accountinfoForm"  onblur="lengthVolidate(7,1);" title="���ţ��4λ" onkeydown="if(event.keyCode==13){if(!lengthVolidate(7,1)){this.focus();return false;}}"
										styleClass="inputField required englishname" maxlength="4" style="width:40px; "/>-<html:text property="phone" styleId="phone"
										name="accountinfoForm"  onblur="lengthVolidate(7,2);" title="�绰�ţ��11λ" onkeydown="if(event.keyCode==13){if(!lengthVolidate(7,2)){this.focus();return false;}}"
										styleClass="inputField required englishname" maxlength="11" style="width:90px; "/>-<html:text property="lianxrfjh" styleId="lianxrfjh"
										name="accountinfoForm"  onblur="lengthVolidate(7,3);" title="�ֻ��ţ��6λ" onkeydown="if(event.keyCode==13){if(!lengthVolidate(7,3)){this.focus();return false;}}"
										styleClass="inputField required englishname" maxlength="6" style="width:50px; "/>
										<span>(����-�绰��-�ֻ���)</span>
									<span style="color: red">*</span><span id="phoneMsg" style="color: red"></span>
								</td>
							</tr>
							<tr style="display:none">
								<td class="class1_td alignright">
									������ϵ��2��
								</td>
								<td class="class1_td alignleft">
									<html:text property="linkman2" styleId="linkman2"
										name="accountinfoForm" 
										styleClass="inputField required englishname" maxlength="15" onchange="lengthVolidate(20,14);" onkeydown="if(event.keyCode==13){if(!lengthVolidate(20,1)){this.focus();return false;}}"
										style="width:180px; " />
									<span id="lianxr2Msg" style="color: red"></span>
								</td>
							</tr>
							<tr  style="display:none">
								<td class="class1_td alignright">
									������ϵ�˵绰2��
								</td>
								
								<td class="class1_td alignleft">
									<html:text property="lianxrqh2" styleId="lianxrqh2"
										name="accountinfoForm"  onblur="lengthVolidate(9,1);" title="���ţ��4λ" onkeydown="if(event.keyCode==13){if(!lengthVolidate(9,1)){this.focus();return false;}}"
										styleClass="inputField required englishname" maxlength="4" style="width:40px; "/>-<html:text property="phone2" styleId="phone2"
										name="accountinfoForm"  onblur="lengthVolidate(9,2);" title="�绰�ţ��11λ" onkeydown="if(event.keyCode==13){if(!lengthVolidate(9,2)){this.focus();return false;}}"
										styleClass="inputField required englishname" maxlength="11" style="width:90px; "/>-<html:text property="lianxrfjh2" styleId="lianxrfjh2"
										name="accountinfoForm"  onblur="lengthVolidate(9,3);" title="�ֻ��ţ��6λ" onkeydown="if(event.keyCode==13){if(!lengthVolidate(9,3)){this.focus();return false;}}"
										styleClass="inputField required englishname" maxlength="6" style="width:50px; "/>
										<span>(����-�绰��-�ֻ���)</span>
									<span id="phone2Msg" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									��������1��
								</td>
								
								<td class="class1_td alignleft">
									<html:text property="fuzr" styleId="fuzr"
										name="accountinfoForm" 
										styleClass="inputField required englishname" maxlength="18" onchange="lengthVolidate(21,14);" onkeydown="if(event.keyCode==13){if(!lengthVolidate(21,1)){this.focus();return false;}}"
										style="width:180px; " />
									<span id="fuzrMsg" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									�������˵绰1��
								</td>
								
								<td class="class1_td alignleft">
									<html:text property="fuzrqh" styleId="fuzrqh"
										name="accountinfoForm"  onblur="lengthVolidate(8,1);" title="���ţ��4λ" onkeydown="if(event.keyCode==13){if(!lengthVolidate(8,1)){this.focus();return false;}}"
										styleClass="inputField required englishname" maxlength="4" style="width:40px; "/>-<html:text property="fuzrdh" styleId="fuzrdh"
										name="accountinfoForm"  onblur="lengthVolidate(8,2);" title="�绰�ţ��11λ" onkeydown="if(event.keyCode==13){if(!lengthVolidate(8,2)){this.focus();return false;}}"
										styleClass="inputField required englishname" maxlength="11" style="width:90px; "/>-<html:text property="fuzrfjh" styleId="fuzrfjh"
										name="accountinfoForm"  onblur="lengthVolidate(8,3);" title="�ֻ��ţ��6λ" onkeydown="if(event.keyCode==13){if(!lengthVolidate(8,3)){this.focus();return false;}}"
										styleClass="inputField required englishname" maxlength="6" style="width:50px; "/>
										<span>(����-�绰��-�ֻ���)</span>
									<span id="fuzrdhMsg" style="color: red"></span>
								</td>
							</tr>
							<tr  style="display:none">
								<td class="class1_td alignright">
									��������2��
								</td>
								<td class="class1_td alignleft">
									<html:text property="fuzr2" styleId="fuzr2"
										name="accountinfoForm" 
										styleClass="inputField required englishname" maxlength="18" onchange="lengthVolidate(22,14);" onkeydown="if(event.keyCode==13){if(!lengthVolidate(22,1)){this.focus();return false;}}"
										style="width:180px;display:none"   />
									<span id="fuzr2Msg" style="color: red"></span>
								</td>
							</tr>
							<tr  style="display:none">
								<td class="class1_td alignright">
									�������˵绰2��
								</td>
								<td class="class1_td alignleft">
									<html:text property="fuzrqh2" styleId="fuzrqh2"
										name="accountinfoForm"  onblur="lengthVolidate(10,1);" title="���ţ��4λ" onkeydown="if(event.keyCode==13){if(!lengthVolidate(10,1)){this.focus();return false;}}"
										styleClass="inputField required englishname" maxlength="4" style="width:40px; "/>-<html:text property="fuzrdh2" styleId="fuzrdh2"
										name="accountinfoForm"  onblur="lengthVolidate(10,2);" title="�绰�ţ��11λ" onkeydown="if(event.keyCode==13){if(!lengthVolidate(10,2)){this.focus();return false;}}"
										styleClass="inputField required englishname" maxlength="11" style="width:90px; "/>-<html:text property="fuzrfjh2" styleId="fuzrfjh2"
										name="accountinfoForm"  onblur="lengthVolidate(10,3);" title="�ֻ��ţ��6λ" onkeydown="if(event.keyCode==13){if(!lengthVolidate(10,3)){this.focus();return false;}}"
										styleClass="inputField required englishname" maxlength="6" style="width:50px; "/>
										<span>(����-�绰��-�ֻ���)</span>
									<span id="fuzrdh2Msg" style="color: red"></span>
								</td>
							</tr>
							<tr >
							<td class="class1_td alignright">
									ӡ������ţ�
									</td>
									<td class="class1_td alignleft">
									<html:text styleId="yinjkbh"  property="yinjkbh" 
										styleClass="inputField account required " onkeydown="if(event.keyCode==13){if(!lengthVolidate(5,22)){this.focus();return false}}"  title="�22λ����"
										onblur="if(lengthVolidate(5,22)){queryYinjk();}"  maxlength="22" style="width:180px;" />
									<span style="color: red">*</span>
									<span id="yinjkbhmsg" style="color: red"></span><span id="yinjkbhmsg2" style="color: green"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">
									��ע��
								</td>
								<td class="class1_td alignleft">
								<html:textarea property="remark" styleId="remark" rows="5" cols="5"
										name="accountinfoForm" styleClass="inputField required remark"
										style="width:380px; height:50px;" onblur="lengthVolidate(2,500);"  onkeydown="if(event.keyCode==13){ if(lengthVolidate(2,200)){event.keyCode=9;}else{return false;}}"></html:textarea>	
								<span id="remarkMsg"  style="color:red"></span>	
								</td>
							
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
				  <button id="commit" type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'"  onkeydown="if(event.keyCode==13){formSubmit();}" class="buttom2">����</button>
				  <button id="update" type="button" style="width:60px;display:none" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'"   class="buttom2">�޸�</button>
				  <button id="save" type="button" style="width:60px;display:none" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" onkeydown="if(event.keyCode==13){formSubmit();}"  class="buttom2">����</button>
				&nbsp;&nbsp;&nbsp;
			 <button id="reset" type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" ><img src="images/back1.gif" width="11" height="11" align="absmiddle">ȡ��</button>
			</div>
		</html:form>	
		<script language="JavaScript" FOR="obj" EVENT="SendEvent(sendMessage)">
	 		var rMessage = SendSesssion("uniDBInterface.jsp",sendMessage,"text");
    		return rMessage;
	</script>
	</body>
</html>