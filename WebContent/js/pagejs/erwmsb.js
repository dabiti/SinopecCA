function InitOcx()
	{
		try{
			//设置图片转存目录，如果开启转存功能则向此目录保存识别时的图像，如果不设默认目录是C:\VideoQRcap\
			VIDEOCAP_OCX.SetBarCodeCopyImagePath("C:/VideoQRcap/");
		}catch(e){
			alert('系统检测：未安装插件或插件异常!\n错误信息：'+e.message);
		}
	}
	function ConfigCameraPin()
	{
		try{
			//插件调整尺寸的方法
			VIDEOCAP_OCX.VideoConfigCameraPin();
		}catch(e){
			alert('系统检测：未安装插件或插件异常!\n错误信息：'+e.message);
			return;
		}
	}
	function ConfigCameraFilter()
	{
		try{
			//插件调整颜色方法
			VIDEOCAP_OCX.VideoConfigCameraFilter();
		}catch(e){
			alert('系统检测：未安装插件或插件异常!\n错误信息：'+e.message);
			return;
		}
	}
	
	//识别二维码的动作
	function ReadBarCode()
	{
		try{
			if(VIDEOCAP_OCX.VideoGetStatus() == 0 || VIDEOCAP_OCX.VideoGetStatus() == -1){
				alert("扫描设备没有开启，请确认！");
				return;
			}
			//显示扫描中的提示区域
			document.getElementById("tu").style.display = '';
			//调用插件的识别方法
			VIDEOCAP_OCX.VideoSnapShotImage();
		
		}catch(e){
			alert('系统检测：未安装插件或插件异常!\n错误信息：'+e.message);
			return;
		}
	}
	
	//调用插件方法启用扫描设备
	function Play()
	{
		try{
			VIDEOCAP_OCX.VideoPlay();
		}catch(e){
			alert('系统检测：未安装插件或插件异常!\n错误信息：'+e.message);
			return;
		}
	}
	
	//调用插件方法停止扫描设备
	function Stop()
	{
		try{
			VIDEOCAP_OCX.VideoStop();
		}catch(e){
			alert('系统检测：未安装插件或插件异常!\n错误信息：'+e.message);
			return;
		}
	}
	
	function SelectDriver()
	{
		try{
			//选择驱动程序
			VIDEOCAP_OCX.VideoSelectDriver();
		}catch(e){
			alert('系统检测：未安装插件或插件异常!\n错误信息：'+e.message);
			return;
		}
	}
	
	//处理ocx插件返回的扫描结果
	function putMessage(barcode)
	{
		try{
			document.getElementById("errordescript").value = "";
			document.getElementById("readcode").value = "";
			if(barcode == "")
			{
				//如果扫描失败调用ocx插件的方法取得失败原因并弹出提示
				document.getElementById("errordescript").value = VIDEOCAP_OCX.GetLastError();
				alert(document.getElementById("errordescript").value);
			}
			else
			{
				//如果扫描成功，返回的信息放入指定信息区域
				document.getElementById("readcode").value = barcode;
			}
			document.getElementById("tu").style.display = 'none';
			
		}catch(e){
			alert('系统检测：未安装插件或插件异常!\n错误信息：'+e.message);
			return;
		}
	}