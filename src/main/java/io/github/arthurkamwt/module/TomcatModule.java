package io.github.arthurkamwt.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.JarScanner;
import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.apache.tomcat.util.scan.StandardJarScanner;

@Slf4j
public class TomcatModule extends AbstractModule {

  private JarScanner getJarScanner() {
    final JarScanner jarScanner = new StandardJarScanner();
    ((StandardJarScanner) jarScanner).setScanClassPath(false);
    return jarScanner;
  }

  private Collection<ErrorPage> getErrorPages() {
    final Collection<ErrorPage> errorPages = new HashSet();

    final ErrorPage error404 = new ErrorPage();
    error404.setErrorCode(404);
    error404.setLocation("/404.html");
    errorPages.add(error404);

    final ErrorPage error500 = new ErrorPage();
    error500.setErrorCode(500);
    error500.setLocation("/500.html");
    errorPages.add(error500);

    return errorPages;
  }

  @Provides
  @Singleton
  Tomcat getTomcat() throws Exception {
    final Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);

    final Context context = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());

    context.setJarScanner(getJarScanner());

    final FilterDef filterDef = new FilterDef();
    filterDef.setFilterName("GuiceFilter");
    filterDef.setFilterClass(com.google.inject.servlet.GuiceFilter.class.getName());
    context.addFilterDef(filterDef);

    final FilterMap filterMap = new FilterMap();
    filterMap.setFilterName("GuiceFilter");
    filterMap.addURLPatternDecoded("/*");
    context.addFilterMap(filterMap);

    context.addApplicationListener(MyGuiceServletContextListener.class.getName());

    getErrorPages().stream().forEach(errorPage -> context.addErrorPage(errorPage));

    return tomcat;
  }
}
