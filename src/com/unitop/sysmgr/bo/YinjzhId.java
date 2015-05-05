/**
 *<dl>
 *<dt><b>YinjzhId:印鉴组合ID</b></dt>
 *<dd></dd>
 *</dl>
 *@copyright :Copyright @2011, IBM ETP. All right reserved.
 *【Update History】
 * Version  Date        Company      Developer       Revise
 * -------   ----------       ---------        -------------       ------------
 * 1.00	  2011-12-1       IBM ETP      LiuShan		    create
 */
package com.unitop.sysmgr.bo;

import java.io.Serializable;

/**
 * @author LiuShan
 *
 */
public class YinjzhId implements Serializable{
	
	private String zhangh;
	private String qiyrq;
	private double jinexx;
	private double jinesx;
	private String xitlx;
	private String zuhgz;//组合规则
	
	
	
	
	/**
	 * @return the zhangh
	 */
	public String getZhangh() {
		return zhangh;
	}
	/**
	 * @param zhangh the zhangh to set
	 */
	public void setZhangh(String zhangh) {
		this.zhangh = zhangh;
	}
	/**
	 * @return the jinexx
	 */
	public double getJinexx() {
		return jinexx;
	}
	/**
	 * @param jinexx the jinexx to set
	 */
	public void setJinexx(double jinexx) {
		this.jinexx = jinexx;
	}
	/**
	 * @return the jinesx
	 */
	public double getJinesx() {
		return jinesx;
	}
	/**
	 * @param jinesx the jinesx to set
	 */
	public void setJinesx(double jinesx) {
		this.jinesx = jinesx;
	}
	/**
	 * @return the xitlx
	 */
	public String getXitlx() {
		return xitlx;
	}
	/**
	 * @param xitlx the xitlx to set
	 */
	public void setXitlx(String xitlx) {
		this.xitlx = xitlx;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(jinesx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(jinexx);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((xitlx == null) ? 0 : xitlx.hashCode());
		result = prime * result + ((zhangh == null) ? 0 : zhangh.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		YinjzhId other = (YinjzhId) obj;
		if (Double.doubleToLongBits(jinesx) != Double
				.doubleToLongBits(other.jinesx))
			return false;
		if (Double.doubleToLongBits(jinexx) != Double
				.doubleToLongBits(other.jinexx))
			return false;
		if (xitlx == null) {
			if (other.xitlx != null)
				return false;
		} else if (!xitlx.equals(other.xitlx))
			return false;
		if (zhangh == null) {
			if (other.zhangh != null)
				return false;
		} else if (!zhangh.equals(other.zhangh))
			return false;
		return true;
	}
	public void setQiyrq(String qiyrq) {
		this.qiyrq = qiyrq;
	}
	public String getQiyrq() {
		return qiyrq;
	}
	public void setZuhgz(String zuhgz) {
		this.zuhgz = zuhgz;
	}
	public String getZuhgz() {
		return zuhgz;
	}

}
