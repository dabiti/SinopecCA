package com.unitop.sysmgr.service.impl;

import java.util.Calendar;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unitop.framework.util.DateModel;
import com.unitop.framework.util.DateTool;
import com.unitop.sysmgr.bo.JiejrBo;
import com.unitop.sysmgr.dao.JiejrglDao;
import com.unitop.sysmgr.service.JiejrService;

/*
 * �ڼ��չ��� by ldd 
 * 2012=06-05
 */
@Service("JiejrServiceImpl")
public class JiejrServiceImpl implements JiejrService {
	@Resource
	private JiejrglDao JiejrglDao;
	
	public JiejrBo getJiejr(String year) {
		// DAO ��ȡ ��ʼ�� year����
		JiejrBo jierBo =  new JiejrBo();
		jierBo.setMonth_01(toJiejrBo(year,"01"));
		jierBo.setMonth_02(toJiejrBo(year,"02"));
		jierBo.setMonth_03(toJiejrBo(year,"03"));
		jierBo.setMonth_04(toJiejrBo(year,"04"));
		jierBo.setMonth_05(toJiejrBo(year,"05"));
		jierBo.setMonth_06(toJiejrBo(year,"06"));
		jierBo.setMonth_07(toJiejrBo(year,"07"));
		jierBo.setMonth_08(toJiejrBo(year,"08"));
		jierBo.setMonth_09(toJiejrBo(year,"09"));
		jierBo.setMonth_10(toJiejrBo(year,"10"));
		jierBo.setMonth_11(toJiejrBo(year,"11"));
		jierBo.setMonth_12(toJiejrBo(year,"12"));
		
		//��ȡĳ��ڼ�������
		JiejrBo jiejr = JiejrglDao.getJiejrBo(year);
		//�ж��Ƿ��Ѿ�����ڼ������ݣ����û��ֱ�ӷ��س�ʼ��ĳ��ڼ������ݣ����ڷ��ز�ѯ���������
		if(jiejr == null) 
		{
			return jierBo;
		}else{
			//�ڼ�������ת��
			jiejr.setMonth_01(doMonthString(jierBo.getMonth_01(),jiejr.getMonth_01()));
			jiejr.setMonth_02(doMonthString(jierBo.getMonth_02(),jiejr.getMonth_02()));
			jiejr.setMonth_03(doMonthString(jierBo.getMonth_03(),jiejr.getMonth_03()));
			jiejr.setMonth_04(doMonthString(jierBo.getMonth_04(),jiejr.getMonth_04()));
			jiejr.setMonth_05(doMonthString(jierBo.getMonth_05(),jiejr.getMonth_05()));
			jiejr.setMonth_06(doMonthString(jierBo.getMonth_06(),jiejr.getMonth_06()));
			jiejr.setMonth_07(doMonthString(jierBo.getMonth_07(),jiejr.getMonth_07()));
			jiejr.setMonth_08(doMonthString(jierBo.getMonth_08(),jiejr.getMonth_08()));
			jiejr.setMonth_09(doMonthString(jierBo.getMonth_09(),jiejr.getMonth_09()));
			jiejr.setMonth_10(doMonthString(jierBo.getMonth_10(),jiejr.getMonth_10()));
			jiejr.setMonth_11(doMonthString(jierBo.getMonth_11(),jiejr.getMonth_11()));
			jiejr.setMonth_12(doMonthString(jierBo.getMonth_12(),jiejr.getMonth_12()));
			return jiejr;
		}
	}
	//���ýڼ������ݡ�ɾ��ĳ��ڼ������ݡ�
	public void deletJiejr(String year) {
		JiejrBo jierBo =  new JiejrBo();
		jierBo.setYear(year);
		JiejrglDao.deleteJiejrBo(jierBo);
	}
	//���ӽڼ�������
	public void updateOrSaveJiejr(JiejrBo JiejrBo) {
		JiejrBo jiejr = JiejrglDao.getJiejrBo(JiejrBo.getYear());
		if(jiejr == null) 
		{
			JiejrglDao.saveJiejrBo(JiejrBo);
		}else{
			JiejrglDao.updateJiejrBo(JiejrBo);
		}
	}
	
	
	
