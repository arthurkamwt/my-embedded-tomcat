package io.github.arthurkamwt;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.github.arthurkamwt.module.ConfigsModule;
import io.github.arthurkamwt.module.MyGuiceServletModule;
import io.github.arthurkamwt.module.TomcatModule;
import org.apache.catalina.startup.Tomcat;

public class Server {

  public static final Injector injector =
      Guice.createInjector(new ConfigsModule(), new MyGuiceServletModule(), new TomcatModule());

  public static void main(String[] args) throws Exception {
    final Tomcat tomcat = injector.getInstance(Tomcat.class);
    tomcat.start();
    tomcat.getServer().await();
  }
}
