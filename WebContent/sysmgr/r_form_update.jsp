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
		var i=0;
		$(document).ready(function() {
			 $("#form").validate({
			   wrapper:"li",
			   submitHandler:function(form)
			   {
				 	form.submit();
				 	window.close();
			   }
			 });
			var jtdxk = $('#beiz').val();
			if($('#shurlx').val()!='动态多选框'&&$('#shurlx').val()!='回显输入'&&$('#shurlx').val()!='子集输入'){
				 $('#shurlxList').html(null);
			}
			if($('#shurlx').val()=='静态多选框'){
				var jtd = jtdxk.split('|');
				for(i=0;i<jtd.length-1;i++)
				{
					var id_name = jtd[i].split('=');
					var delInput = "<a href='javascript:deleteInput("+i+")'>删除</a>";
					var html=$('#shurlxList').html();
					$('#shurlxList').html(html+"<div id=shurlxList"+i+">选项:<input name ='shurlxName'  class='inputField' value='"+id_name[0]+"'/> 数据库保存:<input name ='shurlxValue'  class='inputField' value='"+id_name[1]+"'/>"+delInput+"</div>");
				}
				$('#addInput').html("<input type='button'  onclick='addInput()' value='添加'/>")
			}
			$('#shurlx').unbind().bind('change', function(){
					  if($('#shurlx').val()=='静态多选框')
					  {
						  var delInput = "<a href='javascript:deleteInput("+i+")'>删除</a>";
						  $('#shurlxList').html("<div id=shurlxList"+i+">选项:<input name ='shurlxName'  class='inputField'/> 数据库保存:<input name ='shurlxValue'  class='inputField'/>"+delInput+"</div>");
						  $('#addInput').html("<input type='button'  onclick='addInput()' value='添加'/>")
					  }else{
						  $('#shurlxList').html(null);
						  $('#addInput').html(null);
					  }
					  
					  if($('#shurlx').val()=='动态多选框'||$('#shurlx').val()=='回显输入'||$('#shurlx').val()=='子集输入'){
						  $('#shurlxList').html("数据源:<textarea  name ='shujy'  cols='30' rows='3'/>"+"</textarea><div>注意:数据源数据集合必须是两列，分别为:'key'(库值),'name'(转义)");
					  }else{
						  $('#shurlxList').html(null);
					  }
					});
		})
	
	function addInput()
	{
		    var html=$('#shurlxList').html();
		    i++;
		    var delInput = "<a href='javascript:deleteInput("+i+")'>删除</a>";
			$('#shurlxList').html(html+"<div id=shurlxList"+i+">选项:<input name ='shurlxName'  class='inputField required'/> 数据库保存:<input name ='shurlxValue'  class='inputField'/>"+delInput+"</div>");
	}
	function deleteInput(i){
		$('#shurlxList'+i).html(null);
	}
		function doAddDict()
		{
			var zhidbs = document.getElementById('zhidbs').value;
			if(zhidbs == undefined)
			{
				return;
			}
			window.showModalDialog('dictionaryMgr.do?method=listDictionary&zhidbs='+zhidbs,"dialogWidth:650px;dialogHeight:700px;help:no;scroll:yes;center:yes;status:no");
			return;
	}
		</script> 
		<script type="text/javascript">
			function doAddDict()
			{
				var zhidbs = document.getElementById('zhidbs').value;
				if(zhidbs == undefined)
				{
					return;
				}
				window.showModalDialog('dictionaryMgr.do?method=listDictionary&zhidbs='+zhidbs,"dialogWidth:650px;dialogHeight:700px;help:no;scroll:yes;center:yes;status:no");
				return;
			}
			function doAddYewgz()
			{
				var yaosbs = document.getElementById('yaosbs').value;
				if(yaosbs == undefined)
				{
					return;
				}
				window.showModalDialog('yewgzConfig.do?method=modify&type=ureport&yuansid='+yaosbs,"dialogWidth:650px;dialogHeight:700px;help:no;scroll:yes;center:yes;status:no");
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
					Ureport定制 - 报表管理 - 表单要素配置
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
		<html:form styleId="form" action="r_formMgr.do?method=updateForm" method="post">
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
										修改表单
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									报表标识：
								</td>
								<td class="class1_td alignleft">
									<html:text  property="map(BAOBBS)" styleClass="inputField required BAOBBS" readonly="true"/>
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									要素标识：
								</td>
								<td class="class1_td alignleft">
									<html:text property="map(YAOSBS)" styleId="yaosbs" styleClass="inputField required BAOBBS"  readonly="true"/>
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									字段名称：
								</td>
								<td class="class1_td alignleft">
									<html:text property="map(YAOSMC)"  styleId='zdmc'   styleClass="inputField required BAOBBS" onfocus="javaScript:autoCompleteForTableLine('zdmc',{tableName:this.form.SHUJHQFS.value});" />
									<input id="SHUJHQFS" type="hidden" value="${SHUJHQFS}"/>
									*数据库中表的字段名，如：jigh
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									要素名称：
								</td>
								<td class="class1_td alignleft">
									<html:text  property="map(YAOSBT)" styleClass="inputField required" />
									*查询条件名称，如：机构号
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									字段类型：
								</td>
								<td class="class1_td alignleft">
									<html:select property="map(YAOSGS)">
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
									要素类型：
								</td>
								<td class="class1_td alignleft">
									<html:select styleId="shurlx"  property="map(YAOSLX)">
										<html:option value="文本输入">文本输入</html:option>
										<html:option value="数字输入">数字输入</html:option>
										<html:option value="模糊输入">模糊输入</html:option>
										<html:option value="回显输入">回显输入</html:option>
										<html:option value="子集输入">子集输入</html:option>
										<html:option value="日期输入">日期输入</html:option>
										<html:option value="日期范围">日期范围</html:option>
										<html:option value="静态多选框">静态多选框 </html:option>
										<html:option value="动态多选框">动态多选框 </html:option>	
									</html:select>
									<span id ='addInput'></span> 
									<span id ='delInput'></span>          
									<div id ='shurlxList'><textarea  name ='beiz' id ='beiz'  cols='30' rows='3'/><bean:write name='CustomForm' property='map(BEIZ)' /></textarea><div></>注意:数据源数据集合必须是两列，分别为:'key'(库值),'name'(转义)</div></div>
									*选择表单要素类型，如：文本框，下拉框，多选框等
								</td>
								<td class="class1_td alignleft">
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
								是否必填：
								</td>
								<td class="class1_td alignleft">
								<html:select property="map(SHIFBT)">
									<logic:equal name="CustomForm" property="map(SHIFBT)"
											value="是">
											<option value="是" selected=selected>
												是
											</option>
											<option value="否">
												否
											</option>
										</logic:equal>
										<logic:equal name="CustomForm" property="map(SHIFBT)"
											value="否">
											<option value="是">
												是
											</option>
											<option value="否" selected=selected>
												否
											</option>
										</logic:equal>
								</html:select>
								</td>
								<td class="class1_td alignleft"></td>
							<!--
								<tr>
									<td class="class1_td w250">
										要素长度：
									</td>
									<td class="class1_td alignleft">
										<html:text  property="map(YAOSCD)" styleClass="inputField required BAOBBS"/>
									</td>
									<td class="class1_td alignleft"></td>
								</tr>
							-->
							<tr>
								<td class="class1_td w250">
									默 认 值：
								</td>
								<td class="class1_td alignleft">
									<html:text  property="map(MOYZ)" styleClass="inputField"/>
								</td>
								<td class="class1_td alignleft"></td>
							</tr>
							<tr>
								<td class="class1_td w250">
									显示顺序：
								</td>
								<td class="class1_td alignleft">
									<html:text  property="map(XIANSSX)" styleClass="inputField required BAOBBS" />
									*表单要素的排列顺序 
								</td>								
								
							</tr>
							<tr>
								<td class="class1_td w250">
									 
								</td>
							<td class="class1_td alignleft">
									<html:button property="button" onclick="doAddYewgz();" value="添加业务规则信息"></html:button> 
								</td>
							</tr>
							<!--
							<tr>
								<td class="class1_td w250">
									字典标识：
								</td>
								<td class="class1_td alignleft">
									<html:text styleId="zhidbs" property="map(ZHIDBS)" styleClass="inputField required BAOBBS" />
								</td>
								<td class="class1_td alignleft">
									<html:button property="button" onclick="doAddDict();" value="添加字典要素"></html:button> 
								</td>
							</tr>
							-->
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
				<button type="submit" style="width:60px" type="submit"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12"
						align="middle">
					保存
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="window.close();">
					<img src="images/back1.gif" width="11" height="11"
						align="middle">
					取消
				</button>
			</div>
		</html:form>
	</body>
</html>
