<%@ page language="java" contentType="text/html;charset=GBK" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<%@ include file="/common/title.jsp"%>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="js/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/jquery-ztree-2.5.js"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
<script language="javascript">
	var Tree1;
	var datad = ${juesListJsonString};
	
	var isSubmit = false;

	var setting = {
			checkable: true,
			isSimpleData : true,
			treeNodeKey : "id",
			treeNodeParentKey : "pId",
		callback: {
		drop: function zTreeOnDrop(event, treeId, treeNode, targetNode, moveType) {}
	}
	};

	function makeJsonTree() {
		var notes = Tree1.getNodes();
		var simpleTreeNodes = Tree1.transformToArray(notes);
		//Ȩ���ַ���
		var jsonString = "[";
		for ( var i = 0; i < simpleTreeNodes.length; i++) {
			jsonString += "{";
			jsonString += "id:'" + simpleTreeNodes[i].id + "',";
			if (simpleTreeNodes[i].pId != null) {
				jsonString += "pId:'" + simpleTreeNodes[i].pId + "',";
			} else {
				jsonString += "pId:'" + 0 + "',";
			}
			jsonString += "name:'" + simpleTreeNodes[i].name + "'";
			jsonString += ",checked:" + simpleTreeNodes[i].checked;
			jsonString += "}";
			if (i != simpleTreeNodes.length - 1) {
				jsonString += ",";
			}
			isSubmit = isSubmit||simpleTreeNodes[i].checked;
		}
		jsonString += "]";
		return jsonString;
	}

	//��ʼ������
	$(document).ready(function() {
		//Ȩ����  ���� ����
		Tree1 = $("#srcTree").zTree(setting, datad);
		
		$("#form1").validate( {
			errorLabelContainer : "#error div.error",
			wrapper : "li",
			submitHandler : function(form) {
			
			
				var beiz = $("#beiz").val();
				if(beiz.length>100){alert("[��ע]���Ȳ��ܳ���100�ַ�������������!");return;};
				var jsonString = makeJsonTree();
				$("#jsonString").val(jsonString);
				form.action = document.form1.action;
				if(!isSubmit)
					{
						alert("�����ɫ��Ȩ����ɫȨ�޲���Ϊ��!");return;
					}
				form.submit();
				}
			})

		});
</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="38">
					<img src="images/main_place_pic.gif" width="38" height="32">
				</td>
				<td background="images/main_place_bg.gif">
					ϵͳ���� - ��ɫ����
				</td>
			</tr>
			<tr>
				<td>&nbsp;
					
				</td>
				<td>&nbsp;
					
				</td>
			</tr>
		</table>
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="0">
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
		<html:form styleId="form1" action="roleManager.do?method=update" method="post">
			<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
				</tr>
				<tr>
					<td background="images/table_arrow_05.gif">&nbsp;					</td>
				  <td ><table width="500" border="1" align="center">
				  	<thead class="class1_thead">
								<tr>
									<th colspan="2" class="class1_thead th"> 
										�޸Ľ�ɫ
									</th>
								</tr>
					</thead>
                        <tr>
                          <td width="113"><table width="200" height="305" border="0" align="l" cellpadding="0" cellspacing="1" class="class1_table">
                            <b > <div class="class1_thead">�޸Ľ�ɫ��Ϣ</div> </b>
                            <tr>
                              <td class="class1_td w250" >��ɫID</td>
                              <td class="class1_td alignleft"><bean:write name="RoleForm" property="juesid" /></td>
                            </tr>
                            <tr>
                              <td class="class1_td w250">��ɫ���� </td> 
                              <td class="class1_td alignleft"><html:text styleId="juesmc" property="juesmc"    maxlength="50" styleClass="inputField required"/>
                              </td>
                            </tr>
                            <tr>
                              <td class="class1_td w250">��ɫ���� </td>
                              <td class="class1_td alignleft"><html:text styleId="juesms" property="juesms"   maxlength="60"  styleClass="inputField "/>
                              </td>
                            </tr>
                            <tr>
                              <td class="class1_td w250">��ɫ���� </td>
                              <td class="class1_td alignleft">
                              	 <html:select styleId="juesjb" property="juesjb" title="�ȼ�Խ��,����Խ������:3����ɫΪ����ɫ">
								  	<html:option value="1">֧�м�</html:option>
								  	<html:option value="2">�������м�</html:option>
								  	<html:option value="3">һ�����м�</html:option>
								  	<html:option value="4">���м�</html:option>
								  </html:select>
                              </td>
                            </tr>
                            <tr>
                              <td class="class1_td w250">��ע </td>
                              <td class="class1_td alignleft"><html:textarea styleId="beiz" property="beiz"   style="width:130px;height:185px" styleClass="inputField " />
                              </td>
                            </tr>
                          </table></td>
                          <td width="371" height="300"><div> <b > <div class="class1_thead">��ɫ��Ȩ</div> </b></div><div style="position:relative;overflow:auto;width:280px;height:305px;border:1px solid #ccc;">
                            <ul id="srcTree" class="tree" style="width:250px; overflow: auto;">
                            </ul>
                          </div></td>
                    </tr>
                      </table></td>
					<td background="images/table_arrow_07.gif">&nbsp;					</td>
				</tr>
				<tr></tr>
				<tr>
					<td background="images/table_arrow_05.gif">&nbsp;					</td>
					<td>
					<input id=jsonString name=jsonString type='hidden' /></td>
					<td background="images/table_arrow_07.gif">&nbsp;					</td>
				</tr>
				<tr>
					<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
				</tr>
			</table>
			<div class="funbutton">
				<button type="submit" style="width: 60px" onmouseover= this.className = 'buttom2'; onmouseout=this.className = 'buttom1'; class="buttom1" >
					<img src="images/save1.gif" width="12" height="12" align="absmiddle">����
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width: 60px" onmouseover= this.className = 'buttom2'; onmouseout= this.className = 'buttom1'; class="buttom1" onclick=history.back();>
					<img src="images/back1.gif" width="11" height="11"  align="absmiddle">ȡ��
				</button>
			</div>
		</html:form>
	</body>
</html>