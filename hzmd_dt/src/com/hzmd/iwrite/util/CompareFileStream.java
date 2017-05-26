/**
 * 
 */
package com.hzmd.iwrite.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Yangming
 *
 */
public class CompareFileStream {

  /**
   * 
   */
  public CompareFileStream() {
  }
  
  private static String toHex(int n, int digitLen) {
    String s = Integer.toString(n, 16);
    return StringUtils.leftPad(s, digitLen, '0');
  }
  
  public static boolean printHexFile(File f1) throws Exception {
    BufferedInputStream in1 = new BufferedInputStream(new FileInputStream(f1));
    int count1 = 0;
    int b1 = -1;
    boolean in1Done = false;
    try {
      while(true) {
        if(count1 % 16 == 0 ) {
          if(count1 > 0) {
            System.out.println();
          }
          System.out.print(toHex(count1, 16) + "  ");
        }
        if((b1 = in1.read()) != -1) {
          ++count1;
          System.out.print(toHex(b1, 2).toUpperCase()  + " ");
        } else {
          in1Done = true;
        }
        
        if(in1Done) {
          break;
        }
      }
    } finally {
      IOUtils.closeQuietly(in1);
    }
    
    return true;
  }
  
  public static int compareFile(File f1, File f2) throws Exception {
    BufferedInputStream in1 = new BufferedInputStream(new FileInputStream(f1));
    BufferedInputStream in2 = new BufferedInputStream(new FileInputStream(f2));
    int count = 0;
    int b1 = -1, b2 = -1;
    boolean in1Done = false, in2Done = false;
    int diffCount = 0;
    try {
      while(true) {        
        if((b1 = in1.read()) == -1) {
          in1Done = true;
        }
        if((b2 = in2.read()) == -1) {
          in2Done = true;
        }
        if(b1 != b2) {
          System.out.println("At: " + toHex(count, 8) + " 不等 " + toHex(b1, 2) + ":" + toHex(b2, 2));
          ++diffCount;
        }        
        ++count;
        if(in1Done && in2Done) {
          break;
        }
      }
    } finally {
      IOUtils.closeQuietly(in1);
      IOUtils.closeQuietly(in2);
    }
    
    return diffCount;
  }

  /**
   * @param args
   * @throws Exception 
   */
  public static void main(String[] args) throws Exception {
//    File f1 = new File("E:/working/wys/iTest/3.0/安装包/题库分包/jar/cet4_test.tiku");
//    File f2 = new File("E:/working/wys/iTest/3.0/安装包/题库分包/jar/cet4_test_linux.tiku");
    
    File f1 = new File("E:/working/wys/iTest/3.0/安装包/题库分包/jar/cet4_test.tiku.zip");
    File f2 = new File("E:/working/wys/iTest/3.0/安装包/题库分包/jar/cet4_test_linux.tiku.zip");
    
    int diffCount = compareFile(f1, f2);
    System.out.println("两个文件有" + diffCount + "处不等");
  }

}
