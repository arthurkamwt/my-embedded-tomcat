package io.github.arthurkamwt.servlet.authentication;

import java.io.IOException;
import javax.inject.Singleton;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class AuthenticationFilter extends HttpFilter {

  @Override
  protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    final Object identity = req.getSession().getAttribute("identity");
    if (identity == null && !req.getRequestURI().contains("/authentication/")) {
      res.sendRedirect("/authentication/google");
    } else {
      super.doFilter(req, res, chain);
    }
  }
}
