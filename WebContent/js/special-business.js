//锟斤拷锟斤拷业锟斤拷
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
			if(accountinfo[0]==''||accountinfo[0]==null){alert('账户不存在或没有查询账户信息!');return};
			if(accountinfo[4]==1){alert('账户['+account+']为主账户，不能做此业务!');return};
			
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
				alert("删除过程中发生错误");
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
				alert("更新状态过程中出现异常！");
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
				alert("更新状态过程中出现异常！");
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