<%@ page language="java" contentType="text/html;charset=gbk"  isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>��ʯ������ϵͳ</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link href="style/extremecomponents.css" rel="stylesheet" type="text/css">
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script src="js/exportPdf.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript" src="js/date/time.js"></script>
		<script src="js/pagejs/ocx.js" type="text/javascript"></script>
		<script src="js/pagejs/validateForm.js" type="text/javascript"></script>
		<script type="text/javascript">
			 $(document).ready(function() {
			 
			 
			//��֤
			$("#sealchecklogForm").validate({
			
				
			   errorLabelContainer:"#error div.error",
			   wrapper:"li",
			   submitHandler:function(form){
			   	form.submit();
			   }
			});
			})
			function exportPdf(){
					var jlist='${jlist}';
					var jsql=$("#jsql").val();
				//	alert(jsql);
			if(jlist==null||jlist.length==0){
				return;
				}
			if($("#className").val()==null||$("#className").val().length==0){
				return;
				}
			$("#jlist").val(encodeURI(jlist));
			if(jsql.indexOf("%20")==-1){
				$("#jsql").val(encodeURI(jsql));
			}
			$("#exportForm").submit();
	}
		//	alert($("#checknum").val());
		</script>
	</head>
	<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
	<input id="sessionURL" name="sessionURL" type=hidden value="${sessionURL}"/>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					��־��ѯ - ������ӡ��־��ѯ
				</td>
			</tr>
			<tr>
				<td >
				<table  width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
				<td class="class1_td alignright">&nbsp;</td>
					<td class="class1_td alignleft">&nbsp;
								</td>						
				</tr>
				<tr>
				<td class="class1_td alignright">&nbsp;</td>
								<td class="class1_td alignleft">&nbsp;
								</td>		
				</tr>
				
				</table>
				</td>
				<td style="text-align: left">
					<html:form styleId="sealchecklogForm" action="/batchsealchecklog"
						method="post">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="class1_td alignright">�����ţ�</td>
								<td class="class1_td alignleft">
									<html:text property="orgCode" styleId="orgCode" styleClass="inputField required orgCode"/><span style="color:red">*</span>
								</td>		
								<td class="class1_td alignright">�˺ţ�</td>
								<td class="class1_td alignleft">
									<html:text property="account" styleId="account" styleClass="inputField account"/>
								</td>		
							
								<td class="class1_td alignright">ƾ֤�ţ�</td>
								<td class="class1_td alignleft">
									<html:text property="checknum" styleId="checknum" styleClass="inputField checknum"/>
								</td>
								<td class="class1_td alignright">��Ա���룺</td>
								<td class="class1_td alignleft">
									<html:text property="clerknum" styleId="clerknum" styleClass="inputField clerknum"/>
								</td>
							</tr>
							<tr>
								<td class="class1_td alignright">��ӡ���ڣ�</td>
								<td class="class1_td alignleft">
									<html:text property="begindate" styleId="begindate" styleClass="inputField date_input required"  onclick="WdatePicker()" />��<html:text property="enddate" styleId="enddate" styleClass="inputField date_input required"  onclick="WdatePicker()"  />
								</td>
								<td class="class1_td alignright">��ӡ�����</td>
								<td class="class1_td alignleft">
									<html:select property="checkresult" style="width:65px;">
										<html:option value="">ȫ��</html:option>
										<html:option value="ͨ��">ͨ��</html:option>
										<html:option value="δ��">δ��</html:option>
										<html:option value="δ��">δ��</html:option>
										<html:option value="δ�ҵ�">δ�ҵ�</html:option>
									</html:select>
								</td>
								<td class="class1_td alignright">��ӡ��ʽ��</td>
								<td class="class1_td alignleft">
									<html:select property="checkmode" style="width:65px;">
										<html:option value="">ȫ��</html:option>
										<html:option value="�Զ�">�Զ�</html:option>
										<html:option value="�˹�">�˹�</html:option>
										<html:option value="����">����</html:option>
									</html:select>
								</td>
								<td class="class1_td alignleft">&nbsp;</td>
								<td class="class1_td alignleft">
									<button type="submit" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1">
										<img src="images/search1.gif" width="13" height="13" align="middle">��ѯ
									</button>
								</td>
							</tr>
						</table>
					</html:form>
				</td>
			</tr>
		</table>

		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange error">
						<div class="error orange" id="abc1"></div>
					</td>
				</tr>
				<logic:messagesPresent>
					<tr>
						<td class="orange">
							<div id="abc" class="abc"><html:errors /></div>
						</td>
					</tr>
				</logic:messagesPresent>
			</table>
		</form>
				<br>
		<form action="ajax.do?method=exportPdf" id="exportForm" method="post">
		<input type="hidden" id="jlist" name="jlist" />
		<input type="hidden" id="jsql" name="jsql" value="${jsql}"/>
		<input type="hidden" id="className" name="className" value="SealcheckLog1"/>
		</form>
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
					<button type="button" style="width:60px" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" onClick="exportPdf();">
									<img src="images/add.gif" width="13" height="13" align="absmiddle"/>����</button>
					<ec:table retrieveRowsCallback="org.extremecomponents.table.callback.LimitCallback"
        			  filterRowsCallback="org.extremecomponents.table.callback.LimitCallback" 
			          sortRowsCallback="org.extremecomponents.table.callback.LimitCallback"
			          filterable="false" sortable="false" title="��ӡ���"
						showPagination="true" view="compact" items="list"
						var="sealchecklog"  rowsDisplayed="${ec_yemjlts}" 
						action="${pageContext.request.contextPath}/batchsealchecklog.do"
						imagePath="${pageContext.request.contextPath}/images/table/*.gif">
						<c:choose>
							<c:when test="${list_size>=60000}">
								<ec:exportCsv fileName="sealchecklog.txt" tooltip="Export Text" />
							</c:when>
							<c:otherwise>  
								<ec:exportXls fileName="sealchecklog.xls" tooltip="Export Excel" />
							</c:otherwise> 
						</c:choose>
						<ec:row>
							<ec:column property="account" title="�˺�" />
							<ec:column property="accountname" title="����" />
							<ec:column property="qiyrq" title="��������" />
							<ec:column property="checknum" title="ƾ֤��" />
							<ec:column property="sealinknum" title="ӡ�����" />
							<ec:column property="sealinktype" title="ӡ������" />							
							<ec:column property="checkdate" title="��ӡʱ��" />
							<ec:column property="clerknum" title="��Ա����" />
							<ec:column property="checkmode" title="��ӡ��ʽ" />
							<ec:column property="checkresult" title="��ӡ���" />
					<%	//	<unitop:HasPrivilegeForSystemConfig name="yingjkll">
						//	<ec:column property="���" title="���Ԥ��ӡ��" >
						//	<a href='javascript:try{obj.ShowOcxForm("{\"ocxid\":\"4\",\"guiyxx\":${jsonClerkrStr},\"ocxparam\":{\"zhangh\":\"${sealchecklog.account}\",\"yinjbh\":\"${sealchecklog.sealinknum}\",\"qiyrq\":\"${sealchecklog.qiyrq}\"}}");}catch(e){alert("ϵͳ��⣺����û�а�װ��ӡ���!");};'>���</a>
						//</ec:column>
						//	</unitop:HasPrivilegeForSystemConfig>%>
						</ec:row> 
					</ec:table>
				</td>
				<td background="images/table_arrow_07.gif">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
			</tr>
		</table>
		<div class="stat"></div>
	   	<script language="JavaScript" FOR="obj" EVENT="SendEvent(sendMessage)">
	 		var rMessage = SendSesssion("uniDBInterface.jsp",sendMessage,"text");
    		return rMessage;
	    </script>
	    <OBJECT ID="obj" CLASSID="clsid:87100b1f-19fa-4266-a03e-0536ffa3c8af" style="display:none"></OBJECT>
</html>