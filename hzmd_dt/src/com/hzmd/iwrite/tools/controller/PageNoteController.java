/**
 * 
 */
package com.hzmd.iwrite.tools.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import com.hzmd.common.util.StringUtil;
import com.hzmd.iwrite.tools.model.PageNoteModel;
import com.jfinal.core.Controller;

/**
 * @author yangming
 *
 */
public class PageNoteController extends Controller {

  /**
   * 
   */
  public PageNoteController() {
  }
  
  public void saveNote() {
    String pageId = getParam("pageId");
    String pageName = getParam("pageName");
    String pageNote = getParam("pageNote");
    if(StringUtil.isEmpty(pageId)) {
      renderJson("{\"error\": 1}");
      return;
    }
    PageNoteModel bean = PageNoteModel.mgr._findById(pageId);
    if(bean == null) {
      bean = new PageNoteModel().setId(pageId).setPageName("");
      bean.save();
    }
    if(StringUtil.isNotEmpty(pageName)) {
      bean.setPageName(pageName);
    }
    bean.setPageNote(pageNote);
    bean.update();
    renderJson("{}");
  }
  
  public void getNote() {
    String pageId = getParam("pageId");
    if(StringUtil.isEmpty(pageId)) {
      renderJson("{\"error\": 1}");
      return;
    }
    PageNoteModel bean = PageNoteModel.mgr._findById(pageId);
    if(bean == null) {
      bean = new PageNoteModel().setId(pageId).setPageName("");
      bean.save();
    }
    Map<String, String> map = new LinkedHashMap<String, String>();
    map.put("pageId", bean.getId());
    map.put("pageName", bean.getPageName());
    map.put("pageNote", bean.getPageNote());
    
    renderJson(map);
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
  }

}
