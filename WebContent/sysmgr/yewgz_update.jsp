<%@ page contentType="text/html; charset=GBK" language="java"
	errorPage="" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<%@ include file="/common/validator.jsp"%>
		<script type="text/javascript">
$(document).ready(function() {
	$("#form1").validate({
	   errorLabelContainer:"#error div.error",
	   wrapper:"li",
	   submitHandler:function(form){
		form.submit();
		window.close();
		}
	});
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
					ҵ������� - �޸�ҵ�������Ϣ
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
		<br>
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
		<html:form styleId="form1" method="post"
			action="yewgzConfig.do?method=update&type=${type}">
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
										�޸�ҵ�������Ϣ
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									Ԫ��ID��
								</td>
								<td class="class1_td alignleft">
									<input id="yuansid" name="yuansid" type="text" readonly="readonly"
										value="${yewgz.yuansid}" class="inputField required"
										style="width: 120px;" />
										*ϵͳ�����õ�Ԫ�ص�ID�����磬account
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									Ԫ�����ƣ�
								</td>
								<td class="class1_td alignleft">
									<input id="yuansname" name="yuansname" type="text"
										value="${yewgz.yuansname}" class="inputField2 "
										style="width: 120px;" />
										*Ԫ�ص��������ƣ����磬�˺�
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									Ԫ����ʽ��
								</td>
								<td class="class1_td alignleft">
									<input id="yuansstyle" name="yuansstyle" type="text"
										value="${yewgz.yuansstyle}" class="inputField2"
										style="width: 120px;" />
										*��Html��sytle�����Զ�����ʽ
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									��������ַ�����
								</td>
								<td class="class1_td alignleft">
									<input id="maxlength" value="${yewgz.maxlength}"
										name="maxlength" type="text" class="inputField2"
										style="width: 120px;" />
											*��Ԫ���������������ַ���
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									У�����
								</td>
								<td class="class1_td alignleft">
									<input id="validaterule" value="${yewgz.validaterule}"
										name="validaterule" type="text" class="inputField2"
										style="width: 120px;" />
								<!-- 
							        <select styleId="validaterule" property="validaterule" >
							          <c:forEach items="${rules}" var="rule">							          
										<c:choose>
									    <c:when test="${yewgz.validaterule==rule.key}">
									      <option value="${rule.key}" selected="selected" >${rule.value}</option>
									    </c:when>
									    <c:otherwise>
									      <option value="${rule.key}" >${rule.value}</option>
									    </c:otherwise>   
										</c:choose>						         
							          </c:forEach>
						            </select>	
								 -->
								 *��������ʽ��ʾ	
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									�Ƿ�ֻ����
								</td>
								<td class="class1_td alignleft">
									<html:select styleId="isreadonly" property="isreadonly"
										value="${yewgz.isreadonly}" style="width:120px;">
										<html:option value="readonly">��</html:option>
										<html:option value="">��</html:option>
									</html:select>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									��ʾ��Ϣid��
								</td>
								<td class="class1_td alignleft">
									<input id="msgid" name="msgid" value="${yewgz.msgid}"
										type="text" class="inputField2" style="width: 120px;" />
										*Ԫ����Ҫ�õ�����ʾ��ϢID�����ڽ�����ʾ�ж���
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									��ע��
								</td>
								<td class="class1_td alignleft">
									<input id="remark" name="remark" value="${yewgz.remark}"
										type="text" class="inputField2" style="width:600px;" />
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
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					����
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width: 60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1"
					onclick="history.back();">
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					ȡ��
				</button>
			</div>
		</html:form>
	</body>
</html>
