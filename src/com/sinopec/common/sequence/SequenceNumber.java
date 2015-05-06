package com.sinopec.common.sequence;


import com.unitop.sysmgr.dao.SystemControlParametersDao;

//������кŽӿ�
public class SequenceNumber {
	
	private static String num;	
	private SystemControlParametersDao scpd;
	
	//���캯��
	public SequenceNumber(SystemControlParametersDao scpd){
		this.scpd=scpd;
	}
	
	public String getNumber(String weishu){		
		//�Զ�����λ��
		int wslength = Integer.parseInt(weishu);		
		StringBuilder sb = new StringBuilder();
		StringBuilder maxsb = new StringBuilder();		
		
		//����		
		if(num==null)
		{
			for(int i=0;i<wslength-1;i++)
			{
				sb.append("0");
			}			
			sb.append("1");
			num="2";
			return sb.toString();
		}else{
			//�ж��Ƿ�����������,���������򷵻ػ���		
			for(int j=0;j<wslength;j++)
			{
				maxsb.append("9");							
			}
			if(num.equals(maxsb.toString()))
			{				
				num = null;				
				sb.append(maxsb);				
				String value = String.valueOf(Integer.parseInt(scpd.updateSequence("xit_sequence"))-1);
				SequenceHread.baseNumber = value;				
				return sb.toString();
			}			
			for(int i=0;i<wslength-num.length();i++)
			{
				sb.append("0");
			}			
			sb.append(num);	
			//num����
			int temp=Integer.parseInt(num);
			num=String.valueOf(++temp);					
			return sb.toString();
		}		
	}
}
