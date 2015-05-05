<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link type="text/css" rel="stylesheet" href="../js/tree/xtree.css" />
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="../style/r-left.css" rel="stylesheet" type="text/css">
		<link href="../style/line.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/tree/xtree.js"></script>
		<script type="text/javascript" src="../js/tree/xmlextras.js"></script>
		<script type="text/javascript" src="../js/tree/xloadtree.js"></script>
		<script type="text/javascript" src="../js/shield.js"></script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<input id="orgName" type="hidden" value="${clerk.orgname}">
		<input id=orgCode type="hidden" value="${clerk.orgcode}">
		<table width="213" height="100%" border="0" cellpadding="0"
			cellspacing="1" class="1">
			<tr bgcolor="AAD4DE">
				<td height="27" class="p4">
					<strong>机构管理</strong>
				</td>
			</tr>
			<tr bgcolor="E8F3F6" !onmouseout="this.style.backgroundColor=''"
				!onmouseover="this.style.backgroundColor='D7EBF0'">
				<td valign=top>
<div style=overflow:auto">
<script type="text/javascript">
			var randomid = Math.floor(Math.random() * 1000000) + 1;
          	var orgName = document.getElementById('orgName').value;
          	var orgCode = document.getElementById('orgCode').value;
		 	webFXTreeConfig.rootIcon	= "../js/tree/images/xp/folder.png";
		 	webFXTreeConfig.openRootIcon	= "../js/tree/images/xp/openfolder.png";
		 	webFXTreeConfig.folderIcon	= "../js/tree/images/xp/folder.png";
		 	webFXTreeConfig.openFolderIcon	= "../js/tree/images/xp/openfolder.png";
		 	webFXTreeConfig.fileIcon	= "../js/tree/images/xp/file.png";
		 	webFXTreeConfig.lMinusIcon	= "../js/tree/images/xp/Lminus.png";
		 	webFXTreeConfig.lPlusIcon	= "../js/tree/images/xp/Lplus.png";
		 	webFXTreeConfig.tMinusIcon	= "../js/tree/images/xp/Tminus.png";
		 	webFXTreeConfig.tPlusIcon	= "../js/tree/images/xp/Tplus.png";
		 	webFXTreeConfig.iIcon		= "../js/tree/images/xp/I.png";
		 	webFXTreeConfig.lIcon		= "../js/tree/images/xp/L.png";
		 	webFXTreeConfig.tIcon		= "../js/tree/images/xp/T.png";
		 	webFXTreeConfig.blankIcon       = "../js/tree/images/blank.png";
            webFXTreeConfig.usePersistence  = false;
		 	var tree = new WebFXLoadTree(orgName, 
		 	"../voucherManage.do?method=loadtree&netpointflag="+orgCode+"&randomid="+randomid,
		 	"../voucherManage.do?method=load&&netpointflag="+orgCode,"mainF");
		 	document.write(tree);
	  </script>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
