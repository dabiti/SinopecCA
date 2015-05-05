var xmlHttp = null;
		function SendSesssion(url, Message,textType) {
			if (window.ActiveXObject){
				if (xmlHttp == null)xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			} else if (window.XMLHttpRequest){
				if (xmlHttp == null)xmlHttp = new XMLHttpRequest();
			}
			if('xml'==textType)
			{
				var xmlDoc=new ActiveXObject("Msxml2.DOMDocument.3.0");
				xmlDoc.loadXML(Message);
				xmlHttp.open("post", url, false);
				xmlHttp.send(xmlDoc);
				if (xmlHttp.readyState == 4) {
					try {
						var rMessage = xmlHttp.responseText;
						return rMessage;
					} catch (e) {
						e.printStackTrace();
					}
				}
			}else{
				xmlHttp.open("post", url, false);
				xmlHttp.send(Message);
				if (xmlHttp.readyState == 4) {
					try {
						var rMessage = xmlHttp.responseText;
						return rMessage;
					} catch (e) {
						e.printStackTrace();
					}
				}
			}
		}