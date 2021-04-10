package com.vladislav.oauth.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class GoogleToken {

  @Column(name = "google_access_token")
  private String accessToken;

  @Column(name = "google_refresh_token")
  private String refreshToken;

  @Column(name = "google_scope")
  private String scope;

  @Column(name = "google_token_type")
  private String tokenType;

  @Column(name = "google_expires_in")
  private Integer expiresIn;

}