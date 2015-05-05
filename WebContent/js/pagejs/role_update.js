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
	// Ȩ���ַ���
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

// ��ʼ������
$(document).ready(function() {
	// Ȩ���� ���� ����
		Tree1 = $("#srcTree").zTree(setting, datad);

		$("#form1").validate( {
			errorLabelContainer : "#error div.error",
			wrapper : "li",
			submitHandler : function(form) {
				var beiz = $("#beiz").val();
				if (beiz.length > 300) {
					alert("����ע��������ַ����֧��200�ַ�!");
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