	//StringToJiejrBo  1:�ڼ��� 0�������� 2�����²�������  3�����첻ѡ�� 
	public static  String toJiejrBo(String year,String month){
        StringBuffer monthString = new StringBuffer();
        String dataStirng  = year+"-"+month+"-01";
        DateModel day  = new DateModel(dataStirng);
        int monthCounts = DateTool.month(dataStirng);
		int week = day.getWeek();
		for(int i = 1; i<=38 ;i++)
		{
			if(i<week)
			{
				monthString.append(2);
				continue;
			}
			
			if(i>monthCounts+week-1)
			{
				monthString.append(2);
				continue;
			}
			
			if(i==6)
			{
				monthString.append(1);
				continue;
			}
			
			if(i==7)
			{
				monthString.append(1);
				continue;
			}
			
			if(i==13)
			{
				monthString.append(1);
				continue;
			}
			
			if(i==14)
			{
				monthString.append(1);
				continue;
			}
			
			if(i==20)
			{
				monthString.append(1);
				continue;
			}
			
			if(i==21)
			{
				monthString.append(1);
				continue;
			}
			
			if(i==27)
			{
				monthString.append(1);
				continue;
			}
			
			if(i==28)
			{
				monthString.append(1);
				continue;
			}
			
			if(i==34)
			{
				monthString.append(1);
				continue;
			}
			
			if(i==35)
			{
				monthString.append(1);
				continue;
			}
			monthString.append(0);
		}
		
		String rString="";
		
		//���㵱������
		Calendar cal = Calendar.getInstance();
		int year_=cal.get(Calendar.YEAR);//��ȡ�·� 
		int month_=cal.get(Calendar.MONTH);//��ȡ�·� 
		int day_=cal.get(Calendar.DATE);//��ȡ��
		if(String.valueOf(year_).equals(year) && (month_+1)==Integer.valueOf((month)))
		{	
			int t=0;
			for(int i=0 ;i<monthString.length();i++)
			{	
				if("1".equals(monthString.substring(i, i+1))||"0".equals(monthString.substring(i, i+1)))
				{
					t=i;break;
				}
			}
			
			rString = monthString.substring(0,t+day_-1)+"3"+monthString.substring(t+day_,monthString.length());
		}else{
			rString=monthString.toString();
		}
		return rString;
	}
	
	//�ַ���ת��
	private  String doMonthString(String monthString,String monthString_){
		
		String strTemp = "";
		boolean bool_1_2 = true; 
		int count_1_2 = 0; 
		boolean bool_2_2 = true; 
		int count_2_2 = 0; 
		for(int i = 0 ; i<monthString.length() ; i++ )
		{
			strTemp = monthString.substring(i,i+1);
			if("2".equals(strTemp)&&bool_1_2)
			{
				count_1_2++;
			}else{
				bool_1_2 = false;
				if("2".equals(strTemp)&&bool_2_2)
				{
					count_2_2++;
				}
			}
		}
		
		for(int i = 0 ; i<count_1_2 ; i++ )
		{
			monthString_ = "2"+monthString_;
		}
		
		for(int i = 0 ; i<count_2_2 ; i++ )
		{
			monthString_ = monthString_ + "2";
		}
		
		int index = monthString.indexOf("3");
		String rString="";
		if(index!=-1)
		{	
			if(monthString_.substring(index,index+1).equals("1"))
			{
				rString = monthString_;
			}else{
				rString = monthString_.substring(0,index)+"3"+monthString_.substring(index+1,monthString_.length());
			}
			
		}else{
			rString = monthString_;
		}
		return rString;
	}
}
