package io.github.arthurkamwt.module;

import com.google.inject.servlet.ServletModule;
import io.github.arthurkamwt.servlet.AuthenticationFilter;
import io.github.arthurkamwt.servlet.GoogleAuthCallbackServlet;
import io.github.arthurkamwt.servlet.GoogleAuthServlet;
import io.github.arthurkamwt.servlet.IndexServlet;

public class MyGuiceServletModule extends ServletModule {

  @Override
  protected void configureServlets() {
    // first filter, in order, all that matches
    filter("/*").through(AuthenticationFilter.class);

    // then serve, in order, first that matches
    serve("/").with(IndexServlet.class);
    serve("/authentication/google").with(GoogleAuthServlet.class);
    serve("/authentication/google/callback").with(GoogleAuthCallbackServlet.class);
  }
}
