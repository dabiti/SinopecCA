function getFingerMsg(){
			//设置串口
			dtm.SetPara("COM4");
		 	//获取指纹特征
		  	var strTZ = dtm.GetFeature();
		  	
	   		if(strTZ == "")
	   	 {
	    	alert("采集指纹特征失败!");
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
					// return data;//0柜员不存在 1 通过 2未通过3 密码过期 5验证失败，连接出错
			 }
	 	}
	 )
	 return returnData;
};