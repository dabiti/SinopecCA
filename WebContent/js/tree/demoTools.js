function clone(jsonObj, newName) {
    var buf;
    if (jsonObj instanceof Array) {
        buf = [];
        var i = jsonObj.length;
        while (i--) {
            buf[i] = clone(jsonObj[i], newName);
        }
        return buf;
    }else if (typeof jsonObj == "function"){
        return jsonObj;
    }else if (jsonObj instanceof Object){
        buf = {};
        for (var k in jsonObj) {
	        if (k!="parentNode") {
	            buf[k] = clone(jsonObj[k], newName);
	            if (newName && k=="name") buf[k] += newName;
	        }
        }
        return buf;
    }else{
        return jsonObj;
    }
}

function JsonToString(simpleTreeNodes){
	 var jsonString="";
	 for(var i=0 ; i < simpleTreeNodes.length ; i++)
	 {
		 if(i==0)
			 jsonString+= simpleTreeNodes[i].id+";"+simpleTreeNodes[i].pId+";"+simpleTreeNodes[i].name+";"+simpleTreeNodes[i].open+";"+simpleTreeNodes[i].postIndex+";"+(simpleTreeNodes[i].url=="undefined"?"":simpleTreeNodes[i].url)+";"+simpleTreeNodes[i].checked;
		 else
			 jsonString+= "|"+ simpleTreeNodes[i].id+";"+simpleTreeNodes[i].pId+";"+simpleTreeNodes[i].name+";"+simpleTreeNodes[i].open+";"+simpleTreeNodes[i].postIndex+";"+(simpleTreeNodes[i].url=="undefined"?"":simpleTreeNodes[i].url)+";"+simpleTreeNodes[i].checked;
	 }
	 return jsonString;
}