package com.unitop.sysmgr.bo;

/**
 * ƾ֤��ϸ
 * @author Owner
 *
 */
public class Pingzmx {
	
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
	
	private String begindate = "";// ��ʼʱ��

	private String enddate = "";// ����ʱ��

	private String erwmMsg = "";//��ά�����ݱ���
	
	private String filename = "";


	public Pingzmx(String pingzh, String pich, String zhangh, String jigh,
			String hum, String pingzlx, String guiyh, String riq, String shij,
			String qispzh, String zhongzpzh, String bens, String zhangs,
			String zhuangt, String tum, String begindate, String enddate,
			String erwmMsg, String filename) {
		super();
		this.pingzh = pingzh;
		this.pich = pich;
		this.zhangh = zhangh;
		this.jigh = jigh;
		this.hum = hum;
		this.pingzlx = pingzlx;
		this.guiyh = guiyh;
		this.riq = riq;
		this.shij = shij;
		this.qispzh = qispzh;
		this.zhongzpzh = zhongzpzh;
		this.bens = bens;
		this.zhangs = zhangs;
		this.zhuangt = zhuangt;
		this.tum = tum;
		this.begindate = begindate;
		this.enddate = enddate;
		this.erwmMsg = erwmMsg;
		this.filename = filename;
	}



	public Pingzmx() {
	}

	
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = (filename==null?"":filename);
	}

	public String getErwmMsg() {
		return erwmMsg;
	}



	public void setErwmMsg(String erwmMsg) {
		this.erwmMsg = (erwmMsg==null?"":erwmMsg);
	}



	public String getBegindate() {
		return begindate;
	}



	public void setBegindate(String begindate) {
		this.begindate = (begindate==null?"":begindate);
	}



	public String getEnddate() {
		return enddate;
	}



	public void setEnddate(String enddate) {
		this.enddate = (enddate==null?"":enddate);
	}


	public String getPingzh() {
		return pingzh;
	}

	public void setPingzh(String pingzh) {
		this.pingzh = (pingzh==null?"":pingzh);
	}

	public String getQispzh() {
		return qispzh;
	}

	public void setQispzh(String qispzh) {
		this.qispzh = (qispzh==null?"":qispzh);
	}

	public String getZhongzpzh() {
		return zhongzpzh;
	}

	public void setZhongzpzh(String zhongzpzh) {
		this.zhongzpzh = (zhongzpzh==null?"":zhongzpzh);
	}

	public String getBens() {
		return bens;
	}

	public void setBens(String bens) {
		this.bens = (bens==null?"":bens);
	}

	public String getZhangs() {
		return zhangs;
	}

	public void setZhangs(String zhangs) {
		this.zhangs = (zhangs==null?"":zhangs);
	}


	public String getPich() {
		return pich;
	}

	public void setPich(String pich) {
		this.pich = (pich==null?"":pich);
	}

	public String getZhangh() {
		return zhangh;
	}

	public void setZhangh(String zhangh) {
		this.zhangh = (zhangh==null?"":zhangh);
	}

	public String getJigh() {
		return jigh;
	}

	public void setJigh(String jigh) {
		this.jigh = (jigh==null?"":jigh);
	}

	public String getHum() {
		return hum;
	}

	public void setHum(String hum) {
		this.hum = (hum==null?"":hum);
	}

	public String getPingzlx() {
		return pingzlx;
	}

	public void setPingzlx(String pingzlx) {
		this.pingzlx = (pingzlx==null?"":pingzlx);
	}

	public String getGuiyh() {
		return guiyh;
	}

	public void setGuiyh(String guiyh) {
		this.guiyh = (guiyh==null?"":guiyh);
	}

	public String getRiq() {
		return riq;
	}

	public void setRiq(String riq) {
		this.riq = (riq==null?"":riq);
	}

	public String getShij() {
		return shij;
	}

	public void setShij(String shij) {
		this.shij = (shij==null?"":shij);
	}

	public String getTum() {
		return tum;
	}

	public void setTum(String tum) {
		this.tum = (tum==null?"":tum);
	}



	public void setZhuangt(String zhuangt) {
		this.zhuangt = (zhuangt==null?"":zhuangt);
	}



	public String getZhuangt() {
		return zhuangt;
	}
	
	
	
	
}
