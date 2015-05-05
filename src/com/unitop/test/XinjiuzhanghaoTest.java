package com.unitop.test;

import com.unitop.util.CommonUtil;
import com.unitop.util.CoreBillUtils;

public class XinjiuzhanghaoTest {

	public static void main(String[] args) {

		System.out.println(CommonUtil.getCoreBranchCode("48606000"));
		String zhangh="4233200001819100115922";
		zhangh=CoreBillUtils.parseTypeN(zhangh, 17);
		System.out.println(zhangh);
		
	}
	
}
