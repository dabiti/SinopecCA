/**
 *<dl>
 *<dt><b>类机能概要:</b></dt>
 *<dd></dd>
 *</dl>
 *@copyright :Copyright @2011, IBM ETP. All right reserved.
 *【Update History】
 * Version  Date        Company      Developer       Revise
 * -------   ----------       ---------        -------------       ------------
 * 1.00	  2011-11-16       IBM ETP      LiuShan		    create
 */
package com.unitop.sysmgr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.unitop.sysmgr.bo.sys.Ci_jiedcs;
import com.unitop.sysmgr.bo.sys.Ci_pingzcs;
import com.unitop.sysmgr.bo.sys.Ci_xitcs;
import com.unitop.sysmgr.bo.sys.Ci_xitjd;
import com.unitop.sysmgr.bo.sys.Ci_yewxt;
import com.unitop.sysmgr.bo.sys.PeizDpi;
import com.unitop.sysmgr.dao.InterFaceParameter;

/**
 * @author LiuShan
 *
 */
@Repository("InterFaceParameterImpl")
public class InterFaceParameterImpl extends BaseDataResources implements InterFaceParameter {

	
	
	//dpi
	public void addDPI(PeizDpi peizDpi) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(peizDpi);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	public void deleteDPI(String id) {
		String hql = "delete from PeizDpi where dpiid =?";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery(hql);
			query.setString(0, id);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		
	}
	public List findAllDPI() {
		String hql = "from PeizDpi";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = new ArrayList();
		try{
			list =session.createQuery(hql).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	public PeizDpi findDPIById(String dpiid) {
		return (PeizDpi) super.getBaseHibernateDao().getHibernateSession().get(
				PeizDpi.class, dpiid);
	}
	public void updateDPI(PeizDpi peizDpi) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.update(peizDpi);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	
	//xitjd
	public void addXitjd(Ci_xitjd xitjd) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(xitjd);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	
	public void deleteXitjd(String jiedbs) {
		String hql = "delete from Ci_xitjd where jiedbs =?";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery(hql);
			query.setString(0, jiedbs);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		
	}
	public List findAllXitjd() {
		String hql = "from Ci_xitjd";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = new ArrayList();
		try{
			list =session.createQuery(hql).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	public Ci_xitjd findXitjd(String jiedbs) {
		Session session= super.getBaseHibernateDao().getHibernateSession();
		Ci_xitjd c=null;
		try{
		c=(Ci_xitjd)session.get(Ci_xitjd.class,jiedbs);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return c;
	}
	public void updateXitjd(Ci_xitjd xitjd) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.update(xitjd);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	//yewxt
	public void addYewxt(Ci_yewxt yewxt) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(yewxt);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	public void deleteYewxt(String xitbs) {
		String hql = "delete from Ci_yewxt where xitbs =?";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery(hql);
			query.setString(0, xitbs);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		
	}
	public List findAllYewxt() {
		String hql = "from Ci_yewxt";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = new ArrayList();
		try{
			list =session.createQuery(hql).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	public Ci_yewxt findYewxt(String xitbs) {
		return (Ci_yewxt) super.getBaseHibernateDao().getHibernateSession().get(
				Ci_yewxt.class,xitbs);
	}
	public void updateYewxt(Ci_yewxt yewxt) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.update(yewxt);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	
	//jiedcs
	public void addJiedcs(Ci_jiedcs jiedcx) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(jiedcx);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	
	public void deleteJiedcs(String jiedbs, String cansbs) {
		String hql = "delete from Ci_jiedcs where jiedbs =? and cansbs = ?";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery(hql);
			query.setString(0, jiedbs);
			query.setString(1, cansbs);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	public Ci_jiedcs findJiedcs(String jiedbs, String cansbs) {
		Ci_jiedcs ci_jiedcs = new Ci_jiedcs();
		String hql = "from Ci_jiedcs where jiedbs =? and cansbs = ?";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery(hql);
			query.setString(0, jiedbs);
			query.setString(1, cansbs);
			Object obj = query.uniqueResult();
			ci_jiedcs = (Ci_jiedcs)obj;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return ci_jiedcs;
	}
	public List findAllJiedcs() {
		String hql = "from Ci_jiedcs";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = new ArrayList();
		try{
			list =session.createQuery(hql).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}

	public void updateJiedcs(Ci_jiedcs jiedcs) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.update(jiedcs);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	//pingzcs
	public void addPingzcs(Ci_pingzcs pingzcs) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(pingzcs);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	public void deletePingzcs(String xitbs, String pingzbs) {
		String hql = "delete from Ci_pingzcs where xitbs =? and pingzbs = ?";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery(hql);
			query.setString(0, xitbs);
			query.setString(1, pingzbs);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	public List findAllPingzcs() {
		String hql = "from Ci_pingzcs";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = new ArrayList();
		try{
			list =session.createQuery(hql).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	
	public void updatePingzcs(Ci_pingzcs pingzcs) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.update(pingzcs);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	public  Ci_pingzcs findPingzcs(String xitbs, String pingzbs) {
		Ci_pingzcs pingzcs = new Ci_pingzcs();
		String hql = "from Ci_pingzcs where xitbs =? and pingzbs = ?";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery(hql);
			query.setString(0, xitbs);
			query.setString(1, pingzbs);
			Object obj = query.uniqueResult();
			pingzcs = (Ci_pingzcs)obj;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return pingzcs;
	}
	//xitcs
	public void addXitcs(Ci_xitcs xitcs) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.save(xitcs);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	public void deleteXitcs(String xitbs, String cansbs) {
		String hql = "delete from Ci_xitcs where xitbs =? and cansbs = ?";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery(hql);
			query.setString(0, xitbs);
			query.setString(1, cansbs);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}
	public List findAllXitcs() {
		String hql = "from Ci_xitcs";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		List list = new ArrayList();
		try{
			list =session.createQuery(hql).list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return list;
	}
	public Ci_xitcs findXitcs(String xitbs, String cansbs) {
		Ci_xitcs xitcs = new Ci_xitcs();
		String hql = "from Ci_xitcs where xitbs =? and cansbs = ?";
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try{
			Query query = session.createQuery(hql);
			query.setString(0, xitbs);
			query.setString(1, cansbs);
			Object obj = query.uniqueResult();
			xitcs = (Ci_xitcs)obj;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
		return xitcs;
	}
	public void updateXitcs(Ci_xitcs xitcs) {
		Session session = super.getBaseHibernateDao().getHibernateSession();
		try {
			session.update(xitcs);
			session.flush();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			super.getBaseHibernateDao().closeSession(session);
		}
	}


}
