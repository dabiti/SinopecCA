package com.unitop.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import com.unitop.sysmgr.bo.Zhanghb;
import com.unitop.util.MsgPraseUtil;

public class Text612 {
//	public static void main(String[] args) {
//		List<Zhanghb> zhanghbList=new ArrayList<Zhanghb>();
//		for (int i = 0; i < 4; i++) {
//			Zhanghb zhanghb =new Zhanghb();
//			zhanghb.setZhangh("2014061211111111"+i);
//			zhanghb.setHum("уе"+i);
//			zhanghb.setKaihgy("2014-05-06");
//			zhanghbList.add(zhanghb);
//		}
//		
//		JSONArray jsongArray=JSONArray.fromObject(zhanghbList);
//		String json=jsongArray.toString();
//		List<Zhanghb> list=JSONArray.toList(JSONArray.fromObject(json),Zhanghb.class);
//		for (Zhanghb zhanghb : list) {
//			System.out.println(zhanghb.getZhangh()+"     "+zhanghb.getHum());
//		}
//		
//		
//	}
	private static final int[] numss=new int[]{20,3,7,4,30,4,4,4,7,60,60,12,12,10,10,10,10,10,10,19,19,19,19,19,19,19,19,2,8,19,10,19,19,
		17,19,17,19,19,19,19,19,5,4,1,2,2,2,4,12,3,1,2,10,25,10,19,17,18,10,10,1,2,1,1,7,1,
					8,10,4,10,15,13,1,1};
	
	public static void main(String[] args) {
		String msg="122333444455555666666777777788888888999999999";
		int[]nums=new int[]{1,2,3,4,5,6,7,8,9};
		String names="1,2,3,4,5,6,7,8,9";
		Map<String, String> map=MsgPraseUtil.praseMsg(msg.getBytes(), nums, names);
		
		for (int i : nums) {
			System.out.println(map.get(i+""));
		}
		
		String str="ActNo CcyTyp Status AutoNo AcTpDsc AccTyp IntTp IntMth InrRat ActNm CusNam ComTel CopTel OpnActDt LstMntDt LstTrsrDt LstInPyDt IntEndDt DfltS ActBal UncFnd NPBDpAmt PblInt PsBkBal NCntIntAmt InflBal PrIntAdj ChqAlyNo FixIntChq IntTotAmt LstOvLmtDt NatWitInTx FrgWitInTx CrAmt CrnVDbInt AvCrAmt CrnByAmt LstMixRepAmt CshAdv CshDisInt ByInt SfCd NewBrNo StmtCycle Cycle Day BkNo NRefCd NCDNIntCd Mod SpActFlg PErrNo SndDat GLClCd lstActTpMdfy OdInt ActBal CrnAvlBal RetChqNum CnRtrnChqCnt DfltS2 PsbkSt Option SlSt DpBlNo DpBlSt DfltS3 AgrSgnDt ComTD BlacDt PrInt FxPrInt AbOfsInd SgnInd";
		str="20 3 7 4 30 4 4 4 7 60 60 12 12 10 10 10 10 10 10 19 19 19 19 19 19 19 19 2 8 19 10 19 19 17 19 17 19 19 19 19 19 5 4 1 2 2 2 4 12 3 1 2 10 25 10 19 17 18 10 10 1 2 1 1 7 1 8 10 4 10 15 13 1 1";
		str=str.replaceAll(" ", ",");
		System.out.println(str);
		int j=0;
		for (int i : numss) {
			j+=i;
		}
		System.out.println(j);
	}
}
