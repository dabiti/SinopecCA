package com.unitop.sysmgr.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.unitop.framework.util.DateTool;
public class PingzForm extends ActionForm{
	
	private static final long serialVersionUID = -6866248270158011690L;

	private String pingzh="";//ƾ֤��
	
	private String pich = "";//���κ�
	
	private String zhangh = "";//�˺�
	
	private String jigh ="";//������
	
	private String hum = "";//����
	
	private String pingzlx = "";//ƾ֤����
	
	private String guiyh = "";//��Ա��
	
	private String riq = "";//����

	private String shij = "";//ʱ��
	
	private String qispzh="";//��ʼƾ֤��
	
	private String zhongzpzh="";//��ֹƾ֤��
	
	private String bens = "";//��ӡ����
	
	private String zhangs = "";//��ӡ����
	
	private String zhuangt = "";//״̬
	
	private String tum = "";//��ά��ͼ

	private String begindate = "";//��ʼ����
	
	private String enddate = "";//��ֹ����
	
	private String erwmMsg = "";//��ά�����ݱ���
	
	private String filename = "";//�ļ���
	
	private String paix = "";//���� --20120516 by ldd ����
	
	
	@Override
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		super.reset(arg0, arg1);
		if(begindate == "")
		{
			begindate = DateTool.getNowDayForYYYMMDD().substring(0,7)+"-01";
		}
		if(enddate == "")
		{
			enddate = DateTool.getNowDayForYYYMMDD();
		}
	}

	public String getPaix() {
		return paix;
	}


	public void setPaix(String paix) {
		this.paix = paix;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public void clear() {
		this.pingzh = "";
		this.pich = "";
		this.zhangh = "";
		this.jigh = "";
		this.hum = "";
		this.pingzlx = "";
		this.guiyh = "";
		this.riq = "";
		this.shij = "";
		this.qispzh = "";
		this.zhongzpzh = "";
		this.bens = "";
		this.zhangs = "";
		this.zhuangt = "";
		this.tum = "";
		this.begindate = "";
		this.enddate = "";
		this.erwmMsg = "";
		this.filename="";
	}


	public PingzForm() {
	}





	public String getErwmMsg() {
		return erwmMsg;
	}

	public void setErwmMsg(String erwmMsg) {
		this.erwmMsg = erwmMsg;
	}





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





	public String getPingzh() {
		return pingzh;
	}



	public void setPingzh(String pingzh) {
		this.pingzh = pingzh;
	}



	public String getQispzh() {
		return qispzh;
	}

	public void setQispzh(String qispzh) {
		this.qispzh = qispzh;
	}

	public String getZhongzpzh() {
		return zhongzpzh;
	}

	public void setZhongzpzh(String zhongzpzh) {
		this.zhongzpzh = zhongzpzh;
	}

	public String getBens() {
		return bens;
	}

	public void setBens(String bens) {
		this.bens = bens;
	}

	public String getZhangs() {
		return zhangs;
	}

	public void setZhangs(String zhangs) {
		this.zhangs = zhangs;
	}


	public String getPich() {
		return pich;
	}

	public void setPich(String pich) {
		this.pich = pich;
	}

	public String getZhangh() {
		return zhangh;
	}

	public void setZhangh(String zhangh) {
		this.zhangh = zhangh;
	}

	public String getJigh() {
		return jigh;
	}

	public void setJigh(String jigh) {
		this.jigh = jigh;
	}

	public String getHum() {
		return hum;
	}

	public void setHum(String hum) {
		this.hum = hum;
	}

	public String getPingzlx() {
		return pingzlx;
	}

	public void setPingzlx(String pingzlx) {
		this.pingzlx = pingzlx;
	}

	public String getGuiyh() {
		return guiyh;
	}

	public void setGuiyh(String guiyh) {
		this.guiyh = guiyh;
	}

	public String getRiq() {
		return riq;
	}

	public void setRiq(String riq) {
		this.riq = riq;
	}

	public String getShij() {
		return shij;
	}

	public void setShij(String shij) {
		this.shij = shij;
	}

	public String getTum() {
		return tum;
	}

	public void setTum(String tum) {
		this.tum = tum;
	}



	public void setZhuangt(String zhuangt) {
		this.zhuangt = zhuangt;
	}


	public String getZhuangt() {
		return zhuangt;
	}
	

}
