$(document).ready(function() {

//	$("#form1").validate( {
//		errorLabelContainer : "#error div.error",
//		wrapper : "li",
//		submitHandler : function(form) {
//		alert(orgFlag);
//			if(orgFlag){
//				if (confirm('��������֮�󣬱����������µĹ�Ա��Ϣ������ȫɾ�����˻���Ϣ�����������»����£���ȷ��Ҫ����������?')){
//					form.submit();
//					}
//				}
//			}
//	});
});
var xmlHttp;
var type1;
var orgFlag=false;
function createXMLHttpRequest() {
	if (window.ActiveXObject) {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	} else if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();
	}
}
function startRequest(obj, temp) {
	type1 = temp;
	createXMLHttpRequest();
	xmlHttp.onreadystatechange = handleStateChange;
	var id = obj.id;
	var code = document.form1.newcode.value;
	xmlHttp.open("GET", "orgManage.do?method=getOrgname&code=" + code, true);
	xmlHttp.send(null);
}

function formSubmit(){
	if(orgFlag){
		if (confirm('��������֮�󣬱����������µĹ�Ա��Ϣ������ȫɾ�����˻���Ϣ�����������»����£���ȷ��Ҫ����������?')){
			$("#form1").submit();
			}
		}
}
function getOrgName(){
	$("#orgMsg").text("");
	$("#abc").text('');
	var orgCode=$("#orgCode").val();
	var oldOrgCode=$("#oldOrgCode").text();
	if(orgCode==null||orgCode.length==0){
		orgFlag =false;
		return;
		}
	if(orgCode==oldOrgCode){
		$("#orgMsg").text("�»���������ԭ��������ͬ");
		orgFlag =false;
		return;
	}
	var reg= /(^[0-9]{6}$)|(^[0-9]{4}$)/;
	if(!reg.test(orgCode)){
		$("#orgMsg").text("�����Ÿ�ʽ����ȷ");
		orgFlag =false;
		return;
	}
	$.post("yinjkOperate.do?method=getOrgInfo",{orgCode:orgCode},function(data, textStatus){
	if(textStatus=="success"){
		var result=data;
		var info=result.split(',');
		if(info[0]=="0"){
			$("#orgMsg").text("����������");
			 orgFlag=false;
			return;
			}
		if(info[2]!=3){
			$("#orgMsg").text("ֻ������֧�м���г���");
			orgFlag=false;
			$("#orgCode").focus();
			return;
			}
		$("#orgName").html(info[1]);
		 orgFlag=true;
		}
	},"text");
}

function handleStateChange() {
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			var orgname = xmlHttp.responseText;
			var responseDiv;

			if (type1 == 1) {
				responseDiv = document.getElementById("newname");
			} else {
				responseDiv = document.getElementById("newname2");
			}
			if (responseDiv.hasChildNodes())
				responseDiv.removeChild(responseDiv.childNodes[0]);
			var responseText = document.createTextNode(orgname.split(",")[0]);
			orgWdflag=orgname.split(",")[1]=="3"?true:false;
			if(!orgWdflag){
				
			}
			responseDiv.appendChild(responseText);
		}
	}
}