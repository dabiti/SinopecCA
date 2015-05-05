$(document).ready(function() {
	var date = '${clerk.loginDate}';
	$("#begindate").val(date.substr(0, 8) + '01');
	$("#sealchecklogForm").validate( {
			errorLabelContainer : "#error div.error",
			wrapper : "li",
			submitHandler : function(form) {
				form.submit();
			},
			errorPlacement : function(error, element) {
				var name = element.attr("name");
				if (name == "account" || name == "clerknum")
					error.insertAfter("#error");
				else
					error.insertAfter(element);
			}
		});
	})