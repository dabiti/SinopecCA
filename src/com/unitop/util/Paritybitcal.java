package com.unitop.util;
public class Paritybitcal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String abc="1234567890123456";
		String abc2=GenBancsCd(abc);
		System.out.println(abc2);
	}

	/**
     * @param s
     * @return
     */
    public static String GenBancsCd(String s)
    {
        int ai[][] = {
            {
                10, 9, 8, 7, 6, 5, 4, 3, 2
            }, {
                5, 10, 4, 9, 3, 8, 2, 7, 1
            }, {
                8, 5, 2, 10, 7, 4, 1, 9, 6
            }, {
                4, 8, 1, 5, 9, 2, 6, 10, 3
            }, {
                2, 4, 6, 8, 10, 1, 3, 5, 7
            }, {
                1, 2, 3, 4, 5, 6, 7, 8, 9
            }, {
                6, 1, 7, 2, 8, 3, 9, 4, 10
            }, {
                3, 6, 9, 1, 4, 7, 10, 2, 5
            }, {
                7, 3, 10, 6, 2, 9, 5, 1, 8
            }, {
                9, 7, 5, 3, 1, 10, 8, 6, 4
            }, {
                10, 9, 8, 7, 6, 5, 4, 3, 2
            }, {
                5, 10, 4, 9, 3, 8, 2, 7, 1
            }, {
                8, 5, 2, 10, 7, 4, 1, 9, 6
            }, {
                4, 8, 1, 5, 9, 2, 6, 10, 3
            }, {
                2, 4, 6, 8, 10, 1, 3, 5, 7
            }, {
                1, 2, 3, 4, 5, 6, 7, 8, 9
            }
        };
        int k = 0;
        int i1 = 0;
        int ai1[] = new int[17];
        s = s + "0";
        char ac[] = s.toCharArray();
        for(int j1 = 0; j1 < 17; j1++)
            ai1[j1] = Character.getNumericValue(ac[j1]);

        for(int i = 15; i >= 0; i--)
            if(ai1[i] != 0)
            {
                int j = ai1[i] - 1;
                k += ai[i][j];
            }

        if(k == 0)
        {
            ac[16] = '0';
            return new String(ac);
        }
        int l = k % 10;
        i1 = l % 10;
        for(int k1 = 0; k1 < 16; k1++)
            ac[k1] = String.valueOf(ai1[k1]).charAt(0);

        ac[16] = String.valueOf(i1).charAt(0);
        return new String(ac);
    }
}