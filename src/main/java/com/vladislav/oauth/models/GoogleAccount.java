package com.vladislav.oauth.models;

import javax.persistence.Column;
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

  @Column(name = "google_id", unique = true)
  private String id;

  @Column(name = "google_name")
  private String name;

  @Column(name = "google_locale")
  private String locale;

  @Embedded
  private GoogleToken token;

}
