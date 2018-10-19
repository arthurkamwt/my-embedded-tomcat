package io.github.arthurkamwt.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

@Slf4j
public class TomcatModule extends AbstractModule {

  @Provides
  Tomcat getTomcat() throws Exception {
    final Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);

    final Context context = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());

    final FilterDef filterDef = new FilterDef();
    filterDef.setFilterName("GuiceFilter");
    filterDef.setFilterClass(com.google.inject.servlet.GuiceFilter.class.getName());
    context.addFilterDef(filterDef);

    final FilterMap filterMap = new FilterMap();
    filterMap.setFilterName("GuiceFilter");
    filterMap.addURLPatternDecoded("/*");
    context.addFilterMap(filterMap);

    context.addApplicationListener(MyGuiceServletContextListener.class.getName());

    final ErrorPage errorPage404 = new ErrorPage();
    errorPage404.setErrorCode(404);
    errorPage404.setLocation("/404.html");
    context.addErrorPage(errorPage404);

    return tomcat;
  }
}
