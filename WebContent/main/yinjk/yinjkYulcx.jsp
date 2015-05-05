<%@ page contentType="text/html; charset=GBK" language="java"
	isELIgnored="false"%>
<%@ include file="/common/struts1.2.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tld/extremecomponents" prefix="ec"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>中石化对账系统</title>
		<link href="style/right.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css"
			href="style/extremecomponents.css" />
		<script type="text/javascript" src="js/shield.js"></script>
		<script src="js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="js/date/time.js"></script>
				<script type="text/javascript" src="js/yinjk.js"></script>
		<%@ include file="/common/yewgz.jsp"%>
		<script language="javascript" type="text/javascript">
		function validate(key){
			if(key==1){
				var jigh=$("#jigh").val();
				if(jigh==null||jigh.lenght==0){
					$("#jighMsg").html("机构号不可为空");
					return false;
				}
				var reg=/^[0-9]{4,6}$/;
				if(!reg.test(jigh)){
					$("#jighMsg").html("机构号格式不正确");
					return false;
				}
				$("#jighMsg").html('');
				return true;
			}

			if(key==2){
				var guiyh=$("#guiyh").val();
				if(guiyh!=null&&!guiyh.length==0){
					
				var reg=/^[0-9]{7}$/;
				if(!reg.test(guiyh)){
					$("#guiyhMsg").html("柜员号格式不正确");
					return false;
				}
				}
				$("#guiyhMsg").html('');
				return true;
			}

		}

		function submitForm(){
		if(!validate(1))return;
		if(!validate(2))return;
		formSubmit();

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
					印鉴卡余量查询
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					<form id="form1" name="form1" method="post"
						action="yinjkOperate.do?method=yinjkYulcx">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="class1_td w90">
									机构号:

									<html:text property="jigh" styleId="jigh" name="YinjkForm" onkeydown="if(event.keyCode==13){if(!validate(1)){this.focus();return false}}"
										onblur="validate(1);" 
										style="width:70px;" styleClass="inputField required" />
									<font color=red>*</font>
									<span style="color: red" id="jighMsg"></span>
								</td>
								<td class="class1_td w90">
									柜员号:

									<html:text property="guiyh" styleId="guiyh" style="width:70px;"  onkeydown="if(event.keyCode==13){if(!validate(2)){this.focus();return false}}"
										onblur="validate(2);" 
										name="YinjkForm" styleClass="inputField required" />
									<span style="color: red" id="guiyhMsg"></span>

								</td>

								<td class="class1_td alignright w80">
									状态:
								</td>
								<td class="class1_td alignleft">
									<html:select property="yinjkzt" name="YinjkForm">
										<html:option value="全部">全部</html:option>
										<html:option value="分行库存">分行库存</html:option>
										<html:option value="分行在途">分行在途</html:option>
										<html:option value="支行库存">支行库存</html:option>
										<html:option value="支行在途">支行在途</html:option>
										<html:option value="可用">可用</html:option>
										<html:option value="已用">已用</html:option>
										<html:option value="作废">作废</html:option>
										<html:option value="领用">领用</html:option>
										<html:option value="待销毁">待销毁</html:option>
										<html:option value="销毁">销毁</html:option>
									</html:select>
									<font color=red>*</font>
								</td>
								<td width="10%" class="alignleft class1_td">
									启用\停用日期：
								</td>
								<td width="2%" class="alignleft class1_td" align="left">
									<html:text property="begindate" styleId="begindate"
										styleClass="inputField date_input required" maxlength="10"
										style="width:70px;" onclick="WdatePicker()" name="YinjkForm" />
									-
									<html:text property="enddate" styleId="enddate"
										styleClass="inputField date_input required" maxlength="10"
										style="width:70px;" onclick="WdatePicker()" name="YinjkForm" />
								</td>
								<td class="aligncenter w70">
									<button type="submit" style="width: 60px"
										onclick="submitForm()" onmouseover="this.className='buttom2'"
										onmouseout="this.className='buttom1'" class="buttom1">
										<img src="images/search1.gif" width="13" height="13"
											align="middle">
										查询
									</button>
								</td>

								<td width="35%">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</td>

							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
		<div id="tu"></div>
		<form id=error>
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="orange">
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
		<table width="97%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="up-left" />
					<td class="up-middle" />
						<td class="up-right" />
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
									印鉴卡余量查询
								</th>
							</tr>
						</thead>
						<tr>
							<td class="class1_td  w220 alignright">
								机构号:
							</td>
							<td class="class1_td alignleft w220">
								${yinjkNum.jigh}
							</td>

						</tr>
						<c:if test="${yinjkNum.guiyh !=null && yinjkNum.guiyh !=''}">
							<tr>
								<td class="class1_td  w100 alignright">
									柜员号:
								</td>
								<td class="class1_td alignleft w220">
									${yinjkNum.guiyh}
								</td>

							</tr>
						</c:if>
						<tr>
							<td class="class1_td  w100 alignright">
								状态:
							</td>
							<td class="class1_td alignleft w220">
								${yinjkNum.yinjkzt}
							</td>

						</tr>
						<tr>
							<td class="class1_td  w100 alignright">
								张数:
							</td>
							<td class="class1_td alignleft w220">
								${yinjkNum.num}
							</td>
						</tr>
					</table>
				</td>
				<td background="images/table_arrow_07.gif">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td class="bottom-left" />
					<td class="bottom-middle" />
						<td class="bottom-right" />
			</tr>
		</table>

	</body>
</html>
