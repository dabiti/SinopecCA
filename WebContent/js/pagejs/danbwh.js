$(document).ready(function() {
	$("#form1").validate({
		onsubmit : true,
		submitHandler : function(form) {
			form.submit();
		}
	});
})

function changInput_size(input) {
	var size = input.value.length;
	if (size < 17) {
		size = 120;
	} else {
		if (size <= 100)
			$(input).attr("style", 'width:' + size * 8 + 'px;');
	}
	return;
}

function onsubmitForForm()
{
	if(confirm('È·¶¨É¾³ý¼ÇÂ¼?'))
	{
		return true;
	}else{
		return false;
	}
}