package com.unitop.sysmgr.service;

import java.io.OutputStream;
import java.util.List;
import com.unitop.sysmgr.bo.Piaojyxwjb;
import com.unitop.sysmgr.bo.PiaojyxwjbId;
import com.unitop.sysmgr.bo.Yinjb;
import com.unitop.sysmgr.bo.Yinjk;

public interface AccountImageServcie {
	//获取帐户下印鉴卡文件信息
	public List getZhanghYjkList(String zhangh);
	//获取帐户下文件信息
	public List getBillImgList(String zhangh,String wenjbh);
	//获取帐户单个文件信息
	public Piaojyxwjb getAccountImageInfo(PiaojyxwjbId id);
	//下载票据文件影像
	public void downloadBillImage(PiaojyxwjbId id,OutputStream out);
	//下载印鉴卡影像
	public void downloadYinjkImage(String zhangh,String yinjkh,String billcm,OutputStream out);
	//依据账户与启用日期获取影像-卡柜使用
	public List<Yinjk> getYinjkByQiyrq(String zhangh,String qiyrq);
	//文件索引-从影像平台下载
	public void downloadFromYinxpt(String wenjsy,OutputStream out);
	//获取印鉴
	public Yinjb downloadYinjImage(String zhangh, String yinjbh, String qiyrq);
	public List<Yinjb> getZhanghYjList(String account);
}
