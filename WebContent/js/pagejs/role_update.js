var Tree1;
var datad = '${juesListJsonString}';

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
	// 权限字符串
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

// 初始化方法
$(document).ready(function() {
	// 权限树 数据 生成
		Tree1 = $("#srcTree").zTree(setting, datad);

		$("#form1").validate( {
			errorLabelContainer : "#error div.error",
			wrapper : "li",
			submitHandler : function(form) {
				var beiz = $("#beiz").val();
				if (beiz.length > 300) {
					alert("【备注】输入框字符最多支持200字符!");
					return;
				}
				;
				var jsonString = makeJsonTree();
				$("#jsonString").val(jsonString);
				form.action = document.form1.action;
				form.submit();
			}
		})

	});