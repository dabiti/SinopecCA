package com.unitop.sysmgr.bo;

import java.io.Serializable;

public class YinjkId  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4063968982951375621L;
	private String yinjkh;
	private String zhangh;
	private String qiyrq;
	
	public String getYinjkh() {
		return yinjkh;
	}
	public void setYinjkh(String yinjkh) {
		this.yinjkh = yinjkh;
	}
	public String getZhangh() {
		return zhangh;
	}
	public void setZhangh(String zhangh) {
		this.zhangh = zhangh;
	}
	public void setQiyrq(String qiyrq) {
		this.qiyrq = qiyrq;
	}
	public String getQiyrq() {
		return qiyrq;
	}
	
}
