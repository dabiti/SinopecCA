$.fn.DateTimeMask = function(settings,name){
	$.fn.DateTimeMask(settings);
	if($("#"+name).val()=='')
 	{	
 	var myDate = new Date();
 	var month = myDate.getMonth()+1;
 	var now = myDate.getFullYear() + '-' + (month<10 ? ("0" + month) : month) + '-' + myDate.getDate();
 	$("#"+name).attr("value",now);
 	}
};
$.fn.DateTimeMask = function(settings){
	var options = {
		id:null,							//ҳ��ʱ���ǩID 
		isFirstDay:false,					//�Ƿ��µ�һ�� true:���µ�һ�� false:Ĭ�ϵ���			
		safemodel: false,					//��ȫģʽ�£�����ճ������������
		masktype: "3",						//1:yyyy-MM-dd HH:mm:ss 2:yyyy-MM-dd HH:mm 3:yyyy-MM-dd 4:HH:mm 5:HH:mm:ss 
		isnull: false,						//�Ƿ����ȫ������0
		lawlessmessage: "������ĸ�ʽ����",	//�Ƿ���ʽ����ʾ��Ϣ
		onlycontrolkeydown: true,			//ֻ��������
		focuscssname: "",					//��ý������ʽ
		oldclassname: "",					//��¼��ǰ����ʽ
		isnow: false,
		ismonthstart: false,
		whenfocus:function(){},				//��ý���ʱ��Ļص��������޷���ֵ��
		whenblur: function(){return true;}	//ʧȥ����ʱ��Ļص�������return ture ��ʾ����У��ɹ���return false:�ָ����ϴε�ֵ
	};
	settings = settings || {};
	$.extend(options, settings);
	return this.each(function(){
		//��ʼ��
		if(options.isnow || options.ismonthstart) 
		this.value = $.DateTimeMask.getDateTime(options);
		//��ʼ��ʱ��
		if(options.id!=null){
			if($("#"+options.id).val()=='')
 			{	
 			var myDate = new Date();
 			var month = myDate.getMonth()+1;
 			var day =options.isFirstDay ? "01" : myDate.getDate();
 			var now = myDate.getFullYear() + '-' + (month<10 ? ("0" + month) : month) + '-' + day;
 			$("#"+options.id).attr("value",now);
 			}
		}
		$(this).attr("autocomplete", "off");
		if (options.safemodel) {
			if ($.browser.msie) {
				this.ondragenter = function(){return false;};
				this.onpaste = function(){return false;};
			}
		}

		$(this).keydown(function(event){
			$.DateTimeMask.KeyDown(this,event,options);
		})
		if (!options.onlycontrolkeydown) {
			$(this).focus(function(){
				$.DateTimeMask.SetFocus(this,options);
				options.whenfocus();
			});
			$(this).blur(function(){

				if(!$.DateTimeMask.LostFocus(this,options))
				{
					if(!options.whenblur(this.value)) this.value = this.oldvalue;
				}
			});
		}
	});
};

