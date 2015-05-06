package com.sinopec.report.util;

import java.util.Random;

public class PasswordUtil {

	static char[] s = new char[] { '6', 'f', '9', '1', '7', '8', 'a', '2', 'b',
			'3', 'c', '0', 'd', '5', 'e', '4' };
 
	static public String encodePwd(String password) {
		int length = password.length();
		while (password.length() < 8) {
			Random random = new Random();
			char c = (char) ('A' + random.nextInt(26));
			password = password + c;
		}
		password = "" + length + '=' + password;
		char[] p = password.toCharArray();
		String result = "";
		for (int i = 0; i < p.length; i++) {
			int m = p[i] - 0;
			result = s[m / 16] + result + s[m % 16];
		}
		return result;
	}

	static public String decodePwd(String password) {
		String result = "";
		int length = password.length();
		char[] array = password.toCharArray();
		if ((length % 2) != 0)
			return result;
		for (int i = 0; i < length / 2; i++) {
			int tt = pos(array[i], s) + 1;
			int ll = pos(array[length - i - 1], s) + 1;
			result = (char) (tt * 16 + ll - 17) + result;
		}
		int index = pos('=', result.toCharArray());
		int j = Integer.valueOf(result.substring(0, index)).intValue();
		result = result.substring(index + 1, index + 1 + j);
		return result;
	}

	private static int pos(char a, char array[]) {
		for (int i = 0; i < array.length; i++) {
			if (a == array[i])
				return i;
		}
		return -1;
	}
	public static void main(String args[]){
		System.out.println(PasswordUtil.encodePwd("oracle.jdbc.OracleDriver"));
		System.out.println(PasswordUtil.decodePwd("77111aaa11a5f91f91fb"));
		StringBuffer post =new StringBuffer("aa22aa2111111111919111911171aaa21aaaa2a1aaaa111775c791c49f1d8c7b3ec6f39efabefef7bcf89fc3fe3137");
										     
//		for(int i=0;i<post.length();i++){
//			System.out.println((i+1)+":"+post.substring(i,i+1));
//		}
	}
}
