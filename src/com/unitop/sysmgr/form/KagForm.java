package com.unitop.sysmgr.form;

import org.apache.struts.action.ActionForm;

public class KagForm extends ActionForm {

	private String kagid;
	private String kagmc;
	private String kagip;
	private int cengs;
	private int chouts;
	private int choutkj;
	private int zongkj;
	private int shengykj;
	private int yiykj;
	private String kagzt;
	private String caozgy;
	private String jigh;
 
    
	
	public KagForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public KagForm(String kagid, String kagmc, String kagip, int cengs,
			int chouts, int choutkj, int zongkj, int shengykj, int yiykj,
			String kagzt, String caozgy, String jigh) {
		super();
		this.kagid = kagid;
		this.kagmc = kagmc;
		this.kagip = kagip;
		this.cengs = cengs;
		this.chouts = chouts;
		this.choutkj = choutkj;
		this.zongkj = zongkj;
		this.shengykj = shengykj;
		this.yiykj = yiykj;
		this.kagzt = kagzt;
		this.caozgy = caozgy;
		this.jigh = jigh;
	}


	public void clear() {
		this.kagid = "";
		this.kagmc = "";
		this.kagip = "";
		this.cengs = 1;
		this.chouts =1;
		this.choutkj = 1;
		this.zongkj = 1;
		this.shengykj = 1;
		this.yiykj = 1;
		this.kagzt = "";
		this.caozgy = "";
		this.jigh = "";
	}



	public String getKagid() {
		return kagid;
	}

	public void setKagid(String kagid) {
		this.kagid = kagid;
	}

	public String getKagmc() {
		return kagmc;
	}

	public void setKagmc(String kagmc) {
		this.kagmc = kagmc;
	}

	public String getKagip() {
		return kagip;
	}

	public void setKagip(String kagip) {
		this.kagip = kagip;
	}

	public int getCengs() {
		return cengs;
	}

	public void setCengs(int cengs) {
		this.cengs = cengs;
	}

	public int getChouts() {
		return chouts;
	}

	public void setChouts(int chouts) {
		this.chouts = chouts;
	}

	public int getChoutkj() {
		return choutkj;
	}

	public void setChoutkj(int choutkj) {
		this.choutkj = choutkj;
	}

	public int getZongkj() {
		return zongkj;
	}

	public void setZongkj(int zongkj) {
		this.zongkj = zongkj;
	}

	public int getShengykj() {
		return shengykj;
	}

	public void setShengykj(int shengykj) {
		this.shengykj = shengykj;
	}

	public int getYiykj() {
		return yiykj;
	}

	public void setYiykj(int yiykj) {
		this.yiykj = yiykj;
	}


	public String getKagzt() {
		return kagzt;
	}



	public void setKagzt(String kagzt) {
		this.kagzt = kagzt;
	}



	public String getCaozgy() {
		return caozgy;
	}

	public void setCaozgy(String caozgy) {
		this.caozgy = caozgy;
	}



	public String getJigh() {
		return jigh;
	}



	public void setJigh(String jigh) {
		this.jigh = jigh;
	}

	

}
