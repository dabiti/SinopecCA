$(document).ready(function() {
			 $("#form1").validate({
			   onsubmit:true,
			   onfocusout:true,
			   onkeyup :true,
			   errorLabelContainer:"#error div.error",
			   wrapper:"li"
			   });
			   });