/**
 * 
 */
package com.hzmd.iwrite.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;

/**
 * 加密解密.tiku题库文件
 * @author Yangming
 *
 */
public class DesEncryptFileUtil {

  Key key;

  public DesEncryptFileUtil(String str) {
    initKey(str);//生成密匙   
  }

  private static byte[] strToBytes(String str) {
    try {
      return str.getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {      
      e.printStackTrace();
      return str.getBytes();
    }
  }
  
  /**  
  * 根据参数生成KEY  
  */
  void initKey(String strKey) {
    try {
//      KeyGenerator _generator = KeyGenerator.getInstance("DES");
//      _generator.init(new SecureRandom(strToBytes(strKey)));
//      this.key = _generator.generateKey();
//      _generator = null;
      
      KeyGenerator _generator = KeyGenerator.getInstance("DES");  
      //防止linux下 随机生成key   
      SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
      byte[] seed = strToBytes(strKey);
      secureRandom.setSeed(seed);  
        
      _generator.init(56, secureRandom);  
      this.key = _generator.generateKey();  
      _generator = null;  
      
    } catch (Exception e) {
      throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
    }
  }

  /**  
   * 文件file进行加密并保存目标文件destFile中  
   *  
   * @param file   要加密的文件 如c:/test/srcFile.txt  
   * @param destFile 加密后存放的文件名 如c:/加密后文件.txt  
   */
  void encrypt(File file, File destFile) throws Exception {
    Cipher cipher = Cipher.getInstance("DES");
    // cipher.init(Cipher.ENCRYPT_MODE, getKey());   
    cipher.init(Cipher.ENCRYPT_MODE, this.key);
    OutputStream out = null;
    CipherInputStream cis = null;
    if (destFile.getParentFile() != null) {
      destFile.getParentFile().mkdirs();
    }
    try {
      out = new FileOutputStream(destFile);
      cis = new CipherInputStream(new FileInputStream(file), cipher);
      byte[] buffer = new byte[4096];
      int r;
      while ((r = cis.read(buffer)) > 0) {
        out.write(buffer, 0, r);
      }        
    } finally {
      try {
        cis.close();  
      } catch(Exception e) {        
      }
      try {
        out.close();  
      } catch(Exception e) {        
      }
    }
  }
  
  void encrypt(String file, String destFile) throws Exception {
    encrypt(new File(file), new File(destFile));
  }
  
  /**  
  * 文件采用DES算法解密文件  
  *  
  * @param file 已加密的文件 如c:/加密后文件.txt  
  * @param destFile  
  *         解密后存放的文件名 如c:/ test/解密后文件.txt  
  */
  void decrypt(File file, File dest) throws Exception {
    Cipher cipher = Cipher.getInstance("DES");
    cipher.init(Cipher.DECRYPT_MODE, this.key);
    InputStream is = null;
    CipherOutputStream cos = null;
    try {
      is = new FileInputStream(file);
      cos = new CipherOutputStream(new FileOutputStream(dest), cipher);
      byte[] buffer = new byte[4096];
      int r;
      while ((r = is.read(buffer)) >= 0) {
        cos.write(buffer, 0, r);
      }
    } finally {
      try {
        is.close();  
      } catch(Exception e) {        
      }
      try {
        cos.close();  
      } catch(Exception e) {        
      }
    }
  }

  /**  
  * 文件采用DES算法解密文件  
  *  
  * @param file 已加密的文件 如c:/加密后文件.txt  
  *         * @param destFile  
  *         解密后存放的文件名 如c:/ test/解密后文件.txt  
  */
  void decrypt(String file, String dest) throws Exception {
    decrypt(new File(file), new File(dest));
  }
  
  
  private static DesEncryptFileUtil DEFU = new DesEncryptFileUtil("Th#isisAITesTPa%$s012@3XNB81!7");
  
  private static File _changeFileNameExt(File srcFile, String newExt) {
    String fn = srcFile.getName();
    String prev = fn;
    int pos = fn.indexOf('.');
    if(pos > -1) {
      prev = fn.substring(0, pos);
    }
    String destFileName = prev + newExt;
    return new File(srcFile.getParentFile(), destFileName);
  }
  
  /**
   * 把题库压缩文件加密，生成文件是srcFile同目录下的  文件名前缀.tiku
   * 例如 题库包文件是 c:/mydata/cet4_20160201.tiku.zip
   * 生成文件是  c:/mydata/cet4_20160201.tiku
   * @param srcFile  源.tiku.zip题库压缩包
   * @return 加密后的文件
   */
  public static File encryptTikuFile(File srcFile) throws Exception {
    File encryptFile = _changeFileNameExt(srcFile, ".tiku");
    encryptTikuFile(srcFile, encryptFile);
    return encryptFile;
  }
  
  /**
   * 把题库压缩文件加密
   * @param srcFile  源.tiku.zip题库压缩包
   * @param encryptFile 加密后的题库包文件
   */
  public static void encryptTikuFile(File srcFile, File encryptFile) throws Exception {
    DEFU.encrypt(srcFile, encryptFile);
    //System.out.println("  ::java encrypt from:" + getAbsolutePath(srcFile) + " to:" + getAbsolutePath(encryptFile));
  }
  
  /**
   * 把题库压缩文件加密，生成文件是srcFile同目录下的  文件名前缀.tiku
   * 例如 题库包文件是 c:/mydata/cet4_20160201.tiku.zip
   * 生成文件是  c:/mydata/cet4_20160201.tiku
   * @param srcFilePath
   * @return 加密后的文件
   * @throws Exception
   */
  public static File encryptTikuFile(String srcFilePath) throws Exception {
    return encryptTikuFile(new File(srcFilePath));
  }
  
  /**
   * 把题库压缩文件加密
   * @param srcFile  源.tiku.zip题库压缩包
   * @param encryptFile 加密后的题库包文件
   */
  public static void encryptTikuFile(String srcFilePath, String encryptFilePath) throws Exception {
    encryptTikuFile(new File(srcFilePath), new File(encryptFilePath));
  }
  
  /**
   * 把题库压缩文件解密，生成文件是srcFile同目录下的  文件名前缀.tiku.zip
   * 例如 加密的题库包文件是 c:/mydata/cet4_20160201.tiku
   * 解密的生成题库可打开zip文件是  c:/mydata/cet4_20160201.tiku.zip
   * @param srcFile  加密的源.tiku题库压缩包
   * @return 解密后的文件
   */
  public static File decryptTikuFile(File srcFile) throws Exception {
    File decryptFile = _changeFileNameExt(srcFile, ".tiku.zip");
    decryptTikuFile(srcFile, decryptFile);
    return decryptFile;
  }
  
  /**
   * 把加密的题库文件解密
   * @param srcFile  加密的.tiku题库文件
   * @param decryptFile 解密后的题库包文件(可打开的.tiku.zip文件) .tiku.zip
   */
  public static void decryptTikuFile(File srcFile, File decryptFile) throws Exception {
    DEFU.decrypt(srcFile, decryptFile);
    //System.out.println("  ::java decrypt from:" + getAbsolutePath(srcFile) + " to:" + getAbsolutePath(decryptFile));
  }
  
  static String getAbsolutePath(File f) {
    try {
      return f.getCanonicalPath();
    } catch (IOException e) {
      return f.getAbsolutePath();
    }
  }
  
  /**
   * 把加密的题库文件解密，生成文件是srcFile同目录下的可打开的.tiku.zip文件  文件名前缀是.tiku.zip
   * 例如 加密的题库文件是 c:/mydata/cet4_20160201.tiku
   * 生成文件是  c:/mydata/cet4_20160201.tiku.zip
   * @param srcFilePath
   * @return 解密后的文件
   * @throws Exception
   */
  public static File decryptTikuFile(String srcFilePath) throws Exception {
    return decryptTikuFile(new File(srcFilePath));
  }
  
  /**
   * 把加密的题库文件解密, 生成的文件名前缀是.tiku.zip
   * @param srcFile  加密的.tiku题库文件
   * @param decryptFile 解密后的题库包文件(可打开的.tiku.zip文件) .tiku.zip
   */
  public static void decryptTikuFile(String srcFilePath, String decryptFilePath) throws Exception {
    decryptTikuFile(new File(srcFilePath), new File(decryptFilePath));
  }
  

  static void testCode() throws Exception {
    String srcFile = "E:/working/wys/iTest/3.0/安装包/题库分包/20160127_12_CET4_test.tiku.zip";
    
    long t1 = System.currentTimeMillis();
    File encryptFile = DesEncryptFileUtil.encryptTikuFile(srcFile); //加密
    long t2 = System.currentTimeMillis();
    File decryptFile = DesEncryptFileUtil.decryptTikuFile(encryptFile); //解密
    long t3 = System.currentTimeMillis();
    System.out.println("t2-t1=" + (t2-t1) + "ms, encryptFile=" + encryptFile.getCanonicalPath());
    System.out.println("t3-t2=" + (t3-t2) + "ms, decryptFile=" + decryptFile.getCanonicalPath());
  }

  
  
  public static void main(String[] args) throws Exception {
//    testCode();
    
    if(args == null || args.length < 1) {
      System.out.println("ERROR please input at least two arg!");
    } else {
      String opt = args[0].trim();
      String srcFile = args[1].trim();
      String destFile = args.length > 2 ? args[2].trim() : null;
      if(destFile != null && destFile.length() == 0) {
        destFile = null;
      }
      if(opt.startsWith("-")) {
        opt = opt.substring(1);
      }
      if("d".equalsIgnoreCase(opt) || "decrypt".equalsIgnoreCase(opt)) {
        if(destFile != null) {
          DesEncryptFileUtil.decryptTikuFile(srcFile, destFile);
        } else {
          DesEncryptFileUtil.decryptTikuFile(srcFile);
        }
      } else if("e".equalsIgnoreCase(opt) || "encrypt".equalsIgnoreCase(opt)) {
        if(destFile != null) {
          DesEncryptFileUtil.encryptTikuFile(srcFile, destFile);
        } else {
          DesEncryptFileUtil.encryptTikuFile(srcFile);
        }
      }
    }
    
//    if(args == null || args.length == 0) {
//      System.out.println("ERROR please input at least one arg, means the src file path.");
//    } else {
//      String srcFilePath = args[0].trim();
//      if(args.length > 1) {
//        String destFilePath = args[1].trim();
//        DesEncryptFileUtil.decryptTikuFile(srcFilePath, destFilePath);
//      } else {
//        DesEncryptFileUtil.decryptTikuFile(srcFilePath);
//      }
//    }
  }

}
