/**
 * 
 */
package com.hzmd.iwrite.web.config;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hzmd.common.I;
import com.hzmd.common.freemarker.FreemarkerGlobal;
import com.hzmd.common.util.CmdLog;
import com.hzmd.common.util.DateUtil;
import com.hzmd.common.util.url.UrlUtil;
import com.hzmd.iwrite.util.ItestUtil;

/**
 * @author yangming
 *
 */
public class ItestFreemarkerGlobal extends FreemarkerGlobal {

  /**
   * 
   */
  public ItestFreemarkerGlobal() {
  }

  public String urlEncode(String s) {
    if(s == null) {
      return s;
    }
    return UrlUtil.urlEncodeQuietly2(s, I.UTF8);
  }
  
  @Override
  public String date(Date date) {
    return ItestUtil.formatDate(date);
  }

  public String year(Date date) {
    return DateUtil.formatQuietly(date, "yyyy");
  }

  @Override
  public String dateTime(Date date) {
    return ItestUtil.formatDateTime(date);
  }

  @Override
  public String dateTimeBr(Date date) {
    return ItestUtil.formatDateTimeBr(date);
  }
  
  public String dateTimeToMinute(Date date) {
    return ItestUtil.formatDateTimeToMinute(date);
  }
  
  public String dateTimeToMinuteBr(Date date) {
    return ItestUtil.formatDateTimeToMinuteBr(date);
  }
  
  public String autoCut(String s, int maxLength) {
    if(s == null) {
      return null;
    }
    if(s.length() > maxLength) {
      s = s.substring(0, maxLength) + "...";
    }
    return s;
  }
  
  public <T> T safeGet(List<T> list, int index) {
    if(list != null && index >=0 && index < list.size()) {
      return list.get(index);
    }
    return null;
  }
  
  static final Pattern P_S = Pattern.compile("\\s+");
  
  public int wordCount(String text) {
//    function essayWordCount(essayContent) {
//      essayContent = essayContent.replace(/[\u4E00-\u9FA5]{1}/g, " a ");// 替换中文字符为空格
//      essayContent = essayContent.replace(/\n|\r|^\s+|\s+$/gi, "");// 将换行符，前后空格不计算为单词数
//      essayContent = essayContent.replace(/\s+/gi, " ");// 多个空格替换成一个空格
//      var length = 0;
//      var match = essayContent.match(/\s/g);
//      if (match) {
//        length = match.length + 1;
//      } else if (essayContent) {
//        length = 1;
//      }
//      return length;
//    }  
    // js版本
    
//    if(text == null) {
//      return 0;
//    }
//    return WordUtil.wordCount(text);
    
    if(text == null || (text = text.trim()).length() == 0) {
      return 0;
    }
    text = text.replaceAll("[\u4E00-\u9FA5]{1}", " a ");
    text = text.replaceAll("\\n|\\r|^\\s+|\\s+$", "");
    text = text.replaceAll("\\s+", " ");
    Matcher matcher = P_S.matcher(text);
    int count = 0;
    while(matcher.find()) {
      ++count;
    }
    return count;
  }

  /**
   * 内部存储的分数放大了100倍，转换成显示在页面上的分数
   * @param score
   * @return
   */
  public String scoreToShow(int score) {
    return ItestUtil.scoreToShow(score);
  }
  
  public String getUploadUrlPrev() {
    return G.getUploadUrlPrev();
  }
  
  public String getConfigShow(String key) {
    return G.getProp(key);
  }
  
  public Double stringToDouble(String s){
	  return Double.valueOf(s);
  }
  
  public boolean isJcxlUse() {
    return G.isJcxlUse();
  }
  
  public static void main(String[] args) {
    String s = "This is 中国. hehe哈哈";
    ItestFreemarkerGlobal fg = new ItestFreemarkerGlobal();
    CmdLog.info(fg.wordCount(s) + "");
  }

}
