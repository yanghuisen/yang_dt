/**
 * 
 */
package com.hzmd.iwrite.db;

import java.util.Properties;

import com.hzmd.common.util.PropsUtil;
import com.hzmd.iwrite.db.ArpMappingInitItest;
import com.hzmd.iwrite.web.config.G;
import com.jfinal.ext.arp.ArpStarter;

/**
 * @author yangming
 *
 */
public class UseDbUtil {

  /**
   * 
   */
  public UseDbUtil() {
  }
  
  private static ArpStarter ast;
  
  public static void start() {
    if(ast == null) {
      Properties dbProps = PropsUtil.extractSubProps(G.getProperties(), "db.main");
      ast = new ArpStarter(dbProps, ArpMappingInitItest.me);
      ast.start();  
    }
  }
  
  public static void stop() {
    if(ast != null) {
      ast.stop();
      ast = null;  
    }
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    try {
      UseDbUtil.start();
      
      
      
    } finally {
      UseDbUtil.stop();
    }
  }

}
