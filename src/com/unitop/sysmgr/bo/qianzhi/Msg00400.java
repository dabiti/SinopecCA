package com.unitop.sysmgr.bo.qianzhi;

import java.util.Map;

import com.unitop.exception.BusinessException;
import com.unitop.framework.util.DateTool;
import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.util.ByteArrayUtil;
import com.unitop.util.MsgPraseUtil;

public class Msg00400 {
	private static final String nameStr="ActNo,CcyTyp,Status,AutoNo,AcTpDsc,AccTyp,IntTp,IntMth,InrRat,ActNm,CusNam,ComTel,CopTel,OpnActDt,LstMntDt,LstTrsrDt,LstInPyDt,IntEndDt,DfltS,ActBal,UncFnd,NPBDpAmt,PblInt,PsBkBal,NCntIntAmt,InflBal,PrIntAdj,ChqAlyNo,FixIntChq,IntTotAmt,LstOvLmtDt,NatWitInTx,FrgWitInTx,CrAmt,CrnVDbInt,AvCrAmt,CrnByAmt,LstMixRepAmt,CshAdv,CshDisInt,ByInt,SfCd,NewBrNo,StmtCycle,Cycle,Day,BkNo,NRefCd,NCDNIntCd,Mod,SpActFlg,PErrNo,SndDat,GLClCd,lstActTpMdfy,OdInt,ActBal,CrnAvlBal,RetChqNum,CnRtrnChqCnt,DfltS2,PsbkSt,Option,SlSt,DpBlNo,DpBlSt,DfltS3,AgrSgnDt,ComTD,BlacDt,PrInt,FxPrInt,AbOfsInd,SgnInd";
	private static final int[] nums=new int[]{20,3,7,4,30,4,4,4,7,60,60,12,12,10,10,10,10,10,10,19,19,19,19,19,19,19,19,2,8,19,10,19,19,
														17,19,17,19,19,19,19,19,5,4,1,2,2,2,4,12,3,1,2,10,25,10,19,17,18,10,10,1,2,1,1,7,1,
																	8,10,4,10,15,13,1,1};
	byte[]msg;
	Head head;
	public byte[] getMsg() {
		return msg;
	}
	public Head getHead() {
		return head;
	}
	public Msg00400() {
		
	}
	public Msg00400(byte [] msg) throws BusinessException {
		if(msg==null||msg.length<1397){
			throw new BusinessException("获取核心信息不正确");	
		}
		this.msg=msg;
		this.head = new Head(ByteArrayUtil.copyOfRange(msg, 0, 501));
	}
	public Zhanghb getZhanghb()throws BusinessException{
		if(msg==null||msg.length<1397){
			throw new BusinessException("获取核心信息不正确");
		}
		
		Zhanghb zhanghb=new Zhanghb();
		Map<String, String>msgMap=MsgPraseUtil.praseMsg(ByteArrayUtil.copyOfRange(msg, 501, msg.length), nums, nameStr);
		if(msgMap==null||msgMap.size()==0){
			return null;
		}
		zhanghb.setZhangh(msgMap.get("ActNo").substring(3));
		zhanghb.setHum(msgMap.get("ActNm").trim());
		String zhanghxzid=msgMap.get("AccTyp")+msgMap.get("IntTp");
		zhanghb.setZhanghxz(Zhanghb.zhanghxzConvert(zhanghxzid));
		//00:正常 01久悬 02销户 04待销    	07 结清 	09clos 	0A止付 	0B冻结
		String zhanghzt=msgMap.get("Status");
			zhanghzt=zhanghzt.indexOf("00")!=-1?"有效":zhanghzt.indexOf("01")!=-1?"久悬":zhanghzt.indexOf("02")!=-1||zhanghzt.indexOf("07")!=-1?"销户":"有效";
		zhanghb.setZhanghzt(zhanghzt);
		zhanghb.setJigh(msgMap.get("AutoNo"));
		String opendate=msgMap.get("OpnActDt");
		zhanghb.setKaihrq(DateTool.toSimpleFormatByddmmyyyy(opendate));
		if(zhanghb.getZhangh().equals("00000000000000000"))return null;
		return zhanghb;
	}
	
	
}
