/**
 * 
 */
package com.hzmd.iwrite.web.config;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hzmd.common.I;
import com.hzmd.common.exception.BizException;
import com.hzmd.common.util.FileUtil;
import com.hzmd.common.util.StringUtil;
import com.hzmd.iwrite.global.ITestCC;
import com.jfinal.core.JFinal;
import com.jfinal.core.JsonMsgStd;
import com.jfinal.core.JsonMsgStdImpl;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;

/**
 * 应用中要用到的配置项都在这里定义！！！
 * 关于JFinal上传的技术： http://www.oschina.net/question/67067_47226
 * 
 * @author yangming
 *
 */
public class G {

  /**
   * 
   */
  private G() {
  }

  private static final Logger log = LoggerFactory.getLogger(G.class);

  private static final String CONFIG_FILE = "config.properties";

  private static Prop prop;

  //////////////////////////////////////////////////////////////////////////////////
  // Add your biz config code BEGIN

  private static boolean devMode = false;

  private static String cookieDomain = null;

  private static UrlConfig uc;

  private static String error403View = "/itest/misc/403.html";

  private static String error404View = "/itest/misc/404.html";

  private static String error500View = "/itest/misc/500.html";
  
  private static String errorPermitView = "/itest/misc/permit_error.html";

  private static String tablePrev = "it_";

  /**
   * 通用的上传文件物理文件存储根目录
   */
  private static File uploadBaseDir;

  /**
   * 通用的上传文件url前缀
   */
  private static String uploadUrlPrev;

  // site.user.ssidKey=myappApooIP0Iu$#3880#$uI8dujaAUoO0ABC
  // ssid 加密算法
  private static String ssidKey = "myappAXpo@oIP0Iu$#3880#$uI8dujaAUoO0AYBC";
  
  /**
   * 是否能使用iwrite引擎(是只要能公网访问，就配置成true)  <br />
   * 对应配置项是 <br />
iwrite.use=true
   */
  private static boolean iwriteUse = false;
  
  /**
   * 是否购买了iwrite引擎，只有配置了iwrite引擎，才能访问外网的批改引擎地址。 <br />
   * 对应配置项是 <br />
iwrite.bought=true 
   */
  private static boolean iwriteBought = false;
  
  
  // 是否使用基础训练模块
  private static boolean jclxUse = false;
  
//  /**
//   * 存储和维护专门用于听力的串场话音频资源物理文件存储根目录
//   */
//  private static File ziyuanfilesBaseDir;
//
//  /**
//   * 存储和维护专门用于听力的串场话音频资源url前缀
//   */
//  private static String ziyuanfilesUrlPrev;

  /**
   * 原来听力的音频资源url前缀 <br />
   * 导入题目的mp3资源用公网的
   */
  private static String quesmp3UrlPrev;

  /**
   * 本项目默认的json快速输出定格式化数据
   */
  private static JsonMsgStd jsonMsgStd = new JsonMsgStdImpl(ITestCC.WebCC.JSON_CODE.SUCCESS, ITestCC.WebCC.JSON_CODE.ERROR,
      ITestCC.WebCC.JSON_CODE.NO_PERMISSION, ITestCC.WebCC.JSON_CODE.NOT_LOGIN);


  private static void checkUploadDir(File dir, String type) {
    long t1 = System.currentTimeMillis();
    long t2 = t1;

    if (dir.exists()) {
      if (!dir.isDirectory()) {
        t2 = System.currentTimeMillis();
        throw new BizException(type + "不是一个合法的目录:" + FileUtil.getAbsolutePath(dir) + ",检测用时:" + (t2 - t1) + "ms");
      }
    } else {
      boolean b = dir.mkdirs();
      if (!b) {
        t2 = System.currentTimeMillis();
        throw new BizException(type + "不存在，且不能创建" + dir.getAbsolutePath() + ",检测用时:" + (t2 - t1) + "ms");
      }
    }
    log.info(type + "检测合法:" + FileUtil.getAbsolutePath(dir) + ",检测用时:" + (t2 - t1) + "ms");
  }

