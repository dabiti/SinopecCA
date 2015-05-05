<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ include file="/common/struts1.2.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link href="style/extremecomponents.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<%@ include file="/common/validator.jsp"%>
	<script type="text/javascript">
	var htmlMessage ="";
	var bool =false;
    function pdString(){
    		var sql = $("#sql").val();
	    	if(sql.indexOf("select ")!=-1)
	    	{
	    		htmlMessage = "查询语句类型";
	    		bool= true;
	    	}
	    	if(sql.indexOf("insert ")!=-1)
	    	{
	    		htmlMessage = "插入语句类型";
	    		bool= true;
	    	}
	    	if(sql.indexOf("update ")!=-1)
	    	{
	    		htmlMessage = "更新语句类型";
	    		bool= true;
	    	}
	    	if(sql.indexOf("delete ")!=-1)
	    	{
	    		htmlMessage = "删除语句类型";
	    		bool= true;
	    	}
    }
	function vlidate(){
		var f = document.form1;
		pdString();
		if(bool)
		{	
			if(confirm(htmlMessage))
			{
				f.submit();
			}
		}else{
			alert("输入语句有误，请重新输入！");
		}
	}
	</script>	
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<input id="type" type='hidden' value='${type}' />
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					系统管理 - DB操作平台
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		<div id=LX class="error orange"></div>
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange error">
						<div></div>
					</td>
				</tr>
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
		</form>
		<form id="form1" name="form1" method="post" action="operDB.do?method=doWork">
			<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
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
							<tr>
								<td class="class1_td alignleft"  width="15" height="14">
										<button  type="button" style="width:60px" 
											onmouseover="this.className='buttom2'"
											onclick='vlidate();'
											onmouseout="this.className='buttom1'" class="buttom1">
											执行
										</button>
								</td>
							</tr>
							<tr>
								<td  class="class1_td alignleft" >
									<textarea id="sql" name="sql" style="width:800px"" class="inputField1">${sql}</textarea>
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
		</form>
		<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
			</tr>
			<tr>
				<td background="images/table_arrow_05.gif">
				</td>
				<td>
					<table width="100%" border="0" cellspacing="1" cellpadding="0" class="class1_table">
						<thead class="class1_thead">
							<tr>
								<td>
			<ec:table filterable="false" sortable="false" title="查询列表" showPagination="true" view="compact" items="dataList"  rowsDisplayed="${ec_yemjlts}"  var="LIST" action="" imagePath="${pageContext.request.contextPath}/images/table/*.gif">
					<ec:exportXls fileName="sealchecklog.xls" tooltip="Export Excel" />
				<ec:row>
					<c:forEach items="${titleList}" var="list">
							<ec:column property="${list['TITLE']}" title="${list['TITLE']}" />
					</c:forEach>
				</ec:row>
			</ec:table>
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
