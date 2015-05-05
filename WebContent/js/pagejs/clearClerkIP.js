$(document).ready(function() {
	$("#account").focus();
	$("#form1").validate( {
		errorLabelContainer : "#error div.error",
		wrapper : "li",
		submitHandler : function(form) {
			if (confirm("确定要清除该柜员？")){
				if($("#clerkIP").html()=="未锁定"){
					alert("该柜员未被锁定");
					return;
				}
				form.submit();
			}
		}
	})
});

function startRequest() {
	if(!checkclerkcode())return;

	var clerkCode = $("#clerknum").val();
	if(clerkCode==null||clerkCode.length==0){
		return;
	}
	$.post("clearClerkIP.do?method=get", {
		clerkCode : clerkCode
	}, function(data, textStatus) {
		if (textStatus = "success") {
			var accountinfo = data.split(",");
			$("#clerkName").empty(" ");
			$("#clerkOrg").empty(" ");
			$("#clerkIP").empty(" ");
			$("#error_clerk").empty(" ");

			$("#clerkName").html(" "+accountinfo[0]);
			$("#clerkOrg").html((accountinfo[1]==undefined?" ":accountinfo[1]));
			$("#clerkIP").html((accountinfo[2]==undefined?" ":accountinfo[2]));
			$("#error_clerk").html((accountinfo[3]==undefined?" ":accountinfo[3]));
		}
	}, "text");
}

function checkclerkcode(){
	$("#clerkName").empty(" ");
	$("#clerkOrg").empty(" ");
	$("#clerkIP").empty(" ");
	$("#error_clerk").empty(" ");

		var clerkcode=$("#clerknum").val();
		var reg = /^\d{5,7}$/;
		if(!reg.test(clerkcode)){
			$("#error_clerk").html("柜员号格式不正确");
			return false;
			}else{
			$("#error_clerk").html('');
		return true;
		}
	
}
function submitEnter() {
	if(!checkclerkcode())return;
	startRequest();
}