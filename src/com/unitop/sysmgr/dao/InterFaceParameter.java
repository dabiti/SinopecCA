/**
 *<dl>
 *<dt><b>InterFaceParameter:接口参数类</b></dt>
 *<dd></dd>
 *</dl>
 *@copyright :Copyright @2011, IBM ETP. All right reserved.
 *【Update History】
 * Version  Date        Company      Developer       Revise
 * -------   ----------       ---------        -------------       ------------
 * 1.00	  2011-11-16       IBM ETP      LiuShan		    create
 */
package com.unitop.sysmgr.dao;

import java.util.List;

import com.unitop.sysmgr.bo.sys.Ci_jiedcs;
import com.unitop.sysmgr.bo.sys.Ci_pingzcs;
import com.unitop.sysmgr.bo.sys.Ci_xitcs;
import com.unitop.sysmgr.bo.sys.Ci_xitjd;
import com.unitop.sysmgr.bo.sys.Ci_yewxt;
import com.unitop.sysmgr.bo.sys.PeizDpi;

/**
 * @author LiuShan
 *
 */
public interface InterFaceParameter   extends BaseDataResourcesInterface {
	
	//MakeDPI
	public List findAllDPI();
	public void deleteDPI(String id);
	public PeizDpi findDPIById(String id);
	public void updateDPI(PeizDpi peizDpi);
	public void addDPI(PeizDpi peizDpi);
	//xitjd
	public List findAllXitjd();
	public void deleteXitjd(String jiedbs);
	public Ci_xitjd findXitjd(String jiedbs);
	public void updateXitjd(Ci_xitjd xitjd);
	public void addXitjd(Ci_xitjd xitjd);
	
	//yewxt
	public List findAllYewxt();
	public void deleteYewxt(String xitbs);
	public Ci_yewxt findYewxt(String xitbs);
	public void updateYewxt(Ci_yewxt yewxt);
	public void addYewxt(Ci_yewxt yewxt);
	//jiedcs
	public List findAllJiedcs();
	public void deleteJiedcs(String jiedbs,String cansbs);
	public Ci_jiedcs findJiedcs(String jiedbs,String cansbs);
	public void updateJiedcs(Ci_jiedcs jiedcs);
	public void addJiedcs(Ci_jiedcs jiedcx);
	//pingzcs
	public List findAllPingzcs();
	public void deletePingzcs(String xitbs,String pingzbs);
	public Ci_pingzcs findPingzcs(String xitbs,String pingzbs);
	public void updatePingzcs(Ci_pingzcs pingzcs);
	public void addPingzcs(Ci_pingzcs pingzcs);
	//xitcs
	public List findAllXitcs();
	public void deleteXitcs(String xitbs,String cansbs);
	public Ci_xitcs findXitcs(String xitbs,String cansbs);
	public void updateXitcs(Ci_xitcs xitcs);
	public void addXitcs(Ci_xitcs xitcs);

}
