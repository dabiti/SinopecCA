<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<title>��ʯ������ϵͳ</title>
	<link href="${pageContext.request.contextPath}/style/right.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/js/pagejs/erwmsb.js" type="text/javascript"></script>
	<script language="VBscript">
	Sub VIDEOCAP_OCX_InitVideoFinish()
		'' ��Ƶ�ؼ���ʼ�����֪ͨ,���еĲ��������ڴ�֮��
		InitOcx()
	end sub

	Sub VIDEOCAP_OCX_NotifyBarCodeRead(barcode)
    	putMessage(barcode)
	end sub
	
	Sub SCAN_OCX_OnScanEnd()
   		ScanEnd()
	end sub
	</script>
</head>
<body onkeydown='if(event.keyCode==78&&event.ctrlKey)return false;'>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="38">
				<img src="${pageContext.request.contextPath}/images/main_place_pic.gif" width="38" height="32">
			</td>
			<td	background="${pageContext.request.contextPath}/images/main_place_bg.gif">
				ʵ���α - ��ά��ʶ��				
			</td>
		</tr>
	</table>
	<table width="90%" align="center" cellpadding="0" cellspacing="0" border=0><!--DWLayoutTable-->
		<tr class="class1_td alignleft" style="margin-top:10px;">
			<td class="class1_td alignleft" colspan="2">ʶ�����ݣ�<input id="readcode" name="readcode" type="text" name="MaxPages" size="96"  value=""></td>
			<td class="class1_td alignleft" style="display: none;">ʧ��ԭ��<input id="errordescript" name="errordescript" type="text" name="MaxPages" size="40"  value=""></td>
		</tr>
		
		<tr height="20px" class="class1_td alignleft">
			<td class="class1_td alignleft">�������ã�<input type="button" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" value="ѡ���豸" onClick="javascript:SelectDriver()" id="button75" name="button75">
			<input type="button" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" value="�����ߴ�" onClick="javascript:ConfigCameraPin()"  id="button70" name="button70">
			<input type="button" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" value="������ɫ" onClick="javascript:ConfigCameraFilter()" id="button71" name="button71"></td>
		    <td class="class1_td alignleft">
		    	ɨ������
				<select id="mingma" name="mingma"  size="1" onChange="javascript:VIDEOCAP_OCX.SetBarCodeType(this.value);">
					<option value="0" selected="selected">����</option>
			      	<option value="1">����</option>
		      	</select>
		    	�ļ�ת��
		    	<select id="FileType" name="FileType"  size="1" onChange="javascript:VIDEOCAP_OCX.SetBarCodeCopyImage(this.value);">
			    	<option value="0" selected="selected">���洢</option>
			    	<option value="1">�洢</option>
			    </select>
			</td>
		</tr>
		<tr height="20px" class="class1_td alignleft">

			<td class="class1_td alignleft" colspan="2">ʶ����ƣ�<input type="button" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" value="�����豸" onClick="javascript:Play()" id="button76" name="button76">
			<input type="button" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" value="����ʶ��" onClick="javascript:ReadBarCode()" id="button72" name="button72">
			<input type="button" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" value="�ر��豸" onClick="javascript:Stop()" id="button76" name="button76"></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="10" bgcolor="#FFFFFF" style="display: none;">
				<div align="center">
					<strong>��WEBɨ��ؼ����÷������롷</strong>
				</div>
			</td>
		</tr>
	</table>
	<div id="tu" style="display: none;">
		<table align="center">
			<tr><td><img border="1" src="${pageContext.request.contextPath}/images/appInstall_animated.gif"/><b> ʶ���У����Ե�......<b></td></tr>
		</table>
	</div>
	<table width="418" border="0" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td class="up-left"/><td class="up-middle"/><td class="up-right"/>
		</tr>
		<tr>
			<td background="${pageContext.request.contextPath}/images/table_arrow_05.gif">
				&nbsp;
			</td>
			<td align="center" height="20">
			 	<strong>ɨ��ʶ������</strong>
			</td>
			<td background="${pageContext.request.contextPath}/images/table_arrow_07.gif">
					&nbsp;
			</td>
		</tr>
		<tr>
			<td background="${pageContext.request.contextPath}/images/table_arrow_05.gif">
				&nbsp;
			</td>
			<td>
			 	<!--�ؼ� -->
				<div style="padding:0">
				   <OBJECT ID="VIDEOCAP_OCX" WIDTH="388" HEIGHT="290" CLASSID="CLSID:7E2962B6-5106-4D45-A246-E3F04E526A06" >
				   </OBJECT>
				</div>
			</td>
			<td background="${pageContext.request.contextPath}/images/table_arrow_07.gif">
					&nbsp;
			</td>
		</tr>
		<tr>
			<td class="bottom-left"/><td class="bottom-middle"/><td class="bottom-right"/>
		</tr>
	</table>
</body>
</html>
