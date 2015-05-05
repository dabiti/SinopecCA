<%@ page contentType="text/html; charset=gbk" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="js/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="js/themes/icon.css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="js/easyloader.js"></script>
		<script type="text/javascript">
		function tsmessage(){
			using(['dialog','messager'], function(){
				$.messager.show({
					title:'提示',
					width:300,
					height:150,
					msg:'<font color=red><B>请选要操作的报表!<B></font><br><img src="images/tub.jpg">'
				});
			});
		}
		function showForm(){
		var checkValue=checks();
		if(checkValue==undefined){
			tsmessage();
		return;
		}
		window.showModalDialog('r_formMgr.do?method=listForm&baobbs='+checkValue,"dialogWidth=1024px;dialogHeight=720px;status=no;scroll=no"); 
		}
		function showResult(){
		var checkValue=checks();
		if(checkValue==undefined){
			tsmessage();
		return;
		}
		window.showModalDialog('r_resultMgr.do?method=list&baobbs='+checkValue,"dialogWidth=1024px;dialogHeight=1024px;status=no;scroll=no");
		}
		function add(){
		var checkValue=checks();
		window.location.href='ureportMgr.do?method=add';
		}
		function doDelete(){
		var checkValue=checks();
		if(checkValue==undefined){
			tsmessage();
		return;
		}
		if(confirm("你确认删除报表["+checkValue+"]吗?"))
		window.location.href='ureportMgr.do?method=del&baobbs='+checkValue ; 
		}
		function doUpdate(){
		var checkValue=checks();
		if(checkValue==undefined){
			tsmessage();	
		return;
		}
		window.location.href='ureportMgr.do?method=get&baobbs='+checkValue;
		}
		function go(){
		var checkValue=checks();
		if(checkValue==undefined){
			tsmessage();
		return;
		}
		window.location.href='reportService.do?method=doReport&model=test&baobbs='+checkValue;
		}
		function checks(){
       	var radios=document.getElementsByName("radio1");
        for(var i=0;i<radios.length;i++) {
            if(radios[i].checked==true)
                return radios[i].value;
        }
        }
</script>
	<%@ include file="/common/jicgn.jsp"%>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					Ureport定制 - 报表管理
				</td>
			</tr>
		</table>
		<br>
		<table width="97%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<button type="button" id="button1" style="width:100px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onClick="add()">
					<img src="images/search1.gif" width="13" height="13" align="middle">
					新增报表配置
				</button>
				&nbsp;
				<button type="button" id="button1" style="width:100px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onClick="showForm()">
					<img src="images/search1.gif" width="13" height="13" align="middle">
					表单要素配置
				</button>
				&nbsp;
				<button type="button" id="button1" style="width:100px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onClick="showResult()">
					<img src="images/search1.gif" width="13" height="13" align="middle">
					结果要素配置
				</button>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" id="button1" style="width:100px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onClick="doUpdate();">
					<img src="images/search1.gif" width="13" height="13" align="middle">
					修改
				</button>
				&nbsp;
				<button type="button" id="button1" style="width:100px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onClick="doDelete();">
					<img src="images/search1.gif" width="13" height="13" align="middle">
					删除
				</button>
				&nbsp;
				<button type="button" id="button1" style="width:100px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onClick="go();">
					<img src="images/search1.gif" width="13" height="13" align="middle">
					运行
				</button>				
			</tr>
			<br>
			<tr>
				<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">
					&nbsp;
				</td>
				<td>
					<table width="100%"  border="0" style="table-layout:fixed;word-wrap:break-word;" cellspacing="1" cellpadding="0" class="class1_table">
						<thead style="background:white">
           					<div style="text-align:center;"><b>定制服务列表</b></div>
							<tr>
								<td class="class1_thead th">
									选择
								</td>
								<td class="class1_thead th">
									报表标识
								</td>
								<td class="class1_thead th">
									报表名称
								</td>
								<td class="class1_thead th">
									报表标题
								</td>
								<td class="class1_thead th">
									是否可用
								</td> 
								<td class="class1_thead th">
									URL
								</td>
								<td class="class1_thead th">
									操作
								</td> 
							</tr>
						</thead>
						<c:forEach items="${list}" var="list">
							<tr>
								<td class="class1_td center">
									<input type="radio" name="radio1" value="${list['BAOBBS']}" />
								</td>
								<td class="class1_td center">
									${list['BAOBBS']}
								</td>
								<td class="class1_td center">
									${list['BAOBMC']}
								</td>
								<td class="class1_td center">
									${list['BAOBBT']}
								</td>
								<td class="class1_td center">
									${list['SHIFKY']}
								</td>	
								<td class="class1_td center">
									reportService.do?method=doReport&baobbs=${list['BAOBBS']}
								</td>
								<td class="class1_td center">
									<a href="#" onclick="addJCGN('Ureport报表','${list['BAOBMC']}','reportService.do?method=doReport&baobbs=${list['BAOBBS']}');">导入基础功能库</a>
								</td>
							</tr>
						</c:forEach>
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
	</body>
</html>
