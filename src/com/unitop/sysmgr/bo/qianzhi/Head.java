package com.unitop.sysmgr.bo.qianzhi;

import java.io.UnsupportedEncodingException;

import com.unitop.exception.BusinessException;
import com.unitop.util.ByteArrayUtil;

public class Head {
	private byte[] headMsg; //报文头
	private String flwNo;//前端系统流水号
	private String oldFlwNo;//原服务前段流水号
	private String trsDt;//前端系统系统日期
	private String trsTm;//前端系统系统时间
	private String servicecode;//服务编码
	private String trsNo;//前端系统系统编码
	private String trsBrOrg;//交易分行机构吗
	private String trsOrgan;//交易机构码
	private String trancode;//交易代码
	private String replyCd;//响应代码
	private String replyText;//响应信息描述
	private String fldTech;//技术头保留域
	
	private String terNo;//终端编号
	private String tlrId;//操作员编号
	private String athlrId;//授权操作员编号
	private String cnlBusDt;//前端业务日期
	private String cnlBusTm;//前端业务时间
	private String srvrBusFlwNo;//后台系统业务流水号
	private String srvrBusDt;//后端业务日期
	private String pgNo;//页码
	private String pdEndFlag;//结束标记
	private String wrkStatNo;//工作站号
	private String outErrCd;//核心返回信息码
	private String flg1;//渠道请求
	private String flg4;//交易源系统标志
	private String fldBiz;//业务头保留域
	
	
	public byte[] getHeadMsg() {
		return headMsg;
	}
	public void setHeadMsg(byte[] headMsg) {
		this.headMsg = headMsg;
	}
	public String getFlwNo() {
		return flwNo;
	}
	public void setFlwNo(String flwNo) {
		this.flwNo = flwNo;
	}
	public String getOldFlwNo() {
		return oldFlwNo;
	}
	public void setOldFlwNo(String oldFlwNo) {
		this.oldFlwNo = oldFlwNo;
	}
	public String getTrsDt() {
		return trsDt;
	}
	public void setTrsDt(String trsDt) {
		this.trsDt = trsDt;
	}
	public String getTrsTm() {
		return trsTm;
	}
	public void setTrsTm(String trsTm) {
		this.trsTm = trsTm;
	}
	public String getServicecode() {
		return servicecode;
	}
	public void setServicecode(String servicecode) {
		this.servicecode = servicecode;
	}
	public String getTrsNo() {
		return trsNo;
	}
	public void setTrsNo(String trsNo) {
		this.trsNo = trsNo;
	}
	public String getTrsBrOrg() {
		return trsBrOrg;
	}
	public void setTrsBrOrg(String trsBrOrg) {
		this.trsBrOrg = trsBrOrg;
	}
	public String getTrsOrgan() {
		return trsOrgan;
	}
	public void setTrsOrgan(String trsOrgan) {
		this.trsOrgan = trsOrgan;
	}
	public String getTrancode() {
		return trancode;
	}
	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}
	public String getReplyCd() {
		return replyCd;
	}
	public void setReplyCd(String replyCd) {
		this.replyCd = replyCd;
	}
	public String getReplyText() {
		return replyText;
	}
	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}
	public String getFldTech() {
		return fldTech;
	}
	public void setFldTech(String fldTech) {
		this.fldTech = fldTech;
	}
	public String getTerNo() {
		return terNo;
	}
	public void setTerNo(String terNo) {
		this.terNo = terNo;
	}
	public String getTlrId() {
		return tlrId;
	}
	public void setTlrId(String tlrId) {
		this.tlrId = tlrId;
	}
	public String getAthlrId() {
		return athlrId;
	}
	public void setAthlrId(String athlrId) {
		this.athlrId = athlrId;
	}
	public String getCnlBusDt() {
		return cnlBusDt;
	}
	public void setCnlBusDt(String cnlBusDt) {
		this.cnlBusDt = cnlBusDt;
	}
	public String getCnlBusTm() {
		return cnlBusTm;
	}
	public void setCnlBusTm(String cnlBusTm) {
		this.cnlBusTm = cnlBusTm;
	}
	public String getSrvrBusFlwNo() {
		return srvrBusFlwNo;
	}
	public void setSrvrBusFlwNo(String srvrBusFlwNo) {
		this.srvrBusFlwNo = srvrBusFlwNo;
	}
	public String getSrvrBusDt() {
		return srvrBusDt;
	}
	public void setSrvrBusDt(String srvrBusDt) {
		this.srvrBusDt = srvrBusDt;
	}
	public String getPgNo() {
		return pgNo;
	}
	public void setPgNo(String pgNo) {
		this.pgNo = pgNo;
	}
	public String getPdEndFlag() {
		return pdEndFlag;
	}
	public void setPdEndFlag(String pdEndFlag) {
		this.pdEndFlag = pdEndFlag;
	}
	public String getWrkStatNo() {
		return wrkStatNo;
	}
	public void setWrkStatNo(String wrkStatNo) {
		this.wrkStatNo = wrkStatNo;
	}
	public String getOutErrCd() {
		return outErrCd;
	}
	public void setOutErrCd(String outErrCd) {
		this.outErrCd = outErrCd;
	}
	public String getFlg1() {
		return flg1;
	}
	public void setFlg1(String flg1) {
		this.flg1 = flg1;
	}
	public String getFlg4() {
		return flg4;
	}
	public void setFlg4(String flg4) {
		this.flg4 = flg4;
	}
	public String getFldBiz() {
		return fldBiz;
	}
	public void setFldBiz(String fldBiz) {
		this.fldBiz = fldBiz;
	}
	public Head() {

	}
	public Head(byte[] headMsg) throws BusinessException{
		this.headMsg=headMsg;
		analysisMsg(headMsg);
	}
	private void analysisMsg(byte[] headMsg) throws BusinessException {
		if(headMsg==null||headMsg.length!=501){
			return ;
		}
		this.flwNo=new String (ByteArrayUtil.copyOfRange(headMsg, 0	, 20));
		this.servicecode=new String (ByteArrayUtil.copyOfRange(headMsg, 57	, 70));
		this.trsNo=new String (ByteArrayUtil.copyOfRange(headMsg, 70, 74));
		this.trancode=new String (ByteArrayUtil.copyOfRange(headMsg, 90	, 102));
		this.replyCd=new String (ByteArrayUtil.copyOfRange(headMsg, 102	, 114));
		try {
			this.replyText=new String (ByteArrayUtil.copyOfRange(headMsg, 114, 242),"gbk");
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException("查询印鉴卡信息失败");
		}
		this.outErrCd=new String (ByteArrayUtil.copyOfRange(headMsg, 404	, 407));
		
	}
	
	
}
