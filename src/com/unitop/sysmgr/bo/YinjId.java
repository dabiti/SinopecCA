/**
 *<dl>
 *<dt><b>类机能概要:</b></dt>
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
public class YinjId implements Serializable{
	
	private String zhangh;
	private String qiyrq;
	private String yinjbh;
	//private String yinjbgh; 印鉴变更号  V3.0取消
	
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
	 * @return the yinjbh
	 */
	public String getYinjbh() {
		return yinjbh;
	}
	/**
	 * @param yinjbh the yinjbh to set
	 */
	public void setYinjbh(String yinjbh) {
		this.yinjbh = yinjbh;
	}
	/**
	 * @return the yinjbgh
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((yinjbh == null) ? 0 : yinjbh.hashCode());
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
		YinjId other = (YinjId) obj;
		if (yinjbh == null) {
			if (other.yinjbh != null)
				return false;
		} else if (!yinjbh.equals(other.yinjbh))
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

}
