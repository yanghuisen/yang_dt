/**
 * 
 */
package com.hzmd.iwrite.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hzmd.common.I;
import com.hzmd.common.util.CmdLog;
import com.hzmd.common.util.CollectionUtil;
import com.hzmd.common.util.DateUtil;
import com.hzmd.common.util.FileUtil;
import com.hzmd.common.util.JsonUtil;
import com.hzmd.common.util.RequestUtil;
import com.hzmd.common.util.StringUtil;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.RenderException;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author yangming
 *
 */
public abstract class ItestUtil {

  /**
   * 
   */
  private ItestUtil() {
  }

  static final Logger log = LoggerFactory.getLogger(ItestUtil.class);
  
  static final DecimalFormat YUAN_NF_SHORT = new DecimalFormat("#.##");
  
  /**
   * 记录错误登录日志
   */
  static final Logger loginLog = LoggerFactory.getLogger("loginLog");
  static final Logger adminOptLog = LoggerFactory.getLogger("adminOptLog");
  
  public static void logLoginError(String username, String password, String ip, Date time) {
    Map<String, Object> logInfo = new LinkedHashMap<String, Object>();
    logInfo.put("username", username);
    logInfo.put("password", password);
    logInfo.put("IP", ip);
    if(time == null) {
      time = new Date();
    }
    logInfo.put("time", DateUtil.formatQuietlyFullTime(time));
    loginLog.warn("Login error:" + JsonUtil.toJSONString(logInfo));
  }
  
  /*public static void logAdminOpt(UserModel user, HttpServletRequest req) {
    if(user != null && !user.isStudent()) {
      String uri = req.getRequestURI();
      String queryStr = req.getQueryString();
      if(StringUtil.isNotEmpty(queryStr)) {
        uri += "?" + queryStr;
      }
      String paramsMapStr = StringUtils.replaceEach(RequestUtil.requestParamsToString(req),
          new String[] {"\r\n", "\r", "\n"}, new String[] {"\\r\\n", "\\r", "\\n"});
      String paramStrRaw = RequestUtil.getRequestParamsRaw(req, I.UTF8);
      StringBuilder sb = new StringBuilder();
      Map<String, Object> userMap = new LinkedHashMap<String, Object>();
      userMap.put("uid", user.getId());
      userMap.put("username", user.getUsername());      
      if(user.isTeacher()) {
        sb.append("Teacher::");
      } else {
        sb.append("Admin::");
      }
      sb.append(RequestUtil.ip(req)).append("::").append(JsonUtil.toJSONString(userMap)).append("::").append(uri).append("::")
        .append(paramsMapStr).append("::").append(paramStrRaw);
//      if(user.isTeacher()) {
//        sb.append("::rehcaeT");
//      } else {
//        sb.append("::nimdA");
//      }
      adminOptLog.info(sb.toString());
    }
  }*/
  
  /**
   * 分数取整： 1.0, 1.1 ...  1.4的分数都算1分， 1.5, 1.6 ... 1.9的都算1.5分 <br />
   * 这里的算法是：  100-149都算100，  150-199都算150
   * @param score
   * @return
   */
  public static int stdMachineScore(int score) {
	  /*int xiaoshuBufen = score % 100;
	    int zhengshuBufen = score / 100;
	    return zhengshuBufen * 100 + (xiaoshuBufen < 50 ? 0 : 50);*/
		//修改为四舍五入保留一位小数
	  try{
		  int xiaoshuBufen = score % 10;
		  int zhengshuBufen = score / 10;
		  if(xiaoshuBufen >= 5) {
			  zhengshuBufen++;
		  }
		  return zhengshuBufen*10;
	  }catch(Exception ex){
		  ex.printStackTrace();
	  }
	  return score;
  }
  
  /**
   * 内部存储的分数放大了100倍，转换成显示在页面上的分数
   * @param score
   * @return
   */
  public static String scoreToShow(int score) {
    return YUAN_NF_SHORT.format((double) (double) score / 100);
  }
  
  public static String scoreToShow(Integer score) {
    if(score == null || score < 0) {
      return null;
    } else {
      return scoreToShow(score.intValue());
    }
  }
  
