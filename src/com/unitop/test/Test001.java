package com.unitop.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.unitop.util.SequenceUtil;

public class Test001 {

	public static void main(String[] args) {
		///String s ="123456700000                            20140313141155000S0012030008010001                029177      HX0459      匹配记录已处理                                                                                                                                                                                                                      000000  06004       20140313141000014201      25062015     000ERR0E                                                                                             E0000000000000000000002000000000000000000450000000000000000000000000000000000000000                                                                                                                          000000000000000000000000000000000000000000000000                                                                                                                          000000000000000000000000000000000000000000000000                                                                                                                          000000000000000000000000000000000000000000000000                                                                                                                          000000000000000000000000000000000000000000000000                                                                                                                          00000000000 ";
		//System.out.println(s.length());
		//UUID uu=UUID.randomUUID();
		//System.out.println(uu.toString());
		System.out.println(SequenceUtil.getInstance());
		System.out.println(SequenceUtil.getInstance());
		
		for (int i = 0; i <4; i++) {
			new Thread(new Runnable() {
				
				public void run() {
					System.out.println(SequenceUtil.getInstance());
				}
			}).start();
		}
	}
}
