$(document).ready(function() {
	$("#form1").validate( {
		errorLabelContainer : "#error div.error",
		wrapper : "li",
		submitHandler : function(form) {
			if (confirm('��ȷ��Ҫ�޸Ĺ�����ϵ��?'))
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