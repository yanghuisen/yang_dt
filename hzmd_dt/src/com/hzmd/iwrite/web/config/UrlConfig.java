/**
 * 
 */
package com.hzmd.iwrite.web.config;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hzmd.common.exception.BizException;
import com.hzmd.common.util.FileUtil;
import com.hzmd.common.util.StringUtil;
import com.jfinal.core.JFinal;

/**
 * getServletContext().getContextPath()  要么等于空串（ROOT目录下部署），要么等于 /xxx  （斜杠开头的） <br />
 * 所有的URL前缀（例如apiUrlPath、adminUrlPath）也保证不以斜杠结尾，要么是空串，要么是斜杠开头，不以斜杠结尾
 * @author yadan
 */
public class UrlConfig {
  
  /**
   * 
   */
  private String apiUrlPath = "/appapi";
  
  private String adminUrlPath = "/admin123";

  private String uploadTempPath = "WEB-INF/upload_temp/";

  private File uploadTempDir;

  public UrlConfig() {
  }

  /**
   * JFinal 初始化好之后还要单独调用一下 checkUploadTempDir() 方法！
   * 创建upload的临时目录。
   * 因为这里可能ServletContext还没有准备好！
   * @param adminUrlPath
   * @param apiUrlPath
   * @param uploadTempPath
   */
  public UrlConfig(String adminUrlPath, String apiUrlPath, String uploadTempPath) {
    if (adminUrlPath != null) {
      this.adminUrlPath = StringUtil.addLeftIfNotExist(StringUtil.trimRight(adminUrlPath, '/'), '/');
    }
    if (apiUrlPath != null) {
      this.apiUrlPath = StringUtil.addLeftIfNotExist(StringUtil.trimRight(apiUrlPath, '/'), '/');
    }
    if (StringUtils.isNotEmpty(uploadTempPath)) {
      this.uploadTempPath = StringUtil.addRightIfNotExist(uploadTempPath, '/');
    }
  }

  public void checkUploadTempDir() {
    try {
      getUploadTempDir().mkdirs();
    } catch (Exception e) {
      throw new BizException("mkdir :" + FileUtil.getAbsolutePath(uploadTempDir) + " error!", e);
    }
    if (!uploadTempDir.canRead()) {
      throw new BizException("Permission denied: uploadTempDir " + FileUtil.getAbsolutePath(uploadTempDir)
          + " cann't read.");
    }
    if (!uploadTempDir.canWrite()) {
      throw new BizException("Permission denied: uploadTempDir : " + FileUtil.getAbsolutePath(uploadTempDir)
          + " cann't write.");
    }
  }
  
  /**
   * api请求url （不带content path!）
   */
  public String apiPath(String path) {
    return _urlPathCombo(apiUrlPath, path);
  }

  /**
   * api请求url （带content path!）
   */
  public String apiPathFull(String path) {
    return cp() + _urlPathCombo(apiUrlPath, path);
  }  

  private String _urlPathCombo(String urlPre, String path) {
    if(path == null || path.length() == 0) {
      return urlPre;
    }
    if(path.charAt(0) != '/') {
      return urlPre + "/" + path;
    }
    return urlPre + path;
  }
  
  /**
   * admin 后台的url组合成admin url （不带content path!）
   */
  public String adminPath(String path) {
    return _urlPathCombo(adminUrlPath, path);
  }
  

  /**
   * admin 后台的url组合成实际的admin url （带content path!）
   */
  public String adminPathFull(String path) {
    return cp() + adminPath(path);
  }

  public ServletContext getServletContext() {
    return JFinal.me().getServletContext();
  }

  public String cp() {
    return getServletContext().getContextPath();
  }

  public File getUploadTempDir() {
    if (uploadTempDir == null && getServletContext() != null) {
      uploadTempDir = new File(getServletContext().getRealPath(this.uploadTempPath));
      uploadTempDir.mkdirs();
    }
    return uploadTempDir;
  }
  
  /**
   *  
   * @return
   */
  public String getUploadTempDirPhsicalPath() {
    File uploadTempDir = getUploadTempDir();
    if(uploadTempDir == null) {
      return null;
    } else {
      return FileUtil.getAbsolutePath(uploadTempDir);  
    }
  }

  public String ap() {
    return adminUrlPath;
  }

  public String apf() {
    return cp() + adminUrlPath;
  }

  public String getAdminUrlPath() {
    return adminUrlPath;
  }
  
  public String getApiUrlPath() {
    return apiUrlPath;
  }
  
  public String getApiUrlPathFull() {
    return cp() + apiUrlPath;
  }

  public String getUploadTempPath() {
    return uploadTempPath;
  }

  /**
   * 得到后台登录页面的全路径
   */
  public String pathAdminLoginFull() {
    return adminPathFull("/login");
  }

  public String pathAdminLogin() {
    return adminPath("/login");
  }

  /**
   * 得到后台登录页面的全路径
   */
  public String pathAdminIndexFull() {
    return adminPathFull("/");
  }

  public String pathAdminIndex() {
    return adminPath("/");
  }
  
  public Map<String, Object> toJSONObject() {
    Map<String, Object> ret = new LinkedHashMap<String, Object>();
    ret.put("apiUrlPath", getApiUrlPath());
    ret.put("adminUrlPath", getAdminUrlPath());
    ret.put("uploadTempDirPhsicalPath", getUploadTempDirPhsicalPath());
    ret.put("uploadTempPath", getUploadTempPath());
    return ret;
  }
  
  public String toString() {
    return JSON.toJSONString(toJSONObject());
  }

  // /////////////////////////////////////////////////////////////
  
  public static void main(String[] args) {
    String s = "";
    String s1 = StringUtil.addLeftIfNotExist(StringUtil.trimRight(s, '/'), '/');
    System.out.println(s + "=" + s1);
    s = "adc";
    s1 = StringUtil.addLeftIfNotExist(StringUtil.trimRight(s, '/'), '/');
    System.out.println(s + "=" + s1);
  }

}
