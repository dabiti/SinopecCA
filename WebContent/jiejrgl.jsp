<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>�ڼ��չ���</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script language="javascript">
		  var today_id="";//��������ID
		  
		  $(document).ready(function(){
			  initYearDateString(new Date().getFullYear());
			});
		  function initYearDateString(year){
			  if(validate(year))return;
			  $("#year").val(year);
			  
			  var url = "ajax.do?method=getJiejrgl&year="+year;
			  var monthArray = doJSON(url);
			  //��¼��ʱ ����HTMLҳ�� ��Ҫ��ʾ�û� �������ݲ��ᳬ��12*31������
			  if(monthArray.length>500)
			 	 alert("��ȡ����ʧ��,���ܵ�¼��ʱ,�볢�����µ�¼!");
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
			  $("#"+id).css('background-color',bgcolor);
		  }

		  //��Ԫ����ӵ���¼�
		  function addClick(id){
			  $("#"+id).click(function(){
					 var val = $("#"+id).css('background-color');
					 if('#ff7575' == val)
					 {	
						if(id == today_id)
						{
							$("#"+id).css('background-color','#F9F900');
						}else{
							$("#"+id).css('background-color','');
						}
					 }else{
						$("#"+id).css('background-color','#ff7575');
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
			  			var bgcolor = $("#"+id).css('background-color');

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
		</script>
	</head>
	<body>
	<br>
	<center><b>�ڼ��չ���</b></center>
	<TABLE border='1' align="right">
		<TD bgcolor="#ff7575">&nbsp;&nbsp;</TD>
		<TD>�ڼ���</TD>
		<TD bgcolor="#FFFFFF">&nbsp;&nbsp;</TD>
		<TD>��Ȼ��</TD>
		<TD bgcolor="#F9F900">&nbsp;&nbsp;</TD>
		<TD>��������</TD>
	</TABLE>
	<br/><br/>
	<span>
	<center>
		<input type="button" onclick="backYear()" value="��һ��"/>
			<input id="year" name="year" type="text" size="4" maxLength="4" onkeydown='if(event.keyCode==13){initYearDateString(this.value)}'/>
		<input type="button" onclick="nextYear()" value="��һ��"/></center>
	</span>
	<table height="100%" Width="95%"  style="border: solid 1px #ff0000;" cellpadding="0" align='center'>
	<tr class="holiday-background">
			<td>�·�\����</td>
		    <td>һ</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
			<td>һ</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>һ</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>һ</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>һ</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>��</td>
		    <td>һ</td>
		    <td>��</td>
		    <td>��</td>
 		 </tr>
	<c:forEach var="t" begin="1" end="12">
 	 <tr>
 			<td rowspan="2" class="holiday-background">${t}�·�</td>
 		 	<c:forEach var="r" begin="1" end="38">
		   	 	 <td id="month_${t}_${r}" onmouseover="this.style.cursor='pointer'" class="holiday-date">${r}</td>
		    </c:forEach>
 	 </tr>
 	 <tr>
 	 </tr>
	</c:forEach> 
	</table>
		<center>
		<input type="button" onclick="getMonthString()" value="����"/> 
		<input type="button" onclick="initYearDateString($('#year').val())" value="ȡ��"/>
		<input type="button" onclick="reset()" value="����"/>
		</center>
	</body>
</html>