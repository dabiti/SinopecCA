package com.unitop.sysmgr.form;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.unitop.util.CoreBillUtils;

public class AccountinfoForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	private String account;
	private String accountname;
	private String accountstate;
	private String address;
	private String allexchange;
	private String billperiod;
	private String checkperiod;
	private Long creditgrade;
	private String englishname;//客户号
	private String enterprisecharacter;//企业性质
	private String incomerange;
	private String industrycharacter;//货币号
	private String linkman;
	private String netpointflag;//网点标识
	private String opendate;//启用时间段
	private String opendate1;//开户时间段
	private String phone;//电话
	private String postalcode;//邮编
	private Double registercapital;
	private String remark;//备注 是否包含下级
	private String startdate;//启用日期
	private String startdate1;//
	private Long timestampnum;
	private String yearcheckdate;
	private String sealstate;
	private String yinqbz;
	private String account2;
	private String orgcode;
	private String zhanghzt;
	private String ischecksmall;

	public String getFuyrq() {
		return fuyrq;
	}

	public void setFuyrq(String fuyrq) {
		this.fuyrq = fuyrq;
	}

	private String shenghjgh;
	private String oldaccount;

	private String include;
	private String rengwms;//任务描述
	private String fuyrq;
	private String zhanghxz;
	private String fuzr;//财务负责人
	private String fuzrdh;//负责人电话
	private String fuzr2;//财务负责人
	private String fuzrdh2;//负责人电话
	private String linkman2;//财务联系人2
	private String phone2;//财务联系人电话2
	private String shifdh;//大额交易是否电核
	private String yinjkbh;//印鉴卡编号
	private String yinjkbh2;//印鉴卡编号
	private String yinjkbh3;//印鉴卡编号
	private String yinjkbh4;//印鉴卡编号
	private String gongy;//户名显示框
	private String orgName;
	private String zhuzh;
	
	
	//核心信息
	private String accno;
	private String jiaoyw;
	private String zhangh;
	private String hum;
	private String kaihrq;
	private String beigyzh;
	
	private String lianxrqh;
	private String lianxrqh2;
	private String lianxrfjh;
	private String lianxrfjh2;
	
	private String fuzrqh;
	private String fuzrqh2;
	private String fuzrfjh;
	private String fuzrfjh2;
	private FormFile file;
	
	
	

	

	public String getZhanghzt() {
		return zhanghzt;
	}

	public void setZhanghzt(String zhanghzt) {
		this.zhanghzt = zhanghzt;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

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

	public String getZhuzh() {
		return zhuzh;
	}

	public void setZhuzh(String zhuzh) {
		this.zhuzh = zhuzh;
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

	public String getLinkman2() {
		return linkman2;
	}

	public void setLinkman2(String linkman2) {
		this.linkman2 = linkman2;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getShifdh() {
		return shifdh;
	}

	public void setShifdh(String shifdh) {
		this.shifdh = shifdh;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getJiaoyw() {
		return jiaoyw;
	}

	public void setJiaoyw(String jiaoyw) {
		this.jiaoyw = jiaoyw;
	}

	public String getZhangh() {
		return zhangh;
	}

	public void setZhangh(String zhangh) {
		this.zhangh = zhangh;
	}

	public String getHum() {
		return hum;
	}

	public void setHum(String hum) {
		this.hum = hum;
	}

	public String getKaihrq() {
		return kaihrq;
	}

	public void setKaihrq(String kaihrq) {
		this.kaihrq = kaihrq;
	}

	public String getBeigyzh() {
		return beigyzh;
	}

	public void setBeigyzh(String beigyzh) {
		this.beigyzh = beigyzh;
	}

	public String getAccountname2() {
		return gongy;
	}

	public void setAccountname2(String accountname2) {
		this.gongy = accountname2;
	}

	public AccountinfoForm() {
		super();
	}

	


	
	
	
	public AccountinfoForm(String account, String accountname,
			String accountstate, String address, String allexchange,
			String billperiod, String checkperiod, Long creditgrade,
			String englishname, String enterprisecharacter, String incomerange,
			String industrycharacter, String linkman, String netpointflag,
			String opendate, String opendate1, String phone, String postalcode,
			Double registercapital, String remark, String startdate,
			String startdate1, Long timestampnum, String yearcheckdate,
			String sealstate, String yinqbz, String account2, String orgcode,
			String zhanghzt, String shenghjgh, String oldaccount,
			String include, String rengwms, String fuyrq, String zhanghxz,
			String fuzr, String fuzrdh, String fuzr2, String fuzrdh2,
			String linkman2, String phone2, String shifdh, String yinjkbh,
			String yinjkbh2, String yinjkbh3, String yinjkbh4, String gongy,
			String orgName, String zhuzh, String accno, String jiaoyw,
			String zhangh, String hum, String kaihrq, String beigyzh,
			String lianxrqh, String lianxrqh2, String lianxrfjh,
			String lianxrfjh2, String fuzrqh, String fuzrqh2, String fuzrfjh,
			String fuzrfjh2, FormFile file) {
		super();
		this.account = account;
		this.accountname = accountname;
		this.accountstate = accountstate;
		this.address = address;
		this.allexchange = allexchange;
		this.billperiod = billperiod;
		this.checkperiod = checkperiod;
		this.creditgrade = creditgrade;
		this.englishname = englishname;
		this.enterprisecharacter = enterprisecharacter;
		this.incomerange = incomerange;
		this.industrycharacter = industrycharacter;
		this.linkman = linkman;
		this.netpointflag = netpointflag;
		this.opendate = opendate;
		this.opendate1 = opendate1;
		this.phone = phone;
		this.postalcode = postalcode;
		this.registercapital = registercapital;
		this.remark = remark;
		this.startdate = startdate;
		this.startdate1 = startdate1;
		this.timestampnum = timestampnum;
		this.yearcheckdate = yearcheckdate;
		this.sealstate = sealstate;
		this.yinqbz = yinqbz;
		this.account2 = account2;
		this.orgcode = orgcode;
		this.zhanghzt = zhanghzt;
		this.shenghjgh = shenghjgh;
		this.oldaccount = oldaccount;
		this.include = include;
		this.rengwms = rengwms;
		this.fuyrq = fuyrq;
		this.zhanghxz = zhanghxz;
		this.fuzr = fuzr;
		this.fuzrdh = fuzrdh;
		this.fuzr2 = fuzr2;
		this.fuzrdh2 = fuzrdh2;
		this.linkman2 = linkman2;
		this.phone2 = phone2;
		this.shifdh = shifdh;
		this.yinjkbh = yinjkbh;
		this.yinjkbh2 = yinjkbh2;
		this.yinjkbh3 = yinjkbh3;
		this.yinjkbh4 = yinjkbh4;
		this.gongy = gongy;
		this.orgName = orgName;
		this.zhuzh = zhuzh;
		this.accno = accno;
		this.jiaoyw = jiaoyw;
		this.zhangh = zhangh;
		this.hum = hum;
		this.kaihrq = kaihrq;
		this.beigyzh = beigyzh;
		this.lianxrqh = lianxrqh;
		this.lianxrqh2 = lianxrqh2;
		this.lianxrfjh = lianxrfjh;
		this.lianxrfjh2 = lianxrfjh2;
		this.fuzrqh = fuzrqh;
		this.fuzrqh2 = fuzrqh2;
		this.fuzrfjh = fuzrfjh;
		this.fuzrfjh2 = fuzrfjh2;
		this.file = file;
	}

	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		super.reset(arg0, arg1);
		this.account = null;
		this.accountname = null;
		this.accountstate = null;
		this.address = null;
		this.allexchange = null;
		this.billperiod = null;
		this.checkperiod = null;
		this.creditgrade = null;
		this.englishname = null;
		this.enterprisecharacter = null;
		this.incomerange = null;
		this.industrycharacter = null;
		this.linkman = null;
		this.netpointflag = null;
		this.opendate = null;
		this.opendate1 = null;
		this.phone = null;
		this.postalcode = null;
		this.registercapital = null;
		this.remark = null;
		this.startdate = null;
		this.startdate1 = null;
		this.timestampnum = null;
		this.yearcheckdate = null;
		this.sealstate = null;
		this.yinqbz = null;
		this.account2 = null;
		this.orgcode = null;
		this.zhanghzt = null;
		this.shenghjgh = null;
		this.oldaccount = null;
		this.include = "-1";
		this.rengwms = null;
		this.fuyrq = null;
		this.zhanghxz = null;
		this.fuzr = null;
		this.fuzrdh = null;
		this.fuzr2 = null;
		this.fuzrdh2 = null;
		this.linkman2 = null;
		this.phone2 = null;
		this.shifdh = null;
		this.yinjkbh = null;
		this.yinjkbh2 = null;
		this.yinjkbh3 = null;
		this.yinjkbh4 = null;
		this.gongy = null;
		this.orgName = null;
		this.zhuzh = null;
		this.accno = null;
		this.jiaoyw = null;
		this.zhangh = null;
		this.hum = null;
		this.kaihrq = null;
		this.beigyzh = null;
		this.lianxrqh = null;
		this.lianxrqh2 = null;
		this.lianxrfjh = null;
		this.lianxrfjh2 = null;
		this.fuzrqh = null;
		this.fuzrqh2 = null;
		this.fuzrfjh = null;
		this.fuzrfjh2 = null;
		this.file=null;
		this.ischecksmall=null;
	}
	
	
	
	public String getGongy() {
		return gongy;
	}

	public void setGongy(String gongy) {
		this.gongy = gongy;
	}

	public String getYinjkbh() {
		return yinjkbh;
	}

	public void setYinjkbh(String yinjkbh) {
		this.yinjkbh = yinjkbh;
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

	public String getZhanghxz() {
		return zhanghxz;
	}

	public void setZhanghxz(String zhanghxz) {
		this.zhanghxz = zhanghxz;
	}

	public String getRengwms() {
		return rengwms;
	}

	public void setRengwms(String rengwms) {
		this.rengwms = rengwms;
	}
	public String getOpendate1() {
		return opendate1;
	}

	public void setOpendate1(String opendate1) {
		this.opendate1 = opendate1;
	}

	public String getStartdate1() {
		return startdate1;
	}

	public void setStartdate1(String startdate1) {
		this.startdate1 = startdate1;
	}

	public String getInclude() {
		return include;
	}

	public void setInclude(String include) {
		this.include = include;
	}

	public String getOldaccount() {
		return oldaccount;
	}

	public void setOldaccount(String oldaccount) {
		this.oldaccount = oldaccount;
	}

	public String getShenghjgh() {
		return shenghjgh;
	}

	public void setShenghjgh(String shenghjgh) {
		this.shenghjgh = shenghjgh;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountname() {
		return this.accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getAccountstate() {
		return this.accountstate;
	}

	public void setAccountstate(String accountstate) {
		this.accountstate = accountstate;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAllexchange() {
		return this.allexchange;
	}

	public void setAllexchange(String allexchange) {
		this.allexchange = allexchange;
	}

	public String getBillperiod() {
		return this.billperiod;
	}

	public void setBillperiod(String billperiod) {
		this.billperiod = billperiod;
	}

	public String getCheckperiod() {
		return this.checkperiod;
	}

	public void setCheckperiod(String checkperiod) {
		this.checkperiod = checkperiod;
	}

	public Long getCreditgrade() {
		return this.creditgrade;
	}

	public void setCreditgrade(Long creditgrade) {
		this.creditgrade = creditgrade;
	}

	public String getEnglishname() {
		return this.englishname;
	}

	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}

	public String getEnterprisecharacter() {
		return this.enterprisecharacter;
	}

	public void setEnterprisecharacter(String enterprisecharacter) {
		this.enterprisecharacter = enterprisecharacter;
	}

	public String getIncomerange() {
		return this.incomerange;
	}

	public void setIncomerange(String incomerange) {
		this.incomerange = incomerange;
	}

	public String getIndustrycharacter() {
		return this.industrycharacter;
	}

	public void setIndustrycharacter(String industrycharacter) {
		this.industrycharacter = industrycharacter;
	}

	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getNetpointflag() {
		return this.netpointflag;
	}

	public void setNetpointflag(String netpointflag) {
		this.netpointflag = netpointflag;
	}

	public String getOpendate() {
		return this.opendate;
	}

	public void setOpendate(String opendate) {
		this.opendate = opendate;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostalcode() {
		return this.postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public Double getRegistercapital() {
		return this.registercapital;
	}

	public void setRegistercapital(Double registercapital) {
		this.registercapital = registercapital;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public Long getTimestampnum() {
		return this.timestampnum;
	}

	public void setTimestampnum(Long timestampnum) {
		this.timestampnum = timestampnum;
	}

	public String getYearcheckdate() {
		return this.yearcheckdate;
	}

	public void setYearcheckdate(String yearcheckdate) {
		this.yearcheckdate = yearcheckdate;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("account", getAccount())
				.toString();
	}

	public String getSealstate() {
		return sealstate;
	}

	public void setSealstate(String sealstate) {
		this.sealstate = sealstate;
	}

	public String getYinqbz() {
		return yinqbz;
	}

	public void setYinqbz(String yinqbz) {
		this.yinqbz = yinqbz;
	}

	public String getAccount2() {
		return account2;
	}

	public void setAccount2(String account2) {
		this.account2 = account2;
	}

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1) {
		ActionErrors errors = new ActionErrors();
		if (arg1.getServletPath().equals("/accountcustom.do")) {
			if ((opendate != null && opendate.trim().length() > 0)
					&& (opendate1 != null && opendate1.trim().length() > 0)
					&& (startdate != null && startdate.trim().length() > 0)
					&& (startdate1 != null && startdate1.trim().length() > 0)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date open = format.parse(opendate);
					Date open1 = format.parse(opendate1);
					Date start = format.parse(startdate);
					Date start1 = format.parse(startdate1);

					if (open1.compareTo(open) < 0 && !open.equals(open1))
						errors.add("error date", new ActionMessage(
								"errors.detail", "开户时间段的结束日期不能小于开始日期！"));
					if (start1.compareTo(start) < 0 && !start1.equals(start))
						errors.add("error date", new ActionMessage(
								"errors.detail", "启用时间段的结束日期不能小于开始日期！"));
					if (start.compareTo(open)<0&&!open.equals(start)){
						errors.add("error date", new ActionMessage(
								"errors.detail", "启用日期不能小于开户日期！"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return errors;
	}

	public String getIschecksmall() {
		return ischecksmall;
	}

	public void setIschecksmall(String ischecksmall) {
		this.ischecksmall = ischecksmall;
	}

}
