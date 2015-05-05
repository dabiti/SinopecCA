var setting = {
	checkable : true,
	isSimpleData : true,
	treeNodeKey : "id",
	treeNodeParentKey : "pId",
	callback : {
		drop : function zTreeOnDrop(event, treeId, treeNode, targetNode,
				moveType) {
		}
	}
};

function makeJsonTree() {
	var notes = Tree1.getNodes();
	var simpleTreeNodes = Tree1.transformToArray(notes);
	//È¨ÏÞ×Ö·û´®
	var jsonString = "[";
	for ( var i = 0; i < simpleTreeNodes.length; i++) {
		jsonString += "{";
		jsonString += "id:'" + simpleTreeNodes[i].id + "',";
		if (simpleTreeNodes[i].pId != null) {
			jsonString += "pId:'" + simpleTreeNodes[i].pId + "',";
		} else {
			jsonString += "pId:'" + 0 + "',";
		}
		jsonString += "name:'" + simpleTreeNodes[i].name + "'";
		jsonString += ",checked:" + simpleTreeNodes[i].checked;
		jsonString += "}";
		if (i != simpleTreeNodes.length - 1) {
			jsonString += ",";
		}
	}
	jsonString += "]";
	return jsonString;
}