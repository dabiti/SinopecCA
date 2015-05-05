package com.unitop.sysmgr.bo.qianzhi;

import java.io.UnsupportedEncodingException;

import com.unitop.exception.BusinessException;
import com.unitop.util.ByteArrayUtil;

public class Head {
	private byte[] headMsg; //����ͷ
	private String flwNo;//ǰ��ϵͳ��ˮ��
	private String oldFlwNo;//ԭ����ǰ����ˮ��
	private String trsDt;//ǰ��ϵͳϵͳ����
	private String trsTm;//ǰ��ϵͳϵͳʱ��
	private String servicecode;//�������
	private String trsNo;//ǰ��ϵͳϵͳ����
	private String trsBrOrg;//���׷��л�����
	private String trsOrgan;//���׻�����
	private String trancode;//���״���
	private String replyCd;//��Ӧ����
	private String replyText;//��Ӧ��Ϣ����
	private String fldTech;//����ͷ������
	
	private String terNo;//�ն˱��
	private String tlrId;//����Ա���
	private String athlrId;//��Ȩ����Ա���
	private String cnlBusDt;//ǰ��ҵ������
	private String cnlBusTm;//ǰ��ҵ��ʱ��
	private String srvrBusFlwNo;//��̨ϵͳҵ����ˮ��
	private String srvrBusDt;//���ҵ������
	private String pgNo;//ҳ��
	private String pdEndFlag;//�������
	private String wrkStatNo;//����վ��
	private String outErrCd;//���ķ�����Ϣ��
	private String flg1;//��������
	private String flg4;//����Դϵͳ��־
	private String fldBiz;//ҵ��ͷ������
	
	
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
			throw new BusinessException("��ѯӡ������Ϣʧ��");
		}
		this.outErrCd=new String (ByteArrayUtil.copyOfRange(headMsg, 404	, 407));
		
	}
	
	
}
