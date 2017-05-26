/**
 * 
 */
package com.hzmd.iwrite.util;

import com.hzmd.common.encrypt.Des3Util;

/**
 * @author Yangming
 * @create  2015年4月22日
 */
public class DesEncryptUtil {

  /**
   * 
   */
  public DesEncryptUtil() {
  }
  
  public static String encode(String plainText, String key) {
    try {
      return Des3Util.encode(plainText, key);
    } catch(Exception e) {
      e.printStackTrace();
      return plainText;
    }
  }
  
  public static String decode(String encryptText, String key) {
    try {
      return Des3Util.decode(encryptText, key);
    } catch(Exception e) {
      e.printStackTrace();
      return encryptText;
    }
  }

  /**
   * @param args
   */
  public static void main(String[] args) {

  }

}
