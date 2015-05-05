package com.unitop.sysmgr.bo;

/**
 * Zhanghb entity. @author MyEclipse Persistence Tools
 */

public class Zhanghb implements java.io.Serializable {

	// Fields

	private String zhangh;//帐号
	private String zhuzh;//主帐号
	private String zhangh_;//h获取印鉴信息帐号
	private String kehh;//客户号
	private String hum;//户名
	private String jigh;//机构号
	private String diz;//地址
	private String youzbm;//邮政编码
	private String kaihrq;//开户日期
//	private String qiyrq;
	private String tongctd;//通存通兑标志	
//	private String bingxq;
	private String huobh;//货币号
	private String youwyj;//有无印鉴
	private String youwzh;//有无组合
	private String yinjshzt;//印鉴审核状态
	private String zhanghshzt;//账户审核状态
	private String zuhshzt;//组合审核状态
	private String zhanghzt;//账户状态
	private String beiz;//备注
	
	private String yinjkbh;//印鉴卡编号
	private String yinjkbh2;
	private String yinjkbh3;
	private String yinjkbh4;
	
	//V3.0 增加
	private String fuyrq;//复用日期
	private String quxfyrq;//取消复用日期
	private String jiankbs;//建库标识
	private String tingyrq;//停用日期
	private String zhanghxz;//账户性质
	
	private String fuzr;//财务负责人
	private String fuzrdh;//负责人电话
	private String lianxr;//财务联系人
	private String dianh;//财务联系人电话
	
	private String tongdsz;
	
	private String fuzr2;//财务负责人2
	private String fuzrdh2;//负责人电话2
	private String lianxr2;//财务联系人2
	private String dianh2;//财务联系人电话2
	
	private String shifdh;//大额交易是否启用电核
	private String jiankgy;//建库柜员
	private String kaihgy;//开户柜员

	private String lianxrqh;
	private String lianxrqh2;
	private String lianxrfjh;
	private String lianxrfjh2;
	
	private String ischecksmall;//是否验小码
	
	public String getLianxrqh() {
		return lianxrqh;
	}

	public void setLianxrqh(String lianxrqh) {
		this.lianxrqh = lianxrqh;
	}

	public String getLianxrqh2() {
		return lianxrqh2;
	}

	public void setLianxrqh2(String lianxrqh2) {
		this.lianxrqh2 = lianxrqh2;
	}

	public String getLianxrfjh() {
		return lianxrfjh;
	}

	public void setLianxrfjh(String lianxrfjh) {
		this.lianxrfjh = lianxrfjh;
	}

	public String getLianxrfjh2() {
		return lianxrfjh2;
	}

	public void setLianxrfjh2(String lianxrfjh2) {
		this.lianxrfjh2 = lianxrfjh2;
	}

	public String getFuzrqh() {
		return fuzrqh;
	}

	public void setFuzrqh(String fuzrqh) {
		this.fuzrqh = fuzrqh;
	}

	public String getFuzrqh2() {
		return fuzrqh2;
	}

	public void setFuzrqh2(String fuzrqh2) {
		this.fuzrqh2 = fuzrqh2;
	}

	public String getFuzrfjh() {
		return fuzrfjh;
	}

	public void setFuzrfjh(String fuzrfjh) {
		this.fuzrfjh = fuzrfjh;
	}

	public String getFuzrfjh2() {
		return fuzrfjh2;
	}

	public void setFuzrfjh2(String fuzrfjh2) {
		this.fuzrfjh2 = fuzrfjh2;
	}

	private String fuzrqh;
	private String fuzrqh2;
	private String fuzrfjh;
	private String fuzrfjh2;
	
	
	
	public String getJiankgy() {
		return jiankgy;
	}

	public void setJiankgy(String jiankgy) {
		this.jiankgy = jiankgy;
	}

	public String getKaihgy() {
		return kaihgy;
	}

	public void setKaihgy(String kaihgy) {
		this.kaihgy = kaihgy;
	}

	public String getFuzr2() {
		return fuzr2;
	}

	public void setFuzr2(String fuzr2) {
		this.fuzr2 = fuzr2;
	}

	public String getFuzrdh2() {
		return fuzrdh2;
	}

	public void setFuzrdh2(String fuzrdh2) {
		this.fuzrdh2 = fuzrdh2;
	}

	public String getLianxr2() {
		return lianxr2;
	}

	public void setLianxr2(String lianxr2) {
		this.lianxr2 = lianxr2;
	}

	public String getDianh2() {
		return dianh2;
	}

	public void setDianh2(String dianh2) {
		this.dianh2 = dianh2;
	}

	public String getShifdh() {
		return shifdh;
	}

	public void setShifdh(String shifdh) {
		this.shifdh = shifdh;
	}

	public String getTongdsz() {
		return tongdsz;
	}

	public void setTongdsz(String tongdsz) {
		this.tongdsz = tongdsz;
	}

	public String getYinjkbh2() {
		return yinjkbh2;
	}

