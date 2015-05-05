var counts=0;
function sendTrade(){
  	var URL = "session.jsp";
    var xmlDoc=new ActiveXObject("Msxml2.DOMDocument.3.0");
	var xh=new ActiveXObject("MSXML2.XMLHTTP");
		xh.open("POST",URL,false);
    	xh.setRequestHeader("Content-Type","text/xml");
    	xh.setRequestHeader("charset","UTF-8");
    	//保存session生命周期为一个小时
    	if(counts>0&&counts<5)
    	xh.send("");
    	counts++;
    	setTimeout(sendTrade,1000*60*15);
}