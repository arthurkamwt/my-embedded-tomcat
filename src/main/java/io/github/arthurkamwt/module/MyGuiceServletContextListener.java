package io.github.arthurkamwt.module;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import io.github.arthurkamwt.Server;

public class MyGuiceServletContextListener extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    return Server.injector;
  }
}
