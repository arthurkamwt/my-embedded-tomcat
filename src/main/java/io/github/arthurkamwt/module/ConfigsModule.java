package io.github.arthurkamwt.module;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigsModule extends AbstractModule {

  // src/main/resources
  private static String credentialsFile = "google-client-secret.json";

  private final JsonObject credentialJson;

  public ConfigsModule() {
    final Reader reader =
        new BufferedReader(
            new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(credentialsFile)));
    this.credentialJson = new Gson().fromJson(reader, JsonObject.class);
  }

  @Named("google-client-id")
  @Provides
  @Singleton
  String getGoogleClientId() {
    return credentialJson.getAsJsonObject("web").get("client_id").getAsString();
  }

  @Named("google-client-secret")
  @Provides
  @Singleton
  String getGoogleClientSecret() {
    return credentialJson.getAsJsonObject("web").get("client_secret").getAsString();
  }
}
