/**
 * 
 */
package com.hzmd.iwrite.web.config;
import com.hzmd.iwrite.web.controller.ErrorStatisticsController;
import com.hzmd.iwrite.web.controller.WriteErrorController;
import com.jfinal.config.Routes;

/**
 * @author yangming
 *
 */
public abstract class RouteConfig {

  static void init(Routes me) {
	  me.add("/error", WriteErrorController.class,"/error");
	  me.add("/error_statistics",ErrorStatisticsController.class,"/error");
  }
  
}