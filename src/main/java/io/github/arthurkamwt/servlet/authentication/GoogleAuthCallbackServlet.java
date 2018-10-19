package io.github.arthurkamwt.servlet.authentication;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.api.client.http.GenericUrl;
import com.google.gson.JsonObject;
import io.github.arthurkamwt.repository.GoogleUserRepository;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class GoogleAuthCallbackServlet extends AbstractAuthorizationCodeCallbackServlet {

  private final AuthorizationCodeFlow authorizationCodeFlow;

  private final GoogleUserRepository googleUserRepository;

  @Inject
  public GoogleAuthCallbackServlet(
      AuthorizationCodeFlow authorizationCodeFlow, GoogleUserRepository googleUserRepository) {
    this.authorizationCodeFlow = authorizationCodeFlow;
    this.googleUserRepository = googleUserRepository;
  }

  @Override
  protected AuthorizationCodeFlow initializeFlow() {
    return authorizationCodeFlow;
  }

  @Override
  protected String getUserId(HttpServletRequest httpServletRequest) {
    return httpServletRequest.getSession().getId();
  }

  @Override
  protected String getRedirectUri(HttpServletRequest req) {
    final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
    url.setRawPath("/authentication/google/callback");
    return url.build();
  }

  @Override
  protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
      throws ServletException, IOException {
    final JsonObject jsonIdentity = googleUserRepository.getUserInfo(credential);
    req.getSession().setAttribute("identity", jsonIdentity);
    resp.sendRedirect("/");
  }

  @Override
  protected void onError(
      HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
      throws ServletException, IOException {
    log.info("Error response: {}", errorResponse);
  }
}
