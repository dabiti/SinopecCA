//����ҵ��
function startRequest() {
	var account = $("#account").val();
	var math = Math.random();
	$.post("accountinfo.do?method=getAccount", {math:math, account:account}, function (data, textStatus) {
		if (textStatus = "success") {
			var accountinfo = data.split(",");

			$("#accountname").empty();
			$("#allexchange").empty();
			$("#englishname").empty();
			
			$("#accountname").html('&nbsp'+accountinfo[0]);
			$("#allexchange").html('&nbsp'+accountinfo[1]);
			$("#englishname").html('&nbsp'+accountinfo[2]);
		}
	}, "text");
}
function startRequestForNet() {
	var account = $("#account").val();
	var math = Math.random();
	$.post("accountinfo.do?method=getAccountForNet", {math:math, account:account}, function (data, textStatus) {
		if (textStatus = "success") {
			var accountinfo = data.split(",");
			
			$("#accountname").html('&nbsp'+'');
			$("#allexchange").html('&nbsp'+'');
			$("#startdate1").html('&nbsp'+'');
			$("#accountstate").html('&nbsp'+'');
			$("#hum").html('&nbsp'+' ');
			if(accountinfo[0]==''||accountinfo[0]==null){alert('�˻������ڻ�û�в�ѯ�˻���Ϣ!');return};
			if(accountinfo[4]==1){alert('�˻�['+account+']Ϊ���˻�����������ҵ��!');return};
			
			$("#accountname").html(accountinfo[0]);
			$("#allexchange").html(accountinfo[1]);
			$("#startdate1").html(accountinfo[2]);
			$("#accountstate").html(accountinfo[3]);
		}
	}, "text");
}


function getHumForNet() {
	var zhangh = $("#zhangh").val();
	var math = Math.random();
	$.post("pingz.do?method=getZhanghbHum", {math:math, zhangh:zhangh}, function (data, textStatus) {
		if (textStatus = "success") {
			var hum = data;
			$("#hum").empty();
			$("#hum").html(hum);
		}
	}, "text");
}

function deleteOrUpdate(method,str0){
	var pingzh=str0;
	var math = Math.random();
	$.post("pingz.do?method="+method, {math:math, pingzh:pingzh}, function (data, textStatus) {
		if (textStatus = "success") {
			if(data=="fail"){
				alert("ɾ�������з�������");
			}
		}
	}, "text");
}

function bathdeleteOrUpdate(method,str0,selectString){
	selectString = encodeURI(selectString);
	var pingzh=str0;
	var math = Math.random();
	
	$.ajax({  
			type : "post",  
			url : "pingz.do?method="+method,  
			data : {math:math, pingzh:pingzh,selectString:selectString},  
			async : false,  
			success : function(data){  
				alert(data);
			}  
		});  

}
function updateZhuant(method,str0,str1){
	var qispzh=str0;
	var zhongzpzh=str1;
	var math = Math.random();
	$.post("pingz.do?method="+method, {math:math, qispzh:qispzh,zhongzpzh:zhongzpzh}, function (data, textStatus) {
		if (textStatus = "success") {
			if(data=="fail"){
				alert("����״̬�����г����쳣��");
			}
		}
	}, "text");
}
function submitEnter() {
	if (window.event.keyCode == 13) {
		startRequest();
	}
}
function updateZhuant(method,str0,str1,dayfwmsg,count){
	var qispzh=str0;
	var zhongzpzh=str1;
	var math = Math.random();
	$.post("pingz.do?method="+method, {math:math, qispzh:qispzh,zhongzpzh:zhongzpzh,dayfwmsg:dayfwmsg,count:count}, function (data, textStatus) {
		if (textStatus = "success") {
			if(data=="fail"){ 
				alert("����״̬�����г����쳣��");
			}
		}
	}, "text");
}
function resetInfo() {
	$("#accountname").html('&nbsp'+' ');
	$("#allexchange").html('&nbsp'+' ');
	$("#englishname").html('&nbsp'+' ');
	$("#accountstate").html('&nbsp'+' ');
	$("#hum").html('&nbsp'+' ');
}