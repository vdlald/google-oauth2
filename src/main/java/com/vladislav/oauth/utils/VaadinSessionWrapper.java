package com.vladislav.oauth.utils;

import com.vaadin.flow.server.VaadinSession;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VaadinSessionWrapper {

  public static String getAccessToken() {
    return (String) VaadinSession.getCurrent().getSession().getAttribute("accessToken");
  }
}
