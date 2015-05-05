

function validate(key) {
	var reg;
	switch (key) {
	case 0:
		var yinjkh = $("#yinjkh").val();
		reg = /^[0-9]{7,22}$/;
		if (!reg.test(yinjkh)) {
			$("#yinjkhMsg").text("凭证号格式不正确");
			// $("#yinjkh").focus();
			return false;
		}
		$("#yinjkhMsg").text("");
		return true;
		break;
	case 1:
		var num = $("#num").val();
		reg = /^[1-9]{1}[0-9]{0,4}$/;
		if (!reg.test(num)) {

			$("#numMsg").text("数量格式不正确");
			// $("#num").focus();
			return false;
		}
		$("#numMsg").text('');
		return true;
		break;
	case 2:
		var orgCode = $("#receiveOrgCode").val();
		reg = /^[0-9]{6}$/;
		if (!reg.test(orgCode)) {
			$("#orgMsg").text("领用机构号格式不正确");
			// $("#receiveOrgCode").focus();
			return false;
		}
		$("#orgMsg").text('');
		return true;
		break;
	case 3:
		var clerkCode = $("#lyClerkCode").val();
		reg = /^[0-9]{7}$/;
		if (!reg.test(clerkCode)) {
			$("#clerkMsg").text("柜员号格式不正确");
			// $("#lyClerkCode").focus();
			return false;
		}
		$("#clerkMsg").text('');
		return true;
		break;
	case 4 :
		var hum =$("#hum").val();
		
		if(hum==null||hum.length==0){
			$("#humMsg").text("户名不能为空");
			return false ;
		}

		$("#humMsg").text('');
		return true;
		break;
	case 5 :
		var kehjl1=$("#kehjl1").val();
		if(kehjl1==null||kehjl1.length==0){
			$("#kehjl1Msg").text("客户经理1不能为空");
			return false ;
		}
		$("#kehjl1Msg").text('');
		return true;
		break;
	case 6 :
		var kehjl2=$("#kehjl2").val();
		if(kehjl2==null||kehjl2.length==0){
		$("#kehjl2Msg").text("客户经理2不能为空");
		return false ;
		}
		$("#kehjl2Msg").text('');
		return true;
		break;
	}
}

function formSubmit(){
	$("#form1").submit();
	
}

function submitForm() {
	if (!validate(0))
		return;
	if (!validate(1))
		return;
formSubmit();
	
}

function resetData() {

	$("#yinjkh").val('');
	$("#num").val('');
	$("#endYinjkh").val('');
	$("#receiveOrgCode").val('');
	$("#receiveOrgName").val('');
	$("#lyClerkCode").val('');
	
	$("#hum").val('');
	$("#kehjl1").val();
	$("#kehjl2").val();
	
	$("#yinjkhMsg").html('');
	$("#numMsg").html('');
	$("#orgMsg").html('');
	$("#clerkMsg").html('');
	$("#humMsg").html('');
	$("#kehjl1Msg").html('');
	$("#kehjl2Msg").html('');
	$("#abc").html('');
	$("#abc1").html('');
}

function addNum() {
	var yinjkh = $("#yinjkh").val();
	var num = $("#num").val();

	var reg = /^[1-9]{1}[0-9]{0,4}$/;
	if (!reg.test(num)) {
		return;
	}
	reg = /^[0-9]{7,22}$/;
	if (!reg.test(yinjkh)) {

		return;
	}
	if (yinjkh == null || yinjkh.length == 0 || num == null || num.length == 0)
		return;
	var subyinjkh = yinjkh.substring(yinjkh.length - 6);
	var subyinjkhmain = yinjkh.substring(0, yinjkh.length - 6);
	var result = eval(subyinjkh + "+" + num + "-1");
	$("#endYinjkh").val(subyinjkhmain + result.toString());
}