  private static void _initBiz() {
    devMode = getProp_boolean("site.global.devMode", false);
    cookieDomain = StringUtil.trimEmptyToNull(getProp("site.global.cookieDomain", null));

    String adminPath = getProp("site.uc.adminPath", "/admin123");
    String apiUrlPath = getProp("site.uc.apiPath", "/appapi");
    String uploadTempPath = getProp("upload.tempDir", "/WEB-INF/upload_temp/");
    uc = new UrlConfig(adminPath, apiUrlPath, uploadTempPath);

    error403View = getProp("site.page.403", error403View);
    error404View = getProp("site.page.404", error404View);
    error500View = getProp("site.page.500", error500View);

    tablePrev = getProp("site.db.tablePrev", tablePrev);

    // upload.url.prev=http://192.168.0.99:1900/itest/upload
    // upload.dir=\\\\192.168.0.99\\upload

    uploadBaseDir = new File(getProp("upload.dir", "upload"));
    uploadUrlPrev = StringUtil.trimRight(getProp("upload.url.prev", "upload"), '/');
    checkUploadDir(uploadBaseDir, "配置项:upload.dir, uploadBaseDir");

//    ziyuanfilesBaseDir = new File(getProp("ziyuanfiles.dir", "ziyuanfiles"));
//    ziyuanfilesUrlPrev = StringUtil.trimRight(getProp("ziyuanfiles.url.prev", "ziyuanfiles"), '/');
//    checkUploadDir(ziyuanfilesBaseDir, "配置项:ziyuanfiles.dir, ziyuanfilesBaseDir");

    quesmp3UrlPrev = StringUtil.trimRight(getProp("quesmp3.url.prev", "/upload"), '/');

    ssidKey = getProp("site.user.ssidKey", ssidKey);
    iwriteUse = getProp_boolean("iwrite.use", iwriteUse);
    iwriteBought = getProp_boolean("iwrite.bought", iwriteBought);
    
    jclxUse = getProp_boolean("jcxl.use", jclxUse);
  }

  /**
   * 
   */
  public static void init() {
    PropKit.useless(CONFIG_FILE);
    prop = PropKit.use(CONFIG_FILE, I.UTF8);
    _initBiz();
  }

  public static void reload() {
    init();
  }

  static {
    try {
      init();
      log.trace("context path=[" + JFinal.me().getContextPath() + "]");
    } catch (Exception e) {
      Logs.SYSTEM_FATAL_LOG.error("G init error.", e);
    }
  }

  public static boolean isDevMode() {
    return devMode;
  }

  public static String getCookieDomain() {
    return cookieDomain;
  }

  public static UrlConfig uc() {
    return uc;
  }

  public static String getError403View() {
    return error403View;
  }

  public static String getError404View() {
    return error404View;
  }

  public static String getError500View() {
    return error500View;
  }
  /*
   * 许可验证错误页面
   */
  public static String getErrorPermitView() {
    return errorPermitView;
  }

  public static String tb(String tableName) {
    return tablePrev + tableName;
  }

  public static String getConfigFile() {
    return CONFIG_FILE;
  }

  private static String _getUrlFull(String relPath, String urlPrev) {
    if (relPath == null || (relPath = relPath.trim()).length() == 0) {
      return null;
    }
    if (relPath.startsWith("http")) {
      if (relPath.startsWith("http://") || relPath.startsWith("https://")) {
        return relPath;
      }
    }
    if (relPath.charAt(0) != '/') {
      return urlPrev + "/" + relPath;
    } else {
      return urlPrev + relPath;
    }
  }

  private static File _getRealFile(String relPath, File baseDir) {
    if (relPath == null || (relPath = relPath.trim()).length() == 0) {
      return null;
    }
    if (relPath.startsWith("http")) {
      if (relPath.startsWith("http://") || relPath.startsWith("https://")) {
        return new File(relPath);
      }
    }
    return new File(baseDir, relPath);
  }

//  /**
//   * 通过数据库中存储的上传文件的相对路径，得到全路径
//   * @param relPath
//   * @return
//   */
//  public static String getZiyuanFileUrlFull(String relPath) {
//    return _getUrlFull(relPath, ziyuanfilesUrlPrev);
//  }
//
//  public static File getZiyuanRealFile(String relPath) {
//    return _getRealFile(relPath, ziyuanfilesBaseDir);
//  }

  /**
   * 得到 通过 quesmp3.url.prev 配置的前缀组成的url全路径.
   * 参见 参见 com.hzmd.itest.model.ques.QuesJsonBean.getResPathFull() 
   * @param relPath
   * @return
   */
  public static String getQuesmp3UrlFull(String relPath) {
    return _getUrlFull(relPath, quesmp3UrlPrev);
  }

  public static File getQuesmp3RealFile(String relPath) {
    return new File(_getUrlFull(relPath, quesmp3UrlPrev));
  }
  
  public static boolean isIwriteUse() {
    return iwriteUse;
  }
  
  public static boolean isIwriteBought() {
    return iwriteBought;
  }

  public static void setIwriteUse(boolean canUseIWrite) {
    G.iwriteUse = canUseIWrite;
  }
  
  public static void setIwriteBought(boolean iwriteBought1) {
    G.iwriteBought = iwriteBought1;
  }
  
  public static boolean isJcxlUse() {
    return jclxUse;
  }

  /**
   * 通过数据库中存储的上传文件的相对路径，得到全路径
   * @param relPath
   * @return
   */
  public static String getUploadFileUrlFull(String relPath) {
    return _getUrlFull(relPath, uploadUrlPrev);
  }

  public static String getUploadUrlPrev() {
    return uploadUrlPrev;
  }

  public static File getUploadRealFile(String relPath) {
    return _getRealFile(relPath, uploadBaseDir);
  }

