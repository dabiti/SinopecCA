package com.unitop.sysmgr.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class YinjkForm extends ActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3730035817730783426L;
	private String zhangh;
	private String yinjkh;
	private String jigh;
	private String kagid;
	private String ceng;
	private String choutwz;
	private String zhengmwjm;
	private String fanmwjm;
	private String qiyrq;
	private String shifzk;
	private String yewlx;
	
	
	private String num;
	private String endYinjkh;
	private String receiveOrgCode;
	private String receiveOrgName;
	private String lyClerkCode;
	private String hum;
	private String kehjl1;
	private String kehjl2;
	private String yinjkh2;
	private String yinjkh3;
	private String yinjkh4;
	private String oldyinjkh;
	private String oldyinjkh2;
	private String oldyinjkh3;
	private String oldyinjkh4;
	private String zhanghxz;
	private String phone;
	private String linkman;
	
	private String ffType;
	private String begindate;
	private String enddate;
	private String yinjkzt;
	private String guiyh;
	
	
	
	
	
	public String getBegindate() {
		return begindate;
	}
	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getYinjkzt() {
		return yinjkzt;
	}
	public void setYinjkzt(String yinjkzt) {
		this.yinjkzt = yinjkzt;
	}
	public String getGuiyh() {
		return guiyh;
	}
	public void setGuiyh(String guiyh) {
		this.guiyh = guiyh;
	}
	public String getOldyinjkh() {
		return oldyinjkh;
	}
	public void setOldyinjkh(String oldyinjkh) {
		this.oldyinjkh = oldyinjkh;
	}
	public String getOldyinjkh2() {
		return oldyinjkh2;
	}
	public void setOldyinjkh2(String oldyinjkh2) {
		this.oldyinjkh2 = oldyinjkh2;
	}
	public String getOldyinjkh3() {
		return oldyinjkh3;
	}
	public void setOldyinjkh3(String oldyinjkh3) {
		this.oldyinjkh3 = oldyinjkh3;
	}
	public String getOldyinjkh4() {
		return oldyinjkh4;
	}
	public void setOldyinjkh4(String oldyinjkh4) {
		this.oldyinjkh4 = oldyinjkh4;
	}
	public String getFfType() {
		return ffType;
	}
	public void setFfType(String ffType) {
		this.ffType = ffType;
	}
	public String getYinjkh2() {
		return yinjkh2;
	}
	public void setYinjkh2(String yinjkh2) {
		this.yinjkh2 = yinjkh2;
	}
	public String getYinjkh3() {
		return yinjkh3;
	}
	public void setYinjkh3(String yinjkh3) {
		this.yinjkh3 = yinjkh3;
	}
	public String getYinjkh4() {
		return yinjkh4;
	}
	public void setYinjkh4(String yinjkh4) {
		this.yinjkh4 = yinjkh4;
	}
	public String getZhanghxz() {
		return zhanghxz;
	}
	public void setZhanghxz(String zhanghxz) {
		this.zhanghxz = zhanghxz;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getHum() {
		return hum;
	}
	public void setHum(String hum) {
		this.hum = hum;
	}
	public String getKehjl1() {
		return kehjl1;
	}
	public void setKehjl1(String kehjl1) {
		this.kehjl1 = kehjl1;
	}
	public String getKehjl2() {
		return kehjl2;
	}
	public void setKehjl2(String kehjl2) {
		this.kehjl2 = kehjl2;
	}
	public String getLyClerkCode() {
		return lyClerkCode;
	}
	public void setLyClerkCode(String lyClerkCode) {
		this.lyClerkCode = lyClerkCode;
	}
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}
	public String getReceiveOrgName() {
		return receiveOrgName;
	}
	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}
	public String getEndYinjkh() {
		return endYinjkh;
	}
	public void setEndYinjkh(String endYinjkh) {
		this.endYinjkh = endYinjkh;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}

	//柜员所属柜员号
	private String netpointflag;
	//是否包含下级
	private String remark;
	
	public String getZhangh() {
		return zhangh;
	}
	public void setZhangh(String zhangh) {
		this.zhangh = zhangh;
	}
	public String getYinjkh() {
		return yinjkh;
	}
	public void setYinjkh(String yinjkh) {
		this.yinjkh = yinjkh;
	}
	public String getJigh() {
		return jigh;
	}
	public void setJigh(String jigh) {
		this.jigh = jigh;
	}
	public String getKagid() {
		return kagid;
	}
	public void setKagid(String kagid) {
		this.kagid = kagid;
	}
	public String getCeng() {
		return ceng;
	}
	public void setCeng(String ceng) {
		this.ceng = ceng;
	}
	public String getChoutwz() {
		return choutwz;
	}
	public void setChoutwz(String choutwz) {
		this.choutwz = choutwz;
	}
	
	public String getZhengmwjm() {
		return zhengmwjm;
	}
	public void setZhengmwjm(String zhengmwjm) {
		this.zhengmwjm = zhengmwjm;
	}
	public String getFanmwjm() {
		return fanmwjm;
	}
	public void setFanmwjm(String fanmwjm) {
		this.fanmwjm = fanmwjm;
	}
	public String getQiyrq() {
		return qiyrq;
	}
	public void setQiyrq(String qiyrq) {
		this.qiyrq = qiyrq;
	}
	public String getShifzk() {
		return shifzk;
	}
	public void setShifzk(String shifzk) {
		this.shifzk = shifzk;
	}
	public String getYewlx() {
		return yewlx;
	}
	public void setYewlx(String yewlx) {
		this.yewlx = yewlx;
	}
   
	public String getNetpointflag() {
		return netpointflag;
	}
	public void setNetpointflag(String netpointflag) {
		this.netpointflag = netpointflag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	public YinjkForm() {
		super();
	}
	
	
	
	public YinjkForm(String zhangh, String yinjkh, String jigh, String kagid,
			String ceng, String choutwz, String zhengmwjm, String fanmwjm,
			String qiyrq, String shifzk, String yewlx, String num,
			String endYinjkh, String receiveOrgCode, String receiveOrgName,
			String lyClerkCode, String hum, String kehjl1, String kehjl2,
			String yinjkh2, String yinjkh3, String yinjkh4, String oldyinjkh,
			String oldyinjkh2, String oldyinjkh3, String oldyinjkh4,
			String zhanghxz, String phone, String linkman, String ffType,
			String begindate, String enddate, String yinjkzt, String guiyh,
			String netpointflag, String remark) {
		super();
		this.zhangh = zhangh;
		this.yinjkh = yinjkh;
		this.jigh = jigh;
		this.kagid = kagid;
		this.ceng = ceng;
		this.choutwz = choutwz;
		this.zhengmwjm = zhengmwjm;
		this.fanmwjm = fanmwjm;
		this.qiyrq = qiyrq;
		this.shifzk = shifzk;
		this.yewlx = yewlx;
		this.num = num;
		this.endYinjkh = endYinjkh;
		this.receiveOrgCode = receiveOrgCode;
		this.receiveOrgName = receiveOrgName;
		this.lyClerkCode = lyClerkCode;
		this.hum = hum;
		this.kehjl1 = kehjl1;
		this.kehjl2 = kehjl2;
		this.yinjkh2 = yinjkh2;
		this.yinjkh3 = yinjkh3;
		this.yinjkh4 = yinjkh4;
		this.oldyinjkh = oldyinjkh;
		this.oldyinjkh2 = oldyinjkh2;
		this.oldyinjkh3 = oldyinjkh3;
		this.oldyinjkh4 = oldyinjkh4;
		this.zhanghxz = zhanghxz;
		this.phone = phone;
		this.linkman = linkman;
		this.ffType = ffType;
		this.begindate = begindate;
		this.enddate = enddate;
		this.yinjkzt = yinjkzt;
		this.guiyh = guiyh;
		this.netpointflag = netpointflag;
		this.remark = remark;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		
		super.reset(mapping, request);
		this.zhangh = null;
		this.yinjkh = null;
		this.jigh =  null;
		this.kagid =  null;
		this.ceng = null;
		this.choutwz = null;
		this.zhengmwjm = null;
		this.fanmwjm =  null;
		this.qiyrq =  null;
		this.shifzk = null;
		this.yewlx =  null;
		this.num= null;
		this.endYinjkh= null;
		this.receiveOrgCode= null;
		this.receiveOrgName= null;
		this.lyClerkCode= null;
		this.hum=null;
		this.kehjl1=null;
		this.kehjl2=null;
		this.yinjkh2=null;
		this.yinjkh3=null;
		this.yinjkh4=null;
		this.ffType=null;
		this.zhanghxz=null;
		this.linkman=null;
		this.phone=null;
		this.oldyinjkh=null;
		this.oldyinjkh2=null;
		this.oldyinjkh3=null;
		this.oldyinjkh4=null;
		this.guiyh=null;
		this.begindate=null;
		this.enddate=null;
		this.yinjkzt=null;
		
	}
	
	
	
	
}
