package com.vladislav.oauth.components.googleclient;

import static com.vladislav.oauth.utils.VaadinSessionWrapper.getAccessToken;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladislav.oauth.pojo.ExchangeTokenResponse;
import com.vladislav.oauth.pojo.GoogleProfileResponse;
import com.vladislav.oauth.utils.UtilsMethods;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleClient {

  @Value("${google.client.url}")
  private String url;

  @Value("${google.client.token}")
  private String tokenUrl;

  @Value("${secret.client_id}")
  private String clientId;

  @Value("${secret.client_secret}")
  private String clientSecret;

  @Value("${secret.redirect_uri}")
  private String redirectUri;

  private final OkHttpClient client;
  private final ObjectMapper objectMapper;

  public ExchangeTokenResponse exchangeToken(String code) {
    // create request
    final HashMap<String, String> params = new HashMap<>();
    params.put("code", code);
    params.put("client_id", clientId);
    params.put("client_secret", clientSecret);
    params.put("grant_type", "authorization_code");
    params.put("redirect_uri", redirectUri);

    final String requestBodyRaw = UtilsMethods.mapToQueryParams(params);

    final RequestBody requestBody = RequestBody.create(
        requestBodyRaw.getBytes(StandardCharsets.UTF_8));

    final Request request = new Builder()
        .header("Content-Type", "application/x-www-form-urlencoded")
        .url(tokenUrl)
        .post(requestBody)
        .build();

    // execute request
    try {
      final Response response = client.newCall(request).execute();
      final String json = response.body().string();

      if (response.isSuccessful()) {
        return objectMapper.readValue(json, ExchangeTokenResponse.class);
      } else {
        throw new RuntimeException("Error exchange token: " + json);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public GoogleProfileResponse getProfile() {
    // create request
    final String accessToken = getAccessToken();
    final Request request = new Builder()
        .url(url + "/v1/userinfo?alt=json&access_token=" + accessToken)
        .get()
        .build();

    // execute request
    try {
      final Response response = client.newCall(request).execute();

      final String json = response.body().string();

      return objectMapper.readValue(json, GoogleProfileResponse.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
