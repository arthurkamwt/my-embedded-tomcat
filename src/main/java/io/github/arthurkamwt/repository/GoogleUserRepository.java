package io.github.arthurkamwt.repository;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class GoogleUserRepository {

  private static final String USERINFO_ENDPOINT =
      "https://www.googleapis.com/plus/v1/people/me/openIdConnect";

  private final HttpTransport httpTransport;

  @Inject
  public GoogleUserRepository(HttpTransport httpTransport) {
    this.httpTransport = httpTransport;
  }

  public JsonObject getUserInfo(final Credential credential) throws IOException {
    final HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);
    final GenericUrl url = new GenericUrl(USERINFO_ENDPOINT);

    final HttpRequest request = requestFactory.buildGetRequest(url);
    request.getHeaders().setContentType("application/json");
    final String jsonIdentity = request.execute().parseAsString();

    return new JsonParser().parse(jsonIdentity).getAsJsonObject();
  }
}
