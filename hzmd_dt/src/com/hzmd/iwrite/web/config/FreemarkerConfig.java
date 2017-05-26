/**
 * 
 */
package com.hzmd.iwrite.web.config;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.core.JFinal;
import com.jfinal.render.FreeMarkerRender;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author yangxianming
 * 
 */
public abstract class FreemarkerConfig {

  static final Logger log = LoggerFactory.getLogger(FreemarkerConfig.class);

  /**
   * 
   */
  private FreemarkerConfig() {
  }

  static void initFreemarkerConfig() {
    try {
      Configuration config = FreeMarkerRender.getConfiguration();

      // Initialize the FreeMarker configuration;
      // - Create a configuration instance
      // config = new Configuration();
      // - Templates are stoted in the WEB-INF/templates directory of the Web
      // app.
      // config.setServletContextForTemplateLoading(G.getServletContext(),
      // "/WEB-INF/ftl/"); // "WEB-INF/templates"
      // - Set update dealy to 0 for now, to ease debugging and testing.
      // Higher value should be used in production environment.

      config.setServletContextForTemplateLoading(JFinal.me().getServletContext(), "/WEB-INF/ftl"); // "WEB-INF/templates"
      // config.setTemplateLoader(new
      // WebappTemplateLoader(G.getServletContext(), "/"));

      // TODO
      if (JFinal.me().getConstants().getDevMode()) {
        config.setTemplateUpdateDelay(0);
      } else {
        config.setTemplateUpdateDelay(120); // TODO 性能修改!!!
      }

      // - Set an error handler that prints errors so they are readable with
      // a HTML browser.
      config.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
      // config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

      // - Use beans wrapper (recommmended for most applications)
      //      config.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);   // freemarker从2.3.21开始用Builder处理，这里需要测试
      // TODO 需要研究 测试 和查看这些设置的手册！！！
      BeansWrapperBuilder builder = new BeansWrapperBuilder(Configuration.VERSION_2_3_21);
      // Set desired BeansWrapper configuration properties:
      builder.setUseModelCache(true);
      builder.setExposeFields(true);

      // Get the singleton:
      BeansWrapper beansWrapper = builder.build();

      config.setObjectWrapper(beansWrapper);

      // - Set the default charset of the template files
      config.setDefaultEncoding("UTF-8"); // config.setDefaultEncoding("ISO-8859-1");
      // - Set the charset of the output. This is actually just a hint, that
      // templates may require for URL encoding and for generating META element
      // that uses http-equiv="Content-type".
      config.setOutputEncoding("UTF-8"); // config.setOutputEncoding("UTF-8");
      // - Set the default locale
      config.setLocale(Locale.CHINA /* Locale.CHINA */); // config.setLocale(Locale.US);
      config.setLocalizedLookup(false);

      // 去掉int型输出时的逗号, 例如: 123,456
      // config.setNumberFormat("#"); // config.setNumberFormat("0"); 也可以
      // config.setNumberFormat("#0.#####");
      config.setNumberFormat("#");

      config.setSharedVariable("UC", G.uc());
      config.setSharedVariable("ctxPath", JFinal.me().getContextPath());
      config.setSharedVariable("FG", new ItestFreemarkerGlobal());

      // config.setSharedVariable("BizConsts", BizConsts.mgr);
      // config.setSharedVariable("PageBiz", PageBiz.mgr);

    } catch (Exception e) {
      log.error("Set freemarkerrender config failed", e);
    }
  }
}
