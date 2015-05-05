package com.unitop.sysmgr.bo;
import java.io.Serializable;

/*
 *by wp 
 */

public class PingzjgsybId implements Serializable{
	
	private String jigh;
	private String pingzlx;
	
	
	public void setJigh(String jigh) {
		this.jigh = jigh;
	}
	public String getJigh() {
		return jigh;
	}
	public void setPingzlx(String pingzlx) {
		this.pingzlx = pingzlx;
	}
	public String getPingzlx() {
		return pingzlx;
	}

}
