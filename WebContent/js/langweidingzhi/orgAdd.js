$(document).ready(function(){doLangw();})

/*机构编号*/
{
	var  code_maxlength='3';
	var  code_style='width:150px;';
}

/*人行支付系统行号*/
{
	var  paymentCode_maxlength='12';
	var  paymentCode_style='width:150px;';
}

function doLangw(){
	
	if(document.getElementById('code')!=null)
	{
		$("#code").attr("maxlength",code_maxlength);
		$("#code").attr("style",code_style);
	}
	
	if(document.getElementById('paymentCode')!=null)
	{
		$("#paymentCode").attr("maxlength",paymentCode_maxlength);
		$("#paymentCode").attr("style",paymentCode_style);
	}
}