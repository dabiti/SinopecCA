$(document).ready(function() {
	$("#form1").validate( {
		errorLabelContainer : "#error div.error",
		wrapper : "li",
		submitHandler : function(form) {
			form.submit();
		}
	})
});

function moveTo(id1, id2) {
	var obj1 = document.getElementById(id1);
	var obj2 = document.getElementById(id2);
	// for(i=0;i<obj1.options.length;i++){
	// 对于长度可变的js对象（select/table），删除操作
	// 应该从末尾开始。
	var flag = false;
	for (i = obj1.options.length - 1; i >= 0; i--) {
		var curr = obj1.options[i];
		if (curr.selected) {
			flag = true;
			var txt1 = curr.text;
			var val1 = curr.value;
			var op = new Option(txt1, val1);
			op.selected = true;
			obj2.options[obj2.options.length] = op;
			obj1.options[i] = null;
		}
	}
	if (!flag) {
		alert('至少选择一个选项');
	}
}

function moveAllTo(id1, id2) {
	var obj1 = document.getElementById(id1);
	var obj2 = document.getElementById(id2);
	for (i = obj1.options.length - 1; i >= 0; i--) {
		var curr = obj1.options[i];
		var txt1 = curr.text;
		var val1 = curr.value;
		var op = new Option(txt1, val1);
		op.selected = true;
		obj2.options[obj2.options.length] = op;
		obj1.options[i] = null;
	}
}
/*form提交之前，使select处于选中状态*/
function makeSelected(selectedId) {
	var obj1 = document.getElementById(selectedId);
	for (i = obj1.options.length - 1; i >= 0; i--) {
		obj1.options[i].selected = true;
	}
}

function tijiaoEvent() {
	makeSelected('s2');
	makeSelected('s4');
}