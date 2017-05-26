package com.hzmd.iwrite.web.config;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hzmd.common.util.DateUtil;
import com.hzmd.common.util.PropsUtil;
import com.hzmd.iwrite.db.ArpMappingInitItest;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.arp.ArpConfig;
import com.jfinal.ext.arp.ArpStarter;
import com.jfinal.plugin.druid.DruidStatViewHandler;

/**
 * 114.113.224.143 1126cp--927
 * getServletContext().getContextPath()  要么等于空串（ROOT目录下部署），要么等于 /xxx  （反斜杠开头的）
 * 
 * @author yangxianming
 * 
 */
public class WebConfig extends JFinalConfig {
  static final Logger log = LoggerFactory.getLogger(WebConfig.class);

  public WebConfig() {
  }

  @Override
  public void configConstant(Constants me) {
    
    log.info("loading properties from config.peroperties, got:" + G.toInfoString());
    
    me.setDevMode(G.isDevMode());
    me.setFreeMarkerViewExtension(".ftl");
    me.setError403View(G.getError403View());
    me.setError404View(G.getError404View());
    me.setError500View(G.getError500View());
    me.setUploadedFileSaveDirectory(G.uc().getUploadTempDirPhsicalPath());

    try {
      G.uc().checkUploadTempDir();
    } catch (Exception e) {
      Logs.SYSTEM_FATAL_LOG.error("Check upload temp dir error. uc=" + G.uc().toString(), e);
    }

    log.info("freemarker.template.Template.class.getClassLoader()="
        + freemarker.template.Template.class.getClassLoader());
    log.info("Thread.currentThread().getContextClassLoader()=" + Thread.currentThread().getContextClassLoader());
  }

  @Override
  public void configRoute(Routes me) {
    RouteConfig.init(me);
  }
  
  @Override
  public void configPlugin(Plugins me) {
    Properties dbProps = PropsUtil.extractSubProps(G.getProperties(), "db.main");
    ArpStarter ast = new ArpStarter(dbProps, ArpMappingInitItest.me);
    ast.configPlugin(me);
  }

  @Override
  public void configInterceptor(Interceptors me) {
    
  }

  @Override
  public void configHandler(Handlers me) {
    // 增加druid监控开始
    Properties dbProps = PropsUtil.extractSubProps(G.getProperties(), "db.main");
    ArpConfig arpConfig = new ArpConfig(dbProps);
    // 增加druid监控结束  
  }

  @Override
  public void afterJFinalStart(){
    // 设置默认时间为北京时间
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    FreemarkerConfig.initFreemarkerConfig();
    
    if(log.isDebugEnabled()) {
      log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ G配置信息如下 @@@@@@@@@@@@@@@@@@@@@@@@@@@\n" + G.toInfoString()
          + "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ G配置信息结束 @@@@@@@@@@@@@@@@@@@@@@@@@@@");
//      log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ActionMapping配置信息如下 @@@@@@@@@@@@@@@@@@@@@@@@@@@\n" 
//          + JFinal.me().getActionMapping().toInfoString()
//          + "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ActionMapping配置信息结束 @@@@@@@@@@@@@@@@@@@@@@@@@@@");
      
    }
  }
  
  @Override
  public void afterJFinalStop() {
    try {
      DateUtil.clear();
      log.info("DateUtil.clear() success.");
      log.info("Begin to deregisterDriver jdbc drivers");
//      Properties dbProps = PropsUtil.extractSubProps(G.getProperties(), "db.main");
//      ArpConfig arpConfig = new ArpConfig(dbProps);
//      Driver driver = DriverManager.getDriver(arpConfig.getJdbcUrl());
//      log.info("jdbc driver=" + driver);
//      DriverManager.deregisterDriver(driver);
      Enumeration<Driver> drivers = DriverManager.getDrivers();
      while(drivers.hasMoreElements()) {
        Driver driver = drivers.nextElement();        
        DriverManager.deregisterDriver(driver);
        log.info("Got jdbc driver:" + driver + " success to deregisterDriver it!");
      }
      log.info("End to deregisterDriver jdbc drivers");
    } catch(Exception e) {
      log.error("deregisterDrivererror!", e);
    }
  } 
}
