$(document).ready(function() {
	$("#form1").validate( {
		errorLabelContainer : "#error div.error",
		wrapper : "li",
		submitHandler : function(form) {
			if (confirm('你确定要修改归属关系吗?'))
				form.submit();
		}
	});
	$('#orgCode').blur(function() {
		var code = $("#orgCode").val();
		$.post("orgManage.do?method=getOrgname", {
			code : code
		}, function(data, textStatus) {
			if (textStatus = "success") {
				if(data.indexOf('<html>')!=-1){
					window.location.href='/timeOutlogin.jsp';
					return;
				}
				$("#parentname").empty();
				$("#parentname").html(data.split(",")[0]);
			}
		}, "text");
	})
})