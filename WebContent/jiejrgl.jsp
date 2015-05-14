<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>节假日管理</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script language="javascript">
		  var today_id="";//当天日期ID
		  
		  $(document).ready(function(){
			  initYearDateString(new Date().getFullYear());
			});
		  function initYearDateString(year){
			  if(validate(year))return;
			  $("#year").val(year);
			  
			  var url = "ajax.do?method=getJiejrgl&year="+year;
			  var monthArray = doJSON(url);
			  //登录超时 返回HTML页面 需要提示用户 正常数据不会超过12*31数据量
			  if(monthArray.length>500)
			 	 alert("获取数据失败,可能登录超时,请尝试重新登录!");
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
					  
					  //取消点击事件
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
						  $("#"+id).attr('title',year+"年"+n+"月"+counts+"日");
					  }else{
						  $("#month_"+n+"_"+i).html(null);
						  setBackColorForJiejr(id,'#C0C0C0');
					  }
				  	}
			  }
		  }
		  
		  //更改 元素ID 背景颜色 （节假日）
		  function setBackColorForJiejr(id,bgcolor){
			  $("#"+id).css('background-color',bgcolor);
		  }

		  //对元素添加点击事件
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

		  //年份时间格式验证
		  function validate(year){
			  if(!/^\d*$/.test(year))
			  {
				alert("请输入合法年份格式,例如：2010");
				return true; 
			  }
			  if(year>10000||year<1000)
			  {
				alert("年份范围不合法，合法范围：1000-9999");
				return true;
			  }
		  }
		  
		  //发起ajax请求
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
				  		 
			  			 alert('保存成功!');
			  		 }else{
			  			 alert('保存失败!');
			  		 }
			  	}catch(e){
				  	alert('保存失败!');
			  	}
			}

		  function reset(){
			  try{
			  		 var year = $("#year").val();
			  		 var url = "ajax.do?method=deletJiejrgl&year="+year;
			  		 var b_messae = doJSON(url);
			  		 initYearDateString(year);
			  	}catch(e){
				  	alert('重置失败!');
			  	}
		  }
		</script>
	</head>
	<body>
	<br>
	<center><b>节假日管理</b></center>
	<TABLE border='1' align="right">
		<TD bgcolor="#ff7575">&nbsp;&nbsp;</TD>
		<TD>节假日</TD>
		<TD bgcolor="#FFFFFF">&nbsp;&nbsp;</TD>
		<TD>自然日</TD>
		<TD bgcolor="#F9F900">&nbsp;&nbsp;</TD>
		<TD>当天日期</TD>
	</TABLE>
	<br/><br/>
	<span>
	<center>
		<input type="button" onclick="backYear()" value="上一年"/>
			<input id="year" name="year" type="text" size="4" maxLength="4" onkeydown='if(event.keyCode==13){initYearDateString(this.value)}'/>
		<input type="button" onclick="nextYear()" value="下一年"/></center>
	</span>
	<table height="100%" Width="95%"  style="border: solid 1px #ff0000;" cellpadding="0" align='center'>
	<tr class="holiday-background">
			<td>月份\星期</td>
		    <td>一</td>
		    <td>二</td>
		    <td>三</td>
		    <td>四</td>
		    <td>五</td>
		    <td>六</td>
		    <td>日</td>
			<td>一</td>
		    <td>二</td>
		    <td>三</td>
		    <td>四</td>
		    <td>五</td>
		    <td>六</td>
		    <td>日</td>
		    <td>一</td>
		    <td>二</td>
		    <td>三</td>
		    <td>四</td>
		    <td>五</td>
		    <td>六</td>
		    <td>日</td>
		    <td>一</td>
		    <td>二</td>
		    <td>三</td>
		    <td>四</td>
		    <td>五</td>
		    <td>六</td>
		    <td>日</td>
		    <td>一</td>
		    <td>二</td>
		    <td>三</td>
		    <td>四</td>
		    <td>五</td>
		    <td>六</td>
		    <td>日</td>
		    <td>一</td>
		    <td>二</td>
		    <td>三</td>
 		 </tr>
	<c:forEach var="t" begin="1" end="12">
 	 <tr>
 			<td rowspan="2" class="holiday-background">${t}月份</td>
 		 	<c:forEach var="r" begin="1" end="38">
		   	 	 <td id="month_${t}_${r}" onmouseover="this.style.cursor='pointer'" class="holiday-date">${r}</td>
		    </c:forEach>
 	 </tr>
 	 <tr>
 	 </tr>
	</c:forEach> 
	</table>
		<center>
		<input type="button" onclick="getMonthString()" value="保存"/> 
		<input type="button" onclick="initYearDateString($('#year').val())" value="取消"/>
		<input type="button" onclick="reset()" value="重置"/>
		</center>
	</body>
</html>