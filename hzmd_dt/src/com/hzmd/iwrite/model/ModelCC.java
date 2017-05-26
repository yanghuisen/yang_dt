/**
 * 
 */
package com.hzmd.iwrite.model;

import com.hzmd.common.biz.consts.BizConstant;

/**
 * @author yangming
 * 这个类会被废弃，里面的内容要重构到ITestCC中去
 *
 */
public class ModelCC {

  /**
   * 本系统大部分表都带有status字段， 这个字段可以用于标记假删除 <br />
 * 所有status字段含义都一样！
   * @author yangming
   *
   */
  public static final class C_status {
    
    /**
     * 被隐藏/锁定等
     */
    public static final int HIDDEN = 0;
    
    /**
     * 正常状态
     */
    public static final int NORMAL = 1;
    
    private static final BizConstant bc_Off = new BizConstant(new Object[][] {
        {"启用",  NORMAL},
        {"停用",  HIDDEN},
    });
    
    private static final BizConstant bc_Hidden = new BizConstant(new Object[][] {
        {"正常",  NORMAL},
        {"隐藏",  HIDDEN},
    });
    
    private static final BizConstant bc_Lock = new BizConstant(new Object[][] {
        {"正常",  NORMAL},
        {"锁定",  HIDDEN},
    });
    
    public static BizConstant bizConstantHidden() {
      return bc_Hidden;
    }
    
    public static BizConstant bizConstantLock() {
      return bc_Lock;
    }
    
    public static BizConstant bizConstantOff() {
      return bc_Off;
    }
    
  }
  

  /**
   * 
   */
  public ModelCC() {
  }

}
