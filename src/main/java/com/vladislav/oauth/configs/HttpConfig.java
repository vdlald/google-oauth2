package com.vladislav.oauth.configs;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpConfig {

  @Bean
  public OkHttpClient okHttpClient() {
    return new OkHttpClient();
  }

  @Bean
  public String googleSignInHref(
      @Value("${secret.redirect_uri}") String redirectUri,
      @Value("${secret.client_id}") String clientId
  ) {
    return "https://accounts.google.com/o/oauth2/v2/auth"
        + "?scope=https://www.googleapis.com/auth/userinfo.profile&"
        + "access_type=online&"
        + "include_granted_scopes=true&"
        + "response_type=code&"
        + "redirect_uri=" + redirectUri + "&"
        + "client_id=" + clientId;
  }
}
