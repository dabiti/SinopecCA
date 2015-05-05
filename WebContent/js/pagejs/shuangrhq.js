$(document).ready(function() {
	   //设置弹出窗口位置\大小
	   {
		   window.dialogTop = 200+"px"; 
		   window.dialogLeft = 300+"px"; 
		   window.dialogHeight = 200+"px"; 
		   window.dialogWidth = 260+"px"; 
	   }  
	   $("#form1").validate({
	   errorLabelContainer:"#error div.error",
	   wrapper:"li",
	   submitHandler:function(form){
		   validate();
		   return;
	   }
}) 
});

var error_counts = 0;
function validate(){
   var clerknum = $("#clerknum").val();
   var password = $("#password").val();
   var obj = window.dialogArguments;
   $.post("clerkManage.do?method=shuangrhq",{clerknum:clerknum,password:password,titleName:encodeURI(obj.titleName),account:obj.account},function (data,textStatus){
			if(data==0)
			{
				alert("会签柜员不存在!");
			}
			if(data==4)
			{
				alert("会签柜员不能为登录柜员!");
			}
			if(data==5)
			{
				alert("会签柜员还未登录修改密码,不能双人会签!");
			}
			if(data==1)
			{
				alert("会签成功!");
				window.returnValue='1';
	         	window.close();
			}
			if(data==2)
			{
				alert("密码错误!");
			}
			if(data==3)
			{
				alert("会签柜员与登录柜员不属于同一机构!");
			}
			error_counts++;
			if(error_counts>=3)
			{
				alert("双人会签输入错误三次,会签失败,关闭窗口!");
				window.returnValue='0';
	         	window.close();
			}
   }, "text");
}