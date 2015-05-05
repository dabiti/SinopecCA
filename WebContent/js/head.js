function changeImg(str) {
	switch (str) {
	  case "01":
		parent.mainFrame.location = "nav.htm";
		break;
	  case "02":
		parent.mainFrame.location = "mid1.htm";
		break;
	  case "03":
		parent.mainFrame.location = "mid2.htm";
		break;
	  case "04":
		parent.mainFrame.location = "mid3.htm";
		break;
	  case "05":
		parent.mainFrame.location = "mid4.htm";
		break;
	  case "06":
		parent.mainFrame.location = "mid5.htm";
		break;
	  case "07":
		parent.mainFrame.location = "mid6.htm";
		break;
	  case "08":
		parent.mainFrame.location = "mid7.htm";
		break;
	  case "09":
		parent.mainFrame.location = "mid8.htm";
		break;
	  case "10":
		parent.mainFrame.location = "mid9.htm";
		break;
	}
	document.getElementById("hdImg").src = "images/head_menu_" + str + ".gif";
}
function closeWindow(){
	var xmlHttp = null;
	if (window.ActiveXObject) {
		if (xmlHttp == null)
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	} else if (window.XMLHttpRequest) {
		if (xmlHttp == null)
			xmlHttp = new XMLHttpRequest();
	}
	var xmlDoc=new ActiveXObject("Msxml2.DOMDocument.3.0");
	xmlHttp.open("get", "logout.do?id="+new Date(), true);
	xmlHttp.send();
}
function logout(){
	if(confirm('你确定要退出吗?'))
	{	 
		 closeWindow();
	}else{
		window.event.returnValue = false;
		return;
	}
}

