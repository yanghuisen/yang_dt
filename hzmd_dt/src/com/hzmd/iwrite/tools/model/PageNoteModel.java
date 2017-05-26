/**
 * 请不要删除和改变任何以含有 //@#@iGen 的行， 这些标识用于iGen识别更新！
 */
package com.hzmd.iwrite.tools.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hzmd.iwrite.model.ItestModel;


/**
 //@#@iGen_Properties_Begin@#@//
SELECT pageId, pageName, pageNote
     FROM tools_page_note;
bean.setId(id).setPageName(pageName).setPageNote(pageNote)
pageId=String=id
pageName=String
pageNote=String
//@#@iGen_Properties_End@#@//
 * @author yangming
 *
 */
public class PageNoteModel extends ItestModel<PageNoteModel> {

  private static final long serialVersionUID = 1198067626209450577L;
  
  static final Logger log = LoggerFactory.getLogger(PageNoteModel.class);

  public static final PageNoteModel mgr = new PageNoteModel();
  
  /**
   * 
   */
  public PageNoteModel() {
  }
  
  /////////////////////////////////////////////////////////////////////////////////////
  // 开始常量等杂项业务方法

  public static final class CC {
    
    // TODO 请修改成你字段对应的常量定义
//    public static final class C_status {
//      public static final int FAIL = 0;
//      public static final int SUCCESS = 1;
//      
//      public static final BizConstant me = new BizConstant(new Object[][] {
//        {"失败", FAIL}, {"成功", SUCCESS} 
//      });
//    }
  }

  // 结束常量等杂项业务方法
  ////////////////////////////////////////////////////////////////////////////////////
  
  /////////////////////////////////////////////////////////////////////////////////////
  // 开始跟本Model对象有关的业务方法
  
  // 结束跟本Model对象有关的业务方法
  ////////////////////////////////////////////////////////////////////////////////////
  
  /////////////////////////////////////////////////////////////////////////////////////
  // 开始mgr的业务方法
  
  // 结束mgr的业务方法
  ////////////////////////////////////////////////////////////////////////////////////
  
  ////////////////////////////////////////////////////////////////////////////////////
  // 开始getter setter方法自动生成代码 不要删除含有 //@#@iGen 字符串的标识行
  //@#@iGen_GetterSetters_Begin@#@//

  //@ pageId=String=id
  public String getId() {
    return _get_String("pageId");
  }

  //@ pageId=String=id
  public PageNoteModel setId(String id) {
    return set("pageId", id);
  }

  //@ pageName=String
  public String getPageName() {
    return _get_String("pageName");
  }

  //@ pageName=String
  public PageNoteModel setPageName(String pageName) {
    return set("pageName", pageName);
  }

  //@ pageNote=String
  public String getPageNote() {
    return _get_String("pageNote");
  }

  //@ pageNote=String
  public PageNoteModel setPageNote(String pageNote) {
    return set("pageNote", pageNote);
  }

//@#@iGen_GetterSetters_End@#@//
  // 结束getter setter方法自动生成代码
  ////////////////////////////////////////////////////////////////////////////////////

}
