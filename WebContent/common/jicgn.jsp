<%@ page language="java" pageEncoding="gbk"%>
<script type="text/javascript" src="js/jicgn.js"></script>
<div id="mydialog" style="display: none; padding: 5px; width: 500px; height: 300px;" title="�������ܿ�">
			<table width="100%" border="0" cellspacing="1" cellpadding="0" class="class1_table">
			<form id=jcgcForm name=jcgcForm action="basicFunction.do?method=save" method="post">
							<thead class="class1_thead">
								<tr>
									<th colspan="2" class="class1_thead th">
										������������
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td">
									��������ID��
								</td>
								<td class="class1_td alignleft">
									<input id=gongnid name=gongnid  type='text' class="inputField required "  onblur='validateJicgnForGongnId();'/>
									*����
									<span id="gongnMessage" style="color:red"></span>
								</td>
							</tr>
							<tr>
								<td class="class1_td">
									�������ƣ�
								</td>
								<td class="class1_td alignleft">
									<input id=gongnmc name=gongnmc type='text' class="inputField required " />
									*��⹦�ܶ���
								</td>
							</tr>
							<tr>
								<td class="class1_td">
									���ܷ��ࣺ
								</td>
								<td class="class1_td alignleft">
									<input id=gongnfl name=gongnfl type='text' class="inputField"/>
									*��⹦�ܶ��� һ�㲻��Ҫ�޸�
								</td>
							</tr>
							<tr>
								<td class="class1_td">
									����URL��
								</td>
								<td class="class1_td alignleft">
									<input id=gongnurl name=gongnurl  type='text' class="inputField required" style="width:320px" />
								</td>
							</tr>
							<tr>
								<td class="class1_td">
									Ȩ��λ�ã�
								</td>
								<td class="class1_td alignleft">
									<input id=quanxwz name=quanxwz  type='text' class="inputField required BAOBBS" style="width:30px"    onblur='validateJicgnForQuanxwz();' />
									*Ȩ��λ�� ע�ⲻҪʹ���Ѿ���ʹ�õ�Ȩ��λ
									<span id="quanxwzMessage" style="color:red"></span>
								</td>
							</tr>
							</form>
			</table>
	</div>
