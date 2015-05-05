 package com.unitop.framework.util;
 
 public final class MyByte
 {
   public static byte[] append(byte[] a, byte[] b)
   {
     byte[] z = new byte[a.length + b.length];
     System.arraycopy(a, 0, z, 0, a.length);
     System.arraycopy(b, 0, z, a.length, b.length);
     return z;
   }
 
   public static byte[] toBytes(long n) {
     return toBytes(n, new byte[4]);
   }
   public static byte[] toBytes(long n, byte[] b) {
     b[3] = (byte)(int)n;
     n >>>= 8;
     b[2] = (byte)(int)n;
     n >>>= 8;
     b[1] = (byte)(int)n;
     n >>>= 8;
     b[0] = (byte)(int)n;
     return b;
   }
 
   public static long toLong(byte[] b) {
     return (b[3] & 0xFF) + ((b[2] & 0xFF) << 8) + (
      (b[1] & 0xFF) << 16) + ((b[0] & 0xFF) << 24);
   }
 
   public static boolean areEqual(byte[] a, byte[] b) {
     int aLength = a.length;
     if (aLength != b.length) {
     return false;
     }
 
     for (int i = 0; i < aLength; i++) {
       if (a[i] != b[i]) {
       return false;
       }
     }
    return true;
   }
 
   public static int compareTo(byte[] lhs, byte[] rhs) {
    if (lhs == rhs) {
       return 0;
     }
  if (lhs == null) {
      return -1;
     }
     if (rhs == null) {
       return 1;
     }
    if (lhs.length != rhs.length) {
      return lhs.length < rhs.length ? -1 : 1;
     }
     for (int i = 0; i < lhs.length; i++) {
       if (lhs[i] < rhs[i])
        return -1;
       if (lhs[i] > rhs[i]) {
         return 1;
       }
     }
    return 0;
   }
   public static byte[] byteCon(byte[] b1, byte[] b2) {
     int m = b1.length;
     int n = b2.length;
     byte[] b = new byte[m + n];
    for (int i = 0; i < m; i++) {
      b[i] = b1[i];
     }
    for (int i = 0; i < n; i++) {
       b[(m + i)] = b2[i];
     }
     return b;
   }
   public static short toShort(byte[] b) {
   return (short)((b[1] & 0xFF) + ((b[0] & 0xFF) << 8));
   }
 
   public static int toInteger(byte[] b) {
     return (b[3] & 0xFF) + ((b[2] & 0xFF) << 8) + ((b[1] & 0xFF) << 16) + ((b[0] & 0xFF) << 24);
   }
 }