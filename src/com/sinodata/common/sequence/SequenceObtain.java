package com.sinodata.common.sequence;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unitop.config.SystemConfig;
import com.unitop.sysmgr.dao.SystemControlParametersDao;
import com.unitop.sysmgr.dao.SystemDao;
/*
 * ��ˮ��������
 * by ldd hss
 */
@Service("sequenceObtain")
public class SequenceObtain extends Thread{
	
	@Resource
	private SystemDao systemDao;
	@Resource
	private SystemControlParametersDao scpd;	
	
	private static SequenceHread hread = null;
	private static SequenceYYYYMMDD yyyymmdd = null;
	private static SequenceNumber number =  null;
	public static SystemConfig config = SystemConfig.getInstance();//���systemconfig������ˮ�����ɻ���
	private static String weishu=(String)GetWeishu.getBcodeMesage("weishu");//��ÿ���λ��
	
	private static SequencePZH pzh =  null;

	public void init(){		
		if(hread == null) hread = new SequenceHread();
		if(yyyymmdd == null) yyyymmdd = new SequenceYYYYMMDD(systemDao);
		if(number == null) number = new SequenceNumber(scpd);
		String value = scpd.updateSequence("xit_sequence");		
		hread.baseNumber = String.valueOf(Integer.parseInt(value)-1);
		
		if(pzh == null) pzh = new SequencePZH(systemDao);
	}
	
	//��ȡΨһ��ˮ�Žӿ�
	public synchronized static String getSequenceUUID(){
		return hread.getHread(weishu)+yyyymmdd.getYYYYMMDD()+number.getNumber(weishu);
	}
	
	//���ƾ֤������
	public synchronized static String getPZHSequence(){
		String newpzh = pzh.getNewPZH();
		while (newpzh.length() < 10) {
			newpzh = "0" + newpzh;
		}
		return newpzh;
	}
	
}
