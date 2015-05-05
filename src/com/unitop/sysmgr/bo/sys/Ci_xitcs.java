package com.unitop.sysmgr.bo.sys;

import java.io.Serializable;

public class Ci_xitcs implements Serializable {

	private static final long serialVersionUID = -7453052353682014841L;
	
	private UnionXitcs unionXitcs;
	private String cansmc;
	private String canslx;
	private String cansfl;
	private String cansz;
	private String canssm;
	
	

	public UnionXitcs getUnionXitcs() {
		return unionXitcs;
	}
	public void setUnionXitcs(UnionXitcs unionXitcs) {
		this.unionXitcs = unionXitcs;
	}
	public String getCansmc() {
		return cansmc;
	}
	public void setCansmc(String cansmc) {
		this.cansmc = cansmc;
	}
	public String getCanslx() {
		return canslx;
	}
	public void setCanslx(String canslx) {
		this.canslx = canslx;
	}
	public String getCansfl() {
		return cansfl;
	}
	public void setCansfl(String cansfl) {
		this.cansfl = cansfl;
	}
	public String getCansz() {
		return cansz;
	}
	public void setCansz(String cansz) {
		this.cansz = cansz;
	}
	public String getCanssm() {
		return canssm;
	}
	public void setCanssm(String canssm) {
		this.canssm = canssm;
	}
	
	
}
