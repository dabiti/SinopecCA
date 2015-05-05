package com.sinodata.common.sequence;
/**
 * 获得序列号头
 * 
 * @author huangss
 * 
 */
public class SequenceHread {
	
	public static String baseNumber = "";

	public String getHread(String weishu){
		
		StringBuffer sb = new StringBuffer();
		int ws = Integer.parseInt(weishu);
		
		if (ws > baseNumber.length()) 
		{
			for (int i = 0; i < ws - baseNumber.length(); i++)
			{
				sb.append("0");
			}
			sb.append(baseNumber);
			return sb.toString();
		}
		return baseNumber;
	}
}