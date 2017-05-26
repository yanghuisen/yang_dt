package com.hzmd.iwrite.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.hzmd.common.util.DateUtil;
import com.hzmd.common.util.RandomUtil;
import com.hzmd.common.util.StringUtil;
import com.hzmd.iwrite.global.ITestCC;
import com.hzmd.iwrite.web.config.G;

public abstract class ZiyuanFileImportUtil {

  /**
   * 获取文件类型
   * @param extension
   * @return
   */
  public static int getFileType(String extension) {
    int fileType = 0;
    extension = extension.toLowerCase();
    if (ITestCC.ZiyuanFileCC.C_EXTENSION.YINPIN.contains(extension)) {
      fileType = ITestCC.ZiyuanFileCC.C_FILEYPE.YINPIN;
    } else if (ITestCC.ZiyuanFileCC.C_EXTENSION.SHIPIN.contains(extension)) {
      fileType = ITestCC.ZiyuanFileCC.C_FILEYPE.SHIPIN;
    } else if (ITestCC.ZiyuanFileCC.C_EXTENSION.TEXT.contains(extension)) {
      fileType = ITestCC.ZiyuanFileCC.C_FILEYPE.TEXT;
    } else {
      fileType = ITestCC.ZiyuanFileCC.C_FILEYPE.OTHER;
    }
    return fileType;
  }

  /**
   * 获取文件目录
   */

  public static Map<String, String> getDir(String pref) {
    //String ziyuanfilesDir = G.getProp("ziyuanfiles.dir"); //真实文件保存路径
    String ziyuanfilesDir = G.getProp("upload.dir"); //真实文件保存路径
    //String pref = ITestCC.ZiyuanFileCC.C_SAVE_PREF.PREF; //资源文件的总文件夹
    String path = DateUtil.formatQuietly(new Date(), "yyyy/MM"); //时间目录打散
    String random = RandomUtil.randomAlphanumeric(8, 8); //随机数字文件夹
    String realPath = ziyuanfilesDir + "/" + pref + "/" + path + "/" + random; //绝对路径
    String filePath = pref + "/" + path + "/" + random; //相对路径
    Map<String, String> newMap = new HashMap<String, String>();

    newMap.put("realPath", realPath);
    newMap.put("filePath", filePath);
    newMap.put("random", random);

    return newMap;
  }

  /**
   * 文件复制--从临时目录复制到指定目录
   */

  public static void fileCopy(File source, File to) {
    FileInputStream fi = null;
    FileOutputStream fo = null;
    FileChannel in = null;
    FileChannel out = null;
    try {
      fi = new FileInputStream(source);
      fo = new FileOutputStream(to);
      in = fi.getChannel();//得到对应的文件通道
      out = fo.getChannel();//得到对应的文件通道
      in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        fi.close();
        in.close();
        fo.close();
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static String txt2String(File file) {
    StringBuffer result = new StringBuffer();
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
      String s = null;
      while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
        result.append(s);
      }
      br.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result.toString();
  }
  
  
}
