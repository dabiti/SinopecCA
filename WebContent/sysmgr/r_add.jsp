<%@ page contentType="text/html; charset=GBK" language="java" errorPage="" isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/shield.js" type="text/javascript"></script>
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<%@ include file="/common/validator.jsp"%>	
		<script type="text/javascript">
			 $(document).ready(function() {
			 $("#form").validate({
			  wrapper:"li",
			   submitHandler:function(form){
			   	form.submit();
			   }
			   })
			 })
		</script>		
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					Ureport���� - ��������
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
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange error">
						<div class="error orange"></div>
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
		<html:form styleId="form" action="ureportMgr.do?method=save"
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
									<th colspan="2" class="class1_thead th">
										��������
									</th>
								</tr>
							</thead>

							<tr>
								<td class="class1_td w250">
									������ʶ��
								</td>
								<td class="class1_td alignleft">
									<html:text styleClass="inputField required BAOBBS"  property="map(BAOBBS)" maxlength="20" style="width:145px; " />
									*�������Զ���
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									�������ƣ�
								</td>
								<td class="class1_td alignleft">
									<html:text property="map(BAOBMC)" styleClass="inputField required"  maxlength="20" style="width:145px;" />
									*���磺��ӡ��־��ѯ
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									�������⣺
								</td>
								<td class="class1_td alignleft">
									<html:text  property="map(BAOBBT)" styleClass="inputField required"  maxlength="20" style="width:145px; " />
									*������ͷǰ��ı��⣬���磺��ӡ��־�б�
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
								�Ƿ���ã�
								</td>
								<td class="class1_td alignleft">
								<html:select property="map(SHIFKY)">
									<option value="��">��</option>
									<option value="��">��</option>
								</html:select>
								*�����Ƿ�����
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
								��ҳ������
								</td>
								<td class="class1_td alignleft">
								<html:text  styleClass="inputField required BAOBBS"   property="map(FENYTJ)" maxlength="20" style="width:145px; " />
								*ÿ����ʾ����������
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
								���ݻ�ȡ��ʶ��
								</td>
								<td class="class1_td alignleft">
								<html:textarea styleClass="required"  property="map(SHUJHQFS)" cols="80" rows="3"></html:textarea>
								*���磺����clerktable ��  ��ͼ��(select account from accountinfo)
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
								�Զ����ࣺ
								</td>
								<td class="class1_td alignleft">
								<html:text  styleClass="inputField "   property="map(ZHIDYL)"  style="width:499px; " />
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
				<button type="submit" style="width:60px" type="submit"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					����
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="history.back()">
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					ȡ��
				</button>
			</div>
		</html:form>
	</body>
</html>