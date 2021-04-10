package com.vladislav.oauth.models;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Embeddable
public class GoogleAccount {

  private String id;
  private String name;
  private String locale;

  @Embedded
  private Token token;

}
