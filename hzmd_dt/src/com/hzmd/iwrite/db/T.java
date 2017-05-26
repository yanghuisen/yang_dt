/**
 * 
 */
package com.hzmd.iwrite.db;

import com.hzmd.common.util.CmdLog;
import com.hzmd.iwrite.web.config.G;

/**
 * @author yangming
 *
 */
public class T {

  private static String tb(String tableName) {
    return G.tb(tableName);
  }

  public static final String words = tb("words");
  public static final String group = tb("word_group");
  public static final String tag = tb("word_tag");
  public static final String property = tb("word_property");
  
  /**
   * 
   */
  public T() {
  }

  public static void main(String[] args) {
    CmdLog.info("" + Integer.MAX_VALUE);
  }

}
