function InitOcx()
	{
		try{
			//����ͼƬת��Ŀ¼���������ת�湦�������Ŀ¼����ʶ��ʱ��ͼ���������Ĭ��Ŀ¼��C:\VideoQRcap\
			VIDEOCAP_OCX.SetBarCodeCopyImagePath("C:/VideoQRcap/");
		}catch(e){
			alert('ϵͳ��⣺δ��װ��������쳣!\n������Ϣ��'+e.message);
		}
	}
	function ConfigCameraPin()
	{
		try{
			//��������ߴ�ķ���
			VIDEOCAP_OCX.VideoConfigCameraPin();
		}catch(e){
			alert('ϵͳ��⣺δ��װ��������쳣!\n������Ϣ��'+e.message);
			return;
		}
	}
	function ConfigCameraFilter()
	{
		try{
			//���������ɫ����
			VIDEOCAP_OCX.VideoConfigCameraFilter();
		}catch(e){
			alert('ϵͳ��⣺δ��װ��������쳣!\n������Ϣ��'+e.message);
			return;
		}
	}
	
	//ʶ���ά��Ķ���
	function ReadBarCode()
	{
		try{
			if(VIDEOCAP_OCX.VideoGetStatus() == 0 || VIDEOCAP_OCX.VideoGetStatus() == -1){
				alert("ɨ���豸û�п�������ȷ�ϣ�");
				return;
			}
			//��ʾɨ���е���ʾ����
			document.getElementById("tu").style.display = '';
			//���ò����ʶ�𷽷�
			VIDEOCAP_OCX.VideoSnapShotImage();
		
		}catch(e){
			alert('ϵͳ��⣺δ��װ��������쳣!\n������Ϣ��'+e.message);
			return;
		}
	}
	
	//���ò����������ɨ���豸
	function Play()
	{
		try{
			VIDEOCAP_OCX.VideoPlay();
		}catch(e){
			alert('ϵͳ��⣺δ��װ��������쳣!\n������Ϣ��'+e.message);
			return;
		}
	}
	
	//���ò������ֹͣɨ���豸
	function Stop()
	{
		try{
			VIDEOCAP_OCX.VideoStop();
		}catch(e){
			alert('ϵͳ��⣺δ��װ��������쳣!\n������Ϣ��'+e.message);
			return;
		}
	}
	
	function SelectDriver()
	{
		try{
			//ѡ����������
			VIDEOCAP_OCX.VideoSelectDriver();
		}catch(e){
			alert('ϵͳ��⣺δ��װ��������쳣!\n������Ϣ��'+e.message);
			return;
		}
	}
	
	//����ocx������ص�ɨ����
	function putMessage(barcode)
	{
		try{
			document.getElementById("errordescript").value = "";
			document.getElementById("readcode").value = "";
			if(barcode == "")
			{
				//���ɨ��ʧ�ܵ���ocx����ķ���ȡ��ʧ��ԭ�򲢵�����ʾ
				document.getElementById("errordescript").value = VIDEOCAP_OCX.GetLastError();
				alert(document.getElementById("errordescript").value);
			}
			else
			{
				//���ɨ��ɹ������ص���Ϣ����ָ����Ϣ����
				document.getElementById("readcode").value = barcode;
			}
			document.getElementById("tu").style.display = 'none';
			
		}catch(e){
			alert('ϵͳ��⣺δ��װ��������쳣!\n������Ϣ��'+e.message);
			return;
		}
	}