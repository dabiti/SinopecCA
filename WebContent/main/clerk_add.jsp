<%@page language="java" contentType="text/html;charset=GBK" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib uri="/WEB-INF/tld/unitop.tld" prefix="unitop"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="js/shield.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script type="text/javascript" src="js/pagejs/clerk.js"></script>
		<script type="text/javascript" language="javascript">
		function changeState(){
			if($("#leixbs").val()=="0"){
				$("#pwdfirst").hide();
				$("#pwdsecond").hide();
				$("#password").val("AAAAAAAA");
				$("#password1").val("AAAAAAAA");
				var data=getNewClerkCode();
				if(data.length>6){
					alert(data);
					$("#leixbs").val("1");
					$("#hxgy").attr("selected","selected");
					return;
				}else{
					$("#clerknum_7").attr("readonly","readonly").css("color","gray").val(data);
				}
				
			}
			if($("#leixbs").val()=="1"){
				$("#pwdfirst").hide();
				$("#pwdsecond").hide();
				$("#password").val("AAAAAAAA");
				$("#password1").val("AAAAAAAA");
				$("#clerknum_7").attr("readonly","").css("color","").val("");
			}
		}
		//自动获取验印柜员号
		function getNewClerkCode(){
			 var clerkcode="";
			 var orgcode=$("#orgcode").val();
			
			 $.ajax({
				 	 async:false,
				 	 url:"clerkManage.do?method=getNewClerkCode",
					 dataType:"text",
					 data: {orgcode:orgcode},
					 cache:false,
					 success:function (data,textStatus){
						 if(textStatus=="success")
							 if(data.indexOf('<html>')!=-1){
									window.location.href='timeOutlogin.jsp';
									return;
								}
							 clerkcode=data;
					 }
			 	}
			 )
			 return clerkcode;
			}
		$(function(){
			$("#pwdfirst").hide();
			$("#pwdsecond").hide();
			$("#password").val("AAAAAAAA");
			$("#password1").val("AAAAAAAA");
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
					系统管理 - 柜员管理
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
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td id="errorMessage"class="orange error">
					<div class="error orange"></div>
				</td>
			</tr>
		</table>
		<form id=error name=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange error">
						<div class="error orange">
							${msg}
						</div>
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
		<html:form styleId="form1" action="clerkManage.do?method=createClerk"
			method="post">
			<table width="97%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<html:hidden property="orgcode" />
				<html:hidden property="orgname" />
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
										新增柜员
									</th>
								</tr>
							</thead>
							<tr>
								<td class="class1_td w250">
									机构号：
								</td>
								<td class="class1_td alignleft">
									<bean:write name="clerkForm" property="orgcode" />
									<input type="hidden" value="<bean:write name="clerkForm" property="orgcode" />" id ="orgcode">
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									机构名称：
								</td>
								<td class="class1_td alignleft">
									<logic:notEmpty name="clerkForm" property="orgname">
										<bean:write name="clerkForm" property="orgname" />
									</logic:notEmpty>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									柜员类型：
								</td>
								<td class="class1_td alignleft">
									<select name="leixbs" id="leixbs" class="required" onclick="changeState();" onkeyup="changeState();">
									<option value="1" id="hxgy">核心柜员</option>
										<unitop:HasPrivilegeForZignTag name="31000|YYGY_001">
										<option value="0">验印柜员</option>
										</unitop:HasPrivilegeForZignTag>
									
									</select>
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									柜员编号：
								</td>
								<td class="class1_td alignleft">
									<html:text property="code" styleId="clerknum_7"  styleClass="inputField required clerknum_7"  />
								</td>
							</tr>
							<tr>
								<td class="class1_td w250">
									柜员名称：
								</td>
								<td class="class1_td alignleft">
									<html:text property="name" styleId="clerkname" styleClass="inputField required"/>
								</td>
							</tr>
								
							<tr id="pwdfirst">
								<td class="class1_td w250">
									密码：
								</td>
								<td class="class1_td alignleft">
									<html:password property="password" styleId="password"
										styleClass="inputField required clerk_password"
										/>
								</td>
							</tr>
							<tr id="pwdsecond">
								<td class="class1_td w250">
									重复密码：
								</td>
								<td class="class1_td alignleft">
									<html:password property="password1" styleId="password1"
										styleClass="inputField required clerk_password"
										 />
								</td>
							</tr>
						
							
 							<tr>
								<td class="class1_td w250">
									角色添加：
								</td>
							<td class="class1_td alignleft"> 
							<table>
								<tr>
									<td>
									<img src="images/people.gif" width="12" height="12" align="absmiddle"> 
									   角色列表
									</td>
									<td>
									</td>
									<td>
									<img src="images/people.gif" width="12" height="12" align="absmiddle"> 
									   所选列表
									</td>
								</tr>
								<tr>
									<td>
										<select  name="s1" id="s1"  multiple="multiple" style="width:150px;height:100px;" ondblclick="moveTo('s1','s2');">
											 <c:forEach items="${rolelist}" var="role">
									            <option value="${role.juesid}">${role.juesmc}</option>
									          </c:forEach>
										</select>
									</td>
									<td>
										<input type="button" value="--&gt;" style="width:100px;" onclick="moveTo('s1','s2');"/><br/>
										<input type="button" value="--&gt;&gt;" style="width:100px;" onclick="moveAllTo('s1','s2');"/><br/>
										<input type="button" value="&lt;--" style="width:100px;" onclick="moveTo('s2','s1');"/><br/>
										<input type="button" value="&lt;&lt;--" style="width:100px;" onclick="moveAllTo('s2','s1');"/><br/>
									</td>
									<td>
										<select name="s2" id="s2" multiple="multiple" style="width:150px;height:100px;" ondblclick="moveTo('s2','s1');">
										</select>
									</td>
								</tr>
						</table>
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
				<button type="submit" style="width:60px"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/save1.gif" width="12" height="12"
						align="absmiddle">
					保存
				</button>
				&nbsp;&nbsp;&nbsp;
				<button type="button" style="width:60px" onclick="history.back()"
					onmouseover="this.className='buttom2'"
					onmouseout="this.className='buttom1'" class="buttom1">
					<img src="images/back1.gif" width="11" height="11"
						align="absmiddle">
					取消
				</button>
			</div>
		</html:form>
	</body>
</html>
