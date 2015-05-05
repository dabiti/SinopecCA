<%@ page language="java" pageEncoding="gbk"%>
<script type="text/javascript" src="js/jicgn.js"></script>
<div id="mydialog" style="display: none; padding: 5px; width: 500px; height: 300px;" title="基础功能库">
			<table width="100%" border="0" cellspacing="1" cellpadding="0" class="class1_table">
			<form id=jcgcForm name=jcgcForm action="basicFunction.do?method=save" method="post">
							<thead class="class1_thead">
								<tr>
									<th colspan="2" class="class1_thead th">
										新增基础功能
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td">
									基础功能ID：
								</td>
								<td class="class1_td alignleft">
									<input id=gongnid name=gongnid  type='text' class="inputField required "  onblur='validateJicgnForGongnId();'/>
									*主键
									<span id="gongnMessage" style="color:red"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td">
									功能名称：
								</td>
								<td class="class1_td alignleft">
									<input id=gongnmc name=gongnmc type='text' class="inputField required " />
									*入库功能定义
								</td>
							</tr>
							<tr>
								<td class="class1_td">
									功能分类：
								</td>
								<td class="class1_td alignleft">
									<input id=gongnfl name=gongnfl type='text' class="inputField"/>
									*入库功能定义 一般不需要修改
								</td>
							</tr>
							<tr>
								<td class="class1_td">
									功能URL：
								</td>
								<td class="class1_td alignleft">
									<input id=gongnurl name=gongnurl  type='text' class="inputField required" style="width:320px" />
								</td>
							</tr>
							<tr>
								<td class="class1_td">
									权限位置：
								</td>
								<td class="class1_td alignleft">
									<input id=quanxwz name=quanxwz  type='text' class="inputField required BAOBBS" style="width:30px"    onblur='validateJicgnForQuanxwz();' />
									*权限位置 注意不要使用已经被使用的权限位
									<span id="quanxwzMessage" style="color:red"></span>
								</td>
							</tr>
							</form>
			</table>
	</div>
