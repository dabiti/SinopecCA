<%@ page language="java" contentType="text/html;charset=GBK" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<%@ include file="/common/title.jsp"%>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="js/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/jquery-ztree-2.5.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
<script language="javascript">
	var Tree1, Tree2;
	var datad = ${jicStr};
	var juesdatad = ${chanpStr};

	var setting = {
		isSimpleData : true,
		treeNodeKey : "id",
		treeNodeParentKey : "pId",
		callback: {
		drop: function zTreeOnDrop(event, treeId, treeNode, targetNode, moveType) {}
	}
	};

	var setting1 = {
		isSimpleData : true,
		editable : true,
		edit_renameBtn : true,
		edit_removeBtn : true,
		treeNodeKey : "id",
		treeNodeParentKey : "pId",
		callback: {
		drop: function zTreeOnDrop(event, treeId, treeNode, targetNode, moveType) {}
	}
	};
	
	//数据右移动
	function addRole() {
		moveTreeNode(Tree1, Tree2);
	}

	//数据左移动
	function delRole() {
		//移动方法 参数相反
		moveTreeNode(Tree2, Tree1);
	}

	function moveTreeNode(srcTree, targetTree) {
		var srcNode = srcTree.getSelectedNode();
		if (srcNode==null){
			alert("请先选择需要操作的菜单!");
			return;
		}
		var targetNode = targetTree.getSelectedNode();
		var srcId = srcNode.id;
		var srcpId = srcNode.pId;
		var targetId = targetNode.id;
		var targetpId = targetNode.pId;
		
	//	srcTree.removeNode(srcNode);	
		targetTree.addNodes(targetNode, [srcNode]);
		targetTree.selectNode(targetNode);
	    srcTree.cancelSelectedNode();
	}

	function makeJsonTree() {
		var notes = Tree2.getNodes();
		var simpleTreeNodes = Tree1.transformToArray(notes);
		//权限字符串
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
			jsonString += "}";
			if (i != simpleTreeNodes.length - 1) {
				jsonString += ",";
			}
		}
		jsonString += "]";
		return jsonString;
	}

	//初始化方法
	$(document).ready(function() {
		//权限过滤
		/**
		for(var i=0 ; i<datad.length ; i++)
		{
			for(var j=0 ; j<juesdatad.length ; j++)
			{
				if(juesdatad[j].id==datad[i].id)
				{
					delete datad[i].id;
					delete datad[i].name;
					delete datad[i].pId;
				}
			}
		}
		*/
		//权限树  数据 生成
		Tree1 = $("#srcTree").zTree(setting, datad);
		//角色权限  数据null
		Tree2 = $("#tarTree").zTree(setting1, juesdatad);
		$("#form1").validate( {
			errorLabelContainer : "#error div.error",
			wrapper : "li",
			submitHandler : function(form) {
				var jsonString = makeJsonTree();
				$("#jsonString").val(jsonString);
				form.action = "doMenu.do?method=saveOrUpdate";
				form.submit();
				}
			})

		});
</script>
	</head>
	<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					系统管理 - 角色管理
				</td>
			</tr>
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<logic:messagesPresent>
				<tr>
					<td class="orange">
						<div id=abc class="abc">
							<html:errors />
						</div>
					</td>
				</tr>
			</logic:messagesPresent>
		</table>
		<html:form styleId="form1" action="doMenu.do?method=saveOrUpdate"
			method="post">
			<table width="97%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
				</tr>
				<tr>
					<td background="images/table_arrow_05.gif">
						&nbsp;
					</td>
					<td>
						<input id=jsonString name=jsonString type='hidden' />
						<table width="680px" height="80%" cellspacing="1" cellpadding="3" align="center" border="1" >
							<thead class="class1_thead">
								<tr>
									<th colspan="10" class="class1_thead th">
										功能菜单自定义
									</th>
								</tr>
							</thead>
							<tr height="25px">
								<td colspan="3" align="left" valign="middle">
								<button type="button" style="width:80px"  class="buttom1"   onclick="javascript:location.reload();">
									刷新
								</button>
								</td>
							</tr>
							<tr>
								<td width="45%" style='background-color: yellow' >
								       基础功能菜单库
								</td>
								<td  width="10%">
									<button type="button" style="width:80px"  class="buttom1" onclick="addRole();">
									添加<img style="width:12px"  src="images/tree/moveNode.png" >
								</button>
								</td>
								<td style='background-color: yellow'  width="200%" >
								           产品功能菜单库
								</td>
							</tr>
							<tr>
								<td align="left" valign="top" width="45%" id="td_tree1">
									<ul id="srcTree" class="tree" style="width: 100%; overflow: auto;"></ul>
								</td>
								<td align="center" valign="middle" width="10%">
								<br><br><br><br><br>
									<button type="button" style="width:80px;overflow: auto;"  class="buttom1" onclick="addRole();">
									添加<img style="width:12px"  src="images/tree/moveNode.png" >
								</button>
								</td>
								<td align="left" valign="top" width="200%" id="td_tree2">
									<ul id="tarTree" class="tree" style="width: 100%; overflow: auto;"></ul>
								</td>
							</tr>
						</table>
					</td>
					<td background="images/table_arrow_07.gif">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
				</tr>
			</table>
			<div class="funbutton">
				<button type="submit" style="width: 60px" onmouseover=
	this.className;
= 'buttom2'; onmouseout=
	this.className;
= 'buttom1'; class="buttom1">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					保存
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width: 60px" onmouseover=
	this.className;
= 'buttom2'; onmouseout=
	this.className;
= 'buttom1'; class="buttom1"
					onclick=
	history.back();
>
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					取消
				</button>
			</div>
		</html:form>
	</body>
</html>
