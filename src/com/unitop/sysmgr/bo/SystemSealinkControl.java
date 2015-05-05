package com.unitop.sysmgr.bo;

public class SystemSealinkControl  implements java.io.Serializable{
	
	private String organnum;
	private String min;
	private String max;


	public SystemSealinkControl(String code,String min, String max) {
		super();
		this.organnum = code;
		this.min = min;
		this.max = max;
	}
	public SystemSealinkControl() {
		super();
	}
	
	public String getOrgannum() {
		return organnum;
	}
	public void setOrgannum(String organnum) {
		this.organnum = organnum;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}


}
