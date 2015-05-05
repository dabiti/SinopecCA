var pageType = "";
var keyNumber="";
function initHTML(type) {
	pageType = type;
}
window.onhelp = function () {
	return false;
};
document.oncontextmenu = function () {
	event.returnValue = false;
};
document.onkeydown = function () {
	if (event.keyCode == 116) {
		event.keyCode = 8001 + event.keyCode - 112;
		event.returnValue = false;
	}
	if (event.keyCode == 13 && event.srcElement.name != "cmdok" && event.srcElement.type != "submit" && event.srcElement.type != "reset" && event.srcElement.type != "textarea" && event.srcElement.type != "") {
		event.keyCode = 9;
	}
	if (event.keyCode == 78 && event.ctrlKey) {
		return false;
	}
};
document.onmousedown = function (){
	if ((event.keyCode >= 112) && (event.keyCode <= 123)) {
		event.keyCode = 8001 + event.keyCode - 112;
		event.returnValue = false;
	}
	if ((event.keyCode >= 37) && (event.keyCode <= 40)) {
		event.keyCode = 8101 + event.keyCode - 37;
		event.returnValue = false;
	}
	if ((event.ctrlKey) && (event.keyCode == 78)) {
		event.returnValue = false;
	}
	if ((event.ctrlKey) && (event.keyCode == 79)) {
		event.keyCode = 8201;
		event.returnValue = false;
	}
	if ((event.ctrlKey) && (event.keyCode == 82)) {
		event.keyCode = 8202;
		event.returnvalue = false;
	}
	if ((event.ctrlKey) && (event.keyCode == 80)) {
		event.keyCode = 8203;
		event.returnvalue = false;
	}
	if ((event.keyCode == 8) && (event.srcElement.type != "text" && event.srcElement.type != "textarea" && event.srcElement.type != "password")) {
		event.keyCode = 0;
		event.returnvalue = false;
	}
	if (window.event.srcElement.tagName == "A" && (window.event.shiftKey || window.event.ctrlKey)) {
		alert("\u529f\u80fd\u5c4f\u853d");
	}
};
function lockkey() {
	if(event.keyCode==17)
		keyNumber=17;
	if ((event.keyCode >= 112) && (event.keyCode <= 123))
	{
		if(keyNumber!=17){
			var key = event.keyCode;
			if (event.keyCode == 123)
			{
				
				event.keyCode = 8001 + event.keyCode - 113;
				event.returnValue = false;
			}else{
				event.keyCode = 8001 + event.keyCode - 112;
				event.returnValue = false;
			}
			
			if (key == 122)
			{	
				event.keyCode = 8001 + key;
				event.returnValue = true;
			}
		}else{
			event.keyCode = 8001 + event.keyCode - 112;
			event.returnValue = false;
		}
	}
	if ((event.keyCode >= 37) && (event.keyCode <= 40))
	{
		event.keyCode = 8101 + event.keyCode - 37;
		event.returnValue = false;
	}
	if ((event.ctrlKey) && (event.keyCode == 78)) {
		event.returnValue = false;
	}
	if ((event.ctrlKey) && (event.keyCode == 79)) {
		event.keyCode = 8201;
		event.returnValue = false;
	}
	if ((event.ctrlKey) && (event.keyCode == 82)) {
		event.keyCode = 8202;
		event.returnvalue = false;
	}
	if ((event.ctrlKey) && (event.keyCode == 80)) {
		event.keyCode = 8203;
		event.returnvalue = false;
	}
	if ((event.keyCode == 8) && (event.srcElement.type != "text" && event.srcElement.type != "textarea" && event.srcElement.type != "password")) {
		event.keyCode = 0;
		event.returnvalue = false;
	}
}

function keypress(){
	keyNumber="";
}