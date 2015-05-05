function autoComplete(){
	var account = $("#account").val();
	if(account.length==5){
	//È¡Ïû½¹µã
	$(":input").unautocomplete();
	$.post("autocomplete.do?method=list",{account:account},function(data,textStatus){
		if (textStatus = "success")
		{
			var temp= data.split(",");
			$("#account").autocomplete(temp);
	}},"text");
	}
}

var isComplete=0;
function autoCompleteForTableLine(inputId,parameters){
	if(isComplete==0)
	{
		$.post("autocomplete.do?method=getTableLine",parameters,function(data,textStatus){
			if (textStatus = "success")
			{
				var temp= data.split(",");
				$("#"+inputId).autocomplete(temp,{minChars:0});
			}
		},"text");
		isComplete++;
	}
}


var isZhid=0;
function autoCompleteForZhidMC(inputId,parameters){
	if(isZhid==0)
	{
		$.post("autocomplete.do?method=getZhidMC",parameters,function(data,textStatus){
			if (textStatus = "success")
			{
				var temp= data.split(",");
				$("#"+inputId).autocomplete(temp,{minChars:0});
			}
		},"text");
		isZhid++;
	}
}