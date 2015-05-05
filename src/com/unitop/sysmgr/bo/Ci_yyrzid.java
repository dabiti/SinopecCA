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
 * @author Wcl
 *
 */
public class Ci_yyrzid implements Serializable{
	
	private String renwbs;
	private String yinjbh;
	private String xitbs;
	private String zhangh;
	
	public String getRenwbs() {
		return renwbs;
	}
	public void setRenwbs(String renwbs) {
		this.renwbs = renwbs;
	}
	public String getYinjbh() {
		return yinjbh;
	}
	public void setYinjbh(String yinjbh) {
		this.yinjbh = yinjbh;
	}
	public String getXitbs() {
		return xitbs;
	}
	public void setXitbs(String xitbs) {
		this.xitbs = xitbs;
	}
	public String getZhangh() {
		return zhangh;
	}
	public void setZhangh(String zhangh) {
		this.zhangh = zhangh;
	}
	
}
