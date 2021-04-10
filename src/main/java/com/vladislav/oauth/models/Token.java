package com.vladislav.oauth.models;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Token {

  private String accessToken;
  private String refreshToken;
  private String scope;
  private String tokenType;
  private Integer expiresIn;

}