$(document).ready(function() {
	   //���õ�������λ��\��С
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
				alert("��ǩ��Ա������!");
			}
			if(data==4)
			{
				alert("��ǩ��Ա����Ϊ��¼��Ա!");
			}
			if(data==5)
			{
				alert("��ǩ��Ա��δ��¼�޸�����,����˫�˻�ǩ!");
			}
			if(data==1)
			{
				alert("��ǩ�ɹ�!");
				window.returnValue='1';
	         	window.close();
			}
			if(data==2)
			{
				alert("�������!");
			}
			if(data==3)
			{
				alert("��ǩ��Ա���¼��Ա������ͬһ����!");
			}
			error_counts++;
			if(error_counts>=3)
			{
				alert("˫�˻�ǩ�����������,��ǩʧ��,�رմ���!");
				window.returnValue='0';
	         	window.close();
			}
   }, "text");
}