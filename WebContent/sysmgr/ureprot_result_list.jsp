<%@ page contentType="text/html; charset=GBK" language="java"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
		function onchange(counts){
		var checkValue=checks();
		if(counts==1){
		window.showModalDialog('ureprot_form_add.jsp?baobbs='+checkValue,"dialogWidth=1024px;dialogHeight=720px;status=no;scroll=no"); 
		return;
		}
		if(checkValue==undefined){
		alert("请选择要操作的报表！");
		return;
		}
		if(counts==2){
		window.showModalDialog('ureprot_form_list.jsp?baobbs='+checkValue,"dialogWidth=1024px;dialogHeight=720px;status=no;scroll=no");
		return;
		}
		}
		function checks(){
       	var radios=document.getElementsByName("radio1");
        for(var i=0;i<radios.length;i++) {
            if(radios[i].checked==true)
                return radios[i].value;
        }
        }
</script>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					Ureport定制 - 报表管理 - 结果管理
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
					onClick="onchange(1);">
					<img src="images/search1.gif" width="13" height="13"
						align="middle">
					新增表单配置
				</button>
				&nbsp;
				<button type="button" id="button1" style="width:100px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onClick="onchange(2);">
					<img src="images/search1.gif" width="13" height="13"
						align="middle">
					字典要素配置
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
					<table width="100%" border="0" cellspacing="1" cellpadding="0"
						class="class1_table">
						<thead class="class1_thead">
							<tr>
								<th colspan="7" class="class1_thead th">
									定制服务列表
								</th>
							</tr>
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
									操作
								</td>
							</tr>
						</thead>
						<tr>
							<td class="class1_td center">
								<input type="radio" name="radio1" value="" />
							</td>
							<td class="class1_td center">
								1
							</td>
							<td class="class1_td center">
								2
							</td>
							<td class="class1_td center">
								3
							</td>
							<td class="class1_td center">
								<a href="sysmgr/ureprot_update.jsp">[修改]</a>
								<a href="sysmgr/ureprot_update.jsp">[删除]</a>
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
	</body>
</html>
