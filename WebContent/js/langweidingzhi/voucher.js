$(document).ready(function(){doLangw();})

/*∆æ÷§±‡∫≈*/
{
	var  credencetype_maxlength='2';
	var  credencetype_style='width:150px;';
}


/*’“’¬«¯”Ú ∑ß÷µ«¯”Ú*/
{
	var  sealrect_maxlength='20';
	var  sealrect_style='width:150px;';
}

function doLangw(){

	if(document.getElementById('credencetype')!=null)
	{
		$("#credencetype").attr("maxlength",credencetype_maxlength);
		$("#credencetype").attr("style",credencetype_style);
	}
	
	if(document.getElementById('sealrect')!=null)
	{
		$("#sealrect").attr("maxlength",sealrect_maxlength);
		$("#sealrect").attr("style",sealrect_style);
	}
	
	if(document.getElementById('rvalverect')!=null)
	{
		$("#rvalverect").attr("maxlength",sealrect_maxlength);
		$("#rvalverect").attr("style",sealrect_style);
	}
}