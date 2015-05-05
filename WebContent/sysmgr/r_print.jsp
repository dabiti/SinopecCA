<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<html>
	<head>
		<title>¥Ú”°OCX</title>
	</head>
<body>
<input id=baobbs type=hidden value=${baobbs}>
<OBJECT ID="PrintOCX" width=0 height=0 hspace=0  vspace=0 CLASSID="CLSID:A3FF8155-E7D1-4EBD-8E57-FD7C50A923E4"></OBJECT>
<script LANGUAGE="JavaScript">
	function DoDesign(){
		PrintOCX.doDesign();
	}
	function DoPreview(){
		PrintOCX.DoPreview();
	}
	function DoPrint(){
		PrintOCX.DoPrint();
	}
	var baobbs = document.getElementById('baobbs').value;
	function sendTrade(SendXML,URL)
  	{	
    	var xmlDoc=new ActiveXObject("Msxml2.DOMDocument.3.0");
		var xh=new ActiveXObject("MSXML2.XMLHTTP");
		xh.open("POST",URL,false);
    	xh.setRequestHeader("Content-Type","text/xml");
    	xh.setRequestHeader("charset","UTF-8");
    	xh.send(SendXML);
    	if (xh.readyState == 4)
    	{
      		try{
          		return  xh.responseText;
        	} catch(e){
				alert('Error');
        	}  	
    	}
  	}
  	var ErrStr = "";
	var SchemeXML=sendTrade('','reportService.do?method=printScheme&baobbs='+baobbs);
	if (SchemeXML!="") PrintOCX.AddScheme(SchemeXML, ErrStr);
	var dataset = sendTrade('','reportService.do?method=printDateXml');
	PrintOCX.AddPrintDateset(baobbs, dataset,ErrStr);
</script>
<script language="JavaScript" FOR="PrintOCX" EVENT="OnSaveScheme(SchemeBase64)">
	var baobbs = document.getElementById('baobbs').value;	
 	var xml = '<?xml version="1.0" encoding="utf-8"?>';
 	xml = xml + '<function><funcID>ips_baobdypz_i</funcID>';
 	xml = xml + '<parameters>';
 	xml = xml + '<parameter><name>baobbs</name><value>'+baobbs+'</value></parameter>';
 	xml = xml + '<parameter><name>printdesign</name><value>' + PrintOCX.SchemeBase64 + '</value></parameter>';
 	xml = xml +  '</parameters></function>';
 	sendTrade(xml,'reportService.do?method=savePrintScheme');
</script>
</body>
</html>