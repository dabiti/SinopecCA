package com.unitop.sysmgr.service;

import java.io.OutputStream;
import java.util.List;
import com.unitop.sysmgr.bo.Piaojyxwjb;
import com.unitop.sysmgr.bo.PiaojyxwjbId;
import com.unitop.sysmgr.bo.Yinjb;
import com.unitop.sysmgr.bo.Yinjk;

public interface AccountImageServcie {
	//��ȡ�ʻ���ӡ�����ļ���Ϣ
	public List getZhanghYjkList(String zhangh);
	//��ȡ�ʻ����ļ���Ϣ
	public List getBillImgList(String zhangh,String wenjbh);
	//��ȡ�ʻ������ļ���Ϣ
	public Piaojyxwjb getAccountImageInfo(PiaojyxwjbId id);
	//����Ʊ���ļ�Ӱ��
	public void downloadBillImage(PiaojyxwjbId id,OutputStream out);
	//����ӡ����Ӱ��
	public void downloadYinjkImage(String zhangh,String yinjkh,String billcm,OutputStream out);
	//�����˻����������ڻ�ȡӰ��-����ʹ��
	public List<Yinjk> getYinjkByQiyrq(String zhangh,String qiyrq);
	//�ļ�����-��Ӱ��ƽ̨����
	public void downloadFromYinxpt(String wenjsy,OutputStream out);
	//��ȡӡ��
	public Yinjb downloadYinjImage(String zhangh, String yinjbh, String qiyrq);
	public List<Yinjb> getZhanghYjList(String account);
}
