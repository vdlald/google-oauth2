package com.vladislav.oauth.configs;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpConfig {

  @Bean
  public OkHttpClient okHttpClient() {
    return new OkHttpClient();
  }
}
