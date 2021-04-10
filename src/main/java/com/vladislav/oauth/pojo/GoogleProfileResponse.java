package com.vladislav.oauth.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleProfileResponse {

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("given_name")
  private String givenName;

  @JsonProperty("locale")
  private String locale;

  @JsonProperty("family_name")
  private String familyName;

  @JsonProperty("picture")
  private String picture;

}