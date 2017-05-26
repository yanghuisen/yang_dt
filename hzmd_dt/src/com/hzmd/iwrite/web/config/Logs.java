/**
 * 
 */
package com.hzmd.iwrite.web.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hzmd.common.util.CmdLog;

/**
 * @author yangming
 *
 */
public class Logs {

  /**
   * 
   */
  public Logs() {
  }
  
  /**
   * 系统配置或者影响到系统运行的严重错误的日志
   */
  public static final Logger SYSTEM_FATAL_LOG = LoggerFactory.getLogger("systemFatal");
  
  
  public static final Logger USER_SESSION_LOG = LoggerFactory.getLogger("userSession");
  
  public static void main(String[] args) {
//    long t1 = System.currentTimeMillis();
//    File file = new File("\\\\192.168.0.99\\upload");
//    long t2 = System.currentTimeMillis();
//    boolean b = file.mkdirs();
//    long t3 = System.currentTimeMillis();
//    file.exists();
//    long t4 = System.currentTimeMillis();
//    CmdLog.info("t2-t1=" + (t2-t1) + "ms");
//    CmdLog.info("t3-t2=" + (t3-t2) + "ms");
//    CmdLog.info("t4-t3=" + (t4-t3) + "ms");
//    CmdLog.info("b=" + b);
    
    File f = new File("C:\\test\\1");
    boolean b1 = f.mkdirs();
    CmdLog.info("b1=" + b1);
    
  }

}
