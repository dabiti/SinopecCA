$(document).ready(function(){doLangw();})

/*柜员编号*/
{
	var  code_maxlength='4';
	var  code_style='width:150px;';
}

/*柜员密码*/
{
	var  password_maxlength='8';
	var  password_style='width:150px;';
	
	var  password1_maxlength='8';
	var  password1_style='width:150px;';
}

function doLangw(){
	
	if(document.getElementById('code')!=null)
	{
		$("#code").attr("maxlength",code_maxlength);
		$("#code").attr("style",code_style);
	}
	
	if(document.getElementById('password')!=null)
	{
		$("#password").attr("maxlength",password_maxlength);
		$("#password").attr("style",password_style);
	}
	
	if(document.getElementById('password1')!=null)
	{
		$("#password1").attr("maxlength",password_maxlength);
		$("#password1").attr("style",password_style);
	}
}