function getFingerMsg(){
			//���ô���
			dtm.SetPara("COM4");
		 	//��ȡָ������
		  	var strTZ = dtm.GetFeature();
		  	
	   		if(strTZ == "")
	   	 {
	    	alert("�ɼ�ָ������ʧ��!");
	    	return ;
	   	 }
	   		var factoryId=dtm.GetFactoryId();
	   		if(factoryId==null||factoryId.length==0){
	   			factoryId="1";
			  	}
		  	strTZ = factoryId+strTZ;
	   		return strTZ;
}

function checkFingerMsg(clerkCode,fingerMsg,passwd,checkType){
	 var returnData;
	 $.ajax({
		 	 async:false,
		 	 url:"login.do?method=authorized",
			 dataType:"text",
			 data: {clerkCode:clerkCode,fingerMsg:fingerMsg,passwd:passwd,titleName:encodeURI(""),checkType:encodeURI(checkType),account:"",quanxbs:""},
			 cache:false,
			 success:function (data,textStatus){
				 if(textStatus=="success")
					 returnData=data;
					// return data;//0��Ա������ 1 ͨ�� 2δͨ��3 ������� 5��֤ʧ�ܣ����ӳ���
			 }
	 	}
	 )
	 return returnData;
};