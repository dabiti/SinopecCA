/**
 *<dl>
 *<dt><b>Yinjb:印鉴表实体类</b></dt>
 *<dd></dd>
 *</dl>
 *@copyright :Copyright @2011, IBM ETP. All right reserved.
 *【Update History】
 * Version  Date        Company      Developer       Revise
 * -------   ----------       ---------        -------------       ------------
 * 1.00	  2011-12-1       IBM ETP      LiuShan		    create
 */
package com.unitop.sysmgr.bo;

import java.sql.Blob;

/**
 * @author LiuShan
 *
 */
public class Yinjb {
	
	private YinjId yinjid = new YinjId();
	//private String jigh; V3.0取消jigh
	//private String qiyrq;
	private String shancrq;
	private String yinjlx;
	private String yinjzl;
	private String yinjys;
	private Blob yinjtp;
	private String yinjzt;
	private String yinjshzt;
	//private String xitlx; V3.0取消jigh
	private String yinjkbh;
	//V3.0新增
	private String tingyrq;
	private String yanyjb;
	private String yanyjg;
	
	
	private String zhangh;
	

	public String getZhangh() {
		return zhangh;
	}

	public void setZhangh(String zhangh) {
		this.zhangh = zhangh;
	}

	/**
	 * 
	 */
	public Yinjb() {
		super();
	}

	/**
	 * @param yinjid
	 * @param jigh
	 * @param qiyrq
	 * @param shancrq
	 * @param yinjlx
	 * @param yinjzl
	 * @param yinjys
	 * @param yinjtp
	 * @param yinjzt
	 * @param yinjshzt
	 * @param xitlx
	 * @param yinjkbh
	 */
	public Yinjb(YinjId yinjid, String qiyrq, String shancrq,
			String yinjlx, String yinjzl, String yinjys, Blob yinjtp,
			String yinjzt, String yinjshzt, String yinjkbh,String zhangh) {
		super();
		this.yinjid = yinjid;
		this.shancrq = shancrq;
		this.yinjlx = yinjlx;
		this.yinjzl = yinjzl;
		this.yinjys = yinjys;
		this.yinjtp = yinjtp;
		this.yinjzt = yinjzt;
		this.yinjshzt = yinjshzt;
		this.yinjkbh = yinjkbh;
		this.zhangh=zhangh;
	}
	
	public String getTingyrq() {
		return tingyrq;
	}

	public void setTingyrq(String tingyrq) {
		this.tingyrq = tingyrq;
	}

	/**
	 * @return the yinjzt
	 */
	public String getYinjzt() {
		return yinjzt;
	}

	/**
	 * @param yinjzt the yinjzt to set
	 */
	public void setYinjzt(String yinjzt) {
		this.yinjzt = yinjzt;
	}

	/**
	 * @return the yinjid
	 */
	public YinjId getYinjid() {
		return yinjid;
	}


	/**
	 * @param yinjid the yinjid to set
	 */
	public void setYinjid(YinjId yinjid) {
		this.yinjid = yinjid;
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
	 * @return the yinjlx
	 */
	public String getYinjlx() {
		return yinjlx;
	}

	/**
	 * @param yinjlx the yinjlx to set
	 */
	public void setYinjlx(String yinjlx) {
		this.yinjlx = yinjlx;
	}

	/**
	 * @return the yinjzl
	 */
	public String getYinjzl() {
		return yinjzl;
	}

	/**
	 * @param yinjzl the yinjzl to set
	 */
	public void setYinjzl(String yinjzl) {
		this.yinjzl = yinjzl;
	}

	/**
	 * @return the yinjys
	 */
	public String getYinjys() {
		return yinjys;
	}

	/**
	 * @param yinjys the yinjys to set
	 */
	public void setYinjys(String yinjys) {
		this.yinjys = yinjys;
	}

	/**
	 * @return the yinjtp
	 */
	public Blob getYinjtp() {
		return yinjtp;
	}


	/**
	 * @param yinjtp the yinjtp to set
	 */
	public void setYinjtp(Blob yinjtp) {
		this.yinjtp = yinjtp;
	}


	/**
	 * @return the yinjshzt
	 */
	public String getYinjshzt() {
		return yinjshzt;
	}


	/**
	 * @param yinjshzt the yinjshzt to set
	 */
	public void setYinjshzt(String yinjshzt) {
		this.yinjshzt = yinjshzt;
	}



	/**
	 * @return the yinjkbh
	 */
	public String getYinjkbh() {
		return yinjkbh;
	}


	/**
	 * @param yinjkbh the yinjkbh to set
	 */
	public void setYinjkbh(String yinjkbh) {
		this.yinjkbh = yinjkbh;
	}

	public void setYanyjb(String yanyjb) {
		this.yanyjb = yanyjb;
	}

	public String getYanyjb() {
		return yanyjb;
	}

	public void setYanyjg(String yanyjg) {
		this.yanyjg = yanyjg;
	}

	public String getYanyjg() {
		return yanyjg;
	}


}
