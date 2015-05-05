/**
 *<dl>
 *<dt><b>Yinjzhb:印鉴组合表实体类</b></dt>
 *<dd></dd>
 *</dl>
 *@copyright :Copyright @2011, IBM ETP. All right reserved.
 *【Update History】
 * Version  Date        Company      Developer       Revise
 * -------   ----------       ---------        -------------       ------------
 * 1.00	  2011-12-1       IBM ETP      LiuShan		    create
 */
package com.unitop.sysmgr.bo;

/**
 * @author LiuShan
 *
 */
public class Yinjzhb {
	
	private YinjzhId yinjzhid = new YinjzhId();
	//private String zuhgz;
	private String shancrq;
	private String zuhzt;
	private String zuhshzt;
	//V3.0新增
	private String tingyrq;
	
	/**
	 * 
	 */
	public Yinjzhb() {
		super();
	}

	/**
	 * @param yinjzhid
	 * @param zuhgz
	 * @param qiyrq
	 * @param shancrq
	 * @param zuhzt
	 * @param zuhshzt
	 */
	public Yinjzhb(YinjzhId yinjzhid, String zuhgz, String qiyrq,
			String shancrq, String zuhzt, String zuhshzt) {
		super();
		this.yinjzhid = yinjzhid;
		this.shancrq = shancrq;
		this.zuhzt = zuhzt;
		this.zuhshzt = zuhshzt;
	}
	
	public String getTingyrq() {
		return tingyrq;
	}
	

	public void setTingyrq(String tingyrq) {
		this.tingyrq = tingyrq;
	}

	/**
	 * @return the yinjzhid
	 */
	public YinjzhId getYinjzhid() {
		return yinjzhid;
	}


	/**
	 * @param yinjzhid the yinjzhid to set
	 */
	public void setYinjzhid(YinjzhId yinjzhid) {
		this.yinjzhid = yinjzhid;
	}

	/**
	 * @return the shancrq
	 */
	public String getShancrq() {
		return shancrq;
	}

	/**
	 * @param shancrq the shancrq to set
	 */
	public void setShancrq(String shancrq) {
		this.shancrq = shancrq;
	}

	/**
	 * @return the zuhzt
	 */
	public String getZuhzt() {
		return zuhzt;
	}

	/**
	 * @param zuhzt the zuhzt to set
	 */
	public void setZuhzt(String zuhzt) {
		this.zuhzt = zuhzt;
	}

	/**
	 * @return the zuhshzt
	 */
	public String getZuhshzt() {
		return zuhshzt;
	}

	/**
	 * @param zuhshzt the zuhshzt to set
	 */
	public void setZuhshzt(String zuhshzt) {
		this.zuhshzt = zuhshzt;
	}
	

}
