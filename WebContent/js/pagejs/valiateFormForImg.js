	$(document).ready(function() {
			$("#form1").validate({
   			onsubmit:true,
   			onfocusout:true,
   			onkeyup :true,
   			errorLabelContainer:"#error div.error",
   			wrapper:"li",
   			submitHandler:function(form){
			form.submit();
			$("#tu").html('<table align="center"><tr><td><img border="1" src="images/appInstall_animated.gif"/></td></tr></table>');
			$("#abc").empty();
 	 	 }
  	 	});
  	 	})