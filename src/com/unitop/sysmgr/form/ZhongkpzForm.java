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
	private String pingzlx="";//ƾ֤����
	private String qispzh_="";//��ʼƾ֤��     ������ƾ֤��������ʼƾ֤�ŵ�Ч��
	private String bens="";//����
	private String zhongzpzh_="";//��ֹƾ֤��
	private String rukrq="";//�������
	private String rukjg="";//������
	private String pingzh="";//ƾ֤��
	private String guiyh="";//������Ա�����ù�Ա���˻ع�Ա�����Ϲ�Ա��
	private String lingyjg="";//���û���
	private String jiglyfzr="";//�������ø�����
	private String lingymode="";//���÷�ʽ
	private String tuihmode="";//�˻ط�ʽ
	
	
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
