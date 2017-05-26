package com.hzmd.iwrite.util;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

import com.hzmd.common.util.Md5Util;

public class XorEncryptUtil {
  public static String key = "123456789";

  /**
   * XOR 算法加密
   * 
   * @param data
   * @param key
   */
  public static String encode(String data, String key) {
    return Base64.encodeBase64String(encodeBytes(data, key));
  }

  /**
   * XOR 算法加密
   * 
   * @param data
   * @param key
   */
  public static byte[] encodeBytes(String data, String key) {
    return encodeBytes(data.getBytes(Charset.forName("UTF-8")), key.getBytes());
  }

  /**
   * XOR 算法加密
   * 
   * @param data
   * @param key
   */
  public static byte[] encodeBytes(byte[] data, byte key[]) {
    byte[] result = new byte[data.length + 32];
    String md5Str = Md5Util.md5_32(String.valueOf(System.currentTimeMillis() / 1000));
    String strH = md5Str.substring(0, 8);
    String strL = md5Str.substring(24);
    long longVal = Long.valueOf(strH, 16) ^ Long.valueOf(strL, 16);
    int intVal = java.lang.Math.abs((int) java.lang.Math.abs(longVal % 12345654321L));

    System.arraycopy(data, 0, result, 32, data.length);
    System.arraycopy(md5Str.toString().getBytes(), 0, result, 0, 32);
    for (int i = 32; i < result.length; i++) {
      result[i] = (byte) (result[i] ^ key[(intVal + i) % key.length]);
    }
    return result;
  }

  /**
   * XOR 算法解密
   * 
   * @param data
   * @param key
   */
  public static String decode(String data, String key) {
    return new String(decode(Base64.decodeBase64(data), key.getBytes()), Charset.forName("UTF-8"));
  }

  /**
   * XOR 算法解密
   * 
   * @param data
   * @param key
   */
  public static byte[] decode(byte[] data, byte key[]) {
    byte[] k = new byte[32];
    System.arraycopy(data, 0, k, 0, 32);

    String md5Str = new String(k);
    String strH = md5Str.substring(0, 8);
    String strL = md5Str.substring(24);
    long longVal = Long.valueOf(strH, 16) ^ Long.valueOf(strL, 16);
    int intVal = java.lang.Math.abs((int) java.lang.Math.abs(longVal % 12345654321L));

    for (int i = 32; i < data.length; i++) {
      data[i] = (byte) (data[i] ^ key[(intVal + i) % key.length]);
    }
    byte[] result = new byte[data.length - 32];
    System.arraycopy(data, 32, result, 0, result.length);
    return result;
  }

  public static void main(String[] args) {

    byte[] result1 = encodeBytes("{\"name\"=\"哈哈  Hello ！！！！？？？？\"}".getBytes(), key.getBytes());
    System.out.println(new String(result1));
    String result2 = Base64.encodeBase64String(result1);
    System.out.println(result2);

    byte[] result3 = Base64
        .decodeBase64("N2RiZjBkMzM3YTZjMjc3OTJiZThiMjNkZWMwYjVjYjULQUsbDQQsClYUS0VVQ0QDHgAMRVYIFgBbQVQoFxFRThFeUV4MBgwdElwMHAwDEEcHTlNHWEd+Xj8EMBYDGGkfPTkIJlZO");
    System.out.println(new String(result3));
    byte[] result4 = decode(result3, "itest3.0.itest3.0.pc.client.itest3.0.itest3.0".getBytes());
    System.out.println(new String(result4));
  }
}