$.DateTimeMask = 
{
	//��ý���ʱ��Ĵ�����
	SetFocus : function(obj,options)
	{
		obj.oldvalue = obj.value;
		if(obj.focuscssname && obj.focuscssname!="")
		{
			obj.oldClassName = obj.className;
			obj.className = obj.focuscssname;
		}
	},

	//ʧȥ����ʱ��Ĵ�����
	LostFocus : function(obj,options) 
	{
		var ls_date,ls_time;
		var lb_error = false;
		switch(options.masktype)
		{
			case "1":
				ls_date = obj.value.substr(0,10);
				ls_time = obj.value.substr(11);
				if(obj.value == "0000-00-00 00:00:00")
				{
					if(!options.isnull) lb_error = true;
				}
				else
				{
					if(!($.DateTimeMask.isValidDate(ls_date) && $.DateTimeMask.isValidTime(ls_time))) lb_error = true;
				}
				break;
			case "2":
				ls_date = obj.value.substr(0,10);
				ls_time = obj.value.substr(11)+":00";
				if(obj.value == "0000-00-00 00:00")
				{
					if(!options.isnull) lb_error = true;
				}
				else
				{
					if(!($.DateTimeMask.isValidDate(ls_date) && $.DateTimeMask.isValidTime(ls_time))) lb_error = true;
				}
				break;
			case "3":
				ls_date = obj.value;
				if(ls_date == "0000-00-00")
				{
					if(!options.isnull) lb_error = true;
				}
				else
				{
					if(!$.DateTimeMask.isValidDate(ls_date)) lb_error = true;
				}
				break;
			case "4":
				ls_time = obj.value+":00";;
				if(obj.value == "00:00")
				{
					if(!options.isnull) lb_error = true;	
				}
				else
				{
					if(!$.DateTimeMask.isValidTime(ls_time)) lb_error = true;
				}
				break;
			case "5":
				ls_time = obj.value;
				if(ls_time == "00:00:00")
				{
					if(!options.isnull) lb_error = true;
				}
				else
				{
					if(!$.DateTimeMask.isValidTime(ls_time)) lb_error = true;
				}
				break;
		}
		if(lb_error){
			if(!options.lawlessmessage || options.lawlessmessage!="") alert(options.lawlessmessage);
			obj.value = obj.oldvalue;
		}
		if (obj.focuscssname && obj.focuscssname!="") obj.className = obj.oldClassName;
		return lb_error;
	},

	//����ʱ��Ĵ�����
	KeyDown : function(objTextBox,event,options)  
	{ 
	    //��������
	    var KEY = {
			BACKSPACE: 8,
		    TAB: 9,
		    ENTER: 13,
			END: 35,
			HOME: 36,
		    LEFT: 37,
			RIGTH: 39,
		    DEL: 46
	    };
	    var nKeyCode = event.keyCode; 
	    // ���⴦��İ��� 
	    switch(nKeyCode){
	        case KEY.TAB:
			case KEY.HOME:
			case KEY.END:
	        case KEY.LEFT:
	        case KEY.RIGTH:
	            return;
	        case KEY.ENTER:
				event.preventDefault();
			    if(options.EnterMoveToNext) event.keyCode = 9;
	            return;
	    }
	    //ֻ���Ͳ�ִ���κβ���
	    if(objTextBox.ReadOnly) {
		    event.returnValue = false;
		    return;
	    }
	    // ��ǰ�ı����е��ı� 
	    var strText =objTextBox.value; 
	    // �ı����� 
	    var nTextLen=strText.length; 
	    // ��ǰ���λ�� 
	    var nCursorPos = $.DateTimeMask.GetCursor(objTextBox).start;
		//���԰���
	    event.returnValue = false; 
		//��ֹð��
		event.preventDefault();
	    // ���д���ť 
	    switch (nKeyCode) 
	    { 
	        case KEY.BACKSPACE:
	            if(nCursorPos > 0)
	            { 
	                fronttext = strText.substr(nCursorPos - 1,1); 
	                if(fronttext!="-" && fronttext!=":" && fronttext!=" ")
	                { 
	                    fronttext="0"; 
	                    strText =  strText.substr(0,nCursorPos - 1) + fronttext + strText.substr(nCursorPos, nTextLen-nCursorPos); 
	                } 
	                nCursorPos--; 
	            } 
	            break; 
	        case KEY.DEL:
	            if(nCursorPos<nTextLen) 
	            { 
	                behindtext = strText.substr(nCursorPos,1); 
	                if(behindtext!="-" && behindtext!=":" && behindtext!=" ") behindtext="0"; 
	                if(nCursorPos + 1 == nTextLen) 
	                    strText =  strText.substr(0,nCursorPos) + behindtext; 
	                else 
	                    strText =  strText.substr(0,nCursorPos) + behindtext + strText.substr(nCursorPos+1,nTextLen-nCursorPos-1); 
	                nCursorPos++; 
	            } 
	            break; 
	        default : 
	            if(nCursorPos==nTextLen) break; 
				if(!(nKeyCode >=48 && nKeyCode<=57 || nKeyCode >=96 && nKeyCode<=105)) break;
				if (nKeyCode > 95) nKeyCode -= (95-47); 
	            behindtext = strText.substr(nCursorPos,1); 
	            if(behindtext!="-" && behindtext!=":" && behindtext!=" ") 
	            { 
	                var keycode = String.fromCharCode(nKeyCode); 
	                preText = strText.substr(0,nCursorPos) + keycode + strText.substr(nCursorPos+1,nTextLen); 
	                if(!$.DateTimeMask.DealWith(options.masktype,preText,nCursorPos)) break; 
	                strText = preText; 
	                nCursorPos++; 
	            } 
	            if (nCursorPos>strText.length) 
	            { 
	                nCursorPos=strText.length; 
	            } 
	            //�����,Ҫ���ݵ�ǰλ�þ����Ƿ�Ҫ����λ�á���������":"  "-"  " "
	            if(options.masktype<="3"){
	                if(nCursorPos==4 || nCursorPos==7 || nCursorPos==10 || nCursorPos==13 || nCursorPos==16) nCursorPos++; 
			    }
	            else{
	                if(nCursorPos==2 || nCursorPos==5 ) nCursorPos++;
			    }
	            break; 
	    } 
	    objTextBox.value = strText; 
	    $.DateTimeMask.Selection(objTextBox,nCursorPos,nCursorPos); 
	},
	
	//���ݹ�����ڵ�λ�ã��ж�������ַ��Ƿ�Ϸ�
	DealWith : function(masktype,input,nCursorPos) 
	{
	    var ls_date,ls_time;
	    if(masktype <= "3")
	    {
	        ls_year = input.substr(0,4);  
	        if(ls_year=="0000") ls_year = "2001";  
	        ls_month = input.substr(5,2);  
	        if(ls_month=="00") ls_month = "01";  
	        ls_day = input.substr(8,2);  
	        if(ls_day=="00") ls_day = "01";  
	        ls_date = ls_year +"-"+ ls_month +"-"+ ls_day; 
	        ls_time = "00:00:00";
	        if(masktype=="1") {				
	            ls_time = input.substr(11);
			}
	        else {
				if(masktype=="2") ls_time = input.substr(11) + ":00";
			}
	        //������ڵ�λ�ý����жϵ�ǰ�ַ����Ƿ�Ϸ�
			return (nCursorPos<=10?$.DateTimeMask.isValidDate(ls_date):$.DateTimeMask.isValidTime(ls_time))
	    }
	    else
	    {
	        ls_time = input;
	        if(masktype=="4") ls_time = ls_time + ":00";
	        //������ڵ�λ�ý����жϵ�ǰ�ַ����Ƿ�Ϸ�
	        return $.DateTimeMask.isValidTime(ls_time); 
	    }
	    return true; 
	},

	//��������ȡ������ڵ�λ�ã�������ʼλ�úͽ���λ��
	GetCursor : function(textBox){
		var obj = new Object();
		var start = 0,end = 0;
		if ($.browser.mozilla) {
			start = textBox.selectionStart;
			end = textBox.selectionEnd;
		}
		if ($.browser.msie) {
			var range=textBox.createTextRange(); 
			var text = range.text;
			var selrange = document.selection.createRange();
			var seltext = selrange.text;
			while(selrange.compareEndPoints("StartToStart",range)>0){ 
				selrange.moveStart("character",-1); 
				start ++;
			}
			while(selrange.compareEndPoints("EndToStart",range)>0){ 
				selrange.moveEnd("character",-1); 
				end ++;
			}
		}
		obj.start = start;
		obj.end = end;
		return obj;
	},
	
	//��������field��start��end��ѡ��
	Selection : function(field, start, end) 
	{
		if( field.createTextRange ){
			var r = field.createTextRange();
			r.moveStart('character',start);
			r.collapse(true);
			r.select(); 
		} else if( field.setSelectionRange ){
			field.setSelectionRange(start, end);
		} else {
			if( field.selectionStart ){
				field.selectionStart = start;
				field.selectionEnd = end;
			}
		}
		field.focus();
	},
	
	//�Ƿ�Ϊ����
	isValidDate : function(strDate)
	{
		var ls_regex = "^((((((0[48])|([13579][26])|([2468][048]))00)|([0-9][0-9]((0[48])|([13579][26])|([2468][048]))))-02-29)|(((000[1-9])|(00[1-9][0-9])|(0[1-9][0-9][0-9])|([1-9][0-9][0-9][0-9]))-((((0[13578])|(1[02]))-31)|(((0[1,3-9])|(1[0-2]))-(29|30))|(((0[1-9])|(1[0-2]))-((0[1-9])|(1[0-9])|(2[0-8]))))))$";
		var exp = new RegExp(ls_regex, "i");
		return exp.test(strDate);
	},
	
	//�Ƿ�Ϊʱ��
	isValidTime : function(strTime)
	{
		var a = strTime.match(/^(\d{2,2})(:)?(\d{2,2})\2(\d{2,2})$/);
        if (!a || a[1]>23 || a[3]>59 || a[4]>59) return false;
        return true;
	},

	getDateTime : function(options)
	{
		var d = new Date();
		var vYear = d.getFullYear();
		var vMon = d.getMonth() + 1;
		vMon = (vMon<10 ? "0" + vMon : vMon);
		var vDay = d.getDate();
		var ls_date = vYear+"-"+vMon+"-"+(vDay<10 ?  "0"+ vDay : vDay );
		var vHour = d.getHours();
		var vMin = d.getMinutes();
		var vSec = d.getSeconds();
		var ls_time = (vHour<10 ? "0" + vHour : vHour) + ":"+(vMin<10 ? "0" + vMin : vMin)+":"+(vSec<10 ?  "0"+ vSec : vSec );
		switch(options.masktype)
		{
			case "1":
				return options.isnow?(ls_date + " " + ls_time):(vYear+"-"+vMon+"-"+"01 00:00:00");
			case "2":
				return options.isnow?(ls_date + " " + ls_time.substr(0,5)):(vYear+"-"+vMon+"-"+"01 00:00");
			case "3":
				return options.isnow?ls_date:(vYear+"-"+vMon+"-"+"01");
			case "4":
				return ls_time.substr(0,5);
			case "5":
				return ls_time;
		}
		
	}
}