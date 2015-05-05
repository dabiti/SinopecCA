/**************************************************/
/**
    文件介绍：本文件中专门存放用于判断多复选框是否全选，是否单选，是否多选，或者是否根本没有复选框而要求多选、单选的情况。
	作者：贾洪亮
	时间：2005-7-1
**/


/*
 函数名：multiChk
 参数名：obj：需要判断的checkbox的名称
 功能：判断传递的obj对象选中的情况
*/
function multiChk(obj)
{
	if(typeof(obj)=="object")  //证明所传递的对象存在
	{
        if(!isNaN(obj.length))  //表示只存在一个对象
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
		return null;    //表示对象不存在
   
}


/*

根据oriObj的选择情况全选obj或者取消全选obj
*/
function checkAll(obj,oriObj)
{
    if(typeof(obj)=="object")  //证明所传递的对象存在
	{
        if(!isNaN(obj.length))  //表示只存在一个对象
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

根据isCheck的选择情况全选obj或者取消全选obj
*/
function checkAll_button(obj,isCheck)
{	
    if(typeof(obj)=="object")  //证明所传递的对象存在
	{
        if(!isNaN(obj.length))  //表示只存在一个对象
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

根据isCheck的选择情况全选obj或者取消全选obj
*/
function checkInverse(obj)
{	
    if(typeof(obj)=="object")  //证明所传递的对象存在
	{
        if(!isNaN(obj.length))  //表示只存在一个对象
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


