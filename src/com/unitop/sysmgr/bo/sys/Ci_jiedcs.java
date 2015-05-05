package com.unitop.sysmgr.bo.sys;

import java.io.Serializable;

public class Ci_jiedcs implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -390739031723022948L;
	private UnionJiedcs unionJiedcs;
	private String cansmc;
	private String canslx;
	private String cansfl;
	private String cansz;
	private String canssm;
	
	
	
	
	public String getCansmc() {
		return cansmc;
	}
	public void setCansmc(String cansmc) {
		this.cansmc = cansmc;
	}
	public String getCansfl() {
		return cansfl;
	}
	public void setCansfl(String cansfl) {
		this.cansfl = cansfl;
	}
	

	public UnionJiedcs getUnionJiedcs() {
		return unionJiedcs;
	}
	public void setUnionJiedcs(UnionJiedcs unionJiedcs) {
		this.unionJiedcs = unionJiedcs;
	}
	public String getCanslx() {
		return canslx;
	}
	public void setCanslx(String canslx) {
		this.canslx = canslx;
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