  /**
   * 根据relPath前缀智能判断选择相应的url前缀组成全路径
   * @param relPath
   * @return
   */
  public static String getUrlFullSmart(String relPath) {
//    if (StringUtil.isNotEmpty(relPath)) {
//      int pos = relPath.indexOf("resourcefile/");
//      if (pos == 0 || (pos == 1 && relPath.charAt(0) == '/')) {
//        return getQuesmp3UrlFull(relPath);
//      } else {
//        // TODO
//        pos = relPath.indexOf("_zy/");
//        if (pos == 0 || (pos == 1 && relPath.charAt(0) == '/')) {
//          return getZiyuanFileUrlFull(relPath);
//        } else {
//          return getUploadFileUrlFull(relPath);
//        }
//      }
//    }
//    return null;
    
    // 去掉资源目录的配置！
    if (StringUtil.isNotEmpty(relPath)) {
      int pos = relPath.indexOf("resourcefile/");
      if (pos == 0 || (pos == 1 && relPath.charAt(0) == '/')) {
        return getQuesmp3UrlFull(relPath);
      } else {
        // TODO
        return getUploadFileUrlFull(relPath);
      }
    }
    return null;    
  }

  /**
   * 
   * @param relPath
   * @return
   */
  public static File getRealFileSmart(String relPath) {
//    if (StringUtil.isNotEmpty(relPath)) {
//      int pos = relPath.indexOf("_zy/");
//      if (pos == 0 || (pos == 1 && relPath.charAt(0) == '/')) {
//        return getZiyuanRealFile(relPath);
//      } else {
//        // TODO
//        return getUploadRealFile(relPath);
//      }
//    }
//    return null;
    // 去掉资源路径的配置！！！ 全部采用upload配置项
    if (StringUtil.isNotEmpty(relPath)) {
      return getUploadRealFile(relPath);
    }
    return null;
  }

  /**
   * 
   */
  public static String toInfoString() {
    Map<String, Object> map = new LinkedHashMap<String, Object>();
    map.put("properties", getProperties());

    map.put("devMode", devMode);
    map.put("uc", uc.toJSONObject());
    map.put("error403View", error403View);
    map.put("error404View", error404View);
    map.put("error500View", error500View);
    
    map.put("uploadBaseDir", FileUtil.getAbsolutePath(uploadBaseDir));
    map.put("uploadUrlPrev", uploadUrlPrev);

//    map.put("ziyuanfilesBaseDir", FileUtil.getAbsolutePath(ziyuanfilesBaseDir));
//    map.put("ziyuanfilesUrlPrev", ziyuanfilesUrlPrev);

    map.put("quesmp3UrlPrev", quesmp3UrlPrev);
    map.put("iwriteUse", iwriteUse);
    map.put("iwriteBought", iwriteBought);

    
    return JsonKit.toJson(map);
  }
  
  public static String publicWorknet(){
	  return getProp("iwrite.publicnetwork","false");
  }

  // Add your biz config code END
  //////////////////////////////////////////////////////////////////////////////////

  public static String getProp(String key) {
    return prop.get(key);
  }

  public static String getProp(String key, String defaultVal) {
    return prop.get(key, defaultVal);
  }

  public static String getPropTrim(String key) {
    String value = getProp(key);
    return value != null ? value.trim() : value;
  }

  public static String getPropTrim(String key, String defaultValue) {
    String value = getProp(key, defaultValue);
    return value != null ? value.trim() : value;
  }

  public static Integer getProp_Integer(String key) {
    return prop.get_Integer(key);
  }

  public static Integer getProp_Integer(String key, Integer defaultValue) {
    return prop.get_Integer(key, defaultValue);
  }

  public static int getProp_int(String key) {
    return prop.get_int(key);
  }

  public static int getProp_int(String key, int defaultValue) {
    return prop.get_int(key, defaultValue);
  }

  public static Long getProp_Long(String key) {
    return prop.get_Long(key);
  }

  public static Long getProp_Long(String key, Long defaultValue) {
    return prop.get_Long(key, defaultValue);
  }

  public static long getProp_long(String key) {
    return prop.get_long(key);
  }

  public static long getProp_long(String key, long defaultValue) {
    return prop.get_long(key, defaultValue);
  }

  public static Boolean getProp_Boolean(String key) {
    return prop.get_Boolean(key);
  }

  public static Boolean getProp_Boolean(String key, Boolean defaultValue) {
    return prop.get_Boolean(key, defaultValue);
  }

  public static boolean getProp_boolean(String key) {
    return prop.get_boolean(key);
  }

  public static boolean getProp_boolean(String key, boolean defaultValue) {
    return prop.get_boolean(key, defaultValue);
  }

  public static Properties getProperties() {
    return prop.getProperties();
  }

  public static JsonMsgStd getJsonMsgStd() {
    return jsonMsgStd;
  }

  public static String getQuesmp3UrlPrev() {
    return quesmp3UrlPrev;
  }

  public static String getSsidKey() {
    return ssidKey;
  }
  
 

  /**
   * @param args
   */
  public static void main(String[] args) {
    //CmdLog.info(ItestUtil.formatDateTime(new Date()));

  }

}
