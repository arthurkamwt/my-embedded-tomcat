package io.github.arthurkamwt.servlet;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import io.github.arthurkamwt.repository.GoogleUserRepository;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class GoogleAuthCallbackServlet extends AbstractAuthorizationCodeCallbackServlet {

  private static final Collection<String> SCOPES = Arrays.asList("email", "profile");

  private final String clientId;

  private final String clientSecret;

  private final GoogleUserRepository googleUserRepository;

  @Inject
  public GoogleAuthCallbackServlet(
      @Named("google-client-id") String clientId,
      @Named("google-client-secret") String clientSecret,
      GoogleUserRepository googleUserRepository) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.googleUserRepository = googleUserRepository;
  }

  @Override
  protected AuthorizationCodeFlow initializeFlow() {
    return new GoogleAuthorizationCodeFlow.Builder(
            new NetHttpTransport(),
            JacksonFactory.getDefaultInstance(),
            clientId,
            clientSecret,
            SCOPES)
        //        .setDataStoreFactory(DATA_STORE_FACTORY)
        .setAccessType("offline")
        .build();
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
    final String jsonIdentity = googleUserRepository.getUserInfo(credential);
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
