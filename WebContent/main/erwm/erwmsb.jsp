<%@ page contentType="text/html; charset=GBK" language="java" isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<title>中石化对账系统</title>
	<link href="${pageContext.request.contextPath}/style/right.css" rel="stylesheet" type="text/css">
	<script src="${pageContext.request.contextPath}/js/pagejs/erwmsb.js" type="text/javascript"></script>
	<script language="VBscript">
	Sub VIDEOCAP_OCX_InitVideoFinish()
		'' 视频控件初始化完成通知,所有的操作必须在此之后
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
				实物防伪 - 二维码识别				
			</td>
		</tr>
	</table>
	<table width="90%" align="center" cellpadding="0" cellspacing="0" border=0><!--DWLayoutTable-->
		<tr class="class1_td alignleft" style="margin-top:10px;">
			<td class="class1_td alignleft" colspan="2">识别内容：<input id="readcode" name="readcode" type="text" name="MaxPages" size="96"  value=""></td>
			<td class="class1_td alignleft" style="display: none;">失败原因：<input id="errordescript" name="errordescript" type="text" name="MaxPages" size="40"  value=""></td>
		</tr>
		
		<tr height="20px" class="class1_td alignleft">
			<td class="class1_td alignleft">属性设置：<input type="button" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" value="选择设备" onClick="javascript:SelectDriver()" id="button75" name="button75">
			<input type="button" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" value="调整尺寸" onClick="javascript:ConfigCameraPin()"  id="button70" name="button70">
			<input type="button" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" value="调整颜色" onClick="javascript:ConfigCameraFilter()" id="button71" name="button71"></td>
		    <td class="class1_td alignleft">
		    	扫描类型
				<select id="mingma" name="mingma"  size="1" onChange="javascript:VIDEOCAP_OCX.SetBarCodeType(this.value);">
					<option value="0" selected="selected">隐码</option>
			      	<option value="1">明码</option>
		      	</select>
		    	文件转存
		    	<select id="FileType" name="FileType"  size="1" onChange="javascript:VIDEOCAP_OCX.SetBarCodeCopyImage(this.value);">
			    	<option value="0" selected="selected">不存储</option>
			    	<option value="1">存储</option>
			    </select>
			</td>
		</tr>
		<tr height="20px" class="class1_td alignleft">

			<td class="class1_td alignleft" colspan="2">识别控制：<input type="button" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" value="开启设备" onClick="javascript:Play()" id="button76" name="button76">
			<input type="button" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" value="条码识别" onClick="javascript:ReadBarCode()" id="button72" name="button72">
			<input type="button" onmouseover="this.className='buttom2'" onmouseout="this.className='buttom1'" class="buttom1" value="关闭设备" onClick="javascript:Stop()" id="button76" name="button76"></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="10" bgcolor="#FFFFFF" style="display: none;">
				<div align="center">
					<strong>《WEB扫描控件调用方法输入》</strong>
				</div>
			</td>
		</tr>
	</table>
	<div id="tu" style="display: none;">
		<table align="center">
			<tr><td><img border="1" src="${pageContext.request.contextPath}/images/appInstall_animated.gif"/><b> 识别中，请稍等......<b></td></tr>
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
			 	<strong>扫描识别区域</strong>
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
			 	<!--控件 -->
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
