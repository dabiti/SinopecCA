<%@ page language="java" import="java.util.*" contentType="text/html; charset=gbk" isELIgnored="false" pageEncoding="gbk"%>
<HTML>
	<HEAD>
		<TITLE>ZTREE DEMO</TITLE>
		<link rel="stylesheet" href="style/treestyle/demo.css" type="text/css">
		<link rel="stylesheet" href="style/treestyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/tree/jquery.ztree-2.6.js"></script>
		<script type="text/javascript" src="js/tree/demoTools.js"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<SCRIPT LANGUAGE="JavaScript">
		var zTree1;
		var setting;
		var jsonTree = <%=request.getAttribute("json")%>
		setting = {
	 			isSimpleData : true, 
			    treeNodeKey : "id", 
			    treeNodeParentKey : "pId",
			    keepParent: false,
				keepLeaf: false,
				dragCopy: false,
				dragMove: false,
				editable: true,   
				checkable : false,
				callback: {
					rightClick: zTreeOnRightClick,
					click: clickURL
				}
		};

	$(document).ready(function(){   
		              zTree1 = $("#treeDemo").zTree(setting,jsonTree);
		             });
    
    function clickURL(){
    	var nodes = zTree1.getSelectedNode();
    	window.parent.frames.mainF.location="doMenu.do?method=doAdd&treeId="+nodes.id;
    }
    
	function saveJSON() {
		 var treeNodes = zTree1.getNodes();
		 var simpleTreeNodes = zTree1.transformToArray(treeNodes);
		 var jsonString=JsonToString(simpleTreeNodes);
		 $('#jsonString').val(jsonString);
		 document.jsonForm.submit();
	}
	
	var rMenu;
	$(document).ready(function(){
		reloadTree();
		rMenu = document.getElementById("rMenu");
		$("body").bind("mousedown", 
			function(event){
				if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
					rMenu.style.visibility = "hidden";
				}
			});
	});
	
	function showRMenu(type, x, y) {
		var tree = zTree1.getSelectedNode();
		$("#rMenu ul").show();
		if (type=="root")
		{
			$("#m_del").hide();
			$("#m_check").hide();
			$("#m_unCheck").hide();
		}
		$("#rMenu").css({"top":y+"px", "left":x+"px", "visibility":"visible"});
		if(tree==null)
		{
			hideRMenu();
		} 
		
	}
	
	function hideRMenu() {
		if (rMenu) rMenu.style.visibility = "hidden";
	}

	function zTreeOnRightClick(event, treeId, treeNode) {
		if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
			zTree1.cancelSelectedNode();
			showRMenu("root", event.clientX, event.clientY);
		} else if (treeNode && !treeNode.noR) {
			zTree1.selectNode(treeNode);
			showRMenu("node", event.clientX, event.clientY);
		}
	}
	
	function expandAll(expandSign) {
		zTree1.expandAll(expandSign);
	}

	var addCount = 0;
	
	function addTreeNode() {
		hideRMenu();
		var tree = zTree1.getSelectedNode();
		var tree1 = zTree1.getNodesByParamFuzzy('id',tree.id+addCount);
		if(""==tree1)
		{
			zTree1.addNodes(zTree1.getSelectedNode(),[{id:tree.id+addCount,pId:tree.id,name:"功能"+(addCount++)}]);
		}else{
			addCount++;
			this.addTreeNode();
		}
	}
	
	function removeTreeNode() {
		hideRMenu();
		var node = zTree1.getSelectedNode();
		if (node) {
			if (node.nodes && node.nodes.length > 0) {
				var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
				if (confirm(msg)==true){
					zTree1.removeNode(node);
				}
			} else {
				zTree1.removeNode(node);
			}
		}
	}
	
	function checkTreeNode(checked) {
		var node = zTree1.getSelectedNode();
		if (node) {
			node.checked = checked;
			zTree1.updateNode(node, true);
		}
		hideRMenu();
	}

	function reloadTree() {
		hideRMenu();
		zTree1 = $("#treeDemo").zTree(setting, clone(<%=request.getAttribute("json")%>));
	}

</SCRIPT>
	</HEAD>
	<BODY>
		<table id=a1>
			<tr>
				<td class="p4">
					<ul id="treeDemo" class="tree"></ul>
				</td>
			</tr>
		</table>
		<form id=jsonForm name=jsonForm action=doMenu.do?method=doTree method="post" onsubmit="saveJSON()">
			<input id=jsonString  name=jsonString type=hidden value=""/>
		</form>
		<div id="rMenu" style="position: absolute; visibility: hidden;">
			<li>
				<ul id="m_add" onclick="addTreeNode();">
					<li>
						增加节点
					</li>
				</ul>
				<ul id="m_del" onclick="removeTreeNode();">
					<li>
						删除节点
					</li>
				</ul>
				<ul id="m_reset" onclick="reloadTree();">
					<li>
						初始化树
					</li>
				</ul>
			</li>
		</div>
	</BODY>
</HTML>