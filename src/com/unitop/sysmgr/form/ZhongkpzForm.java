package com.unitop.sysmgr.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.unitop.framework.util.DateTool;

/*
 * by wp
 */


public class ZhongkpzForm extends ActionForm {
	
	private static final long serialVersionUID = -6866248270158011690L;
	private String pingzlx="";//凭证类型
	private String qispzh_="";//起始凭证号     区别于凭证出售中起始凭证号的效验
	private String bens="";//本数
	private String zhongzpzh_="";//终止凭证号
	private String rukrq="";//入库日期
	private String rukjg="";//入库机构
	private String pingzh="";//凭证号
	private String guiyh="";//操作柜员（领用柜员、退回柜员、作废柜员）
	private String lingyjg="";//领用机构
	private String jiglyfzr="";//机构领用负责人
	private String lingymode="";//领用方式
	private String tuihmode="";//退回方式
	
	
	public void setPingzh(String pingzh) {
		this.pingzh = pingzh;
	}
	public String getPingzh() {
		return pingzh;
	}
	public void setPingzlx(String pingzlx) {
		this.pingzlx = pingzlx;
	}
	public String getPingzlx() {
		return pingzlx;
	}
	public void setRukrq(String rukrq) {
		this.rukrq = rukrq;
	}
	public String getRukrq() {
		return rukrq;
	}
	public void setRukjg(String rukjg) {
		this.rukjg = rukjg;
	}
	public String getRukjg() {
		return rukjg;
	}
	public void setGuiyh(String guiyh) {
		this.guiyh = guiyh;
	}
	public String getGuiyh() {
		return guiyh;
	}
	public void setLingyjg(String lingyjg) {
		this.lingyjg = lingyjg;
	}
	public String getLingyjg() {
		return lingyjg;
	}
	public void setJiglyfzr(String jiglyfzr) {
		this.jiglyfzr = jiglyfzr;
	}
	public String getJiglyfzr() {
		return jiglyfzr;
	}
	
	public void setBens(String bens) {
		this.bens = bens;
	}
	public String getBens() {
		return bens;
	}
	public void setZhongzpzh_(String zhongzpzh_) {
		this.zhongzpzh_ = zhongzpzh_;
	}
	public String getZhongzpzh_() {
		return zhongzpzh_;
	}
	public void setQispzh_(String qispzh_) {
		this.qispzh_ = qispzh_;
	}
	public String getQispzh_() {
		return qispzh_;
	}
	public void setLingymode(String lingymode) {
		this.lingymode = lingymode;
	}
	public String getLingymode() {
		return lingymode;
	}
	public void setTuihmode(String tuihmode) {
		this.tuihmode = tuihmode;
	}
	public String getTuihmode() {
		return tuihmode;
	}
	
	

	
	


	


}
