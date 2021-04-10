package com.vladislav.oauth.components.googleclient;

import static com.vladislav.oauth.utils.VaadinSessionWrapper.getAccessToken;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladislav.oauth.pojo.GoogleProfileResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleClient {

  @Value("${google.client.url}")
  private String url;

  private final OkHttpClient client;
  private final ObjectMapper objectMapper;

  public GoogleProfileResponse getProfile() {
    final String accessToken = getAccessToken();
    final Request request = new Builder()
        .url(url + "/v1/userinfo?alt=json&access_token=" + accessToken)
        .get()
        .build();

    try {
      final Response response = client.newCall(request).execute();

      final String json = response.body().string();

      return objectMapper.readValue(json, GoogleProfileResponse.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
