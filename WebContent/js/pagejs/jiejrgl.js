		  var today_id="";//��������ID
		  
		  $(document).ready(function(){
			  initYearDateString(new Date().getFullYear());
			});
		  function initYearDateString(year){
			  if(validate(year))return;
			  $("#year").val(year);
			  
			  var url = "ajax.do?method=getJiejrgl&year="+year;
			  var monthArray = doJSON(url);
			  var month = monthArray.split(",");

			  for(var n=1; n<=month.length ;n++)
			  {
				    var str="";
				    var counts = 1;
				    var counts_bool = false;
				  	for(var i=1 ; i<= month[n-1].length ;i++)
				  	{
					  str = month[n-1].substring(i-1,i);
					  if(counts_bool)
					  {
							counts++;
					  }
					  
					  var id = "month_" + n + "_" + i;
					  
					  //ȡ������¼�
					  $("#"+id).unbind("click")
					  
					  if(str == "3")
					  {
						  setBackColorForJiejr(id,'#F9F900');
						  counts_bool = true;
						  today_id=id;
					  }
					  
					  if(str == "1")
					  {
						  setBackColorForJiejr(id,'#ff7575');
						  counts_bool = true;
					  }
					  
					  if(str == "0")
					  {
						  setBackColorForJiejr(id,'#FFFFFF');
						  counts_bool = true;
					  }
					  
					  if(str != "2")
					  {	  addClick(id);
						  $("#month_"+n+"_"+i).html(counts);
						  $("#"+id).attr('title',year+"��"+n+"��"+counts+"��");
					  }else{
						  $("#month_"+n+"_"+i).html(null);
						  setBackColorForJiejr(id,'#C0C0C0');
					  }
				  	}
			  }
		  }
		  
		  //���� Ԫ��ID ������ɫ ���ڼ��գ�
		  function setBackColorForJiejr(id,bgcolor){
			   $("#"+id).attr('bgcolor',bgcolor);
		  }

		  //��Ԫ����ӵ���¼�
		  function addClick(id){
			  $("#"+id).click(function(){
					 var val = $("#"+id).attr('bgcolor');
					 if('#ff7575' == val)
					 {	
						if(id == today_id)
						{
							$("#"+id).attr('bgcolor','#F9F900');
						}else{
							$("#"+id).attr('bgcolor','');
						}
					 }else{
						$("#"+id).attr('bgcolor','#ff7575');
					 }
				   }); 
		  }

		  //���ʱ���ʽ��֤
		  function validate(year){
			  if(!/^\d*$/.test(year))
			  {
				alert("������Ϸ���ݸ�ʽ,���磺2010");
				return true; 
			  }
			  if(year>10000||year<1000)
			  {
				alert("��ݷ�Χ���Ϸ����Ϸ���Χ��1000-9999");
				return true;
			  }
		  }
		  
		  //����ajax����
		  function doJSON(url){
			 var xmlHttp;
			 if  (window.ActiveXObject)  {
			       xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			 }  
			 else if(window.XMLHttpRequest)  {
			       xmlHttp = new XMLHttpRequest();
			 } 
		     xmlHttp.open("post",url,false);
		     xmlHttp.send(null);
		     if (xmlHttp.readyState == 4)
		     {
		       try{
		      	 var content = xmlHttp.responseText;
		      	 return content;
		       }catch(e){
		          e.printStackTrace();
		       }  	
		     }
		  }
		  
		  function  backYear(){
			   var year = $("#year").val();
			   if(validate(year))return;
			   year = parseInt(year)-1;
			   today_id="";
			   initYearDateString(year);
			   $("#year").val("");
			   $("#year").val(year);
		  }
		  
		  function  nextYear(){
			  var year = $("#year").val();
			  if(validate(year))return;
			  year = parseInt(year)+1;
			  today_id="";
			  initYearDateString(year);
			  $("#year").val("");
			  $("#year").val(year);
			  
		  }

		  function  save(){
			  var year = $("#year").val();
			  year = parseInt(year)+1;
			  initYearDateString(year);
			  $("#year").val("");
			  $("#year").val(year);
		  }

		  function getMonthString(){
			    var monthString="";
			  	for(var i=1 ; i<=12 ; i++)
				{
			  		for(var t=1; t <=38; t++)
				  	{   
			  			var id = "month_" + i + "_" + t;
			  			var bgcolor = $("#"+id).attr('bgcolor');

						if('#c0c0c0' == bgcolor)
						{
							continue;
						}
					  				
						if('#ff7575' == bgcolor || '#999999' == bgcolor)
						{	
							monthString = monthString + "1";
						}else{
							monthString = monthString + "0";
						}
					}
					
					if(i!=12)
					{
						monthString = monthString + ",";
					}
			  	}
			  	try{
			  		 var year = $("#year").val();
			  		 var url = "ajax.do?method=saveJiejrgl&year="+year+"&monthString="+monthString;
			  		 var b_messae = doJSON(url);
			  		 if(b_messae="1"){
				  		 
			  			 alert('����ɹ�!');
			  		 }else{
			  			 alert('����ʧ��!');
			  		 }
			  	}catch(e){
				  	alert('����ʧ��!');
			  	}
			}

		  function reset(){
			  try{
			  		 var year = $("#year").val();
			  		 var url = "ajax.do?method=deletJiejrgl&year="+year;
			  		 var b_messae = doJSON(url);
			  		 initYearDateString(year);
			  	}catch(e){
				  	alert('����ʧ��!');
			  	}
		  }