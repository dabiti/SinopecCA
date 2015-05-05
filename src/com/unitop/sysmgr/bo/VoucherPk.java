package com.unitop.sysmgr.bo;

import java.io.Serializable;

public class VoucherPk implements Serializable{
	
	private String pingzbs;
	
	private String netpointflag;
	
	public String getPingzbs() {
		return pingzbs;
	}
	
	public String getNetpointflag() {
		return netpointflag;
	}
	
	public void setNetpointflag(String netpointflag) {
		this.netpointflag = netpointflag;
	}
	
	public void setPingzbs(String pingzbs) {
		this.pingzbs = pingzbs;
	}
}
