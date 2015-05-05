<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base target="_self">
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link href="style/jquery.autocomplete.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="js/autocompleteSet.js"></script>
		<%@ include file="/common/validator.jsp" %>	
		<script type="text/javascript">
			$(document).ready(function() {
				 $("#form").validate({
				   wrapper:"li",
				   submitHandler:function(form)
				   {
						form.submit();
					 	window.close();
				   }
				 })
				 $('#shurlx').unbind().bind('change', function() {
					 if($('#shurlx').val()=='链接显示'||$('#shurlx').val()=='报表链接'||$('#shurlx').val()=='模式窗口'){
						  $('#shurlxList').html("链接:<textarea  name ='map(beiz)'  cols='30' rows='3'/>");
					 }else{
						  $('#shurlxList').html(null);
						  if($('#shurlx').val()=='序号显示')
						  {
							  $("#zdmc").val('rowid \"rowid\"');
							  $("#zdmc").attr("disabled",true);
						  }
					 }
				 });
				 
			})
		</script> 
		<script type="text/javascript">
			function doAddDict(){
				var zhidbs = document.getElementById('zhidbs').value;
				if(zhidbs==''){alert('字典标识不能为空，请输入再添加!');return;};
				if(zhidbs == undefined)
				{
					return;
				}
				window.showModalDialog('dictionaryMgr.do?method=listDictionary&zhidbs='+zhidbs,"dialogWidth:650px;dialogHeight:700px;help:no;scroll:yes;center:yes;status:no");
				return;
			}
		</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					Ureport定制 - 报表管理 - 结果配置
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
		<html:form styleId="form" action="r_resultMgr.do?method=save&action=${action}"
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
						<table width="100%" border="0" cellspacing="1" cellpadding="0"
							class="class1_table">
							<thead class="class1_thead">
								<tr>
									<th colspan="3" class="class1_thead th">
										修改结果要素
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									报表标识：
								</td>
								<td class="class1_td alignleft">
									<html:text styleClass="inputField required BAOBBS" property="map(baobbs)" readonly="true" />
									*报表ID
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									要素标识：
								</td>
								<td class="class1_td alignleft">
									<html:text property="map(yaosbs)" styleClass="inputField required BAOBBS"  readonly="true" />
									*主键，自定义
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									字段名称：
								</td>
								<td class="class1_td alignleft">
									<html:text property="map(yaosmc)" styleId='zdmc'  styleClass="inputField required BAOBBS" onfocus="javaScript:autoCompleteForTableLine('zdmc',{tableName:this.form.SHUJHQFS.value});" />
									*例如：单张日志查询
									<input id="SHUJHQFS" type="hidden" value="${SHUJHQFS}"/>
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									字段类型：
								</td>
								<td class="class1_td alignleft">
									<html:select property="map(yaosgs)">
										<html:option value="string">字符类型</html:option>
										<html:option value="int">整型</html:option>
										<html:option value="float">浮点型[float]</html:option>
										<html:option value="double">浮点型[double]</html:option>
									</html:select>
									*例如：数据库字段类型
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									要素标题：
								</td>
								<td class="class1_td alignleft">
									<html:text property="map(yaosbt)" styleClass="inputField required" />
									*例如：查询展示栏位名称
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									显示类型：
								</td>
								<td class="class1_td alignleft">
									<html:select property="map(xianslx)" styleId="shurlx">
										<html:option value="普通显示">普通显示</html:option>
										<html:option value="序号显示">序号显示 </html:option>
										<html:option value="模式窗口">模式窗口</html:option>
										<html:option value="链接显示">链接显示</html:option>
										<html:option value="报表链接">报表链接 </html:option>
									</html:select>
									*例如：栏位类型，选择 链接显示  可以实现跳转展示模式
									<span id ='addInput'></span> 
									<span id ='delInput'></span>   
									<div id ='shurlxList'><textarea  name ='map(beiz)'  cols='30' rows='3'><bean:write name='CustomForm' property='map(beiz)' /></textarea></div> 
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									显示顺序：
								</td>
								<td class="class1_td alignleft">
									<html:text property="map(xianssx)" styleClass="inputField required BAOBBS"/>
									*例如：调整栏位显示顺序
									<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									是否显示：
								</td>
								<td class="class1_td alignleft">
									<html:select property="map(shifxs)">
										<logic:equal name="CustomForm" property="map(shifxs)" value="是">
											<option value="是" selected=selected>
												是
											</option>
											<option value="否">
												否
											</option>
										</logic:equal>
										<logic:equal name="CustomForm" property="map(shifxs)"
											value="否">
											<option value="是">
												是
											</option>
											<option value="否" selected=selected>
												否
											</option>
										</logic:equal>
									</html:select>
									*例如：是否显示此栏位在查询结果中
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									字典标识：
								</td>
								<td class="class1_td alignleft">
									<html:text styleId="zhidbs" property="map(zhidbs)" styleClass="inputField" /> <a href="javascript:doAddDict();">添加字典要素</a>
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
				<button type="submit" style="width: 60px" type="submit"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1" >
					<img src="images/save1.gif" width="12" height="12" align="middle">
					保存
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width: 60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="window.close();">
					<img src="images/back1.gif" width="11" height="11" align="middle">
					取消
				</button>
			</div>
		</html:form>
	</body>
</html>
