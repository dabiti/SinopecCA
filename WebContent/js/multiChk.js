/**************************************************/
/**
    �ļ����ܣ����ļ���ר�Ŵ�������ж϶ิѡ���Ƿ�ȫѡ���Ƿ�ѡ���Ƿ��ѡ�������Ƿ����û�и�ѡ���Ҫ���ѡ����ѡ�������
	���ߣ��ֺ���
	ʱ�䣺2005-7-1
**/


/*
 ��������multiChk
 ��������obj����Ҫ�жϵ�checkbox������
 ���ܣ��жϴ��ݵ�obj����ѡ�е����
*/
function multiChk(obj)
{
	if(typeof(obj)=="object")  //֤�������ݵĶ������
	{
        if(!isNaN(obj.length))  //��ʾֻ����һ������
		{
			
           var int_n=0;
		   for(j=0;j<obj.length;j++)
			{
			   if(obj[j].checked) int_n=int_n+1;
		   }

		   return int_n;
		}
		else
		{
			if(obj.checked) return 1;
			else
				return 0;
		}
	}
	else
		return null;    //��ʾ���󲻴���
   
}


/*

����oriObj��ѡ�����ȫѡobj����ȡ��ȫѡobj
*/
function checkAll(obj,oriObj)
{
    if(typeof(obj)=="object")  //֤�������ݵĶ������
	{
        if(!isNaN(obj.length))  //��ʾֻ����һ������
		{          
		   for(j=0;j<obj.length;j++)
			{
			   if(oriObj.checked)
			    obj[j].checked=true;
			   else
				   obj[j].checked=false;
		    }		  
		}
		else
		{
			if(oriObj.checked)
			  obj.checked=true;
			else
			obj.checked=false;
		}
	}
	
}

/*

����isCheck��ѡ�����ȫѡobj����ȡ��ȫѡobj
*/
function checkAll_button(obj,isCheck)
{	
    if(typeof(obj)=="object")  //֤�������ݵĶ������
	{
        if(!isNaN(obj.length))  //��ʾֻ����һ������
		{          
		   for(j=0;j<obj.length;j++)
			{			   
			    obj[j].checked=isCheck;			   
		  }		  
		}
		else
		{			
			  obj.checked=isCheck;			
		}
	}	
}


/*

����isCheck��ѡ�����ȫѡobj����ȡ��ȫѡobj
*/
function checkInverse(obj)
{	
    if(typeof(obj)=="object")  //֤�������ݵĶ������
	{
        if(!isNaN(obj.length))  //��ʾֻ����һ������
		{          
		   for(j=0;j<obj.length;j++)
			{			
				if(obj[j].checked)   
			    obj[j].checked=false;			
			  else
			    obj[j].checked=true;				    	   
		  }		  
		}
		else
		{			
			 if(obj.checked)   
			    obj.checked=false;			
			  else
			    obj.checked=true;		
		}
	}	
}