  /**
   * 日期转成输入日期的0点0分0秒 
   * @param date
   * @return
   */
  public static Date dateToTheDateZero(Date date) {
    if(date == null) {
      return null;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }
  
  /**
   * 日期转成输入日期第二天的0点0分0秒
   * @param date
   * @return
   */
  public static Date dateToTheNextDateZero(Date date) {
    if(date == null) {
      return null;
    }
    Calendar cal = Calendar.getInstance();    
    cal.setTime(date);
    cal.add(Calendar.DATE, 1);
    cal.set(Calendar.HOUR, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  public static String formatDateFromTo(Date beginTime, Date endTime) {
    if(beginTime == null && endTime == null) {
      return null;
    } else {
      if(beginTime == null) {
        return ItestUtil.formatDate(endTime);
      }
      if(endTime == null) {
        return ItestUtil.formatDate(beginTime);
      }
      Calendar beginCalc = Calendar.getInstance();
      Calendar endCalc = Calendar.getInstance();
      beginCalc.setTime(beginTime);
      endCalc.setTime(endTime);
      String timeShow = DateUtil.formatQuietly(beginTime, "yyyy-MM-dd");
      String endTimeShow = null;
      if(beginCalc.get(Calendar.YEAR) != endCalc.get(Calendar.YEAR)) {
        endTimeShow = DateUtil.formatQuietly(endTime, "yyyy-MM-dd");
      } else if(beginCalc.get(Calendar.MONTH) != endCalc.get(Calendar.MONTH)) {
        endTimeShow = DateUtil.formatQuietly(endTime, "MM-dd");
      } else if(beginCalc.get(Calendar.DATE) != endCalc.get(Calendar.DATE)) {
        endTimeShow = DateUtil.formatQuietly(endTime, "dd");
      }
      if(endTimeShow != null) {
        timeShow += "~" + endTimeShow;
      }
      return timeShow;
    }
  }
  
  /**
   * iTest用到的统一的日期格式化
   * @param date
   * @return
   */
  public static String formatDate(Date date) {
    return DateUtil.formatQuietly(date, "yyyy-MM-dd");
  }

  /**
   * iTest用到的统一的日期时间格式化
   * @param date
   * @return
   */
  public static String formatDateTime(Date date) {
    return DateUtil.formatQuietly(date, "yyyy-MM-dd HH:mm:ss");
  }
  
  public static String formatDateTimeToMinute(Date date) {
    return DateUtil.formatQuietly(date, "yyyy-MM-dd HH:mm");
  }
  
  public static String formatDateTimeToMinuteBr(Date date) {
    String s = DateUtil.formatQuietly(date, "yyyy-MM-dd HH:mm");
    if(s != null) {
      s = s.replace(" ", "<br>");
    }
    return s;
  }

  /**
   * iTest用到的统一的日期时间格式化， 格式化成 年-月-日 &lt;br&gt;时:分:秒
   * @param date
   * @return
   */
  public static String formatDateTimeBr(Date date) {
    String s = DateUtil.formatQuietly(date, "yyyy-MM-dd HH:mm:ss");
    if(s != null) {
      s = s.replace(" ", "<br>");
    }
    return s;
  }
  
  public static long dateToLong(Date date) {
    return  date != null ? date.getTime() : 0;
  }
  
  
  /**
   * 把 id list之类的list转换成单字段存储， 例如 [11,22,33] 转换成 "@11@22@33@" <br />
   * [33] 转换成 "@33@" <br />
   * 空list或者null list都转换成  null String <br />
   * @return
   */
  public static String listToStringMulti(Collection<?> list) {
    if(CollectionUtil.isEmpty(list)) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    sb.append('@');
    for(Object o : list) {
      sb.append(o).append('@');
    }
    return sb.toString(); 
  }
  
  /**
   * 把 单字段存储的id list之类的字符串转换成Integer List， 例如 "@11@22@33@" 转换成 [11,22,33] <br />
   * "@33@" 转换成 [33] <br />
   * null String 或者空串都转换成 I.EMPTY_LIST   <br />
   * 也可能返回 null
   * @param s
   * @return
   */
  @SuppressWarnings("unchecked")
  public static List<Integer> stringMultiToListInteger(String s) {
    if(StringUtil.isEmpty(s)) {
      return I.EMPTY_LIST;
    }
    return CollectionUtil.asArrayList(StringUtil.splitToIntegerArray(s, '@'));
  }
  
  @SuppressWarnings("unchecked")
  public static Set<Integer> stringMultiToSetInteger(String s) {
    if(StringUtil.isEmpty(s)) {
      return I.EMPTY_SET;
    }
    return CollectionUtil.asLinkedHashSet(StringUtil.splitToIntegerArray(s, '@'));
  }
  
  /**
   * 把 单字段存储的id list之类的字符串转换成Integer Array， 例如 "@11@22@33@" 转换成 [11,22,33] <br />
   * "@33@" 转换成 [33] <br />
   * null String 或者空串都转换成 null   <br />
   * @param s
   * @return
   */
  public static Integer[] stringMultiToArrayInteger(String s) {
    if(StringUtil.isEmpty(s)) {
      return null;
    }
    return StringUtil.splitToIntegerArray(s, '@');
  }
  
  
  /**
   * 数组转map,对空字符串进行处理
   */
  public static Map<String,Object> arrayToMap(String dataColValuesStr){
	Map<String,Object> orderMap = new LinkedHashMap<String,Object>();
	  JSONArray parseArray = JSON.parseArray(dataColValuesStr);
    String[] newArs = new String[parseArray.size()];
	  for(int i=0;i<parseArray.size();i++){
	    	String headName = parseArray.getString(i);
	    	if("unImport".equals(headName)){
	    		headName = headName+"_"+i;		
	    	}   
	    	newArs[i] = headName;
	    	orderMap.put(newArs[i],i+1);
	    }
	  orderMap.put("array", newArs);
	  return orderMap;
  }
  
  /**
   * 字符串转list  字符串里是@
   * @param str 需要转换的字符串
   * @param class1 
   * @return  转换后的list集合
   */
  public static List<String> string2ListOfAt(String str) {
    List<String> list = null;
    if(""!=str && null!=str  &&  !str.isEmpty()){
      list = new ArrayList<String>();
      if (str.contains("@")) {
        String[] strArr = str.substring(1,str.length()-1).split("@");
        for(String strs:strArr){
          if(StringUtil.isNotEmpty(strs)){
            list.add(strs);
          }
        }
      } else {
        list.add(str);
      }
    }
    return list;
  }
  
  /**
   * 字符串转list  字符串里是,
   * @param str 需要转换的字符串
   * @param class1 
   * @return  转换后的list集合
   */
  public static List<String> string2ListOfComma(String str) {
    List<String> list = null;
    if(""!=str && null!=str  &&  !str.isEmpty()){
      list = new ArrayList<String>();
      if (str.contains(",")) {
        String[] strArr = str.split(",");
        for(String strs:strArr){
          if(StringUtil.isNotEmpty(strs)){
            list.add(strs);
          }
        }
      } else {
        list.add(str);
      }
    }
    return list;
  }
  
  
  
  /**
   * list转字符串
   * @param list集合
   * @return  转换后的字符串
   */
  public static String list2String(List<?> list) {
    String str="";
    if(list!=null && list.size()>0){
      for(Object obj:list){
        str+=","+obj;
      }
      str = str.substring(1);
    }
    return str;
  }
  
  public static String secondToShow(long second) {
    String format;
    Object[] array;
    long hours = (second / (60 * 60));
    long minutes = (second / 60 - hours * 60);
    long seconds =  (second - minutes * 60 - hours * 60 * 60);
    if (hours > 0) {
      format = "%1$,d小时%2$,d分%3$,d秒";
      array = new Object[] { hours, minutes, seconds };
    } else if (minutes > 0) {
      format = "%1$,d分%2$,d秒";
      array = new Object[] { minutes, seconds };
    } else {
      format = "%1$,d秒";
      array = new Object[] { seconds };
    }
    return String.format(format, array);
  }
  
  
  private static final long K = 1024;
  private static final long M = K * K;
  private static final long G = M * K;
  private static final long T = G * K;

  public static String fileLengthToShow(final long value) {
    final long[] dividers = new long[] { T, G, M, K, 1 };
    final String[] units = new String[] { "TB", "GB", "MB", "KB", "B" };
    if (value < 0) {
      return String.valueOf(value);
    } 
    String result = null;
    for (int i = 0; i < dividers.length; i++) {
      final long divider = dividers[i];
      if (value >= divider) {
        result = format(value, divider, units[i]);
        break;
      }
    }
    return result;
  }

  private static String format(final long value, final long divider, final String unit) {
    final double result = divider > 1 ? (double) value / (double) divider : (double) value;
    return String.format("%.1f %s", Double.valueOf(result), unit);
  }
  
  
  private static final char[] ALL_INVAID_CHARS_IN_FILENAME = "\\/:*?\"<>|;,".toCharArray();
  
  private static final char[] FORBIDDEN_CHARS_IN_FILENAME = "\\/:*?\"<>|".toCharArray();
  
  private static final char[] TO_REPLACE_CHARS_IN_FILENAME = ";,".toCharArray();
  
  private static boolean _containsInArray(final char[] cs, char c) {
    for(char _c : cs) {
      if(_c == c) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * 把要生成的文件名标准化，只是文件名，不包含目录和路径！
   * 删除 \ / : * ? " < > |
   * @param fileName
   * @return
   */
  public static String fileNameStd(String fileName) {
    if(fileName == null) {
      return null;
    }
    fileName = fileName.replaceAll("\\s+", "_");
    if(StringUtils.containsAny(fileName, ALL_INVAID_CHARS_IN_FILENAME)) {
      StringBuilder sb = new StringBuilder();
      for(int i=0; i<fileName.length(); ++i) {
        char c = fileName.charAt(i);
        if(_containsInArray(FORBIDDEN_CHARS_IN_FILENAME, c)) {          
        } else if(_containsInArray(TO_REPLACE_CHARS_IN_FILENAME, c)) {
          sb.append('_');
        } else {
          sb.append(c);
        }
      }
      fileName = sb.toString();
    }
    
    return fileName;
  }
  
  /**
   * 把ftl模板文件生成静态文件输出
   * @param root
   * @param view
   * @param outFile
   */
  public static void genFtlToFile(Map<String, Object> root, String view, File outFile) {
    Configuration config = FreeMarkerRender.getConfiguration();
    Writer writer = null;
    try {
      Template template = config.getTemplate(view);
      writer = FileUtil.openFileBufferedWriter(outFile, I.UTF8);
      template.process(root, writer); // Merge the data-model and the template
    } catch (Exception e) {
      throw new RenderException(e);
    } finally {
      IOUtils.closeQuietly(writer);
    }
  }
  
//  private static int _getFilePathLength(File file) {
//    if(file == null) {
//      return 0;
//    }
//    String absolutePath = FileUtil.getAbsolutePath(file);
//    return absolutePath.length();
//  }
  
  private static int _getBaseDirPathLength(File dir, boolean useFirstDirToZip) {
    if(dir == null) {
      return 0;
    }
    try {
      File d = dir.getCanonicalFile();
      if(d != null) {
        dir = d;
      }
    } catch(Exception e) {
    }
    File pdir = dir.getParentFile();
    if(pdir != null && useFirstDirToZip) {
      dir = pdir;
    }
    String path  = FileUtil.getAbsolutePath(dir);
    int len = 0;
    if(path != null && (len = path.length()) > 0) {
      char c = path.charAt(len - 1); 
      return  (c == '\\' || c == '/') ? len : len + 1;
    }
    return 0; 
  }
  
  /**
   * 
   * @param dir
   * @param zipFile
   * @param useFirstDirToZip 是否使用第一级目录作为zip的第一级目录 <br />  
   *   例如 dir=e:/user/myfiles   <br />
   *     当 useFirstDirToZip == true 时, 生成的zip文件第一级目录是 myfiels  <br />
   *     当 useFirstDirToZip == false 时, 生成的zip文件第一级目录和文件是 myfiels下的文件和目录 <br />
   * @throws IOException 
   */
  public static void zipDir(File dir, File zipFile, boolean useFirstDirToZip) throws IOException {
    if(dir == null || !dir.exists() || !dir.isDirectory()) {
      throw new IllegalArgumentException("Parameter is not a legal dir:" + FileUtil.getAbsolutePath(dir));
    }
    FileUtil.ensureParentFileExists(zipFile);
    ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
    zipOut.setEncoding(I.UTF8);
    
    int baseDirLen = _getBaseDirPathLength(dir, useFirstDirToZip);
    
    LinkedList<File> dirList = new LinkedList<File>();
    dirList.add(dir);
//    int count = 0;
    try {
      while(dirList.size() > 0) {
        File d = dirList.removeFirst();
        if(d == null) {
          continue;
        }
        try {
          for(File f : d.listFiles()) {
            if(!f.exists() || !f.canRead()) {
              continue;
            }
            if(f.isDirectory()) {
              dirList.add(f);
            } else if(f.isFile() && !f.equals(zipFile)) {
              String entryPath = FileUtil.getAbsolutePath(f).substring(baseDirLen);
              CmdLog.info("加入entry:" + entryPath);
              ZipEntry entry = new ZipEntry(entryPath);
              zipOut.putNextEntry(entry);
//              ++count;
//              if(count > 1000) {
//                break;
//              }
              FileInputStream fis = null;
              try {
                fis = new FileInputStream(f);
                IOUtils.copy(fis, zipOut);
              } finally {
                IOUtils.closeQuietly(fis);
              }
            }
          }
        } catch(Exception e) {
          log.error("zip error, d=" + FileUtil.getAbsolutePath(d), e);
        }
      }
    } finally {
      IOUtils.closeQuietly(zipOut);
    }
  }


  public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
    return isSameDayOfMillis2(ms1, ms2);
  }
  
  
  static boolean isSameDayOfMillis1(final long ms1, final long ms2) {
    if(Math.abs(ms1 - ms2) >= I.ONE_DAY_IN_MS) {
      return false;
    }
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTimeInMillis(ms1);
    cal2.setTimeInMillis(ms2);
    return cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE);
  }
  
  static final String YMD = "yyyyMMdd";
  
  
  static boolean isSameDayOfMillis2(final long ms1, final long ms2) {
    if(Math.abs(ms1 - ms2) >= I.ONE_DAY_IN_MS) {
      return false;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(ms1);
    int d1 = cal.get(Calendar.DATE);
    cal.setTimeInMillis(ms2);
    return d1 == cal.get(Calendar.DATE);
    
//    if(Math.abs(ms1 - ms2) >= I.ONE_DAY_IN_MS) {
//      return false;
//    }
//    SimpleDateFormat sdf = new SimpleDateFormat();
//    String s1 = sdf.format(new Date(ms1));
//    String s2 = sdf.format(new Date(ms2));
//    return s1.equals(s2);
  }
  
  
  static Set<Character.UnicodeBlock> japChineseKoreaUnicodeBlocks = new HashSet<Character.UnicodeBlock>();
  static {
    // 日语
    japChineseKoreaUnicodeBlocks.add(Character.UnicodeBlock.HIRAGANA);
    japChineseKoreaUnicodeBlocks.add(Character.UnicodeBlock.KATAKANA);
    japChineseKoreaUnicodeBlocks.add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
    japChineseKoreaUnicodeBlocks.add(Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION);
    
    // 韩语
    japChineseKoreaUnicodeBlocks.add(Character.UnicodeBlock.HANGUL_SYLLABLES);
    
    // 中文
    japChineseKoreaUnicodeBlocks.add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
    
  }
  
  /**
   * 判断一个unicode字符是否 方块字符 
   * CJKV (Chinese, Japanese, Korean and Vietnamese)
   * @param c
   * @return
   */
  public static boolean isFangkuaiziChar(char c) {
//    boolean b = Character.isIdeographic(c);
    return japChineseKoreaUnicodeBlocks.contains(Character.UnicodeBlock.of(c));    
//    int v = (int)c;
////    return (v >=19968 && v <= 171941); 
//    return (v >=19968 && v <= 40869); 
  }
  
  static final String BIAODIAN_STR = ",.?!，。、？！()（）<>《》“”\"'‘’【】{}[]…—";
  static final Set<Character> BIADDIAN_CHARS = new LinkedHashSet<Character>();
  static {
    for(int i=0; i<BIAODIAN_STR.length(); ++i) {
      BIADDIAN_CHARS.add(BIAODIAN_STR.charAt(i));
    }
  }
  
  public static String removeBiaodianChars(String s) {
    if(s == null) {
      return s;
    }
    boolean met = false;
    for(int i=0; i<s.length(); ++i) {
      if(BIADDIAN_CHARS.contains(s.charAt(i))) {
        met = true;
        break;
      }
    }
    if(!met) {
      return s;
    }
    StringBuilder sb = new StringBuilder();
    for(int i=0; i<s.length(); ++i) {
      char c = s.charAt(i);
      if(!BIADDIAN_CHARS.contains(c)) {
        sb.append(c);
      }
    }
    return sb.toString();
  }
  

  private static long _p(String datetime) {
    return DateUtil.parseQuietly(datetime, "yyyy-MM-dd HH:mm:ss").getTime();
  }
  
  static void  testSameDay() {
    String [][] timess = {
      {"2016-01-01 23:19:99", "2016-01-01 23:59:99"},  
      {"2016-01-01 23:59:99", "2016-01-01 23:19:99"},
      
      {"2016-01-01 23:19:99", "2016-01-02 00:00:00"},      
      {"2016-01-02 00:00:00", "2016-01-01 23:19:99"},
      
      {"2016-01-02 23:59:59", "2016-01-02 00:00:00"},      
      {"2016-01-02 00:00:00", "2016-01-01 23:59:59"},
      
      {"2016-01-02 23:59:59", "2000-01-02 00:00:00"},      
      {"2000-01-02 00:00:00", "2016-01-01 23:59:59"},
      
      {"2016-01-02 23:59:59", "2016-01-03 00:00:00"},      
      {"2016-01-02 00:00:00", "2016-01-03 23:59:59"},
      
      {"2016-03-11 08:01:00", "2016-03-11 00:00:00"},
      {"2016-03-11 00:00:00", "2016-03-11 08:01:00"},
      
      {"2016-03-18 08:01:00", "2016-03-11 00:00:00"},
      {"2016-03-11 00:00:00", "2016-03-18 08:01:00"},
    };
    
    for(String[] times : timess) {
      boolean b1 = isSameDayOfMillis1(_p(times[0]), _p(times[1]));
      boolean b2 = isSameDayOfMillis2(_p(times[0]), _p(times[1])); 
      System.out.println(times[0] + "\t" + times[1] + "\t" + b1 + "\t" + b2);
    }
    
    int n = 100000;
    
    long t1 = System.currentTimeMillis();
    for(int i=0; i<n; ++i) {
      for(String[] times : timess) {
        isSameDayOfMillis1(_p(times[0]), _p(times[1]));
      }
    }
    long t2 = System.currentTimeMillis();
    for(int i=0; i<n; ++i) {
      for(String[] times : timess) {
        isSameDayOfMillis2(_p(times[0]), _p(times[1]));
      }
    }
    long t3 = System.currentTimeMillis();
    for(int i=0; i<n; ++i) {
      for(String[] times : timess) {
        isSameDayOfMillis(_p(times[0]), _p(times[1]));
      }
    }
    long t4 = System.currentTimeMillis();
    System.out.println("方法1用时:" + (t2-t1) + "ms, 方法2用时:" + (t3-t2) + "ms" + ", 方法用时:" + (t4-t3) + "ms");
  }
  
  
  static void testFangkuaiziChar() {
    String s =
        ""
        + "Кефир стоит пять юаней, а молоко – два юаня."
        + "글쓰기는 문화다. 글을 존중할 줄 아는 문화가 있어야 글쓰기 문화도 융성한다. 대한민국 사회는 여전히 글보다는 말에, 말보다는 영상에 기대는 사회인 듯싶다. 전 세계 어느 인터넷에서도 한국만큼 동영상 콘텐츠가 많은 나라는 없다는 게 전문가들의 지적이다. 글보다는 말을 좋아하고, 공적 담화보다는 사적 담화를 즐긴다. 한국은 여전히 ‘구어체 사회’다. 글쓰기가 융성하려면 우선 기록을 중요하게 생각해야 한다. 문자생활이 사회의 주류적인 의사소통 방식이며 정보저장 방식이고 신뢰받는 표현방식이어야 한다."
        + "彼がそんなことをする人間でないことは、あなたが"
        + "这是中文哈哈。why？? 所以我们只需要对每一个字符判断其是否位于这三个区间即可。另外，如果你希望检测某个字符串是否含有简体中文字符字符串、繁体中文字符串等，只需要查询对应语言的Unicode编码范围，对下面的代码稍作改动即可。"
        + "日圓急升「好日子回不去了」把握陽光 下周鋒面到將變天 周日晚上以後，降雨現象會轉為明顯，屆齡條款改了 老特勤想流淚 47歲特勤警察，一身戰技卻只能指揮交 換匯價漲破0.3元，匯銀主管指出，後續"
        // 法语：
        + "Ce texte ________ par une secrétaire expérimentée, Mon correspondant m’a demandé________ le plus vite possible à écrire    que j’écris   de lui écrire que j’écrive"
        ;
    int count = 0;
    for(int i=0; i<s.length(); ++i) {
      char c = s.charAt(i);
      if(isFangkuaiziChar(c)) {
        System.out.println(s.charAt(i) + "\t" + isFangkuaiziChar(c) + "\t" + (++count) + "\t" + (Character.UnicodeBlock.of(c)));
      } else {
        System.out.println(s.charAt(i) + "\t" + isFangkuaiziChar(c) + "\t" + (int)c + "\t" + (Character.UnicodeBlock.of(c)));
      }
    }
  }
  
  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    testFangkuaiziChar();
    
    
//    testSameDay();
//    String [] fileNames = {"aaa bbb   \t ccc", ":/sdf 呵呵", "15-16-1 学术英语Test 1-(1)"};
//    for(String fileName : fileNames) {
//      CmdLog.info(fileName + "\t---->\t" + fileNameStd(fileName));  
//    }
//    
////    String [] ss = {
////      "@11@22@33@", "@aa@bb", "123@456@", null, "", "  ", "zdssdf@1231@", "@45645656@","@1@2@3@@1@2@3@"
////    };
////    for(String s : ss) {
////      List<Integer> idList = stringMultiToListInteger(s);
////      Integer[] ids = stringMultiToArrayInteger(s);
////      CmdLog.info(s + "\t" + idList + "\t" + Arrays.toString(ids));
////      String s1 = listToStringMulti(idList);
////      CmdLog.info("****"+ "\t" + s1 + "\t" + s);
////      CmdLog.info("1111"+ listToStringMulti(idList));
////    }
//    for(int i=-1; i<3600; ++i) {
//      //CmdLog.info(fileLengthToShow(i));
////      CmdLog.info(secondToShow(i));
//    }
    
//    String [] paths = {
//        "E:\\dev\\\\ant\\lib\\\\",
//        "E:\\dev\\ant\\lib\\\\",
//        "E:\\dev\\\\ant\\lib\\",
//        "E:\\dev\\\\ant\\lib",
//        "E:\\dev\\ant\\lib\\",
//        "E:\\dev\\ant\\lib",
//        
//        "E:\\dev\\\\ant123\\lib1\\\\",
//        "E:\\dev\\ant123\\lib1\\\\",
//        "E:\\dev\\\\ant123\\lib1\\",
//        "E:\\dev\\\\ant123\\lib1",
//        "E:\\dev\\ant123\\lib1\\",
//        "E:\\dev\\ant123\\lib1",
//        
//        "E:/dev//ant/lib//",
//        "E:/dev/ant/lib//",
//        "E:/dev//ant/lib/",
//        "E:/dev//ant/lib",
//        "E:/dev/ant/lib/",
//        "E:/dev/ant/lib",
//        
//        "E:/dev//ant123/lib1//",
//        "E:/dev/ant123/lib1//",
//        "E:/dev//ant123/lib1/",
//        "E:/dev//ant123/lib1",
//        "E:/dev/ant123/lib1/",
//        "E:/dev/ant123/lib1",
//        
//    };
//    
//    for(int i=0; i<paths.length; ++i) {
//      File dir = new File(paths[i]);
//      CmdLog.info(paths[i] + "\t" + FileUtil.getAbsolutePath(dir) + "\t" + FileUtil.getAbsolutePath(dir.getParentFile()));
//    }
//    
//    File dir = new File("F:/temp/zip");
////    File dir = new File("F:");
//    File zipFile1 = new File("F:/temp/zip/生成的zip1.zip");
//    File zipFile2 = new File("F:/temp/zip/生成的zip2.zip");
//    zipDir(dir, zipFile1, true);
//    zipDir(dir, zipFile2, false);
    
//    String [] fs = {
//      "F:",
//      "F:/",
//      "/",
//      "f:/abc",
//      "F:/abc/"
//    };
//    for(String s : fs) {
//      File f = new File(s);
//      CmdLog.info(s + "\t" + FileUtil.getAbsolutePath(f) + "\t" + FileUtil.getAbsolutePath(f.getParentFile()));
//    }
    
  }
}
