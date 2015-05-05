package com.sinodata.common.sequence;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unitop.config.SystemConfig;
import com.unitop.sysmgr.dao.SystemControlParametersDao;
import com.unitop.sysmgr.dao.SystemDao;
/*
 * 流水号生成器
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
	public static SystemConfig config = SystemConfig.getInstance();//获得systemconfig加载流水号生成基数
	private static String weishu=(String)GetWeishu.getBcodeMesage("weishu");//获得控制位数
	
	private static SequencePZH pzh =  null;

	public void init(){		
		if(hread == null) hread = new SequenceHread();
		if(yyyymmdd == null) yyyymmdd = new SequenceYYYYMMDD(systemDao);
		if(number == null) number = new SequenceNumber(scpd);
		String value = scpd.updateSequence("xit_sequence");		
		hread.baseNumber = String.valueOf(Integer.parseInt(value)-1);
		
		if(pzh == null) pzh = new SequencePZH(systemDao);
	}
	
	//获取唯一流水号接口
	public synchronized static String getSequenceUUID(){
		return hread.getHread(weishu)+yyyymmdd.getYYYYMMDD()+number.getNumber(weishu);
	}
	
	//获得凭证号序列
	public synchronized static String getPZHSequence(){
		String newpzh = pzh.getNewPZH();
		while (newpzh.length() < 10) {
			newpzh = "0" + newpzh;
		}
		return newpzh;
	}
	
}