	public void setYinjkbh2(String yinjkbh2) {
		this.yinjkbh2 = yinjkbh2;
	}

	public String getYinjkbh3() {
		return yinjkbh3;
	}

	public void setYinjkbh3(String yinjkbh3) {
		this.yinjkbh3 = yinjkbh3;
	}

	public String getYinjkbh4() {
		return yinjkbh4;
	}

	public void setYinjkbh4(String yinjkbh4) {
		this.yinjkbh4 = yinjkbh4;
	}

	public String getFuzr() {
		return fuzr;
	}

	public void setFuzr(String fuzr) {
		this.fuzr = fuzr;
	}

	public String getFuzrdh() {
		return fuzrdh;
	}

	public void setFuzrdh(String fuzrdh) {
		this.fuzrdh = fuzrdh;
	}

	public String getFuyrq() {
		return fuyrq;
	}

	public void setFuyrq(String fuyrq) {
		this.fuyrq = fuyrq;
	}

	public String getQuxfyrq() {
		return quxfyrq;
	}

	public void setQuxfyrq(String quxfyrq) {
		this.quxfyrq = quxfyrq;
	}

	public String getJiankbs() {
		return jiankbs;
	}

	public void setJiankbs(String jiankbs) {
		this.jiankbs = jiankbs;
	}

	public String getTingyrq() {
		return tingyrq;
	}

	public void setTingyrq(String tingyrq) {
		this.tingyrq = tingyrq;
	}

	public String getZhangh_() {
		return zhangh_;
	}

	public void setZhangh_(String zhangh) {
		zhangh_ = zhangh;
	}
	
	public String getZhuzh() {
		return zhuzh;
	}

	public void setZhuzh(String zhuzh) {
		this.zhuzh = zhuzh;
	}
	public String getYinjkbh() {
		return yinjkbh;
	}

	public void setYinjkbh(String yinjkbh) {
		this.yinjkbh = yinjkbh;
	}

	/** default constructor */
	public Zhanghb() {
	}

	/** minimal constructor */
	public Zhanghb(String zhangh, String hum, String qiyrq, String youwyj,
			String youwzh, String yinjshzt, String zhanghshzt, String zuhshzt,
			String zhanghzt) {
		this.zhangh = zhangh;
		this.hum = hum;
//		this.qiyrq = qiyrq;
		this.youwyj = youwyj;
		this.youwzh = youwzh;
		this.yinjshzt = yinjshzt;
		this.zhanghshzt = zhanghshzt;
		this.zuhshzt = zuhshzt;
		this.zhanghzt = zhanghzt;
	}

	/** full constructor */
	

	//账户性质转换
	public static String zhanghxzConvert(String zhanghxz){
		if(zhanghxz!=null&&!"".equals(zhanghxz)){
			if("31010010".equals(zhanghxz)){
				return "基本户";
			}else if("31010110".equals(zhanghxz)){
				return "一般户";
			}else if("31010310".equals(zhanghxz)){
				return "临时户";
			}else if("31010210".equals(zhanghxz)){
				return "专用户";
			}else if("32020010".equals(zhanghxz)
					||"32030010".equals(zhanghxz)
					||"32050010".equals(zhanghxz)
					||"32160010".equals(zhanghxz)
					||"32170010".equals(zhanghxz)
					||"32190010".equals(zhanghxz)){
				return "定期户";
			}else if("31060010".equals(zhanghxz)
					||"31080010".equals(zhanghxz)){
				return "通知户";
			}else if("41010310".equals(zhanghxz)){
				return "个人高级结算户";
			}else{
				return "其它户";
			}
		}else{
			return "其它户";
		}
	}
	
	

