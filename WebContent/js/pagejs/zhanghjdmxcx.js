function excel(zhangh){
	document.form1.action = "accountinfo.do?method=net_excel&zhangh="+zhangh;
	document.form1.submit();
}
function SendSesssion(){
	var xmlDoc=new ActiveXObject("Msxml2.DOMDocument.3.0");
	var xh =new ActiveXObject("MSXML2.XMLHTTP");
	xh.open("post",document.getElementById('sessionURL').value,false);
	xh.setRequestHeader("Content-Type","text/xml");
	xh.setRequestHeader("charset","GBK");
	xh.send(null);
}
