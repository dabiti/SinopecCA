var showTime='showTime';
function syncSvrTime(startTime,showTime){
	var time = document.getElementById(startTime).value;
	this.showTime=showTime;
	var today=null;
	if(time==""||time==null)
		today = new Date();
	else
		today = new Date(time.substring(0,4),time.substring(5,7)-1,time.substring(8,10),time.substring(11,13),time.substring(14,16),time.substring(17,19));
	function initArray(){
		this.length=initArray.arguments.length
		for(var i=0;i<this.length;i++)
		this[i+1]=initArray.arguments[i]
	}
	var d=new initArray("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
	_year = today.getYear();
	_month = today.getMonth()+1;
	_date = today.getDate();
	_week = d[today.getDay()+1];
	strdate = _year+"年"+_month+"月"+_date+"日 "+_week;
  	clock(strdate,today.getHours(),today.getMinutes(),today.getSeconds());
}
function clock(_date,hour,minute,second){
        var intHours   = hour;
        var intMinutes = minute;
        var intSeconds = second;
        if (intHours < 10)
                hours = "0"+intHours+":";
         else 
                hours = intHours+":";
        if (intMinutes < 10)
                minutes = "0"+intMinutes+":";
         else 
                minutes = intMinutes+":";
        if (intSeconds < 10) 
                seconds = "0"+intSeconds+" ";
         else 
                seconds = intSeconds+" ";
        intSeconds += 1;    
        if (intSeconds>=60){
                intSeconds = 0;
                intMinutes +=1;
        	}
        if (intMinutes>=60){
                intHours   += 1;
                intSeconds  = 0;
                intMinutes  = 0
      	   }
        if (intHours>=24){
                intHours   = 0;
                intSeconds = 0;
                intMinutes = 0;
       	   }
        timeString = _date + " " + hours + minutes + seconds;
        document.getElementById(showTime).innerHTML = timeString;
        timerID = window.setTimeout("clock("+"'"+ _date+"'"+","+intHours+","+intMinutes+","+intSeconds+");",1000);
}
