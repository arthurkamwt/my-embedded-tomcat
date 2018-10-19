package io.github.arthurkamwt.servlet.authentication;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.http.GenericUrl;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class GoogleAuthServlet extends AbstractAuthorizationCodeServlet {

  private final AuthorizationCodeFlow authorizationCodeFlow;

  @Inject
  public GoogleAuthServlet(AuthorizationCodeFlow authorizationCodeFlow) {
    this.authorizationCodeFlow = authorizationCodeFlow;
  }

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws IOException {
    return authorizationCodeFlow;
  }

  @Override
  protected String getUserId(HttpServletRequest httpServletRequest) {
    return httpServletRequest.getSession().getId();
  }

  @Override
  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
    final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
    url.setRawPath("/authentication/google/callback");
    return url.build();
  }
}
