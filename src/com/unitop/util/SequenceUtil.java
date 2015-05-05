package com.unitop.util;

public class SequenceUtil {
		private static int num;
		static{
			num=0;
		}
		
		public static synchronized int getInstance(){
			num++;
			if(num>9999999){
				num=0;
			}
			return num;
		}
	
}