	public Zhanghb(String zhangh, String zhuzh, String zhangh2, String kehh,
			String hum, String jigh, String diz, String youzbm, String kaihrq,
			String tongctd, String huobh, String youwyj, String youwzh,
			String yinjshzt, String zhanghshzt, String zuhshzt,
			String zhanghzt, String beiz, String yinjkbh, String yinjkbh2,
			String yinjkbh3, String yinjkbh4, String fuyrq, String quxfyrq,
			String jiankbs, String tingyrq, String zhanghxz, String fuzr,
			String fuzrdh, String lianxr, String dianh, String tongdsz,
			String fuzr2, String fuzrdh2, String lianxr2, String dianh2,
			String shifdh, String jiankgy, String kaihgy, String lianxrqh,
			String lianxrqh2, String lianxrfjh, String lianxrfjh2,
			String fuzrqh, String fuzrqh2, String fuzrfjh, String fuzrfjh2) {
		super();
		this.zhangh = zhangh;
		this.zhuzh = zhuzh;
		zhangh_ = zhangh2;
		this.kehh = kehh;
		this.hum = hum;
		this.jigh = jigh;
		this.diz = diz;
		this.youzbm = youzbm;
		this.kaihrq = kaihrq;
		this.tongctd = tongctd;
		this.huobh = huobh;
		this.youwyj = youwyj;
		this.youwzh = youwzh;
		this.yinjshzt = yinjshzt;
		this.zhanghshzt = zhanghshzt;
		this.zuhshzt = zuhshzt;
		this.zhanghzt = zhanghzt;
		this.beiz = beiz;
		this.yinjkbh = yinjkbh;
		this.yinjkbh2 = yinjkbh2;
		this.yinjkbh3 = yinjkbh3;
		this.yinjkbh4 = yinjkbh4;
		this.fuyrq = fuyrq;
		this.quxfyrq = quxfyrq;
		this.jiankbs = jiankbs;
		this.tingyrq = tingyrq;
		this.zhanghxz = zhanghxz;
		this.fuzr = fuzr;
		this.fuzrdh = fuzrdh;
		this.lianxr = lianxr;
		this.dianh = dianh;
		this.tongdsz = tongdsz;
		this.fuzr2 = fuzr2;
		this.fuzrdh2 = fuzrdh2;
		this.lianxr2 = lianxr2;
		this.dianh2 = dianh2;
		this.shifdh = shifdh;
		this.jiankgy = jiankgy;
		this.kaihgy = kaihgy;
		this.lianxrqh = lianxrqh;
		this.lianxrqh2 = lianxrqh2;
		this.lianxrfjh = lianxrfjh;
		this.lianxrfjh2 = lianxrfjh2;
		this.fuzrqh = fuzrqh;
		this.fuzrqh2 = fuzrqh2;
		this.fuzrfjh = fuzrfjh;
		this.fuzrfjh2 = fuzrfjh2;
	}

	// Property accessors

	public String getZhangh() {
		return this.zhangh;
	}

	
	public void setZhangh(String zhangh) {
		this.zhangh = zhangh;
	}

	public String getKehh() {
		return this.kehh;
	}

	public void setKehh(String kehh) {
		this.kehh = kehh;
	}

	public String getHum() {
		return this.hum;
	}

	public void setHum(String hum) {
		this.hum = hum;
	}

	public String getJigh() {
		return this.jigh;
	}

	public void setJigh(String jigh) {
		this.jigh = jigh;
	}

	public String getDiz() {
		return this.diz;
	}

	public void setDiz(String diz) {
		this.diz = diz;
	}

	public String getYouzbm() {
		return this.youzbm;
	}

	public void setYouzbm(String youzbm) {
		this.youzbm = youzbm;
	}

	public String getLianxr() {
		return this.lianxr;
	}

	public void setLianxr(String lianxr) {
		this.lianxr = lianxr;
	}

	public String getDianh() {
		return this.dianh;
	}

	public void setDianh(String dianh) {
		this.dianh = dianh;
	}

	public String getKaihrq() {
		return this.kaihrq;
	}

	public void setKaihrq(String kaihrq) {
		this.kaihrq = kaihrq;
	}

//	public String getQiyrq() {
//		return this.qiyrq;
//	}
//
//	public void setQiyrq(String qiyrq) {
//		this.qiyrq = qiyrq;
//	}

	public String getTongctd() {
		return this.tongctd;
	}

	public void setTongctd(String tongctd) {
		this.tongctd = tongctd;
	}

//	public String getBingxq() {
//		return this.bingxq;
//	}
//
//	public void setBingxq(String bingxq) {
//		this.bingxq = bingxq;
//	}

	public String getHuobh() {
		return this.huobh;
	}

	public void setHuobh(String huobh) {
		this.huobh = huobh;
	}

	public String getYouwyj() {
		return this.youwyj;
	}

	public void setYouwyj(String youwyj) {
		this.youwyj = youwyj;
	}

	public String getYouwzh() {
		return this.youwzh;
	}

	public void setYouwzh(String youwzh) {
		this.youwzh = youwzh;
	}

	public String getYinjshzt() {
		return this.yinjshzt;
	}

	public void setYinjshzt(String yinjshzt) {
		this.yinjshzt = yinjshzt;
	}

	public String getZhanghshzt() {
		return this.zhanghshzt;
	}

	public void setZhanghshzt(String zhanghshzt) {
		this.zhanghshzt = zhanghshzt;
	}

	public String getZuhshzt() {
		return this.zuhshzt;
	}

	public void setZuhshzt(String zuhshzt) {
		this.zuhshzt = zuhshzt;
	}

	public String getZhanghzt() {
		return this.zhanghzt;
	}

	public void setZhanghzt(String zhanghzt) {
		this.zhanghzt = zhanghzt;
	}

	public String getBeiz() {
		return this.beiz;
	}

	public void setBeiz(String beiz) {
		if(beiz!=null){
			beiz=beiz.replaceAll("\n", "");
			beiz=beiz.replaceAll("\r", "");
		}
		this.beiz = beiz;
	}

	public void setZhanghxz(String zhanghxz) {
		this.zhanghxz = zhanghxz;
	}

	public String getIschecksmall() {
		return ischecksmall;
	}

	public void setIschecksmall(String ischecksmall) {
		this.ischecksmall = ischecksmall;
	}

	public String getZhanghxz() {
		return zhanghxz;
	}


	
	
}