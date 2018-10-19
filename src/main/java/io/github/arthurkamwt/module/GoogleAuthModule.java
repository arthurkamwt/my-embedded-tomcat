package io.github.arthurkamwt.module;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import java.util.Arrays;
import java.util.Collection;
import javax.inject.Named;

public class GoogleAuthModule extends AbstractModule {

  private static final Collection<String> SCOPES = Arrays.asList("email", "profile");

  @Override
  protected void configure() {
    bind(HttpTransport.class).to(NetHttpTransport.class).asEagerSingleton();
    bind(JsonFactory.class).toInstance(JacksonFactory.getDefaultInstance());
  }

  @Provides
  @Singleton
  AuthorizationCodeFlow getGoogleAuthorizationCodeFlow(
      @Named("google-client-id") String clientId,
      @Named("google-client-secret") String clientSecret,
      HttpTransport httpTransport,
      JsonFactory jsonFactory) {

    return new GoogleAuthorizationCodeFlow.Builder(
            httpTransport, jsonFactory, clientId, clientSecret, SCOPES)
        .build();
  }
}
