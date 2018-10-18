package io.github.arthurkamwt.servlet;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class GoogleAuthServlet extends AbstractAuthorizationCodeServlet {

  private static final Collection<String> SCOPES = Arrays.asList("email", "profile");

  private final String clientId;

  private final String clientSecret;

  @Inject
  public GoogleAuthServlet(
      @Named("google-client-id") String clientId,
      @Named("google-client-secret") String clientSecret) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws IOException {
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
    return null;
  }

  @Override
  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
    final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
    url.setRawPath("/authentication/google/callback");
    return url.build();
  }
}
